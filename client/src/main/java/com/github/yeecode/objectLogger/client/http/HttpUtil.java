package com.github.yeecode.objectLogger.client.http;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public final class HttpUtil {
    private HttpUtil() {

    }

    private static final Log LOGGER = LogFactory.getLog(HttpUtil.class);


    public static synchronized void sendLog(String jsonString, String baseUrl) {
        try {
            List<NameValuePair> paramsList = new ArrayList<>();
            paramsList.add(new BasicNameValuePair("logJsonString", jsonString));
            sendPost(baseUrl + "/ObjectLogger/log/add", paramsList);
        } catch (Exception ex) {
            LOGGER.error("sendLog error!", ex);
        }
    }

    private static synchronized String sendPost(String url, List<NameValuePair> nameValuePairList) throws Exception {
        CloseableHttpResponse response = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            post.setEntity(entity);
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            LOGGER.error("send post error", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
}
