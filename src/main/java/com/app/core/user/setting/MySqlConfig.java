package com.app.core.user.setting;

import io.netty.util.internal.StringUtil;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Log4j2
@Configuration
public class MySqlConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.r2dbc.url}")
    private String url;

    @Value("${spring.r2dbc.username}")
    private String username;

    @Value("${spring.r2dbc.password}")
    private String password;

    @Override
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.parse(url);
        Builder builder = ConnectionFactoryOptions.builder()
                .from(options)
                .option(USER, findEnvUser())
                .option(PASSWORD, findEnvPassword());
        return ConnectionFactories.get(builder.build());
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        return initializer;
    }

    private String findEnvUser() {
        return StringUtil.isNullOrEmpty(username)
                ? System.getenv("mysql_username") : username;
    }

    private String findEnvPassword() {
        return StringUtil.isNullOrEmpty(password)
                ? System.getenv("mysql_password") : password;
    }
}
