package fr.arolla.trainreservation.ticket_office.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BookingReferenceClient {

  private final RestTemplate restTemplate;

  BookingReferenceClient() {
    restTemplate = new RestTemplate();
  }

  public String getBookingRequest() {
    return restTemplate.getForObject("http://127.0.0.1:8082/booking_reference", String.class);
  }
}
