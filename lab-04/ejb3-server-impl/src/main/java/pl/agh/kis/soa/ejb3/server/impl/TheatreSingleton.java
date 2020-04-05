package pl.agh.kis.soa.ejb3.server.impl;

import javax.ejb.Lock;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TheatreSingleton {

    private List<Seat> seats;

    public TheatreSingleton(){
        seats = new ArrayList<Seat>();
        seats.add(new Seat(1,1,false, 100));
        seats.add(new Seat(1,2,true, 100));
        seats.add(new Seat(1,3,true, 150));
        seats.add(new Seat(1,4,false, 150));
        seats.add(new Seat(1,5,true, 100));
        seats.add(new Seat(1,6,false, 100));
        seats.add(new Seat(2,1,false, 80));
        seats.add(new Seat(2,2,true, 80));
        seats.add(new Seat(2,3,false, 110));
        seats.add(new Seat(2,4,false, 110));
        seats.add(new Seat(2,5,true, 80));
        seats.add(new Seat(2,6,false, 80));
    }

    @Lock
    public int getSeatPrice(Seat seat) {
        return seat.getPrice();
    }

    @Lock
    public List<Seat> getSeatList() {
        return seats;
    }

    @Lock
    public void buyTicket( Seat seat, User user ){
        seat.setReserved(true);

        int userNewBudget = user.getBudget() - seat.getPrice();
        user.setBudget(userNewBudget);
    }
}
