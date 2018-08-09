package com.sync.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author : peterlee
 * @Date :  2018/8/9 0009
 * @Discription :
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HttpUtilTest {

    @Test
    public void get() throws Exception {
        String s = HttpUtil.get("http://www.baidu.com/", null);
        System.out.println(s);
    }

    @Test
    public void request() throws Exception {
        String s=HttpUtil.httpsRequest("https://kyfw.12306.cn/","GET",null);
        System.out.println(s);
    }

}