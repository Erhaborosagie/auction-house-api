package com.auction.biddingService.service;

import com.auction.biddingService.entities.Bid;
import org.springframework.http.ResponseEntity;

/**
 * The interface for the Bid Tracker.
 * This interface exposes methods allowing people to submit {@link Bid}s on items in a room
 * and query the current state of the auction.
 */
public interface BiddingService {
    public ResponseEntity submitBid(Bid bid, String  username);

    public ResponseEntity viewBids(String roomId, String  username);

    ResponseEntity endBid(String roomId, String username);
}
