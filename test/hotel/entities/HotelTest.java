package hotel.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;


/**
 * Author: Bikram Shrestha
 *         11645312
 *         ITC515 Professional Programming Practices
 *         Assessment 4
 * This is the unit test of the Hotel addServiceCharge()
 * method. This test shows that the method is working as
 * expected so there must be a problem in the method call
 * made within this method.
 * */
public class HotelTest {
    int stayLength;
    int occupantNumber;
    int cost;
    int roomId;

    @Mock Booking booking;

    @InjectMocks
    Hotel hotel;


    /*
    Setting up variables that are common to all the test.
     */
    @Before
    public void setUp()   {
        MockitoAnnotations.initMocks(this);
        stayLength = 2;
        occupantNumber =2;
        cost = 10;
        roomId = 101;
    }


    /*
    All the information that was generated after test is reset to null or 0 as
    suitable for object type to eliminate any tampering the preceding test.
     */
    @After
    public void tearDown()   {
        hotel = null;
        stayLength = 0;
        occupantNumber = 0;
        cost = 0;
        roomId = 0;
    }

    /*
    This test shows that addServiceCharge() is working as expected while conducting
    unit test so the method call made within the method must be problematic.
     */
    @Test
    public void addServiceCharge() {
        //Arrange
        hotel.activeBookingsByRoomId.put(roomId,booking);
        ServiceType serviceType = ServiceType.BAR_FRIDGE;
        doNothing().when(booking).addServiceCharge(serviceType,cost);
        //Act and assert.
        hotel.addServiceCharge(roomId,serviceType,cost);

    }
}