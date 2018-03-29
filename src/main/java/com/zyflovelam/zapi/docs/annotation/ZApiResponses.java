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
@Target(ElementType.METHOD)
public @interface ZApiResponses {
    /**
     * 返回值
     *
     * @return
     */
    ZApiResponse[] zApiResponses();

    /**
     * 返回头
     *
     * @return
     */
    ZApiResponseHeader[] zApiResponseHeaders() default {};
}
