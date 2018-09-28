import hotel.checkout.CheckoutCTL;
import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.*;
import hotel.service.RecordServiceCTL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Bikram Shrestha
 *         11645312
 *         ITC515 Professional Programming Practices
 *         Assessment 4
 * This test is carried out to replicate the bug that cause
 * the service charge to be zero while performing the
 * hotel check out use case. The test is performed without any
 * user input and all the methods call are automated. As there
 * is no method that can be used to check the charge of the
 * room without changing the code, the print output is taken
 * as a confirmation of the bugs that has been reported.
 * */
public class BugZeroCharge {
    Hotel hotel;
    Date date;
    Room room;
    Guest guest;
    CreditCard card;
    CheckoutCTL checkoutCTL;
    RecordServiceCTL recordServiceCTL;
    int stayLength;
    int occupantNumber;
    int cost;
    long confirmationNumber;

    /*
    Setting up variables that are common to all the test.
     */
    @Before
    public void setUp() {
        stayLength = 2;
        occupantNumber =2;
        cost = 10;
        stayLength = 2;
        hotel = new Hotel();
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
        date = null;
        recordServiceCTL = null;
        confirmationNumber = 0;
        cost = 0;
        stayLength = 0;
    }


    /*
    This test use the all the variable that was used to conduct a UAT Test
    case 1 and it is able to replicate the bug as expected.
     */
    @Test
    public void testBugZeroChargeCase1() {
        // Arrange
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = format.parse("28-09-2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        room = new Room(201,RoomType.DOUBLE);
        guest = new Guest("Bikram", "12 Home", 123);
        card = new CreditCard(CreditCardType.MASTERCARD, 1, 1);

        // This portion of code Book a Room and Check in.
        confirmationNumber = hotel.book(room, guest, date, stayLength, 2, card);
        hotel.checkin(confirmationNumber);

        //Act
        //This portion of code add service or record service to the checked room.
        recordServiceCTL = new RecordServiceCTL(hotel);
        recordServiceCTL.roomNumberEntered(room.getId());
        recordServiceCTL.serviceDetailsEntered(ServiceType.ROOM_SERVICE,200);
        recordServiceCTL = new RecordServiceCTL(hotel);
        recordServiceCTL.roomNumberEntered(room.getId());
        recordServiceCTL.serviceDetailsEntered(ServiceType.BAR_FRIDGE,200);

        //Assert
        //This portion of code will try to check out from hotel.
        checkoutCTL = new CheckoutCTL(hotel);
        checkoutCTL.setStateRoom();
        checkoutCTL.roomIdEntered(room.getId());
    }


    /*
    This test use the all the variable that was used to conduct a UAT Test
    case 2 and it is able to replicate the bug as expected.
   */
    @Test
    public void testBugZeroChargeCase2() {
        //Arrange
        room = new Room(101,RoomType.SINGLE);
        guest = new Guest("Romeo", "12 Hotel", 321);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = format.parse("28-09-2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        card = new CreditCard(CreditCardType.VISA, 2, 2);

        // This portion of code Book a Room and Check in.
        confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
        hotel.checkin(confirmationNumber);

        //Act
        //This portion of code add service or record service to the checked room.
        recordServiceCTL = new RecordServiceCTL(hotel);
        recordServiceCTL.roomNumberEntered(room.getId());
        recordServiceCTL.serviceDetailsEntered(ServiceType.RESTAURANT,200);
        recordServiceCTL = new RecordServiceCTL(hotel);
        recordServiceCTL.roomNumberEntered(room.getId());
        recordServiceCTL.serviceDetailsEntered(ServiceType.ROOM_SERVICE,200);

        //Assert
        // This portion of code Book a Room and Check in.
        checkoutCTL = new CheckoutCTL(hotel);
        checkoutCTL.setStateRoom();
        checkoutCTL.roomIdEntered(room.getId());
    }

    /*
    This test use the all the variable that was used to conduct a UAT Test
    case 3 and it is able to replicate the bug as expected.
   */
    @Test
    public void testBugZeroChargeCase3() {

        //Arrange
        room = new Room(201,RoomType.DOUBLE);
        guest = new Guest("Chitra", "21 Home", 111);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = format.parse("28-09-2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        card = new CreditCard(CreditCardType.VISA, 2, 2);

        //Act
        // This portion of code Book a Room and Check in.
        confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
        hotel.checkin(confirmationNumber);
        recordServiceCTL = new RecordServiceCTL(hotel);
        recordServiceCTL.roomNumberEntered(room.getId());
        recordServiceCTL.serviceDetailsEntered(ServiceType.BAR_FRIDGE,200);
        recordServiceCTL = new RecordServiceCTL(hotel);
        recordServiceCTL.roomNumberEntered(room.getId());
        recordServiceCTL.serviceDetailsEntered(ServiceType.ROOM_SERVICE,200);

        //Assert
        // This portion of code Book a Room and Check in.
        checkoutCTL = new CheckoutCTL(hotel);
        checkoutCTL.setStateRoom();
        checkoutCTL.roomIdEntered(room.getId());
    }

    /*
    In this test case 4 all the variables that is required to conduct a test is randomized
    using the loop and the test is been run 20 times in a loop. This test is able to replicate
    the bug that was reported.
     */
    @Test
    public void testBugZeroChargeCase4(){
        for(int i = 1; i < 21; i++){
            //Arrange
            room= new Room(i,RoomType.DOUBLE);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            guest= new Guest("user"+ i, "address" + i, 100 + i);
            try {
                date = format.parse("28-09-2018");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            stayLength = i;
            card = new CreditCard(CreditCardType.VISA,1,1);

            // This portion of code Book a Room and Check in.
            confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
            hotel.checkin(confirmationNumber);

            //Act
            //This portion of code add service or record service to the checked room.
            recordServiceCTL = new RecordServiceCTL(hotel);
            recordServiceCTL.roomNumberEntered(room.getId());
            recordServiceCTL.serviceDetailsEntered(ServiceType.BAR_FRIDGE,10 + i);
            recordServiceCTL = new RecordServiceCTL(hotel);
            recordServiceCTL.roomNumberEntered(room.getId());
            recordServiceCTL.serviceDetailsEntered(ServiceType.ROOM_SERVICE,50 + i);
            recordServiceCTL = new RecordServiceCTL(hotel);
            recordServiceCTL.roomNumberEntered(room.getId());
            recordServiceCTL.serviceDetailsEntered(ServiceType.RESTAURANT,150 + i);

            //Assert
            // This portion of code Book a Room and Check in.
            checkoutCTL = new CheckoutCTL(hotel);
            checkoutCTL.setStateRoom();
            checkoutCTL.roomIdEntered(room.getId());
        }
    }

}