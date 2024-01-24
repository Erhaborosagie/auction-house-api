package com.auction.InvoiceService.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntities {
    private String roomId;
    private List<String> users;
    private Bid bid;
}
