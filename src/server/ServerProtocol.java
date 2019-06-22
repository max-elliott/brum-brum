package server;


import clientside.Customer;
import clientside.Driver;
import clientside.User;
import communication.CreateAccount;
import communication.Job;
import communication.JobSearch;
import communication.Login;
import communication.Message;

public class ServerProtocol {
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
    public Message processMessage(Message input) throws Exception {//throws exception?
    	
    	String id = input.getId();
    	
    	System.out.println("Server Procotol: " + id);
    	
	    if(id.equals("LOGIN")){//customerLogin case
	    	System.out.println("ServerProtocol -> LOGIN REQUEST RECEIVED");
	    	
	        Login login = (Login) input.getData();
	        Message m = Server.db.loginQuery(login);
	        if (m.getId().equals("ServerProtocol -> LOGIN SUCCESSFUL")) {
	        	setUser((User)m.getData());
	        }
	        return m;
    	}
	    else if(id.equals("LOGOUT")){
	    	User user = (User) input.getData();
	    	System.out.println("ServerProtocol -> LOGOUT requested");
	    	return Server.db.logoutQuery(user);
	    }
	    else if(id.equals("CREATE ACCOUNT")){
	    	
	    	CreateAccount account = (CreateAccount) input.getData();
	    	return Server.db.createAccount(account);
	    }
	    else if(id.equals("EDIT")){
	    	
	    	CreateAccount acct = (CreateAccount) input.getData();
	    	return Server.db.editAccountQuery(acct);
	    }
	    else if(id.equals("REQUEST PICKUP")){
	    	
	    	Job job = (Job) input.getData();
	    	return Server.db.requestRideQuery(job);
	    }
	    else if(id.equals("RATE DRIVER")){
	    	
	    	Job job = (Job) input.getData();
	    	return Server.db.rateDriver(job);//needs to be made
	    }
	    else if(id.equals("RATE CUSTOMER")){
	    	
	    	Job job = (Job) input.getData();
//	    	System.out.println("SERVER: Driver Rating:" + job.getDriverRating() + " Customer Rating: " + job.getRating());
//			System.out.println("SERVER: Job from: " + job.getFrom() + "Job to: " + job.getTo());
	    	return Server.db.rateCustomer(job);//needs to be made
	    }
	    else if(id.equals("GET RATING")){
	    	User user = (User) input.getData();
	    	return Server.db.getMyRating(user);
	    }
	    else if(id.equals("REQUEST JOB")){
	    	
	    	JobSearch jobSearch = (JobSearch) input.getData();
	    	return Server.db.searchJobsQuery(jobSearch);
	    }
	    else if(id.equals("ACCEPT")){
	    	
	    	Job jobAccept = (Job) input.getData();
	    	return Server.db.acceptJobQuery(jobAccept);
	    }
	    else if(id.equals("DECLINE")){
	    	
	    	Job job = (Job) input.getData();
	    	return Server.db.requestRideQuery(job);
	    }
	    else if(id.equals("SET AVAILABLE")){
	    	
	    	Driver d = (Driver) input.getData();
	    	return Server.db.setAvailable(d);
	    }
	    else if(id.equals("SET UNAVAILABLE")){
	    	
	    	Driver d = (Driver) input.getData();
	    	return Server.db.setUnavailable(d);
	    }
	    else if(id.equals("REQUEST DRIVER HISTORY")){
	    	
	    	Driver d = (Driver) input.getData();
	    	return Server.db.requestHistoryQuery(d);//customer or driver or either
	    }
	    else if(id.equals("REQUEST CUSTOMER HISTORY")){
	    	
	    	Customer c = (Customer) input.getData();
	    	return Server.db.requestHistoryQuery(c);//customer or driver or either
	    }
	    else if(id.equals("FINISHED")) {
	    	
	    	Job job = (Job) input.getData();
	    	return Server.db.setFinished(job);
	    } 
	    else if(id.equals("STARTED")) {
	    	
	    	Job job = (Job) input.getData();
	    	return Server.db.setStarted(job);
	    }
	    else if(id.equals("CHECK ACCEPTED")) {
	    	Job job = (Job) input.getData();
	    	System.out.println("ServerProtocol -> check accepted request received");
	    	return Server.db.checkAccepted(job);
	    }
	    else if(id.equals("CHECK STATUS")){
	    	
	    	if(input.getData().getClass().equals(Customer.class)){
	    		Customer c = (Customer) input.getData();
	    		return Server.db.checkStatus(c);
	    	}
	    	else if(input.getData().getClass().equals(Driver.class)){
	    		Driver d = (Driver) input.getData();
	    		return Server.db.checkStatus(d);
	    	}
	    }
	    else if (id.equals("CONTACT")) {
	    	if (input.getData().getClass().equals(Customer.class) || input.getData().getClass().equals(Driver.class)) {
	    		User user = (User) input.getData();
//	    		System.out.println("ServerProtocol -> Message == " + user.getMessage());
	    		return Server.db.contact(user);
	    	}
	    }
    	else{
    		System.out.println("ServerProtocol -> Message id not recognised.");
    		return new Message("OBJECT NOT RECOGNISED");
    	}
	    return new Message("Error: method probably not implemented in database yet.");
    }
}
