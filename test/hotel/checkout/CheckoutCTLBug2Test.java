package hotel.checkout;


import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.Booking;
import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType;


/**
 * Author: Bikram Shrestha
 *         11645312
 *         ITC515 Professional Programming Practices
 *         Assessment 4
 * This is the Integration test of the CheckoutCTL entities. This test
 * shows that the bug persist and must be problem within the
 * method call made in CheckoutCTL.creditDetailsEntered() method.
 * */
public class CheckoutCTLBug2Test {
    Hotel hotel;
    Date date;
    Room room;
    Guest guest;
    CreditCard card;
    CheckoutCTL checkoutCTL;
    int stayLength;

    /*
    Setting up variables that are common to all the test.
     */
    @Before
    public void setUp() throws Exception {
        hotel = new Hotel();
        hotel.addRoom(RoomType.SINGLE, 101);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        date = format.parse("01-10-2018");
        room = hotel.findAvailableRoom(RoomType.SINGLE, date, stayLength);
        guest = new Guest("Bikram", "12 Home", 123);
        card = new CreditCard(CreditCardType.VISA, 2, 2);
        checkoutCTL = new CheckoutCTL(hotel);
    }

    /*
    All the information that was generated after test is reset to null or 0 as
    suitable for object type to eliminate any tampering the preceding test.
     */
    @After
    public void tearDown() {
        card = null;
        guest = null;
        room = null;
        hotel = null;
        checkoutCTL = null;
        stayLength = 0;
    }

    /*
     creditDetailsEntered() method should have remove booking. This test verify whether
     this has been carried out.
     */
    @Test
    public void testBookingRemovedAfterPayment() {
        //Arrange
        checkoutCTL.setStateRoom();
        long confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
        //Act
        hotel.checkin(confirmationNumber);
        checkoutCTL.roomIdEntered(room.getId());
        checkoutCTL.chargesAccepted(true);
        checkoutCTL.creditDetailsEntered(card.getType(), card.getNumber(), card.getCcv());
        //Assert
        Booking booking = hotel.findBookingByConfirmationNumber(confirmationNumber);
        System.out.println("Booking is checked out: " +  (booking==null));
        assertEquals(true,booking == null);
    }

}