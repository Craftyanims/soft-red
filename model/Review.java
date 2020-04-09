package model;

import java.io.Serializable;


public class Review implements Serializable {

	public Paper paper;
	public Reviewer reviewer;
	public String commentsFilePath;
	public int score;
	public Review(Reviewer r, Paper p, String path) {
		reviewer = r;
		paper = p;
		commentsFilePath = path;
	}
	
}
