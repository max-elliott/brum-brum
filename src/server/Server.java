package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import clientside.Customer;
import clientside.Driver;
import clientside.User;
import database.Database;
import interfaces.IServer;

/**
 * Created by Rob on 22/02/2017.
 * @author Robert Campbell and Max Elliott
 * @version 2017-03-03
 */
public class Server implements IServer {
	
	private ServerSocket serverSocket;
	private int portNumber = 7001;
	static protected Database db;
	private ExecutorService threadPool;
	private static final int numberOfThreads = 100;
	static public HashSet<String> connectedCustomers;
	static public HashSet<String> connectedDrivers;
	private boolean isStopped;
	
	public Server(){
		this.isStopped = false;
	}
	
	public Server(int portNumber){
		this.portNumber = portNumber;
	}
	
	public void setPort(String port) {
		this.portNumber = Integer.parseInt(port);
	}
	
    public void run() {
        
        try{
        	serverSocket = new ServerSocket(portNumber);
        	db = new Database();
        	connectedCustomers = new HashSet<String>();
        	connectedDrivers = new HashSet<String>();
        	threadPool = Executors.newFixedThreadPool(numberOfThreads);
        	int count = 0;
        	System.out.println("Thread count: " + count);
			while (this.isStopped == false) {
	            threadPool.execute(new ServerThread(serverSocket.accept()));
	            count++;
	            System.out.println("Thread count: " +count);
	        }
			System.out.println("Server: out of while loop");

        } catch (IOException e) {
            //System.err.println("Couldn't listen on port number: " + portNumber);
            threadPool.shutdown();
            System.exit(-1);
        }
    }
    
    /**
     * Stop server - used by GUI
     */
    public synchronized void stop(){
        
        try {
        	System.out.println("Server: start of stop()");

            this.isStopped = true;
            this.serverSocket.close();
            
            System.out.println("attempt to shutdown executor");
            threadPool.shutdown();
            threadPool.awaitTermination(5, TimeUnit.SECONDS);
            
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        finally {
            threadPool.shutdownNow();
            System.out.println("shutdown finished");
        }
    }
    
    public Set<String> getConnectedCustomers(){
    	return connectedCustomers;
    }
    
    public Set<String> getConnectedDrivers(){
    	return connectedDrivers;
    }
    
    public void setIsStopped(boolean b){
    	this.isStopped = b;
    }
    
    public boolean getIsStopped(){
    	return this.isStopped;
    }

    public static void main(String[] args) throws Exception {
        
        if (args.length != 1) {
        	Server server = new Server(7001);
        	server.run();
    	} else {
    		Server server = new Server(Integer.parseInt(args[0]));
    		server.run();
    	}
        
    }
}
