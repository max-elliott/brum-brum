package clientside;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import communication.*;
import interfaces.IDriverClient;

/**
 * The DriverClient class implementing IDriverClient.
 * @author jpcryne, Robert Campbell
 * @version 06/03/2017
 */
public class DriverClient extends Client implements IDriverClient {
	private Socket clientSocket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private Driver driver;


	public DriverClient(String ip, String port) {
		super(ip, port);

	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Driver getDriver() {
		return this.driver;
	}

	@Override
	public boolean login(String username, String password, String type) {
		if(super.login(username, password, type)) {
			setDriver((Driver) getUser());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Job requestJob(JobSearch search) {
		try {
			//Include driver object in message so that driver ID can be accessed
			getToServer().writeObject(new Message("REQUEST JOB", search));
			Object serverObject = getFromServer().readObject();

			if (serverObject.getClass().equals(Message.class)) {
				Message message = (Message) serverObject;
				if (message.getId().equals("JOB")) {
//					System.out.println("DriverClient -> Job received");
					return (Job) message.getData();
				}
				else if (message.getId().equals("FAILED")) {
//					System.out.println("DriverClient -> Job request failed");
					return null;
				}
			}

		} 
		catch (IOException e){
			//e.printStackTrace();
			setValid(false);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Message acceptJob(Job job) {
		try {
			long time = new Date().getTime();
			//System.out.println("Job time: " + time + " DriverID: " + job.getDriver().getId() + " JobID: " + job.getJobID());

			Message message = new Message("ACCEPT", job);
			getToServer().writeObject(message);
			Object serverObject = getFromServer().readObject();

			if (serverObject.getClass().equals(Message.class)) {
				message = (Message) serverObject;
				String id = message.getId();
				if (id.equals("JOB ACCEPTED")) {
					return new Message("JOB ACCEPTED");
				} 
				else if (id.equals("JOB UNAVAILABLE")) {
					return new Message("JOB UNAVAILABLE");
				} 
				else if (id.equals("FAILED")) {
					return new Message("FAILED");
				}
			} else {
//				System.out.println("DriverClient -> Unexpected object received");
				return new Message("ERROR");
			}
		}
		catch (IOException e) {
			//			e.printStackTrace();
			setValid(false);
			return new Message("ERROR");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new Message("ERROR");
		}
		return new Message("ERROR");
	}

	@Override
	public Message declineJob(Job job) {
		try {
			Message message = new Message("DECLINE", job);
			getToServer().writeObject(message);
			Object serverObject = getFromServer().readObject();

			if (serverObject.getClass().equals(Message.class)) {
				message = (Message) serverObject;
				String id = message.getId();
				if (id.equals("JOB") && message.getData().equals(Job.class)) {
					return new Message("true", message.getData());
				} else if (id.equals("JOB UNAVAILABLE")) {
//					System.out.println("DriverClient -> Job is unavailable");
					return new Message("false");
				} else if (id.equals("FAILED")) {
//					System.out.println("DriverClient -> Request failed");
					return new Message("false");
				}
			} else {
//				System.out.println("DriverClient -> Unexpected object received");
				return new Message("false");
			}
		}
		catch (IOException e) {
			//			e.printStackTrace();
			setValid(false);
			return new Message("false");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return new Message("false");
		}
		return new Message("false");
	}



	@Override
	public boolean startJob(Job job) {
		Message message = new Message("STARTED", job);
		try {
			getToServer().writeObject(message);
			Object serverObject = getFromServer().readObject();

			if (serverObject.getClass().equals(Message.class)) {
				message = (Message) serverObject;
				String id = message.getId();
				if (id.equals("JOB STARTED")) {
//					System.out.println("Started set");
					return true;
				} else if (id.equals("FAILED")) {
//					System.out.println("DriverClient -> Request failed");
					return false;
				}
			} else {
//				System.out.println("DriverClient -> Unexpected object received");
				return false;
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			setValid(false);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean finishJob(Job job) {
		Message message = new Message("FINISHED", job);
		try {
			getToServer().writeObject(message);
			Object serverObject = getFromServer().readObject();

			if (serverObject.getClass().equals(Message.class)) {
				message = (Message) serverObject;
				String id = message.getId();
				if (id.equals("JOB FINISHED")) {
//					System.out.println("job finished");
					return true;
				} else if (id.equals("FAILED")) {
//					System.out.println("DriverClient -> Request failed");
					return false;
				}
			} else {
//				System.out.println("DriverClient -> Unexpected object received");
				return false;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			setValid(false);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean setAvailable() {
		try {
			Message message = new Message("SET AVAILABLE");
			getToServer().writeObject(message);
			Object serverObject = getFromServer().readObject();

			if (serverObject.getClass().equals(Message.class)) {
				message = (Message) serverObject;
				String id = message.getId();
				if (id.equals("AVAILABILITY SET")) {
//					System.out.println("Availability set");
					return true;
				} else if (id.equals("FAILED")) {
//					System.out.println("DriverClient -> Request failed");
					return false;
				}
			} else {
//				System.out.println("DriverClient -> Unexpected object received");
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

	@Override
	public boolean setUnavailable() {
		try {
			Message message = new Message("SET UNAVAILABLE");
			getToServer().writeObject(message);
			Object serverObject = getFromServer().readObject();

			if (serverObject.getClass().equals(Message.class)) {
				message = (Message) serverObject;
				String id = message.getId();
				if (id.equals("AVAILABILITY SET")) {
//					System.out.println("Availability set");
					return true;
				} else if (id.equals("FAILED")) {
//					System.out.println("DriverClient -> Request failed");
					return false;
				}
			} else {
//				System.out.println("DriverClient -> Unexpected object received");
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

	public boolean rateCustomer(Job job) {
		try {
			Message message = new Message("RATE CUSTOMER", job);
			getToServer().writeObject(message);
			Object serverObject = getFromServer().readObject();

			if (serverObject.getClass().equals(Message.class)) {
				message = (Message) serverObject;
				String id = message.getId();
				if (id.equals("CUSTOMER RATED")) {
//					System.out.println("DriverClient -> Customer rated");
					return true;
				} else if (id.equals("FAILED")) {
//					System.out.println("Request failed");
					return false;
				}
			} else {
//				System.out.println("DriverClient -> Unexpected object received");
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

	@Override
	public Message checkStatus(){
		try{
			Message message = new Message("CHECK STATUS", this.driver);
			getToServer().writeObject(message);
			Object serverObject = getFromServer().readObject();

			if (serverObject.getClass().equals(Message.class)) {
				Message m = (Message) serverObject;
				return m;
			}
			else{
//				System.out.println("DriverClient -> checkStatus() : Object not recognised");
				return null;
			}
		}
		catch (IOException e) {
			//e.printStackTrace();
			setValid(false);
			return null;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
