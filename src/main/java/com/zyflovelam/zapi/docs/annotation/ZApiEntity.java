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
@Target(ElementType.TYPE)
public @interface ZApiEntity {

    /**
     * 描述信息
     *
     * @return
     */
    String value() default "";

    /**
     * 作者
     *
     * @return
     */
    String author() default "";

    /**
     * 版本信息
     *
     * @return
     */
    String version() default "1.0";


}
