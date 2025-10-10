package com.project.board.main.api.config;

import com.mongodb.MongoClientSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {
    @Bean
    public MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .applyToConnectionPoolSettings(builder ->
                        builder.maxSize(50)
                                .minSize(5)
                                .maxConnectionIdleTime(1, TimeUnit.MINUTES)
                                .maxConnectionLifeTime(5, TimeUnit.MINUTES)
                ).build();
    }
}
