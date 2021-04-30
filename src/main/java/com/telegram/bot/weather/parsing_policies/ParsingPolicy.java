package com.telegram.bot.weather.parsing_policies;

import com.fasterxml.jackson.databind.JsonNode;
import com.telegram.bot.weather.languages.Language;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ParsingPolicy {

    public SendMessage parse(JsonNode json, String cityName, Language language, Environment environment, Boolean subscribed);

    default Double kelvinToCelsius(Double kelvin) {
        return kelvin - 273.15;
    }

}
