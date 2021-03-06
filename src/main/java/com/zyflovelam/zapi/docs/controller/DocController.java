package com.zyflovelam.zapi.docs.controller;

import com.alibaba.fastjson.JSONObject;
import com.zyflovelam.zapi.docs.config.ZApiConfig;
import com.zyflovelam.zapi.docs.entity.po.ApiInfo;
import com.zyflovelam.zapi.docs.entity.vo.ApiEntityVo;
import com.zyflovelam.zapi.docs.entity.vo.ApiVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Project: BucksYard-DataServer
 * Package: com.bucksyard.api.docs.service.controller
 * <p>
 * Created by zyflovelam on 2018/3/27.
 *
 * @author by zyflovelam
 */
@Controller
public class DocController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ZApiConfig zApiConfig;

    @GetMapping("/z-docs")
    public ModelAndView docs() {
        ApiInfo apiInfo = zApiConfig.getApiInfo();
        List<ApiVo> apiList = zApiConfig.getApiList();
        List<ApiEntityVo> apiEntityList = zApiConfig.getApiEntityList();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("apiInfo", apiInfo);
        String string = JSONObject.toJSONString(apiList);
        modelAndView.addObject("apiList", string);
        String list = JSONObject.toJSONString(apiEntityList);
        modelAndView.addObject("apiEntityList", list);
        logger.info("进入到z-docs");
        logger.info("视图信息：");
        logger.info(JSONObject.toJSONString(modelAndView));

        return modelAndView;
    }
}
