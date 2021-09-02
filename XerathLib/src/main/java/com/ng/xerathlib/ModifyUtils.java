package com.ng.xerathlib;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class ModifyUtils {

    /**
     * 修改某个 Class
     * @param srcByteCode
     * @return
     */
    static byte[] modifyClasses(byte[] srcByteCode) {
        byte[] classBytesCode = null;
        try {
            classBytesCode = modifyClass(srcByteCode);
            if (classBytesCode == null) {
                classBytesCode = srcByteCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classBytesCode;
    }

    private static byte[] modifyClass(byte[] srcByteCode) {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new CustomClassVisitor(classWriter);
        ClassReader classReader = new ClassReader(srcByteCode);
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }



}
