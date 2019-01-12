package com.nimble.sloth.dispatcher.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 27017;

    private static final String USERNAME_VARIABLE = "username";
    private static final String PASSWORD_VARIABLE = "password";

    @Bean
    public MongoClient mongoClient() {
        try {
            final String username = System.getenv(USERNAME_VARIABLE);
            final String password = System.getenv(PASSWORD_VARIABLE);
            final String uriString = String.format("mongodb://%s:%s@ds129914.mlab.com:29914/the-nimble-sloth-dispatcher", username, password);
            final MongoClientURI uri = new MongoClientURI(uriString);
            return new MongoClient(uri);
        } catch (Exception e) {
            return localhostClient();
        }
    }

    @Bean
    public MongoTemplate mongoTemplate(final @Autowired MongoClient client) {
        return new MongoTemplate(client, "the-nimble-sloth-dispatcher");
    }

    private MongoClient localhostClient() {
        return new MongoClient(DEFAULT_HOST, DEFAULT_PORT);
    }
}
