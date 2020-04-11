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
import model.Editor;
import model.Researcher;
import model.Reviewer;


public class EditorListPane extends GridPane {

	private ArrayList<Editor> editors;
	private int currentRow = 0;
	
	
	private String title;
	
	public EditorListPane(ArrayList<Editor> editors, String title) {
		this.editors = editors;
		this.title = title;
		
		// Style this grid
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
		// Build the list
		buildEditorList();
	}
	
	private void buildEditorList() {
		Label title = new Label(this.title);
		title.setFont(new Font(30));
		this.add(title, 0, currentRow, 4, 1);
		currentRow++;
		
		
		if(editors.size() <= 0) {
			Label message = new Label("There are no editors");
			this.add(message, 0, currentRow, 3, 1);
			currentRow++;
		}
		else {
			
			for(Editor e : editors) {
				Label editorName = new Label(e.name);
				
				Button edit = new Button("Edit");
				edit.setOnAction(event -> editEditor(e));
				
				Button delete = new Button("Delete");
				delete.setOnAction(event -> deleteEditor(e));
				
				this.add(editorName, 0, currentRow);
				this.add(edit, 1, currentRow);
				this.add(delete, 2, currentRow);
				currentRow++;
			}
		}
		
		Button add = new Button("Add new editor");
		add.setOnAction(event -> displayNewEditor());
		this.add(add, 0, currentRow, 3, 1);
		currentRow++;
	}
	
	private void displayNewEditor() {
		Navigation.navigate(NewEditorPane.class);
	}
	
	private void deleteEditor(Editor e) {
		DataStore.removeEditor(e);
		Navigation.navigate(AdministratorPane.class);
	}
	
	private void editEditor(Editor e) {
		
		// HACK: With proper design Navigation.primaryStage shouldn't be needed
		Pane editPane = new EditEditorPane(Navigation.primaryStage, e);
		
		Navigation.navigate(editPane);
	}
}
