package hotel.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Author: Bikram Shrestha
 *         11645312
 *         ITC515 Professional Programming Practices
 *         Assessment 4
 * This is the Integration test of the Hotel entities. This test
 * shows that the bug persist and must be problem within the
 * method call made in Hotel.checkOut() method.
 * */
public class HotelBug2Test {

    Hotel hotel;
    Guest guest;
    Booking booking;
    CreditCard card;
    Room room;
    Date date;
    int stayLength;
    int occupantNumber;
    public Map<Long, Booking> bookingsByConfirmationNumber;
    public Map<Integer, Booking> activeBookingsByRoomId;


    /*
    All the variables that is created in the before seems to be used frequently
    by all the methods to function. So, instead of writing the same code again
    and again, it is being provided as a default items to be created before any
    test case is conducted.
     */
    @Before
    public void setUp() throws Exception {
        bookingsByConfirmationNumber = new HashMap<>();
        activeBookingsByRoomId = new HashMap<>();

        stayLength = 4;
        occupantNumber = 2;
        guest = new Guest("Bikram", "12 Home", 123);
        hotel = new Hotel();
        hotel.addRoom(RoomType.SINGLE, 101);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        date = format.parse("01-10-2018");
        room = hotel.findAvailableRoom(RoomType.SINGLE, date, stayLength);
    }


    /*
    All the information that was generated after test is reset to null or 0 as
    suitable for object type to eliminate any tampering the preceding test.
     */
    @After
    public void tearDown() {
        hotel = null;
        bookingsByConfirmationNumber = null;
        activeBookingsByRoomId = null;
        guest = null;
        booking = null;
        card = null;
        room = null;
        date = null;
        stayLength = 0;
        occupantNumber = 0;
    }


    /*
     checkOut() method should remove booking. This test verify whether
     this has been carried out.
     */
    @Test
    public void testCheckoutCheckedOut() {
        //Arrange
        long confirmationNumber = hotel.book(room, guest, date, stayLength, occupantNumber, card);
        hotel.checkin(confirmationNumber);
        int roomId = room.getId();
        booking  = hotel.findActiveBookingByRoomId(roomId);
        hotel.checkout(roomId);

        //Act
        boolean isCheckedOut = booking == null;
        System.out.println("Booking is checked out: " +  (booking==null));


        //Assert
        assertEquals(true, isCheckedOut);
    }
}