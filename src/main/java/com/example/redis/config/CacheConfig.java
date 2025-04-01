package com.example.redis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheManager redisCacheManager(

            RedisConnectionFactory redisConnectionFactory
            ){
        //캐시 매니저가 사용할 설정 구성 먼저 진행
        //Redis를 이용해서 Spring Cache를 사용할 때 Redis 관련 설정을 모아두는 클래스
        RedisCacheConfiguration configuration = RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues() //null을 캐싱하는지
                .entryTtl(Duration.ofSeconds(10))//키본 캐시 유지 시간(Time To Live)
                .computePrefixWith(CacheKeyPrefix.simple())//캐시를 구분하는 접두사 설정
                .serializeValuesWith( //캐시에 저장할 값 어떻게 직열화/역직렬화 할 것인지
                        SerializationPair.fromSerializer(RedisSerializer.java())
                );
        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(configuration) //위의 설정을 기본 값으로 사용함
                .build();
    }
}
