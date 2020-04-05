package view;

import javafx.stage.Stage;
import java.util.ArrayList;

import global.Navigation;
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
import javafx.scene.text.Font;
import model.Journal;
import model.DataStore;
public class JournalItemPage extends BasePane {

	private Journal journal;
	private model.DataStore db;
	private Stage primaryStage;
	private Pane mainPane;
	private ArrayList<Journal> journals;
	private int currentRow = 0;
	private String title;
    
	
	public JournalItemPage(Stage stage, String title, Journal journal) {
		super(stage, title);
		this.journal = journal;
		
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
		// Build the list
		buildJournalList();
		// TODO Auto-generated constructor stub
	}
	
	private void setAlignment(Pos center) {
		// TODO Auto-generated method stub
		
	}

	private void setVgap(int i) {
		// TODO Auto-generated method stub
		
	}

	private void setHgap(int i) {
		// TODO Auto-generated method stub
		
	}

	private void displayJournals() {
		// Re-load db because a new user could have been created since.
		this.db = DataStore.load();
		
		FrontPane frontPane = new FrontPane(primaryStage, "Front Page");
		
		mainPane.getChildren().add(frontPane);
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

	

	private void add(Button add, int i, int currentRow2, int j, int k) {
			// TODO Auto-generated method stub
			
		}

	private void add(Label title2, int i, int currentRow2, int j, int k) {
			// TODO Auto-generated method stub
			
		}

	private void add(Label journalName, int i, int currentRow2) {
			// TODO Auto-generated method stub
			
		}

	private void add(Button edit, int i, int currentRow2) {
			// TODO Auto-generated method stub
			
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
		
		Pane viewItems = new JournalItemPage(Navigation.primaryStage, title, j);
		
		Navigation.navigate(viewItems);
	}

}
