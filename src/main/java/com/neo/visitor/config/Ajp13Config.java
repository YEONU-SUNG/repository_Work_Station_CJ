package com.neo.visitor.config;

import java.net.InetAddress;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.apache.coyote.ajp.AjpNioProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Ajp13Config {

    // @Bean
   // public ServletWebServerFactory servletContainer() {
   //    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
   //    tomcat.addAdditionalTomcatConnectors(createAjpConnector());
   //    return tomcat;
   // }

   // private Connector createAjpConnector() {
    //     Connector ajpConnector = new Connector("AJP/1.3");
    //     AjpNioProtocol protocol= (AjpNioProtocol)ajpConnector.getProtocolHandler();
    //     protocol.setSecret("myapjsecret");
   //    ajpConnector.setPort(18010);
   //    ajpConnector.setSecure(false);
   //    ajpConnector.setAllowTrace(false);
    //     ajpConnector.setScheme("http");
        
   //    return ajpConnector;
    // }
    
    // @Bean
    // public TomcatServletWebServerFactory servletContainer() {
    //     TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    //     Connector ajpConnector = new Connector("org.apache.coyote.ajp.AjpNioProtocol");
    //     AjpNioProtocol protocol= (AjpNioProtocol)ajpConnector.getProtocolHandler();
    //     protocol.setSecret("myapjsecret");
    //     ajpConnector.setPort(8080);
    //     ajpConnector.setSecure(true);
    //     tomcat.addAdditionalTomcatConnectors(ajpConnector);
    //     return tomcat;
    // }

    // @Bean
    // public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
    //   return server -> {
    //     if (server instanceof TomcatServletWebServerFactory) {
    //         ((TomcatServletWebServerFactory) server).addAdditionalTomcatConnectors(redirectConnector());
    //     }
    //   };
    // }

    // private Connector redirectConnector() {
    //    Connector connector = new Connector("AJP/1.3");
    //    connector.setScheme("http");
    //    connector.setPort(8080);
    //    connector.setSecure(false);
    //    connector.setAllowTrace(false);
    //    return connector;
    // }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        // 톰켓 포트
        tomcat.setPort(18080);
        Connector ajpConnector = new Connector("AJP/1.3");
        // apache 와 통신할 포트
        ajpConnector.setPort(8182);
        ajpConnector.setSecure(false);
        ajpConnector.setAllowTrace(false);
        ajpConnector.setScheme("http");
        ((AbstractAjpProtocol<?>) ajpConnector.getProtocolHandler()).setSecretRequired(false);
        ajpConnector.setParseBodyMethods("POST,PUT,DELETE");
        tomcat.addAdditionalTomcatConnectors(ajpConnector);
        tomcat.addConnectorCustomizers(connector -> connector.setParseBodyMethods("POST,PUT,DELETE"));
        return tomcat;
    }
}