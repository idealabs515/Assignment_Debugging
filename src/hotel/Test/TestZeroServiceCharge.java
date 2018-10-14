package test;

import java.text.SimpleDateFormat;
import java.util.Date;

import hotel.checkout.CheckoutCTL;
import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.*;
import hotel.service.RecordServiceCTL;

import org.junit.Test;

public class TestZeroServiceCharge {
//MUhammad Ahmed Shoaib
//11628053
//This is an automated test for the first bug which is when the user adds service charge 
//to a room but it still does not add in checkout

	@Test
	public void SimplificationAutomatedTest() {
		 Hotel hotel =new Hotel();
		    Date date =null;
		    CheckoutCTL coCTL;
		    RecordServiceCTL rsCTL;
		    long cNum;
		    
		  SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	        try {
	            date = format.parse("20-11-2018");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	       Room room = new Room(101,RoomType.DOUBLE);
	        Guest guest = new Guest("Muhammad Ahmed Shoaib", "14 wilson st", 1111);
	        CreditCard card = new CreditCard(CreditCardType.MASTERCARD, 4, 4);

	       
	        cNum = hotel.book(room, guest, date, 1, 1, card);
	        hotel.checkin(cNum);

	        rsCTL = new RecordServiceCTL(hotel);
	        rsCTL.roomNumberEntered(room.getId());
	        rsCTL.serviceDetailsEntered(ServiceType.ROOM_SERVICE,100);

	        coCTL = new CheckoutCTL(hotel);
	        coCTL.setRoomState();
	        coCTL.roomIdEntered(room.getId());
	    
		
	}

}
