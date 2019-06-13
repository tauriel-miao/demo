package com.tauriel.demo.es_sql_demo;

import com.floragunn.searchguard.SearchGuardPlugin;
import com.floragunn.searchguard.ssl.util.SSLConfigConstants;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import com.floragunn.searchguard.support.ConfigConstants;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;

public class EsApiTest {

			private static TransportClient client;

		    private final static String article="test_es_sql";
		    private final static String content="es_sql";

		    @Before
		    public void getClient() throws Exception{

				ClassLoader classLoader = EsApiTest.class.getClassLoader();
				URL resource = classLoader.getResource("kirk-keystore.jks");
				URL truresource = classLoader.getResource("truststore.jks");
				String keypath = URLDecoder.decode(resource.getPath(), "UTF-8");
				String trupath = URLDecoder.decode(truresource.getPath(), "UTF-8");

				//windows中路径会多个/ 如/E windows下需要打开注释
				if (keypath.startsWith("/")) {
					keypath=keypath.substring(1, keypath.length());
				}
				if (trupath.startsWith("/")) {
					trupath = trupath.substring(1, trupath.length());
				}

				//设置集群名称
		        Settings settings = Settings.builder().put("cluster.name", "searchguard_demo")
						.put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_ENABLED, true)
/*						.put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_KEYSTORE_FILEPATH, keypath)
						.put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_KEYSTORE_PASSWORD, "ks_password")
						.put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_TRUSTSTORE_FILEPATH, trupath)
						.put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_TRUSTSTORE_PASSWORD, "ts_password")
						.put(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_FILEPATH, keypath)
						.put(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_PASSWORD, "changeit")
						.put(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_FILEPATH, trupath)
						.put(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_PASSWORD, "changeit")
						.put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_PEMKEY_PASSWORD, "admin")*/
						.put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_ENFORCE_HOSTNAME_VERIFICATION, false)
		        		.put("client.transport.sniff", true).build();// 集群名
		        //创建client
				client = new PreBuiltTransportClient(settings, SearchGuardPlugin.class)
		                .addTransportAddress(new TransportAddress(InetAddress.getByName("172.16.160.167"), 9300));
				//client.admin().cluster().nodesInfo(new NodesInfoRequest()).actionGet();

		        System.out.println("123");

		    }


	/**
	 * 根据index、type、id进行查询
	 */
	@Test
	public void searchByIndex()  throws Exception{
		GetResponse response = client.prepareGet(article,content,"1")
				.execute().actionGet();
		String json = response.getSourceAsString();
		System.out.println("result : " + json);
	}

}
