package uasGizi;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        StackPane root = new StackPane();
        Scene primaryScene = new Scene(root, 900, 650);
        stage.setScene(primaryScene);
            
        stage.setMaximized(true);
        
        LoginView lv = new LoginView();
        lv.show(stage, primaryScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}