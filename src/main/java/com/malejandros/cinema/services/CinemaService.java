package com.malejandros.cinema.services;

import com.malejandros.cinema.models.Cinema;
import com.malejandros.cinema.models.Seat;
import com.malejandros.cinema.models.Token;
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
            cinema.addPurchasedSeats(s);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("ticket", s);
            return map;
        } catch (Exception e) {
            throw new IndexOutOfBoundsException(e.getMessage());
        }
    }

    public Seat returnTicket(Token token) {
        try {
            Seat s = cinema.getPurchasedSeatByToken(token.getToken());
            s.setBooked(false);
            s.setToken(null);
            cinema.addAvailableSeat(s);
            return s;
        } catch (Exception e) {
            throw new IndexOutOfBoundsException(e.getMessage());
        }
    }

    public Map<String, Integer> getStats(String password) {
        if(password != null) {
            if (password.equals("super_secret")) {
                Map<String, Integer> map = new HashMap<>();
                map.put("current_income", cinema.getCurrentIncome());
                map.put("number_of_available_seats", cinema.getNumberOfAvailableSeats());
                map.put("number_of_purchased_tickets", cinema.getNumberOfPurchasedTickets());
                return map;
            }
        }
        throw new RuntimeException("The password is wrong!");
    }

}
