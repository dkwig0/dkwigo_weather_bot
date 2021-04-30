package com.telegram.bot.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllByCityName(String cityName);

    List<Subscription> findAllByHourToSendAndMinuteToSend(Byte hourToSend, Byte minuteToSend);

    List<Subscription> findAllByChatId(Long chatId);

    List<Subscription> findAllByChatIdAndCityName(Long chatId, String cityName);

}
