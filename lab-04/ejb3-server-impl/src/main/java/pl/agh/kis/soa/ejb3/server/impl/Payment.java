package pl.agh.kis.soa.ejb3.server.impl;

import javax.ejb.Stateful;

@Stateful
public class Payment {
    public void checkPayment( Seat seat, User user ) throws Exception {
        if( !isAvailable(seat) ){
            throw new Exception("To miejsce jest już zarezerwowane!");
        }

        if( !canBuy(seat, user) ){
            throw new Exception("Nie masz wystarczajaco funduszy by zarezerwoac to miejsce! Stan Twojego konta to: " + user.getBudget());
        }
    }

    public boolean isAvailable( Seat seat ){
        return !seat.isReserved();
    }

    public boolean canBuy( Seat seat, User user ) {
        return user.getBudget() - seat.getPrice() >= 0;
    }
}
