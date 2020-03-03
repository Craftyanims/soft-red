/**
 * Author: Soft Red (Jeffery Yang, Dylan Wheeler, Steven Loppe, Thien-Kim Nguyen, Hafsa Zia) - 2020
 * Created for SENG 300 at The University of Calgary during the winter semester of 2020
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

    Scene login_scene, researcher_scene, reviewer_scene, editor_scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Journal Submission System");

        /*
        * Populate the login page
        */
        BorderPane login_layout = new BorderPane();

        TextField login_tf = new TextField();

        Button login_b = new Button("Press Me");
        // TODO: set this to have logic based on the account given as input
        login_b.setOnAction(e -> {
            primaryStage.setScene(researcher_scene);
        });

        login_layout.setTop(login_b);
        login_layout.setCenter(login_tf);
        login_scene = new Scene(login_layout, 800, 600);


        /*
        * Populate the Researcher User page
        */
        BorderPane researcher_layout = new BorderPane();

        Label researcher_l = new Label("Researcher");

        researcher_layout.setCenter(researcher_l);
        researcher_scene = new Scene(researcher_layout, 800, 600);


        /*
         * Populate the Reviewer User page
         */
        BorderPane reviewer_layout = new BorderPane();

        Label reviewer_l = new Label("Reviewer");

        reviewer_layout.setCenter(reviewer_l);
        reviewer_scene = new Scene(reviewer_layout, 800, 600);


        /*
         * Populate the Editor User page
         */
        BorderPane editor_layout = new BorderPane();

        Label editor_l = new Label("Editor");

        editor_layout.setCenter(editor_l);
        editor_scene = new Scene(editor_layout, 800, 600);


        primaryStage.setScene(login_scene);
        primaryStage.show();
    }

    public User[] getAccounts() {
        // Gets accounts from a file and returns them all as an array
        // TODO: read accounts from a separate file, call methods in each user class to get values as objects
        User[] accounts = new User[3];

        return accounts;
    }

    public void addAccount(User aUser) {
        // TODO: access accounts file and save this user to a new line

    }

    public void removeAccount(User aUser) {
        // Should this take the user or something like an ID or index
    }

}
