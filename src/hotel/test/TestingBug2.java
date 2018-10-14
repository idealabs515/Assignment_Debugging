package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.Booking;
import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType;


public class TestingBug2 {

	@Test
	public void CheckoutTest() {
		Hotel hotel =new Hotel();
	    Date date =null;
	   
	    long cNum;
		 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	        try {
	            date = format.parse("20-11-2018");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        Guest guest = new Guest("Muhammad Ahmed Shoaib", "14 Wilson St", 1111);
		       CreditCard card = new CreditCard(CreditCardType.MASTERCARD, 4, 4);
	        
	        hotel.addRoom(RoomType.SINGLE,101);
	        Room room;
	        room = hotel.findAvailableRoom(RoomType.SINGLE,date,1);
	       
	       
  
	       cNum = hotel.book(room, guest, date, 1, 1, card);
	        hotel.checkin(cNum);
	        Booking booking  = hotel.findActiveBookingByRoomId(room.getId());
	        hotel.checkout(room.getId());

	        boolean isCheckedOut = booking == null;

	        assertEquals(true, isCheckedOut);
	       
	}

}
