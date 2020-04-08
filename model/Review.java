package model;

import java.io.Serializable;

public class Review implements Serializable {

	public Paper paper;
	public Reviewer reviewer;
	public String commentsFilePath;
	public int score;
	
}
