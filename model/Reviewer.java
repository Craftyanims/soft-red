package model;

import java.io.Serializable;

public class Reviewer extends User implements Serializable {

	public Reviewer(String name, String password) throws Exception {
		super(name, password);
	}
	
}
