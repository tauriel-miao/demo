package com.tauriel.demo.redis_demo.config;

import com.tauriel.demo.redis_demo.pub_sub.client.SubClient_2;
import com.tauriel.demo.redis_demo.pub_sub.sub.SubHandler_2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@ImportResource("classpath:spring-context.xml")
public class RedisConfiguration {

   @Bean
   public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory jedisConnectionFactory){
       RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
       redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory);
       redisMessageListenerContainer.afterPropertiesSet();
       return redisMessageListenerContainer;
   }

}
