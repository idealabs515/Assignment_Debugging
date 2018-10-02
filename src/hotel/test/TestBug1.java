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
	
}

