package clientside;
import java.util.Arrays;

/** 
 * Customer.java
 * Customer class for BrumBrum containing details of that driver.
 * @author Robert Campbell
 * @version 2017-02-24
 */
public class Customer extends User {
	
	public Customer(String firstName, String lastName, String email, String telNumber) {
		super(firstName, lastName, email, telNumber);
	}
	
	public Customer(int id, String message) {
		super(id, message);
	}

	@Override
	public String toString() {
		return "Customer [getFirstName()=" + getFirstName() + ", getLastName()="
				+ getLastName() + ", getState()=" + getState() + ", getRating()=" + getRating() + ", getHistory()="
				+ Arrays.toString(getHistory()) + ", getEmail()=" + getEmail() + ", getTelNumber()=" + getTelNumber()
				+ ", getId()=" + getId() + "]";
	}
}
