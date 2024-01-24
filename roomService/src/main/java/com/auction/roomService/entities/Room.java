package com.auction.roomService.entities;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Room {
    private String id;
    private Set<String> users;
}
