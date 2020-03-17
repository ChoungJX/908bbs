package com.demo.index.interceptor;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GloablExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "服务器摸鱼ing...";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", msg);
        jsonObject.put("status","-2");
        return jsonObject;
    }
}
