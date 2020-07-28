package xmxrProject.genServer.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

public class C_HttpUtils {


    public static Map<String, Object> doGet(String url, Map<String, String> args) {
        Map<String, Object> result = null;
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 参数
        StringBuffer params = new StringBuffer();
        try {
            // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
            for (Map.Entry<String, String> entry : args.entrySet()) {
                params.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
            }
            params.append("ISDOGETEND=ISDOGETEND");
//            System.out.println(params);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        // 创建Get请求
        HttpGet httpGet = new HttpGet(url + "?" + params);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(15000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(15000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(15000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                String responseString = EntityUtils.toString(responseEntity);
//                System.out.println(responseString);
                result = JSON.parseObject(responseString);
//                System.out.println(result);
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param url      访问地址
     * @param JSONBody json 字符串
     * @param headers  header 映射
     * @return 返回结果
     * @throws Exception
     */
    public static String sendHttpPost(String url, String JSONBody, Map<String, String> headers) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        httpPost.setEntity(new StringEntity(JSONBody));
        CloseableHttpResponse response = httpClient.execute(httpPost);
//		System.out.println(response.getStatusLine().getStatusCode() + "\n");
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
//		System.out.println(responseContent);
        response.close();
        httpClient.close();
        return responseContent;
    }


    public static String requestOCRForHttp(String url, Map<String, String> requestParams, String filePathAndName)
            throws Exception {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        /** HttpPost */
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();// 建立多文件实例
        FileBody filebody = new FileBody(new File(filePathAndName));
        reqEntity.addPart("pic", filebody);// upload为请求后台的File upload;
        for (String key : requestParams.keySet()) {
            String value = requestParams.get(key);
            reqEntity.addPart(key, new StringBody(value, ContentType.TEXT_PLAIN.withCharset(Charset.forName("utf-8"))));
        }
        httpPost.setEntity(reqEntity.build()); // 设置实体
        /** HttpResponse */
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "utf-8");
            EntityUtils.consume(httpEntity);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
            }
        }
        return result;
    }

//    public void doGet(String... params) {
//        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        // 创建Get请求
//        HttpGet httpGet = new HttpGet("http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=");
//
//        // 响应模型
//        CloseableHttpResponse response = null;
//        try {
//            // 由客户端执行(发送)Get请求
//            response = httpClient.execute(httpGet);
//            // 从响应模型中获取响应实体
//            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应状态为:" + response.getStatusLine());
//            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // 释放资源
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

//    public void doGetTestWayTwo() {
//        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//        // 参数
//        URI uri = null;
//        try {
//            // 将参数放入键值对类NameValuePair中,再放入集合中
//            List<NameValuePair> params = new ArrayList<>();
//            params.add(new BasicNameValuePair("name", "&"));
//            params.add(new BasicNameValuePair("age", "18"));
//            // 设置uri信息,并将参数集合放入uri;
//            // 注:这里也支持一个键值对一个键值对地往里面放setParameter(String key, String value)
//            uri = new URIBuilder().setScheme("http").setHost("localhost")
//                    .setPort(12345).setPath("/doGetControllerTwo")
//                    .setParameters(params).build();
//        } catch (URISyntaxException e1) {
//            e1.printStackTrace();
//        }
//        // 创建Get请求
//        HttpGet httpGet = new HttpGet(uri);
//
//        // 响应模型
//        CloseableHttpResponse response = null;
//        try {
//            // 配置信息
//            RequestConfig requestConfig = RequestConfig.custom()
//                    // 设置连接超时时间(单位毫秒)
//                    .setConnectTimeout(5000)
//                    // 设置请求超时时间(单位毫秒)
//                    .setConnectionRequestTimeout(5000)
//                    // socket读写超时时间(单位毫秒)
//                    .setSocketTimeout(5000)
//                    // 设置是否允许重定向(默认为true)
//                    .setRedirectsEnabled(true).build();
//
//            // 将上面的配置信息 运用到这个Get请求里
//            httpGet.setConfig(requestConfig);
//
//            // 由客户端执行(发送)Get请求
//            response = httpClient.execute(httpGet);
//
//            // 从响应模型中获取响应实体
//            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应状态为:" + response.getStatusLine());
//            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // 释放资源
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
