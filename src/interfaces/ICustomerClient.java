package interfaces;
import interfaces.IClient;

import java.util.List;

import communication.Job;
import communication.Message;

/**
 * Interface for the Customer Client class.
 * @author jpcryne
 * @version 2017-02-24
 */
public interface ICustomerClient extends IClient {
	
	/**
	 * Allows a user to create an account
	 * @param customer, Customer object
	 * @return boolean of success
	 */
	public String createAccount(String firstName, String lastName, String email, String telNumber, String password);

	
	/**
	 * Allows a user to logout of their account.
	 * @return boolean of success
	 */
	public boolean logout();
	
	/**
	 * Allows a user to request a pickup
	 * @param from, String of location
	 * @param to, String of location
	 * @param jobType, package or person
	 * @param details, the details of the job
	 * @return boolean, server connected.
	 */
	public Message requestPickup(String from, String to, String jobType, String details);
	
	/**
	 * Gives the driver of the job a rating.
	 * @param rating, int from 1 to 5.
	 * @param job, the Job object being rated.
	 * @return boolean, server connected.
	 */
	public boolean rateDriver(Job job);
	
	/**
	 * Returns all the previous jobs involving the client.
	 * @return array of the jobs.
	 */
	public List<Job> requestHistory();
	
	
	public Message checkStatus();
}
