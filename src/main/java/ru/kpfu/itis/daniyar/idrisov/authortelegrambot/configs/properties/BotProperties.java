package ru.kpfu.itis.daniyar.idrisov.authortelegrambot.configs.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "bot")
@PropertySource("classpath:application.yaml")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class BotProperties {

    String name;

    String token;
}
