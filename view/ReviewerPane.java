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
public class ReviewerPane extends BasePane {
    private StackPane pane;
    public ReviewerPane(Stage ps){
    	super(ps, "Reviewer Pane");
    	
        pane = new StackPane();
        Label reviewer_l = new Label("Reviewer");
        pane.getChildren().addAll(reviewer_l);
        
        this.setCenter(pane);
    }

    public StackPane getPane(){
        return pane;
    }

}