package com.demo.index.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * What_time
 */
public class What_time {

    private String time_string;

    public String getTime_string() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time_string = df.format(new Date());
        return time_string;
    }
}