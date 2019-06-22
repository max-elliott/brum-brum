package clientside;
import java.io.Serializable;
import java.util.Arrays;

import communication.Job;

/**
 * The generalised user class.
 * @author jpcryne
 * @version 2017-3-6
 */
public class User implements Serializable {
	private String firstName;
	private String lastName;
	private String state;
	private String message;
	private double rating;
	private Job[] history;
	private String email;
	private String telNumber;
	private int id;
	
	/**
	 * Constructor for a basic User.
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param telNumber
	 */
	public User(String firstName, String lastName, String email, String telNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.telNumber = telNumber;
		this.state = "NOT LOGGED IN";
	}
	
	/**
	 * Unsure if this is needed?
	 * @param firstName
	 * @param lastName
	 */
	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.state = "NOT LOGGED IN";
	}
	
	public User(int id, String message) {
		this.id = id;
		this.message = message;
	}

	/**
	 * The getter for firstName
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * The getter for lastName
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * The getter for state
	 * @return String
	 */
	public String getState() {
		return state;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * The getter for rating
	 * @return double
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * The getter for history
	 * @return Job[]
	 */
	public Job[] getHistory() {
		return history;
	}

	/**
	 * The getter for email
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * The getter for telNumber
	 * @return String
	 */
	public String getTelNumber() {
		return telNumber;
	}

	/**
	 * The getter for id
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for the firstName
	 * @param String
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Setter for the lastName
	 * @param String
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Setter for the state
	 * @param String
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Setter for the rating
	 * @param double
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

	/**
	 * Setter for the history
	 * @param Job[]
	 */
	public void setHistory(Job[] history) {
		this.history = history;
	}

	/**
	 * Setter for the email
	 * @param String
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Setter for the telNumber
	 * @param String
	 */
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	/**
	 * Setter for the id
	 * @param int
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	/**
	 * toString method for the User class.
	 */
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", state=" + state + ", rating=" + rating
				+ ", history=" + Arrays.toString(history) + ", email=" + email + ", telNumber=" + telNumber + ", id="
				+ id + "]";
	}
}
