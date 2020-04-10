package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import global.Auth;
import global.Navigation;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.geometry.Insets;


public class EditorPane extends BasePane {
    private BorderPane center;
    public static Paper paper;
    private Pane pane;
    private Pane pane2;
    private Pane pane3;
    private File entry;
    private Label fileDir;
    private Label pickR;
    
    private ChoiceBox <Reviewer> reviewer1;
    private ChoiceBox <Reviewer> reviewer2;
    private ChoiceBox <Reviewer> reviewer3;
    private ChoiceBox <Paper> paperBox;
    private ChoiceBox <Journal> journalBox;
    
    public EditorPane(Stage ps) {
        super(ps, "Editor Pane");

        center = new BorderPane();
        pane = new VBox();
        pane2 = new HBox();
        pane3 = new Pane();

        center.setPadding(new Insets(20));
        initGUI();
      
        Label researcher_l = new Label("Editor");
        researcher_l.setTranslateY(-300);

        createSubmission(ps);
        setDeadline(ps);
        
        DataStore db = DataStore.load();  
      
        ArrayList<Reviewer> reviewers = db.university.reviewers;
        ArrayList <Journal> journals = db.university.journals;
        
        journalBox = new ChoiceBox(FXCollections.observableArrayList(journals));
        journalBox.setTranslateY(-45);
        journalBox.setTranslateX(195);
        
        Button getPaperBtn = new Button("Get Papers");
        getPaperBtn.setTranslateY(-125);
        getPaperBtn.setTranslateX(275);
        
        getPaperBtn.setOnAction(e -> {
            Journal j = journalBox.getValue();
            paperBox.setItems(FXCollections.observableArrayList(j.papers));
            paperBox.setVisible(true);
            paperBox.setTranslateY(-25);
            paperBox.setTranslateX(195);
        });
            
        paperBox = new ChoiceBox();
        paperBox.setVisible(false);
        paperBox.setTranslateY(-35);
        paperBox.setTranslateX(195);
        
        reviewer1 = new ChoiceBox(FXCollections.observableArrayList(reviewers));
        reviewer1.setTranslateY(-15);
        reviewer1.setTranslateX(195);
        
        reviewer2 = new ChoiceBox(FXCollections.observableArrayList(reviewers));
        reviewer2.setTranslateY(5);
        reviewer2.setTranslateX(195);
        reviewer3 = new ChoiceBox(FXCollections.observableArrayList(reviewers));
        reviewer3.setTranslateY(25);
        reviewer3.setTranslateX(195);
       
        Button submitBtn = new Button("Submit");
        submitBtn.setTranslateY(-20);
        submitBtn.setTranslateX(275);

        submitBtn.setOnAction(e -> {
        	setReviewers();
    //        submitToPaperPage();
        
        });
        
        addChild(journalBox);
        addChild(paperBox);
        addChild(reviewer1);
        addChild(reviewer2);
        addChild(reviewer3);
        addChild(submitBtn);
        addChild(getPaperBtn);

        center.setCenter(pane);
        this.setCenter(center);


    }

    public void initGUI() {
        BorderPane bp = new BorderPane();
        HBox bg = new HBox();
        Image image = new Image("GUI_assets/icon_editor.png");
        ImageView iv = new ImageView(image);
        iv.setFitWidth(200);
        iv.setFitHeight(200);
        iv.setPreserveRatio(true);
        bg.setAlignment(Pos.BOTTOM_RIGHT);
        bg.getChildren().add(iv);
        bp.setRight(iv);
        center.setBottom(bp);
    }

    private void addChild(Node child) {
        pane.getChildren().addAll(child);

    }

    public void createSubmission(Stage ps) {
//        Button findBtn = new Button("Open File");
//        findBtn.setTranslateY(100);
//        findBtn.setTranslateX(200);
        // TODO: set this to have logic based on the account given as input
//        findBtn.setOnAction(e -> {
//            entry = selectFile(ps);
//        });
       
        pickR = new Label("Select a Reviewer");
        pickR.setTranslateY(10);
        pickR.setTranslateX(60);

    //    Button assignBtn = new Button("Assign");
   //     assignBtn.setTranslateY(236);
   //     assignBtn.setTranslateX(275);

//        fileDir = new Label("Select a PDF File");
//        fileDir.setTranslateY(105);
//        fileDir.setTranslateX(132);


//        pane2.getChildren().addAll(fileDir);
//        pane2.getChildren().addAll(findBtn);
  //      pane2.getChildren().addAll(assignBtn);
        pane2.getChildren().addAll(pickR);
        addChild(pane2);
    }

    private void setReviewers(){
        paper = paperBox.getValue();
        paper.reviewers = new ArrayList<Reviewer>();
    	DataStore db = new DataStore();
        Reviewer r1 = reviewer1.getValue();
        paper.reviewers.add(r1);
        
        Reviewer r2 = reviewer2.getValue();
        paper.reviewers.add(r2);
        
        Reviewer r3 = reviewer3.getValue();
        paper.reviewers.add(r3);
        db.serialize();
    }
    
