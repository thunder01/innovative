package com.innovative.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfiguration implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {
	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfiguration.class);

	@Value("${spring.data.elasticsearch.cluster-nodes}")
	private String clusterNodes;
	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;
	private TransportClient transportClient;

	@Override
	public void destroy() throws Exception {
		try {
			logger.info("Closing elasticSearch client");
			if (transportClient != null) {
				transportClient.close();
			}
		} catch (final Exception e) {
			logger.error("Error closing ElasticSearch client: ", e);
		}
	}

	@Override
	public TransportClient getObject() throws Exception {
		return transportClient;
	}

	@Override
	public Class<TransportClient> getObjectType() {
		return TransportClient.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		buildClient();
	}

	protected void buildClient() {
		try {
			logger.info("初始化开始"+clusterName);
			@SuppressWarnings("resource")
			PreBuiltXPackTransportClient preBuiltXPackTransportClient = new PreBuiltXPackTransportClient(settings());

			if (!"".equals(clusterNodes)){
                for (String nodes:clusterNodes.split(",")) {
                    String InetSocket [] = nodes.split(":");
                    String  Address = InetSocket[0];
                    Integer  port = Integer.valueOf(InetSocket[1]);
                    preBuiltXPackTransportClient.addTransportAddress(new
                                     InetSocketTransportAddress(InetAddress.getByName(Address),port ));
                    logger.info("node地址"+Address+":"+port);
                }
                transportClient=preBuiltXPackTransportClient;
                logger.info("transportclient生成完毕");
			}
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
		
	}

	/**
	 * 初始化默认的client
	 */
	private Settings settings() {
		Settings settings = Settings.builder()
	            .put("cluster.name",clusterName) 
	            //自动嗅探集群中的其它机器
	            .put("client.transport.sniff",true)
	            .put("xpack.security.user", "elastic:changeme")
	            .build();
	    return settings;
	}
}