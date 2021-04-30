package com.telegram.bot;

import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weather-api", url = "api.openweathermap.org")
public interface WeatherApiProxy {

    @GetMapping("/data/2.5/weather")
    public String getWeatherByCity(@RequestParam("q") String city, @RequestParam("appid") String appid);

}
