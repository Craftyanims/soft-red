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
import model.Researcher;
import model.Reviewer;

public class NewResearcherPane extends BasePane {

	private model.DataStore db;
	
	private TextField newResearcherName;
	private PasswordField newResearcherPassword;
	
	private GridPane container;
	
	public NewResearcherPane(Stage stage) {
		super(stage, "New Researcher Form");
		
		this.db = DataStore.load();
		
		//https://docs.oracle.com/javafx/2/get_started/form.htm
		container = new GridPane();
		container.setAlignment(Pos.CENTER);
		container.setHgap(10);
		container.setVgap(10);
		container.setPadding(new Insets(25, 25, 25, 25));

		
		Label name = new Label("Researcher Name:");
		container.add(name, 0, 1);
		
		newResearcherName = new TextField();
		container.add(newResearcherName, 1, 1);
		
		
		Label password = new Label("Password:");
		container.add(password, 0, 2);
		
		newResearcherPassword = new PasswordField();
		container.add(newResearcherPassword, 1, 2);
		
		
		Button submit = new Button("Submit");
		submit.setOnAction(event -> createNewResearcher());
		container.add(submit, 0, 3, 1, 2);
		
		
		this.setCenter(container);
	}
	
	private void createNewResearcher() {
		//TODO: Error handling for the form
		try {
			String name = newResearcherName.getText();
			String password = newResearcherPassword.getText();
			
			
			Researcher researcher = new Researcher(name, password);
		
			this.db.university.researchers.add(researcher);
			this.db.serialize();
			
			Navigation.navigate(AdministratorPane.class);
		}
		catch(Exception e) {
			// TODO: proper error handling
		}
	}
}
