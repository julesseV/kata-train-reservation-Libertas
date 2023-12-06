package fr.arolla.trainreservation.ticket_office.domain;

import fr.arolla.trainreservation.ticket_office.Seat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TrainDataDomain {
  public boolean isCoachOccupationAbove70(final Stream<Seat> coachSeats, final Stream<Seat> occupiedCoachSeats) {
    return ((double) occupiedCoachSeats.count() / coachSeats.count()) >= 0.70;
  }

  public boolean isCoachOccupationWithAddedAbove70(final Stream<Seat> coachSeats, final Stream<Seat> occupiedCoachSeats, int seatCount) {
    return ((double) occupiedCoachSeats.count() / coachSeats.count()) >= 0.70;
  }

  public boolean isCoachFull(final Stream<Seat> coachSeats, final Stream<Seat> occupiedCoachSeats, int seatCount) {
    return ((double) (occupiedCoachSeats.count() + seatCount) / coachSeats.count()) >= 1.0;
  }

  public boolean isTrainOccupationAbove70(final Stream<Seat> occupiedSeats, final List<Seat> seats, int seatCount) {
    return ((double) (occupiedSeats.count() + seatCount) / seats.size()) >= 0.70;
  }
}
