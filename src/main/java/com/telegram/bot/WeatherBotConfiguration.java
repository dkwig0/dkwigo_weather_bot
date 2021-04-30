package com.telegram.bot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherBotConfiguration {

    @Bean(name = "bot_token")
    public String botToken() {
        return "1684776543:AAGvpYkTFtYpmRIOaoijekJUbKKnnD51gjc";
    }

    @Bean(name = "bot_username")
    public String botUsername() {
        return "dkwigo_weather_bot";
    }

}
