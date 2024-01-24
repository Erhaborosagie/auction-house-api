package com.auction.InvoiceService.service;

import com.auction.InvoiceService.entities.Bid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "invoice")
    public void receiveInvoice(String bidString){
        System.out.println("invoiceNotice " + bidString);
        Bid bid = convertStringToBid(bidString);
        System.out.println("Invoice received for " + bid.getItem());

        //Todo generate invoice pdf, store in file system and mail to user

        kafkaTemplate.send("payment",bid.toString());
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
}
