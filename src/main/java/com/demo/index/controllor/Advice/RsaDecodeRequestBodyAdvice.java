package com.demo.index.controllor.Advice;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import com.demo.index.util.RSAUtils;
import com.demo.index.util.annotation.RsaSecurityParameter;
 
/**
 * @author monkey
 * @desc 请求数据解密
 * @date 2018/10/29 20:17
 */
@ControllerAdvice(basePackages = "com.demo.index.controllor")
public class RsaDecodeRequestBodyAdvice implements RequestBodyAdvice {
 
    private static final Logger logger = LoggerFactory.getLogger(RsaDecodeRequestBodyAdvice.class);
 
    private static final String PRIVATE_KEY="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIZDVSsip+SOjwocKd578R9dqWtzrpq0/JOPGA5ZqD8mW2NNlvQHJUk53kUZOpZwytvaszIdlmfd6DLZMMO6vhC9/OdONkY7nUyUB7hkaXhU7k7lVBj2DjOPDu3TTg20tX+3rrjwOj+6uCdBzW4vcgMuK5IWq0Dgw0oyX/hJwtW/AgMBAAECgYAl+VVVZe/mjQuX6G9PJe8E2BDa+1UfHKFzer1rEaCse8TNSVywmHTLSTq8gG45rp0xoaGdQxEPP4w1FYjdUyv+V0KUY6l9b1p9eG5wh66fjR4kC/RsDupFKTBN2xiCWD/4Dy1cgOG++cfusgXgPLH3GKQ17HXksTlpWzEflqpkwQJBAMIJbWbsaFTAmEZTIKH/LSKTG5DXHLtsRw9L9OfCKyYyKbUPdoupwOIodXDlxY2ODYoGWbU3eqjPEH6sUE6iydsCQQCxI2CgRjSffPSRKNAGC53KMr71IYBTg+zkrAEMmtYPpCWEl10ntbtYMI7f+oVZ7rwmzoHYgVkt7QIQJU9DIsLtAkB9ArPgXqktCVRR4pagqBV9NAfLfju9qJnzif1EH33LQwBJ7adzfa+ORYC8dtybQY0JguPi89Zr0dQPuJaYwxqHAkAYmrBeMsI2cPXLf969KDnnjvrlM50OfKGX9ahDpvIaxBMOArRGwsBd9Iiz5alGH5n0zfRNLVJExcnmkjgbPCXVAkEAhHESVqQD0GW2DHz88yvbP2AwOMVoS1MHHd2o43IWNPP0kfiTr7uWhCkI5+5Z/VvWRXOCDBXogfSbbsP0N9oWEA==";
 
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }
 
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }
 
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        try {
            boolean encode = false;
            if (methodParameter.getMethod().isAnnotationPresent(RsaSecurityParameter.class)) {
                //获取注解配置的包含和去除字段
                RsaSecurityParameter serializedField = methodParameter.getMethodAnnotation(RsaSecurityParameter.class);
                //入参是否需要解密
                encode = serializedField.inDecode();
            }
            if (encode) {
                logger.info("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密");
                return new MyHttpInputMessage(inputMessage);
            }else{
                return inputMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密出现异常："+e.getMessage());
            return inputMessage;
        }
    }
 
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }
 
    class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;
 
        private InputStream body;
 
        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            String content = easpString(IOUtils.toString(inputMessage.getBody(),"utf-8"));
            this.body = IOUtils.toInputStream(RSAUtils.decryptDataOnJava(content,PRIVATE_KEY));
        }
 
        @Override
        public InputStream getBody() throws IOException {
            return body;
        }
 
        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
 
        /**
         *
         * @param requestData
         * @return
         */
        public String easpString(String requestData){
            if(requestData != null && !requestData.equals("")){
                String s = "{\"requestData\":";
                if(!requestData.startsWith(s)){
                    throw new RuntimeException("参数【requestData】缺失异常！");
                }else{
                    int closeLen = requestData.length()-1;
                    int openLen = "{\"requestData\":".length();
                    String substring = StringUtils.substring(requestData, openLen, closeLen);
                    return substring;
                }
            }
            return "";
        }
    }
}