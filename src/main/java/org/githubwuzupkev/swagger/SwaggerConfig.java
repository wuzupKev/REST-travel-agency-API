package org.githubwuzupkev.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "travel-agency-manager-API",
                description = "allows the admin to manage transaction and info itself about the users and the offers",
                termsOfService = "https://kevin-chavarria-portfolio.netlify.app/",
                version = "1.0",
                contact = @Contact(name = "Kevin Chavarria",
                        url = "https://kevin-chavarria-portfolio.netlify.app/"
                        ,email = "kvnthehuman@gmail.com"),
                license = @License(name = "Standard user software by Kevin Chavarria",
                        url = "https://kevin-chavarria-portfolio.netlify.app/")),
        servers ={
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD SERVER",
                        url = "http://localhost:8080"
                )
        },
        security = @SecurityRequirement(
                name = "security token"
        )
)

@SecurityScheme(
        name = "security token",
        description = "acces api token",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in= SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
