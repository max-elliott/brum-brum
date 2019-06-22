package database;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/** A basic encryption program using the Secure Hash Algorithm (SHA) and Salt.
 * To be incorporated into the Server/Database code to ensure that our passwords
 * are not stored in an easy to crack manner.
 * 
 * Currently uses SHA-1 but that can easily be changed to more secure varieties of SHA
 * 
 * @author Robert Campbell
 * @created 06/03/2017
 * @version 13/03/2017
 */
public class Encryption {
	
	/** method to generate the salt used to encrypt the password
	 * NOTE: This MUST be saved along with the hashed password in the database
	 * If we don't save the Salt for each user then we can't do the same encryption
	 * on the password the users submits when they log in!!!!
	 * 
	 * @return returns the salt
	 */
	public static byte[] getSalt(){
		try {
		    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//		    Create array for salt
		    byte[] salt = new byte[16];
//		    Get a random salt
		    sr.nextBytes(salt);
		    return salt;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** method to encrypt a password
	 * 
	 * @param password the user supplied password
	 * @param salt the salt for that particular user
	 * @return the hashed password to be stored in the database
	 */
	public static String encrypt(String password, byte[] salt) {
		String generatedPassword = null;
		try {
//			Uses SHA-1 but could be changed to SHA-256, SHA-384 OR SHA-512 depending on how secure we want it to be
//			The longer the hash is the more secure it is
			MessageDigest md = MessageDigest.getInstance("SHA-1");
//			Add the password bytes to be digested
			md.update(salt);
			byte[] bytes = md.digest(password.getBytes());
//			Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
//            Get complete hashed password in hex format
            generatedPassword = sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return generatedPassword;
	}
//	public static void main(String[] args) {
//		String password = "passwod";
//		byte[] salt = getSalt();
//		String hash = encrypt(password, salt);
////		System.out.println("Hash generated from '" + password + "': " + hash + " (Salt: " + salt + ")");
////		System.out.println(hash.equals(encrypt("password", salt)));
////		System.out.println(hash.length());
//	}

}
