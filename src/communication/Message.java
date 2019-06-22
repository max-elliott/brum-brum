package communication;
import java.io.Serializable;

/**Message objects to be sent across ObjectStreams between the client and the server.
 * 
 * @author Max Elliott
 * @created 2017-03-02
 * @version 2017-03-02
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;//identification string for the Message
	private Object data = null; //used to send any additional information the client/server will need
	
	public Message(String id){
		this.id = id;
	}
	
	public Message(String id, Object data) {
		this.id = id;
		this.data = data;
	}
	
	public String getId(){
		return this.id;
	}
	
	public Object getData(){
		return this.data;
	}
}