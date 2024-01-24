package com.auction.NotificationService.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntities {
    private String roomId;
    private Set<String> users;
    private Bid bid;
}
