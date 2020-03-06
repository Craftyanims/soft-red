package model;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class University implements Serializable {

	public String name;
	public ArrayList<Journal> journals;
	
	public ArrayList<Administrator> administrators;
	
	public ArrayList<Reviewer> reviewers;
	
	public University(String name) {
		this.name = name;
		
		this.journals = new ArrayList<Journal>();
		this.administrators = new ArrayList<Administrator>();
		this.reviewers = new ArrayList<Reviewer>();
	}
	
	public void addJournal(Journal journal) {
		this.journals.add(journal);
		
		journal.owningUniversity = this;
	}
}