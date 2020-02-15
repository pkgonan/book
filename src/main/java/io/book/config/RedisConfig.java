package io.book.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static io.lettuce.core.ReadFrom.REPLICA_PREFERRED;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceClientConfigurationBuilderCustomizer lettuceClientConfigurationBuilderCustomizer() {
        return builder -> builder
                .readFrom(REPLICA_PREFERRED)
                .commandTimeout(Duration.ofSeconds(1L))
                .clientOptions(getClientOptions());
    }

    private ClientOptions getClientOptions() {
        return ClusterClientOptions.builder()
                .topologyRefreshOptions(ClusterTopologyRefreshOptions.enabled())
                .autoReconnect(true)
                .timeoutOptions(TimeoutOptions.builder().fixedTimeout(Duration.ofSeconds(1L)).build())
                .build();
    }
}