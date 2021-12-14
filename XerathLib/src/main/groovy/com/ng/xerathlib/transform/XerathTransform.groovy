package com.ng.xerathlib.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ng.xerathlib.constants.HookExtensions
import com.ng.xerathlib.scene.AnalyseHelper
import com.ng.xerathlib.scene.TransformExtension
import com.ng.xerathlib.transform.executer.AppTransformExecutor
import com.ng.xerathlib.transform.executer.JarTransformExecutor
import org.gradle.api.Project
import org.gradle.workers.internal.Worker

import java.security.SecureClassLoader
import java.util.concurrent.Callable
/**
 * 框架Transform
 */
class XerathTransform extends Transform {

    private static final Set<QualifiedContent.Scope> SCOPES = new HashSet<>()
    private final Worker worker

    static {
        SCOPES.add(QualifiedContent.Scope.PROJECT)
        SCOPES.add(QualifiedContent.Scope.SUB_PROJECTS)
        SCOPES.add(QualifiedContent.Scope.EXTERNAL_LIBRARIES)
    }

    XerathTransform(Project project) {
        this.project = project
        this.worker = Schedulers.IO()
        project.getExtensions().create(HookExtensions.NOAH_HOOK_TRAFFIC_INFO, TransformExtension.class)
        println("======NoahTransform 插件初始化======")
    }

    @Override
    String getName() {
        return "XerathTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return SCOPES
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        //获取入参
        AnalyseHelper.getInstance().setTransformExtension(project.getExtensions().getByName(HookExtensions.XERATH_HOOK_TRAFFIC_INFO))
        URLClassLoader urlClassLoader = ClassLoaderHelper.getClassLoader(inputs, referencedInputs, project)
        //输出
        File dest = outputProvider.getContentLocation(
                jarInput.getFile().getAbsolutePath(),
                jarInput.getContentTypes(),
                jarInput.getScopes(),
                Format.JAR);
        //遍历输入
        for (TransformInput input in inputs) {
            //当前app遍历
            for (DirectoryInput dirInput in input.directoryInputs) {
                transformApp(dirInput, dest)
            }
            //jar遍历
            for (JarInput jarInput : input.jarInputs) {//jar（第三方库，module）
                Status status = jarInput.getStatus()
                transformJar(jarInput.getFile(), dest, status, urlClassLoader);
            }
        }
    }

    private void transformApp(File dirInput, File dest) {
        println("======transformApp ======" + dirInput.name)
        worker.submit(new Callable<String>() {
            @Override
            String call() throws Exception {
                AppTransformExecutor.modifyClassWithPath(dirInput.file, dest)
                return null
            }
        })
    }

    private void transformJar(File srcJar, File destJar, Status status, SecureClassLoader classLoader) {
        println("======transformJar ======" + srcJar.name)
        worker.submit(new Callable<String>() {
            @Override
            String call() throws Exception {
                JarTransformExecutor.weave(srcJar, destJar, classLoader);
                return null
            }
        })
    }

}