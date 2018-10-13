package hotel.test;
/**
 * The class is used to demonstrate the buggy behavior where service charges are not charged after checkout
 * @author ChittyVaishnav
 * @StudenId 11639078
 */
import hotel.HotelHelper;
import hotel.checkout.CheckoutCTL;
import hotel.entities.Hotel;
import hotel.service.RecordServiceCTL;

public class RecordTestCase {
	private static Hotel hotel;

	public static void main(String[] args) throws Exception {
		//Loading  a sample hotel room
		hotel = HotelHelper.loadHotel();
		//Running the record service to check add service charge
		new RecordServiceCTL(hotel).run();
		//Checking whether the service charge is applied to it after checking out
		new CheckoutCTL(hotel).run();
	}

}
