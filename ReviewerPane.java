import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
public class ReviewerPane extends StackPane{
    private StackPane pane;
    public ReviewerPane(Stage ps){
        pane = new StackPane();
        Label reviewer_l = new Label("Reviewer");
        pane.getChildren().addAll(reviewer_l);
    }

    public StackPane getPane(){
        return pane;
    }

}