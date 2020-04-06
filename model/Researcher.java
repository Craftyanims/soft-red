package model;

import java.io.Serializable;

public class Researcher extends User implements Serializable {

	public String name;
	public String password;
	
	public Researcher(String name, String password) throws Exception {
		super(name, password);
	}
	
}
