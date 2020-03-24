package view;

import java.util.ArrayList;

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
import javafx.stage.Stage;
import model.DataStore;
import model.Reviewer;

public class AdministratorPane extends BasePane {
	
	private model.DataStore db;
	private Stage primaryStage;
	private Pane mainPane;
	
	private TextField newReviewerName;
	private PasswordField newReviewerPassword;
	
	
	public AdministratorPane(Stage stage) {
		super(stage, "Administrator Page");

		this.db = DataStore.load();
		
		this.primaryStage = stage;
		
		this.mainPane = new VBox();
		
		displayReviewers();
		
		this.setCenter(mainPane);
		
	}
	
	private void displayReviewers() {
		// Re-load db because a new user could have been created since.
		this.db = DataStore.load();
		
		ReviewerListPane reviewerListpane = new ReviewerListPane(db.university.reviewers, "Reviewer List");
		
		ResearcherListPane researcherListPane = new ResearcherListPane(db.university.researchers, "Researcher List");
		
		EditorListPane editorListPane = new EditorListPane(db.university.editors, "Editor List");
		
		mainPane.getChildren().add(reviewerListpane);
		mainPane.getChildren().add(researcherListPane);
		mainPane.getChildren().add(editorListPane);
	}
	
	
}
