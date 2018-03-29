package com.zyflovelam.zapi.docs.entity.vo;

import com.zyflovelam.zapi.docs.annotation.ZApiParamType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Project: BucksYard-DataServer
 * Package: com.bucksyard.api.docs.service.entity.vo
 * <p>
 * Created by zyflovelam on 2018/3/27.
 *
 * @author by zyflovelam
 */
public class ApiVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private String author;
    private String version;
    private List<ApiMethodVo> apiMethods;

    public String getName() {
        return name;
    }

    public ApiVo setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApiVo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ApiVo setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ApiVo setVersion(String version) {
        this.version = version;
        return this;
    }

    public List<ApiMethodVo> getApiMethods() {
        return apiMethods;
    }

    public ApiVo setApiMethods(List<ApiMethodVo> apiMethods) {
        this.apiMethods = apiMethods;
        return this;
    }

    public static class ApiMethodVo {
        private String name;
        private String description;
        private String[] url;
        private RequestMethod[] method;
        private List<ApiParamVo> apiParams;
        private ApiResponsesVo apiResponses;

        public ApiResponsesVo getApiResponses() {
            return apiResponses;
        }

        public ApiMethodVo setApiResponses(ApiResponsesVo apiResponses) {
            this.apiResponses = apiResponses;
            return this;
        }

        public String getName() {
            return name;
        }

        public ApiMethodVo setName(String name) {
            this.name = name;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public ApiMethodVo setDescription(String description) {
            this.description = description;
            return this;
        }

        public String[] getUrl() {
            return url;
        }

        public ApiMethodVo setUrl(String[] url) {
            this.url = url;
            return this;
        }

        public RequestMethod[] getMethod() {
            return method;
        }

        public ApiMethodVo setMethod(RequestMethod[] method) {
            this.method = method;
            return this;
        }

        public List<ApiParamVo> getApiParams() {
            return apiParams;
        }

        public ApiMethodVo setApiParams(List<ApiParamVo> apiParams) {
            this.apiParams = apiParams;
            return this;
        }


    }

    public static class ApiParamVo {
        private String name;
        private String dataType;
        private String description;
        private boolean required;
        private ZApiParamType paramType;

        public String getName() {
            return name;
        }

        public ApiParamVo setName(String name) {
            this.name = name;
            return this;
        }

        public String getDataType() {
            return dataType;
        }

        public ApiParamVo setDataType(String dataType) {
            this.dataType = dataType;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public ApiParamVo setDescription(String description) {
            this.description = description;
            return this;
        }

        public boolean isRequired() {
            return required;
        }

        public ApiParamVo setRequired(boolean required) {
            this.required = required;
            return this;
        }

        public ZApiParamType getParamType() {
            return paramType;
        }

        public ApiParamVo setParamType(ZApiParamType paramType) {
            this.paramType = paramType;
            return this;
        }
    }

    public static class ApiResponseVo {
        private int code;
        private String message;
        private Object responseBody;

        public int getCode() {
            return code;
        }

        public ApiResponseVo setCode(int code) {
            this.code = code;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public ApiResponseVo setMessage(String message) {
            this.message = message;
            return this;
        }

        public Object getResponseBody() {
            return responseBody;
        }

        public ApiResponseVo setResponseBody(Object responseBody) {
            this.responseBody = responseBody;
            return this;
        }
    }

    public static class ApiResponseHeaderVo {
        private String name;
        private String value;
        private String description;

        public String getName() {
            return name;
        }

        public ApiResponseHeaderVo setName(String name) {
            this.name = name;
            return this;
        }

        public String getValue() {
            return value;
        }

        public ApiResponseHeaderVo setValue(String value) {
            this.value = value;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public ApiResponseHeaderVo setDescription(String description) {
            this.description = description;
            return this;
        }
    }

    public static class ApiResponsesVo {
        private ApiResponseVo[] apiResponses;
        private ApiResponseHeaderVo[] apiResponseHeaders;

        public ApiResponseVo[] getApiResponses() {
            return apiResponses;
        }

        public ApiResponsesVo setApiResponses(ApiResponseVo[] apiResponses) {
            this.apiResponses = apiResponses;
            return this;
        }

        public ApiResponseHeaderVo[] getApiResponseHeaders() {
            return apiResponseHeaders;
        }

        public ApiResponsesVo setApiResponseHeaders(ApiResponseHeaderVo[] apiResponseHeaders) {
            this.apiResponseHeaders = apiResponseHeaders;
            return this;
        }
    }


}
