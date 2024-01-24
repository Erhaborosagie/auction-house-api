package com.auction.roomService.controllers;

import com.auction.roomService.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is the entry-point to the auction house.
 * Required services will be autowired here.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/rooms")
public class RoomController {
    private final RoomService roomService;

    @RequestMapping(value="/{roomId}", method = RequestMethod.GET)
    public ResponseEntity<?> enterRoom(@PathVariable String roomId, @RequestHeader String  username){
        return roomService.enterRoom(roomId, username);
    }



}
