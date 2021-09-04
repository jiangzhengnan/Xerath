package com.ng.xerathlib

import com.android.build.api.transform.*
import com.android.utils.FileUtils
import org.apache.commons.codec.digest.DigestUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

/**
 * Transform工具类
 */
class TransformUtil  {

    //ASM注入
    static void hookClass(String filePath, String className) {
        //1.声明ClassReader
        ClassReader reader = new ClassReader(new FileInputStream(new File(filePath)))
        //2声明 ClassWriter
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
        //3声明ClassVisitor
        CostTimeClassVisitor adapter = new CostTimeClassVisitor(writer)
        //4调用accept方法 传入classVisitor
        reader.accept(adapter, ClassReader.EXPAND_FRAMES)
        if (adapter.changed) {
            println className + "is changed:" + adapter.changed
            byte[] bytes = writer.toByteArray()
            FileOutputStream fos = new FileOutputStream(new File(filePath))
            fos.write(bytes)
        }


    }
    static void modifyClassWithPath(File dir) {
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

    //默认排除
    public static final DEFAULT_EXCLUDE = [
            '^android\\..*',
            '^androidx\\..*',
            '.*\\.R$',
            '.*\\.R\\$.*$',
            '.*\\.BuildConfig$',
    ]

    //获取类名
    static String getClassName(String root, String classPath) {
        return classPath.substring(root.length() + 1, classPath.length() - 6)
                .replaceAll("/", ".")       // unix/linux
                .replaceAll("\\\\", ".")    //windows
    }

    static boolean isSystemClass(String fileName) {
        for (def exclude : DEFAULT_EXCLUDE) {
            if (fileName.matches(exclude)) return true
        }
        return false
    }

    static void copyFile(JarInput jarInput, TransformOutputProvider outputProvider) {
        def dest = getDestFile(jarInput, outputProvider)
        FileUtils.copyFile(jarInput.file, dest)
    }

    static File getDestFile(JarInput jarInput, TransformOutputProvider outputProvider) {
        def destName = jarInput.name
        // 重名名输出文件,因为可能同名,会覆盖
        def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath)
        if (destName.endsWith(".jar")) {
            destName = destName.substring(0, destName.length() - 4)
        }
        // 获得输出文件
        File dest = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
        return dest
    }


}