package com.sync.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author : peterlee
 * @Date :  2018/8/9 0009
 * @Discription :
 */
//@Configuration
//@EnableScheduling
public class TaskDemo {

    //表示每2秒执行一次
//    @Scheduled(cron = "0/2 * * * * *")
    public void timer(){
        //获取当前时间
        LocalDateTime localDateTime =LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        JSONObject parse = JSON.parseObject("{\n" +
                "        \"processlist\": [\n" +
                "            {\n" +
                "                \"systemid\": 8215,\n" +
                "                \"hostid\": 32769,\n" +
                "                \"entityid\": 4336,\n" +
                "                \"status\": 0,\n" +
                "                \"type\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"systemid\": 8215,\n" +
                "                \"hostid\": 32769,\n" +
                "                \"entityid\": 4351,\n" +
                "                \"status\": 0,\n" +
                "                \"type\": 0\n" +
                "            }\n" +
                "        ],\n" +
                "        \"groupid\": \"1\",\n" +
                "        \"groupname\": \"xx\"\n" +
                "    }");

        System.out.println(parse);
        JSONArray jsonArray = parse.getJSONArray("processlist");
        jsonArray.forEach(j->{
            JSONObject object = (JSONObject) j;
            System.out.println( "entityid:"+object.get("entityid"));
        });


    }


  /*  表2下面给出一些完整的Cron表示式的实例：
     CRON表达式    含义
    "0 0 12 * * ?"    每天中午十二点触发
    "0 15 10 ? * *"    每天早上10：15触发
    "0 15 10 * * ?"    每天早上10：15触发
    "0 15 10 * * ? *"    每天早上10：15触发
    "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
    "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
    "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
    "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
    "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
    "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
    "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发*/
}
