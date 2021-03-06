package com.Bhan.webserver;

import com.timgroup.statsd.NoOpStatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Value("${publish.metrics:true}")
    private boolean publishMetrics;
    @Value("${metrics.server.hostname:localhost}")
    private String metricsServerHost;
    @Value("${metrics.server.port:8125}")
    private int metricsServerPort;

    @Bean
    public StatsDClient metricsClient() {


        if (publishMetrics) {
            return new NonBlockingStatsDClient("csye6225", metricsServerHost, metricsServerPort);
        }

        return new NoOpStatsDClient();
    }
}
