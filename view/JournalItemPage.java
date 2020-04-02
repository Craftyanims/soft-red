package view;

import javafx.stage.Stage;
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
import model.Journal;
import model.DataStore;
public class JournalItemPage extends BasePane {

	private Journal journal;
	private model.DataStore db;
	private Stage primaryStage;
	private Pane mainPane;
	
	public JournalItemPage(Stage stage, String title, Journal journal) {
		super(stage, title);
		this.journal = journal;
		// TODO Auto-generated constructor stub
	}
	
	private void displayJournals() {
		// Re-load db because a new user could have been created since.
		this.db = DataStore.load();
		
		FrontPane frontPane = new FrontPane(primaryStage, "Front Page");
		
		mainPane.getChildren().add(frontPane);
		

	}
}
