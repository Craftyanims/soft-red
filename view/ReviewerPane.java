package view;
import global.Navigation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class ReviewerPane extends BasePane {
    private StackPane pane;
    public ReviewerPane(Stage ps){
        super(ps, "Reviewer Pane");
        System.out.println("Reviewer Pane");
        FrontPane frontPane = new FrontPane(ps, "Front Page");
        Navigation.navigate(frontPane);
    }

    public StackPane getPane(){
        return pane;
    }

}