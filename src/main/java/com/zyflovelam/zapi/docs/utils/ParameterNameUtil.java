package com.zyflovelam.zapi.docs.utils;


import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.LocalVariableNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Project: BucksYard-DataServer
 * Package: com.bucksyard.api.docs.service.utils
 * <p>
 * Created by zyflovelam on 2018/3/27.
 *
 * @author by zyflovelam
 */
public class ParameterNameUtil {

    /**
     * 获取参数名称
     *
     * @param clazz
     * @param method
     * @param num
     * @return
     */
    public static String getParameterName(Class<?> clazz, Method method, int num) {
        List<String> methodParamNames = null;
        try {
            methodParamNames = getMethodParamNames(clazz, method);
        } catch (IOException e) {
            return "";
        }
        return methodParamNames.get(num);
    }

    /**
     * 获取方法参数名列表
     *
     * @param clazz
     * @param m
     * @return
     * @throws IOException
     */
    public static List<String> getMethodParamNames(Class<?> clazz, Method m) throws IOException {
        try (InputStream in = clazz.getResourceAsStream("/" + clazz.getName().replace('.', '/') + ".class")) {
            return getMethodParamNames(in, m);
        }
    }

    public static List<String> getMethodParamNames(InputStream in, Method m) throws IOException {
        try (InputStream ins = in) {
            return getParamNames(ins,
                    new EnclosingMetadata(m.getName(), Type.getMethodDescriptor(m), m.getParameterTypes().length));
        }

    }

    /**
     * 获取构造器参数名列表
     *
     * @param clazz
     * @param constructor
     * @return
     */
    public static List<String> getConstructorParamNames(Class<?> clazz, Constructor<?> constructor) {
        try (InputStream in = clazz.getResourceAsStream("/" + clazz.getName().replace('.', '/') + ".class")) {
            return getConstructorParamNames(in, constructor);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<String> getConstructorParamNames(InputStream ins, Constructor<?> constructor) {
        try (InputStream in = ins) {
            return getParamNames(in, new EnclosingMetadata(constructor.getName(), Type.getConstructorDescriptor(constructor),
                    constructor.getParameterTypes().length));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 获取参数名列表辅助方法
     *
     * @param in
     * @param m
     * @return
     * @throws IOException
     */
    private static List<String> getParamNames(InputStream in, EnclosingMetadata m) throws IOException {
        ClassReader cr = new ClassReader(in);
        ClassNode cn = new ClassNode();
        cr.accept(cn, ClassReader.EXPAND_FRAMES);
        List<MethodNode> methods = cn.methods;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < methods.size(); ++i) {
            List<LocalVariable> varNames = new ArrayList<>();
            MethodNode method = methods.get(i);
            if (method.desc.equals(m.desc) && method.name.equals(m.name)) {
                List<LocalVariableNode> local_variables = method.localVariables;
                for (int l = 0; l < local_variables.size(); l++) {
                    String varName = local_variables.get(l).name;
                    int index = local_variables.get(l).index;
                    if (!"this".equals(varName)) {
                        varNames.add(new LocalVariable(index, varName));
                    }
                }
                LocalVariable[] tmpArr = varNames.toArray(new LocalVariable[varNames.size()]);
                Arrays.sort(tmpArr);
                for (int j = 0; j < m.size; j++) {
                    list.add(tmpArr[j].name);
                }
                break;
            }
        }
        return list;
    }

    /**
     * 方法本地变量索引和参数名封装
     *
     * @author xby Administrator
     */
    static class LocalVariable implements Comparable<LocalVariable> {
        public int index;
        public String name;

        public LocalVariable(int index, String name) {
            this.index = index;
            this.name = name;
        }

        @Override
        public int compareTo(LocalVariable o) {
            return this.index - o.index;
        }
    }

    /**
     * 封装方法描述和参数个数
     *
     * @author xby Administrator
     */
    static class EnclosingMetadata {
        public String name;
        public String desc;
        public int size;

        public EnclosingMetadata(String name, String desc, int size) {
            this.name = name;
            this.desc = desc;
            this.size = size;
        }
    }

}
