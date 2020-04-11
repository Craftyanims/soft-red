package view;

import java.util.ArrayList;

import global.Navigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataStore;
import model.Journal;


public class JournalListPane extends GridPane {
    
	private ArrayList<Journal> journals;
	private int currentRow = 0;
	private String title;
    
    
	public JournalListPane(ArrayList<Journal> journals, String title) {
		this.journals = journals;
		this.title = title;
		
		// Style this grid
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
		// Build the list
		buildJournalList();
	}
    

	public JournalListPane(Stage ps) {
		// TODO Auto-generated constructor stub
	}


	private void buildJournalList() {
		Label title = new Label(this.title);
		title.setFont(new Font(30));
		this.add(title, 0, currentRow, 4, 1);
		currentRow++;
		
		
		if(journals.size() <= 0) {
			Label message = new Label("There are no journals");
			this.add(message, 0, currentRow, 3, 1);
			currentRow++;
		}
		else {
			
			for(Journal j : journals) {
				Label journalName = new Label(j.name);
				
				Button edit = new Button("Edit");
				edit.setOnAction(event -> editJournal(j));
				
				Button view = new Button("View");
				view.setOnAction(event -> viewJournal(j));
				
				Button delete = new Button("Delete");
				delete.setOnAction(event -> deleteJournal(j));
				
				this.add(journalName, 0, currentRow);
				this.add(edit, 1, currentRow);
				this.add(view, 2, currentRow);
				this.add(delete, 3, currentRow);
				currentRow++;
			}
		}
		
		Button add = new Button("Add new journal");
		add.setOnAction(event -> displayNewJournal());
		this.add(add, 0, currentRow, 3, 1);
		currentRow++;
	}


	private void displayNewJournal() {
		Navigation.navigate(NewJournalPane.class);
    	}
	
	private void deleteJournal(Journal j) {
		DataStore.removeJournal(j);
		Navigation.navigate(FrontPane.class);
	}
	
	private void editJournal(Journal j) {
		
		// HACK: With proper design Navigation.primaryStage shouldn't be needed
		Pane editPane = new EditJournalPane(Navigation.primaryStage, j);
		
		Navigation.navigate(editPane);
	}
	
	private void viewJournal(Journal j) {
		

		Pane viewItems = new JournalItemPage(Navigation.primaryStage,"Journal Item Page", j);

		Navigation.navigate(viewItems);
	}

    
	public Pane generateJournalListItem(model.Journal journal) {

        HBox container = new HBox();

        Label titleLabel = new Label(journal.name);
        Button viewButton = new Button("View");
        viewButton.setOnAction(e -> {
        	Pane journalPage = new JournalItemPage(Navigation.primaryStage, "Journal Item Page", journal);
    		
    		Navigation.navigate(journalPage);
        	
        });
        
        container.getChildren().addAll(titleLabel, viewButton);

        return container;
    	}

	public Pane createPane(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
