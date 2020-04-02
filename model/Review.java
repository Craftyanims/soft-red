package model;

import java.io.Serializable;

public class Review implements Serializable {

	public Reviewer reviewer;
	public Paper paper;
	public int score;
	
	public Review(Reviewer reviewer, Paper paper, int score) {
		this.reviewer = reviewer;
		this.paper = paper;
		this.score = score;
	}
	
}
