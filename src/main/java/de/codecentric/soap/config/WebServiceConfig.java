package de.codecentric.soap.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cdyne.ws.weatherws.WeatherSoap;

import de.codecentric.soap.endpoint.WeatherServiceEndpoint;

@Configuration
public class WebServiceConfig {
	
	@Bean
    public ServletRegistrationBean dispatcherServlet() {
        CXFServlet cxfServlet = new CXFServlet();
        return new ServletRegistrationBean(cxfServlet, "/soap-api/*");
    }
    
    // If you don´t want to import the cxf.xml-Springbean-Config you have to setUp this Bus for yourself
    // <bean id="cxf" class="org.apache.cxf.bus.spring.SpringBus" destroy-method="shutdown"/>
    @Bean(name="cxf")
    public SpringBus springBus() {
    	return new SpringBus();
    }
    
    @Bean
    public WeatherSoap weatherService() {
    	return new WeatherServiceEndpoint();
    }
    
    @Bean
    public Endpoint endpoint() {
    	EndpointImpl endpoint = new EndpointImpl(springBus(), weatherService());
    	endpoint.publish("/WeatherSoapService");
    	//TODO: SetWSDL-Location properly
    	return endpoint;
    }
	
}
