package clientside;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import communication.Job;
import communication.Message;
import interfaces.ICustomerClient;

public class CustomerClient extends Client implements ICustomerClient {
	private Socket clientSocket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private Customer customer;	
	
	public CustomerClient(String ip, String port) {
		super(ip, port);
	}
	
	@Override
	public boolean login(String username, String password, String type) {
		if(super.login(username, password, type)) {
			setCustomer((Customer) getUser());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The getter for customer
	 * @return Customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Setter for the customer
	 * @param Customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public Message requestPickup(String from, String to, String jobType, String details) {
		try {	
			getToServer().writeObject(new Message("REQUEST PICKUP", new Job(to, from, jobType, this.customer, details)));
			Object serverObject = getFromServer().readObject();
			
			if(serverObject.getClass().equals(Message.class)) {
				Message message = (Message) serverObject;
				if(message.getId().equals("REQUEST PICKUP SUCCESSFUL")) {
					return new Message("true", message.getData());
				} else if(message.getId().equals("FAILED")) {
//					System.out.println("CustomerClient -> Problem in database.");
					return new Message("false");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			setValid(false);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Message("false");
	}

	@Override
	public boolean rateDriver(Job job) {
		try {
			Message message = new Message("RATE DRIVER", job);
			getToServer().writeObject(message);
			Object serverObject = getFromServer().readObject();
			
			if (serverObject.getClass().equals(Message.class)) {
				message = (Message) serverObject;
				String id = message.getId();
				if (id.equals("DRIVER RATED")) {
//					System.out.println("CustomerClient -> Customer rated");
					return true;
				} else if (id.equals("FAILED")) {
//					System.out.println("CustomerClient -> Request failed");
					return false;
				}
			} else {
//				System.out.println("CustomerClient -> Unexpected object received");
				return false;
			}
		}
		catch (IOException e) {
//			e.printStackTrace();
			setValid(false);
			return false;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * Tests if a requesting job has been accepted.
	 * @param job
	 * @return
	 */
	public Message isAccepted(Job job) {
		try {
			getToServer().writeObject(new Message("CHECK ACCEPTED", job));
			Object object = getFromServer().readObject();
			if(object.getClass().equals(Message.class)) {
				
				Message message = (Message) object;
				if(message.getId().equals("ACCEPTED")) {
					return new Message("ACCEPTED", (Job) message.getData());
				} else if (message.getId().equals("REQUESTING")) {
					return new Message("REQUESTING", (Job) message.getData());
				} else if (message.getId().equals("ONGOING")){
					return new Message("ONGOING", (Job) message.getData());
				} else if (message.getId().equals("FINISHED")){
					return new Message("FINISHED", (Job) message.getData());
				} else {
					System.out.println("CustomerClient -> Error in query");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			setValid(false);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Message("false");
	}
	
	@Override
	public Message checkStatus(){
		try{
			Message message = new Message("CHECK STATUS", this.customer);
			getToServer().writeObject(message);
			Object serverObject = getFromServer().readObject();
			
			if (serverObject.getClass().equals(Message.class)) {
				Message m = (Message) serverObject;
				return m;
			}
			else{
//				System.out.println("CustomerClient -> checkStatus(): Object not recognised");
				return null;
			}
		}
			catch (IOException e) {
//				e.printStackTrace();
				setValid(false);
				return null;
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
	}

}
