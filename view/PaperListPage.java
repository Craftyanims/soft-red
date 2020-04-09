package view;

import javafx.stage.Stage;
import java.util.ArrayList;

import global.Navigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Journal;
import model.Paper;
import model.PaperStatus;
import model.Reviewer;
import model.Researcher;
import model.Review;
import model.DataStore;
public class PaperListPage extends BasePane {

	private Paper paper;
	private model.DataStore db;
	private Stage primaryStage;
	private VBox mainPane;
	private int currentRow = 0;
	private String title;
//	private ArrayList<Reviewer> reviewers;
    
	
	public PaperListPage(Stage stage, String title, Paper paper) {
		super(stage, title);
		this.title = title;
		this.paper = paper;
		
		this.mainPane = new VBox();
		buildPaperListPage();		
		
		addFakeReviewers();
		
		
		
		/*-----------------------------------------------
		|
		|	Name: <Paper NAME>
		|	Editor: <Researcher NAME>
		|	
		|	1. reviewer		
		|	2. rerviwer
		|	3. reviewer
		|		Button: Edit reviewers
		|  		|
		|
		-------------------------------------------------*/
		
		buildPaperListPage();
			Label title1 = new Label(this.title);
			title1.setFont(new Font(30));
			this.add(title1, 0, currentRow, 4, 1);
			currentRow++;
			
		// Display the paper's status
		Label status = new Label("Staus: " + getFriendlyStatus(this.paper.status));
		this.mainPane.getChildren().add(status);
		
		//Display the list of reviews for the paper
		generateFakeReviews();
		this.mainPane.getChildren().add(generateReviewsList());
		
		//Display the Editor's accept and reject buttons
		this.mainPane.getChildren().add(generateAcceptRejectButtons());
	}
	
	private void add(Label title2, int i, int currentRow2, int j, int k) {
		// TODO Auto-generated method stub
		
	}

	private void addFakeReviewers() {
		
		try {
			Reviewer re1 = new Reviewer("name1", "password1");
			Reviewer re2 = new Reviewer("name2", "password2");
			Reviewer re3 = new Reviewer("name3", "password3");
			
			paper.add(re1);
			paper.add(re2);
			paper.add(re3);
			
		}
		catch(Exception e) {
			//TODO: Handle errors properly
		}

	}
	
	private void buildPaperListPage() {
		Label name = new Label("Name: " + paper.name);
		Label researcher = new Label("Researcher: " + paper.author.name);
		Button editButton = new Button("Edit Reviewers");
		editButton.setOnAction(event -> editReviewers());
		
		
		VBox reviewerList = generateReviewerList();
		
		
		mainPane.getChildren().addAll(name, researcher, reviewerList, editButton);
		this.setCenter(mainPane);
	}
	
	private VBox generateReviewerList() {
		VBox reviewerList = new VBox(20);
		
		for(Reviewer r : paper.reviewers) {
			VBox itemBox = new VBox(10);
			
			Label reviewerName1 = new Label("Reviewer 1: " + r.reviewer.name);
			Label reviewerName2 = new Label("Reviewer 2: " + r.reviewer.name);
			Label reviewerName3 = new Label("Reviewer 3: " + r.reviewer.name);
			Button editReviewerButton = new Button("Edit Reviewers");
			
			itemBox.getChildren().addAll(reviewerName1, reviewerName2, reviewerName3, editReviewerButton);
			reviewerList.getChildren().add(itemBox);
		}
		
		return reviewerList;
	}
	
	private void editReviewers() {
		Navigation.navigate(EditorPane.class);	
	}
	
	/**
	 * A temporary function to see the reviews before the review creation code
	 * has been finished
	 */
	private void generateFakeReviews() {
		try {
			Reviewer reviewer1 = new Reviewer("rev name", "");
			Reviewer reviewer2 = new Reviewer("rev name2", "");
			Reviewer reviewer3 = new Reviewer("rev name3", "");
			Review r1 = new Review(reviewer1, this.paper, "C:/fakepath");
			Review r2 = new Review(reviewer2, this.paper, "C:/fakepath2");
			Review r3 = new Review(reviewer3, this.paper, "C:/fakepath3");
			
			this.paper.reviews.add(r1);
			this.paper.reviews.add(r2);
			this.paper.reviews.add(r3);
		}
		catch(Exception e) {
			//TODO: deal with exceptions
		}
	}
	
	private VBox generateReviewsList() {
		VBox container = new VBox(20);
		
		Label reviewTitle = new Label("Reviews:");
		container.getChildren().add(reviewTitle);
		
		for(Review r : this.paper.reviews) {
			Label reviewerName = new Label("Reviewer: " + r.reviewer.name);
			Label score = new Label("Score: " + r.score);
			Label commentsFilePath = new Label("Comments File: " + r.commentsFilePath);
			
			VBox reviewItem = new VBox(reviewerName, score, commentsFilePath);
			container.getChildren().add(reviewItem);
		}
		
		if(this.paper.reviews.size() <= 0) {
			Label noReviews = new Label("No reviews have been created");
			container.getChildren().add(noReviews);
		}
		
		return container;
	}
	
	private HBox generateAcceptRejectButtons() {
		Button accept = new Button("Accept Paper");
		Button reject = new Button("Reject Paper");

		accept.setOnAction(event -> acceptPaper());
		reject.setOnAction(event -> rejectpaper());
		
		
		HBox container = new HBox(20);
		container.getChildren().addAll(accept, reject);
		return container;
	}
	
	private void acceptPaper() {
		this.paper.status = PaperStatus.ACCEPTED;
		PaperListPage plp = new PaperListPage(this.primaryStage, this.title, this.paper);
		Navigation.navigate(plp);
	}
	
	private void rejectpaper() {
		this.paper.status = PaperStatus.REJECTED;
		PaperListPage plp = new PaperListPage(this.primaryStage, this.title, this.paper);
		Navigation.navigate(plp);
	}
	
	private String getFriendlyStatus(PaperStatus ps) {
		if(ps == PaperStatus.SUBMITTED) {
			return "Submitted";
		}
		if(ps == PaperStatus.ACCEPTED) {
			return "Accepted";
		}
		else {
			return "Rejected";
		}
	}
}
