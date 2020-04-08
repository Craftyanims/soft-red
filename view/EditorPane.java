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

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

public class   EditorPane extends BasePane {
    private Pane pane;
    private Pane pane2;
    private Pane pane3;
    private File entry;
    private Label fileDir;
    private Label pickR;
    public EditorPane(Stage ps){
    	super(ps, "Editor Pane");
    	
    	
        pane = new VBox();
        pane2 = new HBox();
        pane3 = new Pane();
        

        Label researcher_l = new Label("Editor");
        researcher_l.setTranslateY(-300);

        createSubmission(ps);
        setDeadline(ps);
        
        DataStore db = DataStore.load();  
        ArrayList<Reviewer> reviewers = db.university.reviewers;
        //ArrayList<String> names = new ArrayList<String>();


       // ChoiceBox cb = new ChoiceBox();
       // for(Reviewer r : reviewers) {
         //      cb.getItems().add(r.name);
        //}
        
        
        ChoiceBox cb1 = new ChoiceBox(FXCollections.observableArrayList(
        	    "First", "Reviewers")
        	);
        cb1.setTranslateY(120);
        cb1.setTranslateX(275);
        
       ChoiceBox cb2 = new ChoiceBox(FXCollections.observableArrayList(
        	    "Second", "Reviewers")
        	);
       cb2.setTranslateY(140);
       cb2.setTranslateX(275);
       ChoiceBox cb3 = new ChoiceBox(FXCollections.observableArrayList(
       	    "Third", "Reviewers" )
       	);
       cb3.setTranslateY(160);
       cb3.setTranslateX(275);


       
        addChild(cb1);
        addChild(cb2);
        addChild(cb3);
        
        this.setCenter(pane);
   
        
        
    }

    private void addChild(Node child){
        pane.getChildren().addAll(child);

    }

    public void createSubmission(Stage ps){
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
            try{
                saveFile(entry);
                System.out.println("Complete!");

            }catch (IOException error){
                error.printStackTrace();
            }
        });

        pickR = new Label("Select a Reviewer");
        pickR.setTranslateY(150);
        pickR.setTranslateX(-126);
        
        Button assignBtn = new Button("Assign");
        assignBtn.setTranslateY(236);
        assignBtn.setTranslateX(195);

        fileDir = new Label("Select a PDF File");
         fileDir.setTranslateY(105);
         fileDir.setTranslateX(132);
   
        
        pane2.getChildren().addAll(fileDir);
        pane2.getChildren().addAll(findBtn);
        pane2.getChildren().addAll(submitBtn);
        pane2.getChildren().addAll(assignBtn);
        pane2.getChildren().addAll(pickR);
        addChild(pane2);
    }

    private void saveFile(File source) throws IOException {
        File folder = new File("All Journals");
        folder.mkdirs();

        File dest = new File("All Journals\\NAME_"+source.getName());
        DataStore db = new DataStore();
        University u = db.load().university;
        u.journals.add(new Journal(source.getName()));
        db.serialize();


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

    private File selectFile(Stage ps){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.pdf")
        );

        File f = fc.showOpenDialog(ps);
        if(f != null) {
            fileDir.setText(f.getName());

            return f;
        }else{
            System.out.println("file not selected");
            return null;
        }
    }

    private void setDeadline(Stage ps) {
        TextField deadlineTF = new TextField("yyyy-mm-dd");
        deadlineTF.setTranslateX(275);
        deadlineTF.setTranslateY(330);
        deadlineTF.setMinWidth(85);
        deadlineTF.setMaxWidth(85);

        Label selectJournalL = new Label("Set journal deadline");
        selectJournalL.setTranslateX(138);
        selectJournalL.setTranslateY(300);
        selectJournalL.setMinWidth(120);
        selectJournalL.setMaxWidth(120);

        Label deadlineErrorL = new Label("Must be a valid date in the format: yyyy-mm-dd");
        deadlineErrorL.setVisible(false);
        deadlineErrorL.setTextFill(Color.web("#FF7263"));
        deadlineErrorL.setTranslateX(0);
        deadlineErrorL.setTranslateY(0);

        ChoiceBox selectDeadlineCB = new ChoiceBox();
        selectDeadlineCB.setTranslateX(275);
        selectDeadlineCB.setTranslateY(360);
        selectDeadlineCB.setMinWidth(95);

        DataStore db = DataStore.load();
        ArrayList<Journal> journalList = db.university.journals;

        ChoiceBox selectJournalCB = new ChoiceBox();
        selectJournalCB.setTranslateX(275);
        selectJournalCB.setTranslateY(300);
        selectJournalCB.setMinWidth(120);
        for (Journal journal : journalList) {
            selectJournalCB.getItems().add(journal.name);
        }

        selectJournalCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                // Checks if the selected journal has been changed
                System.out.println("Loading deadlines for selected journal");
                // populate ChoiceBox with journal's deadlines
                selectDeadlineCB.getSelectionModel().clearSelection();
                Journal selectedJournal = journalList.get(new_value.intValue());
                for (String deadline : selectedJournal.deadlines) {
                    selectDeadlineCB.getItems().add(deadline);
                }
            }
        });

        Button deadlineB = new Button("Add");
        deadlineB.setTranslateX(400);
        deadlineB.setTranslateY(330);
        deadlineB.setMinWidth(40);

        deadlineB.setOnAction(e -> {
            deadlineErrorL.setVisible(false);
            if (isValidDate(deadlineTF.getText())) {
                for (Journal journal : journalList) {
                    if (selectJournalCB.getValue() != null && journal.name == selectJournalCB.getValue()) {
                        System.out.println("Adding " + deadlineTF.getText() + " to journal's deadlines");
                        journal.deadlines.add(deadlineTF.getText());
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
        deadlineDeleteB.setTranslateX(400);
        deadlineDeleteB.setTranslateY(360);
        deadlineDeleteB.setMinWidth(60);

        deadlineDeleteB.setOnAction(e -> {
            for (Journal journal : journalList) {
                if (selectJournalCB.getValue() != null &&  journal.name == selectJournalCB.getValue()) {
                    if (selectDeadlineCB.getValue() != null) journal.deadlines.remove(selectDeadlineCB.getValue());
                    System.out.println("Removing " + selectDeadlineCB.getValue() + " from journal's deadlines");
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

        //TODO : sort deadlines by date before saving them to database

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
            return true;
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return false;
    }


    public Pane getPane(){
        return pane;
    }


}
