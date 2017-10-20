package com.innovative.config;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
/**
 * JedisCluster配置
 * @author fzy
 * @date 2017/10/20
 */
@Configuration
@ConditionalOnClass({JedisCluster.class})
public class JedisClusterConfig {
	@Value("${spring.redis.cache.clusterNodes}")
	private String clusterNodes;
	@Value("${spring.redis.cache.commandTimeout}")
	private int commandTimeout;
	
	@Bean
	@Singleton
    public JedisCluster getJedisCluster() {
        String[] serverArray = clusterNodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String ipPort: serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(),Integer.valueOf(ipPortPair[1].trim())));
        }
        
        JedisCluster cluster=new JedisCluster(nodes, commandTimeout);
        return cluster;
    }
}
