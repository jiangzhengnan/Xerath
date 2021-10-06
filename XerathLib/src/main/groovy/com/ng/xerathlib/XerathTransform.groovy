package com.ng.xerathlib

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
/**
 * 自定义的 Transform 类
 */
class XerathTransform extends Transform {

    @Override
    String getName() {
        //Transform名称
        return "XerathTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }
    private static final Set<QualifiedContent.Scope> SCOPES = new HashSet<>();
    static {
        SCOPES.add(QualifiedContent.Scope.PROJECT)
        SCOPES.add(QualifiedContent.Scope.SUB_PROJECTS)
        SCOPES.add(QualifiedContent.Scope.EXTERNAL_LIBRARIES)
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return SCOPES
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        //遍历输入
        for (TransformInput input in inputs) {
            //遍历directoryInputs
            for (DirectoryInput dirInput in input.directoryInputs) {
                //处理需要插桩的文件
                TransformUtil.modifyClassWithPath(dirInput.file)
                //Copy修改之后的文件
                File dest = outputProvider.getContentLocation(dirInput.name, dirInput.contentTypes,
                        dirInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(dirInput.file, dest)
            }
            //遍历JarInput 因为我们这里只对自己的方法插桩 所以不对JarInput做处理
            for (JarInput jarInput : input.jarInputs) {//jar（第三方库，module）
                if (jarInput.scopes.contains(QualifiedContent.Scope.SUB_PROJECTS)) {//module library
                    //从module中获取注解信息
                    // readClassWithJar(jarInput)
                }
                //虽然不做处理 但是还是要记得重新拷贝回去 不然会有问题
                TransformUtil.copyFile(jarInput, outputProvider)
            }
        }
    }


}