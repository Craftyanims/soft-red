package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Journal  implements Serializable {

	public String name;
	public Editor editor;
	
	public List<Paper> papers;
	
	public University owningUniversity;

	public ArrayList<String> deadlines;
	
	public Journal(String name) {
		this.name = name;
		
		this.papers = new ArrayList<Paper>();
	}
	
	
}
