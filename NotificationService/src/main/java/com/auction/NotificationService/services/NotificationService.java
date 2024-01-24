package com.auction.NotificationService.services;

import com.auction.NotificationService.entities.Bid;
import com.auction.NotificationService.entities.NotificationEntities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationService {
    final List<NotificationEntities> entities;
    final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "newBidNotice")
    public void receiveBids(String bidString){
        System.out.println("enterBid " + bidString);
       Bid bid = convertStringToBid(bidString);
       if (null == bid) return;

        System.out.println("convertedBid " + bid);

        NotificationEntities bidEntity = entities.stream()
                .filter(entity -> entity.getRoomId().equalsIgnoreCase(bid.getRoomId()))
                .findFirst().orElse(null);

        if (null == bidEntity){
            bidEntity = new NotificationEntities(bid.getRoomId(), Collections.singleton(bid.getUser()), bid);
            entities.add(bidEntity);
        }else {

            entities.stream()
                    .filter(entity -> entity.getRoomId().equalsIgnoreCase(bid.getRoomId()))
                    .forEach(entity -> entity.setBid(bid));
        }

    }

    private Bid convertStringToBid(String bidString) {
        ObjectMapper mapper = new ObjectMapper();
        Bid bid;
        try {
           bid = mapper.readValue(bidString, Bid.class);
        } catch (JsonProcessingException e) {
            bid = null;
            e.printStackTrace();
        }
        return bid;
    }

    @KafkaListener(topics = "endBidNotice")
    public void endBidNotification(String bidString){
        System.out.println("endBidNotice " + bidString);
        Bid bid = convertStringToBid(bidString);
        NotificationEntities notificationEntity = entities.stream()
                .filter(entity -> entity.getRoomId().equalsIgnoreCase(bid.getRoomId()))
                .findFirst().orElse(null);

        if (null == notificationEntity) return;

        //Todo send notice to all bidders for that bid notificationEntity.getUsers()

        //send message to the invoice service
        kafkaTemplate.send("invoice", notificationEntity.getBid().toString());
    }
}
