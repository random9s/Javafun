/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpvc4grid;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;


/**
 *
 * @author jakeparham
 */
public class Jpvc4grid extends Application {
        private final int num_rows = 50;
        private final int num_cols = 50;
        private final int grid_width = 500;
        private final int grid_height = 500;
        
    @Override
    public void start(Stage primaryStage) {   
        ArrayList<Color> colors = new ArrayList<>();
        colors = initColors();
        GridPane grid = new GridPane();
        grid.setPrefSize(grid_width, grid_height);
        
        Random rand = new Random();
        
        for(int i=0; i<num_rows;i++){
            for(int j=0;j<num_cols;j++){
                int index = rand.nextInt(colors.size());
                Color c = colors.get(index);
                Rectangle r = new Rectangle(i, j, (grid_width/num_rows), (grid_height/num_cols));
                r.setFill(c);
                grid.add(r, i, j);
            }
        }
        
        
        Scene scene = new Scene(grid, grid_width, grid_height);        
        
        primaryStage.setTitle("Grid");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public ArrayList<Color> initColors(){
        ArrayList<Color> colors = new ArrayList<>();
        Random rand = new Random();
               
        for(int i=0; i<10; i++){
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);
            
            Color c = Color.rgb(r,g,b);
            colors.add(c);
        }
        
        return colors;
    }
}
