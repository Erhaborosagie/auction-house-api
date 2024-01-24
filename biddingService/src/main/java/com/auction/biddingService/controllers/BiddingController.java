package com.auction.biddingService.controllers;

import com.auction.biddingService.entities.Bid;
import com.auction.biddingService.service.BiddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is the entry-point to the auction house. For simplicity case there'd be just one controller.
 * Required services will be autowired here.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bidding")
public class BiddingController {
    private final BiddingService biddingService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity submitBid(@RequestBody Bid bid, @RequestHeader String  username){
        return biddingService.submitBid(bid, username);
    }

    @RequestMapping(value="/bids/{roomId}", method = RequestMethod.GET)
    public ResponseEntity viewBids(@PathVariable String roomId, @RequestHeader String  username){
        return biddingService.viewBids(roomId, username);
    }

    @RequestMapping(value="/{roomId}", method = RequestMethod.DELETE)
    public ResponseEntity endBid(@PathVariable String roomId, @RequestHeader String  username){
        return biddingService.endBid(roomId, username);
    }


}
