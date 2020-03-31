package view;

import global.Auth;
import global.Navigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Editor;
import model.User;

public class LoginPane extends BasePane {

	private GridPane container;
	
	private TextField userNameField;
	private PasswordField passwordField;
	
	public LoginPane(Stage primaryStage) {
		super(primaryStage, "Login Page");


//        TextField login_tf = new TextField();
//
//        BorderPane container = new BorderPane();
//        
//        Button login_b = new Button("Press Me");
//        // TODO: set this to have logic based on the account given as input
//        login_b.setOnAction(e -> {
//            //primaryStage.setScene(researcher_scene);
//        });
//
//        container.setTop(login_b);
//        container.setCenter(login_tf);
		
		generateLoginForm();
        this.setCenter(container);
        
	}
	
	private void generateLoginForm() {
		
		//https://docs.oracle.com/javafx/2/get_started/form.htm
		container = new GridPane();
		container.setAlignment(Pos.CENTER);
		container.setHgap(10);
		container.setVgap(10);
		container.setPadding(new Insets(25, 25, 25, 25));
		
		Label userName = new Label("User name:");
		container.add(userName, 0, 1);
		
		userNameField = new TextField();
		container.add(userNameField, 1, 1);
		
		Label password = new Label("Password:");
		container.add(password, 0, 2);
		
		passwordField = new PasswordField();
		container.add(passwordField, 1, 2);
		
		//selectedEditor = new ComboBox<Editor>();
		//selectedEditor.getItems().addAll(this.db.university.editors);
		//container.add(selectedEditor, 1, 2);
	
		Button login = new Button("Login");
		login.setOnAction(event -> login());
		container.add(login, 0, 3, 1, 2);
		
		
	}
	
	private void login() {
		String userName = userNameField.getText();
		String password = passwordField.getText();
		
		boolean result = Auth.login(userName, password);
		
		if(result) {
			Navigation.navigate(FrontPane.class);	
		}
		else {
			Navigation.navigate(LoginPane.class);
		}
	}
}
