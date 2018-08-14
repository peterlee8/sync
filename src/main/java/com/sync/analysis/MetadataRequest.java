package com.sync.analysis;

import com.alibaba.fastjson.JSONArray;
import com.sync.util.HttpUtil;
import org.apache.log4j.Logger;

/**
 * @Author : peterlee
 * @Date :  2018/8/14 0014
 * @Discription :
 */
public class MetadataRequest {
    private static Logger log = Logger.getLogger(MetadataRequest.class);
    //monitor 推送元数据
    private static final String POST_METADATA = "http://192.168.0.91/monitor/openapi/v2/metadata/metric/create?apikey=e10adc3949ba59abbe56e057f2gg88dd";

    /**
     * 为指标建立元数据
     * @param array
     */
    public void saveToMetadata(JSONArray array){
        try {
            String s= HttpUtil.httpsRequest(POST_METADATA,"POST",array.toJSONString());
        }catch (Exception e){
            log.error(String.format("同步数据到monitor元数据出错,list = %s",array.toJSONString()));
            e.printStackTrace();
        }
        log.info("同步数据到monitor元数据成功");
    }
}
