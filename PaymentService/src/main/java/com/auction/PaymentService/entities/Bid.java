package com.auction.PaymentService.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Bid {
    private int amount;
    private String roomId;
    private String user;
    private String item;
}
