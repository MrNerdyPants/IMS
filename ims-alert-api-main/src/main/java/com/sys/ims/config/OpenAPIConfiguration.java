package com.sys.ims.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "IMS", version = "1.0", description = "IMS CRM API documentation")
)
public class OpenAPIConfiguration {

}
