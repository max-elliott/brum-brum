//package test;
//
//import org.junit.Test;
//
//import clientside.Customer;
//import clientside.Driver;
//import clientside.User;
//import communication.CreateAccount;
//import communication.Job;
//import communication.JobSearch;
//import communication.Login;
//import communication.Message;
//import database.Database;
//
//import static org.junit.Assert.assertEquals;
//
//public class databaseTest {
////	 @Test
////	 public void createAccountTest1() {
////	 Database data1 = new Database();
////	 CreateAccount acc=new
////	 CreateAccount("Cus","C","C2@gmail.com","12345678911","password");
////	
////	 String expected="ACCOUNT CREATED";
////	 String actual =data1.createAccount(acc).getId();
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void createAccountTest2() {
////	 Database data1 = new Database();
////	 CreateAccount acc=new
////	 CreateAccount("Cus","C","C1@gmail.com","12345678911","password");
////	 String expected="EMAIL IN USE";
////	 String actual =data1.createAccount(acc).getId();
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void createDriverAccountTest1() {
////	 Database data1 = new Database();
////	 CreateAccount acc=new
////	 CreateAccount("Dri","D","D3@gmail.com","12345678911","password");
////	
////	 String expected="ACCOUNT CREATED";
////	 String actual =data1.createDriverAccount(acc,"Limo","The Longest","R0S1222").getId();
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void createDriverAccountTest2() {
////	 Database data1 = new Database();
////	 CreateAccount acc=new
////	 CreateAccount("Dri","D","D2@gmail.com","12345678911","password");
////	 String expected="Account already exists";
////	 String actual =data1.createDriverAccount(acc,"Limo","The Longest","R0S1222").getId();
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void loginQueryTest1(){
////	 Database data1 = new Database();
////	 Login l=new Login("C1@gmail.com", "password", "Customer") ;
////	 String expected="LOGIN SUCCESSFUL";
////	 String actual =data1.loginQuery(l).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void loginQueryTest2(){
////	 Database data1 = new Database();
////	 Login l=new Login("C1@gmail.com", "password", "Customer") ;
////	 String expected="ALREADY LOGGED IN";
////	 String actual =data1.loginQuery(l).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void loginQueryTest3(){
////	 Database data1 = new Database();
////	 Login l=new Login("C1@gmail.com", "paffword", "Customer") ;
////	 String expected="LOGIN FAILED";
////	 String actual =data1.loginQuery(l).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void loginQueryTest4(){
////	 Database data1 = new Database();
////	 Login l=new Login("C213@gmail.com", "password", "Customer") ;
////	 String expected="LOGIN FAILED";
////	 String actual =data1.loginQuery(l).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void loginQueryTest5(){
////	 Database data1 = new Database();
////	 Login l=new Login("D2@gmail.com", "password", "Driver") ;
////	 String expected="LOGIN SUCCESSFUL";
////	 String actual =data1.loginQuery(l).getId();
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void loginQueryTest6(){
////	 Database data1 = new Database();
////	 Login l=new Login("D2@gmail.com", "password", "Driver") ;
////	 String expected="LOGIN FAILED";
////	 String actual =data1.loginQuery(l).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void loginQueryTest7(){
////	 Database data1 = new Database();
////	 Login l=new Login("D1@gmail.com", "paffword", "Driver") ;
////	 String expected="LOGIN FAILED";
////	 String actual =data1.loginQuery(l).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void logoutQueryTest1(){
////	 Database data1 = new Database();
////	 User u=new User("Cus", "C","C1@gmail.com","12345678911") ;
////	 String expected="LOGOUT SUCCESSFUL";
////	 String actual =data1.logoutQuery(u).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void logoutQueryTest2(){
////	 Database data1 = new Database();
////	 User u=new User("Cus", "C","C21@gmail.com","12345678911") ;
////	 String expected="ERROR";
////	 String actual =data1.logoutQuery(u).getId();
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void logoutQueryTest3(){
////	 Database data1 = new Database();
////	 User u=new User("Dri", "D","D2@gmail.com","12345678911") ;
////	 String expected="LOGOUT SUCCESSFUL";
////	 String actual =data1.logoutQuery(u).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void logoutQueryTest4(){
////	 Database data1 = new Database();
////	 User u=new User("Dri", "D","D2@gmail.com","12345678911") ;
////	 String expected="LOGOUT FAILED";
////	 String actual =data1.logoutQuery(u).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void requestRideQueryTest1(){
////	 Database data1 = new Database();
////	 Customer c1=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c1.setId(50);
////	 Job j=new Job("university", "new street","Person",c1) ;
////	 String expected="REQUEST PICKUP SUCCESSFUL";
////	 String actual =data1.requestRideQuery(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void requestRideQueryTest2(){
////	 Database data1 = new Database();
////	 Customer c1=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c1.setId(50);
////	 Job j=new Job("university", "new street","Person",c1) ;
////	 String expected="ALREADY REQUESTED PICKUP";
////	 String actual =data1.requestRideQuery(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void requestRideQueryTest3(){
////	 Database data1 = new Database();
////	 Customer c1=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c1.setId(43);
////	 Job j=new Job("university", "new street","Person",c1) ;
////	 String expected="FAILED";
////	 String actual =data1.requestRideQuery(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void searchJobsQueryTest1(){
////	 Database data1 = new Database();
////	 JobSearch search=new JobSearch(17,-1) ;
////	 String expected="JOB";
////	 String actual =data1.searchJobsQuery(search).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void searchJobsQueryTest2(){
////	 Database data1 = new Database();
////	 JobSearch search=new JobSearch(80,-1) ;
////	 String expected="FAILED";
////	 String actual =data1.searchJobsQuery(search).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void searchJobsQueryTest3(){
////	 Database data1 = new Database();
////	 JobSearch search=new JobSearch(16,1000) ;
////	 String expected="FAILED";
////	 String actual =data1.searchJobsQuery(search).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////
////	 @Test
////	 public void acceptJobQueryTest1(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("New Street","University of Birmingham",d) ;
////	 j.setJobID(184);
////	 String expected="JOB ACCEPTED";
////	 String actual =data1.acceptJobQuery(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void acceptJobQueryTest2(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("The Prince of Wales, Moseley","University of  Birmingham",d) ;
////	 j.setJobID(1000);
////	 String expected="JOB UNAVAILABLE";
////	 String actual =data1.acceptJobQuery(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////
////	 @Test
////	 public void endJobQueryTest1(){
////	 Database data1 = new Database();
////	 Customer c1=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c1.setId(50);
////	 Job j=new Job("New Street","Person","University of Birmingham",c1) ;
////	 String expected="JOB COMPLETED";
////	 String actual =data1.endJobQuery(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void endJobQueryTest2(){
////	 Database data1 = new Database();
////	 Customer c1=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c1.setId(1000);
////	 Job j=new Job("The Prince of Wales, Moseley","Person","University of Birmingham",c1) ;
////	 String expected="FAILED";
////	 String actual =data1.endJobQuery(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////
////	 @Test
////	 public void requestHistoryQueryTest1(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 String expected="HISTORY";
////	 String actual =data1.requestHistoryQuery(d).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void requestHistoryQueryTest2(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(1000);
////	 String expected="FAILED";
////	 String actual =data1.requestHistoryQuery(d).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void requestHistoryQueryTest3(){
////	 Database data1 = new Database();
////	 Customer c=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c.setId(50);
////	 Job j=new Job("New Street","Person","University of Birmingham",c) ;
////	 String expected="HISTORY";
////	 String actual =data1.requestHistoryQuery(c).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void requestHistoryQueryTest4(){
////	 Database data1 = new Database();
////	 Customer c=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c.setId(1000);
////	 Job j=new Job("The Prince of Wales, Moseley","Person","University of Birmingham",c) ;
////	 String expected="FAILED";
////	 String actual =data1.endJobQuery(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void setAvailableTest1(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 String expected="DRIVER SET AVAILABLE";
////	 String actual =data1.setAvailable(d).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void setAvailableTest2(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(1000);
////	 String expected="FAILED";
////	 String actual =data1.setAvailable(d).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void setUnavailableTest1(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 String expected="DRIVER SET UNAVAILABLE";
////	 String actual =data1.setUnavailable(d).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void setUnavailableTest2(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(-1);
////	 String expected="FAILED";
////	 String actual =data1.setUnavailable(d).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void setStartedTest1(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(185);
////	 String expected="JOB STARTED";
////	 String actual =data1.setStarted(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void setStartedTest2(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("there","here",d) ;
////	 j.setJobID(-2);
////	 String expected="FAILED";
////	 String actual =data1.setFinished(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////
////	 @Test
////	 public void setFinishedTest1(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(185);
////	 String expected="JOB FINISHED";
////	 String actual =data1.setFinished(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void setFinishedTest2(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("The Prince of Wales, Moseley","University of Birmingham",d) ;
////	 j.setJobID(-2);
////	 String expected="FAILED";
////	 String actual =data1.setFinished(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void rateCustomerTest1(){
////	 Database data1 = new Database();
////	 Customer c=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c.setId(50);
////	 Job j=new Job("six way","five way","Person",c) ;
////	 j.setJobID(185);
////	 j.setCustomerRating(3);
////	 String expected="CUSTOMER RATED";
////	 String actual =data1.rateCustomer(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void rateCustomerTest2(){
////	 Database data1 = new Database();
////	 Customer c=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c.setId(50);
////	 Job j=new Job("six way","five way","Person",c) ;
////	 j.setJobID(-2);
////	 j.setCustomerRating(3);
////	 String expected="FAILED";
////	 String actual =data1.setFinished(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void rateCustomerTest3(){
////	 Database data1 = new Database();
////	 Customer c=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c.setId(-2);
////	 Job j=new Job("six way","five way","Person",c) ;
////	 j.setJobID(185);
////	 j.setCustomerRating(3);
////	 String expected="FAILED";
////	 String actual =data1.setFinished(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void rateDriverTest1(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(185);
////	 j.setCustomerRating(3);
////	 String expected="DRIVER RATED";
////	 String actual =data1.rateDriver(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void rateDriverTest2(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 Job j=new Job("six way","five way",d);
////	 j.setJobID(185);
////	 j.setDriverRating(3);
////	 String expected="DRIVER RATED";
////	 String actual =data1.rateDriver(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void rateDriverTest3(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(-2);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(185);
////	 j.setDriverRating(3);
////	 String expected="FAILED";
////	 String actual =data1.setFinished(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void rateDriverTest4(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(-2);
////	 j.setDriverRating(3);
////	 String expected="FAILED";
////	 String actual =data1.setFinished(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void rateDriverTest5(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(100);
////	 j.setDriverRating(3);
////	 String expected="FAILED";
////	 String actual =data1.setFinished(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
//	 @Test
//	 public void getMyRatingTest1(){
//	 Database data1 = new Database();
//	 User u= new User("Dri", "D","D2@gmail.com","12345678911");
//	 u.setId(17);
//	 String expected="RATED";
//	 String actual =data1.getMyRating(u).getId();
//	 System.out.println(actual);
//	 assertEquals(expected, actual);
//	 }
////	 @Test
////	 public void getMyRatingTest2(){
////	 Database data1 = new Database();
////	 User u= new User("Dri", "D","D2@gmail.com","12345678911");
////	 u.setId(-2);
////	 String expected="FAILED";
////	 String actual =data1.getMyRating(u).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void getMyRatingTest3(){
////	 Database data1 = new Database();
////	 User u= new User("Cus", "C","C1@gmail.com","12345678911");
////	 u.setId(50);
////	 String expected="RATED";
////	 String actual =data1.getMyRating(u).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void getMyRatingTest4(){
////	 Database data1 = new Database();
////	 User u= new User("Cus", "C","C1@gmail.com","12345678911");
////	 u.setId(-2);
////	 String expected="FAILED";
////	 String actual =data1.getMyRating(u).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkAcceptedTest1(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(185);
////	 String expected="FINISHED";
////	 String actual =data1.checkAccepted(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkAcceptedTest2(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(186);
////	 String expected="ONGOING";
////	 String actual =data1.checkAccepted(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkAcceptedTest3(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(188);
////	 String expected="ACCEPTED";
////	 String actual =data1.checkAccepted(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkAcceptedTest4(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(-2);
////	 String expected="FAILED";
////	 String actual =data1.checkAccepted(j).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkStatusTest1(){
////	 Database data1 = new Database();
////	 Customer c=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c.setId(50);
////	 Job j=new Job("six way","five way","Person",c) ;
////	 j.setJobID(192);
////	 String expected="REQUESTING";
////	 String actual =data1.checkStatus(c).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkStatusTest2(){
////	 Database data1 = new Database();
////	 Customer c=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c.setId(50);
////	 Job j=new Job("six way","five way","Person",c) ;
////	 j.setJobID(188);
////	 String expected="ACCEPTED";
////	 String actual =data1.checkStatus(c).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkStatusTest3(){
////	 Database data1 = new Database();
////	 Customer c=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c.setId(50);
////	 Job j=new Job("six way","five way","Person",c) ;
////	 j.setJobID(186);
////	 String expected="ONGOING";
////	 String actual =data1.checkStatus(c).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkStatusTest4(){
////	 Database data1 = new Database();
////	 Customer c=new Customer("Cus", "C","C1@gmail.com","12345678911");
////	 c.setId(50);
////	 Job j=new Job("six way","five way","Person",c) ;
////	 j.setJobID(193);
////	 String expected="NO CURRENT RIDES";
////	 String actual =data1.checkStatus(c).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkStatusTest5(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(188);
////	 String expected="CURRENTLY ACCEPTED";
////	 String actual =data1.checkStatus(d).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkStatusTest6(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(186);
////	 String expected="CURRENTLY ONGOING";
////	 String actual =data1.checkStatus(d).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	 @Test
////	 public void checkStatusTest7(){
////	 Database data1 = new Database();
////	 Driver d= new Driver("Dri", "D","D2@gmail.com","12345678911");
////	 d.setId(17);
////	 Job j=new Job("six way","five way",d) ;
////	 j.setJobID(193);
////	 String expected="NO CURRENT RIDES";
////	 String actual =data1.checkStatus(d).getId();
////	 System.out.println(actual);
////	 assertEquals(expected, actual);
////	 }
////	@Test
////	public void getDriverTest1() {
////		Database data1 = new Database();
////		Driver d = new Driver("Dri", "D", "D2@gmail.com", "12345678911");
////		d.setId(17);
////		d.setCar_make("Limo");
////		d.setCar_model("The Longest");
////		d.setRating(1.5);
////		d.setReg_no("R0S1222");
////		Driver expected = d;
////		Driver actual = data1.getDriver(17);
////		System.out.println(actual);
////		assertEquals(expected, actual);
////	}
////	@Test
////	public void getCustomerTest1() {
////		Database data1 = new Database();
////		Customer c = new Customer("Cus", "C", "C1@gmail.com", "12345678911");
////		c.setId(50);
////		c.setRating(3);
////		Customer expected = c;
////		Customer actual = data1.getCustomer(50);
////		System.out.println(actual);
////		assertEquals(expected, actual);
////	}
//}