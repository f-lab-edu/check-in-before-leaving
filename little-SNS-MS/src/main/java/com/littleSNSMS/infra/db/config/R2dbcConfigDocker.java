package com.littleSNSMS.infra.db.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@EnableR2dbcAuditing
@Configuration
@Profile("docker")
public class R2dbcConfigDocker {

    private static final String DB_DRIVER = "mysql";
    private static final String DB_HOST = "db";
    private static final int DB_PORT = 3306;
    private static final String INITIAL_DB_SCHEMA = "db/schema.sql";
    private static final String DROP_DB_SCHEMA = "db/schema-drop.sql";

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ConnectionFactoryInitializer initializer(R2dbcProperties r2dbcProperties) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        ConnectionFactory rootConnectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER, DB_DRIVER)
                .option(HOST, DB_HOST)
                .option(PORT, DB_PORT)
                .option(USER, r2dbcProperties.getUsername())
                .option(PASSWORD, r2dbcProperties.getPassword())
                .build());
        initializer.setConnectionFactory(rootConnectionFactory);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource(INITIAL_DB_SCHEMA));
        initializer.setDatabasePopulator(populator);

        ResourceDatabasePopulator cleaner = new ResourceDatabasePopulator();
        cleaner.addScript(new ClassPathResource(DROP_DB_SCHEMA));
        initializer.setDatabaseCleaner(cleaner);

        return initializer;
    }

    @Bean
    public ConnectionFactory connectionFactory(R2dbcProperties properties) {
        final String database = "sns";
        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER, DB_DRIVER)
                .option(HOST, DB_HOST)
                .option(PORT, DB_PORT)
                .option(USER, properties.getUsername())
                .option(PASSWORD, properties.getPassword())
                .option(DATABASE, database)
                .build());
    }
}
