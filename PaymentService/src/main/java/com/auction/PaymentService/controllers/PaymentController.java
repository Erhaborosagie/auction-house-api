package com.auction.PaymentService.controllers;

import com.auction.PaymentService.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the entry-point to the auction house.
 * Required services will be autowired here.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/payment")
public class PaymentController {

    private final PaymentService service;

    @RequestMapping(value = "/{roomId}", method = RequestMethod.GET)
    public ResponseEntity<?> processPayment(@PathVariable String roomId){
        return service.processPayment(roomId);
    }
}
