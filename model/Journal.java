package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Journal  implements Serializable {

	public String name;
	public Editor editor;
	
	public List<Paper> papers;
	
	public University owningUniversity;
	
	public Journal(String name) {
		this.name = name;
		
		this.papers = new ArrayList<Paper>();
	}
	
	
}
