package com.mgreis.delivery.conf;

import org.ff4j.FF4j;
import org.ff4j.web.FF4jDispatcherServlet;
import org.ff4j.web.embedded.ConsoleServlet;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * Servlet configuration class for {@link FF4j}.
 *
 * @author Mario Pereira
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({ConsoleServlet.class, FF4jDispatcherServlet.class})
@AutoConfigureAfter(FF4jConfiguration.class)
public class FF4jServletConfig extends SpringBootServletInitializer {

    /**
     * Servlet registration class for the ff4j web console.
     * @param   ff4jDispatcherServlet an instance of {@link FF4jDispatcherServlet}
     * @return  a new instance of te {@link ServletRegistrationBean} class
     */
    @Bean
    public ServletRegistrationBean ff4jDispatcherServletRegistrationBean(
        final FF4jDispatcherServlet ff4jDispatcherServlet) {
        return new ServletRegistrationBean(ff4jDispatcherServlet, "/ff4j-web-console/*");
    }

    /**
     *  Servlet creation for {@link FF4jDispatcherServlet}.
     *
     * @param ff4j  an instance of the {@link FF4j} class.
     * @return an instance of the {@link FF4jDispatcherServlet}.
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public FF4jDispatcherServlet getFF4jDispatcherServlet(FF4j ff4j) {
        final FF4jDispatcherServlet ff4jDispatcherServlet = new FF4jDispatcherServlet();
        ff4jDispatcherServlet.setFf4j(ff4j);
        return ff4jDispatcherServlet;
    }
}
