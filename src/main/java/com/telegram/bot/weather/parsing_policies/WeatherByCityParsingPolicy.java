package com.telegram.bot.weather.parsing_policies;

import com.fasterxml.jackson.databind.JsonNode;
import com.telegram.bot.weather.Temperature;
import com.telegram.bot.weather.WeatherForecastBean;
import com.telegram.bot.weather.languages.Language;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class WeatherByCityParsingPolicy implements ParsingPolicy {

    @Override
    public SendMessage parse(JsonNode json, String cityName, Language language, Environment environment, Boolean subscribed) {

        SendMessage responseMessage = new SendMessage();

        JsonNode main = json.get("main");
        Double windSpeed = json.get("wind").get("speed").asDouble();
        Double cloudiness = json.get("clouds").get("all").asDouble();

        responseMessage.setText(new WeatherForecastBean(
                new Temperature(
                        kelvinToCelsius(main.get("temp_min").asDouble()),
                        kelvinToCelsius(main.get("temp_max").asDouble()),
                        kelvinToCelsius(main.get("feels_like").asDouble()),
                        kelvinToCelsius(main.get("temp").asDouble())),
                main.get("pressure").asDouble(),
                main.get("humidity").asDouble(),
                cloudiness,
                windSpeed)
                .toPrettyString(cityName, language, environment));

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton testButton = new InlineKeyboardButton();

        if (subscribed) {
            testButton.setText("unsubscribe");
            testButton.setCallbackData("unsubscribe");
        } else {
            testButton.setText("subscribe");
            testButton.setCallbackData("open_time_picker");
        }

        row.add(testButton);

        keyboard.add(row);

        responseMessage.setReplyMarkup(new InlineKeyboardMarkup(keyboard));

        return responseMessage;
    }
}
