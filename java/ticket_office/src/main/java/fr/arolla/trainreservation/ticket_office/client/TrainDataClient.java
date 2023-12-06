package fr.arolla.trainreservation.ticket_office.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.arolla.trainreservation.ticket_office.Seat;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainDataClient {

  private final RestTemplate restTemplate;

  TrainDataClient() {
    restTemplate = new RestTemplate();
  }

  public List<Seat> getTrainDataByID(String trainId) {
    String json = restTemplate.getForObject("http://127.0.0.1:8081/data_for_train/" + trainId, String.class);

    ObjectMapper objectMapper = new ObjectMapper();
    ArrayList<Seat> seats = new ArrayList<>();
    try {
      var tree = objectMapper.readTree(json);
      var seatsNode = tree.get("seats");
      for (JsonNode node : seatsNode) {
        String coach = node.get("coach").asText();
        String seatNumber = node.get("seat_number").asText();
        var jsonBookingReference = node.get("booking_reference").asText();
        if (jsonBookingReference.isEmpty()) {
          var seat = new Seat(seatNumber, coach, null);
          seats.add(seat);
        } else {
          var seat = new Seat(seatNumber, coach, jsonBookingReference);
          seats.add(seat);
        }
      }
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return seats;
  }
}
