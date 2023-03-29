package com.malejandros.cinema.controllers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malejandros.cinema.models.Cinema;
import com.malejandros.cinema.models.Seat;
import com.malejandros.cinema.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> getSeats() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String res = objectMapper.writeValueAsString(cinemaService.getSeats());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error was occurred on the server, please try again!", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseSeat(@RequestBody Seat seat) {
        ObjectMapper objectMapper = new ObjectMapper();
        String res;
        Map<String, Object> map = new HashMap<>();
        try {
            try {
                map = cinemaService.purchaseSeat(seat);
                res = objectMapper.writeValueAsString(map);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (JacksonException e) {
                return new ResponseEntity<>("An error was occurred on the server, please try again!", HttpStatus.SERVICE_UNAVAILABLE);
            } catch (Exception e) {

                map.put("error", e.getMessage());
                res = objectMapper.writeValueAsString(map);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        } catch (JacksonException e) {
            return new ResponseEntity<>("An error was occurred on the server, please try again!", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
