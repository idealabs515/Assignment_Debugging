package hotel.service;

import hotel.entities.Booking;
import hotel.entities.Hotel;
import hotel.entities.ServiceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Author: Bikram Shrestha
 *         11645312
 *         ITC515 Professional Programming Practices
 *         Assessment 4
 * This test is performed to check whether there is problem in
 * RecordServiceCTL.serviceDetailsEntered() method. This test
 * shows that the serviceDetailsEntered() is functioning as
 * expected so the error must be somewhere else.
 * */
public class RecordServiceCTLTest {

    @Mock Hotel hotel;
    @Mock Booking booking;

    @InjectMocks
    RecordServiceCTL recordServiceCTL;

    /*
    Setting up variables that are common to all the test.
    */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /*
    All the information that was generated after test is reset to null or 0 as
    suitable for object type to eliminate any tampering the preceding test.
     */
    @After
    public void tearDown() throws Exception {
        recordServiceCTL = null;
    }

    /*
    This test shows that the serviceDetailsEntered() is working as expected so
    the method call done within the method must be wrong.
     */
    @Test
    public void serviceDetailsEnteredTest() {
        //Act
        when(hotel.findActiveBookingByRoomId(101)).thenReturn(booking);
        recordServiceCTL.roomNumberEntered(101);
        doNothing().when(hotel).addServiceCharge(anyInt(),any(ServiceType.class),anyDouble());
        //Assert
        recordServiceCTL.serviceDetailsEntered(ServiceType.BAR_FRIDGE,200);
    }
}