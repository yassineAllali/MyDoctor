package com.mydoctor.presentation.controller.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("yassine.elallali.dev@gmail.com");
        contact.setName("EL Allali Yassine");

//        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("My Doctor Api")
                .version("1.0")
                .contact(contact)
                .description("My Doctor Api.")
                .termsOfService("google.com");
//                .license(mitLicense);

        return new OpenAPI().info(info);

    }
}