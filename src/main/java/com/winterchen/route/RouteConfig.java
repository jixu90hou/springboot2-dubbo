package com.winterchen.route;


import com.winterchen.handler.PostHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

@Configuration
@EnableWebFlux
@SpringBootApplication
public class RouteConfig implements CommandLineRunner {
    @Autowired
    private PostHandler postHandler;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        System.out.println("==============================================");

        return route(GET("/posts"), postHandler::all)
                .andRoute(POST("/posts").and(contentType(APPLICATION_JSON)), postHandler::create)
                .andRoute(GET("/posts/{id}"), postHandler::get)
                .andRoute(GET("/posts/list/{id}"), postHandler::list)
                .andRoute(PUT("/posts/{id}"), postHandler::update)    //默认参数为application/json
                .andRoute(DELETE("/posts/{id}"), postHandler::delete)
                .andRoute(GET("/user/findAllUsers"),postHandler::findAllUsers);
    }
    @Override
    public void run(String... args)  {
        RouterFunction<ServerResponse> router = routes();
        RouterFunction<ServerResponse> router2 = webFlux()
                .andRoute(GET("/user/findAllUsers"),postHandler::findAllUsers);

        // 转化为通用的Reactive HttpHandler
        HttpHandler httpHandler = toHttpHandler(router);
        HttpHandler httpHandler2 = toHttpHandler(router2);
        // 适配成Netty Server所需的Handler
        ReactorHttpHandlerAdapter httpAdapter = new ReactorHttpHandlerAdapter(httpHandler2);
        // 创建Netty Server
        HttpServer server = HttpServer.create("localhost", 8081);
        System.out.println("===========reactor is run=========");
        // 注册Handler并启动Netty Server
        server.newHandler(httpAdapter).block();
    }
    @Bean
    public RouterFunction<ServerResponse> webFlux() {
        return RouterFunctions.route(RequestPredicates.GET("/webFlux"), request -> {
            Mono<String> str = Mono.just("Hello World-f").delayElement(Duration.ofMillis(10));
            return ServerResponse.ok().body(str, String.class);
        });
    }
}