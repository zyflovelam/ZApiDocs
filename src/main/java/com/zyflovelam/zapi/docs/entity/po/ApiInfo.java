package com.zyflovelam.zapi.docs.entity.po;

import java.io.Serializable;

/**
 * Project: BucksYard-DataServer
 * Package: com.bucksyard.api.docs.service.entity.po
 * <p>
 * Created by zyflovelam on 2018/3/27.
 *
 * @author by zyflovelam
 */
public class ApiInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String description;
    private String baseUrl;
    private String version;
    private String author;

    public String getTitle() {
        return title;
    }

    public ApiInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApiInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public ApiInfo setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ApiInfo setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ApiInfo setAuthor(String author) {
        this.author = author;
        return this;
    }
}
