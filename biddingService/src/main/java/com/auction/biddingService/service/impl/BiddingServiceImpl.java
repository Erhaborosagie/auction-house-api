package com.auction.biddingService.service.impl;

import com.auction.biddingService.entities.Bid;
import com.auction.biddingService.entities.BidHouse;
import com.auction.biddingService.service.BiddingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BiddingServiceImpl implements BiddingService {

    final List<BidHouse> bidHouses;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public ResponseEntity submitBid(Bid bid, String username) {
        if (null == bid.getRoomId() || bid.getAmount() <1) return ResponseEntity.status(400)
                .body("please enter valid bid with amount greater than zero and valid roomId");

        BidHouse bidHouse1 = bidHouses.stream()
                .filter(bidHouse -> bidHouse.getRoomId().equalsIgnoreCase(bid.getRoomId()))
                .findFirst().orElse(null);
        if (null == bidHouse1) return ResponseEntity.status(404).body("No such bidding room " + bid.getRoomId());

        if(!bidHouse1.getUserBids().containsKey(username))
            return ResponseEntity.status(403).body("You have not registered for this bid through the room");

        if (bidHouse1.getMaxBid() >= bid.getAmount())
            return ResponseEntity.status(403).body("bid amount can't be less than existing maximum bid. Existing max is " + bidHouse1.getMaxBid());

        bidHouses.stream()
                .filter(bidHouse -> bidHouse.getRoomId().equalsIgnoreCase(bid.getRoomId()))
                .findFirst()
                .map(bidHouse -> {
                    bidHouse.setMaxBid(bid.getAmount());
                    bidHouse.getUserBids().replace(username, bid);
                    return bidHouse;
                });

        bid.setUser(username);
        bid.setItem(bidHouse1.getItem());

        kafkaTemplate.send("newBidNotice", bid.toString());

        return ResponseEntity.ok(bid.toString());
    }

    @Override
    public ResponseEntity viewBids(String roomId, String username) {
        return ResponseEntity.ok(bidHouses);
    }

    @Override
    public ResponseEntity endBid(String roomId, String username) {
        //check if admin
        if(!username.equalsIgnoreCase("admin")) return ResponseEntity.status(403).body("You have no access to this resource");

        //check that room exist
        BidHouse bidHouse1 = bidHouses.stream().filter(bidHouse -> bidHouse.getRoomId().equalsIgnoreCase(roomId))
                .findFirst()
                .orElse(null);
        if (null == bidHouse1) return ResponseEntity.status(404).body("No such bidding room " + roomId);

        Bid bid = new Bid();
        bid.setRoomId(roomId);

        kafkaTemplate.send("endBidNotice", bid.toString());

        bidHouses.removeIf(bidHouse -> bidHouse.getRoomId().equalsIgnoreCase(roomId));

        return ResponseEntity.ok("Bid deleted");
    }

    @KafkaListener(topics = "newRoom")
    public void consumeNewRoomMessage(Map<String, String> message){
        System.out.println("newRoom received " + message);
        final String roomId = message.values().iterator().next();
        final String userId = message.keySet().iterator().next();
        BidHouse bidHouse1 = bidHouses.stream()
                .filter(bidHouse -> bidHouse.getRoomId().equalsIgnoreCase(roomId))
                .findFirst()
                .orElse(null);

        if (null == bidHouse1){
            bidHouse1 = new BidHouse();
            bidHouse1.setRoomId(roomId);
            bidHouse1.setBidStarted(true);

            Map<String, Bid> userBid = new HashMap<>();
            userBid.put(userId, null);

            bidHouse1.setUserBids(userBid);
            bidHouse1.setItem("");

            bidHouses.add(bidHouse1);
        }else {
            bidHouses.stream()
                    .filter(bidHouse -> bidHouse.getRoomId().equalsIgnoreCase(roomId))
                    .forEach(bidHouse -> {
                        if (!bidHouse.getUserBids().containsKey(userId)) bidHouse.getUserBids().put(userId, null);
                    });

        }

    }

}
