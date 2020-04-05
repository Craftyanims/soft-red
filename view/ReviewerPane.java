package view;
import global.Navigation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
public class ReviewerPane extends BasePane {
    private BorderPane center;
    private StackPane pane;
    public ReviewerPane(Stage ps){
        super(ps, "Reviewer Pane");
//        this.setCenter(pane);
        pane = new StackPane();
        initGUI();
        this.setCenter(pane);
        //FrontPane frontPane = new FrontPane(ps, "Front Page");
        //Navigation.navigate(frontPane);

    }
    public void initGUI(){
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

    public StackPane getPane(){
        return pane;
    }

}