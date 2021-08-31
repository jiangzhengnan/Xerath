package com.ng.xerathlib

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import groovy.io.FileType
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Project

/**
 * 自定义的 Transform 类
 */
class XerathTransform extends Transform {
    Project mProject

    XerathTransform(Project project) {
        mProject = project
    }

    /**
     * Task Name
     */
    @Override
    String getName() {
        return "customPumpkin"
    }

    /**
     * 需要处理的数据类型，这里处理class文件
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 操作内容范围,处理所有的class字节码
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 当前Transform是否支持增量编译
     */
    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        transformInvocation.getInputs().each {
            TransformInput input ->
                //这里面存放第三方的 jar 包
                input.jarInputs.each {
                    JarInput jarInput ->
                        String destName = jarInput.file.name
                        String absolutePath = jarInput.file.absolutePath
                        println "jarInput destName: ${destName}"
                        println "jarInput absolutePath: ${absolutePath}"
                        // 重命名输出文件（同目录copyFile会冲突）
                        def md5Name = DigestUtils.md5(absolutePath)
                        if (destName.endsWith(".jar")) {
                            destName = destName.substring(0, destName.length() - 4)
                        }

                        //def modifyJar = ModifyUtils.modifyJar(jarInput.file, transformInvocation.context.getTemporaryDir())
                        def modifyJar = null
                        if (modifyJar == null) {
                            modifyJar = jarInput.file
                        }

                        //获取输出文件
                        File dest = transformInvocation.getOutputProvider()
                                .getContentLocation(destName+"_"+md5Name,
                                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                        //中间可以将 jarInput.file 进行操作！
                        //copy 到输出目录
                        FileUtils.copyFile(modifyJar, dest)
                }

                //这里存放着开发者手写的类
                input.directoryInputs.each {
                    DirectoryInput directoryInput ->
                        def dest = transformInvocation.getOutputProvider()
                                .getContentLocation(
                                        directoryInput.name,
                                        directoryInput.contentTypes,
                                        directoryInput.scopes, Format.DIRECTORY)
                        println "directory output dest: $dest.absolutePath"
                        File dir = directoryInput.file
                        HashMap<String, File> modifyMap = new HashMap<>()
                        if (dir) {
                            dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) {
                                File classFile -> //遍历一遍把需要遍历的类存到 map
                                    if (!classFile.name.endsWith("R.class")
                                            && !classFile.name.endsWith("BuildConfig.class")
                                            && !classFile.name.contains("R\$")) {
                                        File modified = modifyClassFile(dir, classFile, transformInvocation.context.getTemporaryDir())
                                        if (modified != null) {
                                            modifyMap.put(classFile.absolutePath.replace(dir.absolutePath, ""), modified)
                                        }
                                    }
                            }
                            FileUtils.copyDirectory(directoryInput.file, dest)
                            //取出 map
                            modifyMap.entrySet().each {
                                Map.Entry<String, File> entry ->
                                    File target = new File(dest.absolutePath + entry.getKey());
                                    if(target.exists()) {
                                        target.delete()
                                    }
                                    //将修改的覆盖掉
                                    FileUtils.copyFile(entry.getValue(), target)
                                    entry.getValue().delete()
                            }
                        }
                }
        }
    }

    /**
     * 修改目录里的 class
     * @param dir
     * @param classFile
     * @param tempDir
     * @return
     */
    private static File modifyClassFile(File dir, File classFile, File tempDir) {
        File modified = null
        try {
            println "dir.absolutePath + File.separator: ${dir.absolutePath + File.separator}"
            String className = path2ClassName(classFile.absolutePath.replace(dir.absolutePath + File.separator, ""))
            println "className: $className"
            byte[] sourceClassBytes = IOUtils.toByteArray(new FileInputStream(classFile))
            byte[] modifyClassBytes = ModifyUtils.modifyClasses(sourceClassBytes)
            if (modifyClassBytes) {
                modified = new File(tempDir, className.replace('.', '') + '.class')
                if (modified.exists()) {
                    modified.delete()
                }
                modified.createNewFile()
                new FileOutputStream(modified).write(modifyClassBytes)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return modified
    }
    private static String path2ClassName(String pathName) {
        pathName.replace(File.separator, ".").replace(".class", "")
    }
}