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
import model.Reviewer;
import model.Researcher;
import model.DataStore;
public class PaperListPage extends BasePane {



	private Paper paper;
	private model.DataStore db;
	private Stage primaryStage;
	private VBox mainPane;
	private int currentRow = 0;
	private String title;
    
	
	public PaperListPage(Stage stage, String title, Paper paper) {
		super(stage, title);
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
		Label researcher = new Label("Researcher: " + paper.researcher.name);
		
		
		VBox reviewerList = generateReviewerList();
		
		
		mainPane.getChildren().addAll(name, researcher, reviewerList);
		this.setCenter(mainPane);
	}
	
	private VBox generateReviewerList() {
		VBox reviewerList = new VBox(20);
		
		for(Reviewer r : paper.reviewers) {
			VBox itemBox = new VBox(10);
			
			Label reviewerName1 = new Label("Reviewer 1: " + r.re1.name);
			Label reviewerName2 = new Label("Reviewer 2: " + r.re2.name);
			Label reviewerName3 = new Label("Reviewer 3: " + r.re3.name);
			Button editReviewerButton = new Button("Edit Reviewers");
			
			itemBox.getChildren().addAll(reviewerName1, reviewerName2, reviewerName3, editReviewerButton);
			paperList.getChildren().add(itemBox);
		}
		
		return reviewerList;
	}


	


	
	
//	private void deleteJournal(Journal j) {
//		DataStore.removeJournal(j);
//		Navigation.navigate(FrontPane.class);
//	}
//	
//	private void editJournal(Journal j) {
//		
//		// HACK: With proper design Navigation.primaryStage shouldn't be needed
//		Pane editPane = new EditJournalPane(Navigation.primaryStage, j);
//		
//		Navigation.navigate(editPane);
//	}
	

}