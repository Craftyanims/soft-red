package view;

import java.util.ArrayList;

import global.Navigation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;

import java.nio.file.Files;

import java.io.File;
import java.io.IOException;

import model.DataStore;
import model.Journal;

public class JournalListPane extends BasePane {

    private Stage stage;

    private ArrayList<model.Journal> createFakeJournals() {
        ArrayList<model.Journal> fake = new ArrayList<model.Journal>();
        fake.add(new model.Journal("sample_1"));
        fake.add(new model.Journal("sample_2"));
        fake.add(new model.Journal("sample_3"));

        return fake;
    }


    public JournalListPane(Stage stage) {
        super(stage, "Journal List Page");
        System.out.println("Journal List Pane");
        this.stage = stage;

        DataStore db = DataStore.load();
        
        VBox contentPane = createPane(db.university.journals);
        this.setCenter(contentPane);

    }

    public VBox createPane(ArrayList<model.Journal> journals){
        VBox contentPane = new VBox();
        if(journals == null){
            journals = createFakeJournals();
        }
        for (int i = 0; i < journals.size(); i++) {
            Pane journalBox = generateJournalListItem(journals.get(i));
            journalBox.setStyle("-fx-background-color: pink; -fx-padding: 10px;");
            contentPane.getChildren().addAll(journalBox);
        }
        
        
        // Insert the "add new journal" button
        Button newJournal = new Button("New Journal");
        newJournal.setOnAction(event -> Navigation.navigate(NewJournalPane.class));
        
        contentPane.getChildren().add(newJournal);
        
        return contentPane;
    }

    public Pane generateJournalListItem(model.Journal journal) {
        HBox container = new HBox();

        Label titleLabel = new Label(journal.name);
        Button viewButton = new Button("View");
        viewButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Journal");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.pdf"));
            fileChooser.setInitialFileName(journal.name);
            File file = new File("All Journals\\" + journal.name + ".pdf");

            if (file != null) {
                File dest = fileChooser.showSaveDialog(stage);
                if (dest != null) {
                    try {
                        Files.copy(file.toPath(), dest.toPath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                System.out.println("file not found");
            }
        });

        container.getChildren().addAll(titleLabel, viewButton);

        return container;
    }
}
