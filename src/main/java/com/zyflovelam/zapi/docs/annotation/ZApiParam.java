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
 * 标识此参数为请求参数
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ZApiParam {

    /**
     * 参数名称
     *
     * @return
     */
    String value() default "";

    /**
     * 参数类型
     *
     * @return
     */
    Class<?> dataType();

    /**
     * 参数描述
     *
     * @return
     */
    String description() default "";

    /**
     * 是否是必须的参数
     *
     * @return
     */
    boolean required() default true;

    /**
     * 参数来源类型
     * header
     * query
     * path
     * body
     * form
     *
     * @return
     */
    ZApiParamType paramType();
}
