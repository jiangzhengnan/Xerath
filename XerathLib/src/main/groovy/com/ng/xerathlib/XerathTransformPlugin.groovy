package com.ng.xerathlib

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 用于注册自定义 Transform 的插件
 */
class XerathTransformPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "NgTransformPlugin apply"
        def android = project.extensions.getByType(AppExtension)
        //注册 hook click操作的task
        android.registerTransform(new XerathTransform(project))
    }
}