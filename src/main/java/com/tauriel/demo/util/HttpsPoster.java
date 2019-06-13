package com.tauriel.demo.util;

import org.apache.commons.lang.math.NumberUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpsPoster {

  private static boolean isIgnoreCertificate = PropertiesUtil.getBooleanValue("isIgnoreCertificate");
  private static SSLContext sslContext;
  static HostnameVerifier hnv = new MyHostnameVerifier();
  
  /**
   * 获得KeyStore.
   * 
   * @param keyStorePath 密钥库路径
   * @param password 密码
   * @return 密钥库
   * @throws Exception
   */
  public static KeyStore getKeyStore(String password, String keyStorePath){
    // 实例化密钥库
	KeyStore ks = null;
	// 获得密钥库文件流
	InputStream is = null;
	try {
		ks = KeyStore.getInstance("JKS");
		is = HttpsPoster.class.getClassLoader().getResourceAsStream(keyStorePath);
		// 加载密钥库
		ks.load(is, password.toCharArray());
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		// 关闭密钥库文件流
		try {
			if(is!=null){
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    return ks;
  }

  /**
   * 获得SSLSocketFactory.
   * 
   * @param password 密码
   * @param keyStorePath 密钥库路径
   * @param trustStorePath 信任库路径
   * @return SSLSocketFactory
   * @throws Exception
   */
  public static SSLContext getSSLContext(String password, String keyStorePath,
      String trustStorePath) throws Exception {
    // 实例化密钥库
    KeyManagerFactory keyManagerFactory =
        KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    // 获得密钥库
    KeyStore keyStore = getKeyStore(password, keyStorePath);
    // 初始化密钥工厂
    keyManagerFactory.init(keyStore, password.toCharArray());

    // 实例化信任库
    TrustManagerFactory trustManagerFactory =
        TrustManagerFactory.getInstance(TrustManagerFactory
            .getDefaultAlgorithm());
    // 获得信任库
    KeyStore trustStore = getKeyStore(password, trustStorePath);
    // 初始化信任库
    trustManagerFactory.init(trustStore);
    // 实例化SSL上下文
    SSLContext ctx = SSLContext.getInstance("TLS");
    // 初始化SSL上下文
    ctx.init(keyManagerFactory.getKeyManagers(),
        trustManagerFactory.getTrustManagers(), null);
    // 获得SSLSocketFactory
    return ctx;
  }

  /**
   * 根据已有的信任库初始化HttpsURLConnection.
   * 
   * @param password 密码
   * @param keyStorePath 密钥库路径
   * @param trustStorePath 信任库路径
   * @throws Exception
   */
  public static void initHttpsURLConnection(String password,
      String keyStorePath, String trustStorePath) throws Exception {
    try {
      sslContext = getSSLContext(password, keyStorePath, trustStorePath);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    }
    if(isIgnoreCertificate){
    	X509TrustManager tm = new X509TrustManager() {
    		@Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
    		@Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
    		@Override
            public X509Certificate[] getAcceptedIssuers() {
    			return null;
    		}
    	};
    	sslContext.init(null, new TrustManager[] { tm }, null);
    }
    
  }

  /**
   * 发送http请求
   * @param url
   * @param param
   * @param isProxy
   * @return
   * @throws IOException
   */
  public static String sendPost(String url, String param,boolean isProxy) throws IOException {
	    HttpURLConnection conn = null;
	    OutputStream out = null;
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		try {
			URL url1 = new URL(null,url,new sun.net.www.protocol.http.Handler());
		    if(isProxy){
		    	InetSocketAddress addr = new InetSocketAddress(PropertiesUtil.getStringValue("proxyHost"),
		    			PropertiesUtil.getIntegerValue("proxyPort"));// 创建代理服务器
		    	Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
		    	conn = (HttpURLConnection) url1.openConnection(proxy);
		    }else{
		    	conn = (HttpURLConnection) url1.openConnection();
		    }
		    
		    conn.setConnectTimeout(NumberUtils.toInt(PropertiesUtil.getStringValue("connnectimeOut"),3)*1000);
		    conn.setReadTimeout(NumberUtils.toInt(PropertiesUtil.getStringValue("readtimeOut"),5)*1000);
		    
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Content-Length",
		        String.valueOf(param.getBytes().length));
		    
		    conn.setUseCaches(false);
		    // 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
		    out=conn.getOutputStream();
		    out.write(param.getBytes("UTF-8"));
			// flush输出流的缓冲
			out.flush();
			conn.getOutputStream().close();
			in =new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
			    sb.append(line);
			}
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			if(null!=out){
				out.close();
			}
			if(null!=in){
				in.close();
			}
			if(null!=conn){
				conn.disconnect();
			}
		}
		return sb.toString();
	}
  
  /**
   * 发送https请求
   * 
   * @param url 请求的地址
   * @param post_str 请求的数据
   * @return
   * @throws IOException
   * @throws Exception
   */
  public static String post(String url, String post_str,boolean isProxy) throws Exception {// url是post的目标，post_str是post的数据
	HttpsURLConnection urlCon = null;
	OutputStream out = null;
	BufferedReader in = null;
	StringBuffer sb = new StringBuffer();
	try{
	    URL url1 = new URL(null,url,new sun.net.www.protocol.https.Handler());
	    if(isProxy){
	    	InetSocketAddress addr = new InetSocketAddress(PropertiesUtil.getStringValue("proxyHost"),
	    			PropertiesUtil.getIntegerValue("proxyPort"));// 创建代理服务器
	    	Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
	    	urlCon = (HttpsURLConnection) url1.openConnection(proxy);
	    }else{
	    	urlCon = (HttpsURLConnection) url1.openConnection();
	    }
	    urlCon.setSSLSocketFactory(sslContext.getSocketFactory());
	    urlCon.setHostnameVerifier(hnv);
	    urlCon.setConnectTimeout(NumberUtils.toInt(PropertiesUtil.getStringValue("connnectimeOut"),3)*1000);
		urlCon.setReadTimeout(NumberUtils.toInt(PropertiesUtil.getStringValue("readtimeOut"),5)*1000);
	    urlCon.setDoInput(true);
	    urlCon.setDoOutput(true);
	    urlCon.setRequestMethod("POST");
	    urlCon.setRequestProperty("Content-Length",
	        String.valueOf(post_str.getBytes().length));
	    //setDefaultAuthentication();
	    urlCon.setUseCaches(false);
	    // 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
	    out=urlCon.getOutputStream();
	    out.write(post_str.getBytes("UTF-8"));
	    out.flush();
	    urlCon.getOutputStream().close();
	    in =new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "utf-8"));
	    String line;
	    while ((line = in.readLine()) != null) {
	      sb.append(line);
	    }
	}finally{
		if(null!=out){
			out.close();
		}
		if(null!=in){
			in.close();
		}
		if(null!=urlCon){
			urlCon.disconnect();
		}
	}
    return sb.toString();
  }
  
  /** 
   * 设置全局校验器对象 
   */  
  public static void setDefaultAuthentication() {  
	  MyAuthenticator auth = new MyAuthenticator("bairong","Proxy_br");
      Authenticator.setDefault(auth);  
  }  
  
  /**
   * 实现用于主机名验证的基接口。 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
   */
  static class MyHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
      // if ("localhost".equals(hostname)) {
      // return true;
      // } else {
      // return false;
      // }
      return true;
    }
  }

  /**
   * 
   * 保存信任证书
   * 
   */
  public static class SavingTrustManager implements X509TrustManager {
    private final X509TrustManager tm;
    private X509Certificate[] chain;

    public SavingTrustManager(X509TrustManager tm) {
      this.tm = tm;
    }

    public X509TrustManager getTM() {
      return tm;
    }

    public X509Certificate[] getChain() {
      return chain;
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      throw new UnsupportedOperationException();
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      this.chain = chain;
      tm.checkServerTrusted(chain, authType);
    }
  }

}
