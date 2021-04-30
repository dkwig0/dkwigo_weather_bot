package com.telegram.bot.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegram.bot.db.SubscriptionRepository;
import com.telegram.bot.weather.languages.Language;
import com.telegram.bot.weather.parsing_policies.CityNotFoundParsingPolicy;
import com.telegram.bot.weather.parsing_policies.ParsingPolicy;
import com.telegram.bot.weather.parsing_policies.ServerErrorParsingPolicy;
import com.telegram.bot.weather.parsing_policies.WeatherByCityParsingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class WeatherApiParser {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    Environment environment;

    @Autowired
    private WeatherApiProxy weatherApiProxy;

    private final String appid;

    Map<Integer, ParsingPolicy> parsingPolicies;

    public WeatherApiParser(@Value("${weather.api.appid}") String appid) {
        this.appid = appid;
        this.initParsingPolicies();
    }

    private void initParsingPolicies() {

        parsingPolicies = new HashMap<Integer, ParsingPolicy>();

        parsingPolicies.put(200, new WeatherByCityParsingPolicy());
        parsingPolicies.put(401, new ServerErrorParsingPolicy());
        parsingPolicies.put(404, new CityNotFoundParsingPolicy());
        parsingPolicies.put(429, new ServerErrorParsingPolicy());
    }

    public SendMessage getSendMessageResponseByCityName(Long chatId, String cityName, String language) throws JsonProcessingException {

        JsonNode forecast;

        forecast = new ObjectMapper().readTree(weatherApiProxy.getWeatherByCity(cityName, appid));

        SendMessage responseMessage = parsingPolicies.get(
                forecast.get("cod").asInt()
        ).parse(
                forecast,
                cityName,
                Optional.ofNullable(Language.valueOfString(language)).orElse(Language.EN),
                environment,
                subscriptionRepository.findAllByChatIdAndCityName(chatId, cityName).size() > 0
        );

        responseMessage.setChatId(chatId.toString());

        return responseMessage;
    }

    private Double kelvinToCelsius(Double kelvin) {
        return kelvin - 273.15;
    }

}
