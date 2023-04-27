package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;

/**
 *
 * @author HBFL3X
 */
public class MainController implements Initializable {
    
    @FXML private TextField queryInput;
    @FXML private WebView webView;
    @FXML private Button searchBtn;
    @FXML private ComboBox<String> zoomCombo;
    
    private WebEngine webEngine;
    private String currentZoomLevel;
    private WebHistory history;
    
    private ObservableList zoomLevels = 
            FXCollections.observableArrayList("10%", "40%", "60%", "80%", "100%", "120%", "140%", "160%", "180%", "200%");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        webEngine = webView.getEngine();
        webEngine.load("http://www.google.com");
        
        zoomCombo.setItems(zoomLevels);
        zoomCombo.setValue("100%");
    }
    
    public void search(){
        webEngine.load("http://"+queryInput.getText());
        System.out.println("searching...");
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
    
}
