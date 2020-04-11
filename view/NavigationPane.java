package view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import global.Navigation;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

public class NavigationPane extends VBox {


	private HashMap<String, Class<? extends Pane>> links = new HashMap<String, Class<? extends Pane>> ();
	
	public NavigationPane(Stage primaryStage)  {
		links.put("Journal List", FrontPane.class);
		links.put("Login", LoginPane.class);
		links.put("Researcher", ResearcherPane.class);
		links.put("Reviewer", ReviewerPane.class);
		links.put("Editor", EditorPane.class);
		links.put("Administrator", AdministratorPane.class);
		VBox buttonLayout = new VBox();
		links.forEach((name, pane) -> {
			Button button = new NavigationBtn(name);
			
			button.setOnAction(event -> {
                    try {
						Navigation.navigate(pane);
                    } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    }

            );
            buttonLayout.getChildren().add(button);


        });
        VBox logoLayout = initLogo();
        getChildren().addAll(buttonLayout,logoLayout);
        setSpacing(30);
        setStyle("-fx-background-color:#D0D0D0");
    }

    private VBox initLogo() {
        VBox layout = new VBox();
        Image image = new Image("GUI_assets/softRed_logo.png");
        double ratio = image.getWidth() / image.getHeight();
        ImageView iv = new ImageView(image);
        double btnHeight = 60;
        iv.setFitHeight(btnHeight);
        iv.setFitWidth(btnHeight * ratio);
        layout.getChildren().add(iv);
        layout.setAlignment(Pos.BASELINE_CENTER);
        return layout;
    }

    private class NavigationBtn extends Button {
        private ImageView neutral;
        private ImageView hover;

        public NavigationBtn(String label) {
            super(label);
            setPrefWidth(104);
            setStyle("-fx-background-color: transparent;");
            setAlignment(Pos.BASELINE_LEFT);
            setStyle("-fx-background-image: url('/GUI_assets/button_neutral.png')");
            Image image = new Image("GUI_assets/button_neutral.png");
            neutral = new ImageView(image);
            image = new Image("GUI_assets/button_hover.png");
            hover = new ImageView(image);
//			setGraphic(neutral);
            addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent e) {
                    setStyle("-fx-background-image: url('/GUI_assets/button_hover.png')");


                }
            });
            addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    setStyle("-fx-background-image: url('/GUI_assets/button_neutral.png')");


                }
            });
        }
    }
}
