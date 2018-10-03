package hotel.checkout;

import hotel.entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Author: Bikram Shrestha
 *         11645312
 *         ITC515 Professional Programming Practices
 *         Assessment 4
 * This is the test that is conducted to prove that there is no
 * error while executing the roomIdEntered() method in the
 * CheckoutCTL class by conducting Unit test of roomIdEntered().
 * */
public class CheckoutCTLTest {
    Date date;
    int stayLength;
    int occupantNumber;
    long confirmationNumber;

    @Mock Hotel hotel;
    @Mock Guest guest;
    @Mock Booking booking;

    @InjectMocks CheckoutCTL checkoutCTL;

    /*
    Setting up variables that are common to all the test.
    */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        stayLength = 2;
        occupantNumber =2;
        stayLength = 2;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = format.parse("30-09-2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
    All the information that was generated after test is reset to null or 0 as
    suitable for object type to eliminate any tampering the preceding test.
     */
    @After
    public void tearDown() {
        checkoutCTL = null;
        date = null;
        confirmationNumber = 0;
        stayLength = 0;
    }
    /*
    This test is performed to show that it will throw RuntimeException if it
    is in different state.
     */
    @Test(expected = RuntimeException.class) //Assert
    public void testRoomIdEnteredThrowRuntimeException() {
        //Arrange
        int roomId = 101;
        //Act
        checkoutCTL.roomIdEntered(roomId);
    }

    /*
    This test is performed to show that if booking is null then it will present
    with No active booking found notification.
     */
    @Test
    public void testRoomIdEnteredNoBooking() {
        //Arrange
        int roomId = 101;
        booking = null;
        //Act
        when(hotel.findActiveBookingByRoomId(roomId)).thenReturn(null);
        checkoutCTL.setStateRoom();
        //Assert
        checkoutCTL.roomIdEntered(roomId);
    }

    /*
    This test is performed to show that the method is performing as intended.
     */
    @Test
    public void testRoomIdEnteredShowCharges() {
        //Arrange
        List<ServiceCharge> charges = new ArrayList<>();
        charges.add(new ServiceCharge(ServiceType.ROOM_SERVICE,100));
        charges.add(new ServiceCharge(ServiceType.BAR_FRIDGE,200));
        int roomId = 101;
        long mockedConfirmationNumber = 25102018201L;
        //Act
        when(hotel.findActiveBookingByRoomId(roomId)).thenReturn(booking);
        when(booking.getConfirmationNumber()).thenReturn(mockedConfirmationNumber);
        when(booking.getArrivalDate()).thenReturn(date);
        when(booking.getGuest()).thenReturn(guest);
        when(guest.getName()).thenReturn("MockedName");
        when(guest.getAddress()).thenReturn("MockedAddress");
        when(guest.getPhoneNumber()).thenReturn(123);
        when(booking.getCharges()).thenReturn(charges);
        checkoutCTL.setStateRoom();
        //Assert
        checkoutCTL.roomIdEntered(roomId);
    }

}