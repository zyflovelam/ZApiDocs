package com.zyflovelam.zapi.docs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Project: BucksYard-DataServer
 * Package: com.bucksyard.api.docs.service.annotation
 * <p>
 * Created by zyflovelam on 2018/3/28.
 *
 * @author by zyflovelam
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ZApiResponseHeader {

    /**
     * 返回头名称
     * @return
     */
    String name() default "";

    /**
     * 返回头的值
     * @return
     */
    String value() default "";

    /**
     * 描述
     * @return
     */
    String description() default "";

}
