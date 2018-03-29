package com.zyflovelam.zapi.docs.config;

import com.zyflovelam.zapi.docs.entity.po.ApiInfo;
import com.zyflovelam.zapi.docs.entity.vo.ApiEntityVo;
import com.zyflovelam.zapi.docs.entity.vo.ApiVo;
import com.zyflovelam.zapi.docs.utils.DataTypeUtil;
import com.zyflovelam.zapi.docs.utils.PackageUtil;
import com.zyflovelam.zapi.docs.utils.ParameterNameUtil;
import com.zyflovelam.zapi.docs.annotation.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Project: BucksYard-DataServer
 * Package: com.bucksyard.api.docs.service.config
 * <p>
 * Created by zyflovelam on 2018/3/27.
 *
 * @author by zyflovelam
 */
public class ZApiConfig {

    private Logger logger = Logger.getLogger(ZApiConfig.class);

    private String basePack;
    private ApiInfo apiInfo;
    private List<ApiVo> apiList;
    private List<ApiEntityVo> apiEntityList;
    private static String[] excludeClass = {
            "java.lang.String",
            "java.lang.Boolean",
            "java.lang.Double",
            "java.lang.Short",
            "java.lang.Integer",
            "java.lang.Long",
            "java.lang.Float",
            "java.lang.Character",
            "java.lang.Byte",
            "boolean",
            "int",
            "float",
            "double",
            "char",
            "byte",
            "short",
            "long"
    };

