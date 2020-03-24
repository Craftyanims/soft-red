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

}
