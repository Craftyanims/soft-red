package view;

import global.Navigation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
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
    private VBox pane2;

    public ReviewerPane(Stage ps) {
        super(ps, "Reviewer Pane");
//        this.setCenter(pane);
        pane = new StackPane();
        pane2 = new VBox();
        initGUI();
        initJournalList(ps);
        initDeadlineList(ps);
        pane.getChildren().add(pane2);
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


        for (Journal jo : db.university.journals) {
            for (Paper j : jo.papers) {
                Label journalName = new Label(j.name);

                Button view = new Button("VIEW");
//                edit.setOnAction(event -> editJournal(j));

                //Button view = new Button("View");

                Button addComment = new Button("UPLOAD COMMENTS");
                Reviewer r = db.university.findReviewer(name);

                if (j.reviewers.contains(r)) {
                    addComment.setOnAction(e -> {
                        try {
                            Reviewer r = db.university.findReviewer(name);
                            File entry = selectFile(ps);
                            System.out.println("Saving. . .");
                            String path = saveFile(entry);
                            String name = Auth.getCurrentUser().name;
                            Review review = new Review(r, j, path);
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

        }
        pane2.getChildren().

                add(gp);

    }

    private String saveFile(File source) throws IOException {
        File folder = new File("Journal_Comments");
        folder.mkdirs();
        String sig = "_COMMENT_" + Auth.getCurrentUser().name;
        String path = "Journal_Comments\\" + source.getName() + sig + ".pdf";
        File dest = new File(path);
        DataStore db = new DataStore();
        University u = db.load().university;
        u.journals.add(new Journal(source.getName() + sig));
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

    private void initDeadlineList(Stage ps) {
        DataStore db = DataStore.load();
        List<Journal> journals = db.university.journals;
        List<String> deadlines;

        GridPane gp = new GridPane();

        int rowCounter = 0;

        Label title = new Label("Upcoming Deadlines");
        title.setFont(new Font(30));
        gp.add(title, 0, rowCounter, 4, 1);
        rowCounter++;

        Label emptyJournalsL = new Label("There are no upcoming deadlines");
        emptyJournalsL.setVisible(false);

        for (int i = 0; i < journals.size(); i++) {
            if (journals.get(i).deadlines.size() > 0 && findNextDeadline(journals.get(i).deadlines) >= 0) {
                deadlines = journals.get(i).deadlines;
                Label newDeadlineL = new Label(journals.get(i).name +
                        ": next deadline is " + deadlines.get(findNextDeadline(deadlines)));
                gp.add(newDeadlineL, 0, rowCounter);
                rowCounter++;
            }
        }

        if (rowCounter == 1) {
            emptyJournalsL.setVisible(true);
            gp.add(emptyJournalsL, 0, rowCounter, 3, 1);
        }

        pane2.getChildren().add(gp);
    }

    private int findNextDeadline(List<String> deadlines) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String currentDate = sdf.format(date);

        if (deadlines.size() == 0) return -1;

        int deadlineNumber = 0;

        for (String deadline : deadlines) {
            int currentYear = Integer.parseInt(currentDate.substring(0, 4));
            int currentMonth = Integer.parseInt(currentDate.substring(5, 7));
            int currentDay = Integer.parseInt(currentDate.substring(8, 10));

            int deadlineYear = Integer.parseInt(deadline.substring(0, 4));
            int deadlineMonth = Integer.parseInt(deadline.substring(5, 7));
            int deadlineDay = Integer.parseInt(deadline.substring(8, 10));

            if (deadlineYear > currentYear ||
                    deadlineYear == currentYear && deadlineMonth > currentMonth ||
                    deadlineYear == currentYear && deadlineMonth == currentMonth && deadlineDay > currentDay) {
                break;
            }
            deadlineNumber++;
        }

        if (deadlineNumber >= deadlines.size()) return -1;
        return deadlineNumber;
    }

    public StackPane getPane() {
        return pane;
    }

}