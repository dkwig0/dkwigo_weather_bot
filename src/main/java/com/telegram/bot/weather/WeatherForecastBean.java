package com.telegram.bot.weather;

import com.telegram.bot.weather.languages.Language;
import org.springframework.core.env.Environment;

public class WeatherForecastBean {

    Environment environment;

    private Temperature temperature;

    private Double pressure;
    private Double humidity;
    private Double cloudiness;
    private Double windSpeed;

    public WeatherForecastBean(Temperature temperature, Double pressure, Double humidity, Double cloudiness,
                               Double windSpeed) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloudiness = cloudiness;
        this.windSpeed = windSpeed;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(Double cloudiness) {
        this.cloudiness = cloudiness;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String toPrettyString(String cityName, Language language, Environment environment) {
        return String.format(
                environment.getProperty("format." + language.string + ".forecast"),
                cityName,
                temperature.toPrettyString(language, environment),
                pressure,
                humidity,
                cloudiness,
                windSpeed
        );
    }

}
