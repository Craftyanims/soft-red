package view;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import model.Journal;

public class JournalListPane extends BasePane {

	private ArrayList<model.Journal> createFakeJournals() {
		ArrayList<model.Journal> fake = new ArrayList<model.Journal>();
		fake.add(new model.Journal("a fake journal"));
		fake.add(new model.Journal("another fake journal"));
		fake.add(new model.Journal("a third fake journal"));
		fake.add(new model.Journal("a final fake journal"));
		
		return fake;
	}
	
	public JournalListPane(Stage stage) {
		super(stage, "Journal List Page");
		
		ArrayList<model.Journal> journals = createFakeJournals();
		
		VBox contentPane = new VBox();
		
		for (int i = 0; i < journals.size(); i++) { 
			Pane journalBox = generateJournalListItem(journals.get(i));
			journalBox.setStyle("-fx-background-color: pink; -fx-padding: 10px;");
			contentPane.getChildren().addAll(journalBox);
		}
		this.setCenter(contentPane);
	}
	
	public Pane generateJournalListItem(model.Journal journal) {
		HBox container = new HBox();
		
		Label titleLabel = new Label(journal.name);
		Button viewButton = new Button("View");
		
		container.getChildren().addAll(titleLabel, viewButton);
		
		return container;
	}
}
