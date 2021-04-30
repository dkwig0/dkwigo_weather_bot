package com.telegram.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telegram.bot.callback_response.*;
import com.telegram.bot.db.Subscription;
import com.telegram.bot.db.SubscriptionRepository;
import com.telegram.bot.weather.WeatherApiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WeatherBot extends TelegramLongPollingBot {

    Map<String, List<CallbackResponse>> callbackResponses;

    @Autowired
    Environment environment;

    @Autowired
    private WeatherApiParser weatherApiParser;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    Logger logger = LoggerFactory.getLogger(WeatherApplication.class);

    private final String botToken;

    private final String botUsername;

    public WeatherBot(@Value("${bot.token}") String botToken,
                      @Value("${bot.username}") String botUsername) {
        super();
        this.botToken = botToken;
        this.botUsername = botUsername;
        initCallbackResponses();

    }

    @PostConstruct
    private void registerBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initCallbackResponses() {
        callbackResponses = new HashMap<>();

        callbackResponses.put("unsubscribe", Arrays.asList(new UnsubscribeResponse(), new DeleteMessageResponse()));
        callbackResponses.put("open_time_picker", Arrays.asList(new OpenTimePickerResponse()));
        callbackResponses.put("time_plus", Arrays.asList(new TimeIncrementResponse()));
        callbackResponses.put("time_minus", Arrays.asList(new TimeDecrementResponse()));
        callbackResponses.put("subscribe", Arrays.asList(new SubscribeResponse(), new SuccessMessageResponse()));
        callbackResponses.put("time_reset", Arrays.asList(new TimeResetResponse()));
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {
            BotApiMethod responseMethod;
            for (CallbackResponse response : callbackResponses.get(update.getCallbackQuery().getData())) {
                if ((responseMethod = response.getMethod(update, subscriptionRepository, environment)) != null) {
                    try {
                        execute(responseMethod);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (update.hasMessage()) {
            System.out.println(update.getMessage().getVenue());
            if (!update.getMessage().getText().startsWith("/")) {
                try {
                    execute(weatherApiParser.getSendMessageResponseByCityName(
                            update.getMessage().getChatId(),
                            update.getMessage().getText(),
                            update.getMessage().getFrom().getLanguageCode()
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Scheduled(cron = "0 0,30 * * * *") //at 00:00 and 30:every hour, every day of month, every month, every day of week
    public void sendToSubscribers() {

        LocalTime utc0time = LocalTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(0)));

        List<Subscription> subs = subscriptionRepository.findAllByHourToSendAndMinuteToSend(
                (byte)utc0time.getHour(),
                (byte)utc0time.getMinute()
        );

        for (Subscription sub : subs) {
            try {
                sendApiMethodAsync(weatherApiParser.getSendMessageResponseByCityName(
                                sub.getChatId(),
                                sub.getCityName(),
                                sub.getLanguageCode()
                ));
                System.out.println("Sent to: " + sub.getChatId());
            } catch(JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

}
