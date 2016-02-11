/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpvc4checker;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author jakeparham
 */
class CheckerBoard {
    private int numRow;
    private int numCols;
    private int boardWidth;
    private int boardHeight;
    private Color lightColor;
    private Color darkColor;
    private int squareWidth;
    private int squareHeight;
    private GridPane board;
    
    public CheckerBoard(int rows, int cols, int width, int height){
        this.numRow = rows;
        this.numCols = cols;
        this.boardWidth = width;
        this.boardHeight = height;
        this.lightColor = Color.RED;
        this.darkColor = Color.BLACK;
        this.squareHeight = height * height;
        this.squareWidth = width * width;
        this.board = null;
    }
    
    public CheckerBoard(int rows, int cols, int width, int height, Color light , Color dark){
        this.numRow = rows;
        this.numCols = cols;
        this.boardWidth = width;
        this.boardHeight = height;
        this.lightColor = light;
        this.darkColor = dark;
        this.squareHeight = height * height;
        this.squareWidth = width * width;
        this.board = null;
    }
    
    public GridPane build(){
        GridPane grid = new GridPane();

        for(int i=0; i<this.numRow;i++){
            for(int j=0;j<this.numCols;j++){
                switch ((i+j) % 2) {
                    case 0:
                        {
                            Rectangle tile = new Rectangle(i, j, (this.boardWidth/this.numRow), (this.boardWidth/this.numCols));
                            tile.setFill(this.lightColor);
                            grid.add(tile, i, j);
                            break;
                        }
                    case 1:
                        {
                            Rectangle tile = new Rectangle(i, j, (this.boardWidth/this.numRow), (this.boardWidth/this.numCols));
                            tile.setFill(this.darkColor);
                            grid.add(tile, i, j);
                            break;
                        }
                    default:
                        System.out.println("I don't know how you got here!");
                        break;
                }
            }
        }
        this.board = grid;
        return grid;
    }
    
    public GridPane getBoard(){
        if(this.board != null){ return this.board;} 
        else{ return build(); }
    }
    
    public int getNumRows(){ return this.numRow; }
    public int getNumCols(){ return this.numCols; }
    public int getWidth(){ return this.boardWidth; }	
    public int getHeight(){ return this.boardHeight; }
    public Color getLightColor(){ return this.lightColor; }
    public Color getDarkColor(){ return this.darkColor; }
    public int getSquareWidth(){ return this.squareWidth; }
    public int getSquareHeight(){ return this.squareHeight; }
}
