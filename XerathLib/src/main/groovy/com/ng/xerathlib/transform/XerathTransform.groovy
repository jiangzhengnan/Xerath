package com.ng.xerathlib.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ng.xerathlib.constants.HookExtensions
import com.ng.xerathlib.scene.AnalyseHelper
import com.android.ide.common.internal.WaitableExecutor
import com.ng.xerathlib.scene.TransformExtension
import com.ng.xerathlib.transform.executer.AppTransformExecutor
import com.ng.xerathlib.transform.executer.JarTransformExecutor
import com.ng.xerathlib.transform.util.ClassLoaderHelper
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.workers.internal.Worker

import java.security.SecureClassLoader
import java.util.concurrent.Callable
/**
 * 框架Transform
 */
class XerathTransform extends Transform {
    final static String NAME = "XerathTransform"
    private WaitableExecutor waitableExecutor

    private Project project

    XerathTransform(Project project) {
        this.project = project
        this.waitableExecutor = WaitableExecutor.useGlobalSharedThreadPool()
        project.getExtensions().create(HookExtensions.XERATH_HOOK_TRAFFIC_INFO, TransformExtension.class)
        println("======NoahTransform 插件初始化======")
    }

    @Override
    String getName() {
        return NAME
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        long startTime = System.currentTimeMillis()
        URLClassLoader urlClassLoader = ClassLoaderHelper.getClassLoader(transformInvocation.inputs, transformInvocation.referencedInputs, project)
        transformInvocation.getOutputProvider().deleteAll()
        //遍历输入
        for (TransformInput input in transformInvocation.inputs) {
            //当前app遍历
            for (DirectoryInput dirInput in input.directoryInputs) {
                File outputDir = transformInvocation.outputProvider.getContentLocation(dirInput.file.absolutePath, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(dirInput.file, outputDir)
                println("======transform App ======" + outputDir.name)
                transformApp(dirInput.file, outputDir)
            }
            //jar遍历
            for (JarInput jarInput : input.jarInputs) {//jar（第三方库，module）
                File outputJar = transformInvocation.outputProvider.getContentLocation(jarInput.file.absolutePath, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, outputJar)
                println("======transform Jar ======" + outputJar.name)
                Status status = jarInput.getStatus()
                transformJar(jarInput.file, outputJar, status, urlClassLoader)
            }
        }
        waitableExecutor.waitForTasksWithQuickFail(true)
        System.out.println(NAME + " 耗时 " + (System.currentTimeMillis() - startTime) + " ms")
    }

    private void transformApp(File dirInput, File dest) {
        println("======transformApp ======" + dirInput.name)
        this.waitableExecutor.execute {
            AppTransformExecutor.modifyClassWithPath(dirInput, dest)
        }
    }

    private void transformJar(File srcJar, File destJar, Status status, SecureClassLoader classLoader) {
        this.waitableExecutor.execute {
            JarTransformExecutor.weave(srcJar, destJar, classLoader)
        }
    }

}