package com.sync.analysis;

import com.sync.model.PcServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
        parseRequest.hostIds();
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