package com.sync.util;

import com.sync.trustManager.MyX509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author : peterlee
 * @Date :  2018/8/9 0009
 * @Discription :
 */
public class HttpUtil {
    private static Logger log = Logger.getLogger(HttpUtil.class);

    /**
     * post请求（用于请求json格式的参数）
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, String params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK || state ==HttpStatus.SC_NO_CONTENT) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null){
                String jsonString = EntityUtils.toString(responseEntity);
                    return jsonString;
                }else{
                    return null;
                }
            }
            else{
                log.error("请求返回:"+state+"("+url+")");
            }
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 发送 get请求
     */
    public static String get(String url, Map<String, String> headerMap) {
        //HttpClient4.5.2请求时出现Cookie rejected警告的解决方法
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        HttpClientBuilder globalBuilder = HttpClients.custom().setDefaultRequestConfig(globalConfig);
        //CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();

        // 创建默认的httpClient实例.
        //CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpClient httpclient = globalBuilder.build();

        String body = null;

        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);

            //设置头部信息
            if(headerMap != null){
                Iterator it = headerMap.entrySet().iterator();
                while (it.hasNext()) {
                    String key;
                    String value;
                    Map.Entry entry = (Map.Entry) it.next();
                    key = entry.getKey().toString();
                    value = entry.getValue().toString();
                    httpget.setHeader(key, value);
                    //log.info(key+"="+value);
                }
            }

            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                log.info("statusCode:" + statusCode);
                if (statusCode != HttpStatus.SC_OK) {
                    log.error("Method failed:" + response.getStatusLine());
                }

                // Read the response body
                body = EntityUtils.toString(entity, "UTF-8");

//          if (entity != null) {
//             log.info("Response content: " + body);
//          }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                //e.printStackTrace();
                log.error(e.toString());
            }
        }
        return body;
    }

    /**
     * 处理https GET/POST请求
     * 请求地址、请求方法、参数
     */
    public static String httpsRequest(String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer=null;
        try{
            //创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            TrustManager[] tm={new MyX509TrustManager()};
            //初始化
            sslContext.init(null, tm, new java.security.SecureRandom());
            //获取SSLSocketFactory对象
            SSLSocketFactory ssf=sslContext.getSocketFactory();

            HostnameVerifier hv = (urlHostName, session) -> {
                log.info("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
                return true;
            };

            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            URL url=new URL(requestUrl);
            HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            //设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            //往服务器端写内容
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }
            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

}
