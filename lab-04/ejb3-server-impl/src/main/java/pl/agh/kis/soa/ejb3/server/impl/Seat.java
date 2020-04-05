package pl.agh.kis.soa.ejb3.server.impl;

import javax.ejb.Stateless;

@Stateless
public class Seat {
    private int row;
    private int number;
    private boolean reserved;
    private int price;

    public Seat() {}

    public Seat(int row, int number, boolean reserved, int price) {
        this.row = row;
        this.number = number;
        this.reserved = reserved;
        this.price = price;
    }

    public boolean isReserved() {
        return reserved;
    }

    public String getReservationMsg () {
        if ( this.reserved ) {
            return "Niedostepne";
        } else {
            return "Dostępne";
        }
    }

    public void setReserved( boolean reserved ) {
        this.reserved = reserved;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice( int price ) {
        this.price = price;
    }

    public int getRow() {
        return this.row;
    }

    public int getSeatNumber() {
        return this.number;
    }
}
