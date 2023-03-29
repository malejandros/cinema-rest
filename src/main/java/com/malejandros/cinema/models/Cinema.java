package com.malejandros.cinema.models;

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
        purchasedSeats.add(seat);
        seats.remove(seat);
    }

    public void addAvailableSeat(Seat seat) {
        seats.add(seat);
    }

    @Override
    public String toString() {
        return "Cinema:" + seats;
    }
}
