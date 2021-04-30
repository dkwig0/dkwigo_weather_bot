package com.telegram.bot.db;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "chatId")
    private Long chatId;

    @Column(name = "cityName")
    private String cityName;

    @Column(name = "hourToSend")
    private Byte hourToSend;

    @Column(name = "minuteToSend")
    private Byte minuteToSend;

    private String languageCode;

    public Subscription() {
    }

    public Subscription(Long chatId, String cityName, Byte hourToSend, Byte minuteToSend, String languageCode) {
        this.chatId = chatId;
        this.cityName = cityName;
        this.hourToSend = hourToSend;
        this.minuteToSend = minuteToSend;
        this.languageCode = languageCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Byte getHourToSend() {
        return hourToSend;
    }

    public void setHourToSend(Byte hourToSend) {
        this.hourToSend = hourToSend;
    }

    public Byte getMinuteToSend() {
        return minuteToSend;
    }

    public void setMinutesToSend(Byte minuteToSend) {
        this.minuteToSend = minuteToSend;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(chatId, that.chatId)
                .append(cityName, that.cityName)
                .append(hourToSend, that.hourToSend)
                .append(minuteToSend, that.minuteToSend)
                .append(languageCode, that.languageCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(chatId)
                .append(cityName)
                .append(hourToSend)
                .append(minuteToSend)
                .append(languageCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", cityName='" + cityName + '\'' +
                ", hourToSend=" + hourToSend +
                ", minuteToSend=" + minuteToSend +
                ", languageCode='" + languageCode + '\'' +
                '}';
    }
}
