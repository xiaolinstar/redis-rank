package org.xiaolin.redis.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

/**
 * @author xlxing
 * @Description SwaggerUI默认配置信息
 * @create 2023/6/18
 */


@OpenAPIDefinition(
        info = @Info(
                title = "基于Redis-ZSet实现的排行榜",
                description = "后端请求接口",
                version = "1.0.0",
                contact = @Contact(name = "Xiaolin Xing", email = "xlxing@bupt.edu.cn"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")
        )
)
@Configuration
public class SwaggerConfig {
}
