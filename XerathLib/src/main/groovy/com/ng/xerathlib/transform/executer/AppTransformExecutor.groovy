package com.ng.xerathlib.transform.executer

import com.android.utils.FileUtils
import com.ng.xerathlib.transform.util.TransformUtil
import com.ng.xerathlib.asm.load.XerathClassVisitor
import com.ng.xerathlib.asm.preload.XerathPreLoadClassVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class AppTransformExecutor {

    //当前工程注入
    static void modifyClassWithPath(File dir, File dest) {
        def root = dir.absolutePath
        dir.eachFileRecurse { File file ->
            def filePath = file.absolutePath
            //过滤非class文件
            if (!filePath.endsWith(".class")) return
            def className = TransformUtil.getClassName(root, filePath)

            //过滤系统文件
            if (TransformUtil.isSystemClass(className)) return

            //hook关键代码
            hookClassPre(filePath, className)
            hookClass(filePath, className)

            FileUtils.copyDirectory(dirInput.file, dest)
        }
    }


    //ASM注入
    static void hookClass(String filePath, String className) {
        println "[ Xerath ] --- start hook className: "+className
        ClassReader reader = new ClassReader(new FileInputStream(new File(filePath)))
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)

        //核心处理ClassVisitor
        XerathClassVisitor adapter = new XerathClassVisitor(writer)
        reader.accept(adapter, ClassReader.EXPAND_FRAMES)

        if (adapter.changed) {
            println "[ Xerath ] --- finish hook className: "+className
            byte[] bytes = writer.toByteArray()
            FileOutputStream fos = new FileOutputStream(new File(filePath))
            fos.write(bytes)
        }
        println ""
    }

    //预处理
    static void hookClassPre(String filePath, String className) {
        println "[ Xerath ] --- start hook className: "+className
        ClassReader reader = new ClassReader(new FileInputStream(new File(filePath)))
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)

        //预处理ClassVisitor
        XerathPreLoadClassVisitor preAdapter = new XerathPreLoadClassVisitor(writer)
        reader.accept(preAdapter, ClassReader.EXPAND_FRAMES)

        if (preAdapter.changed) {
            println "[ Xerath ] --- finish hook className: "+className
            byte[] bytes = writer.toByteArray()
            FileOutputStream fos = new FileOutputStream(new File(filePath))
            fos.write(bytes)
        }
        println ""
    }

}