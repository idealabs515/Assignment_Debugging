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

public class TestBug1 {
	Hotel hotel;
	Date date;
	int stayLength = 5;
	Room room;
	Guest guest;
	CreditCard card;
	CheckoutCTL checkoutCTL;
	RecordServiceCTL recordServiceCTL;
	
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
		long confNo = hotel.book(room, guest, date, 1, 2, card);
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
    public void testBug1_0() throws Exception {
		//set room id for record service control class
		recordServiceCTL.roomNumberEntered(room.getId());
		double serviceFeeExpected = 7.00;
		IOUtils.output("Service charge system Output which equals to expected value");
		//add service charge to the room given above
		recordServiceCTL.serviceDetailsEntered(ServiceType.ROOM_SERVICE, serviceFeeExpected);
		//get the added service fee from checkout control class
		double serviceFee = checkoutCTL.getFirstServiceCharge(room.getId());
		IOUtils.output("Trace record for RecordServiceCTL class serviceDetailsEntered method:");	
		String mesg = String.format("\nCharge Listed for Room %d for %s is $%.2f\n", room.getId(), ServiceType.ROOM_SERVICE.getDescription(), serviceFee);
		IOUtils.outputln(mesg);	
		assertEquals(serviceFeeExpected, serviceFee, 0);
	}

	@Test 
    public void testBug1_1() throws Exception {
		double serviceFeeExpected = 7.00;
		//directly adding the service charge from hotel class
		hotel.addServiceCharge(room.getId(), ServiceType.ROOM_SERVICE, serviceFeeExpected);
		//get the added service fee from checkout control class
		double serviceFee = checkoutCTL.getFirstServiceCharge(room.getId());
		IOUtils.output("Trace record for Hotel class addServiceCharge method:");
		String mesg = String.format("\nCharge Listed for Room %d for %s is $%.2f\n", room.getId(), ServiceType.ROOM_SERVICE.getDescription(), serviceFee);
		IOUtils.outputln(mesg);	
		assertEquals(serviceFeeExpected, serviceFee, 0);
	}

	@Test 
    public void testBug1_2() throws Exception {

		double serviceFeeExpected = 7.00;
		Booking booking = hotel.findActiveBookingByRoomId(room.getId());
		//directly adding the service charge from booking class
		booking.addServiceCharge(ServiceType.ROOM_SERVICE, serviceFeeExpected);
		//get the added service fee from checkout control class
		double serviceFee = checkoutCTL.getFirstServiceCharge(room.getId());
		IOUtils.output("Trace record for Booking class addServiceCharge method:");
		String mesg = String.format("\nCharge Listed for Room %d for %s is $%.2f\n", room.getId(), ServiceType.ROOM_SERVICE.getDescription(), serviceFee);
		IOUtils.outputln(mesg);	
		assertEquals(serviceFeeExpected, serviceFee, 0);
	}
	
	@Test 
    public void testBug1_3() throws Exception {
		double serviceFeeExpected = 7.00;
		//testing the constructor for service charge class
		ServiceCharge serviceCharge = new ServiceCharge(ServiceType.ROOM_SERVICE, serviceFeeExpected);
		//get the service fee from Service charge class
		double serviceFee = serviceCharge.getCost();
		IOUtils.output("Trace record for ServiceCharge class constructor:");
		String mesg = String.format("\nCharge Listed for Room %d for %s is $%.2f\n", room.getId(), ServiceType.ROOM_SERVICE.getDescription(), serviceFee);
		IOUtils.outputln(mesg);	
		assertEquals(serviceFeeExpected, serviceFee, 0);
	}
}

