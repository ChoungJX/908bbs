package com.demo.index.controllor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.demo.index.dao.AreaDao;
import com.demo.index.dao.SpareDao;
import com.demo.index.domain.po.AreaDo;

import com.demo.index.util.IpUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AreaControllor {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private SpareDao spareDao;

    

    @RequestMapping(value = "/api_get_bbsinfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject sendMessage(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        


        JSONObject bbs_info = new JSONObject();
        List<AreaDo> areaDo = areaDao.findAll();
        for (AreaDo x:areaDo){
            bbs_info.put(x.getareaName(),spareDao.findBySubordinateAreaId(x.getAreaId()));
        }
        

        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("spare", bbs_info);
        result.put("area", areaDo);
        return result;
    }

    @RequestMapping(value = "/api_index_get_number2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject index_get_number2(@RequestBody JSONObject jsonParam,HttpServletResponse response, HttpServletRequest request){
        String get_ip = IpUtil.getIpAddr(request);
        jsonParam.put("ip",get_ip);

        List<JSONObject> data = new ArrayList<>();
        List<String> name = new ArrayList<>();

        List<JSONObject> get_data = spareDao.getHot2();
        for(JSONObject x:get_data){
            JSONObject one = new JSONObject();
            one.put("name",spareDao.findByspareId(x.get("spare_id").toString()).getSpareName());
            one.put("value",Integer.parseInt(x.get("allnumber").toString()));
            name.add(spareDao.findByspareId(x.get("spare_id").toString()).getSpareName());
            data.add(one);
        }


        JSONObject result = new JSONObject();
        result.put("status","1");
        result.put("data",data);
        result.put("name",name);
        return result;
    }
}