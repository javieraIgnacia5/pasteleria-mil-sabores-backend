
package cl.milsabores.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pastelería Mil Sabores - API REST")
                        .version("1.0")
                        .description(
                                "API REST para la gestión de productos, " +
                                        "categorías, boletas y detalles de compra " +
                                        "de Pastelería Mil Sabores."
                        ));
    }
}
