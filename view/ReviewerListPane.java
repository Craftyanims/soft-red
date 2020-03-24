package view;

import java.util.ArrayList;

import global.Navigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataStore;
import model.Reviewer;


public class ReviewerListPane extends GridPane {

	private ArrayList<Reviewer> reviewers;
	private int currentRow = 0;
	
	
	private String title;
	
	public ReviewerListPane(ArrayList<Reviewer> reviewers, String title) {
		this.reviewers = reviewers;
		this.title = title;
		
		
		// For debugging only
		//this.setGridLinesVisible(true);
		
		// Style this grid
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
		// Build the list
		buildReviewerList();
	}
	
	private void buildReviewerList() {
		Label title = new Label(this.title);
		title.setFont(new Font(30));
		this.add(title, 0, currentRow, 4, 1);
		currentRow++;
		
		
		if(reviewers.size() <= 0) {
			Label message = new Label("There are no reviewers");
			this.add(message, 0, currentRow, 3, 1);
			currentRow++;
		}
		else {
			
			for(Reviewer r : reviewers) {
				Label reviewerName = new Label(r.name);
				
				Button edit = new Button("Edit");
				edit.setOnAction(event -> editReviewer(r));
				
				//Button view = new Button("View");
				
				Button delete = new Button("Delete");
				delete.setOnAction(event -> deleteReviewer(r));
				
				this.add(reviewerName, 0, currentRow);
				this.add(edit, 1, currentRow);
				//this.add(view, 2, currentRow);
				this.add(delete, 2, currentRow);
				currentRow++;
			}
		}
		
		Button add = new Button("Add new reviewer");
		add.setOnAction(event -> displayNewReviewer());
		this.add(add, 0, currentRow, 3, 1);
		currentRow++;
	}
	
	private void displayNewReviewer() {
		Navigation.navigate(NewReviewerPane.class);
	}
	
	private void deleteReviewer(Reviewer r) {
		DataStore.removeReviewer(r);
		Navigation.navigate(AdministratorPane.class);
	}
	
	private void editReviewer(Reviewer r) {
		
		// HACK: With proper design Navigation.primaryStage shouldn't be needed
		Pane editPane = new EditReviewerPane(Navigation.primaryStage, r);
		
		Navigation.navigate(editPane);
	}
}
