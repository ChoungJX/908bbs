package com.demo.index.controllor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
/**
 * DebugControllor
 */
public class DebugControllor {

    @RequestMapping("/debug")
    public String debug(){
        return "debug";
    }
}