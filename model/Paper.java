package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paper implements Serializable {

	public String name;
	
	public Researcher author;
	public Reviewer reviewer;
	
	public List<Reviewer> reviewers;
	
	public ArrayList<Reviewer> nominated;
	
	public Object researcher;

	public String paperPath;
	
	public Paper(String name) {
		this.name = name;
		this.reviewers = new ArrayList<Reviewer>();
	}

	public void add(Reviewer re1) {
		// TODO Auto-generated method stub
		
	}
	
}
