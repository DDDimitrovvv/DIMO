package bg.softuni.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HomePageInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageInterceptor.class);

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long startTime = System.currentTimeMillis();

        LOGGER.info("***************************************");
        LOGGER.info("-------- HomePageInterceptor.preHandle --- ");
        LOGGER.info("Request URL: " + request.getRequestURL());
        LOGGER.info("Start Time: " + System.currentTimeMillis());

        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        LOGGER.info("-------- HomePageInterceptor.postHandle --- ");
        LOGGER.info("Request URL: " + request.getRequestURL());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {

        LOGGER.info("-------- HomePageInterceptor.afterCompletion --- ");

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();

        LOGGER.info("Request URL: " + request.getRequestURL());
        LOGGER.info("End Time: " + endTime);
        LOGGER.info("Time Taken: " + (endTime - startTime));
        LOGGER.info("***************************************");
    }
}
