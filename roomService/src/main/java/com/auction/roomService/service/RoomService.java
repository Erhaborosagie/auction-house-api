package com.auction.roomService.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * The interface for a Room service.
 * This interface exposes methods allowing individuals to enter a room based on roomId
 * It checks if the room exist
 */
@Service
public interface RoomService {
    public ResponseEntity<?> enterRoom(String roomId, String username);
}
