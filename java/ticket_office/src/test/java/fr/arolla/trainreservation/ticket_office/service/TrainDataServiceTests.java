package fr.arolla.trainreservation.ticket_office.service;

import fr.arolla.trainreservation.ticket_office.Seat;
import fr.arolla.trainreservation.ticket_office.client.TrainDataClient;
import fr.arolla.trainreservation.ticket_office.domain.TrainDataDomain;
import fr.arolla.trainreservation.ticket_office.exception.TrainFullException;
import fr.arolla.trainreservation.ticket_office.tools.TrainFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class TrainDataServiceTests {

  @Test
  public void trainCapacityIsAbove70() {
    List<Seat> train = TrainFactory.createTrain().addCoach("A", 100, 0.7).generate();
    TrainDataClient trainDataClient = Mockito.mock(TrainDataClient.class);
    Mockito.when(trainDataClient.getTrainDataByID(Mockito.anyString())).thenReturn(train);
    TrainDataService trainDataService = new TrainDataService(trainDataClient, new TrainDataDomain());

    Assertions.assertThrows(TrainFullException.class, () -> trainDataService.getAvailableSeats("1", 3));
  }
}
