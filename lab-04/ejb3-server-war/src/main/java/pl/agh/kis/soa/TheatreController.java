package pl.agh.kis.soa;

import pl.agh.kis.soa.ejb3.server.impl.Payment;
import pl.agh.kis.soa.ejb3.server.impl.Seat;
import pl.agh.kis.soa.ejb3.server.impl.TheatreSingleton;
import pl.agh.kis.soa.ejb3.server.impl.User;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean(name="TheatreController")
public class TheatreController implements Serializable {
    private List<Seat> seats;
    private Seat selectedSeat;
    private String errorMsg = "";

    @EJB
    TheatreSingleton singleton;

    @EJB
    User logged;

    @EJB
    Payment payment;

    public String getError() {
        return errorMsg;
    }

    public void setError(String error) {
        this.errorMsg = error;
    }

    public Seat getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(Seat selectedSeat) {
        this.selectedSeat = selectedSeat;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getSeats(){
        seats = singleton.getSeatList();
        return seats;
    }

    public void buyTicket(Seat seat){
        if( !errorMsg.equals("") ){
            errorMsg = "";
        }

        selectedSeat = seat;
        try {
            payment.checkPayment(seat, logged);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorMsg = e.getMessage();
            return;
        }

        singleton.buyTicket(seat, logged);
    }
}
