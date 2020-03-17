package com.demo.index.controllor;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.index.dao.AreaDao;
import com.demo.index.dao.BbsDao;
import com.demo.index.dao.PaperclipDao;
import com.demo.index.dao.ReceiveDao;
import com.demo.index.dao.SpareDao;
import com.demo.index.dao.UserDao;
import com.demo.index.domain.po.BbsDo;
import com.demo.index.domain.po.PaperclipDo;
import com.demo.index.domain.po.ReceiveDo;
import com.demo.index.domain.po.SpareDo;
import com.demo.index.domain.po.UserDo;
import com.demo.index.util.CookieUtils;
import com.demo.index.util.IpUtil;
import com.demo.index.util.Token2Username;
import com.demo.index.util.annotation.UserLoginToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class BbsControllor {

    @Autowired
    private SpareDao spareDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private BbsDao bbsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Token2Username checkToken;

    @Autowired
    private ReceiveDao receiveDao;

    @Autowired
    private PaperclipDao paperclipDao;

    @RequestMapping(value = "/api_get_where", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject getWhere(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String get_spare = jsonParam.get("spare").toString();
        JSONObject temp_one = spareDao.getSpare_areaName(get_spare);


        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("name", temp_one);
        return result;
    }

    @RequestMapping(value = "/api_get_one_where", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject getOneWhere(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String get_spare = jsonParam.get("bid").toString();
        BbsDo bbsDo = bbsDao.findBybbsId(get_spare);


        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("area_name",areaDao.findByareaId(bbsDo.getAreaId()).getareaName());
        result.put("spare_name",spareDao.findByspareId(bbsDo.getSpareId()).getSpareName());
        result.put("spare_href",bbsDo.getSpareId());
        result.put("bbs_name",bbsDo.getBbsTitle());
        return result;
    }

    @RequestMapping(value = "/api_get_bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject getBbs(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
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

        
        String get_spare = jsonParam.get("spare").toString();
        List<BbsDo> get_bbs = bbsDao.getBbs(get_spare, (page-1)*onePage, onePage);
        int allNumber = bbsDao.getBbsNumber(get_spare);
        List<JSONObject> data = new ArrayList<>();

        for(BbsDo x:get_bbs){
            JSONObject one_bbs = new JSONObject();
            one_bbs.put("bbsTitle",x.getBbsTitle());
            one_bbs.put("bbsId",x.getBbsId());
            one_bbs.put("lastTime",x.getLastReceiveTime());
            UserDo get_user = userDao.findByUId(x.getSubmitUserId());
            one_bbs.put("username",get_user.getUsername());
            one_bbs.put("readNumber", x.getReadNumber());
            try{
                if(x.getIfTop().equals("1")){
                    one_bbs.put("flag","1");
                }else{
                    one_bbs.put("flag","0");
                }
            }catch(Exception e){
                one_bbs.put("flag","0");
            }
            data.add(one_bbs);
        }

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data", data);
        result.put("page",page);
        result.put("number", allNumber);
        result.put("onepage",onePage);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_create_bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject createBbs(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        if(jsonParam.get("bbsTitle").toString().length() < 3){
            JSONObject result = new JSONObject();
            result.put("status","0");
            result.put("message","请确保标题大于3个字！");
            return result;
        }
        if(jsonParam.get("bbsContent").toString().length() < 3){
            JSONObject result = new JSONObject();
            result.put("status","0");
            result.put("message","请确保内容大于3个字！");
            return result;
        }

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            result.put("message","请先登录!");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();

        jsonParam.put("submitUserId", userDo.getuId());



        String get_spare = jsonParam.get("spare").toString();
        JSONObject temp_one = spareDao.getSpare_areaId(get_spare);

        jsonParam.put("areaId", temp_one.get("area_id").toString());
        jsonParam.put("spareId", temp_one.get("spare_id").toString());
        
        BbsDo bbsDo = new BbsDo();
        bbsDo.createBbs(jsonParam);

        bbsDao.save(bbsDo);

        JSONObject result = new JSONObject();
        result.put("bid",bbsDo.getBbsId());
        result.put("status","1");
        
        return result;
    }

    @RequestMapping(value = "/api_one_bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject getOneBbs(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
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

        String bbsId = jsonParam.get("bbsId").toString();

        int allNumber = receiveDao.getReceiveNumber(bbsId);
        
        List<ReceiveDo> get_receive = receiveDao.getReceive(bbsId, (page-1)*onePage, onePage);
        List<JSONObject> convert_receive = new ArrayList<>();
        for(ReceiveDo x:get_receive){
            JSONObject one = new JSONObject();
            UserDo userDo = userDao.findByUId(x.getSubmitUserID());
            one.put("username", userDo.getUsername());
            one.put("receiveContent",x.getReceiveContent());
            one.put("receiveTime",x.getReceiveTime());
            one.put("reId",x.getReceiveId());
            convert_receive.add(one);
        }


        BbsDo bbsDo = bbsDao.findBybbsId(bbsId);
        JSONObject convert_bbs = new JSONObject();
        UserDo userDo = userDao.findByUId(bbsDo.getSubmitUserId());
        PaperclipDo paperclipDo = paperclipDao.findByBelongBbs(bbsDo.getBbsId());
        convert_bbs.put("username",userDo.getUsername());
        convert_bbs.put("bbsTitle",bbsDo.getBbsTitle());
        convert_bbs.put("bbsContent", bbsDo.getBbsContent());
        convert_bbs.put("lastTime",bbsDo.getLastTime());
        convert_bbs.put("readNumber", bbsDo.getReadNumber());
        convert_bbs.put("bbsId", bbsDo.getBbsId());
        convert_bbs.put("spId", bbsDo.getSpareId());
        
        String get_paperclip = bbsDo.getPaperclip();
        if(get_paperclip == null || get_paperclip.equals("")){
            convert_bbs.put("purl","000");
        }else{
            convert_bbs.put("pname", paperclipDo.getPaperclipName());
            convert_bbs.put("purl",paperclipDo.getPaperclipsUrl());
        }

        bbsDo.setReadNumber(bbsDo.getReadNumber()+1);
        bbsDao.save(bbsDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("receive", convert_receive);
        result.put("bbs", convert_bbs);
        result.put("page",page);
        result.put("number", allNumber);
        result.put("onepage",onePage);
        return result;
    }


    @UserLoginToken
    @RequestMapping(value = "/api_create_receive", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject createReceive(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        if(jsonParam.get("receiveContent").toString().length() < 3){
            JSONObject result = new JSONObject();
            result.put("status","0");
            result.put("message","请确保内容大于3个字！");
            return result;
        }

        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            result.put("message","请先登录!");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        
        String bbsId = jsonParam.get("bbsId").toString();
        jsonParam.put("bbsId", bbsId);
        jsonParam.put("submitUserID", userDo.getuId());
        
        ReceiveDo receiveDo = new ReceiveDo();

        receiveDo.createReceive(jsonParam);
        receiveDao.save(receiveDo);

        BbsDo bbsDo = bbsDao.findBybbsId(bbsId);
        bbsDo.setReceiveNumber(bbsDo.getReceiveNumber()+1);
        bbsDao.save(bbsDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_person_get_bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject person_get_bbs(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
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
        //List<BbsDo> get_bbs = bbsDao.findBysubmitUserId(userDo.getuId());
        List<BbsDo> get_bbs = bbsDao.getBbs_subuser(userDo.getuId(), (page-1)*onePage,onePage);
        List<JSONObject> data = new ArrayList<>();

        for(BbsDo x:get_bbs){
            JSONObject one = new JSONObject();
            one.put("bbsID",x.getBbsId());
            one.put("bbsName", x.getBbsTitle());
            one.put("bbsArea",areaDao.findByareaId(x.getAreaId()).getareaName());
            one.put("bbsSpare",spareDao.findByspareId(x.getSpareId()).getSpareName());
            one.put("bbsTime",x.getSubmitTime());

            data.add(one);
        }


        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        result.put("page", page);
        result.put("onepage",onePage);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_person_get_bbs_number", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject person_get_bbs_number(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();


        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("number",bbsDao.getBbs_subuser_numver(userDo.getuId()));
        result.put("onepage",9);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_person_get_receive", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject person_get_receive(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
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
        //List<BbsDo> get_bbs = bbsDao.findBysubmitUserId(userDo.getuId());bbsDao.getBbs_subuser(userDo.getuId(), (page-1)*onePage,onePage);
        List<ReceiveDo> get_re = receiveDao.getReceive_user(userDo.getuId(), (page-1)*onePage,onePage);
        List<JSONObject> data = new ArrayList<>();

        for(ReceiveDo x:get_re){
            JSONObject one = new JSONObject();
            BbsDo bbsDo = bbsDao.findBybbsId(x.getBbsId());
            one.put("bbsTitle",bbsDo.getBbsTitle());
            one.put("reContent",x.getReceiveContent());
            one.put("reTime",x.getReceiveTime());
            one.put("bbsArea",areaDao.findByareaId(bbsDo.getAreaId()).getareaName());
            one.put("bbsSpare",spareDao.findByspareId(bbsDo.getSpareId()).getSpareName());
            one.put("bbsID",x.getReceiveId());
            one.put("bbsId",bbsDo.getBbsId());

            data.add(one);
        }


        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        result.put("page", page);
        result.put("onepage",onePage);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_person_get_receive_number", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject person_get_receive_number(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();


        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("number",receiveDao.getReceiveNumber_user(userDo.getuId()));
        result.put("onepage",9);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_delete_bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject delete_bbs(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        List<String> delData = JSONArray.toJavaObject(jsonParam.getJSONArray("delData"), List.class);

        int userType = Integer.parseInt(userDo.getType());
        if (userType >= 400){
            for (String x:delData){
                BbsDo bbsDo = bbsDao.findBybbsId(x);
                bbsDao.delete(bbsDo);
                receiveDao.delReceive_bbsId(bbsDo.getBbsId());
            }
            JSONObject result = new JSONObject();
            result.put("status","1");
            return result;
        }

        if (userType >= 300){
            for (String x:delData){
                BbsDo bbsDo = bbsDao.findBybbsId(x);
                if(userDo.getuId().equals(areaDao.findByareaId(bbsDo.getAreaId()).getareaUserId())){
                    bbsDao.delete(bbsDo);
                    receiveDao.delReceive_bbsId(bbsDo.getBbsId());
                }
            }
            JSONObject result = new JSONObject();
            result.put("status","1");
            return result;
        }

        if (userType >= 200){
            for (String x:delData){
                BbsDo bbsDo = bbsDao.findBybbsId(x);
                if(userDo.getuId().equals(spareDao.findByspareId(bbsDo.getSpareId()).getSpareUserId())){
                    bbsDao.delete(bbsDo);
                    receiveDao.delReceive_bbsId(bbsDo.getBbsId());
                }
            }
            JSONObject result = new JSONObject();
            result.put("status","1");
            return result;
        }

        int flag = 0;

        for (String x:delData){
            BbsDo bbsDo = bbsDao.findBybbsId(x);
            if(userDo.getuId().equals(bbsDo.getSubmitUserId())){
                bbsDao.delete(bbsDo);
                receiveDao.delReceive_bbsId(bbsDo.getBbsId());
                flag = 1;
                
            }
        }

        if (flag == 1){
            JSONObject result = new JSONObject();
            result.put("status","1");
            return result;
        }

        JSONObject result = new JSONObject();
        result.put("status","0");
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_delete_receive", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject delete_receive(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        List<String> delData = JSONArray.toJavaObject(jsonParam.getJSONArray("delData"), List.class);

        int userType = Integer.parseInt(userDo.getType());
        if (userType >= 400){
            for (String x:delData){
                ReceiveDo receiveDo = receiveDao.findByreceiveId(x);
                receiveDao.delete(receiveDo);
            }
            JSONObject result = new JSONObject();
            result.put("status","1");
            return result;
        }

        if (userType >= 300){
            for (String x:delData){
                BbsDo bbsDo = bbsDao.findBybbsId(receiveDao.findByreceiveId(x).getBbsId());
                if(userDo.getuId().equals(areaDao.findByareaId(bbsDo.getAreaId()).getareaUserId())){
                    ReceiveDo receiveDo = receiveDao.findByreceiveId(x);
                    receiveDao.delete(receiveDo);
                }
            }
            JSONObject result = new JSONObject();
            result.put("status","1");
            return result;
        }

        if (userType >= 200){
            for (String x:delData){
                BbsDo bbsDo = bbsDao.findBybbsId(receiveDao.findByreceiveId(x).getBbsId());
                if(userDo.getuId().equals(spareDao.findByspareId(bbsDo.getSpareId()).getSpareUserId())){
                    ReceiveDo receiveDo = receiveDao.findByreceiveId(x);
                    receiveDao.delete(receiveDo);
                }
            }
            JSONObject result = new JSONObject();
            result.put("status","1");
            return result;
        }

        int flag = 0;

        for (String x:delData){
            ReceiveDo receiveDo = receiveDao.findByreceiveId(x);
            if(userDo.getuId().equals(receiveDo.getSubmitUserID())){
                receiveDao.delete(receiveDo);
                flag = 1;
                
            }
        }

        if (flag == 1){
            JSONObject result = new JSONObject();
            result.put("status","1");
            return result;
        }

        JSONObject result = new JSONObject();
        result.put("status","0");
        return result;
    }

    @RequestMapping(value = "/api_index_get_number", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject index_get_number(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        List<SpareDo> get_spare = spareDao.findAll();
        List<JSONObject> data = new ArrayList<>();

        for(SpareDo x:get_spare){
            JSONObject one = new JSONObject();
            one.put("name",x.getSpareName());
            one.put("value",bbsDao.getBbsNumber(x.getSpareId()));
            data.add(one);
        }

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        return result;
    }

    @RequestMapping(value = "/api_index_get_hot_bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject index_get_hot_bbs(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        List<BbsDo> get_bbs = bbsDao.getBbs_hot();

        List<JSONObject> data = new ArrayList<>();
        for(BbsDo x:get_bbs){
            JSONObject one = new JSONObject();
            one.put("title",x.getBbsTitle());
            one.put("bid",x.getBbsId());
            one.put(
                "username",
                userDao.findByUId(x.getSubmitUserId()).getUsername()
            );
            one.put(
                "sname",
                spareDao.findByspareId(x.getSpareId()).getSpareName()
            );
            one.put("number",x.getReadNumber());
            data.add(one);
        }

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        return result;
    }

    @RequestMapping(value = "/api_get_hot_spare_bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject get_hot_spare_bbs(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){        
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String get_spare = jsonParam.get("spare").toString();
        List<BbsDo> get_bbs = bbsDao.getBbs_hot_spare(get_spare);

        List<JSONObject> data = new ArrayList<>();
        for(BbsDo x:get_bbs){
            JSONObject one = new JSONObject();
            one.put("title",x.getBbsTitle());
            one.put("rnumber",x.getReadNumber());
            one.put("rcnumber",x.getReceiveNumber());
            one.put("username",userDao.findByUId(x.getSubmitUserId()).getUsername());
            one.put("bid",x.getBbsId());
            data.add(one);
        }
        
        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        return result;
    }

    @RequestMapping(value = "/api_get_great_spare_bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject get_great_spare_bbs(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){        
        int onePage = 5;
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

        
        String get_spare = jsonParam.get("spare").toString();
        List<BbsDo> get_bbs = bbsDao.getBbs_great(get_spare, (page-1)*onePage,onePage);

        List<JSONObject> data = new ArrayList<>();
        for(BbsDo x:get_bbs){
            JSONObject one = new JSONObject();
            one.put("title",x.getBbsTitle());
            one.put("rnumber",x.getReadNumber());
            one.put("rcnumber",x.getReceiveNumber());
            one.put("username",userDao.findByUId(x.getSubmitUserId()).getUsername());
            one.put("bid",x.getBbsId());
            data.add(one);
        }
        
        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        result.put("page", page);
        result.put("onepage",onePage);
        return result;
    }

    @RequestMapping(value = "/api_get_great_spare_bbs_number", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject get_great_spare_bbs_number(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        String get_spare = jsonParam.get("spare").toString();

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("number",bbsDao.getBbsNumber_great(get_spare));
        result.put("onepage",5);
        return result;
    }

    @RequestMapping(value = "/api_get_bbs_search", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject getBbs_search(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
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

        
        String get_spare = jsonParam.get("spare").toString();
        List<BbsDo> get_bbs = bbsDao.getBbs_search(get_spare, (page-1)*onePage, onePage);
        int allNumber = bbsDao.getBbs_search_number(get_spare);
        List<JSONObject> data = new ArrayList<>();

        for(BbsDo x:get_bbs){
            JSONObject one_bbs = new JSONObject();
            one_bbs.put("bbsTitle",x.getBbsTitle());
            one_bbs.put("bbsId",x.getBbsId());
            one_bbs.put("lastTime",x.getLastReceiveTime());
            UserDo get_user = userDao.findByUId(x.getSubmitUserId());
            one_bbs.put("username",get_user.getUsername());
            one_bbs.put("readNumber", x.getReadNumber());
            data.add(one_bbs);
        }

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data", data);
        result.put("page",page);
        result.put("number", allNumber);
        result.put("onepage",onePage);
        return result;
    }

    @UserLoginToken
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,@RequestParam("bid") String bid) {
        String fileName = file.getOriginalFilename();
        if(fileName.indexOf("\\") != -1){
            fileName = fileName.substring(fileName.lastIndexOf("\\"));
        }
        String[] fileNameSplit = fileName.split("\\.");
        String fileType = fileNameSplit[fileNameSplit.length-1];
        String fileName1 = ""+new Date().getTime();
        String fileName2 = ""+fileName.hashCode();
        String realFileName = fileName1+"_"+fileName2+"."+fileType;

        String filePath = "/Data/files/";
        String baseUrl = "/files/";
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath+realFileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败,文件大于10M限制！";
        }

        PaperclipDo paperclipDo = new PaperclipDo();
        BbsDo bbsDO = bbsDao.findBybbsId(bid);
        if(bbsDO == null){
            return "创建帖子失败！";
        }
        paperclipDo.setPaperclipName(fileName);
        paperclipDo.setBelongBbs(bbsDO.getBbsId());
        paperclipDo.setPaperclipsUrl(baseUrl+realFileName);
        paperclipDao.save(paperclipDo);
        bbsDO.setPaperclip(paperclipDo.getPaperclipId());
        bbsDao.save(bbsDO);
        return "上传成功!";
    }

    @UserLoginToken
    @RequestMapping("/upload_pic")
    @ResponseBody
    public JSONObject upload_pic(@RequestParam("files") MultipartFile[] files) {
        List<String> data = new ArrayList<>();
        for(MultipartFile x:files){
            String fileName = x.getOriginalFilename();
            if(fileName.indexOf("\\") != -1){
                fileName = fileName.substring(fileName.lastIndexOf("\\"));
            }
            String[] fileNameSplit = fileName.split("\\.");
            String fileType = fileNameSplit[fileNameSplit.length-1];
            String fileName1 = ""+new Date().getTime();
            String fileName2 = ""+fileName.hashCode();
            String realFileName = fileName1+"_"+fileName2+"."+fileType;

            String filePath = "/Data/files/";
            String baseUrl = "/files/";
            File targetFile = new File(filePath);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(filePath+realFileName);
                out.write(x.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                JSONObject result = new JSONObject();
                result.put("errno","0");
                result.put("data", data);
                return result;
            }
            data.add(baseUrl+realFileName);
        }

        JSONObject result = new JSONObject();
        result.put("errno","0");
        result.put("data", data);
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            
        }
        return result;
    }
    
}