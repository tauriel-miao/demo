package com.tauriel.demo.util;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpConnectionManager4 {
	private static int connectimeOut = NumberUtils.toInt(PropertiesUtil.getStringValue("connectimeOut"),3)*1000;
	private static int readtimeOut = NumberUtils.toInt(PropertiesUtil.getStringValue("readtimeOut"),5)*1000;
	private static int pooltimeOut = NumberUtils.toInt(PropertiesUtil.getStringValue("pooltimeOut"),5)*1000;
	
	private static String keytoolPwd = PropertiesUtil.getStringValue("keytool.pwd");
	private static String keystorePath = PropertiesUtil.getStringValue("keyStorePath");
	private static String trustStorePath = PropertiesUtil.getStringValue("trustStorePath");
	
	private static boolean isIgnoreCertificate = PropertiesUtil.getBooleanValue("isIgnoreCertificate");
	
	private static boolean isProxy = PropertiesUtil.getBooleanValue("isProxy");
	private static String proxyHost = PropertiesUtil.getStringValue("proxyHost");
	private static int proxyPort = NumberUtils.toInt(PropertiesUtil.getStringValue("proxyPort"),80);
	
	private static int maxTotal = NumberUtils.toInt(PropertiesUtil.getStringValue("maxTotal"),800);
	private static int maxPerRoute = NumberUtils.toInt(PropertiesUtil.getStringValue("maxPerRoute"),100);
	
	private final static Object syncLock = new Object();
	private static PoolingHttpClientConnectionManager cm;// 创建连接池
	private static CloseableHttpClient httpClient;

	static {
		try {
			SSLContext sslcontext = null;
	    	try {
	    		if(isIgnoreCertificate){
	    			sslcontext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
	    		}else{
	    			sslcontext = HttpsPoster.getSSLContext(keytoolPwd, keystorePath, trustStorePath);
	    		}
	    		
			} catch (KeyManagementException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	SSLConnectionSocketFactory sslsf = isIgnoreCertificate?
	    			new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER):new SSLConnectionSocketFactory(sslcontext,HttpsPoster.hnv);
	    			
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", sslsf).build();

			cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			cm.setMaxTotal(maxTotal);
			cm.setDefaultMaxPerRoute(maxPerRoute);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	/** 
	 * 绕过验证 
	 *   
	 * @return 
	 * @throws NoSuchAlgorithmException  
	 * @throws KeyManagementException  
	 */
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {

			@Override
            public void checkClientTrusted(
								X509Certificate[] paramArrayOfX509Certificate,
								String paramString) throws CertificateException {
			}


			@Override
            public void checkServerTrusted(
								X509Certificate[] paramArrayOfX509Certificate,
								String paramString) throws CertificateException {
			}


			@Override
            public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[]{trustManager}, null);
		return sc;
	}

	/**
     * 获取HttpClient对象
     * 
     */
    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = createHttpClient();
                }
            }
        }
        return httpClient;
    }
	
	public static CloseableHttpClient createHttpClient() {
		RequestConfig requestConfig = null;
		if(isProxy){
    		HttpHost proxy = new HttpHost(proxyHost,proxyPort);
    		requestConfig = RequestConfig.custom().setConnectTimeout(connectimeOut).setConnectionRequestTimeout(pooltimeOut).setSocketTimeout(readtimeOut).setProxy(proxy).setCookieSpec(CookieSpecs.BEST_MATCH).build();//
		}else{
			requestConfig = RequestConfig.custom().setConnectTimeout(connectimeOut).setConnectionRequestTimeout(pooltimeOut).setSocketTimeout(readtimeOut).setCookieSpec(CookieSpecs.BEST_MATCH).build();//
		}
		// 请求重试处理
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			@Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 3) {// 如果已经重试了5次，就放弃
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					return false;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					return false;
				}
				if (exception instanceof SSLException) {// SSL握手异常
					return false;
				}

				HttpClientContext clientContext = HttpClientContext.adapt(context);
//				HttpRequest request = clientContext.getRequest();
//				// 如果请求是幂等的，就再次尝试
//				if (!(request instanceof HttpEntityEnclosingRequest)) {
//                    return true;
//                }
			return false;
			}
		};

		// 声明重定向策略对象
		LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
    	httpClient = HttpClients.custom()
    				.setConnectionManager(cm)
    				.setDefaultRequestConfig(requestConfig)
    				.setRedirectStrategy(redirectStrategy)
    				.setRetryHandler(httpRequestRetryHandler)
    				.build();
		return httpClient;
	}
	
	
	private static void setPostParams(HttpPost httpost,
            Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String post(String url, Map<String, Object> params) throws IOException {
        HttpPost httppost = new HttpPost(url);
        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        String result = "";
        try {
        	response = getHttpClient().execute(httppost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        }finally {
            try {
                if (response != null) {
                    response.close();
                }
                httppost.releaseConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

	public static void main(String[] args) throws IOException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userName", "ptsyb");
		paramMap.put("password","ptsyb");
		paramMap.put("apiCode","4000142");
		String url = "https://api.100credit.cn/bankServer2/user/login.action";
		String post = HttpConnectionManager4.post(url, paramMap);
		System.out.println(post);
    }

}
