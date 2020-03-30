package model;

import java.io.Serializable;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User implements Serializable {

	public int id;
	public String name;
	private byte[] hashedPassword;
	private byte[] salt;
	
	public User(String name, String password) throws Exception {
		this.name = name;
		
		setPassword(password);
		
		assignUserId();
	}
	
	private void assignUserId() {
		DataStore db = DataStore.load();
		this.id = db.nextUserId;
		db.nextUserId++;
		db.serialize();
	}
	
	private byte[] generatePasswordSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		
		return salt;
	}
	
	/**
	 * THIS IS NOT A SECURE PASSWORD HASHING FUNCTION
	 * Why is this not secure? Because I am not a cryptographer 
	 * and I am not capable of auditing this code. 
	 * I pretty much blindly copied it from
	 * https://www.baeldung.com/java-password-hashing
	 * @param password
	 * @return
	 */
	private byte[] generatePasswordHash(String password, byte[] salt) throws Exception {
		
		// 100 iterations is NOT ENOUGH. But for debugging purposes on laptops we're keeping this artificially low. (It caused an issue with my debugging)
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 100, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = factory.generateSecret(spec).getEncoded();
		
		return hash;	
	}
	
	public boolean checkPassword(String passwordToCheck) {
		try {
			byte[] hashToCheck = generatePasswordHash(passwordToCheck, this.salt);
			// https://stackoverflow.com/a/9499597
			return Arrays.equals(this.hashedPassword, hashToCheck);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setPassword(String password) throws Exception {
		this.salt = generatePasswordSalt();
		this.hashedPassword = generatePasswordHash(password, this.salt);
	}
	
	public String toString() {
		return this.name;
	}
	
}
