package org.practices.demo.client;


import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@Slf4j
public class HttpsTrust {
    public static SSLSocketFactory socketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new trustManager()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    public static class hostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static class trustManager implements X509TrustManager, TrustManager {
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public void readMetaData() {
        OkHttpClient client = null;
        try {
            client = new OkHttpClient.Builder()
                    .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
                    .sslSocketFactory(HttpsTrust.socketFactory(), new HttpsTrust.trustManager())
                    .hostnameVerifier(new HttpsTrust.hostnameVerifier())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        String url = "https://www.baidu.com";
        Request request = new Request.Builder().url(url).build();

        //CodeSketch.info(request.toString());

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            InputStream is = response.body().byteStream();
            int code = response.code();
            String body = response.body().string();
            log.info("response code:{}", code);
            log.info("response body:{}", body);
            /*CodeSketch.info(templateInfos.length); for (int i = 0; i < templateInfos.length; i++) { System.out.println(templateInfos[i]); }*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        HttpsTrust httpsTrust = new HttpsTrust();
        httpsTrust.readMetaData();
    }
}
