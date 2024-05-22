package moonduck.server.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = @Server(url = "/", description = "Default Server URL"),
        info = @Info(
                title = "문덕 백엔드 API 명세",
                description = "springdoc을 이용한 Swagger API 문서입니다.",
                version = "1.0",
                contact = @Contact(
                        name = "springdoc 공식문서",
                        url = "https://springdoc.org/"
                )
        )
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI();
    }
}