package fr.arolla.trainreservation.ticket_office.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.arolla.trainreservation.ticket_office.Seat;
import fr.arolla.trainreservation.ticket_office.client.BookingReferenceClient;
import fr.arolla.trainreservation.ticket_office.client.TrainDataClient;
import fr.arolla.trainreservation.ticket_office.domain.TrainDataDomain;
import fr.arolla.trainreservation.ticket_office.exception.TrainFullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrainDataService {
  @Autowired
  private final TrainDataClient trainDataClient;

  @Autowired
  private final TrainDataDomain trainDataDomain;

  TrainDataService(TrainDataClient trainDataClient, TrainDataDomain trainDataDomain){
    this.trainDataClient = trainDataClient;
    this.trainDataDomain = trainDataDomain;
  }

  public Stream<Seat> getAvailableSeats(String trainId, int seatCount){
    List<Seat> seats = trainDataClient.getTrainDataByID(trainId);

    var occupiedSeats = seats.stream().filter(seat -> seat.bookingReference() != null);
    if (trainDataDomain.isTrainOccupationAbove70(occupiedSeats, seats, seatCount)){
      throw new TrainFullException();
    }

    for (String coach:getCoachs(seats)){
      var coachSeats = seats.stream().filter(seat -> seat.coach().equals(coach));
      var occupiedCoachSeats = seats.stream().filter(seat -> seat.coach().equals(coach) && seat.bookingReference() != null);
      if (trainDataDomain.isCoachOccupationAbove70(occupiedCoachSeats, coachSeats)){

      }

    }


    //var availableCoachSeats = seats.stream().filter(seat -> seat.coach().equals("A") && seat.bookingReference() == null);
    //var coachSeats = seats.stream().filter(seat -> seat.coach().equals("A"));
    var occupiedCoachSeats = seats.stream().filter(seat -> seat.coach().equals("A") && seat.bookingReference() != null);

    // Step 3: find available seats (hard-code coach 'A' for now)
    return seats.stream().filter(seat -> seat.coach().equals("A") && seat.bookingReference() == null);
  }

  private List<String> getCoachs(List<Seat> seats) {
    return seats.stream().map(Seat::coach).distinct().collect(Collectors.toList());
  }
}
