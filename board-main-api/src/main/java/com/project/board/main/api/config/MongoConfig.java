package com.project.board.main.api.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private String port;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Bean
    public MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .applyToConnectionPoolSettings(builder ->
                        builder.maxSize(50)
                                .minSize(5)
                                .maxConnectionIdleTime(1, TimeUnit.MINUTES)
                                .maxConnectionLifeTime(5, TimeUnit.MINUTES))
                .applyConnectionString(new ConnectionString("mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + database))
                .build();
    }
}
