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
import model.Editor;
import model.Reviewer;

public class NewEditorPane extends BasePane {

	private model.DataStore db;
	
	private TextField newEditorName;
	private PasswordField newEditorPassword;
	
	private GridPane container;
	
	public NewEditorPane(Stage stage) {
		super(stage, "New Editor Form");
		
		this.db = DataStore.load();
		
		//https://docs.oracle.com/javafx/2/get_started/form.htm
		container = new GridPane();
		container.setAlignment(Pos.CENTER);
		container.setHgap(10);
		container.setVgap(10);
		container.setPadding(new Insets(25, 25, 25, 25));

		
		Label name = new Label("Editor Name:");
		container.add(name, 0, 1);
		
		newEditorName = new TextField();
		container.add(newEditorName, 1, 1);
		
		
		Label password = new Label("Password:");
		container.add(password, 0, 2);
		
		newEditorPassword = new PasswordField();
		container.add(newEditorPassword, 1, 2);
		
		
		Button submit = new Button("Submit");
		submit.setOnAction(event -> createNewEditor());
		container.add(submit, 0, 3, 1, 2);
		
		
		this.setCenter(container);
	}
	
	private void createNewEditor() {
		//TODO: Error handling for the form
		try {
			String name = newEditorName.getText();
			String password = newEditorPassword.getText();
			
			
			Editor editor = new Editor(name, password);
		
			this.db.university.editors.add(editor);
			this.db.serialize();
			
			Navigation.navigate(AdministratorPane.class);
		}
		catch(Exception e) {
			// TODO: proper error handling
		}
	}
}
