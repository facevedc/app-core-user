package com.app.core.user.setting;

import com.app.core.user.api.ping.PingHandler;
import com.app.core.user.api.types.TypesHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> pingRoute(PingHandler pingHandler) {
        return RouterFunctions
                .route(GET("/ping").and(accept(MediaType.APPLICATION_JSON)), pingHandler::health);
    }

    @Bean
    public RouterFunction<ServerResponse> typesRoute(TypesHandler typesHandler) {
        return RouterFunctions
                .route(GET("/{types}").and(accept(MediaType.APPLICATION_JSON)), typesHandler::find)
                .andRoute(POST("/{types}").and(accept(MediaType.APPLICATION_JSON)), typesHandler::insert)
                .andRoute(PUT("/{types}").and(accept(MediaType.APPLICATION_JSON)), typesHandler::update)
                .andRoute(DELETE("/{types}").and(accept(MediaType.APPLICATION_JSON)), typesHandler::delete);
    }


}
