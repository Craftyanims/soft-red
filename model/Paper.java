package model;

import java.io.Serializable;

public class Paper implements Serializable {

	public String name;
	
	public Researcher author;
	
	public Paper(String name) {
		this.name = name;
	}
	
}
