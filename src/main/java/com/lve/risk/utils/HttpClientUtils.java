package com.lve.risk.utils;

import com.lve.risk.exception.BusinessException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HttpClientUtils {
    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private  RequestConfig config;

    /**
     * 无参GET请求
     * @param url
     * @return
     */
    public String doGet(String url){
        //创建get请求
        HttpGet httpGet = new HttpGet(url);
        //设置配置信息
        httpGet.setConfig(config);
        //发送请求
        try {
            CloseableHttpResponse response = this.httpClient.execute(httpGet);
            // 判断状态码是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 返回响应体的内容
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("GET请求发送失败");
        }
        return null;
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, Object> map) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);

            if (!CollectionUtils.isEmpty(map)) {
                // 遍历map,拼接请求参数
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
                }
            }

            // 调用不带参数的get请求
            return this.doGet(uriBuilder.build().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new BusinessException("GET请求发送失败");
        }

    }


    /**
     * 带参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public String doPost(String url, Map<String, Object> map) {
        try {
            // 声明httpPost请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            // 加入配置信息
            httpPost.setConfig(config);

            // 判断map是否为空，不为空则进行遍历，封装from表单对象
            if (!CollectionUtils.isEmpty(map)) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                // 构造from表单对象
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

                // 把表单放到post里
                httpPost.setEntity(urlEncodedFormEntity);
            }

            // 发起请求
            CloseableHttpResponse response = this.httpClient.execute(httpPost);

            // 判断状态码是否为200
            if (response.getStatusLine().getStatusCode() == 401) {
                // 返回响应体的内容
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("POST请求发送失败");
        }
        return null;
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doPost(String url) {
        try {
            return this.doPost(url, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("POST请求发送失败");
        }
    }

    public String postSendMsg(String url, String params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity stringEntity = new StringEntity(params,"UTF-8");
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        return result;
    }
}
