package com.ng.xerathlib

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

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
        //return "XerathTransform"
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
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
        inputs.each {
            TransformInput input ->
                //这里面存放第三方的 jar 包
                input.jarInputs.each {
                    //暂无需处理
                }
                //这里存放着开发者手写的类
                input.directoryInputs.each {
                    DirectoryInput dirInput ->
                        //处理需要插桩的文件
                        modifyClassWithPath(dirInput.file)
                        //Copy修改之后的文件
                        File dest = outputProvider.getContentLocation(dirInput.name, dirInput.contentTypes,
                                dirInput.scopes, Format.DIRECTORY)
                        FileUtils.copyDirectory(dirInput.file, dest)
                }
        }
    }

    void modifyClassWithPath(File dir) {
        def root = dir.absolutePath
        dir.eachFileRecurse { File file ->
            def filePath = file.absolutePath
            //过滤非class文件
            if (!filePath.endsWith(".class")) return
            def className = getClassName(root, filePath)
            //过滤系统文件
            if (isSystemClass(className)) return
            //hook关键代码
            hookClass(filePath, className)
        }
    }

    private static void hookClass(String filePath, String className) {
        //1.声明ClassReader
        ClassReader reader = new ClassReader(new FileInputStream(new File(filePath)))
        //2声明 ClassWriter
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
        //3声明ClassVisitor
        CostTimeClassAdapter adapter = new CostTimeClassAdapter(writer)
        //4调用accept方法 传入classVisitor
        reader.accept(adapter, ClassReader.EXPAND_FRAMES)
        if (adapter.changed) {
            println className + "is changed:" + adapter.changed
            byte[] bytes = writer.toByteArray()
            FileOutputStream fos = new FileOutputStream(new File(filePath))
            fos.write(bytes)
        }
    }


    //默认排除
    private static final DEFAULT_EXCLUDE = [
            '^android\\..*',
            '^androidx\\..*',
            '.*\\.R$',
            '.*\\.R\\$.*$',
            '.*\\.BuildConfig$',
    ]

    //获取类名
    private static String getClassName(String root, String classPath) {
        return classPath.substring(root.length() + 1, classPath.length() - 6)
                .replaceAll("/", ".")       // unix/linux
                .replaceAll("\\\\", ".")    //windows
    }

    private static boolean isSystemClass(String fileName) {
        for (def exclude : DEFAULT_EXCLUDE) {
            if (fileName.matches(exclude)) return true
        }
        return false
    }



}