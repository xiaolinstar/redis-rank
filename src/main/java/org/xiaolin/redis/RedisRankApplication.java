package org.xiaolin.redis;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xlxing
 */
@SpringBootApplication
@MapperScan("org.xiaolin.redis.mapper")
public class RedisRankApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisRankApplication.class, args);
    }

}
