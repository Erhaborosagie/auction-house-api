package com.auction.roomService.service.impl;

import com.auction.roomService.entities.Room;
import com.auction.roomService.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {

    private final List<Room> rooms;

    @Autowired
    private KafkaTemplate<String, Map<String, String>> kafkaTemplate;

    {
        Room room = new Room("room0", new HashSet<>());
        Room room1 = new Room("room1", new HashSet<>());
        Room room2 = new Room("room2", new HashSet<>());
        Room room3 = new Room("room3", new HashSet<>());
        Room room4 = new Room("room4", new HashSet<>());
        Room room5 = new Room("room5", new HashSet<>());

        rooms = new ArrayList<>();
        rooms.add(room);
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
    }

    public ResponseEntity<?> enterRoom(String roomId, String  username) {

        Room room1 = rooms.stream().filter(room -> room.getId().equals(roomId)).findFirst().orElse(null);
        if (null==room1) return ResponseEntity.status(404).body("No room with id " + roomId );

        rooms.stream()
                .filter(room -> room.getId().equals(roomId))
                .forEach(room -> room.getUsers().add(username));

        Map<String, String> kafkaMap = new HashMap<>();
        kafkaMap.put(username, roomId);
        kafkaTemplate.send("newRoom", kafkaMap);

        return ResponseEntity.ok().build();
    }
}
