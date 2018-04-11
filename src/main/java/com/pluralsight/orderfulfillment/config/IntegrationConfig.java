package com.pluralsight.orderfulfillment.config;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class IntegrationConfig extends CamelConfiguration {

    private static final String OUTBOUND_DIRECTORY = "order.fulfillment.center.1.outbound.folder";
    private static final String INBOUND_DIRECTORY = "order.fulfillment.center.1.inbound.folder";

    @Inject
    private Environment environment;

    @Inject
    private CamelContext camelContext;

    @Override
    public List<RouteBuilder> routes() {
        List<RouteBuilder> routeList = new ArrayList<>();
        routeList.add(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://" + environment.getProperty(OUTBOUND_DIRECTORY) + "?noop=false")
                        .to("file://" + environment.getProperty(INBOUND_DIRECTORY) + "/test");
            }
        });

        return routeList;
    }

    @PostConstruct
    public void setup() {
        camelContext.setTracing(true);
    }

}
