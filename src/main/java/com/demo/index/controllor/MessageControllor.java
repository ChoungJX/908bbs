package com.demo.index.controllor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.demo.index.dao.MessageDao;
import com.demo.index.dao.UserDao;
import com.demo.index.domain.po.MessageDo;
import com.demo.index.domain.po.UserDo;
import com.demo.index.util.CookieUtils;
import com.demo.index.util.IpUtil;
import com.demo.index.util.Token2Username;
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
public class MessageControllor {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private Token2Username checkToken;

    
    @UserLoginToken
    @RequestMapping(value = "/api_message_send", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject sendMessage(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();

        UserDo getRuser = userDao.findByUsername(jsonParam.get("username").toString());
        if (getRuser==null){
            JSONObject result = new JSONObject();
            result.put("status","0");
            return result;
        }
        if (userDo.getuId().equals(getRuser.getuId())){
            JSONObject result = new JSONObject();
            result.put("status","0");
            return result;
        }

        jsonParam.put("sendUserId", userDo.getuId());
        jsonParam.put("receiveUserId", getRuser.getuId());

        MessageDo messageDo = new MessageDo();
        messageDo.createMessage(jsonParam);

        messageDao.save(messageDo);



        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_message_receive_no", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject receiveMessageNo(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        int onePage = 9;
        int page = 1;
        
        try{
            String pageData = jsonParam.get("page").toString();
            page = Integer.parseInt(pageData);
            if (page <=0){
                page = 1;
            }
        }catch(Exception e){
            page = 1;
        }
        
        
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();

        List<MessageDo> getList = messageDao.selectReceiveNo(userDo.getuId(), (page-1)*onePage,onePage);
        
        for(int i=0;i<getList.size();i++){
            
        }
        

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("message",getList);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_message_receive_no_number", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject receiveMessageNo_number(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        int onePage = 9;
        int page = 1;
        
        try{
            String pageData = jsonParam.get("page").toString();
            page = Integer.parseInt(pageData);
            if (page <=0){
                page = 1;
            }
        }catch(Exception e){
            page = 1;
        }
        
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();

        int getnumber = messageDao.selectReceiveNo_number(userDo.getuId());
        
        

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("number",getnumber);
        result.put("onepage",onePage);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_message_receive_yes", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject receiveMessageYes(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        int onePage = 9;
        int page = 1;
        
        try{
            String pageData = jsonParam.get("page").toString();
            page = Integer.parseInt(pageData);
            if (page <=0){
                page = 1;
            }
        }catch(Exception e){
            page = 1;
        }
        
        
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();

        List<MessageDo> getList = messageDao.selectReceiveYes(userDo.getuId(), (page-1)*onePage,onePage);
        
        
        

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("message",getList);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_message_receive_yes_number", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject receiveMessageYes_number(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        int onePage = 9;
        
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();

        int getNumber = messageDao.selectReceiveYes_number(userDo.getuId());
        
        

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("number",getNumber);
        result.put("onepage",onePage);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_message_send_yes", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject sendMessageYes(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        int onePage = 9;
        int page = 1;
        
        try{
            String pageData = jsonParam.get("page").toString();
            page = Integer.parseInt(pageData);
            if (page <=0){
                page = 1;
            }
        }catch(Exception e){
            page = 1;
        }
        
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();

        List<Object> getList = messageDao.selectSendYes(userDo.getuId(), (page-1)*onePage,onePage);
        
        

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("message",getList);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_message_send_yes_number", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject sendMessageYes_number(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        int onePage = 9;
        
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();

        int getNumber = messageDao.selectSendYes_number(userDo.getuId());
        
        

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("onepage",onePage);
        result.put("number",getNumber);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_oneMessage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject oneMessage(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        UserDo userDo = checkToken.getUserDo();
        MessageDo messageDo = messageDao.getOne(jsonParam.get("mId").toString());

        if (!messageDo.getReceiveUserId().equals(userDo.getuId()) && !messageDo.getSendUserId().equals(userDo.getuId())){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }

        if(messageDo.getReceiveUserId().equals(userDo.getuId())){
            UserDo get_send_user = userDao.findByUId(messageDo.getSendUserId());

            JSONObject result = new JSONObject();
            result.put("status","1");
            result.put("sendUser",get_send_user.getUsername());
            result.put("receiveUser",userDao.findByUId(messageDo.getReceiveUserId()).getUsername());
            result.put("title", messageDo.getmTitle());
            result.put("content", messageDo.getmContent());

            messageDo.setmStatus("1");
            messageDao.save(messageDo);
            
            return result;
        }
        if(messageDo.getSendUserId().equals(userDo.getuId())){
            UserDo get_send_user = userDao.findByUId(messageDo.getSendUserId());

            JSONObject result = new JSONObject();
            result.put("status","1");
            result.put("sendUser",get_send_user.getUsername());
            result.put("receiveUser",userDao.findByUId(messageDo.getReceiveUserId()).getUsername());
            result.put("title", messageDo.getmTitle());
            result.put("content", messageDo.getmContent());

            messageDao.save(messageDo);
            
            return result;
        }

        JSONObject result = new JSONObject();
        result.put("status","-2");
        return result;
    }
}