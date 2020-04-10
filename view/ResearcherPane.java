package view;

import javafx.scene.text.Font;
import model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DataStore;
import model.Reviewer;
import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

public class ResearcherPane extends BasePane {

    private BorderPane center;


    private Pane pane;
    private Pane pane2;
    private Pane pane3;
    private File entry;
    private Label fileDir;
    private Label pickR;

    private model.DataStore db;

    private ComboBox<Reviewer> selectedReviewer;
    private GridPane container;

    public ResearcherPane(Stage ps) {
        super(ps, "Researcher Pane");
        container = new GridPane();
        center = new BorderPane();
        initGUI();

        this.db = DataStore.load();
        pane = new VBox();
        pane2 = new HBox();
        pane3 = new VBox();


        Label researcher_l = new Label("Researcher");
        researcher_l.setTranslateY(-300);

        createSubmission(ps);
        initDeadlineList(ps);

        DataStore db = DataStore.load();
        ArrayList<Reviewer> reviewers = db.university.reviewers;


        ChoiceBox cb1 = new ChoiceBox(FXCollections.observableArrayList(
                reviewers)
        );
        cb1.setTranslateY(120);
        cb1.setTranslateX(275);
        ChoiceBox cb2 = new ChoiceBox(FXCollections.observableArrayList(
                reviewers)
        );
        cb2.setTranslateY(140);
        cb2.setTranslateX(275);
        ChoiceBox cb3 = new ChoiceBox(FXCollections.observableArrayList(
                reviewers)
        );
        cb3.setTranslateY(160);
        cb3.setTranslateX(275);


        addChild(cb1);
        addChild(cb2);
        addChild(cb3);
        center.setCenter(pane);
        this.setCenter(center);


    }

    private void addChild(Node child) {
        pane.getChildren().addAll(child);

    }


    public void createSubmission(Stage ps) {
        Button findBtn = new Button("Open File");
        findBtn.setTranslateY(100);
        findBtn.setTranslateX(200);
        // TODO: set this to have logic based on the account given as input
        findBtn.setOnAction(e -> {
            entry = selectFile(ps);
        });
        Button submitBtn = new Button("Submit");
        submitBtn.setTranslateY(100);
        submitBtn.setTranslateX(250);


        submitBtn.setOnAction(e -> {
            System.out.println("Saving. . .");
            try {
                saveFile(entry);
                System.out.println("Complete!");

            } catch (IOException error) {
                error.printStackTrace();
            }
        });
        pickR = new Label("Select a Reviewer");
        pickR.setTranslateY(150);
        pickR.setTranslateX(-126);

        Button assignBtn = new Button("Request");
        assignBtn.setTranslateY(236);
        assignBtn.setTranslateX(195);

        fileDir = new Label("Select a PDF File");
        fileDir.setTranslateY(105);
        fileDir.setTranslateX(132);
        addChild(fileDir);
        addChild(findBtn);
        addChild(submitBtn);
        pane2.getChildren().addAll(fileDir);
        pane2.getChildren().addAll(findBtn);
        pane2.getChildren().addAll(submitBtn);
        pane2.getChildren().addAll(assignBtn);
        pane2.getChildren().addAll(pickR);
        addChild(pane2);
    }

    public void initGUI() {
        BorderPane bp = new BorderPane();
        HBox bg = new HBox();
        Image image = new Image("GUI_assets/icon_researcher.png");
        ImageView iv = new ImageView(image);
        iv.setFitWidth(200);
        iv.setFitHeight(200);
        iv.setPreserveRatio(true);
        bg.setAlignment(Pos.BOTTOM_RIGHT);
        bg.getChildren().add(iv);
        bp.setRight(iv);
        center.setBottom(bp);
    }


    private void saveFile(File source) throws IOException {
        File folder = new File("All Journals");
        folder.mkdirs();

        File dest = new File("All Journals\\NAME_" + source.getName());
        DataStore db = new DataStore();
        University u = db.load().university;
        u.journals.add(new Journal(source.getName()));
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
    }

    private File selectFile(Stage ps) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.pdf")
        );

        File f = fc.showOpenDialog(ps);
        if (f != null) {
            fileDir.setText(f.getName());

            return f;
        } else {
            System.out.println("file not selected");
            return null;
        }
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
                        ": Next deadline is " + deadlines.get(findNextDeadline(deadlines)));
                gp.add(newDeadlineL, 0, rowCounter);
                rowCounter++;
            }
        }

        if (rowCounter == 1) {
            emptyJournalsL.setVisible(true);
            gp.add(emptyJournalsL, 0, rowCounter, 3, 1);
        }

        pane3.getChildren().add(gp);
        addChild(pane3);
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


    public Pane getPane() {
        return pane;

    }


}
