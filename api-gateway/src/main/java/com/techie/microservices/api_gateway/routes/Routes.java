package com.techie.microservices.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute(){
        return GatewayRouterFunctions.route("product_service")
                .route(RequestPredicates.path("/api/product"), HandlerFunctions.http("http://192.168.88.251:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute(){
        return GatewayRouterFunctions.route("order_service")
                .route(RequestPredicates.path("/api/order"),HandlerFunctions.http("http://192.168.88.251:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryRoute(){
        return GatewayRouterFunctions.route("inventory_service")
                .route(RequestPredicates.path("/api/inventory/**"),HandlerFunctions.http("http://192.168.88.251:8082"))
                .build();
    }
}
