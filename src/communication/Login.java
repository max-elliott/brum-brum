package communication;
import java.io.Serializable;

/**
 * A class for checking the username and password when entered into the Log In GUI.
 * Currently only checks against given username and password - need to connect to the server
 * and database
 * 
 * @author Elizabeth Warner
 * @version 2017-02-26
 *
 */
public class Login implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String userType;
	
	public Login(String username, String password, String type) {
		this.username = username;
		this.password = password;
		this.userType = type;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserType() {
		return this.userType;
	}
	
	

}
