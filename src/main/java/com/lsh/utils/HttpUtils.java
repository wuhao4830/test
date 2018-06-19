package com.lsh.utils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.lsh.base.common.config.PropertyUtils;
import com.lsh.base.common.utils.RandomUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HTTP工具箱
 *
 * Created by wuHao on 16/11/5.
 */
public final class HttpUtils {
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 70000;
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);


    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        requestConfig = configBuilder.build();
    }



    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url                 请求的URL地址
     * @param queryString 请求的查询参数,可以为null
     * @param charset         字符集
     * @param pretty            是否美化
     * @return 返回请求响应的HTML
     */
    public static String doGet(String url, String queryString, String charset, boolean pretty) {
        logger.info("[POST][URI=" + url + "][request:" + queryString + "]");
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            if (!StringUtils.isBlank(queryString))
                //对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
                method.setQueryString(URIUtil.encodeQuery(queryString));
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty)
                        response.append(line).append(System.getProperty("line.separator"));
                    else
                        response.append(line);
                }
                reader.close();
            }
        } catch (URIException e) {
            logger.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
        } catch (IOException e) {
            logger.error("执行HTTP Get请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        logger.info("[GET][URI=" + url + "][response:" +response.toString() + "]");
        return response.toString();
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url         请求的URL地址
     * @param params    请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, Object> params) {
        logger.info("[POST][URI=" + url + "][request:" + JSON.toJSONString(params) + "]");
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        //设置Http Post数据
        if (params != null) {
            try {
                method.setRequestHeader("api-version","1");
                Long random = RandomUtils.genId();
                method.setRequestHeader("random",random.toString());
                method.setRequestHeader("sign",random.toString());
                method.setRequestHeader("platform","wms-openApi");
                method.setRequestEntity(new StringRequestEntity(JSON.toJSONString(params), "application/json", "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (true)
                    response.append(line).append(System.getProperty("line.separator"));
                else
                    response.append(line);
            }
            reader.close();
            }
        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        logger.info("[POST][URI=" + url + "][response:" + response.toString() + "]");
        return response.toString();
    }


    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url         请求的URL地址
     * @param params    请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doPostByForm(String url, Map<String, Object> params) {
        logger.info("[POST][URI=" + url + "][request:" + JSON.toJSONString(params) + "]");
        StringBuilder response = new StringBuilder();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        //设置Http Post数据
        if (params != null) {
            try {
                method.setRequestHeader("api-version","1");
                Long random = RandomUtils.genId();
                method.setRequestHeader("random",random.toString());
                method.setRequestHeader("sign",random.toString());
                method.setRequestHeader("platform","wms-openApi");
                method.setRequestEntity(new MultipartRequestEntity(getParts(params), method.getParams()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (true)
                    response.append(line).append(System.getProperty("line.separator"));
                else
                    response.append(line);
            }
            reader.close();
            }
        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        logger.info("[POST][URI=" + url + "][response:" + response.toString() + "]");

        return response.toString();
    }
    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) {
        return doPost(apiUrl, new HashMap<String, Object>());
    }
    /**
     * 发送 POST 请求（HTTP），JSON形式
     *            json对象
     * @return
     */
    public static String doPostToken(String apiUrl) {

        return doPost(apiUrl,"","");
    }
    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json
     *            json对象
     * @return
     */
    public static String doPost(String apiUrl, Object json) {
        String contentType = PropertyUtils.getString("wumart_contentType");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.addHeader("Content-Type",contentType);
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(json), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }
    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json json对象
     * @return
     */
    public static String doPost(String apiUrl, Object json,String token) {
        String contentType = PropertyUtils.getString("wumart_contentType");
        if(token != null && !token.equals("")){
            contentType += "gatewayToken=" + token + ";";
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.addHeader("Content-Type",contentType);

            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    public static Part[] getParts(Map<String,Object> params) {
        List<Part> partList = new ArrayList<Part>();
        for(String key:params.keySet()){
            partList.add(new StringPart(key,params.get(key).toString(),"UTF-8"));
        }
        int size = partList.size();
        return partList.toArray(new Part[size]);
    }

    public static void main(String[] args) {
        for(int i = 0 ;i<100 ;i++) {
            String y = doGet("https://www.baidu.com/s?ie=UTF-8&wd=linux%E6%97%B6%E9%97%B4%E6%88%B3", null, "UTF-8", false);
            System.out.print(y);
        }

        Map<String,String> params = new HashMap<String, String>();
        Map<String,Object> json = new HashMap<String, Object>();
        json.put("goodsNO","123313");
        json.put("goodsName","测试数据");
        json.put("unit","盒");
        json.put("temperatureType",1);
        params.put("json", JSON.toJSONString(json));
        params.put("userKey","lianshang");
        params.put("userValue", "c9832be12857363438bd2664ee203fb9");

    }
}
