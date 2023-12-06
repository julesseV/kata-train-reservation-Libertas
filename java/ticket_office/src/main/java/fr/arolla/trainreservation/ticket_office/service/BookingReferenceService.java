package fr.arolla.trainreservation.ticket_office.service;

import fr.arolla.trainreservation.ticket_office.client.BookingReferenceClient;
import org.springframework.stereotype.Service;

@Service
public class BookingReferenceService {

  private final BookingReferenceClient bookingReferenceClient;

  BookingReferenceService(BookingReferenceClient bookingReferenceClient){
    this.bookingReferenceClient = bookingReferenceClient;
  }

  public String getBookingReference(){
    return bookingReferenceClient.getBookingRequest();
  }
}
