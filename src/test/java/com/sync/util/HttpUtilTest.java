package com.sync.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
        String s=HttpUtil.httpsRequest("https://mamingrui.top/broada/gethost1.json","GET",null);
        JSONObject jsonObject = (JSONObject) JSON.parse(s);
        System.out.println(jsonObject.getString("success"));
        JSONArray data =(JSONArray) jsonObject.get("data");
        data.forEach(d->{
            JSONObject dataJson = (JSONObject) d;
            System.out.println(dataJson.getString("HOST_ID"));
        });
    }

}