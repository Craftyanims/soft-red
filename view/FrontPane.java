package view;

import javafx.stage.Stage;
import model.DataStore;

public class FrontPane extends BasePane {

	public FrontPane(Stage stage) {
		this(stage, "Front Page");
	}
	
	public FrontPane(Stage stage, String title) {
		super(stage, title);
		
		DataStore db = DataStore.load();
		
		JournalListPane jlp = new JournalListPane(db.university.journals, "Journal List");
		
		this.setCenter(jlp);
	}
	
}
