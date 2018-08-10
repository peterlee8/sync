package com.sync.analysis;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.regexp.internal.RE;
import com.sync.model.PcServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @Author : peterlee
 * @Date :  2018/8/10 0010
 * @Discription :
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ParseRequestTest {

    @Test
    public void parse() throws Exception {
        ParseRequest parseRequest =new ParseRequest();
        List<PcServer> pcServers = parseRequest.hostIds();
        parseRequest.savePcServer(pcServers);
        //System.out.println(JSON.toJSONString(pcServers));
    }

    @Test
    public void test() throws Exception {
        ParseRequest parseRequest =new ParseRequest();

    }

    private String append( String s){
        return " i m :"+s;
    }


}