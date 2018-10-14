package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import hotel.checkout.CheckoutCTL;
import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType;
import hotel.entities.ServiceType;
import hotel.service.RecordServiceCTL;

public class TestCheckedOutRoomSC {

	@Test
	public void testCheckedOutRoomServiceCharge() {
		
		//MUhammad Ahmed Shoaib
		//11628053
		//This is an automated test for the second bug which is that the user can still
		//add the service charges for a room after the guest has been checked out.
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
	        Guest guest = new Guest("Muhammad Ahmed Shoaib", "14 Wilson St", 1111);
	       CreditCard card = new CreditCard(CreditCardType.MASTERCARD, 4, 4);

	        cNum = hotel.book(room, guest, date, 1, 1, card);
	        hotel.checkin(cNum);
	        
	        rsCTL = new RecordServiceCTL(hotel);
	        rsCTL.roomNumberEntered(room.getId());
	        rsCTL.serviceDetailsEntered(ServiceType.ROOM_SERVICE,100);
	        
	  
	        coCTL = new CheckoutCTL(hotel);
	        coCTL.setRoomState();
	        coCTL.roomIdEntered(room.getId());
	        rsCTL = new RecordServiceCTL(hotel);
	        rsCTL.roomNumberEntered(room.getId());
	        coCTL.chargesAccepted(true);
	        coCTL.creditDetailsEntered(CreditCardType.MASTERCARD,1,1);
	        coCTL.completed();

	        rsCTL = new RecordServiceCTL(hotel);
	        rsCTL.roomNumberEntered(room.getId());
	        rsCTL.serviceDetailsEntered(ServiceType.RESTAURANT,50);
	    
	}

}
