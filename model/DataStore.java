package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class DataStore implements Serializable {

	public University university; 
	
	public int nextUserId;
	
	private static DataStore singleton = null;
	
	public DataStore() {
		
		//Create some hard-coded data to allow the datastore to work
		
		University uni = new University("University of Calgary");
		
		this.university = uni;
		
		
		Journal mainJournal = new Journal("The main, hard-coded journal");
		this.university.addJournal(mainJournal);
		
		this.nextUserId = 0;
		
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
		if(DataStore.singleton != null) {
			return DataStore.singleton;
		}
		else {	
			try {
				FileInputStream fis = new FileInputStream("serialized.database"); 
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				DataStore.singleton = (DataStore) ois.readObject();
				
				return DataStore.singleton;
			}
			catch(Exception e) {
				//TODO: This is terrible and should never have existed.
				e.printStackTrace();
				DataStore db =new DataStore();
				db.serialize();
				return db;
			}
		}
	}
	
	
	/*
	 * Remove a reviewer from the main universities pool of users
	 * by user id of reviewer
	 */
	public static void removeReviewer(Reviewer reviewerToRemove) {
		DataStore db = DataStore.load();
		ArrayList<Reviewer> reviewers = db.university.reviewers;
		
		// Loop through the reviewers and remove the one with the matching user id
		for(Reviewer reviewer : reviewers) {
			if(reviewer.id == reviewerToRemove.id) {
				reviewers.remove(reviewer);
				db.serialize();
				break;
			}
		}
		
	}
	
	/*
	 * Remove a researcher from the main universities pool of users
	 * by user id of researcher
	 */
	public static void removeResearcher(Researcher researcherToRemove) {
		DataStore db = DataStore.load();
		ArrayList<Researcher> researchers = db.university.researchers;
		
		// Loop through the reviewers and remove the one with the matching user id
		for(Researcher researcher : researchers) {
			if(researcher.id == researcherToRemove.id) {
				researchers.remove(researcher);
				db.serialize();
				break;
			}
		}
		
	}
	
	/*
	 * Remove a editor from the main universities pool of users
	 * by user id of editor
	 */
	public static void removeEditor(Editor editorToRemove) {
		DataStore db = DataStore.load();
		ArrayList<Editor> editors = db.university.editors;
		
		// Loop through the editors and remove the one with the matching user id
		for(Editor editor: editors) {
			if(editor.id == editorToRemove.id) {
				editors.remove(editor);
				db.serialize();
				break;
			}
			
		}
		
	}
	
	
}
