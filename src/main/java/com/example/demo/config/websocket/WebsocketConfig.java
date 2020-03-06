package com.example.demo.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Component
public class WebsocketConfig {
	 
	    /**
	     * ServerEndpointExporter 作用
	     *
	     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
	     *
	     * @return
	     */
	    @Bean
	    public ServerEndpointExporter serverEndpointExporter() {
	        return new ServerEndpointExporter();
	    }
}
