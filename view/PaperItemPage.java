package view;

import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

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
public class PaperItemPage extends BasePane {

	private Paper paper;
	private model.DataStore db;
	private Stage primaryStage;
	private VBox mainPane;
	private int currentRow = 0;
	private String title;
//	private ArrayList<Reviewer> reviewers;


	public PaperItemPage(Stage stage, String title, Paper paper) {
		super(stage, title);
		this.title = title;
		this.paper = paper;

		this.mainPane = new VBox();
		buildPaperListPage();

	//	addFakeReviewers();



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


			Label title1 = new Label(this.title);
			title1.setFont(new Font(30));
			this.add(title1, 0, currentRow, 4, 1);
			currentRow++;

		// Display the paper's status
		Label status = new Label("Status: " + getFriendlyStatus(this.paper.status));
		this.mainPane.getChildren().add(status);

		//Display the list of reviews for the paper
	//	generateFakeReviews();
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

			paper.reviewers.add(re1);
			paper.reviewers.add(re2);
			paper.reviewers.add(re3);

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

	private void nominatedReviewers() {

	}
	private VBox generateReviewerList() {
		VBox reviewerList = new VBox(20);
		Boolean isNominatedList = false;  
		List<Reviewer> list = paper.reviewers;
			if(list.size() == 0) {
				System.out.println("list is null" + paper.nominated.size());
				list = paper.nominated;
				isNominatedList = true;
			}
		System.out.println(paper.reviewers.size());
		for(Reviewer r : list) {
			VBox itemBox = new VBox(10);
			System.out.println(r.name);
			Label reviewerName1 = new Label("Reviewer: " + r.name);
			if(isNominatedList == true) {
				reviewerName1.setText("Reviewer: " + r.name + "(Researcher Request)");
			}
	//		Label reviewerName2 = new Label("Reviewer 2: " + r.reviewer.name);
	//		Label reviewerName3 = new Label("Reviewer 3: " + r.reviewer.name);
	//		Button editReviewerButton = new Button("Edit Reviewers");

			itemBox.getChildren().addAll(reviewerName1);
			reviewerList.getChildren().add(itemBox);
		}

		return reviewerList;
	}

	private void editReviewers() {
		EditorPane.paper = paper;
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
		PaperItemPage plp = new PaperItemPage(this.primaryStage, this.title, this.paper);
		Navigation.navigate(plp);
	}

	private void rejectpaper() {
		this.paper.status = PaperStatus.REJECTED;
		PaperItemPage plp = new PaperItemPage(this.primaryStage, this.title, this.paper);
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
