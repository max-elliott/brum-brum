package communication;
import java.io.Serializable;

import clientside.User;

/**
 * 
 * @author Max Elliott
 * @created 2017-03-09
 */
public class CreateAccount extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String password;
	private int id;
	private String type;
	
	public CreateAccount(String firstName, String lastName, String email, String telNumber, String password) {
		super(firstName, lastName, email, telNumber);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
