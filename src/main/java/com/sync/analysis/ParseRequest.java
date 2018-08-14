package com.sync.analysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sync.model.Monitor;
import com.sync.model.PcServer;
import com.sync.util.HttpUtil;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @Author : peterlee
 * @Date :  2018/8/10 0010
 * @Discription :
 */
public class ParseRequest {
    private static Logger log = Logger.getLogger(ParseRequest.class);
    //获取所有数据
    private static final String GET_URL = "https://mamingrui.top/broada/gethost1.json";
    private static final String GET = "GET";
    //同步到cmdb中数据
    private static final String POST_CMDB = "http://192.168.0.91/store/openapi/v2/resources/batch_save?apikey=e10adc3949ba59abbe56e057f2gg88dd";
    //同步到monitror数据
    private static final String POST_MONITOR = "http://116.62.244.181/monitor/openapi/v2/hosts/create?apikey=e10adc3949ba59abbe56e057f2gg88dd";



    /**
     * send https URL .
     * get all HOST_ID
     * @return  Set<String>
     */
    public JSONArray hostIds(){
        String s= HttpUtil.httpsRequest(GET_URL,GET,null);
        JSONObject jsonObject = (JSONObject) JSON.parse(s);
        JSONArray data =(JSONArray) jsonObject.get("data");
        //同步数据到cmdb
        CompletableFuture<Void> cmdbFuture = CompletableFuture.runAsync(() -> saveCmdb(data));
        //同步数据到monitor
        CompletableFuture<Void> monitorFuture = CompletableFuture.runAsync(() -> saveToMonitor(data));
        //阻塞等待
        CompletableFuture.allOf(cmdbFuture,monitorFuture).join();
        return data;
    }

    /**
     *
     * @param data
     */
    public void saveCmdb(JSONArray data){
        List<PcServer> list =new ArrayList<>();
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
        try {
            String post = HttpUtil.post(POST_CMDB, JSON.toJSONString(list));
        } catch (Exception e) {
            log.error(String.format("同步数据到cmdb出错,list = %s", JSON.toJSONString(list)));
            e.printStackTrace();
        }
    }

    /**
     * 同步数据到monitor出错
     * @param data
     */
    public void saveToMonitor(JSONArray data){
        List<Monitor> list =new ArrayList<>();
        data.forEach(d->{
            JSONObject dataJson = (JSONObject) d;
            Monitor monitor = new Monitor();
            monitor.setId(dataJson.getString("HOST_ID"));
            monitor.setIp(dataJson.getString("HOST_IP"));
            monitor.setName(dataJson.getString("HOST_NAME"));
            monitor.setType("Server");
            monitor.setOnline_state(dataJson.getString("HOST_STATUS"));
            list.add(monitor);
        });
        try {
            String post = HttpUtil.post(POST_MONITOR, JSON.toJSONString(list));
        } catch (Exception e) {
            log.error(String.format("同步数据到monitor出错,list = %s", JSON.toJSONString(list)));
            e.printStackTrace();
        }
    }
}
