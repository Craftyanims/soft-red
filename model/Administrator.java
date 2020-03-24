package model;

import java.io.Serializable;

public class Administrator extends User implements Serializable {

	public Administrator(String name, String password) throws Exception {
		super(name, password);
	}
}
