package view;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Paper;
import model.Researcher;

public class PaperItemPane extends BasePane {

	private Paper paper;
	
	private VBox mainPane;
	
	public PaperItemPane(Stage stage) {
		super(stage, "Paper Item Pane");
		
		//TEMPORARILY HARDCODING A PAPER OBJECT
		
		this.paper = new Paper("A fake paper");
		try {
			this.paper.author = new Researcher("a fake researcher", "password");			
		} catch(Exception e) {
			//TODO: Actual error handling
		}
		
		buildPaperItemPane();
	}
	
	private void buildPaperItemPane() {
		
		Label author = new Label("Author: " + paper.author.name);
		
		ReviewListPane rlp = new ReviewListPane(this.paper);
		
		Button acceptButton = new Button("Accept");
		Button rejectButton = new Button("Reject");
		HBox actionBox = new HBox(acceptButton, rejectButton);
		
		mainPane = new VBox(author, rlp, actionBox);
		this.setCenter(mainPane);
	}
}
