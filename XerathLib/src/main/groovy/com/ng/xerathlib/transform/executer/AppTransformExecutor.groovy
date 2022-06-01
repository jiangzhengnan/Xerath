package com.ng.xerathlib.transform.executer

import com.android.utils.FileUtils
import com.ng.xerathlib.transform.util.TransformUtil
import com.ng.xerathlib.asm.core.CoreClassVisitor
import com.ng.xerathlib.asm.preload.PreLoadClassVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import com.ng.xerathlib.asm.HookClassExecutor
class AppTransformExecutor {

    //当前工程注入
    static void modifyClassWithPath(File dir, File dest) {
        def root = dir.absolutePath
        def filePath
        def className
        dir.eachFileRecurse { File file ->
            filePath = file.absolutePath
            if (!filePath.endsWith(".class")) return
            className = TransformUtil.getClassName(root, filePath)
            if (TransformUtil.isSystemClass(className)) return
            println " "
            //hook关键代码
            HookClassExecutor.hookAppClass(filePath)
            println "[ Xerath ] --- hook className: " + className
            FileUtils.copyDirectory(dir, dest)
        }
    }


//    //ASM注入
//    static void hookClass(String filePath, String className) {
//        println "[ Xerath ] --- start hook className: "+className
//        ClassReader reader = new ClassReader(new FileInputStream(new File(filePath)))
//        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
//
//        //核心处理ClassVisitor
//        CoreClassVisitor adapter = new CoreClassVisitor(writer)
//        reader.accept(adapter, ClassReader.EXPAND_FRAMES)
//
//        if (adapter.changed) {
//            println "[ Xerath ] --- finish hook className: "+className
//            byte[] bytes = writer.toByteArray()
//            FileOutputStream fos = new FileOutputStream(new File(filePath))
//            fos.write(bytes)
//        }
//        println ""
//    }
//
//    //预处理
//    static void hookClassPre(String filePath, String className) {
//        println "[ Xerath ] --- start hook className: "+className
//        ClassReader reader = new ClassReader(new FileInputStream(new File(filePath)))
//        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
//
//        //预处理ClassVisitor
//        PreLoadClassVisitor preAdapter = new PreLoadClassVisitor(writer)
//        reader.accept(preAdapter, ClassReader.EXPAND_FRAMES)
//
//        if (preAdapter.changed) {
//            println "[ Xerath ] --- finish hook className: "+className
//            byte[] bytes = writer.toByteArray()
//            FileOutputStream fos = new FileOutputStream(new File(filePath))
//            fos.write(bytes)
//        }
//        println ""
//    }

}