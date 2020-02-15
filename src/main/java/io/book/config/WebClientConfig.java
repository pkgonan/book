package io.book.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .clone()
                .clientConnector(clientConnector())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clone();
    }

    @Bean
    public ClientHttpConnector clientConnector() {
        return new ReactorClientHttpConnector(
                HttpClient.from(
                        TcpClient.create(connectionProvider())
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
                                .doOnConnected(connection -> connection
                                        .addHandlerLast(new ReadTimeoutHandler(2000, TimeUnit.MILLISECONDS))
                                        .addHandlerLast(new WriteTimeoutHandler(1000, TimeUnit.MILLISECONDS))
                                )
                )
        );
    }

    private ConnectionProvider connectionProvider() {
        return ConnectionProvider.fixed("webflux");
    }
}
