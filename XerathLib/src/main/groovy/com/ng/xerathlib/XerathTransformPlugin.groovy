package com.ng.xerathlib

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.ng.xerathlib.transform.XerathTransform
import org.gradle.api.Plugin
import org.gradle.api.Project
/**
 * 用于注册自定义 Transform 的插件
 */
class XerathTransformPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("======XerathTransformPlugin apply 插件开始注册   ======")
        registerForApp(project)
    }

    /**
     * 注册 for android 工程
     */
    static void registerForApp(Project project) {
        AppExtension appExtension = project.extensions.getByType(AppExtension.class)
        appExtension.registerTransform(new XerathTransform(project))
    }

    /**
     * 注册 for android library
     */
    static void registerForLib(Project project) {
        LibraryExtension appExtension = project.extensions.getByType(LibraryExtension.class)
        appExtension.registerTransform(new XerathTransform(project))
    }
}