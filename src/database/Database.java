package database;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import clientside.Customer;
import clientside.Driver;
import clientside.User;
import communication.CreateAccount;
import communication.Job;
import communication.JobSearch;
import communication.Login;
import communication.Message;
import interfaces.IDatabase;
import server.Server;


/**
 * Created by Rob on 24/02/2017.
 * @author Robert Campbell & Max Elliott & Haomo Zhu
 * @version 2017-03-03
 * @version 2017-03-13
 */
public class Database implements IDatabase {
	Connection conn = null;

	public Database() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found");
		}
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://mod-fund-databases.cs.bham.ac.uk/hxz673", "hxz673",
					"exui0sodkx");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		if (conn != null) {
			System.out.println("Database accessed!");
		} else {
			System.out.println("Failed to make connection");
		}
	}

	@Override
	public Message createAccount(CreateAccount acc) {
		int count1 = 0;

		try {
			PreparedStatement checkAccount = conn
					.prepareStatement("SELECT count(*) FROM customers WHERE  email = ? ");
			checkAccount.setString(1, acc.getEmail());
			ResultSet rs1 = checkAccount.executeQuery();
			if (rs1.next()) {
				count1 = rs1.getInt(1);
			}
			if (count1 > 0) {
				return new Message("EMAIL IN USE", "CLOSE THREAD");
			} else {
				PreparedStatement createAccount = conn.prepareStatement(
						"INSERT INTO customers(firstname, surname, email, tel_no, hash, salt) VALUES(?,?,?,?,?,?) ");
				byte[] salt = Encryption.getSalt();
				String hash = Encryption.encrypt(acc.getPassword(), salt);
				createAccount.setString(1, acc.getFirstName());
				createAccount.setString(2, acc.getLastName());
				createAccount.setString(3, acc.getEmail());
				createAccount.setString(4, acc.getTelNumber());
				createAccount.setString(5, hash);
				createAccount.setBytes(6, salt);
				int rs2 = createAccount.executeUpdate();
				if (rs2 == 0) {
					return new Message("FAILED", "CLOSE THREAD");
				} else {
					return new Message("ACCOUNT CREATED", "CLOSE THREAD");
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Message("FAILED", "CLOSE THREAD");
		}
	}
	
	public Message createDriverAccount(CreateAccount acc, String car_make, String car_model, String reg_no) {
		int count1 = 0;

		try {
			PreparedStatement checkAccount = conn
					.prepareStatement("SELECT count(*) FROM drivers WHERE  email = ? ");
			checkAccount.setString(1, acc.getEmail());
			ResultSet rs1 = checkAccount.executeQuery();
			if (rs1.next()) {
				count1 = rs1.getInt(1);
			}
			if (count1 > 0) {
				return new Message("Account already exists");
			} else {
				PreparedStatement createAccount = conn.prepareStatement(
						"INSERT INTO drivers(firstname, surname, email, tel_no, hash, salt, car_make, car_model, reg_no, status) VALUES(?,?,?,?,?,?,?,?,?,?) ");
				byte[] salt = Encryption.getSalt();
				String hash = Encryption.encrypt(acc.getPassword(), salt);
				createAccount.setString(1, acc.getFirstName());
				createAccount.setString(2, acc.getLastName());
				createAccount.setString(3, acc.getEmail());
				createAccount.setString(4, acc.getTelNumber());
				createAccount.setString(5, hash);
				createAccount.setBytes(6, salt);
				createAccount.setString(7, car_make);
				createAccount.setString(8, car_model);
				createAccount.setString(9, reg_no);
				createAccount.setString(10, "INACTIVE");
				int rs2 = createAccount.executeUpdate();
				if (rs2 == 0) {
					return new Message("FAILED");
				} else {
					return new Message("ACCOUNT CREATED");
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Message("Create Account FAILED");
		}
	}
	
	public Message editAccountQuery(CreateAccount acct) {
		try {
			PreparedStatement checkCredentialsQuery;
			PreparedStatement checkEmailQuery;
			PreparedStatement editAccountQuery;
			ResultSet rs1, rs2;
			String table;
//			System.out.println("Database.java -> type: " + acct.getType());
			if (acct.getType().equals("Customer")) {
				table = "customers";
				System.out.println("Database.java -> Customer edit request received");
				checkCredentialsQuery = conn.prepareStatement("SELECT hash, salt FROM customers WHERE id = ?");
			} 
			else if (acct.getType().equals("Driver")) {
				table = "drivers";
				System.out.println("Database.java -> Driver edit request received");
				checkCredentialsQuery = conn.prepareStatement("SELECT hash, salt FROM drivers WHERE id = ?");
			} 
			else {
//				System.out.println("Database.java -> Neither customer or driver type");
				return new Message("FAILED");
			}
//			Don't forget to set the ID!
			checkCredentialsQuery.setInt(1, acct.getId());
			
//			Grab the hash and salt from the relevant
			rs1 = checkCredentialsQuery.executeQuery();
			while (rs1.next()) {
				System.out.println("Database.java -> User details found in " + table);
				byte[] salt = rs1.getBytes("salt");
				String hash = rs1.getString("hash");
				
//				Check if their new email address already exists in the database
				if (table.equals("customers")) {
					checkEmailQuery = conn.prepareStatement("SELECT * FROM customers WHERE email = ? AND NOT id = ?");
				} else {
					checkEmailQuery = conn.prepareStatement("SELECT * FROM drivers WHERE email = ? AND NOT id = ?");
				}
				checkEmailQuery.setString(1, acct.getEmail());
				checkEmailQuery.setInt(2, acct.getId());
				rs2 = checkEmailQuery.executeQuery();
				while (rs2.next()) {
					return new Message("EMAIL IN USE");
				}

//				Prepare for the update
				if (hash.equals(Encryption.encrypt(acct.getPassword(), salt))) {
					System.out.println("Database.java -> Credentials matched successfully in " + table + " table");
					if (table.equals("customers")) {
						editAccountQuery = conn.prepareStatement("UPDATE customers SET firstname = ?, surname = ?, tel_no = ?, email = ? WHERE id = ?");
					} else {
						editAccountQuery = conn.prepareStatement("UPDATE drivers SET firstname = ?, surname = ?, tel_no = ?, email = ? WHERE id = ?");
					}
					editAccountQuery.setString(1, acct.getFirstName());
					editAccountQuery.setString(2, acct.getLastName());
					editAccountQuery.setString(3, acct.getTelNumber());
					editAccountQuery.setString(4, acct.getEmail());
					editAccountQuery.setInt(5, acct.getId());
					
//					Execute the UPDATE query
					int i = editAccountQuery.executeUpdate();
					if (i == 0) {
						return new Message("FAILED");
					} else {
						return new Message("SUCCESS");
					}
				} else {
					System.out.println("Database.java -> Password didn't match in " + table + " table");
					return new Message("INCORRECT PASSWORD");
				}
			}
			System.out.println("Database.java -> checkCredentials failed!");
			return new Message("FAILED");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Database.java -> editAccountQuery() failed");
		return new Message("FAILED");
	}


	@Override
	public Message loginQuery(Login l) {
		if (l.getUserType().equals("Customer")) {//customer trying to logon
			if(Server.connectedCustomers.contains(l.getUsername())){
				return new Message("ALREADY LOGGED IN", "CLOSE THREAD");
			}
			try {
				PreparedStatement checkCustomerLogin = conn
						.prepareStatement("SELECT * FROM customers WHERE email = ?");
				checkCustomerLogin.setString(1, l.getUsername());
				ResultSet rs1 = checkCustomerLogin.executeQuery();
				Customer customer1 = null;
				if (rs1.next() == true) {
					System.out.println("Customer details found in database.");
					byte[] salt = rs1.getBytes("salt");
					String hash = rs1.getString("hash");
//					System.out.println("hash: " + hash);
//					System.out.println("encrypted password: " + Encryption.encrypt(l.getPassword(), salt));
					if (hash.equals(Encryption.encrypt(l.getPassword(), salt))) {
						System.out.println("Customer details found in database.");
						customer1 = new Customer(rs1.getString("firstName"), rs1.getString("surname"),rs1.getString("email"),rs1.getString("tel_no"));
						customer1.setId(rs1.getInt("id"));
						Server.connectedCustomers.add(rs1.getString("email"));
						
						
						return new Message("LOGIN SUCCESSFUL", customer1);
					} else {
//						Incorrect password
						return new Message("LOGIN FAILED");
					}
				} else {
					System.out.println("Customer details NOT found in database.");
				}
			} catch (SQLException e) {
				System.out.println("Error with CUSTOMER login");
				e.printStackTrace();
			}
		}
		else if (l.getUserType().equals("Driver")) {
			if(Server.connectedDrivers.contains(l.getUsername())){
				return new Message("ALREADY LOGGED IN", "CLOSE THREAD");
			}
			try {
				PreparedStatement checkDriverLogin = conn
						.prepareStatement("SELECT * FROM drivers WHERE email = ?");
				checkDriverLogin.setString(1, l.getUsername());
				ResultSet rs1 = checkDriverLogin.executeQuery();
				Driver driver1 = null;
				if (rs1.next() == true) {
					System.out.println("Driver details found in database.");
					byte[] salt = rs1.getBytes("salt");
					String hash = rs1.getString("hash");
//					System.out.println("hash: " + hash);
//					System.out.println("encrypted password: " + Encryption.encrypt("password", salt));
					if (hash.equals(Encryption.encrypt("password", salt))) {
						driver1 = new Driver(rs1.getString("firstName"), rs1.getString("surname"),rs1.getString("email"),rs1.getString("tel_no"));
						driver1.setId(rs1.getInt("id"));
						PreparedStatement activeDriver = conn
								.prepareStatement("UPDATE drivers SET status = 'ACTIVE' WHERE email = ?");
						activeDriver.setString(1, l.getUsername());
						int i = activeDriver.executeUpdate();
						if (i == 0) {
							return new Message("FAILED");
						} else {
							Server.connectedDrivers.add(rs1.getString("email"));
							return new Message("LOGIN SUCCESSFUL", driver1);
						}
					} else {
//						Incorrect password
						return new Message("LOGIN FAILED");
					}
				} else {
					System.out.println("Details not found in database.");
					return new Message("LOGIN FAILED");
				}
			} catch (SQLException e) {
				System.out.println("Error with DRIVER login");
				e.printStackTrace();
			}
		}
		return new Message("LOGIN FAILED");
	}

	@Override
	public Message logoutQuery(User user) {
		if (user.getClass().equals(Customer.class)) {
			Server.connectedCustomers.remove(user.getEmail());
			return new Message("LOGOUT SUCCESSFUL");
		} 
		else if (user.getClass().equals(Driver.class)) {
			Driver driver = (Driver) user;
			try {
				PreparedStatement logoutQuery = conn.prepareStatement("UPDATE drivers SET status = 'INACTIVE' WHERE id = ?");
				logoutQuery.setInt(1, driver.getId());
				int i = logoutQuery.executeUpdate();
				if (i == 0) {
					return new Message("LOGOUT FAILED");
				} else {
					Server.connectedDrivers.remove(user.getEmail());
					return new Message("LOGOUT SUCCESSFUL", "CLOSE THREAD");
				}
			}
			catch (SQLException e) {
			}
		}
		return new Message("ERROR");
	}
	
	@Override
	public Message requestRideQuery(Job j){

		try {
			//check if customer has any active rides
			PreparedStatement checkRide = conn.prepareStatement("SELECT COUNT(*) FROM ride WHERE cust_id = ? AND (status = 'REQUESTING' OR status = 'ACTIVE')");
			checkRide.setInt(1, j.getCustomer().getId());
			ResultSet rs = checkRide.executeQuery();
			rs.next();
			if(rs.getInt(1) == 0){//no current rides requested
				PreparedStatement requestPickup = conn.prepareStatement(
						"INSERT INTO ride(start_location, end_location, cust_id, status, job_type, details) values (?,?,?,?,?,?)");

				requestPickup.setString(1, j.getFrom());
				requestPickup.setString(2, j.getTo());
				requestPickup.setInt(3, j.getCustomer().getId());
				requestPickup.setString(4, "REQUESTING");
				requestPickup.setString(5, j.getJobType());
				requestPickup.setString(6, j.getDetails());
				

				int i = requestPickup.executeUpdate();
				
				//Allows us to get the JobID to the GUI for rating.
				PreparedStatement retrieveJobID = conn.prepareStatement("SELECT id FROM ride WHERE (cust_id = ? AND status = 'REQUESTING')");
				retrieveJobID.setInt(1, j.getCustomer().getId());
//				System.out.println("CustomerID: " + j.getCustomer().getId());
				ResultSet rs1 = retrieveJobID.executeQuery();
				if(rs1.next()) {
//					System.out.println("Database.java -> requestRideQuery() job retrieved");
					j.setJobID(rs1.getInt(1));
				} else {
//					System.out.println("Database.java -> requestRideQuery() no job retrieved");
				}
				
				if (i == 0){
					return new Message("FAILED");
				}
				else{
					return new Message("REQUEST PICKUP SUCCESSFUL", j); 

				}
			}
			else {
				return new Message("ALREADY REQUESTED PICKUP");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return new Message("FAILED");
	}

	@Override
	public Message searchJobsQuery(JobSearch search) {
		try {
	//		The following statement makes sure that the ride table is ORDER before performing the search as weird errors can occur otherwise
			PreparedStatement searchJobsQuery = conn.prepareStatement("SELECT * FROM (SELECT * FROM ride WHERE status = 'REQUESTING' ORDER BY id) AS rides WHERE (id > ?) LIMIT 1");
	//		PreparedStatement searchJobsQuery = conn.prepareStatement("SELECT ride.cust_id FROM (SELECT * FROM ride WHERE status = 'REQUESTING' ORDER BY id) AS rides WHERE (status = 'REQUESTING' AND ride.id > ?) limit 1");
	
			searchJobsQuery.setInt(1, search.getJobId());
			ResultSet jobQuery = searchJobsQuery.executeQuery();
			
			while (jobQuery.next()) {
				System.out.println("\n************************JOB REQUEST*****************************");
	
				int customerID = jobQuery.getInt("cust_id");
				System.out.println("Customer ID: " + customerID);
				
				PreparedStatement searchCustomerQuery = conn.prepareStatement("SELECT * FROM (SELECT * FROM customers ORDER BY id) as cust WHERE id = ? LIMIT 1");
				searchCustomerQuery.setInt(1, customerID);
				ResultSet customerQuery = searchCustomerQuery.executeQuery();
				
				while (customerQuery.next()) {
					int driverID = search.getDriverID();
					String firstname = customerQuery.getString("firstname");
					String surname = customerQuery.getString("surname");
					String email = customerQuery.getString("email");
					String tel_no = customerQuery.getString("tel_no");
					double rating = customerQuery.getDouble("rating");
					int jobID = jobQuery.getInt("id");
					String from = jobQuery.getString("start_location");
					String to = jobQuery.getString("end_location");
					String job_type = jobQuery.getString("job_type");
					String details = jobQuery.getString("details");
					
	//				Print everything to console
					System.out.println("Customer: " + firstname + " "+ surname);
					System.out.println("(Email: " + email + " | " + "Tel_no: " + tel_no);
					System.out.println("Rating: " + customerQuery.getDouble("rating"));
					System.out.println("Driver: " + driverID);
					System.out.println("(Job #" + jobID +") From: " + from + " --> " + to);
					
//					Create job and customer objects to add to return Message
					Customer customer = new Customer(firstname, surname, email, tel_no);
					customer.setId(customerID);
					customer.setRating(rating);
					Job job = new Job(to, from, job_type, customer);
					job.setJobID(jobID);
					job.setDetails(details);
					return new Message("JOB", job);
					}
		//			Job actually existed but failed when we tried to accept it
					return new Message("FAILED");
				}
				return new Message("FAILED");
			
			} catch (SQLException e) {
				e.printStackTrace();
		}
		return new Message("FAIL");
	}

	@Override
	public Message acceptJobQuery(Job j) {
		
		try {
			PreparedStatement acceptJobQuery = conn.prepareStatement(
					"UPDATE ride SET status = 'ACCEPTED', pick_up_time = ?, driver_id = ? WHERE (id = ? AND status = 'REQUESTING')");
			long time = new Date().getTime();
			acceptJobQuery.setLong(1, time);
			//System.out.println("Is driver null?" + j.getDriver().getFirstName());
			acceptJobQuery.setInt(2, j.getDriver().getId());
			acceptJobQuery.setInt(3, j.getJobID());
			int i = acceptJobQuery.executeUpdate();
			if (i == 0) {
				return new Message("JOB UNAVAILABLE");
			} else {
				return new Message("JOB ACCEPTED");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Message("FAILED");
		}
	}

	@Override
	public Message requestHistoryQuery(Driver d) {
		try {
			PreparedStatement requestHistoryQuery = conn
					.prepareStatement("SELECT ride.id, start_location, end_location, cust_id, job_type, driver_id,"
							+ " driver_rating, cust_rating, drop_off_time, firstname,"
							+ " surname, email, tel_no FROM ride INNER JOIN customers"
							+ " ON customers.id = cust_id WHERE driver_id = ?"
							+ " ORDER BY drop_off_time DESC;");
			requestHistoryQuery.setInt(1, d.getId());
			ResultSet rs1 = requestHistoryQuery.executeQuery();

			List<Job> requestHistory = new ArrayList<Job>();
			while (rs1.next()) {
					Customer c1 = new Customer(rs1.getString("firstname"), rs1.getString("surname"),
					rs1.getString("email"), rs1.getString("tel_no"));
					Job job1 = null;
					job1 = new Job(rs1.getString("end_location"), rs1.getString("start_location"), rs1.getString("job_type"), c1);
					job1.setDriver(d);
					job1.setJobID(rs1.getInt("id"));
					job1.setCustomerRating(rs1.getInt("cust_rating"));
					job1.setDriverRating(rs1.getInt("driver_rating"));
					job1.setDetails("details");
					Date date = new Date(rs1.getLong("drop_off_time"));
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, EEE d MMM");
					job1.setEndTime(sdf.format(date));
					requestHistory.add(job1);
			}

			return new Message("HISTORY", requestHistory);
		} catch (SQLException e) {
			e.printStackTrace();
			return new Message("FAILED");
		}
	}

	@Override

	public Message requestHistoryQuery(Customer c) {
		try {
			PreparedStatement requestHistoryQuery = conn
					.prepareStatement("SELECT start_location,end_location, driver_id, "
							+ "cust_rating, driver_rating, drop_off_time, firstname, surname, email, "
							+ "tel_no, car_make, car_model, reg_no FROM ride INNER JOIN drivers "
							+ "ON drivers.id = driver_id WHERE cust_id = ? ORDER BY drop_off_time DESC");
			requestHistoryQuery.setInt(1, c.getId());
			ResultSet rs1 = requestHistoryQuery.executeQuery();

			List<Job> requestHistory = new ArrayList<Job>();
			while (rs1.next()) {
				Driver d1 = new Driver(rs1.getString("firstname"), rs1.getString("surname"), rs1.getString("email"),
						rs1.getString("tel_no"));
				d1.setCar_make(rs1.getString("car_make"));
				d1.setCar_model(rs1.getString("car_model"));
				d1.setReg_no(rs1.getString("reg_no"));
				Job job1 = null;
				job1 = new Job(rs1.getString("end_location"), rs1.getString("start_location"), d1);
				job1.setCustomer(c);
				job1.setCustomerRating(rs1.getInt("cust_rating"));
				job1.setDriverRating(rs1.getInt("driver_rating"));
				job1.setDetails("details");
				Date date = new Date(rs1.getLong("drop_off_time"));
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, EEE d MMM");
				job1.setEndTime(sdf.format(date));
				requestHistory.add(job1);
			}
			return new Message("HISTORY", requestHistory);
		} catch (SQLException e) {
			e.printStackTrace();
			return new Message("FAILED");
		}
	}

	@Override
	public Message setAvailable(Driver d) {
		try {
			PreparedStatement setAvailable = conn
					.prepareStatement("UPDATE drivers SET status = 'ACTIVE' WHERE email=?");
			setAvailable.setString(1, d.getEmail());

			int i = setAvailable.executeUpdate();
			if (i == 0) {
				return new Message("FAILED");
			} else {
				return new Message("DRIVER SET AVAILABLE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Message("FAILED");
		}
	}

	@Override
	public Message setUnavailable(Driver d) {
		try {
			PreparedStatement setUnavailable = conn
					.prepareStatement("UPDATE drivers SET status = 'INACTIVE' WHERE email=?");
			setUnavailable.setString(1, d.getEmail());

			int i = setUnavailable.executeUpdate();
			if (i == 0) {
				return new Message("FAILED");
			} else {
				return new Message("DRIVER SET UNAVAILABLE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Message("FAILED");
		}
	}
	
	@Override
	public Message setFinished(Job job) {
		try {
			PreparedStatement getStartTime = conn.prepareStatement("SELECT pick_up_time FROM ride "
					+ "WHERE id = ?");
			getStartTime.setInt(1, job.getJobID());
			
			ResultSet rs = getStartTime.executeQuery();
			
			long startTime = 0;
			
			while(rs.next()) {
				startTime = rs.getLong("pick_up_time");
			}
			
			if(startTime == 0) {
				System.out.println("Database.java -> setFinished() : No start time found.");
				return new Message("FAILED");
			}
			
			
			PreparedStatement setFinished = conn
					.prepareStatement("UPDATE ride SET status = 'FINISHED', length_of_journey = ?, drop_off_time = ? "
							+ "WHERE (id=? AND status = 'ONGOING')");
			
			long endTime = new Date().getTime();
			long duration = endTime-startTime;
			setFinished.setLong(1, duration);
			setFinished.setLong(2, endTime);
			setFinished.setInt(3, job.getJobID());
			
			int i = setFinished.executeUpdate();
			if (i == 0) {
				return new Message("FAILED");
			} else {
				
				setAvailable(job.getDriver());
				
				return new Message("JOB FINISHED");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Message("FAILED");
		}
	}
	
	@Override
	public Message setStarted(Job job) {
		try {
			PreparedStatement setStarted = conn
					.prepareStatement("UPDATE ride SET status = 'ONGOING', pick_up_time = ? "
							+ "WHERE (id = ? AND status = 'ACCEPTED')");
			long startTime = new Date().getTime();
			setStarted.setLong(1, startTime);
			setStarted.setInt(2, job.getJobID());
			
			
			int i = setStarted.executeUpdate();
			if (i == 0) {
				System.out.println("db.setStarted: Failed");
//				System.out.println("i = 0!! JobID = " + job.getJobID());
				return new Message("FAILED");
			} else {
				
				setUnavailable(job.getDriver());
				
				return new Message("JOB STARTED");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Message("FAILED");
		}
	}

	public Message rateCustomer(Job job) {
		try {
			int cust_id = job.getCustomer().getId();
			PreparedStatement rateCustomerQuery = conn.prepareStatement("UPDATE ride SET cust_rating = ? WHERE id = ? AND cust_id = ?");
			rateCustomerQuery.setDouble(1, (double)job.getCustomerRating());
			rateCustomerQuery.setInt(2, job.getJobID());
			rateCustomerQuery.setInt(3, cust_id);
			
//			System.out.println("DB: Driver Rating:" + job.getDriverRating() + " Customer Rating: " + job.getRating());
//			System.out.println("DB: Job from: " + job.getFrom() + "Job to: " + job.getTo());
//			System.out.println("DB: State: " + job.getState());
//			System.out.println("DB: JobID " + job.getJobID());
			System.out.println("Database.java -> rateCustomer() : CustomerID = " + cust_id);
			int i = rateCustomerQuery.executeUpdate();
			if (i == 0) {
				return new Message("FAILED");
			}
			else {
				DecimalFormat df = new DecimalFormat("#.#");
				PreparedStatement getAverageRating = conn.prepareStatement("SELECT avg(cust_rating) AS average_rating FROM ride "
						+ "WHERE cust_id = ? AND status = 'FINISHED'");
				getAverageRating.setInt(1, cust_id);
				ResultSet rs1 = getAverageRating.executeQuery();
				while (rs1.next()) {
					double rating = Double.parseDouble((df.format(rs1.getDouble("average_rating"))));
					System.out.println("Database.java -> Average has been calculated as..." + rs1.getDouble("average_rating"));
					
					PreparedStatement setCustomerRating = conn.prepareStatement("UPDATE customers SET rating = ? WHERE id = ?");
					setCustomerRating.setDouble(1, rating);
					setCustomerRating.setInt(2, cust_id);
					
					i = setCustomerRating.executeUpdate();
					if (i == 0) {
						System.out.println("Database.java -> Problem when adding customer rating to customers table");
					} else {
						System.out.println("Database.java -> Customer rating added to customers table");
					}
				}
				System.out.println("Database.java -> Customer was rated successfully");
				return new Message("CUSTOMER RATED");
			}
		} catch (SQLException e) {
			System.out.println("Customer rating failed");
//			e.printStackTrace();
		}
		return new Message("FAILED");
	}
	
	public Message rateDriver(Job job) {
		try {
			int driver_id = job.getDriver().getId();
			PreparedStatement rateDriverQuery = conn.prepareStatement("UPDATE ride SET driver_rating = ? WHERE id = ? AND driver_id = ?");
			rateDriverQuery.setDouble(1, (double)job.getDriverRating());
			rateDriverQuery.setInt(2, job.getJobID());
			rateDriverQuery.setInt(3, driver_id);
			
			System.out.println("Database.java -> DriverID = " + driver_id);
			int i = rateDriverQuery.executeUpdate();
			if (i == 0) {
				return new Message("FAILED");
			}
			else {
				DecimalFormat df = new DecimalFormat("#.#");
				PreparedStatement getAverageRating = conn.prepareStatement("SELECT avg(driver_rating) AS average_rating FROM ride "
						+ "WHERE driver_id = ? AND status = 'FINISHED'");
				getAverageRating.setInt(1, driver_id);
				ResultSet rs1 = getAverageRating.executeQuery();
				while (rs1.next()) {
					double rating = Double.parseDouble((df.format(rs1.getDouble("average_rating"))));
					System.out.println("Database.java -> Average has been calculated as..." + rs1.getDouble("average_rating"));
					
					PreparedStatement setDriverRating = conn.prepareStatement("UPDATE drivers SET rating = ? WHERE id = ?");
					setDriverRating.setDouble(1, rating);
					setDriverRating.setInt(2, driver_id);
					
					i = setDriverRating.executeUpdate();
					if (i == 0) {
						System.out.println("Database.java -> Problem when adding driver rating to drivers table");
					} else {
						System.out.println("Database.java -> Driver rating added to drivers table");
					}
				}
				System.out.println("Database.java -> Driver was rated successfully");
				return new Message("DRIVER RATED");
			}
		} catch (SQLException e) {
			System.out.println("Driver rating failed");
//			e.printStackTrace();
		}
		return new Message("FAILED");
	}
	
	public Message getMyRating(User user) {
		PreparedStatement getMyRatingQuery;
		DecimalFormat df = new DecimalFormat("#.#");
		String myRating = "0";
		try {
			if (user.getClass().equals(Customer.class)) {
				getMyRatingQuery = conn.prepareStatement("SELECT rating FROM customers WHERE id = ? LIMIT 1");
				getMyRatingQuery.setInt(1, user.getId());
				ResultSet rs1 = getMyRatingQuery.executeQuery();
				while (rs1.next()) {
					myRating = df.format(rs1.getDouble("rating"));
				}
				return new Message("RATED", myRating);
			} else if (user.getClass().equals(Driver.class)) {
				getMyRatingQuery = conn.prepareStatement("SELECT rating FROM drivers WHERE id = ? LIMIT 1");
				getMyRatingQuery.setInt(1, user.getId());
				ResultSet rs1 = getMyRatingQuery.executeQuery();
				while (rs1.next()) {
					myRating = df.format(rs1.getDouble("rating"));
				}
				return new Message("RATED", myRating);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return new Message("FAILED", myRating);
	}
	
	public Message checkAccepted(Job job) {
		try {
			PreparedStatement checkRequestQuery = conn.prepareStatement("SELECT * FROM ride WHERE id = ?");
			checkRequestQuery.setInt(1, job.getJobID());
			ResultSet rs1 = checkRequestQuery.executeQuery();
			while (rs1.next()) {
//				Check the status of the ride
				String status = rs1.getString("status");
				if (status.equals("ACCEPTED") || status.equals("ONGOING") || status.equals("FINISHED")) {
					DecimalFormat df = new DecimalFormat("#.#");
//					Get the drivers details from the database
					PreparedStatement getDriverInfo = conn.prepareStatement("SELECT * FROM drivers WHERE id = ?");
					int driverID = rs1.getInt("driver_id");
					getDriverInfo.setInt(1, driverID);
					ResultSet rs2 = getDriverInfo.executeQuery();
//					Add the drivers details to the job object
					while (rs2.next()) {
						Driver driver = new Driver(rs2.getString("firstname"), 
								rs2.getString("surname"), 
								rs2.getString("email"), 
								rs2.getString("tel_no"));
//						Format the rating
						double rating = Double.parseDouble(df.format(rs2.getDouble("rating")));
						driver.setRating(rating);
						driver.setId(driverID);
						driver.setCar_make(rs2.getString("car_make"));
						driver.setCar_model(rs2.getString("car_model"));
						driver.setReg_no(rs2.getString("reg_no"));
						job.setDriver(driver);
//						In the odd case that the job is already marked as finished then inform the client
						if (status.equals("FINISHED")) {
							return new Message("FINISHED", job);
						} else if(status.equals("ONGOING")) {
							return new Message("ONGOING", job);
						}
						return new Message("ACCEPTED", job);
					}
//					System.out.println("Database.class -> No driver?");
					return new Message("FAILED");
				}
				System.out.println("Database.class -> No change in status");
				return new Message("REQUESTING");
			}
			System.out.println("Database.class -> No matching job?");
			return new Message("FAILED");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Database.class -> Something went very wrong!");
		return new Message("FAILED");
	}
	
	public Message checkStatus(Customer c){
		try{
			PreparedStatement checkState = conn.prepareStatement("SELECT * FROM ride WHERE cust_id = ? AND (status = 'REQUESTING'" +
																"OR status = 'ACCEPTED' OR status = 'ONGOING')");
			checkState.setInt(1, c.getId());
			ResultSet rs1 = checkState.executeQuery();
			
			if(rs1.next()){//checks if they have a current ride in any unfinished state
				Job j = new Job(rs1.getString("end_location"),rs1.getString("start_location"), rs1.getString("job_type"), c, rs1.getString("details"));
				j.setJobID(rs1.getInt("id"));
				String status = rs1.getString("status");
				j.setState(status);
				
				if(status.equals("REQUESTING")){
					return new Message("REQUESTING", j);
				}
				else if (status.equals("ACCEPTED")){
					Driver d = getDriver(rs1.getInt("driver_id"));
					j.setDriver(d);
					return new Message("ACCEPTED", j);
				}
				else if(status.equals("ONGOING")){
					Driver d = getDriver(rs1.getInt("driver_id"));
					j.setDriver(d);
					j.setStartTime(Long.toString(rs1.getLong("pick_up_time")));
					return new Message("ONGOING", j);
				}
			}
			else{//check if they have a finished ride that needs rating
				
				PreparedStatement checkRate = conn.prepareStatement("SELECT * FROM ride WHERE cust_id = ? AND status = 'FINISHED'" +
																	"AND driver_rating IS NULL");
				checkRate.setInt(1, c.getId());
				ResultSet rs2 = checkRate.executeQuery();
				if(rs2.next() == true){
					Job j = new Job(rs2.getString("end_location"),rs2.getString("start_location"), rs2.getString("job_type"), c, rs2.getString("details"));
					j.setJobID(rs2.getInt("id"));
					Driver d = getDriver(rs2.getInt("driver_id"));
					j.setDriver(d);
					j.setStartTime(Long.toString(rs2.getLong("pick_up_time")));
					j.setEndTime(Long.toString(rs2.getLong("drop_off_time")));
					System.out.println("Database -> ride needs rating (JOB ID: " + j.getJobID() + ")");
					return new Message("CURRENTLY RATING", j);//NEEDS CHANGING BACK TO CURRENTLY RATING
				}
				else{
					return new Message("NO CURRENT RIDES");
				}
			}
			return new Message("FAILED");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return new Message("FAILED");
		}
	}
	
	public Message checkStatus(Driver d){
		try{
			PreparedStatement checkState = conn.prepareStatement("SELECT * FROM ride WHERE driver_id = ? AND " + 
																"(status = 'ACCEPTED' OR status = 'ONGOING')");
			checkState.setInt(1, d.getId());
			ResultSet rs1 = checkState.executeQuery();
			if(rs1.next() == true){//checks if they have a current ride in any unfinished state
				Job j = new Job(rs1.getString("end_location"),rs1.getString("start_location"), d);
				String status = rs1.getString("status");
				j.setJobID(rs1.getInt("id"));
				j.setJobType(rs1.getString("job_type"));
				j.setDetails(rs1.getString("details"));
				Customer c = getCustomer(rs1.getInt("cust_id"));
				j.setCustomer(c);
				j.setDriver(d);
				j.setState(status);
				
				if (status.equals("ACCEPTED")){
					
					return new Message("CURRENTLY ACCEPTED", j);
				}
				else if(status.equals("ONGOING")){
					j.setStartTime(Long.toString(rs1.getLong("pick_up_time")));
					return new Message("CURRENTLY ONGOING", j);
				}
			}
			else{//check if they have a finished ride that needs rating
				
				PreparedStatement checkRate = conn.prepareStatement("SELECT * FROM ride WHERE driver_id = ? AND status = 'FINISHED'" +
																	"AND cust_rating IS NULL");
				checkRate.setInt(1, d.getId());
				ResultSet rs2 = checkRate.executeQuery();
				if(rs2.next() == true){
					Job j = new Job(rs2.getString("end_location"),rs2.getString("start_location"), d);
					String status = rs2.getString("status");
					j.setJobID(rs2.getInt("id"));
					j.setJobType(rs2.getString("job_type"));
					j.setDetails(rs2.getString("details"));
					Customer c = getCustomer(rs2.getInt("cust_id"));
					j.setCustomer(c);
					j.setStartTime(Long.toString(rs2.getLong("pick_up_time")));
					j.setStartTime(Long.toString(rs2.getLong("drop_off_time")));
					j.setState(status);
					
					return new Message("CURRENTLY RATING", j);//NEEDS CHANGING BACK TO CURRENTLY RATING
				}
				else{
					return new Message("NO CURRENT RIDES", new Job());
				}
			}
			return new Message("FAILED");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return new Message("FAILED");
		}
	}
	
	public Message contact(User u) {
		PreparedStatement contactQuery;
		String type;
		type = (u.getClass().equals(Customer.class)) ? "Customer" : "Driver";
//		System.out.println("Database.java -> Message == " + u.getMessage());
//		System.out.println("Database.java -> type == " + u.getClass().getName());
		try {
			if (type.equals("Customer")) {
				contactQuery = conn.prepareStatement("INSERT INTO customer_messages (customer_id, message, time, status) VALUES (?, ?, ?, 'PENDING')"); 
			} else {
				contactQuery = conn.prepareStatement("INSERT INTO driver_messages (driver_id, message, time, status) VALUES (?, ?, ?, 'PENDING')");
			} 
			contactQuery.setInt(1, u.getId());
			contactQuery.setString(2, u.getMessage());
			long time = new Date().getTime();
			contactQuery.setLong(3, time);
			int i = contactQuery.executeUpdate();
			return (i == 0) ? new Message("FAILED") : new Message("SUCCESS");
		} catch (SQLException e){
			e.printStackTrace();
		}
		return new Message("FAILED");
	}
	
	public Driver getDriver(int id){
		
		try{
			PreparedStatement getDriverInfo = conn.prepareStatement("SELECT * FROM drivers WHERE id = ?");
			getDriverInfo.setInt(1, id);
			ResultSet rs = getDriverInfo.executeQuery();
	//		Add the drivers details to the job object
			while (rs.next()) {
				Driver driver = new Driver(rs.getString("firstname"), 
						rs.getString("surname"), 
						rs.getString("email"), 
						rs.getString("tel_no"));
	//			Format the rating
				DecimalFormat df = new DecimalFormat("#.#");
				double rating = Double.parseDouble(df.format(rs.getDouble("rating")));
				driver.setRating(rating);
				driver.setId(id);
				driver.setCar_make(rs.getString("car_make"));
				driver.setCar_model(rs.getString("car_model"));
				driver.setReg_no(rs.getString("reg_no"));
				return driver;
			}
			return null;
		} catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
public Customer getCustomer(int id){
		
		try{
			PreparedStatement getCustomerInfo = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");
			getCustomerInfo.setInt(1, id);
			ResultSet rs = getCustomerInfo.executeQuery();
	//		Add the drivers details to the job object
			while (rs.next()) {
				Customer cust = new Customer(rs.getString("firstname"), 
						rs.getString("surname"), 
						rs.getString("email"), 
						rs.getString("tel_no"));
	//			Format the rating
				DecimalFormat df = new DecimalFormat("#.#");
				double rating = Double.parseDouble(df.format(rs.getDouble("rating")));
				cust.setRating(rating);
				cust.setId(id);
				return cust;
			}
			return null;
		} catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}