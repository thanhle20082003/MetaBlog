package fullstack.website.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi customerApi(){
        return GroupedOpenApi.builder()
                .group("meta-blog")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Spring OpenAPI Meta Blog")
                        .description("API by OpenAPI with project Meta Blog")
                        .version("1.4.0"));
    }
}
