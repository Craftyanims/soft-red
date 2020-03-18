package view;
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
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

public class ResearcherPane extends BasePane {
    private Pane pane;
    private Pane pane2;
    private File entry;
    private Label fileDir;
    public ResearcherPane(Stage ps){
    	super(ps, "Researcher Pane");
    	
    	
        pane = new VBox();
        pane2 = new HBox();
        

        Label researcher_l = new Label("Researcher");
        researcher_l.setTranslateY(-300);

        createSubmission(ps);
        DataStore test = new DataStore();
        test.serialize();
        
        DataStore db = DataStore.load();  
        ArrayList<Reviewer> reviewers = db.university.reviewers;
        //ArrayList<String> names = new ArrayList<String>();


       // ChoiceBox cb = new ChoiceBox();
       // for(Reviewer r : reviewers) {
         //      cb.getItems().add(r.name);
        //}
        
       // ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
        	//    "First", "Second", "Third")
        //	);
        //in choicebox get list of reviewers or make a function to get reviewers
        
        ChoiceBox cb1 = new ChoiceBox(FXCollections.observableArrayList(
        	    "First", "Second", "Third")
        	);
        
       ChoiceBox cb2 = new ChoiceBox(FXCollections.observableArrayList(
        	    "First", "Second", "Third")
        	);
        
   //     addChild(cb);
        addChild(cb1);
        addChild(cb2);
      //  addChild(researcher_l);
        
        this.setCenter(pane);
       // this.setCenter(cb);
      // this.setRight(cb1);
        
        
    }

    private void addChild(Node child){
        pane.getChildren().addAll(child);

    };

    public void createSubmission(Stage ps){
        Button findBtn = new Button("open file");
      //  findBtn.setTranslateY(-100);
      //  findBtn.setTranslateX(200);
        // TODO: set this to have logic based on the account given as input
        findBtn.setOnAction(e -> {
            entry = selectFile(ps);
        });
        Button submitBtn = new Button("submit");

        submitBtn.setOnAction(e -> {
            System.out.println("saving. . .");
            try{
                saveFile(entry);
                System.out.println("Complete!");

            }catch (IOException error){
                error.printStackTrace();
            }
        });

        fileDir = new Label("select a pdf file");


       // fileDir.setTranslateY(-100);

      //  addChild(fileDir);
        //addChild(findBtn);
        //addChild(submitBtn);
        pane2.getChildren().addAll(fileDir);
        pane2.getChildren().addAll(findBtn);
        pane2.getChildren().addAll(submitBtn);
        addChild(pane2);
    }

    //    private void saveFile(File file){
//        File dest = new File("\\All_Entries");
//        try {
//            Path src = Paths.get(file);
//            Path fnl = Paths.get(dest);
//            copy(src.toFile(),dest.toFile());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    private void saveFile(File source) throws IOException {
        File folder = new File("All Journals");
        folder.mkdirs();

        File dest = new File("All Journals\\NAME_"+source.getName());
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

    public Pane getPane(){
        return pane;
    }


}
