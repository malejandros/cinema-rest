package com.malejandros.cinema.services;

import com.malejandros.cinema.models.Cinema;
import com.malejandros.cinema.models.Seat;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CinemaService {
    private Cinema cinema;

    public CinemaService() {
        this.cinema = new Cinema(9, 9);
    }

    public Cinema getSeats() {
        return cinema;
    }

    public Map<String, Object> purchaseSeat(Seat seat) {
        try {
            Seat s = cinema.getSeatByRowAndColumn(seat.getRow(), seat.getColumn());
            if(s.isBooked()) {
                throw new RuntimeException("The ticket has been already purchased!");
            }
            UUID token = UUID.randomUUID();
            s.setToken(token);
            s.setBooked(true);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("ticket", s);
            return map;
        } catch (Exception e) {
            throw new IndexOutOfBoundsException(e.getMessage());
        }
    }


}
