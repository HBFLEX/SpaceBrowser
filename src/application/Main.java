package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 *
 * @author USER
 */
public class Main extends Application {
    
    public void start(Stage stage) throws IOException{
        // root scene
        Parent root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // stage configurations
        Image icon = new Image("/resources/browserIcon.png");
        stage.getIcons().add(icon);
        stage.setTitle("SpaceBrowser");
        scene.getStylesheets().add(getClass().getResource("/resources/main.css").toExternalForm());
        
        stage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
