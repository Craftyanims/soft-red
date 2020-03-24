package view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import global.Navigation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavigationPane extends VBox {

	private HashMap<String, Class<? extends Pane>> links = new HashMap<String, Class<? extends Pane>> ();
	
	public NavigationPane(Stage primaryStage)  {
		links.put("Journal List", JournalListPane.class);
		links.put("Login", LoginPane.class);
		links.put("Researcher", ResearcherPane.class);
		links.put("Reviewer", ReviewerPane.class);
		links.put("Editor", EditorPane.class);
		links.put("Administrator", AdministratorPane.class);
		
		links.forEach((name, pane) -> {
			Button button = new Button(name);
			
			button.setOnAction(event -> {
                    try {
						Navigation.navigate(pane);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            );
			
			getChildren().addAll(button);
		});
		
		setStyle("-fx-background-color: cyan");
	}
	
}
