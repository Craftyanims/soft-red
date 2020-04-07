package view;

import global.Navigation;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.geometry.Insets;



import javafx.scene.text.Font;
import model.DataStore;
import model.Journal;

public class ReviewerPane extends BasePane {
    private BorderPane center;
    private StackPane pane;

    public ReviewerPane(Stage ps) {
        super(ps, "Reviewer Pane");
//        this.setCenter(pane);
        pane = new StackPane();
        initGUI();
        initJournalList();
        this.setCenter(pane);
        FrontPane frontPane = new FrontPane(ps, "Front Page");


    }

    private void initJournalList() {
        GridPane gp = new GridPane();
            Label title = new Label("Journals");
        title.setFont(new Font(30));
        int currentRow = 0;
        gp.add(title, 0, currentRow, 4, 1);
        currentRow++;

        DataStore db = DataStore.load();
        ArrayList<Journal> journals = db.university.journals;


        if (journals.size() <= 0) {
            Label message = new Label("There are no journals");
            gp.add(message, 0, currentRow, 3, 1);
            currentRow++;
        } else {

            for (Journal j : journals) {
                Label journalName = new Label(j.name);

                Button view = new Button("VIEW");
//                edit.setOnAction(event -> editJournal(j));

                //Button view = new Button("View");

                Button addComment = new Button("UPLOAD COMMENTS");
//                delete.setOnAction(event -> deleteJournal(j));

                gp.add(journalName, 0, currentRow);
                gp.add(view, 1, currentRow);
                //this.add(view, 2, currentRow);
                gp.add(addComment, 2, currentRow);
                currentRow++;
            }
        }
        pane.getChildren().add(gp);
    }

    public void initGUI() {
        BorderPane bp = new BorderPane();
        HBox bg = new HBox();
        bg.setPadding(new Insets(20));
        Image image = new Image("GUI_assets/icon_reviewer.png");
        ImageView iv = new ImageView(image);
        iv.setFitWidth(200);
        iv.setFitHeight(200);
        iv.setPreserveRatio(true);
        bg.setAlignment(Pos.BOTTOM_RIGHT);
        bg.getChildren().add(iv);
        bp.setBottom(bg);
        pane.getChildren().add(bp);
    }

    public StackPane getPane() {
        return pane;
    }

}