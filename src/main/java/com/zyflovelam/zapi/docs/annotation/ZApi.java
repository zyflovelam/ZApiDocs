package com.zyflovelam.zapi.docs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Project: BucksYard-DataServer
 * Package: com.bucksyard.api.docs.service.annotation
 * <p>
 * Created by zyflovelam on 2018/3/27.
 *
 * @author by zyflovelam
 * 标志此类为一个接口类，作用与类上
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ZApi {

    /**
     * 此类的简单描述
     *
     * @return
     */
    String name() default "";

    /**
     * 此类的详细信息
     *
     * @return
     */
    String description() default "";

    /**
     * 类作者
     *
     * @return
     */
    String author() default "";

    /**
     * 版本
     *
     * @return
     */
    String version() default "1.0";
}
