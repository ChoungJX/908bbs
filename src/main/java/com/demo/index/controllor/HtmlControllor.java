package com.demo.index.controllor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletResponse;

import com.demo.index.util.CookieUtils;
import com.demo.index.util.annotation.UserLoginToken;

//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

//@RestController
@Controller
/**
 * IndexControllor
 */
public class HtmlControllor {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "sign";
    }

    @UserLoginToken
    @RequestMapping("/message")
    public String message(){
        return "message";
    }

    @UserLoginToken
    @RequestMapping("/one_message")
    public String oneMessage(){
        return "oneMessage";
    }

    @RequestMapping("/bbs_view")
    public String bbs(){
        return "bbs_view";
    }

    @RequestMapping("/one_bbs")
    public String oneBbs(){
        return "one_bbs";
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "success";
    }

    @UserLoginToken
    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        CookieUtils.delCookie(response, "sessionid");
        return "logout";
    }

    @UserLoginToken
    @GetMapping("/myPage")
    public String myPage(){
        return "myPage";
    }

    @UserLoginToken
    @GetMapping("/mySetting")
    public String mySetting(){
        return "mySetting";
    }


    @GetMapping("/admin_login")
    public String admin_login(){
        return "admin_login";
    }

    @UserLoginToken
    @GetMapping("/admin_index")
    public String admin_index(){
        return "admin_index";
    }

    @UserLoginToken
    @GetMapping("/admin_bbs_index")
    public String admin_bbs_index(){
        return "admin_bbs_index";
    }

    @UserLoginToken
    @GetMapping("/admin_area")
    public String admin_area(){
        return "admin_area";
    }

    @UserLoginToken
    @GetMapping("/admin_bbs_view")
    public String admin_bbs_view(){
        return "admin_bbs_view";
    }

    @UserLoginToken
    @GetMapping("/admin_person")
    public String admin_person(){
        return "admin_person";
    }

    @UserLoginToken
    @GetMapping("/admin_info_index")
    public String admin_info_index(){
        return "admin_info_index";
    }

    @UserLoginToken
    @GetMapping("/admin_info")
    public String admin_info(){
        return "admin_info";
    }

    @UserLoginToken
    @GetMapping("/admin_recommon")
    public String admin_recommon(){
        return "admin_recommon_index";
    }


    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @RequestMapping("/bbs_view_search")
    public String bbs_view_search(){
        return "bbs_view_search";
    }
}