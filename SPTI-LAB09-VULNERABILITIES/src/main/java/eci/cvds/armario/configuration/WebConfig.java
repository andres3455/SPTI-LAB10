package eci.cvds.armario.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthInterceptor basicAuthInterceptor;

    @Autowired
    public WebConfig(AuthInterceptor basicAuthInterceptor) {

        this.basicAuthInterceptor = basicAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/prendas")
                .addPathPatterns("/login/eliminarSesiones");
    }

}
