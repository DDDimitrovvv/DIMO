package bg.softuni.config;

import bg.softuni.components.HomePageInterceptor;
import bg.softuni.components.StatisticsPageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoadInterceptorConfig implements WebMvcConfigurer {
    private final HomePageInterceptor homePageInterceptor;
    private final StatisticsPageInterceptor statisticsPageInterceptor;

    public LoadInterceptorConfig(HomePageInterceptor homePageInterceptor, StatisticsPageInterceptor statisticsPageInterceptor) {
        this.homePageInterceptor = homePageInterceptor;
        this.statisticsPageInterceptor = statisticsPageInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.
                addInterceptor(homePageInterceptor).
                addPathPatterns("/home");
        registry.
                addInterceptor(statisticsPageInterceptor).
                addPathPatterns("/admin/statistics");
    }
}
