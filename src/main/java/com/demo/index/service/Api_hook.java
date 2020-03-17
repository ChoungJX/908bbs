package com.demo.index.service;




import com.alibaba.fastjson.JSONObject;


import org.springframework.stereotype.Service;

/**
 * Api_hook
 */
@Service
public class Api_hook {

    public JSONObject login(JSONObject jsonParam){



        JSONObject result = new JSONObject();
        result.put("status", "1");
        return result;
    }

    public JSONObject enroll(JSONObject jsonParam){
        




        JSONObject result = new JSONObject();
        result.put("status", "1");
        return result;
    }
}