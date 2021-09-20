package com.ng.xerathlib


import org.gradle.api.Plugin
import org.gradle.api.Project
/**
 * 用于注册自定义 Transform 的插件
 */
class XerathTransformPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "XerathTransformPlugin apply 插件开始注册"
        //注册task
        project.android.registerTransform(new XerathTransform())
    }
}