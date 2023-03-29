package com.malejandros.cinema.services;

import com.malejandros.cinema.models.Cinema;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {
    private Cinema cinema;

    public CinemaService() {
        this.cinema = new Cinema(9, 9);
    }

    public Cinema getSeats() {
        return cinema;
    }
}
