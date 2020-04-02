package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Paper implements Serializable {

	public String name;
	
	public Researcher author;
	
	public ArrayList<Review> reviews;
	
	public PaperStatus status;
	
	public Paper(String name) {
		this.name = name;
		this.status = PaperStatus.SUBMITTED;
		this.reviews = new ArrayList<Review>();
	}
	
	public void addReview(Review review) {
		this.reviews.add(review);
	}
	
}
