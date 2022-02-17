package com.ng.xerathcore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : jiangzhengnan
 * @creation : 2021/09/15
 * @description :
 * 方法移除
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
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Xerath_MethodRemove {

    String[] removeMethods() default {};

}
