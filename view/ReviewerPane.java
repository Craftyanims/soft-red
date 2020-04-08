package view;

import global.Navigation;
import java.util.ArrayList;
import java.util.List;
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

import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javafx.scene.text.Font;
import model.DataStore;
import model.Journal;
import model.Paper;
import model.University;
import model.Reviewer;
import model.Review;
import global.Auth;

public class ReviewerPane extends BasePane {
    private BorderPane center;
    private StackPane pane;

    public ReviewerPane(Stage ps) {
        super(ps, "Reviewer Pane");
//        this.setCenter(pane);
        pane = new StackPane();
        initGUI();
        initJournalList(ps);
        this.setCenter(pane);
        FrontPane frontPane = new FrontPane(ps, "Front Page");


    }

    private void initJournalList(Stage ps) {
        GridPane gp = new GridPane();
            Label title = new Label("Journals");
        title.setFont(new Font(30));
        int currentRow = 0;
        gp.add(title, 0, currentRow, 4, 1);
        currentRow++;

        DataStore db = DataStore.load();
        List<Paper> papers = db.university.journals.get(0).papers;


        if (papers.size() <= 0) {
            Label message = new Label("There are no papers");
            gp.add(message, 0, currentRow, 3, 1);
            currentRow++;
        } else {

            for (Paper j : papers) {
                Label journalName = new Label(j.name);

                Button view = new Button("VIEW");
//                edit.setOnAction(event -> editJournal(j));

                //Button view = new Button("View");

                Button addComment = new Button("UPLOAD COMMENTS");

                addComment.setOnAction(e -> {
                    try {
                        File entry = selectFile(ps);
                        System.out.println("Saving. . .");
                        String path = saveFile(entry);
                        String name = Auth.getCurrentUser().name;
                        Reviewer r = db.university.findReviewer(name);
                        Review review = new Review(r,path);
                        j.addReview(review);
                        System.out.println("Complete!");

                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                });
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
    private String saveFile(File source) throws IOException {
        File folder = new File("Journal_Comments");
        folder.mkdirs();
        String sig = "_COMMENT_" + Auth.getCurrentUser().name;
        String path = "Journal_Comments\\"+source.getName()+sig+".pdf";
        File dest = new File(path);
        DataStore db = new DataStore();
        University u = db.load().university;
        u.journals.add(new Journal(source.getName()+sig));
        db.serialize();
        //boolean b = dest.mkdirs();

        InputStream is = null;
        OutputStream os = null;


        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
        return path;
    }

    private File selectFile(Stage ps) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.pdf")
        );

        File f = fc.showOpenDialog(ps);
        if (f != null) {
            return f;
        } else {
            System.out.println("file not selected");
            return null;
        }
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