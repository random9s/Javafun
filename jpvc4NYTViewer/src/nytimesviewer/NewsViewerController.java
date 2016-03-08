/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nytimesviewer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dale
 * http://stackoverflow.com/questions/26227786/loading-urls-in-javafx-webview-is-crashing-the-jvm
 */
public class NewsViewerController implements Initializable {

    private Stage stage;
    
    ArrayList<NYTNewsStory> stories;
    
    
    @FXML
    private TextField searchTextField;
    
    @FXML
    private ListView newsListView;
    
    @FXML
    private WebView webView;
    
    private static String searchString = "University of Missouri";
    private WebEngine webEngine;
    ObservableList<String> newsListItems;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void ready(Stage stage) {
        this.stage = stage;
        webEngine = webView.getEngine();
        //newsManager = new NYTNewsManager();
        
        newsListItems = FXCollections.observableArrayList();
        newsListView.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            // When the contents of the newsListView changes a situation can be created
            // where autoselection results in a new_val that is out of range of the stories.
            // The following makes sure the new_val is within the bounds of stories.
            if ((int)new_val < 0 || (int)new_val > (stories.size() - 1)) {
                return;
            }
            NYTNewsStory story = stories.get((int)new_val);
            webEngine.load(story.webUrl);
        });
        
        // put initial search string in searchTextField and load news based
        // on that search
        searchTextField.setText(searchString);
        loadNews();
    }
    
    private void loadNews() {
        try {
            NYTNewsManager.load(searchString);
        } catch(Exception ex) {
            displayExceptionAlert(ex);
            return;
        }
        
        stories = NYTNewsManager.getNewsStories();
        newsListItems.clear();
        
        for (NYTNewsStory story : stories) {
            newsListItems.add(story.headline);
        }
        //newsListView.getItems().clear();
        newsListView.setItems(newsListItems);
        if (stories.size() > 0) {
            newsListView.getSelectionModel().select(0);
            newsListView.getFocusModel().focus(0);
            newsListView.scrollTo(0);
        }
    }
    
    @FXML
    private void handleSearch(ActionEvent event) {
        if (searchTextField.getText().equals("")) {
            displayErrorAlert("The search field cannot be blank. Enter one or more search words.");
            return;
        }
        searchString = searchTextField.getText();
        loadNews();
    }
    
    @FXML
    private void handleUpdate(ActionEvent event) {
        loadNews();
    }
    
    @FXML
    private void handleAbout(ActionEvent event) {
        displayAboutAlert();
    }
    
    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void displayExceptionAlert(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText("An Exception Occurred!");
        alert.setContentText("An exception occurred.  View the exception information below by clicking Show Details.");

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
    
    private void displayAboutAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("New York Times Viewer");
        alert.setContentText("This application was developed by Dale Musser for CS3330 at the University of Missouri.");
        
        TextArea textArea = new TextArea("The New York Times API is used to obtain a news feed.  Developer information is available at http://developer.nytimes.com. ");
        textArea.appendText("Dale's api-key is used in this application.  If you develop your own applicatyion get your own api-key from the New York Times.");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
            
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        alert.getDialogPane().setExpandableContent(expContent);
        
        alert.showAndWait();
    }
    
}
