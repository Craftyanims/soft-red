package model;

import java.io.Serializable;

public class Reviewer extends User implements Serializable {

	public Object re1;
	public Object re2;
	public Object re3;
	public Reviewer reviewer;
	

	public Reviewer(String name, String password) throws Exception {
		super(name, password);
		
	}
	
}
