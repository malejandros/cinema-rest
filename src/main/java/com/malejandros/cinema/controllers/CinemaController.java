package com.malejandros.cinema.controllers;

import com.malejandros.cinema.models.Cinema;
import com.malejandros.cinema.models.Seat;
import com.malejandros.cinema.models.Token;
import com.malejandros.cinema.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CinemaController {
    private CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    public ResponseEntity<?> getSeats() {
        try {
            Cinema res = cinemaService.getSeats();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error was occurred on the server, please try again!", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seat) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = cinemaService.purchaseSeat(seat);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> refundTicket(@RequestBody Token token) {
        Map<String, Object> map = new HashMap<>();
        Token t = token;
        try {
            Seat s = cinemaService.returnTicket(t);
            map.put("returned_ticket", s);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/stats{password}")
    public ResponseEntity<?> getStats(@RequestParam String password) {
        try {
            Map<String, Integer> map = new HashMap<>();
            map = cinemaService.getStats(password);
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatusCode.valueOf(401));
        }
    }
}


