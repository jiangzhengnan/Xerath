package com.ng.xerathlib.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;

import com.ng.xerathlib.asm.base.BaseClassVisitor;
import com.ng.xerathlib.hook.XerathHookHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/03/18
 * @description :
 */
public class HookClassExecutor {

    public static void hookAppClass(String filePath) {
        hookSingleCv(filePath, "com.ng.xerathlib.asm.preload.PreLoadClassVisitor");
        hookSingleCv(filePath, "com.ng.xerathlib.asm.core.CoreClassVisitor");
        hookSingleCv(filePath, "com.ng.xerathlib.asm.tree.TreeClassVisitor");
    }

    public static void hookSingleCv(String filePath, String cvClassName) {
        ClassReader reader;
        BaseClassVisitor nowCv = null;
        try {
            reader = new ClassReader(new FileInputStream(new File(filePath)));
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);

            Class<?> cla = Class.forName(cvClassName);
            Constructor<?> con = cla.getConstructor(ClassVisitor.class);
            Object obj = con.newInstance(writer);
            if (obj instanceof BaseClassVisitor) {
                nowCv = (BaseClassVisitor) obj;
                reader.accept(nowCv, ClassReader.EXPAND_FRAMES);
            }
            if (nowCv == null) {
                return;
            }
            boolean changed = XerathHookHelper.getInstance().isClassChanged() || nowCv.changed;
            System.out.println("类:" + cvClassName + " 是否修改了?: " + XerathHookHelper.getInstance()
                                                                                  .isClassChanged() + " " + nowCv.changed);
            if (changed) {
                byte[] bytes = writer.toByteArray();
                FileOutputStream fos = new FileOutputStream(new File(filePath));
                fos.write(bytes);
            }
        } catch (Exception e) {
            System.out.println("报错:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
