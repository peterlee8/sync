package com.sync.model;

import java.util.List;

/**
 * @Author : peterlee
 * @Date :  2018/8/10 0010
 * @Discription :
 */
public class PcServer {

    private String classCode;
    private List<String> sources;
    private String name;
    private String serialNumber ;
    private String host_status ;
    private String ip ;
    private String zhujiType ;
    private String hostServerIp ;

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getHost_status() {
        return host_status;
    }

    public void setHost_status(String host_status) {
        this.host_status = host_status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getZhujiType() {
        return zhujiType;
    }

    public void setZhujiType(String zhujiType) {
        this.zhujiType = zhujiType;
    }

    public String getHostServerIp() {
        return hostServerIp;
    }

    public void setHostServerIp(String hostServerIp) {
        this.hostServerIp = hostServerIp;
    }
}
