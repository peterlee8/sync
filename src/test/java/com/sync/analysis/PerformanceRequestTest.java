package com.sync.analysis;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author : peterlee
 * @Date :  2018/8/14 0014
 * @Discription :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceRequestTest {


    @Test
    public void getDetailByHostId() throws Exception {
        PerformanceRequest request = new PerformanceRequest();
        JSONObject jsonObject = request.getDetailByHostId("18943");
        System.out.println(jsonObject.toJSONString());
    }

}