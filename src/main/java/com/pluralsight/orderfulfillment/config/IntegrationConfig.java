package com.pluralsight.orderfulfillment.config;

import com.pluralsight.orderfulfillment.order.OrderStatus;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
public class IntegrationConfig extends CamelConfiguration {

    private static final String OUTBOUND_DIRECTORY = "order.fulfillment.center.1.outbound.folder";
    private static final String INBOUND_DIRECTORY = "order.fulfillment.center.1.inbound.folder";

    @Inject
    private DataSource dataSource;

    @Inject
    private Environment environment;

    @Bean(name = "sql")
    public SqlComponent sqlComponent() {
        SqlComponent sqlComponent = new SqlComponent();
        sqlComponent.setDataSource(dataSource);
        System.out.println(sqlComponent);
        return sqlComponent;
    }


    @Bean
    public RouteBuilder newWebSiteOrderRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("sql:" + "select id from orders.\"order\" where status = '" + OrderStatus.NEW.getCode() + "'" + "?"
                        + "consumer.onConsume=" + "update orders.\"order\" set status = '" + OrderStatus.PROCESSING.getCode()
                        + "' where id = :#id")
                        .to("log:com.pluralsight.orderfulfillemnt.order?level=INFO")
                        .log(LoggingLevel.INFO, "Consuming orders from a database");
            }
        };
    }

}