    public ZApiConfig(String basePack, ApiInfo apiInfo) {
        logger.info("开始建立API");
        this.apiInfo = apiInfo;
        this.basePack = basePack;
        Set<Class<?>> classes = PackageUtil.getClazzFromPackage(basePack);
        List<ApiVo> apiList = new ArrayList<>();
        List<ApiEntityVo> apiEntityList = new ArrayList<>();
        for (Class<?> clazz : classes) {
            /**
             * 检测是否属于ZApi
             * 1.获取是否是Controller或RestController
             * 2.获取Controller上有没有ZApi注解
             * 3.获取每个方法，检测是否有ZApiMethod
             * 4.获取方法的参数，检测是否有ZApiParam
             */
            boolean isController = clazz.isAnnotationPresent(RestController.class) || clazz.isAnnotationPresent(Controller.class);
            if (isController) {
                boolean hasZApi = clazz.isAnnotationPresent(ZApi.class);
                //是ZApi
                if (hasZApi) {
                    ApiVo apiVo = new ApiVo();
                    //判断是否有基地址
                    String[] baseUrls;
                    boolean hasControllerRequestMapping = clazz.isAnnotationPresent(RequestMapping.class);
                    if (hasControllerRequestMapping) {
                        //有基地址
                        RequestMapping controllerRequestMapping = clazz.getAnnotation(RequestMapping.class);
                        baseUrls = controllerRequestMapping.value();
                    }
                    ZApi zApi = clazz.getAnnotation(ZApi.class);
                    String name = zApi.name();
                    if ("".equals(name)) {
                        name = clazz.getName();
                    }
                    apiVo.setName(name);
                    apiVo.setDescription(zApi.description());
                    apiVo.setAuthor(zApi.author());
                    apiVo.setVersion(zApi.version());
                    //获取方法
                    Method[] methods = clazz.getDeclaredMethods();
                    List<ApiVo.ApiMethodVo> apiMethods = new ArrayList<>();
                    for (int i = 0; i < methods.length; i++) {
                        Method method = methods[i];
                        boolean hasZApiMethod = method.isAnnotationPresent(ZApiMethod.class);
                        if (hasZApiMethod) {
                            ApiVo.ApiMethodVo apiMethodVo = new ApiVo.ApiMethodVo();
                            ZApiMethod zApiMethod = method.getAnnotation(ZApiMethod.class);
                            String zApiMethodName = zApiMethod.name();
                            if ("".equals(zApiMethodName)) {
                                zApiMethodName = method.getName();
                            }
                            apiMethodVo.setName(zApiMethodName);
                            apiMethodVo.setDescription(zApiMethod.description());
                            String[] zApiMethodUrl = zApiMethod.url();
                            RequestMethod[] zApiRequestMethods = zApiMethod.method();
                            if (zApiMethodUrl.length == 0 || "".equals(zApiMethodUrl[0])) {
                                //没有主动填写url，将spring上的url获取出来
                                boolean hasMethodRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                                if (hasMethodRequestMapping) {
                                    //用的是RequestMapping
                                    RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                                    zApiMethodUrl = methodRequestMapping.value();
                                    RequestMethod[] methodRequestMappingMethods = methodRequestMapping.method();
                                    if (methodRequestMappingMethods.length == 0) {
                                        zApiRequestMethods = new RequestMethod[]{RequestMethod.GET};
                                    } else {
                                        zApiRequestMethods = methodRequestMappingMethods;
                                    }
                                } else {
                                    if (method.isAnnotationPresent(GetMapping.class)) {
                                        GetMapping methodGetMapping = method.getAnnotation(GetMapping.class);
                                        zApiMethodUrl = methodGetMapping.value();
                                        zApiRequestMethods = new RequestMethod[]{RequestMethod.GET};
                                    } else if (method.isAnnotationPresent(PostMapping.class)) {
                                        PostMapping methodPostMapping = method.getAnnotation(PostMapping.class);
                                        zApiMethodUrl = methodPostMapping.value();
                                        zApiRequestMethods = new RequestMethod[]{RequestMethod.POST};
                                    } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                                        DeleteMapping methodDeleteMapping = method.getAnnotation(DeleteMapping.class);
                                        zApiMethodUrl = methodDeleteMapping.value();
                                        zApiRequestMethods = new RequestMethod[]{RequestMethod.DELETE};
                                    } else if (method.isAnnotationPresent(PatchMapping.class)) {
                                        PatchMapping methodPatchMapping = method.getAnnotation(PatchMapping.class);
                                        zApiMethodUrl = methodPatchMapping.value();
                                        zApiRequestMethods = new RequestMethod[]{RequestMethod.PATCH};
                                    } else if (method.isAnnotationPresent(PutMapping.class)) {
                                        PutMapping methodPutMapping = method.getAnnotation(PutMapping.class);
                                        zApiMethodUrl = methodPutMapping.value();
                                        zApiRequestMethods = new RequestMethod[]{RequestMethod.PUT};
                                    }
                                }
                            }
                            apiMethodVo.setUrl(zApiMethodUrl);
                            apiMethodVo.setMethod(zApiRequestMethods);
                            //获取方法的参数
                            Parameter[] parameters = method.getParameters();
                            List<ApiVo.ApiParamVo> apiParams = new ArrayList<>();
                            for (int j = 0; j < parameters.length; j++) {
                                Parameter parameter = parameters[j];
                                boolean hasZApiParam = parameter.isAnnotationPresent(ZApiParam.class);
                                if (hasZApiParam) {
                                    //需要描述的参数
                                    ZApiParam zApiParam = parameter.getAnnotation(ZApiParam.class);
                                    ApiVo.ApiParamVo apiParamVo = new ApiVo.ApiParamVo();
                                    String zApiParamName = zApiParam.name();
                                    if ("".equals(zApiParamName)) {
                                        zApiParamName = ParameterNameUtil.getParameterName(clazz, method, j);
                                    }
                                    apiParamVo.setName(zApiParamName);
                                    apiParamVo.setParamType(zApiParam.paramType());
                                    apiParamVo.setDataType(DataTypeUtil.getDataTypeJSON(zApiParam.dataType()));
                                    apiParamVo.setRequired(zApiParam.required());
                                    apiParamVo.setDescription(zApiParam.description());
                                    apiParams.add(apiParamVo);
                                }
                            }
                            apiMethodVo.setApiParams(apiParams);
                            //获取方法的返回值
                            boolean hasZApiResponses = method.isAnnotationPresent(ZApiResponses.class);
                            ApiVo.ApiResponsesVo apiResponsesVo = new ApiVo.ApiResponsesVo();
                            if (hasZApiResponses) {
                                //有一堆返回值
                                ZApiResponses zApiResponses = method.getAnnotation(ZApiResponses.class);

                                //返回值
                                ZApiResponse[] zApiResponsesArray = zApiResponses.zApiResponses();
                                ApiVo.ApiResponseVo[] apiResponsesList = new ApiVo.ApiResponseVo[zApiResponsesArray.length];
                                for (int k = 0; k < zApiResponsesArray.length; k++) {
                                    ZApiResponse zApiResponse = zApiResponsesArray[k];
                                    ApiVo.ApiResponseVo apiResponseVo = new ApiVo.ApiResponseVo();
                                    apiResponseVo.setCode(zApiResponse.code());
                                    apiResponseVo.setMessage(zApiResponse.message());
                                    Class<?> responseBody = zApiResponse.responseBody();
                                    apiResponseVo.setResponseBody(responseBody);
                                    apiResponsesList[k] = apiResponseVo;
                                }
                                apiResponsesVo.setApiResponses(apiResponsesList);

                                //返回头
                                ZApiResponseHeader[] zApiResponseHeaders = zApiResponses.zApiResponseHeaders();
                                ApiVo.ApiResponseHeaderVo[] apiResponseHeaders = new ApiVo.ApiResponseHeaderVo[zApiResponseHeaders.length];
                                for (int u = 0; u < zApiResponseHeaders.length; u++) {
                                    ZApiResponseHeader zApiResponseHeader = zApiResponseHeaders[u];
                                    ApiVo.ApiResponseHeaderVo apiResponseHeaderVo = new ApiVo.ApiResponseHeaderVo();
                                    apiResponseHeaderVo.setName(zApiResponseHeader.name());
                                    apiResponseHeaderVo.setDescription(zApiResponseHeader.description());
                                    apiResponseHeaderVo.setValue(zApiResponseHeader.value());
                                    apiResponseHeaders[u] = apiResponseHeaderVo;
                                }
                                apiResponsesVo.setApiResponseHeaders(apiResponseHeaders);
                            } else {
                                if (method.isAnnotationPresent(ZApiResponse.class)) {
                                    ZApiResponse zApiResponse = method.getAnnotation(ZApiResponse.class);
                                    ApiVo.ApiResponseVo apiResponseVo = new ApiVo.ApiResponseVo();
                                    apiResponseVo.setCode(zApiResponse.code());
                                    apiResponseVo.setMessage(zApiResponse.message());
                                    Class<?> responseBody = zApiResponse.responseBody();
                                    apiResponseVo.setResponseBody(responseBody);
                                    ApiVo.ApiResponseVo[] apiResponseVos = new ApiVo.ApiResponseVo[]{apiResponseVo};
                                    apiResponsesVo.setApiResponses(apiResponseVos);
                                }
                                if (method.isAnnotationPresent(ZApiResponseHeader.class)) {
                                    ZApiResponseHeader zApiResponseHeader = method.getAnnotation(ZApiResponseHeader.class);
                                    ApiVo.ApiResponseHeaderVo apiResponseHeaderVo = new ApiVo.ApiResponseHeaderVo();
                                    apiResponseHeaderVo.setName(zApiResponseHeader.name());
                                    apiResponseHeaderVo.setValue(zApiResponseHeader.value());
                                    apiResponseHeaderVo.setDescription(zApiResponseHeader.description());
                                    ApiVo.ApiResponseHeaderVo[] apiResponseHeaderVos = new ApiVo.ApiResponseHeaderVo[]{apiResponseHeaderVo};
                                    apiResponsesVo.setApiResponseHeaders(apiResponseHeaderVos);
                                }
                            }
                            apiMethodVo.setApiResponses(apiResponsesVo);
                            apiMethods.add(apiMethodVo);
                        }
                    }
                    apiVo.setApiMethods(apiMethods);
                    apiList.add(apiVo);
                }
            } else if (clazz.isAnnotationPresent(ZApiEntity.class)) {
                ApiEntityVo apiEntityVo = new ApiEntityVo();
                ZApiEntity zApiEntity = clazz.getAnnotation(ZApiEntity.class);
                apiEntityVo.setName(clazz.getName());
                apiEntityVo.setDescription(zApiEntity.value());
                apiEntityVo.setAuthor(zApiEntity.author());
                apiEntityVo.setVersion(zApiEntity.version());
                List<ApiEntityVo.ApiEntityFieldVo> apiEntityFieldVos = new ArrayList<>();
                Field[] fields = clazz.getDeclaredFields();

                //判断类中是否所有属性都没有ZApiEntityField注解，如果没有则默认全部都添加
                boolean f = false;
                for (Field field : fields) {
                    if (field.isAnnotationPresent(ZApiEntityField.class)) {
                        f = true;
                        break;
                    }
                }
                if (f) {
                    //只获取又ZApiEntityField注解的字段
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(ZApiEntityField.class)) {
                            ZApiEntityField zApiEntityField = field.getAnnotation(ZApiEntityField.class);
                            ApiEntityVo.ApiEntityFieldVo apiEntityFieldVo = new ApiEntityVo.ApiEntityFieldVo();
                            apiEntityFieldVo.setName(field.getName());
                            apiEntityFieldVo.setFieldType(field.getType().getName());
                            apiEntityFieldVo.setDescription(zApiEntityField.value());
                            boolean defined = true;
                            String typeName = field.getType().getName();
                            for (String excludeClass : excludeClass) {
                                if (typeName.equals(excludeClass)) {
                                    defined = false;
                                    break;
                                }
                            }
                            apiEntityFieldVo.setDefined(defined);
                            apiEntityFieldVos.add(apiEntityFieldVo);
                        }
                    }
                } else {
                    for (Field field : fields) {
                        String name = field.getName();
                        //排除serialVersionUID字段
                        if ("serialVersionUID".equals(name)) {
                            continue;
                        }
                        ApiEntityVo.ApiEntityFieldVo apiEntityFieldVo = new ApiEntityVo.ApiEntityFieldVo();
                        apiEntityFieldVo.setName(name);
                        apiEntityFieldVo.setDescription("");
                        apiEntityFieldVo.setFieldType(field.getType().getName());
                        boolean defined = true;
                        String typeName = field.getType().getName();
                        for (String excludeClass : excludeClass) {
                            if (typeName.equals(excludeClass)) {
                                defined = false;
                                break;
                            }
                        }
                        apiEntityFieldVo.setDefined(defined);
                        apiEntityFieldVos.add(apiEntityFieldVo);
                    }
                }

                apiEntityVo.setApiEntityFields(apiEntityFieldVos);
                apiEntityList.add(apiEntityVo);
            }
        }
        this.apiEntityList = apiEntityList;
        this.apiList = apiList;
    }


    public String getBasePack() {
        return basePack;
    }


    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public List<ApiVo> getApiList() {
        return apiList;
    }

    public List<ApiEntityVo> getApiEntityList() {
        return apiEntityList;
    }
}
