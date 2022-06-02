package com.ng.xerathlib.asm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.security.SecureClassLoader;

import com.ng.xerathlib.asm.base.BaseClassVisitor;
import com.ng.xerathlib.hook.XerathHookHelper;
import com.ng.xerathlib.utils.ExtendClassWriter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * @author : jiangzhengnan.jzn@alibaba-inc.com
 * @creation : 2022/03/18
 * @description :
 */
public class HookClassExecutor {

    //jar注入
    public static byte[] hookJarClass(InputStream inputStream, SecureClassLoader classLoader) throws IOException {
        byte[] nowContent = null;
        if (inputStream != null) {
            nowContent = hookJarSingleCv(inputStream, classLoader, "com.ng.xerathlib.asm.preload.PreLoadClassVisitor");
        }
        if (nowContent != null) {
            nowContent = hookJarSingleCv(new ByteArrayInputStream(nowContent), classLoader, "com.ng.xerathlib.asm.core.CoreClassVisitor");
        }
        if (nowContent != null) {
            nowContent = hookJarSingleCv(new ByteArrayInputStream(nowContent), classLoader, "com.ng.xerathlib.asm.tree.TreeClassVisitor");
        }
        //byte[] nowContent = waveSingleCv(inputStream, classLoader, new PreLoadClassVisitor());
        //nowContent = waveSingleCv(new ByteArrayInputStream(nowContent), classLoader, CoreClassVisitor);
        //nowContent = waveSingleCv(new ByteArrayInputStream(nowContent), classLoader, TreeClassVisitor);
        return nowContent;
    }

    public static byte[] waveSingleCv(InputStream inputStream, SecureClassLoader classLoader, ClassVisitor classVisitor) throws IOException {
        ClassReader classReader = new ClassReader(inputStream);
        ClassWriter classWriter = new ExtendClassWriter(classLoader, ClassWriter.COMPUTE_FRAMES);
        classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);
        return classWriter.toByteArray();
    }

    public static byte[] hookJarSingleCv(InputStream inputStream, SecureClassLoader classLoader, String cvClassName) {
        ClassReader reader;
        BaseClassVisitor nowCv;
        ClassWriter writer = new ExtendClassWriter(classLoader, ClassWriter.COMPUTE_FRAMES);
        try {
            reader = new ClassReader(inputStream);
            Class<?> cla = Class.forName(cvClassName);
            Constructor<?> con = cla.getConstructor(ClassVisitor.class);
            Object obj = con.newInstance(writer);
            if (obj instanceof BaseClassVisitor) {
                nowCv = (BaseClassVisitor) obj;
                reader.accept(nowCv, ClassReader.EXPAND_FRAMES);
            }
            return writer.toByteArray();
        } catch (Exception e) {
            System.out.println("报错:" + e.getMessage());
            e.printStackTrace();
        }
        return writer.toByteArray();
    }

    //app注入
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
            //boolean changed = XerathHookHelper.getInstance().isClassChanged() || nowCv.changed;
            if (nowCv.changed()) {
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
