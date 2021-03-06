package com.tfkfan.config;

import com.tfkfan.web.resolver.SoapExceptionResolver;
import com.tfkfan.webservices.types.*;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tech.jhipster.config.JHipsterProperties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    private final JHipsterProperties jHipsterProperties;

    public WebConfigurer(Environment env, JHipsterProperties jHipsterProperties) {
        this.env = env;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        log.info("Web application fully configured");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = jHipsterProperties.getCors();
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins()) || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v3/api-docs", config);
            source.registerCorsConfiguration("/swagger-ui/**", config);
        }
        return new CorsFilter(source);
    }

    @Bean
    public ServletRegistrationBean<CXFServlet> dispatcherServlet() {
        return new ServletRegistrationBean<>(new CXFServlet(), "/ws/v1/*");
    }

    @Bean
    @Primary
    public DispatcherServletPath dispatcherServletPathProvider() {
        return () -> "";
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        SpringBus cxfBus = new SpringBus();
        cxfBus.setProperty("org.apache.cxf.logging.FaultListener", new SoapExceptionResolver());
        return cxfBus;
    }

    @Bean
    public LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        return loggingFeature;
    }

    @Bean
    public Endpoint categoryServiceEndpointBean(Bus bus, CategoryServicePorts serviceEndpoint) {
        EndpointImpl endpoint = new EndpointImpl(bus, serviceEndpoint);

        final Service service = new CategoryService();

        endpoint.setServiceName(service.getServiceName());
        endpoint.setEndpointName(service.getPorts().next());
        endpoint.setWsdlLocation(service.getWSDLDocumentLocation().toString());
        endpoint.setProperties(getEndpointProps());
        endpoint.publish("/categoryservice");
        return endpoint;
    }

    @Bean
    public Endpoint productModelServiceEndpointBean(Bus bus, ProductModelServicePorts serviceEndpoint) {
        EndpointImpl endpoint = new EndpointImpl(bus, serviceEndpoint);

        final Service service = new ProductModelService();

        endpoint.setServiceName(service.getServiceName());
        endpoint.setEndpointName(service.getPorts().next());
        endpoint.setWsdlLocation(service.getWSDLDocumentLocation().toString());
        endpoint.setProperties(getEndpointProps());
        endpoint.publish("/modelservice");
        return endpoint;
    }

    @Bean
    public Endpoint categoryModelServiceEndpointBean(Bus bus, CategoryModelServicePorts serviceEndpoint) {
        EndpointImpl endpoint = new EndpointImpl(bus, serviceEndpoint);

        final Service service = new CategoryModelService();

        endpoint.setServiceName(service.getServiceName());
        endpoint.setEndpointName(service.getPorts().next());
        endpoint.setWsdlLocation(service.getWSDLDocumentLocation().toString());
        endpoint.setProperties(getEndpointProps());
        endpoint.publish("/categorymodelservice");
        return endpoint;
    }

    private Map<String, Object> getEndpointProps() {
        final Map<String, Object> props = new HashMap<>();
        //props.put("schema-validation-enabled", "REQUEST");    //?????????????????? ????????????????
        return props;
    }
}
