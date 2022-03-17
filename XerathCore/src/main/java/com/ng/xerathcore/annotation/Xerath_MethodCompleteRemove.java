package com.ng.xerathcore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : jiangzhengnan
 * @creation : 2022/03/03
 * @description :
 * 方法完全移除
 * (参数定义+方法调用)
 * removeMethods 待移除的方法
 * 例如:
 *
 *  @Xerath_MethodRemove(
 *             removeMethods = {"org/json/JSONObject|put|(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;"}
 *  )
 *  public void someMethod() {
 *      ...
 *  }
 *
 *  按owner,name,desc的顺序,都以 | 分割，有多个的话以数组形式输入
 *
 *  编译期间优化掉项目所有调用了配置中配置的方法调用(包含参数形成指令)
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Xerath_MethodCompleteRemove {

    String[] removeMethods() default {};

}
