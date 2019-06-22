package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import clientside.Customer;
import clientside.Driver;
import communication.Message;

/**
 * Created by Rob on 22/02/2017.
 * @author Max Robert Moosebell & Max Elliott
 * @version 2017-03-03
 */
public class ServerThread extends Thread {
	
    private Socket socket = null;
    private Object clientObject;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
    	ServerProtocol msp = new ServerProtocol();
        try (
                ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream fromClient = new ObjectInputStream(socket.getInputStream());
        ) {
            
            while(true){
	            if ((clientObject = fromClient.readObject()) != null) {
	            	if(clientObject.getClass().equals(Message.class)){
		            	Message clientMessage = (Message) clientObject;
		            	Message mspResponse = msp.processMessage(clientMessage);
		            	if(mspResponse.getData() != null){
			            	if(mspResponse.getData().getClass().equals(Message.class)){
			            		System.out.println("Thread: Closing thread socket.");
				            	toClient.writeObject(mspResponse);
				            	break;
			            	}
		            	}
		            	toClient.writeObject(mspResponse);
	            	}
	            	else {
		    			System.out.println("Object not recognised.");
		            }
	            }
            }
            
        } catch (SocketException e) {
        	if (msp.getUser() != null) {
        		System.out.println("user disconnected (" + msp.getUser().getFirstName() + " " + msp.getUser().getLastName() + " (" + msp.getUser().getClass().getSimpleName() +"))");
        	
        	}
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (EOFException e) {
        	System.out.println("EOFException! Possibly not threadsafe");
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally{
        	try{
	        	if(msp.getUser().getClass().equals(Customer.class)){//remove customer from connectedCustomers
	    			Server.connectedCustomers.remove(msp.getUser().getEmail());
	    			System.out.println("Thread: Removed customer from connectedCustomers");
	    		}
	    		else if(msp.getUser().getClass().equals(Driver.class)){//remove driver from connectedDrivers
	    			Server.connectedDrivers.remove(msp.getUser().getEmail());
	    			System.out.println("Thread: Removed driver from connectedDrivers");
	    		}
        	} catch(NullPointerException e){
        		System.out.print("Couldn't log on or create account or something");
        	}
        	
        	
        	try {
        		this.interrupt();
				this.socket.close();
				System.out.println("Thread: Thread socket closed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }
}
