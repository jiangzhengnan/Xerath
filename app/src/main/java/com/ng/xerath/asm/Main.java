package com.ng.xerath.asm;


import com.ng.xerath.MainActivity;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;

import static org.objectweb.asm.Opcodes.ASM5;


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
    public static final String LOCAL_PATH = "/Users/xiaoguagua/AndroidProjects/MyProjects/ng_projects/Xerath/app/build/" +
            "intermediates/javac/debug/classes/com/ng/xerath";

    private static void startHook() {
        try {
            //1.首先创建ClassReader,读取目标类Child的内容
            ClassReader cr = new ClassReader(MainActivity.class.getName());
            //2.然后创建ClassWriter对象，
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new TestClassVisitor(ASM5, cw);
            cr.accept(cv, ASM5);
            // 获取生成的class文件对应的二进制流
            byte[] code = cw.toByteArray();
            //将二进制流写到out/下
            FileOutputStream fos = new FileOutputStream(LOCAL_PATH + "/MainActivity.class");
            fos.write(code);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
