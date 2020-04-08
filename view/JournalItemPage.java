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
		
//		this.setAlignment(Pos.CENTER);
//		this.setHgap(10);
//		this.setVgap(10);
//		this.setPadding(new Insets(25, 25, 25, 25));
		
		
		addFakePapers();
		
		
		
		/*-----------------------------------------------
		|
		|	Name: <JOURNAL NAME>
		|	Editor: <EDITOR NAME>
		|	
		|	1. Paper Name
		|		Author: NAME
		|		Button: View paper
		|
		|	2. Paper Name 2
		|		Author: Name
		|		Button: View paper
		|   etc...
		|
		|
		-------------------------------------------------*/
		
		buildJournalItemPage();
			Label title1 = new Label(this.title);
			title1.setFont(new Font(30));
			this.add(title1, 0, currentRow, 4, 1);
			currentRow++;
		
		
	}
	
	private void add(Label title2, int i, int currentRow2, int j, int k) {
		// TODO Auto-generated method stub
		
	}

	private void addFakePapers() {
		
		try {
			Paper paper1 = new Paper("Paper Title");
			paper1.author = new Researcher("res name", "password");
			Paper paper2 = new Paper("Paper Title");
			paper2.author = new Researcher("res2 name", "password");
			Paper paper3 = new Paper("Paper Title");
			paper3.author = new Researcher("res3 name", "password");
			
			
			journal.papers.add(paper1);
			journal.papers.add(paper2);
			journal.papers.add(paper3);
		}
		catch(Exception e) {
			//TODO: Handle errors properly
		}

	}
	
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
	
	private VBox generatePaperList() {
		VBox paperList = new VBox(20);
		
		for(Paper p : journal.papers) {
			VBox itemBox = new VBox(10);
			
			Label paperName = new Label("Paper Name: " + p.name);
			Label authorName = new Label("Author Name: " + p.author.name);
			Button viewPaperButton = new Button("View Paper");
			viewPaperButton.setOnAction(event -> viewPaper(p));
			
			itemBox.getChildren().addAll(paperName, authorName, viewPaperButton);
			paperList.getChildren().add(itemBox);
		}
		
		return paperList;
	}


	


	
	
	private void viewPaper(Paper p) {
		Pane viewItem = new PaperListPage(Navigation.primaryStage, "Paper List Page", p);
		
		Navigation.navigate(viewItem);
	}
//	
//	private void editJournal(Journal j) {
//		
//		// HACK: With proper design Navigation.primaryStage shouldn't be needed
//		Pane editPane = new EditJournalPane(Navigation.primaryStage, j);
//		
//		Navigation.navigate(editPane);
//	}
	

}
