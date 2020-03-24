package view;

import global.Navigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.DataStore;
import model.Reviewer;

public class NewReviewerPane extends BasePane {

	private model.DataStore db;
	
	private TextField newReviewerName;
	private PasswordField newReviewerPassword;
	
	private GridPane container;
	
	public NewReviewerPane(Stage stage) {
		super(stage, "New Reviewer Form");
		
		this.db = DataStore.load();
		
		//https://docs.oracle.com/javafx/2/get_started/form.htm
		container = new GridPane();
		container.setAlignment(Pos.CENTER);
		container.setHgap(10);
		container.setVgap(10);
		container.setPadding(new Insets(25, 25, 25, 25));

		
		Label name = new Label("Reviewer Name:");
		container.add(name, 0, 1);
		
		newReviewerName = new TextField();
		container.add(newReviewerName, 1, 1);
		
		
		Label password = new Label("Password:");
		container.add(password, 0, 2);
		
		newReviewerPassword = new PasswordField();
		container.add(newReviewerPassword, 1, 2);
		
		
		Button submit = new Button("Submit");
		submit.setOnAction(event -> createNewReviewer());
		container.add(submit, 0, 3, 1, 2);
		
		
		this.setCenter(container);
	}
	
	private void createNewReviewer() {
		//TODO: Error handling for the form
		try {
			String name = newReviewerName.getText();
			String password = newReviewerPassword.getText();
			
			
			Reviewer reviewer = new Reviewer(name, password);
		
			this.db.university.reviewers.add(reviewer);
			this.db.serialize();
			
			Navigation.navigate(AdministratorPane.class);
		}
		catch(Exception e) {
			// TODO: proper error handling
		}
	}
}
