package com.telegram.bot.weather.parsing_policies;

import com.fasterxml.jackson.databind.JsonNode;
import com.telegram.bot.weather.languages.Language;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ServerErrorParsingPolicy implements ParsingPolicy {
    @Override
    public SendMessage parse(JsonNode json, String cityName, Language language, Environment environment, Boolean subscribed) {
        SendMessage responseMessage = new SendMessage();
        responseMessage.setText("Server error!");
        return responseMessage;
    }
}
