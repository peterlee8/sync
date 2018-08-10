package com.sync.analysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sync.model.PcServer;
import com.sync.util.HttpUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : peterlee
 * @Date :  2018/8/10 0010
 * @Discription :
 */
public class ParseRequest {

    private static final String GET_URL = "https://mamingrui.top/broada/gethost1.json";
    private static final String GET = "GET";
    private static final String POST = "POST";
    //
    private static final String POST_URL = "http://192.168.0.91/store/openapi/v2/resources/batch_save?apikey=e10adc3949ba59abbe56e057f2gg88dd";

    /**
     * send https URL .
     * get all HOST_ID
     * @return  Set<String>
     */
    public List<PcServer> hostIds(){
        List<PcServer> list =new ArrayList<>();
        String s= HttpUtil.httpsRequest(GET_URL,GET,null);
        JSONObject jsonObject = (JSONObject) JSON.parse(s);
        JSONArray data =(JSONArray) jsonObject.get("data");
        data.forEach(d->{
            JSONObject dataJson = (JSONObject) d;
            PcServer pcServer = new PcServer();
            pcServer.setClassCode("PCServer");
            pcServer.setSources(Arrays.asList("user"));
            pcServer.setName(dataJson.getString("HOST_NAME"));
            pcServer.setSerialNumber(dataJson.getString("HOST_ID"));
            pcServer.setIp(dataJson.getString("HOST_IP"));
            pcServer.setHost_status(dataJson.getString("HOST_STATUS"));
            pcServer.setZhujiType(dataJson.getString("TYPE"));
            pcServer.setHostServerIp(dataJson.getString("HOSTSERVERIP"));
            list.add(pcServer);
        });
        return list;
    }

    public void savePcServer(List<PcServer> list){
        try {
            String post = HttpUtil.post(POST_URL, JSON.toJSONString(list));
            System.out.println(JSON.toJSONString(post));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test(Set<String> set){
        set.parallelStream().map(s->{
            System.out.println(s);
            return s;
        }).collect(Collectors.toList());
    }
}
