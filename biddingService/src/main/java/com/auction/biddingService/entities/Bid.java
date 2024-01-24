package com.auction.biddingService.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bid {
    private int amount;
    private String roomId;
    private String user;
    private String item;

    @Override
    public String toString(){
        return "{\n" +
                "    \"amount\":\"" + amount  + "\"," +
                "    \"roomId\":\""  + roomId + "\"," +
                "    \"user\":\""  + user + "\"," +
                "    \"item\":\""  + item + "\"" +
                "}";
    }
}
