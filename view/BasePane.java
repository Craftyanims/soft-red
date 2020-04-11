package view;

import global.Auth;
import global.Navigation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.DataStore;

public class BasePane extends BorderPane {

	
	public BasePane(Stage stage, String title) {
		BorderPane headerPane = new BorderPane();
		
		Label titleLabel = new Label(title);

		headerPane.setLeft(titleLabel);
		
		
		if(Auth.getCurrentUser() != null) {
			
			String currentUsername = Auth.getCurrentUser().name; 
			Label currentUser = new Label("Currently logged in as: " + currentUsername);
			Button logout = new Button("Log out");
			HBox box = new HBox();
			box.getChildren().addAll(currentUser, logout);
			
			headerPane.setRight(box);
			
			logout.setOnAction(event -> logout());
		}
		else {
			
		}


        titleLabel.setStyle("-fx-text-fill: white");
        headerPane.setStyle("-fx-background-color: #737373");

		this.setTop(headerPane);

		
		
		
		this.setLeft(new NavigationPane(stage));
	}
	
	private void logout() {
		Auth.logout();
		
		Navigation.navigate(LoginPane.class);
	}
}
