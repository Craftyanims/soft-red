package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DataStore implements Serializable {

	public University university; 
	
	
	public DataStore() {
		
		//Create some hard-coded data to allow the datastore to work
		
		University uni = new University("University of Calgary");
		
		this.university = uni;
		
		
		Journal mainJournal = new Journal("The main, hard-coded journal");
		this.university.addJournal(mainJournal);
		
		
		
	}
	
	

	/**
	 * serialization and deserialization code adapted from 
	 * https://www.geeksforgeeks.org/object-graph-java-serialization/
	 */
	public boolean serialize() {
		try {
			FileOutputStream fos = new FileOutputStream("serialized.database"); 
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(this);
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * serialization and deserialization code adapted from 
	 * https://www.geeksforgeeks.org/object-graph-java-serialization/
	 */	
	public static DataStore load() {
		try {
			FileInputStream fis = new FileInputStream("serialized.database"); 
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			DataStore db = (DataStore) ois.readObject();
			
			return db;
		}
		catch(Exception e) {
			//TODO: This is terrible and should never have existed.
			return new DataStore();
		}
	}
}
