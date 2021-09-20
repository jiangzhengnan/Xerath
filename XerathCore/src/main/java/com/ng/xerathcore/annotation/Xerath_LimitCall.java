package com.ng.xerathcore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : jiangzhengnan
 * @creation : 2021/09/15
 * @description :重复点击
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Xerath_LimitCall {

    long time() default 0L;

}
