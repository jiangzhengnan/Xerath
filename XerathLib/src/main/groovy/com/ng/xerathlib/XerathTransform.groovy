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
        return "OriannaTransform"
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
        // OutputProvider管理输出路径，如果消费型输入为空，你会发现OutputProvider == null
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        transformInvocation.getInputs().each {
            TransformInput input ->
                //这里面存放第三方的 jar 包
                input.jarInputs.each {
                    //暂不需要处理
                }

                //这里存放着开发者手写的类
                input.directoryInputs.each {
                    DirectoryInput directoryInput ->
                        // 处理源码文件
                        processDirectoryInputs(directoryInput, outputProvider,transformInvocation)
                }
        }
    }

    /**
     * 处理源码文件
     */
    private void processDirectoryInputs(DirectoryInput directoryInput,
                                        TransformOutputProvider outputProvider,
                                        TransformInvocation transformInvocation) {
        def dest = outputProvider
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
                    if (target.exists()) {
                        target.delete()
                    }
                    //将修改的覆盖掉
                    FileUtils.copyFile(entry.getValue(), target)
                    entry.getValue().delete()
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