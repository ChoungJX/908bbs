package com.demo.index.controllor;

import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.index.dao.TestDao;
import com.demo.index.domain.po.TestDo;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

//@RestController
@Controller
/**
 * IndexControllor
 */
public class TestControllor {
    @Autowired
    private TestDao testDao;

    @RequestMapping("/666")
    public String index(){
        TestDo aaa = new TestDo();
        aaa.setUSER_ID("111");
        aaa.setUsername("222");
        aaa.setPassword("password");
        testDao.save(aaa);
        return "index";
    }

}