package com.zyflovelam.zapi.docs.annotation;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * Project: BucksYard-DataServer
 * Package: com.bucksyard.api.docs.service.annotation
 * <p>
 * Created by zyflovelam on 2018/3/27.
 *
 * @author by zyflovelam
 * 标识此方法为一个基于Http协议的API请求，作用在请求方法上
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ZApiMethod {
    /**
     * 接口的说明
     *
     * @return
     */
    String value() default "";

    /**
     * 接口详细功能描述
     *
     * @return
     */
    String description() default "";

    /**
     * 接口地址
     * @return
     */
    String[] url() default "";

    /**
     * 接口请求方式
     * @return
     */
    RequestMethod[] method() default RequestMethod.GET;
}
