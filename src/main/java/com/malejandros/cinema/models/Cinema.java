package com.malejandros.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Cinema {
    @JsonProperty("total_rows")
    private int rows;
    @JsonProperty("total_columns")
    private int columns;
    @JsonProperty("available_seats")
    private List<Seat> seats = new ArrayList<>();
    private List<Seat> purchasedSeats = new ArrayList<>();
    private int currentIncome;

    public Cinema(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        creatSeats();
    }

    private void creatSeats() {
        for (int i = 0; i < rows ; i++) {
            for (int j = 0; j < columns; j++) {
                seats.add(new Seat(i + 1, j + 1));
            }
        }
    }

    public Seat getSeatByRowAndColumn(int row, int column) {
        for(Seat s: seats) {
            if(s.getRow() == row && s.getColumn() == column) {
                return s;
            }
        }
        for(Seat s: purchasedSeats) {
            if(s.getRow() == row && s.getColumn() == column) {
                return s;
            }
        }
        throw new IndexOutOfBoundsException("The number of a row or a column is out of bounds!");
    }

    public Seat getPurchasedSeatByToken(String token) {
        for(var seat: purchasedSeats) {
            if(seat.getToken() != null) {
                if(seat.getToken().toString().equals(token)) {
                    return seat;
                }
            }
        }
        throw new RuntimeException("Wrong token!");
    }

    public void addPurchasedSeats(Seat seat) {
        currentIncome += seat.getPrice();
        purchasedSeats.add(seat);
        seats.remove(seat);
    }

    public void addAvailableSeat(Seat seat) {
        currentIncome -= seat.getPrice();
        seats.add(seat);
        purchasedSeats.remove(seat);
    }

    @JsonIgnore
    public int getCurrentIncome() {
        if(purchasedSeats.isEmpty()) {
            return 0;
        } else {
            int income = 0;
            for(Seat s: purchasedSeats) {
                income += s.getPrice();
            }
            return income;
        }
    }

    @JsonIgnore
    public int getNumberOfAvailableSeats() {
        return seats.size();
    }

    @JsonIgnore
    public int getNumberOfPurchasedTickets() {
        return purchasedSeats.size();
    }

    @Override
    public String toString() {
        return "Cinema:" + seats;
    }
}
