package view;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
public class EditorPane extends BasePane {

    private StackPane pane;
    public EditorPane(Stage ps){
    	super(ps, "Editor Pane");
    	
        pane = new StackPane();
        Label editor_l = new Label("Editor");

        pane.getChildren().addAll(editor_l);
        
        this.setCenter(pane);
    }


    public StackPane getPane(){
        return pane;
    }

}