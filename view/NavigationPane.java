package view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavigationPane extends VBox {

	private HashMap<String, Class<? extends Pane>> links = new HashMap<String, Class<? extends Pane>> ();
	
	
	public NavigationPane(Stage primaryStage) {
		links.put("Journal List", JournalListPane.class);
		links.put("Login", LoginPane.class);
		links.put("Researcher", ResearcherPane.class);
		links.put("Reviewer", ReviewerPane.class);
		links.put("Editor", EditorPane.class);
		links.put("Administrator", AdministratorPane.class);
		
		links.forEach((name, pane) -> {
			Button button = new Button(name);
			
			button.setOnAction(e -> {
				try {
					Constructor constructor = pane.getConstructor(Stage.class);
					Pane newPane = (Pane) constructor.newInstance(primaryStage);
					Scene newScene = generateSceneForPane(newPane);
					primaryStage.setScene(newScene);
			        //primaryStage.show();	
				}
				catch(NoSuchMethodException exception) {
					// Swallowing exceptions is bad but I think it is impossible for a
					// subclass of Pane not to have a constructor that takes a Stage object.
					// Which would make this safe to do...
				}
				catch(InvocationTargetException exception) {
					// Swallowing exceptions is bad but I think it is impossible for a
					// subclass of Pane not to have a constructor that takes a Stage object.
					// Which would make this safe to do...
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
	        });
			
			getChildren().addAll(button);
		});
		
		
		setStyle("-fx-background-color: cyan");
	}
	
	private Scene generateSceneForPane(Pane pane) {
		Scene scene = new Scene(pane, 600, 600);
        return scene;
	}
	
}
