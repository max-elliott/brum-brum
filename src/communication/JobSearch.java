package communication;

import java.io.Serializable;

/**
 * 
 * @author Max Elliott, Robert Campbell
 * @created 2017-03-02
 * @version 2017-03-14
 */
public class JobSearch implements Serializable {
	private int jobID;
	private int driverID;
	
	/**
	 * Constructor used for a driver to initially search for jobs.
	 */
	public JobSearch(int driverID, int previousJobID){
		this.jobID = previousJobID;
		this.driverID = driverID;
	}
	
	/**
	 * Initial constructor
	 * @param driverID, id of the driver
	 */
	public JobSearch(int driverID){
		this.jobID = -1;
		this.driverID = driverID;
	}
	
	public int getDriverID() {
		return driverID;
	}
	

	public int getJobId() {
		return this.jobID;
	}
}
