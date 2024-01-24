package com.auction.biddingService.entities;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BidHouse {
    private Map<String, Bid> userBids;
    private String roomId;
    boolean bidStarted;
    int maxBid = 0;
    private String item;
}
