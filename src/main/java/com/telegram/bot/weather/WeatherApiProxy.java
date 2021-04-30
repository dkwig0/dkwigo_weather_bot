package com.telegram.bot.weather;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

@Component
public class WeatherApiProxy {

    private CookieManager cookies;
    private OkHttpClient client;

    public WeatherApiProxy() {
        cookies = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookies))
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public String getWeatherByCity(String city, String appid) {

        Request weatherByCityRequest = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + appid)
                .build();

        try {
            Response weatherResponse = client.newCall(weatherByCityRequest).execute();
            return weatherResponse.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "{cod: 404}";
        }

    }

}
