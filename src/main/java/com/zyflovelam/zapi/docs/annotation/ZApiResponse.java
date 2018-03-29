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
public @interface ZApiResponse {
    /**
     * 返回码
     *
     * @return
     */
    int code() default 200;

    /**
     * 返回的文本消息
     *
     * @return
     */
    String message() default "";

    /**
     * 返回体
     *
     * @return
     */
    Class<?> responseBody() default Void.class;


}
