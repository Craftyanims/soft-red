package model;

import java.io.Serializable;

public class Editor extends User implements Serializable {

	public Editor(String name, String password) throws Exception {
		super(name, password);
	}
}
