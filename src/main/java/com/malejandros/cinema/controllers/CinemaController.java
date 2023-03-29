package com.malejandros.cinema.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malejandros.cinema.models.Cinema;
import com.malejandros.cinema.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
