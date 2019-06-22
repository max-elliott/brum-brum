package interfaces;

import java.util.List;

import communication.Job;
import communication.Message;

/**
 * The interface for the Client class.
 * @author jpcryne
 *
 */
public interface IClient {
	/**
	 * Allows a user to create an account
	 * @param customer, Customer object
	 * @return boolean of success
	 */
	public String createAccount(String firstName, String lastName, String email, String telNumber, String password);
	
	/**
	 * Allows a user to login to their account.
	 * @param username, String
	 * @param password, String
	 * @param type, String
	 * @return boolean of success.
	 */
	public boolean login(String username, String password, String type);
	
	/**
	 * Allows a user to logout of their account.
	 * @return boolean of success
	 */
	public boolean logout();
	
	/**
	 * Returns all the previous jobs involving the client.
	 * @return array of the jobs.
	 */
	public List<Job> requestHistory();
	
	/**
	 * Gets Rating from the Server.
	 * @return rating, String
	 */
	public String getRating();
	
	/**
	 * Allows a user to edit their account information
	 * @param user
	 * @return success, String
	 */
	public String editUser(String firstName, String lastName, String email, String telNumber, String password);


}

