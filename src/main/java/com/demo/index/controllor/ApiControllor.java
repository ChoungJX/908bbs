package com.demo.index.controllor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.demo.index.service.Api_caller;
import com.demo.index.service.Api_hook;
import com.demo.index.util.IpUtil;

//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


//@RestController
@Controller
/**
 * IndexControllor
 */
public class ApiControllor {
    private Api_hook native_class;
    private Map<String,Method> function_list;

    public ApiControllor() throws Exception{
        Api_caller aaa = new Api_caller();
        function_list = aaa.getApi_call();
        native_class = new Api_hook();
    }

    @ResponseBody
    @RequestMapping(value = "/api", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void receive(@RequestBody JSONObject jsonParam, HttpServletRequest request) throws Exception{
        String get_api = jsonParam.get("api").toString();
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);


        

        
        Method func = function_list.get(get_api);
        func.invoke(native_class,jsonParam);
        
    }
}