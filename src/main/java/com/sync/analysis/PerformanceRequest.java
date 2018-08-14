package com.sync.analysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sync.util.HttpUtil;
import org.apache.log4j.Logger;

import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.PropertyDescriptor.GET;

/**
 * @Author : peterlee
 * @Date :  2018/8/14 0014
 * @Discription :
 */
public class PerformanceRequest {
    private static Logger log = Logger.getLogger(PerformanceRequest.class);
    //获取所有数据
    private static final String GET_URL = "https://mamingrui.top/broada/gethost1.json";
    //根据host_id 获取对应的详细参数
    private static final String GET_DETAIL = "https://mamingrui.top/AppNodeService/zhyw/api/getItemsValue?HOST_ID=%s";
    //现场-》根据host_id 获取对应的详细参数
    //private static final String GET_DETAIL = "https://mamingrui.top/AppNodeService/zhyw/api/getItemsValue?param={HOST_ID:+%s}";
    //monitor推送指标
    private static final String POST_DETAIL = "http://10.20.67.178/monitor/openapi/v2/datapoints/create?apikey=e10adc3949ba59abbe56e057f2gg88dd";

    /**
     * send https URL .
     * get all HOST_ID
     * @return  Set<String>
     */
    public JSONArray listAndSave (){
        String s= HttpUtil.httpsRequest(GET_URL,"GET",null);
        JSONObject jsonObject = (JSONObject) JSON.parse(s);
        JSONArray data =(JSONArray) jsonObject.get("data");
       data.parallelStream().map( o ->{
           JSONObject json = (JSONObject) o;
           String hostId =json.getString("HOST_ID");
           //根据host_id 获取对应的详细参数
           JSONObject object = getDetailByHostId(hostId);
           //推数据到monitor
           saveToMonitor(object);
           return o;
       }).collect(Collectors.toList());
        return data;
    }

    /**
     * 根据host_id 获取对应的详细参数
     * @param hostId
     * @return
     */
    public JSONObject getDetailByHostId(String hostId){
        String s= HttpUtil.httpsRequest(String.format(GET_DETAIL, hostId),"GET",null);
        return JSON.parseObject(s);
    }

    /**
     *  monitor推送指标
     * @param object
     */
    public void saveToMonitor(JSONObject object){
        JSONObject param = new JSONObject();
        param.put("host_id",object.getString("HOST_ID"));
        param.put("metric",object.getString("metric"));
        param.put("value",object.getString("value"));
        param.put("timestamp",object.getString("timestamp"));
        try {
            HttpUtil.post(POST_DETAIL,param.toJSONString());
        } catch (Exception e) {
            log.error(String.format("同步数据到monitor元数据出错,object = %s",object.toJSONString()));
            e.printStackTrace();
        }
    }
}
