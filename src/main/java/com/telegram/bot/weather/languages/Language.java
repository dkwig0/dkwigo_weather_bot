package com.telegram.bot.weather.languages;

public enum Language {
    EN("en"),
    RU("ru");

    public final String string;

    private Language(String value) {
        this.string = value;
    }

    public static Language valueOfString(String value) {
        for (Language lang : Language.values()) {
            if (value.equals(lang.string)) {
                return lang;
            }
        }
        return null;
    }

}
