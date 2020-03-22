package view;

import javafx.stage.Stage;
import model.Journal;

public class JournalItemPage extends BasePane {

	private Journal journal;
	
	public JournalItemPage(Stage stage, String title, Journal journal) {
		super(stage, title);
		this.journal = journal;
		// TODO Auto-generated constructor stub
	}
	
	private void displayJournals() {
		// Re-load db because a new user could have been created since.
		this.db = DataStore.load();
		
		JournalListPane journalListpane = new JournalListPane(db.university.journals, "Journal List");
		
		mainPane.getChildren().add(journalListpane);
		

}
