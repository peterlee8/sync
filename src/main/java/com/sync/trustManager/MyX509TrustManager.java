package com.sync.trustManager;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 * @Author : peterlee
 * @Date :  2018/8/9 0009
 * @Discription :
 */
public class MyX509TrustManager implements X509TrustManager{

        /**
         *  检查客户端证书
         * @param chain
         * @param authType
         * @throws CertificateException
         */
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        /**
         * 检查服务器端证书
         * @param chain
         * @param authType
         * @throws CertificateException
         */
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        /**
         * 返回受信任的X509证书数组
         * @return
         */
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
}
