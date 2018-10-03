package hotel.entities;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Author: Bikram Shrestha
 *         11645312
 *         ITC515 Professional Programming Practices
 *         Assessment 4
 * This is the Integration test of the Booking entities. This test
 * shows that the service charge object has not been added to the list
 * of ServiceCharge after calling the method as expected. So this
 * method Booking.addServiceCharge is not working as expected. By looking
 * at the code there seems to be mistake made on naming parameter and variable
 * used within the method.
 * */
public class BookingTest {
    List<ServiceCharge> charges;
    Room room;
    Booking booking;
    Date date;
    Hotel hotel;
    Guest guest;
    CreditCard card;
    int stayLength;
    int occupantNumber;

    int phoneNumber;

    /*
    Setting up variables that are common to all the test.
    */
    @Before
    public void setUp() throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        date = format.parse("02-10-2018");

        stayLength = 4;
        occupantNumber = 2;
        phoneNumber = 123;

        hotel = new Hotel();
        hotel.addRoom(RoomType.DOUBLE,201);
        hotel.addRoom(RoomType.TWIN_SHARE, 301);
        room = hotel.findAvailableRoom(RoomType.DOUBLE,date,stayLength);
        guest = new Guest("Bikram", "12 Home", phoneNumber);
        card = new CreditCard(CreditCardType.VISA,1,1);
        booking = new Booking(guest,room,date,stayLength,occupantNumber,card);
    }

    /*
    All the information that was generated after test is reset to null or 0 as
    suitable for object type to eliminate any tampering the preceding test.
     */
    @After
    public void tearDown()   {
        booking = null;

    }

    /*
    This test shows that the method is not working as expected. The variable
    declared in the parameter is not being used within the method due to
    spelling mistake made.
     */
    @Test
    public void addServiceChargeExceptionTest() {
        //Arrange
        double barCost = 100;
        //Act
        booking.addServiceCharge(ServiceType.BAR_FRIDGE,barCost);

        //Assert
        charges = booking.getCharges();

        for(ServiceCharge charge: charges){
            if(charge.getCost()==barCost){
                System.out.println("Charge is added successfully");
            }
            else{
                System.out.println("Charge is not added successfully");
            }
        }
    }
}