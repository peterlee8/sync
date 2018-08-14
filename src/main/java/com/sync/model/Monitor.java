package com.sync.model;

/**
 * @Author : peterlee
 * @Date :  2018/8/14 0014
 * @Discription :
 */
public class Monitor {

    private String id;
    private String name;
    private String ip;
    private String type;
    private String online_state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOnline_state() {
        return online_state;
    }

    public void setOnline_state(String online_state) {
        this.online_state = online_state;
    }
}
