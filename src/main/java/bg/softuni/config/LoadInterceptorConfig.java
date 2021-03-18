package bg.softuni.config;

import bg.softuni.components.HomePageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoadInterceptorConfig implements WebMvcConfigurer {
    private final HomePageInterceptor homePageInterceptor;

    public LoadInterceptorConfig(HomePageInterceptor homePageInterceptor) {
        this.homePageInterceptor = homePageInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.
                addInterceptor(homePageInterceptor).
                addPathPatterns("/home");
    }
}
