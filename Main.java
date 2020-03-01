import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
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
        StackPane login_layout = new StackPane();

        TextField login_tf = new TextField();

        Button login_b = new Button("Login");
        // TODO: set this to have logic based on the account given as input
        login_b.setOnAction(e -> {
            primaryStage.setScene(researcher_scene);
        });

        login_layout.getChildren().addAll(login_tf, login_b);
        login_scene = new Scene(login_layout, 600, 800);


        /*
        * Populate the Researcher User page
        */
        ResearcherPane researcher_layout = new ResearcherPane(primaryStage);
        researcher_scene = new Scene(researcher_layout.getPane(), 600, 800);

        /*
         * Populate the Reviewer User page
         */
        StackPane reviewer_layout = new StackPane();

        Label reviewer_l = new Label("Reviewer");

        reviewer_layout.getChildren().addAll(reviewer_l);
        reviewer_scene = new Scene(reviewer_layout, 600, 800);


        /*
         * Populate the Editor User page
         */
        StackPane editor_layout = new StackPane();

        Label editor_l = new Label("Editor");

        editor_layout.getChildren().addAll(editor_l);
        editor_scene = new Scene(editor_layout, 600, 800);


        primaryStage.setScene(login_scene);
        primaryStage.show();
    }

}
