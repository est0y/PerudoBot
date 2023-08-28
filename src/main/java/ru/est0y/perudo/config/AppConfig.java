package ru.est0y.perudo.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.est0y.perudo.config.props.PlayerProps;

@EnableConfigurationProperties(PlayerProps.class)
@Configuration
public class AppConfig {
}
