package com.zyflovelam.zapi.docs.entity.vo;

import java.util.List;

/**
 * Project: BucksYard-DataServer
 * Package: com.bucksyard.api.docs.service.entity.vo
 * <p>
 * Created by zyflovelam on 2018/3/28.
 *
 * @author by zyflovelam
 */
public class ApiEntityVo {
    private String name;
    private String description;
    private String author;
    private String version;
    private List<ApiEntityFieldVo> apiEntityFields;

    public String getName() {
        return name;
    }

    public ApiEntityVo setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApiEntityVo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ApiEntityVo setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ApiEntityVo setVersion(String version) {
        this.version = version;
        return this;
    }

    public List<ApiEntityFieldVo> getApiEntityFields() {
        return apiEntityFields;
    }

    public ApiEntityVo setApiEntityFields(List<ApiEntityFieldVo> apiEntityFields) {
        this.apiEntityFields = apiEntityFields;
        return this;
    }

    public static class ApiEntityFieldVo{
        private String name;
        private String fieldType;
        private String description;
        private boolean defined;

        public boolean isDefined() {
            return defined;
        }

        public ApiEntityFieldVo setDefined(boolean defined) {
            this.defined = defined;
            return this;
        }

        public String getName() {
            return name;
        }

        public ApiEntityFieldVo setName(String name) {
            this.name = name;
            return this;
        }

        public String getFieldType() {
            return fieldType;
        }

        public ApiEntityFieldVo setFieldType(String fieldType) {
            this.fieldType = fieldType;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public ApiEntityFieldVo setDescription(String description) {
            this.description = description;
            return this;
        }
    }
}
