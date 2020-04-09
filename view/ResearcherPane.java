package view;

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
import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import global.Auth;
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
    private File entry;
    private Label fileDir;
    private Label pickR;
    private Label pickJ;
    private model.DataStore db;

    private ChoiceBox <Reviewer> reviewer1;
    private ChoiceBox <Reviewer> reviewer2;
    private ChoiceBox <Reviewer> reviewer3;
    private ChoiceBox <Journal> journalsBox;
    

    public ResearcherPane(Stage ps) {
        super(ps, "Researcher Pane");
        center = new BorderPane();
        initGUI();

        this.db = DataStore.load();
        pane = new VBox();
        pane2 = new HBox();


        Label researcher_l = new Label("Researcher");
        researcher_l.setTranslateY(-300);

        createSubmission(ps);

        DataStore db = DataStore.load();
        ArrayList<Reviewer> reviewers = db.university.reviewers;
        ArrayList<Journal> journals = db.university.journals;


        reviewer1 = new ChoiceBox(FXCollections.observableArrayList(
                reviewers)
        );
        reviewer1.setTranslateY(160);
        reviewer1.setTranslateX(275);
        reviewer2 = new ChoiceBox(FXCollections.observableArrayList(
                reviewers)
        );
        reviewer2.setTranslateY(180);
        reviewer2.setTranslateX(275);
        reviewer3 = new ChoiceBox(FXCollections.observableArrayList(
                reviewers)
        );
        reviewer3.setTranslateY(200);
        reviewer3.setTranslateX(275);
        
        journalsBox = new ChoiceBox(FXCollections.observableArrayList(
               journals)
        );
        journalsBox.setTranslateY(40);
        journalsBox.setTranslateX(275);


        addChild(reviewer1);
        addChild(reviewer2);
        addChild(reviewer3);
        addChild(journalsBox);
        center.setCenter(pane);
        this.setCenter(center);


    }

    private void addChild(Node child) {
        pane.getChildren().addAll(child);

    }

    ;

    public void createSubmission(Stage ps) {
        Button findBtn = new Button("Open File");
        findBtn.setTranslateY(100);
        findBtn.setTranslateX(187);
        // TODO: set this to have logic based on the account given as input
        findBtn.setOnAction(e -> {
            entry = selectFile(ps);
        });
        Button submitBtn = new Button("Submit");
        submitBtn.setTranslateY(275);
        submitBtn.setTranslateX(195);


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
        pickR.setTranslateY(170);
        pickR.setTranslateX(-135);

 
        fileDir = new Label("Select a PDF File");
        fileDir.setTranslateY(105);
        fileDir.setTranslateX(72);
        
        pickJ = new Label("Select Journal");
        pickJ.setTranslateY(140);
        pickJ.setTranslateX(-228);
        
        addChild(fileDir);
        addChild(findBtn);
        addChild(submitBtn);
        pane2.getChildren().addAll(fileDir);
        pane2.getChildren().addAll(findBtn);
        pane2.getChildren().addAll(submitBtn);
        pane2.getChildren().addAll(pickR);
        pane2.getChildren().addAll(pickJ);
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
        //boolean b = dest.mkdirs();

        InputStream is = null;
        OutputStream os = null;
        
        Paper p = new Paper(source.getName());
        p.author = (Researcher) Auth.getCurrentUser(); 
        
        Reviewer r1 = reviewer1.getValue();
        p.nominated.add(r1);
        
        Reviewer r2 = reviewer2.getValue();
        p.nominated.add(r2);
        
        Reviewer r3 = reviewer3.getValue();
        p.nominated.add(r3);
        
        Journal j = journalsBox.getValue();
        j.papers.add(p);
        db.serialize();
        
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

    public Pane getPane() {
        return pane;

    }


}
