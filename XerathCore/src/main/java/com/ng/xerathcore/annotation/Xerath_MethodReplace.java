package com.ng.xerathcore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : jiangzhengnan
 * @creation : 2021/09/15
 * @description :
 * 方法替换
 * replaceMethods 待替换的方法
 * targetMethods  需要被替换成的目标方法
 * 例如:
 *
 *  @Xerath_MethodReplace(
 *             replaceMethods = {"org/json/JSONObject|put|(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;"},
 *             targetMethods = {"com/ng/xerathcore/utils/JsonPrinter|print|(Ljava/lang/String;Ljava/lang/Object;)V"}
 *  )
 *  public void someMethod() {
 *      ...
 *  }
 *
 *  按owner,name,desc的顺序,都以 | 分割，有多个的话以数组形式输入
 *  replaceMethods与targetMethods必须成对存在！
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Xerath_MethodReplace {

    String[] targetMethods() default {};

    String[] replaceMethods() default {};

}
