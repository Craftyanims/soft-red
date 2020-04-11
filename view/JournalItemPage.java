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
import model.Paper;
import model.Researcher;
import model.DataStore;
public class JournalItemPage extends BasePane {

	private Journal journal;
	private model.DataStore db;
	private Stage primaryStage;
	private VBox mainPane;
	private int currentRow = 0;
	private String title;
	
	public JournalItemPage(Stage stage, String title, Journal journal) {
		super(stage, title);
		this.journal = journal;
		
		this.mainPane = new VBox();
		buildJournalItemPage();
		
		

		Label title1 = new Label(this.title);
		title1.setFont(new Font(30));
		this.add(title1, 0, currentRow, 4, 1);
		currentRow++;
		
		
	}
	
	private void add(Label title2, int i, int currentRow2, int j, int k) {
		// TODO Auto-generated method stub
		
	}
	// this function creates the Journal Item Page which shows the name of the journal and the name of the editor assigned to it
	private void buildJournalItemPage() {
		
		String editorName = "No editor assigned";
		
		if(journal.editor != null && journal.editor.name != null) {
			editorName = journal.editor.name;
		}
		
		
		Label name = new Label("Name: " + journal.name);
		Label editor = new Label("Editor: " + editorName);
		
		
		VBox paperList = generatePaperList();
		
		
		mainPane.getChildren().addAll(name, editor, paperList);
		this.setCenter(mainPane);
	}
	
	//generates the list of papers in selected journal with paper name label
	//and author name label and generates a view paper button 
	private VBox generatePaperList() {
		VBox paperList = new VBox(20);
		
		for(Paper p : journal.papers) {
			VBox itemBox = new VBox(10);
			
			Label paperName = new Label("Paper Name: " + p.name);
			Label authorName = null;
			try {
			authorName = new Label("Author Name: " + p.author.name);
			} catch(Exception e) {}
			Button viewPaperButton = new Button("View Paper");
			viewPaperButton.setOnAction(event -> viewPaper(p));
			
			itemBox.getChildren().addAll(paperName, authorName, viewPaperButton);
			paperList.getChildren().add(itemBox);
		}
		
		return paperList;
	}
	//this function is called when the 'View Paper' button is clicked and navigates to Paper Item Page
	private void viewPaper(Paper p) {
		Pane viewItem = new PaperItemPage(Navigation.primaryStage, "Paper Item Page", p);
		
		Navigation.navigate(viewItem);
	}

}
