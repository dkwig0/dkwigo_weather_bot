package com.telegram.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class WeatherBot extends TelegramLongPollingBot {

    @Autowired
    private WeatherApiProxy weatherApi;

    public WeatherBot() {
        super();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (Exception e) {}
    }

    public String getBotUsername() {
        return "dkwigo_weather_bot";
    }

    public String getBotToken() {
        return "1684776543:AAGvpYkTFtYpmRIOaoijekJUbKKnnD51gjc";
    }

    public void onUpdateReceived(Update update) {
        try {
            JsonNode forecast = new ObjectMapper().readTree(weatherApi.getWeatherByCity("Lodz", "151dd1cb0e0b87a72ad27a5f5966b30b"));
            SendMessage msg = new SendMessage(update.getMessage().getChatId().toString(), forecast.get("main").toString());
            sendApiMethod(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}
