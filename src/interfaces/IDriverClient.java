package interfaces;

import java.util.List;

import communication.*;

/**
 * Interface for Driver Client class
 * @author Rob Campbell
 * @version 2017-02-24
 */
public interface IDriverClient extends IClient{
	
	/**
	 *  Allows a driver to logout of their account
	 * @return boolean of success
	 */
	public boolean logout();
	
	/**
	 * Allows a driver to request a new job from the 
	 * server's available jobs
	 * @return a Job object containing the details of the job
	 */
	public Job requestJob(JobSearch search);
	
	/**
	 * Allows a driver to accept a job
	 * @param job the Job object being accepted
	 * @return Message, message from server
	 */
	public Message acceptJob(Job job);
	
	/**
	 * 
	 * @param job the Job object being declined
	 * @return boolean of success
	 */
	public Message declineJob(Job job);
	
	/**
	 * 
	 * @return boolean of success
	 */
	public boolean startJob(Job job);
	
	/**
	 * 
	 * @return boolean of success
	 */
	public boolean finishJob(Job job);
	
	/**
	 * Sets the driver's status to active
	 * Allows the driver to receive new jobs
	 * @return boolean of success
	 */
	public boolean setAvailable();
	
	/**
	 * Sets the driver's status to inactive
	 * Used when driver is either currently busy or logged out
	 * @return boolean of success
	 */
	public boolean setUnavailable();
	
	/**
	 * Returns all the previous jobs involving the driver
	 * @return array of the jobs
	 */
	public List<Job> requestHistory();
	
	/**
	 * Upon completion of a job the driver must be able to rate the customer
	 * @return a boolean of success
	 */
	public boolean rateCustomer(Job job);
	
	
	public Message checkStatus();
}
