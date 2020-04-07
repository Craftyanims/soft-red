package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Reviewer extends User implements Serializable {
	private ArrayList<Journal>toReview;
	public Reviewer(String name, String password) throws Exception {
		super(name, password);
		toReview = new ArrayList<Journal>();
	}
	
}
