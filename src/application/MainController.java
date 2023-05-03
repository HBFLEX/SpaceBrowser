package application;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.stage.Stage;

/**
 *
 * @author HBFL3X
 */
public class MainController implements Initializable {
    
    @FXML private TextField queryInput;
    @FXML private WebView webView;
    @FXML private Button searchBtn;
    @FXML private ComboBox<String> zoomCombo;
    @FXML private Label titleBar;
    @FXML private Button openNewWindow;
    
    private WebEngine webEngine;
    private String currentZoomLevel;
    private WebHistory history;
    private double loadingStateValue = 0.0;
    private Timer timer;
    private TimerTask timerTask;
    
    private ObservableList zoomLevels = 
            FXCollections.observableArrayList("10%", "40%", "60%", "80%", "100%", "120%", "140%", "160%", "180%", "200%");
    
    String page_not_found_file = getClass().getClassLoader().getResource("resources/page_not_found.html").toExternalForm();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        webEngine = webView.getEngine();
        webEngine.load("http://www.google.com");
        
        // config the loading state of the current page
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>(){
            @Override
            public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
                
                if (newState == State.FAILED){
                    webEngine.load(page_not_found_file);
                }
                
                if (newState == State.READY){
                    titleBar.setText("Waiting for google.com");
                }
                
                if (newState == State.RUNNING){
                    queryInput.setText(webEngine.getLocation());
                    titleBar.setText("Loading...");
                }
                
                if (newState == State.SUCCEEDED){    
                    if (webEngine.getTitle() != null)
                        titleBar.setText(webEngine.getTitle());
                    else
                        titleBar.setText(webEngine.getLocation());
                }
            }
        
        });
        
        zoomCombo.setItems(zoomLevels);
        zoomCombo.setValue("100%");
    }
    
    public void search(){
        // check if the queryInput contains http, 
        // if it does replace it with location of the current page
        // otherwise add http appending with the queryInput url address provided
        if (queryInput.getText().contains("http"))
            webEngine.load(queryInput.getText());
        else
            webEngine.load("http://"+queryInput.getText());
    }
    
    public void reloadPage(){
        webEngine.reload();
    }
    
    public void prevPage(){
        history = webEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(-1);
    }
    
    public void setZoomLevel(){
        currentZoomLevel = zoomCombo.getSelectionModel().getSelectedItem().toString();
        currentZoomLevel = currentZoomLevel.substring(0, currentZoomLevel.length() - 1);
        webView.setZoom(Double.parseDouble(currentZoomLevel) * 0.01);
    }
    
    public void nextPage(){
        history = webEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(1);
    }
    
    public void instanstiateNewWindow() throws IOException{
        Stage newWindow = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
        Scene scene = new Scene(root);
        newWindow.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/resources/browserIcon.png"));
        newWindow.getIcons().add(icon);
        newWindow.show();
    }
    
//    public void runProgressLoader(){
//        timer = new Timer();
//        timerTask = new TimerTask(){
//            public void run(){
//                loadingStateValue += 0.1;
//                progressLoader.setProgress(loadingStateValue);
//            }
//        };
//        
//        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
//    }
    
}
