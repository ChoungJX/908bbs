package com.demo.index.controllor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import com.demo.index.dao.UserDao;
import com.demo.index.domain.po.UserDo;
import com.demo.index.service.TokenService;
import com.demo.index.util.CookieUtils;
import com.demo.index.util.IpUtil;
import com.demo.index.util.MD5Util;
import com.demo.index.util.Token2Username;
import com.demo.index.util.What_time;
import com.demo.index.util.annotation.RsaSecurityParameter;
import com.demo.index.util.annotation.UserLoginToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
/**
 * DebugControllor
 */
public class UserControllor {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private Token2Username checkToken;


    @RequestMapping(value = "/api_enroll", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @RsaSecurityParameter
    public JSONObject enrool(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        if(jsonParam.get("username").toString().length()>16 || jsonParam.get("username").toString().length()<6){
            JSONObject result = new JSONObject();
            result.put("status","-1");
            result.put("message","请保证用户和密码介于6~16位之间！");
            return result;
        }

        if(jsonParam.get("password").toString().length()>16 || jsonParam.get("password").toString().length()<6){
            JSONObject result = new JSONObject();
            result.put("status","-1");
            result.put("message","请保证用户和密码介于6~16位之间！");
            return result;
        }

        String get_username = jsonParam.get("username").toString();
        UserDo userDo = userDao.findByUsername(get_username);

        if(userDo != null){
            JSONObject result = new JSONObject();
            result.put("status","0");
            return result;
        }

        jsonParam.put("password",MD5Util.getMD5(jsonParam.get("password").toString()));
        UserDo new_user = new UserDo();
        new_user.enroll_user(jsonParam);

        userDao.save(new_user);
        

        String token = tokenService.getToken(new_user);
        CookieUtils.writeCookie(response, "sessionid", token);
        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }

    @RequestMapping(value = "/api_login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @RsaSecurityParameter
    public JSONObject login(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        What_time now_time = new What_time();
        jsonParam.put("ip",get_ip);
        jsonParam.put("password",MD5Util.getMD5(jsonParam.get("password").toString()));

        String get_username = jsonParam.get("username").toString();
        UserDo userDo = userDao.findByUsername(get_username);

        if(userDo == null){
            JSONObject result = new JSONObject();
            result.put("status","0");
            result.put("message","用户不存在！");
            return result;
        }

        String password = jsonParam.get("password").toString();
        if (!password.equals(userDo.getPassword())){
            JSONObject result = new JSONObject();
            result.put("status","-1");
            result.put("message","密码错误！");
            return result;
        }

        if(userDo.getType().equals("0")){
            JSONObject result = new JSONObject();
            result.put("status","-3");
            result.put("message","用户已被禁封！");
            return result;
        }

        userDo.setLast_login_ip(get_ip);
        userDo.setLast_login_time(now_time.getTime_string());
        userDao.save(userDo);

        String token = tokenService.getToken(userDo);
        CookieUtils.writeCookie(response, "sessionid", token);
        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }


    @RequestMapping(value = "/api_index_viewer", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject index_viewer(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);
        CookieUtils.writeCookie(response, "public", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGQ1UrIqfkjo8KHCnee/EfXalrc66atPyTjxgOWag/JltjTZb0ByVJOd5FGTqWcMrb2rMyHZZn3egy2TDDur4QvfznTjZGO51MlAe4ZGl4VO5O5VQY9g4zjw7t004NtLV/t6648Do/urgnQc1uL3IDLiuSFqtA4MNKMl/4ScLVvwIDAQAB");

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","0");
            result.put("url", "/login");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("username", userDo.getUsername());
        result.put("url", "/");
        return result;
    }

    @RequestMapping(value = "/api_admin_login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_login(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        What_time now_time = new What_time();
        jsonParam.put("ip",get_ip);


        String get_username = jsonParam.get("username").toString();
        UserDo userDo = userDao.findByUsername(get_username);

        if(userDo == null){
            JSONObject result = new JSONObject();
            result.put("status","0");
            return result;
        }

        String password = jsonParam.get("password").toString();
        if (!password.equals(userDo.getPassword())){
            JSONObject result = new JSONObject();
            result.put("status","-1");
            return result;
        }

        int userType = Integer.parseInt(userDo.getType());
        if (userType < 200){
            JSONObject result = new JSONObject();
            result.put("status","0");
            return result;
        }

        userDo.setLast_login_ip(get_ip);
        userDo.setLast_login_time(now_time.getTime_string());
        userDao.save(userDo);


        String token = tokenService.getToken(userDo);
        CookieUtils.writeCookie(response, "sessionid", token);
        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }


    
    @UserLoginToken
    @RequestMapping(value = "/api_person_info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject person_info(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();

        JSONObject data = new JSONObject();

        data.put("ip",userDo.getLast_login_ip());
        data.put("lastTime",userDo.getLast_login_time());
        data.put("enrollTime",userDo.getEnroll_time());
        data.put("uId",userDo.getuId());
        data.put("email",userDo.getEmail());
        data.put("username",userDo.getUsername());
                

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_change_info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject change_person_info(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();
        if (jsonParam.containsKey("password")){
            userDo.setPassword(MD5Util.getMD5(jsonParam.get("password").toString()));
        }

        if (jsonParam.containsKey("email")){
            userDo.setEmail(jsonParam.getString("email"));
        }

        userDao.save(userDo);
        
        token = tokenService.getToken(userDo);
        CookieUtils.writeCookie(response, "sessionid", token);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_logout", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject logout(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            result.put("message","登录验证失败！");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();
        if (jsonParam.containsKey("password")){
            userDo.setPassword(MD5Util.getMD5(jsonParam.get("password").toString()));
        }

        
        CookieUtils.delCookie(response, "sessionid");
        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("message","注销成功！");
        return result;
    }
}