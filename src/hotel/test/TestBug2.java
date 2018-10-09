package hotel.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;

import hotel.checkout.CheckoutCTL;
import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.Booking;
import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType;
import hotel.entities.ServiceCharge;
import hotel.entities.ServiceType;
import hotel.service.RecordServiceCTL;
import hotel.utils.IOUtils; 

public class TestBug2 {
	Hotel hotel;
	Date date;
	int stayLength = 5;
	Room room;
	Guest guest;
	CreditCard card;
	CheckoutCTL checkoutCTL;
	RecordServiceCTL recordServiceCTL;
	long confNo;
	
	@Before 
    public void setUp() throws Exception {
		//create prerequisite components 
		hotel = new Hotel();
		hotel.addRoom(RoomType.TWIN_SHARE, 301);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		date = format.parse("01-01-2019");
		
		guest = new Guest("Huseyin", "Caliskan", 424043907);
		card = new CreditCard(CreditCardType.VISA, 2, 123);
		
		room = hotel.findAvailableRoom(RoomType.TWIN_SHARE, date, 1);
		//create a booking and checkin before testing creditDetailsEntered method
		confNo = hotel.book(room, guest, date, 1, 2, card);
		hotel.checkin(confNo);
		
		//create checkout control and record service control instances
		checkoutCTL = new CheckoutCTL(hotel);
		recordServiceCTL = new RecordServiceCTL(hotel);
	}
	
	@After 
    public void tearDown() throws Exception {
		//remove references for the created global objects
		card = null;
		guest = null;
		room = null;
		hotel = null;
		checkoutCTL = null;
	}
	
	
	@Test 
    public void testBug2_0() throws Exception {
		//change state to room to prevent user input
		checkoutCTL.setStateToRoom();
		IOUtils.outputln("\nTrace record for system output for checkout process");
		//give the room id of the booking above
		checkoutCTL.roomIdEntered(room.getId());
		//accept the charges to continue testing
		checkoutCTL.chargesAccepted(true);
		//enter the credit card details
		checkoutCTL.creditDetailsEntered(card.getType(), card.getNumber(), card.getCcv());
		//Testing the expected behaviour
		IOUtils.outputln("\nMock method to test the condition for expected error message to be called");
		Booking booking = recordServiceCTL.testBug2(room.getId());
		//Checking for trace if the booking is null
		if (booking != null)
			IOUtils.outputln("Expected null but an active booking has been found");
		else
			IOUtils.outputln("Returns null as expected");
		assertEquals(null, booking);
	
	}
	
	@Test 
    public void testBug2_1() throws Exception {
		IOUtils.outputln("\nTrace record for Hotel class checkout method:");	
		//directly checkout the room from the hotel class
		hotel.checkout(room.getId());
		//Testing the expected behaviour
		IOUtils.outputln("\nMock method to test the condition for expected error message to be called");
		Booking booking = recordServiceCTL.testBug2(room.getId());
		//Checking for trace if the booking is null
		if (booking != null)
			IOUtils.outputln("Expected null but an active booking has been found");
		else
			IOUtils.outputln("Returns null as expected");
		
		assertEquals(null, booking);
	}
}

