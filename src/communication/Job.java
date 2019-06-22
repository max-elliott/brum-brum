package communication;
import java.io.Serializable;

import clientside.Customer;
import clientside.Driver;

/**
 * Class containing all information about a job.
 * (to/from/driver id/customer id/time/active?/completed?)
 * @author jpcryne
 * @version 2017-03-02
 * @created 2017-02-24
 */
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;
	private String startLocation, endLocation, startTime, state, jobType, endTime;
	private int driverRating, jobID;
	private int customerRating;
	private String details = "";
	private Customer customer;
	private Driver driver;
	
	/**
	 * Constructor for the posting of a job by a customer.
	 * @param to, String
	 * @param from, String
	 * @param customerID, int
	 */
	public Job(String to, String from, String jobType, Customer customer, String details) {
		this.startLocation = from;
		this.endLocation = to;
		this.jobType = jobType;
		this.customer = customer;
		this.details = details;
		this.state = "requesting!";
	}
	
	/**
	 * Constructor for the posting of a job by a customer.
	 * @param to, String
	 * @param from, String
	 * @param customerID, int
	 */
	public Job(String to, String from, String jobType, Customer customer) {
		this.startLocation = from;
		this.endLocation = to;
		this.jobType = jobType;
		this.customer = customer;
		this.state = "requesting!";
	}
	
	public Job(String to, String from, Driver driver) {
		this.startLocation = from;
		this.endLocation = to;
		this.driver = driver;
	
	}
	
	public Job() {}
	
	public Job(String to, String from, String jobType, Customer customer, int rating, int jobID){
		this.startLocation = from;
		this.endLocation = to;
		this.jobType = jobType;
		this.customer = customer;
		this.state = "requesting!!";
		this.customerRating = rating;
		this.jobID = jobID;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	/**
	 * Job object for Customer.
	 * @param to
	 * @param from
	 */
	public Job(String to, String from) {
		this.startLocation = from;
		this.endLocation = to;
	}
	
	/**
	 * Sets the driverID for a job.
	 * @param driverID
	 */
	public void setDriver(Driver driver) { //Should this be synchronised?
		this.driver = driver;
	}
	
	/**
	 * Sets the state.
	 * @param newState
	 */
	public void setState(String newState) {
		this.state = newState;
	}
	
	/**
	 * Sets the job's start time.
	 * @param startTime, String
	 */
	public void setStartTime(String startTime) { 
		this.startTime = startTime;
	}

	/**
	 * @return to, String
	 */
	public String getTo() {
		return endLocation;
	}

	/**
	 * @return from, String
	 */
	public String getFrom() {
		return startLocation;
	}

	/**
	 * @return startTime, String
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @return the driverID, int
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * @return the customerID, int
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * Returns the current state of a Job.
	 * @return state, String
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * @return the driverRating
	 */
	public int getDriverRating() {
		return driverRating;
	}

	/**
	 * @param driverRating the driverRating to set
	 */
	public void setDriverRating(int driverRating) {
		this.driverRating = driverRating;
	}
	
	/**
	 * Retrieves JobID
	 * @return String
	 */
	public int getJobID() {
		return jobID;
	}
	/**
	 * Sets the driverID for a job.
	 * @param driverID
	 */
	public void setJobID(int jobID) {
		this.jobID = jobID;
	}
	/**
	 * Sets the JobType for a job.
	 * @param JobType
	 */
	public void setJobType(String jobType) {
		this.jobType= jobType;
	}
	/**
	 * Retrieves JobType
	 * @return String
	 */
	public String  getJobType() {
		return jobType;
	}	
	
	/**
	 * Sets the details for a job.
	 * @param details
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * Retrieves JobType
	 * @return String
	 */
	public String  getDetails() {
		return details;
	}
	/**
	 * The getter for endTime
	 * @return String
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * Setter for the endTime
	 * @param String
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * The getter for customerRating
	 * @return int
	 */
	public int getCustomerRating() {
		return customerRating;
	}

	/**
	 * Setter for the customerRating
	 * @param int
	 */
	public void setCustomerRating(int customerRating) {
		this.customerRating = customerRating;
	}
	
	
	
}