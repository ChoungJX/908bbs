package com.demo.index.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * Api_caller
 */
public class Api_caller {

    private Map<String,Method> api_call;


    public Api_caller() throws Exception{
        api_call = new HashMap<>();
        Class<?> clazz = Class.forName("com.demo.index.service.Api_hook");
        
        Method login = clazz.getMethod("login",JSONObject.class);
        api_call.put("login", login);


        Method enroll = clazz.getMethod("enroll",JSONObject.class);
        api_call.put("enroll", enroll);
    }

    public Map<String,Method> getApi_call(){
        return api_call;
    }
}