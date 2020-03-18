package view;
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
    	JournalListPane jp = new JournalListPane(ps);
        pane = new StackPane();
        Pane journalList = jp.createPane(null);
        Label reviewer_l = new Label("Reviewer");
        pane.getChildren().addAll(reviewer_l);
        pane.getChildren().addAll(journalList);

        this.setCenter(pane);
    }

    public StackPane getPane(){
        return pane;
    }

}