package hotel.test;
/**
 * The class demonstrates the second buggy behavior  also showing 1st one which is recording service charge even after checking out 
 * @author ChittyVaishnav
 * @studentId 11639078
 */
import hotel.HotelHelper;
import hotel.checkout.CheckoutCTL;
import hotel.entities.Hotel;
import hotel.service.RecordServiceCTL;

public class RecordTestCase2 {
	private static Hotel hotel;
	public static void main(String[] args) throws Exception {
		//Loading the basic class
		hotel = HotelHelper.loadHotel();
		//Checking out the user
		new CheckoutCTL(hotel).run();
		//Running the record service to check add service charge
		new RecordServiceCTL(hotel).run();
		
	}
}
