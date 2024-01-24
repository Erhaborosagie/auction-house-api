package com.auction.PaymentService.services;

import com.auction.PaymentService.entities.Bid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    final List<Bid> bids;
    final KafkaTemplate<String, Bid> kafkaTemplate;

    @KafkaListener(topics = "payment")
    public void receiveInvoice(String bidString){
        System.out.println("endBidNotice " + bidString);
        Bid bid = convertStringToBid(bidString);
        System.out.println("Payment detail received for " + bid.getRoomId());

        bids.add(bid);
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

    public ResponseEntity<?> processPayment(String roomId) {
        Bid bid1 = bids.stream().filter(bid -> bid.getRoomId().equalsIgnoreCase(roomId)).findFirst().orElse(null);
        if (bid1 == null) return ResponseEntity.status(404).body("No bid with such roomId in the payment service");

        //Todo PaymentGateway with amount

        return ResponseEntity.ok("Payment processed successfully");
    }
}
