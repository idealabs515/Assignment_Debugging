package test;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;


import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.Booking;
import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType;
import hotel.entities.ServiceCharge;
import hotel.entities.ServiceType;


public class TestingBug1 {

	@Test
	public void addServiceChargesTest() {
		Hotel hotel =new Hotel();
	    Date date =null;
	    List<ServiceCharge> charges;
	    long cNum;
		 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	        try {
	            date = format.parse("20-11-2018");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        hotel.addRoom(RoomType.SINGLE,101);
	        Room room;
	        room = hotel.findAvailableRoom(RoomType.SINGLE,date,1);
	        Guest guest = new Guest("Muhammad Ahmed Shoaib", "14 Wilson St", 1111);
	       CreditCard card = new CreditCard(CreditCardType.MASTERCARD, 4, 4);
	       Booking booking = new Booking(guest,room,date,1,1,card);

        double cost = 100;
       
        booking.addServiceCharge(ServiceType.ROOM_SERVICE,cost);

        charges = booking.getCharges();

        for(ServiceCharge charge: charges){
            if(charge.getCost()==cost){
                System.out.println("successful");
            }
            else{
                System.out.println("not successful");
            }
        }
    }
	}


