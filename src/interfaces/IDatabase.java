package interfaces;
import java.sql.ResultSet;

import clientside.Customer;
import clientside.Driver;
import clientside.User;
import communication.CreateAccount;
import communication.Job;
import communication.JobSearch;
import communication.Login;
import communication.Message;

/**interface for all database methods
 * 
 * @author Max Elliott
 * @created 2017-03-02
 * @version 2017-03-06
 */
public interface IDatabase {
	
	/**Creating a new customer account
	 * 
	 * @param c Customer containing first name, last name, email, password, telephone number
	 * @return Message: id = "ACCOUNT CREATED" or "EMAIL IN USE", data = Customer object for new account
	 */
	public Message createAccount(CreateAccount acc);
	
	/**Login attempt from customer OR driver.
	 * 
	 * @param login Login object containing username and password
	 * @return Message: id = "LOGIN SUCCESSFUL" or "LOGIN FAILED", data = Customer or Driver object, or nothing
	 */
	public Message loginQuery(Login login);
	
	public Message logoutQuery(User user);
	
	
	
	
	/**Driver searching for jobs
	 * 
	 * @param searchJob object containing previous search jobIDs.
	 * @return Message: id = "JOB FOUND" or "NO CURRENT JOBS", data = Job object of the lowest ID job in database after the jobID in searchJob
	 */
	public Message searchJobsQuery(JobSearch search);
	
	/**Driver wanting to accept a job
	 * 
	 * @param j job to be accepted, contains driver's ID
	 * @return Message: id = "JOB ACCEPTED" or "JOB ALREADY TAKEN", no data
	 */
	public Message acceptJobQuery(Job j);
	
	/**Driver indicating a job has been completed.
	 * 
	 * @param j job that has been completed
	 * @return Message: id = "JOB COMPLETE", //needs to implement a rating system
	 */
	
	public Message setAvailable(Driver d);
	
	public Message setUnavailable(Driver d);
	
	/**Driver requesting their job history
	 * 
	 * @param d Driver performing request
	 * @return Message: id = "DRIVER HISTORY", data = ArrayList<Job> of all previous jobs by the driver
	 */
	public Message requestHistoryQuery(Driver d);
	
	/**Customer requesting their ride history
	 * 
	 * @param c Customer performing request
	 * @return Message: id = "CUSTOMER HISTORY", data = ArrayList<Job> of all previous rides by the customer
	 */
	public Message requestHistoryQuery(Customer c);
	
	/**Customer requesting a ride.
	 * 
	 * @param j job with start, end, and customer
	 * @return Message: id - "RIDE REQUEST RECIEVED", data = nothing?
	 */
	public Message requestRideQuery(Job j);
	
	public Message rateCustomer(Job j);
	
	public Message rateDriver(Job j);
	
	public Message setStarted(Job job);
	
	public Message setFinished(Job job);
	
	public Message getMyRating(User user);
	
	public Message checkAccepted(Job job);
	
	public Message checkStatus(Customer c);
	
	public Message checkStatus(Driver d);
	
	public Message editAccountQuery(CreateAccount acc);

}
