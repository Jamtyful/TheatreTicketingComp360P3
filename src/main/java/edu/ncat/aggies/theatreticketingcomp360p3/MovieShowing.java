package edu.ncat.aggies.theatreticketingcomp360p3;

import java.util.ArrayList;
import java.util.Date;

public class MovieShowing {
    private final int totalSeats;
    private final Date time;
    private final String title;

    private final ArrayList<String> reservations = new ArrayList<>();


    public MovieShowing(int numSeats, Date time, String title) {
        this.totalSeats = numSeats;
        this.time = time;
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public String getTitle() {
        return title;
    }

    //throw the NoSeatAvailableException on seats full. Otherwise, returns seat number.
    public int BuyTicket(String name) throws NoSeatAvailableException {
        if (reservations.size() >= totalSeats) throw new NoSeatAvailableException();
        reservations.add(name);
        return reservations.size();
    }

    public void returnSeat(String name){
        reservations.remove(name);
    }

    public boolean hasTicket(String name){
        return reservations.contains(name);
    }

    public boolean hasReservation(String name){
        return reservations.contains(name);
    }

    @Override
    public String toString() {
        return title + " " + time;
    }

    public static class NoSeatAvailableException extends Throwable {
    }
}
