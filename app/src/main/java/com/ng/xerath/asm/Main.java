package com.ng.xerath.asm;


import com.ng.xerath.asm.visitor.TestClassVisitor;
import com.ng.xerath.asm.visitor.TestPreLoadClassVisitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;


/**
 * @author : jiangzhengnan
 * @creation : 2021/08/23
 * @description :
 * ASM的最简单用法
 * Child 有 phone 可以 call
 */
public class Main {

    public static void main(String[] args) {
        //testChild();
        startHook();
    }

    //Child 的 class文件路径
    public static final String LOCAL_PATH = "/Users/xiaoguagua/AndroidProjects/MyProjects/" +
            "ng_projects/Xerath/app/build/intermediates/javac/debug/classes/com/ng/xerath/asm";

    public static final String HOME_PATH = "/Users/pumpkin/AndroidPro/AndroidStudioPrivate/Xerath/app/build/" +
            "intermediates/javac/debug/classes/com/ng/xerath";

    private static void startHook() {
        try {

            ClassReader cr = new ClassReader(ASMShow.class.getName());
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

            //预加载参数
            TestPreLoadClassVisitor preCv = new TestPreLoadClassVisitor(cw);
            cr.accept(preCv, ClassReader.EXPAND_FRAMES);

            TestClassVisitor cv = new TestClassVisitor(cw);
            cr.accept(cv, ClassReader.EXPAND_FRAMES);


            // 获取生成的class文件对应的二进制流
            byte[] code = cw.toByteArray();
            //将二进制流写到out/下
            FileOutputStream fos = new FileOutputStream(LOCAL_PATH + "/ASMShow.class");
            fos.write(code);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
