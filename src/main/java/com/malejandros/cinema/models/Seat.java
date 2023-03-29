package com.malejandros.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Seat {
    private int row;
    private int column;
    private int price;
    private boolean booked;
    private UUID token;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        price = row <= 4 ? 10 : 8;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    @JsonIgnore
    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    @JsonIgnore
    public UUID getToken() {
        return token;
    }
}
