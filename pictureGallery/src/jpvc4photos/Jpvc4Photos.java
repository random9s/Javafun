/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpvc4photos;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author jakeparham
 */
public class Jpvc4Photos extends Application {
    private PhotoManager photoManager;
    ArrayList<Photo> photos;
    private int index = 0;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane sp = new StackPane();
        Scene scene = new Scene(sp, 800, 600);
        createViewer(sp, primaryStage, scene);
    }
    
    private void createViewer(StackPane sp, Stage stage, Scene scene) throws Exception{        
        Image scrollLeft = new Image(getClass().getResourceAsStream("left_btn.jpeg"));
        Image scrollRight = new Image(getClass().getResourceAsStream("right_btn.jpeg"));
        
        Button leftBtn = new Button("", new ImageView(scrollLeft));    
        Button rightBtn = new Button("", new ImageView(scrollRight));
        leftBtn.setMinSize(20, 40);
        leftBtn.setMaxSize(50, 85);
        rightBtn.setMinSize(20, 40);
        rightBtn.setMaxSize(50, 85);
        
        leftBtn.setStyle("-fx-background-color: rgba(0,0,0,0.08),\n" +
                         "linear-gradient(#5a61af, #51536d),\n" +
                         "linear-gradient(#e4fbff 0%,#cee6fb 10%, #a5d3fb 50%, #88c6fb 51%, #d5faff 100%);\n" +
                         "-fx-background-insets: 0 0 -1 0,0,1;\n" +
                         "-fx-background-radius: 5,5,4;\n" +
                         "-fx-padding: 3 30 3 30;\n" +
                         "-fx-text-fill: #242d35;\n" +
                         "-fx-font-size: 14px;");
        leftBtn.setOnAction((ActionEvent e) -> {
            if (!((this.getIndex() - 1) < 0)) {
                try {
                    this.index--;
                    createViewer(sp, stage, scene);
                } catch (Exception ex) {
                    Logger.getLogger(Jpvc4Photos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        rightBtn.setStyle("-fx-background-color: rgba(0,0,0,0.08),\n" +
                         "linear-gradient(#5a61af, #51536d),\n" +
                         "linear-gradient(#e4fbff 0%,#cee6fb 10%, #a5d3fb 50%, #88c6fb 51%, #d5faff 100%);\n" +
                         "-fx-background-insets: 0 0 -1 0,0,1;\n" +
                         "-fx-background-radius: 5,5,4;\n" +
                         "-fx-padding: 3 30 3 30;\n" +
                         "-fx-text-fill: #242d35;\n" +
                         "-fx-font-size: 14px;");
        rightBtn.setOnAction((ActionEvent e) -> {
            if(!((this.getIndex() + 1) > (photos.size()-1))){
                try {
                    this.index++;
                    createViewer(sp, stage, scene);
                } catch (Exception ex) {
                    Logger.getLogger(Jpvc4Photos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        photoManager = new PhotoManager();
        photoManager.load();
        photos = photoManager.getPhotos();
                
        Photo curr = photos.get(this.index);
        String imageURL = curr.getUrl();
        Image img = new Image(imageURL);
        ImageView imgView = new ImageView(img);
        
        sp.getChildren().removeAll(sp.getChildren());

        sp.getChildren().add(imgView);
        sp.getChildren().add(leftBtn);
        sp.getChildren().add(rightBtn);
        sp.setAlignment(imgView, Pos.CENTER);
        sp.setAlignment(leftBtn, Pos.CENTER_LEFT);
        sp.setAlignment(rightBtn, Pos.CENTER_RIGHT);
        
        stage.setTitle("Picture Viewer");
        System.out.println(curr.toString());
        stage.setScene(scene);
        stage.show();
    }
    
    public int getIndex(){ return this.index; }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
