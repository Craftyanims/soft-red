package view;

import java.util.ArrayList;

import global.Navigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataStore;
import model.Researcher;
import model.Reviewer;


public class ResearcherListPane extends GridPane {

	private ArrayList<Researcher> researchers;
	private int currentRow = 0;
	
	
	private String title;
	
	public ResearcherListPane(ArrayList<Researcher> researchers, String title) {
		this.researchers = researchers;
		this.title = title;
		
		// Style this grid
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
		// Build the list
		buildResearcherList();
	}
	
	private void buildResearcherList() {
		Label title = new Label(this.title);
		title.setFont(new Font(30));
		this.add(title, 0, currentRow, 4, 1);
		currentRow++;
		
		
		if(researchers.size() <= 0) {
			Label message = new Label("There are no researchers");
			this.add(message, 0, currentRow, 3, 1);
			currentRow++;
		}
		else {
			
			for(Researcher r : researchers) {
				Label researcherName = new Label(r.name);
				
				Button edit = new Button("Edit");
				edit.setOnAction(event -> editResearcher(r));
				
				Button delete = new Button("Delete");
				delete.setOnAction(event -> deleteResearcher(r));
				
				this.add(researcherName, 0, currentRow);
				this.add(edit, 1, currentRow);
				this.add(delete, 2, currentRow);
				currentRow++;
			}
		}
		
		Button add = new Button("Add new researcher");
		add.setOnAction(event -> displayNewResearcher());
		this.add(add, 0, currentRow, 3, 1);
		currentRow++;
	}
	
	private void displayNewResearcher() {
		Navigation.navigate(NewResearcherPane.class);
	}
	
	private void deleteResearcher(Researcher r) {
		DataStore.removeResearcher(r);
		Navigation.navigate(AdministratorPane.class);
	}
	
	private void editResearcher(Researcher r) {
		
		// HACK: With proper design Navigation.primaryStage shouldn't be needed
		Pane editPane = new EditResearcherPane(Navigation.primaryStage, r);
		
		Navigation.navigate(editPane);
	}
}
