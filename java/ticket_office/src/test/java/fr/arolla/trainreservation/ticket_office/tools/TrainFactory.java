package fr.arolla.trainreservation.ticket_office.tools;

import fr.arolla.trainreservation.ticket_office.Seat;

import java.util.ArrayList;
import java.util.List;

public class TrainFactory {

  private final List<Seat> seats;

  private int id = 1;

  private TrainFactory() {
    this.seats = new ArrayList<>();
  }

  public static TrainFactory createTrain() {
    return new TrainFactory();
  }

  public TrainFactory addCoach(String coachName, int capacity, double occupancy) {
    int occupiedSeatsCount = (int) (capacity * occupancy);

    for (int seatNumber = 1; seatNumber <= capacity; seatNumber++) {
      String seatNum = String.valueOf(seatNumber);
      String bookingReference = null;

      // Mark the first seats as occupied based on occupancy rate
      if (seatNumber <= occupiedSeatsCount) {
        bookingReference = generateBookingReference();
      }

      seats.add(new Seat(seatNum, coachName, bookingReference));
    }
    return this;
  }

  public List<Seat> generate() {
    return new ArrayList<>(seats);
  }

  private String generateBookingReference() {
    return "" + this.id++;
  }
}