 //   private void submitToPaperPage() {
//		Navigation.navigate(PaperItemPage.class);
//	}
    
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

  
    private void setDeadline(Stage ps) {
        TextField deadlineTF = new TextField("yyyy-mm-dd");
        deadlineTF.setTranslateX(195);
        deadlineTF.setTranslateY(180);
        deadlineTF.setMinWidth(85);
        deadlineTF.setMaxWidth(85);

        Label selectJournalL = new Label("Set journal deadline");
        selectJournalL.setTranslateX(60);
        selectJournalL.setTranslateY(150);
        selectJournalL.setMinWidth(120);
        selectJournalL.setMaxWidth(120);

        Label deadlineErrorL = new Label("Must be a valid date in the format: yyyy-mm-dd");
        deadlineErrorL.setVisible(false);
        deadlineErrorL.setTextFill(Color.web("#FF7263"));
        deadlineErrorL.setTranslateX(0);
        deadlineErrorL.setTranslateY(0);

        ChoiceBox selectDeadlineCB = new ChoiceBox();
        selectDeadlineCB.setTranslateX(195);
        selectDeadlineCB.setTranslateY(210);
        selectDeadlineCB.setMinWidth(95);

        DataStore db = DataStore.load();
        ArrayList<Journal> journalList = db.university.journals;

        ChoiceBox selectJournalCB = new ChoiceBox();
        selectJournalCB.setTranslateX(195);
        selectJournalCB.setTranslateY(150);
        selectJournalCB.setMinWidth(120);
        for (Journal journal : journalList) {
            selectJournalCB.getItems().add(journal.name);
        }

        selectJournalCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                // Checks if the selected journal has been changed
                //System.out.println("Loading deadlines for selected journal");
                // populate ChoiceBox with journal's deadlines
                selectDeadlineCB.getSelectionModel().clearSelection();
                Journal selectedJournal = journalList.get(new_value.intValue());
                for (String deadline : selectedJournal.deadlines) {
                    selectDeadlineCB.getItems().add(deadline);
                }
            }
        });

        Button deadlineB = new Button("Add");
        deadlineB.setTranslateX(320);
        deadlineB.setTranslateY(180);
        deadlineB.setMinWidth(40);

        deadlineB.setOnAction(e -> {
            deadlineErrorL.setVisible(false);
            if (isValidDate(deadlineTF.getText())) {
                for (Journal journal : journalList) {
                    if (selectJournalCB.getValue() != null && journal.name == selectJournalCB.getValue()) {
                        //System.out.println("Adding " + deadlineTF.getText() + " to journal's deadlines");
                        insertDeadline(journal.deadlines, deadlineTF.getText());
                        db.serialize();

                        //refresh deadlines choice box
                        selectDeadlineCB.getSelectionModel().clearSelection();
                        selectDeadlineCB.getItems().clear();
                        for (String deadline : journal.deadlines) {
                            selectDeadlineCB.getItems().add(deadline);
                        }
                    }
                }
            } else {
                deadlineErrorL.setVisible(true);
            }
        });

        Button deadlineDeleteB = new Button("Remove");
        deadlineDeleteB.setTranslateX(320);
        deadlineDeleteB.setTranslateY(210);
        deadlineDeleteB.setMinWidth(60);

        deadlineDeleteB.setOnAction(e -> {
            for (Journal journal : journalList) {
                if (selectJournalCB.getValue() != null &&  journal.name == selectJournalCB.getValue()) {
                    if (selectDeadlineCB.getValue() != null) journal.deadlines.remove(selectDeadlineCB.getValue());
                    //System.out.println("Removing " + selectDeadlineCB.getValue() + " from journal's deadlines");
                    db.serialize();

                    //refresh deadlines choice box
                    selectDeadlineCB.getSelectionModel().clearSelection();
                    selectDeadlineCB.getItems().clear();
                    for (String deadline : journal.deadlines) {
                        selectDeadlineCB.getItems().add(deadline);
                    }
                }
            }
        });

        pane3.getChildren().addAll(selectJournalL, selectJournalCB, deadlineTF,
                selectDeadlineCB, deadlineB, deadlineDeleteB, deadlineErrorL);
        addChild(pane3);
    }


    private boolean isValidDate(String aDate) {
        // Makes sure the date is in the correct format
        // Must be a valid date of the form yyyy-mm-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(aDate);
            if (aDate.length() == 10) {
                return true;
            }
        } catch (ParseException e) {
            //e.printStackTrace();
            // ParseException expected if date does not follow intended format
        }
        return false;
    }


    private void insertDeadline(List<String> deadlines, String insert) {
        // Simple linear insertion to place the new deadline in the correct database position
        int i;
        for (i = 0; i < deadlines.size(); i++) {
            String current = deadlines.get(i);
            int currentYear = Integer.parseInt(current.substring(0, 4));
            int currentMonth = Integer.parseInt(current.substring(5, 7));
            int currentDay = Integer.parseInt(current.substring(8, 10));

            int insertYear = Integer.parseInt(insert.substring(0, 4));
            int insertMonth = Integer.parseInt(insert.substring(5, 7));
            int insertDay = Integer.parseInt(insert.substring(8, 10));

            if (currentYear > insertYear ||
                currentYear == insertYear && currentMonth > insertMonth ||
                currentYear == insertYear && currentMonth == insertMonth && currentDay > insertDay) {
                break;
            }
        }
        deadlines.add(i, insert);
    }


    public Pane getPane() {
        return pane;
    }


}
