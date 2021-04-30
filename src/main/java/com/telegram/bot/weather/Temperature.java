package com.telegram.bot.weather;

import com.telegram.bot.weather.languages.Language;
import org.springframework.core.env.Environment;

public class Temperature {

    private Double minTemperature;
    private Double maxTemperature;
    private Double feelsLikeTemperature;
    private Double currentTemperature;

    public Temperature(Double minTemperature, Double maxTemperature, Double feelsLikeTemperature,
                       Double currentTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.currentTemperature = currentTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public void setFeelsLikeTemperature(Double feelsLikeTemperature) {
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String toPrettyString(Language language, Environment environment) {
        return String.format(
                environment.getProperty("format." + language.string + ".temperature"),
                String.format("%.1f", minTemperature),
                String.format("%.1f", maxTemperature),
                String.format("%.1f", feelsLikeTemperature),
                String.format("%.1f", currentTemperature)
        );
    }
}
