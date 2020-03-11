package view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BasePane extends BorderPane {

	
	public BasePane(Stage stage, String title) {
		Label titleLabel = new Label(title);
		Pane titlePane = new Pane();
		titlePane.getChildren().addAll(titleLabel);
		titlePane.setStyle("-fx-background-color: orange");
		this.setTop(titlePane);
		
		
		
		this.setLeft(new NavigationPane(stage));
	}
}
