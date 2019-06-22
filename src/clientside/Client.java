package clientside;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import communication.CreateAccount;
import communication.Job;
import communication.Login;
import communication.Message;
import interfaces.IClient;

/**
 * The superclass for the CustomerClient and DriverClient classes.
 * @author jpcryne, Robert Campbell
 * @version 14/03/2017
 *
 */
public class Client implements IClient {
	private Socket clientSocket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private User user;
	private boolean isValid = false;
	
	public Client(String ip, String port) {
		try {
			this.clientSocket = new Socket(ip, Integer.parseInt(port));
			this.toServer = new ObjectOutputStream(clientSocket.getOutputStream());
			this.fromServer = new ObjectInputStream(clientSocket.getInputStream());
			isValid = true;
		} catch (IOException e) {
			System.out.println("Client.java -> Server connection unavailable");
			isValid = false;
		} 
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	public void setValid(boolean status) {
		isValid = status;
	}
	
	public String createAccount(String firstName, String lastName, String email, String telNumber, String password) {
		CreateAccount account = new CreateAccount(firstName, lastName, email, telNumber, password);
		try {
			toServer.writeObject(new Message("CREATE ACCOUNT", account));
			Object serverObject = fromServer.readObject();
			if(serverObject.getClass().equals(Message.class)) {
				Message message = (Message) serverObject;
				if(message.getId().equals("ACCOUNT CREATED")) {
					return "SUCCESS";
				} else if(message.getId().equals("EMAIL IN USE")) {
					return "Email already taken.";
				} else if(message.getId().equals("FAILED")) {
					return "Problems connecting to database.";
				} else {
					return "Problems connecting to server.";
				}
			} else {
				System.out.println("Client -> Object not recognised");
				return "Wrong object sent from Server.";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			isValid = false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Exception occurred.";
	}

	public boolean login(String username, String password, String type) {
		try {
			toServer.writeObject(new Message("LOGIN", new Login(username, password, type)));
			Object serverObject = fromServer.readObject(); 
			//Does this need a while loop, break when we get an object?
			//No, I don't think so. It should be fine without it
			if(serverObject.getClass().equals(Message.class)) {
				Message response = (Message) serverObject;
				if (response.getId().equals("LOGIN SUCCESSFUL") && response.getData().getClass().equals(Driver.class)) {
					System.out.println("Client -> logged in");
					setUser((Driver) response.getData());
					return true;
				} else if(response.getId().equals("LOGIN SUCCESSFUL") && response.getData().getClass().equals(Customer.class)) {
					System.out.println("Client -> logged in");
					setUser((Customer) response.getData());
					return true;
				}
				else if(response.getId().equals("ALREADY LOGGED IN")){
					System.out.println("Client -> User already logged into server");
					return false;
				}
				else if (response.getId().equals("LOGIN FAILED")) {
					System.out.println("Client -> login failed...");
					return false;
				} else {
					System.out.println("Client -> Unexpected response from server...");
					return false;
				}
	    	} else{
//	    		Depends on what server sends back if login does not exist.
				System.out.println("Client -> nexpected Object class received from server...");
	    		return false;
	    	}
		} catch (IOException e) {
//			e.printStackTrace();
			isValid = false;
			return false;
		} catch(ClassNotFoundException e) {
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public boolean logout() {
		Message message;
		if(user.getClass().equals(Customer.class)) {
			message = new Message("LOGOUT", (Customer) getUser());
		} else if(user.getClass().equals(Driver.class)) {
			message = new Message("LOGOUT", (Driver) getUser());
		} else {
			System.out.println("Client -> Class must be Customer or Driver");
			return false;
		}
		
		try {
			toServer.writeObject(message);
			Object object = fromServer.readObject();
			if(object.getClass().equals(Message.class)) {
				Message serverMessage = (Message) object;
				if(serverMessage.getId().equals("LOGOUT SUCCESSFUL")) {
					toServer.close();
					fromServer.close();
					System.out.println("Client -> logged out");
					return true;
				} else if(serverMessage.getId().equals("LOGOUT FAILED")) {
					System.out.println("Client -> log out failed...");
					System.out.println(serverMessage.getId());
					return false;
				} else {
					System.out.println("Client -> Unexpected message id");
					return false;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			isValid = false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public List<Job> requestHistory() {
		ArrayList<Job> jobs =  new ArrayList<Job>();
		Job j = new Job("-", "-", "-", new Customer("-", "-", "-", "-"));
		j.setDriver(new Driver("-", "-"));
		jobs.add(j);
		try {
			Message message;
			if(user.getClass().equals(Customer.class)) {
				message = new Message("REQUEST CUSTOMER HISTORY", user);
			} else if(user.getClass().equals(Driver.class)) {
				message = new Message("REQUEST DRIVER HISTORY", user);
			} else {
				message = new Message("");
			}
			
			toServer.writeObject(message);
			Object serverObject = fromServer.readObject();
			
			if (serverObject.getClass().equals(Message.class)) {
				message = (Message) serverObject;
				String id = message.getId();
				if (message.getId().equals("HISTORY") && message.getData().getClass().equals(java.util.ArrayList.class)) {
					System.out.println("Client -> History received");
					return (ArrayList<Job>) message.getData();
				} else if (id.equals("FAILED")) {
					System.out.println("Client -> NO HISTORY");
//					Create a new job array with a single empty job in it
//					We made need to initialise all of these things to default states 
					return jobs;
				} else {
					System.out.println("Client -> Unexpected Message ID: " + message.getId());
//					System.out.println(message.getId().equals("HISTORY") && message.getData().equals(java.util.ArrayList.class));
//					System.out.println(message.getId().equals("HISTORY"));
//					System.out.println(message.getData().equals(java.util.ArrayList.class));
					return jobs;
				}
			} else {
				System.out.println("Client -> Unexpected object received");
				return jobs;
			}
		}
		catch (IOException e) {
//			e.printStackTrace();
			isValid = false;
			return jobs;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return jobs;
		}
	}

	public String getRating() {
		try {
			toServer.writeObject(new Message("GET RATING", getUser()));
			Object serverObject = fromServer.readObject();
			if (serverObject.getClass().equals(Message.class)) {
				Message message = (Message) serverObject;
				if (message.getId().equals("RATED")) {
					String rating = (String) message.getData();
					return rating;
				}
				return "";
			}
		} catch (IOException e) {
//			e.printStackTrace();
			isValid = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		Something went very wrong if you made it here
		return "No Rating";
	}		
	
	/**
	 * Allows a user to edit their account information
	 * @param user
	 * @return String
	 */
	public String editUser(String firstName, String lastName, String email, String telNumber, String password) {
		CreateAccount account = new CreateAccount(firstName, lastName, email, telNumber, password);
		int id = this.user.getId();
		account.setId(id);
		if (this.user.getClass().equals(Customer.class)) {
			account.setType("Customer");
		} else {
			account.setType("Driver");
		}
		try {
			toServer.writeObject(new Message("EDIT", account));
			Object serverObject = fromServer.readObject();
			if(serverObject.getClass().equals(Message.class)) {
				Message message = (Message) serverObject;
				if(message.getId().equals("SUCCESS")) {
					this.user.setFirstName(firstName);
					this.user.setLastName(lastName);
					this.user.setEmail(email);
					this.user.setTelNumber(telNumber);
					return "SUCCESS";
				} else if (message.getId().equals("EDIT FAILED")) {
					System.out.println("Client.java -> Edit failed");
					return "FAILED";
				} else if (message.getId().equals("INCORRECT PASSWORD")) {
					System.out.println("Client.java -> incorrect password");
					return "INCORRECT PASSWORD";
				} else if (message.getId().equals("EMAIL IN USE")) {
					System.out.println("Client.java -> email already in use");
					return "EMAIL IN USE";
				} else {
					return "FAILED";
				}
			} else {
				System.out.println("Object not recognised");
				return "FAILED";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			isValid = false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "FAILED";
	}

	/**
	 * The getter for toServer
	 * @return ObjectOutputStream
	 */
	public ObjectOutputStream getToServer() {
		return toServer;
	}

	/**
	 * The getter for fromServer
	 * @return ObjectInputStream
	 */
	public ObjectInputStream getFromServer() {
		return fromServer;
	}
	
	/**
	 * Allows users to send messages to the company
	 * @param u user object
	 * @return String, "SUCCESS" or "FAILED"
	 */
	public String contact(String contactMessage, String type) {
		try {
			User user;
			if (type.equals("Customer")) {
				user = new Customer(getUser().getId(), contactMessage);
			} else {
				user = new Driver(getUser().getId(), contactMessage);
			}
			toServer.writeObject(new Message("CONTACT", user));
			Object serverObject = fromServer.readObject();
			if(serverObject.getClass().equals(Message.class)) {
				Message message = (Message) serverObject;
				return (message.getId().equals("SUCCESS")) ? "SUCCESS" : "FAILED";
			} else {
				System.out.println("Object not recognised");
				return "FAILED";
			}
		} catch (IOException e) {
//			e.printStackTrace();
			isValid = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "FAILED";
	}


}
