package clientside;
import java.io.Serializable;

import communication.Job;

/** Driver.java
 * Driver class for BrumBrum containing details of that driver.
 * @author Robert Campbell
 * @version 2017-02-24
 */
public class Driver extends User {
	private Job currentJob;
	private String car_make;
	private String car_model;
	private String reg_no;
	private String status;
	
	/**
	 * Full Constructor
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param telNumber
	 */
	public Driver(String firstName, String lastName, String email, String telNumber) {
		super(firstName, lastName, email, telNumber);
	}
	
	/**
	 * Is this needed? Maybe for the creation of empty job.
	 * @param firstName
	 * @param lastName
	 */
	public Driver(String firstName, String lastName) {
		super(firstName, lastName);
	}
	
	public Driver(int id, String message) {
		super(id, message);
	}

	/**
	 * The getter for currentJob
	 * @return Job
	 */
	public Job getCurrentJob() {
		return currentJob;
	}

	/**
	 * Setter for the currentJob
	 * @param Job
	 */
	public void setCurrentJob(Job currentJob) {
		this.currentJob = currentJob;
	}

	/**
	 * The getter for car_make
	 * @return String
	 */
	public String getCar_make() {
		return car_make;
	}

	/**
	 * Setter for the car_make
	 * @param String
	 */
	public void setCar_make(String car_make) {
		this.car_make = car_make;
	}

	/**
	 * The getter for car_model
	 * @return String
	 */
	public String getCar_model() {
		return car_model;
	}

	/**
	 * Setter for the car_model
	 * @param String
	 */
	public void setCar_model(String car_model) {
		this.car_model = car_model;
	}

	/**
	 * The getter for reg_no
	 * @return String
	 */
	public String getReg_no() {
		return reg_no;
	}

	/**
	 * Setter for the reg_no
	 * @param String
	 */
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}

	/**
	 * The getter for status
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Setter for the status
	 * @param String
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
