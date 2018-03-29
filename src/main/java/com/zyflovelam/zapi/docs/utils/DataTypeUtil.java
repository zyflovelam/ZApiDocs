package com.zyflovelam.zapi.docs.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Project: BucksYard-DataServer
 * Package: com.bucksyard.api.docs.service.utils
 * <p>
 * Created by zyflovelam on 2018/3/27.
 *
 * @author by zyflovelam
 */
public class DataTypeUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    private static String clazzPrefixJava = "java";
    private static String[] excludeName = {"serialVersionUID"};


    public static String getDataTypeJSON(Class<?> clazz) {
        JSONObject jsonObject = new JSONObject();
        String type = clazz.getName();
        jsonObject.put("type", type);
        if (type.indexOf(".") > 0) {
            if (clazzPrefixJava.equals(type.substring(0, type.indexOf(".")))) {
                jsonObject.put("defined", false);
                return jsonObject.toJSONString();
            }
        }
//        Field[] declaredFields = clazz.getDeclaredFields();
//        JSONArray fields = new JSONArray();
//        for (Field declaredField : declaredFields) {
//            fields.add(getType(declaredField));
//        }
//        jsonObject.put("fields", fields);
        jsonObject.put("defined", true);
        return jsonObject.toJSONString();
    }

    private static JSONObject getType(Field field){
        JSONObject item = new JSONObject();
        String name = field.getName();
        boolean f = false;
        for (String n : excludeName) {
            if (n.equals(name)) {
                f = true;
                break;
            }
        }
        if (f) {
            return null;
        }
        String typeName = field.getType().getName();
        item.put(name, typeName);
        return item;
    }
}
