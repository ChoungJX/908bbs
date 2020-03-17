package com.demo.index.controllor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.demo.index.dao.AccessDao;
import com.demo.index.dao.AreaDao;
import com.demo.index.dao.BbsDao;
import com.demo.index.dao.InfoDao;
import com.demo.index.dao.ReceiveDao;
import com.demo.index.dao.RecommonDao;
import com.demo.index.dao.SpareDao;
import com.demo.index.dao.UserDao;
import com.demo.index.domain.po.AccessDo;
import com.demo.index.domain.po.AreaDo;
import com.demo.index.domain.po.BbsDo;
import com.demo.index.domain.po.InfoDo;
import com.demo.index.domain.po.RecommonDo;
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
import org.springframework.web.bind.annotation.RestController;



@RestController
/**
 * DebugControllor
 */
public class AdminControllor {

    @Autowired
    private Token2Username checkToken;

    @Autowired
    private AccessDao accessDao;

    @Autowired
    private SpareDao spareDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private BbsDao bbsDao;

    @Autowired
    private ReceiveDao receiveDao;

    @Autowired
    private InfoDao infoDao;

    @Autowired
    private RecommonDao recommonDao;

    @UserLoginToken
    @RequestMapping(value = "/api_get_admin_access", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject get_admin_access(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 200){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        List<AccessDo> get_access = accessDao.getAccess(userDo.getType());


        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data", get_access);
        return result;
    }  

    @UserLoginToken
    @RequestMapping(value = "/api_get_admin_bbs_access", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject get_admin_bbs_start(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        
        List<JSONObject> data = new ArrayList<>();

        if (userType >= 400){
            List<SpareDo> get_a = spareDao.findAll();
            for(SpareDo x:get_a){
                JSONObject one = new JSONObject();
                one.put("sname",x.getSpareName());
                one.put("aname",areaDao.findByareaId(x.getSubordinateAreaId()).getareaName());
                one.put("sid",x.getSpareId());
                data.add(one);
            }
            JSONObject result = new JSONObject();
            result.put("status","1");
            result.put("data",data);
            return result;
        }

        if (userType >= 300){
            List<AreaDo> get_area = areaDao.findByAreaUserId(userDo.getuId());
            List<SpareDo> get_a = new ArrayList<>();
            for (AreaDo x:get_area){
                for(SpareDo y:spareDao.findBySubordinateAreaId(x.getAreaId())){
                    get_a.add(y);
                }
            }
            
            for(SpareDo x:get_a){
                JSONObject one = new JSONObject();
                one.put("sname",x.getSpareName());
                one.put("aname",areaDao.findByareaId(x.getSubordinateAreaId()).getareaName());
                one.put("sid",x.getSpareId());
                data.add(one);
            }
            JSONObject result = new JSONObject();
            result.put("status","1");
            result.put("data",data);
            return result;
        }

        if(userType >= 200){
            List<SpareDo> get_a = spareDao.findBySpareUserId(userDo.getuId());

            for(SpareDo x:get_a){
                JSONObject one = new JSONObject();
                one.put("sname",x.getSpareName());
                one.put("aname",areaDao.findByareaId(x.getSubordinateAreaId()).getareaName());
                one.put("sid",x.getSpareId());
                data.add(one);
            }
        }
        
        
        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        return result;
    }
    
    @UserLoginToken
    @RequestMapping(value = "/api_get_admin_area", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject get_admin_area(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 400){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        List<AreaDo> get_area = areaDao.findAll();

        List<JSONObject> areaData = new ArrayList<>();
        List<List<JSONObject>> spareData = new ArrayList<>();

        for (AreaDo x:get_area){
            JSONObject oneArea = new JSONObject();
            List<JSONObject> allSpare = new ArrayList<>();
            oneArea.put("aid", x.getAreaId());
            oneArea.put("aname", x.getareaName());
            oneArea.put("username",userDao.findByUId(x.getareaUserId()).getUsername());
            
            for (SpareDo y:spareDao.findBySubordinateAreaId(x.getAreaId())){
                JSONObject oneSpare = new JSONObject();
                oneSpare.put("sid",y.getSpareId());
                oneSpare.put("sname",y.getSpareName());
                oneSpare.put("username",userDao.findByUId(y.getSpareUserId()).getUsername());
                oneSpare.put("info",y.getPaperclip());
                allSpare.add(oneSpare);
            }

            areaData.add(oneArea);
            spareData.add(allSpare);
        }

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("areaData",areaData);
        result.put("spareData",spareData);
        return result;
    }  

    @UserLoginToken
    @RequestMapping(value = "/api_admin_change_spare", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_change_spare(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 400){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        SpareDo spareDo = spareDao.findByspareId(jsonParam.get("sid").toString());
        spareDo.setSpareName(jsonParam.get("sname").toString());
        spareDo.setPaperclip(jsonParam.get("info").toString());
        
        UserDo find_u = userDao.findByUsername(jsonParam.get("username").toString());
        if (find_u == null){
            JSONObject result = new JSONObject();
            result.put("status","-1");
            return result;
        }

        spareDo.setSpareUserId(find_u.getuId());
        spareDao.save(spareDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }  

    @UserLoginToken
    @RequestMapping(value = "/api_admin_create_spare", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_create_spare(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 400){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }
        
        UserDo find_u = userDao.findByUsername(jsonParam.get("username").toString());
        if (find_u == null){
            JSONObject result = new JSONObject();
            result.put("status","-1");
            return result;
        }

        jsonParam.put("spareUserId",find_u.getuId());
        SpareDo spareDo = new SpareDo();
        spareDo.creatSpare(jsonParam);

        spareDao.save(spareDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }  

    @UserLoginToken
    @RequestMapping(value = "/api_admin_change_area", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_change_area(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 400){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        AreaDo areaDo = areaDao.findByareaId(jsonParam.get("aid").toString());
        areaDo.setareaName(jsonParam.get("aname").toString());
        
        UserDo find_u = userDao.findByUsername(jsonParam.get("username").toString());
        if (find_u == null){
            JSONObject result = new JSONObject();
            result.put("status","-1");
            return result;
        }

        areaDo.setareaUserId(find_u.getuId());
        areaDao.save(areaDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    } 
    
    @UserLoginToken
    @RequestMapping(value = "/api_admin_create_area", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_create_area(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 400){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }
        
        UserDo find_u = userDao.findByUsername(jsonParam.get("username").toString());
        if (find_u == null){
            JSONObject result = new JSONObject();
            result.put("status","-1");
            return result;
        }

        
        jsonParam.put("spareId", "default");
        jsonParam.put("areaUserId",find_u.getuId());
        AreaDo areaDo = new AreaDo();
        areaDo.createArea(jsonParam);
        areaDao.save(areaDo);
        

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }  

    @UserLoginToken
    @RequestMapping(value = "/api_admin_delete_area", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_delete_area(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 400){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }
        
        AreaDo areaDo = areaDao.findByareaId(jsonParam.get("aid").toString());

        List<SpareDo> get_sp = spareDao.findBySubordinateAreaId(areaDo.getAreaId());
        for(SpareDo x:get_sp){
            List<BbsDo> get_bbs = bbsDao.findBySpareId(x.getSpareId());
            for(BbsDo y:get_bbs){
                receiveDao.delReceive_bbsId(y.getBbsId());
                bbsDao.delete(y);
            }
            spareDao.delete(x);
        }

        areaDao.delete(areaDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }  

    @UserLoginToken
    @RequestMapping(value = "/api_admin_delete_spare", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_delete_spare(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 400){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }
        
        SpareDo spareDo = spareDao.findByspareId(jsonParam.get("sid").toString());

        List<BbsDo> get_bbs = bbsDao.findBySpareId(spareDo.getSpareId());
        for(BbsDo y:get_bbs){
            receiveDao.delReceive_bbsId(y.getBbsId());
            bbsDao.delete(y);
        }


        spareDao.delete(spareDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }  

    @UserLoginToken
    @RequestMapping(value = "/api_admin_get_bbs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_get_bbs(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
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
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 200){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        List<JSONObject> data = new ArrayList<>();

        if(userType >= 400){
            List<BbsDo> get_bbs = bbsDao.getBbs(jsonParam.get("sid").toString(),(page-1)*onePage, onePage);
            for(BbsDo x:get_bbs){
                JSONObject one = new JSONObject();
                one.put("bid",x.getBbsId());
                one.put("title",x.getBbsTitle());
                one.put("username",userDao.findByUId(x.getSubmitUserId()).getUsername());
                one.put("sname",spareDao.findByspareId(x.getSpareId()).getSpareName());
                one.put("time",x.getSubmitTime());
                data.add(one);
            }
            JSONObject result = new JSONObject();
            result.put("status","1");
            result.put("data",data);
            result.put("page", page);
            result.put("onepage",onePage);
            return result;
        }

        if(userType >= 300){
            AreaDo areaDo = areaDao.findByareaId(spareDao.findByspareId(jsonParam.get("sid").toString()).getSpareId());
            if(areaDo.getareaUserId().equals(userDo.getuId())){
                List<BbsDo> get_bbs = bbsDao.getBbs(jsonParam.get("sid").toString(),(page-1)*onePage, onePage);
                for(BbsDo x:get_bbs){
                    JSONObject one = new JSONObject();
                    one.put("bid",x.getBbsId());
                    one.put("title",x.getBbsTitle());
                    one.put("username",userDao.findByUId(x.getSubmitUserId()).getUsername());
                    one.put("sname",spareDao.findByspareId(x.getSpareId()).getSpareName());
                    one.put("time",x.getSubmitTime());
                    data.add(one);
                }
                JSONObject result = new JSONObject();
                result.put("status","1");
                result.put("data",data);
                result.put("page", page);
                result.put("onepage",onePage);
                return result;
            }
        }

        if(userType >= 200){
            SpareDo spareDo = spareDao.findByspareId(jsonParam.get("sid").toString());

            if(!spareDo.getSpareUserId().equals(userDo.getuId())){
                JSONObject result = new JSONObject();
                result.put("status","0");
                response.sendRedirect("/admin_login");
                return result; 
            }

            List<BbsDo> get_bbs = bbsDao.getBbs(jsonParam.get("sid").toString(),(page-1)*onePage, onePage);
            for(BbsDo x:get_bbs){
                JSONObject one = new JSONObject();
                one.put("bid",x.getBbsId());
                one.put("title",x.getBbsTitle());
                one.put("username",userDao.findByUId(x.getSubmitUserId()).getUsername());
                one.put("sname",spareDao.findByspareId(x.getSpareId()).getSpareName());
                one.put("time",x.getSubmitTime());
                data.add(one);
            }
            JSONObject result = new JSONObject();
            result.put("status","1");
            result.put("data",data);
            result.put("page", page);
            result.put("onepage",onePage);
            return result;
        }

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        result.put("page", page);
        result.put("onepage",onePage);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_admin_get_bbs_number", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_get_bbs_number(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
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
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 200){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }


        if(userType >= 400){
            JSONObject result = new JSONObject();
            result.put("status","1");
            result.put("number",bbsDao.getBbsNumber(jsonParam.get("sid").toString()));
            result.put("page", page);
            result.put("onepage",onePage);
            return result;
        }

        if(userType >= 300){
            AreaDo areaDo = areaDao.findByareaId(spareDao.findByspareId(jsonParam.get("sid").toString()).getSpareId());
            if(areaDo.getareaUserId().equals(userDo.getuId())){
                JSONObject result = new JSONObject();
                result.put("status","1");
                result.put("number",bbsDao.getBbsNumber(jsonParam.get("sid").toString()));
                result.put("page", page);
                result.put("onepage",onePage);
                return result;
            }
        }

        if(userType >= 200){
            SpareDo spareDo = spareDao.findByspareId(jsonParam.get("sid").toString());

            if(!spareDo.getSpareUserId().equals(userDo.getuId())){
                JSONObject result = new JSONObject();
                result.put("status","0");
                response.sendRedirect("/admin_login");
                return result; 
            }

            JSONObject result = new JSONObject();
            result.put("status","1");
            result.put("page", page);
            result.put("onepage",onePage);
            result.put("number",bbsDao.getBbsNumber(jsonParam.get("sid").toString()));
            return result;
        }

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("page", page);
        result.put("onepage",onePage);
        result.put("number",0);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_admin_get_person", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_get_person(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
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
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 500){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }
        List<JSONObject> data = new ArrayList<>();

        List<UserDo> get_user = userDao.getAllUser((page-1)*onePage, onePage);
        for(UserDo x:get_user){
            JSONObject one_user = new JSONObject();
            one_user.put("username",x.getUsername());
            one_user.put("firsttime",x.getFirst_login_time());
            one_user.put("lasttime",x.getLast_login_time());
            one_user.put("type",x.getType());
            one_user.put("uid",x.getuId());
            data.add(one_user);
        }

        
        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        return result; 
    }

    @UserLoginToken
    @RequestMapping(value = "/api_admin_get_person_number", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_get_person_number(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
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
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 500){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("page", page);
        result.put("onepage",onePage);
        result.put("number",userDao.count());
        return result;
        
    }

    @UserLoginToken
    @RequestMapping(value = "/api_admin_change_person_type", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_change_person_type(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 500){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        UserDo get_user = userDao.findByUId(jsonParam.get("uid").toString());
        get_user.setType(jsonParam.get("type").toString());
        userDao.save(get_user);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
        
    }

    @UserLoginToken
    @RequestMapping(value = "/api_admin_inhibit_person", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_inhibit_person(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 500){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        UserDo get_user = userDao.findByUId(jsonParam.get("uid").toString());
        if(get_user.getType().equals("0")){
            get_user.setType("100");
        }else{
            get_user.setType("0");
        }
        
        userDao.save(get_user);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
        
    }

    
    @RequestMapping(value = "/api_admin_get_info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_get_info(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);


        InfoDo infoDo = infoDao.findAll().get(0);


        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("info",infoDo);
        return result;
        
    }

    @UserLoginToken
    @RequestMapping(value = "/api_admin_change_info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_change_info(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 500){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        InfoDo infoDo = infoDao.findAll().get(0);
        infoDo.setTitle(jsonParam.get("title").toString());
        infoDo.setContent(jsonParam.get("content").toString());

        infoDao.save(infoDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
        
    }

    @RequestMapping(value = "/api_admin_get_recommon", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_get_recommon(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        List<RecommonDo> get_re = recommonDao.findAll();
        List<JSONObject> data = new ArrayList<>();

        for(RecommonDo x:get_re){
            JSONObject one = new JSONObject();
            one.put("title",x.getTitle());
            one.put("content",x.getContent());
            one.put("pic",x.getPic());
            one.put("rcid",x.getRecommonId());
            data.add(one);
        }

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        return result;
        
    }

    @UserLoginToken
    @RequestMapping(value = "/api_admin_create_recommon", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_create_recommon(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 500){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        RecommonDo recommonDo = new RecommonDo();
        recommonDo.createRC(jsonParam);
        recommonDao.save(recommonDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
        
    }

    @UserLoginToken
    @RequestMapping(value = "/api_admin_change_recommon", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_change_recommon(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 500){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        RecommonDo recommonDo = recommonDao.findByRecommonId(jsonParam.get("rcid").toString());
        recommonDo.setTitle(jsonParam.get("title").toString());
        recommonDo.setContent(jsonParam.get("content").toString());
        recommonDo.setPic(jsonParam.get("pic").toString());
        recommonDao.save(recommonDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_admin_delete_recommon", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject admin_delete_recommon(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 500){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        RecommonDo recommonDo = recommonDao.findByRecommonId(jsonParam.get("rcid").toString());
        recommonDao.delete(recommonDo);

        JSONObject result = new JSONObject();
        result.put("status","1");
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/api_change_bbs_info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject change_bbs_info(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request) throws Exception{
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        
        String token = CookieUtils.getCookie(request, "sessionid");
        if(!checkToken.ifUsername(token)){
            JSONObject result = new JSONObject();
            result.put("status","-2");
            return result;
        }
        UserDo userDo = checkToken.getUserDo();
        int userType = Integer.parseInt(userDo.getType());
        if (userType < 200){
            JSONObject result = new JSONObject();
            result.put("status","0");
            response.sendRedirect("/admin_login");
            return result; 
        }

        String bid = jsonParam.get("bid").toString();
        String change_info = jsonParam.get("info").toString();
        BbsDo bbsDo = bbsDao.findBybbsId(bid);
        if(userType >= 400){
            if(change_info.equals("1")){
                try{
                    if(bbsDo.getIfTop().equals("1")){
                        bbsDo.setIfTop("");
                        bbsDao.save(bbsDo);
                    }else{
                        bbsDo.setIfTop("1");
                        bbsDao.save(bbsDo);
                    }
                }catch(Exception e){
                    bbsDo.setIfTop("1");
                    bbsDao.save(bbsDo);
                }
                JSONObject result = new JSONObject();
                result.put("status","1");
                return result;
            }
            if(change_info.equals("2")){
                try{
                    if(bbsDo.getIfGreat().equals("1")){
                        bbsDo.setIfGreat("");
                        bbsDao.save(bbsDo);
                    }else{
                        bbsDo.setIfGreat("1");
                        bbsDao.save(bbsDo);
                    }
                }catch(Exception e){
                    bbsDo.setIfGreat("1");
                    bbsDao.save(bbsDo);
                }
                JSONObject result = new JSONObject();
                result.put("status","1");
                return result;
            }
        }

        if(userType >= 300){
            AreaDo areaDo = areaDao.findByareaId(bbsDo.getAreaId());
            if(areaDo.getareaUserId().equals(userDo.getuId())){
                if(change_info.equals("1")){
                    try{
                        if(bbsDo.getIfTop().equals("1")){
                            bbsDo.setIfTop("");
                            bbsDao.save(bbsDo);
                        }else{
                            bbsDo.setIfTop("1");
                            bbsDao.save(bbsDo);
                        }
                    }catch(Exception e){
                        bbsDo.setIfTop("1");
                        bbsDao.save(bbsDo);
                    }
                    JSONObject result = new JSONObject();
                    result.put("status","1");
                    return result;
                }
                if(change_info.equals("2")){
                    try{
                        if(bbsDo.getIfGreat().equals("1")){
                            bbsDo.setIfGreat("");
                            bbsDao.save(bbsDo);
                        }else{
                            bbsDo.setIfGreat("1");
                            bbsDao.save(bbsDo);
                        }
                    }catch(Exception e){
                        bbsDo.setIfGreat("1");
                        bbsDao.save(bbsDo);
                    }
                    JSONObject result = new JSONObject();
                    result.put("status","1");
                    return result;
                }
            }
        }

        if(userType >= 200){
            SpareDo spareDo = spareDao.findByspareId(bbsDo.getSpareId());
            if(spareDo.getSpareUserId().equals(userDo.getuId())){
                if(change_info.equals("1")){
                    try{
                        if(bbsDo.getIfTop().equals("1")){
                            bbsDo.setIfTop("");
                            bbsDao.save(bbsDo);
                        }else{
                            bbsDo.setIfTop("1");
                            bbsDao.save(bbsDo);
                        }
                    }catch(Exception e){
                        bbsDo.setIfTop("1");
                        bbsDao.save(bbsDo);
                    }
                    JSONObject result = new JSONObject();
                    result.put("status","1");
                    return result;
                }
                if(change_info.equals("2")){
                    try{
                        if(bbsDo.getIfGreat().equals("1")){
                            bbsDo.setIfGreat("");
                            bbsDao.save(bbsDo);
                        }else{
                            bbsDo.setIfGreat("1");
                            bbsDao.save(bbsDo);
                        }
                    }catch(Exception e){
                        bbsDo.setIfGreat("1");
                        bbsDao.save(bbsDo);
                    }
                    JSONObject result = new JSONObject();
                    result.put("status","1");
                    return result;
                }
            }
        }

        JSONObject result = new JSONObject();
        result.put("status","0");
        result.put("message","你没有权限更改！");
        return result;
    }
}