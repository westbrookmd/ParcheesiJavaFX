//BoardBuilder programmed by Christopher Smith
//Additional coding by Marshall Westbrook
//Use this to create the game board

import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class BoardBuilder extends Application {

   //initialize constants used to count total number of spaces (including invisible), as well as the width and height of a single space
   private static final int BOARD_SIZE = 361;
   private static final int SPACE_WIDTH_MULTIPLE = 28;
   private static final int SPACE_HEIGHT_MULTIPLE = 29;
   private static final float CIRCLE_RADIUS_MULTIPLE = 9.5f;
   
   //create a GridPane object that will be used by the main GUI to display the board; this will prevent oddities that happen from using a StackPane on the GUI 
   public GridPane boardBuilder(Stage primaryStage) {
      //create panes for the boards
      StackPane board = new StackPane();
      GridPane startSpaces = new GridPane();
      GridPane cross = new GridPane();
      //cross.setGridLinesVisible(true);
      
      //create and initialize arrays for each space, create separate array to hold only the tiles that are actually seen by the player
      Circle[] start = new Circle[4];
      Tile[] spaces = new Tile[BOARD_SIZE];
      Tile[] gameTiles = new Tile[96];
      
      for(int i = 0; i < start.length; i++){
         start[i] = new Circle();
         //circle size based on multiple of window size
         start[i].radiusProperty().bind(Bindings.min(primaryStage.widthProperty().divide(CIRCLE_RADIUS_MULTIPLE), primaryStage.heightProperty().divide(CIRCLE_RADIUS_MULTIPLE)));
         start[i].setStroke(Color.BLACK);
         //set the colors of starting spaces to match player colors. later on we'll need to determine extra colors so that pieces can be more easily seen against spaces with matching colors (for example, green pieces at green start)
         switch(i) {
            case 0: start[i].setFill(Color.rgb(203, 195, 227)); break; //LIGHTPURPLE
            case 1: start[i].setFill(Color.KHAKI); break;
            case 2: start[i].setFill(Color.rgb(255,165,0)); break; //LIGHTORANGE
            case 3: start[i].setFill(Color.PALEGREEN); break;
         }
      }
      
      //set default color for visible spaces
      for(int i = 0; i < spaces.length; i++){
         spaces[i] = new Tile();
         //space size based on multiple of window size
         spaces[i].base.widthProperty().bind(primaryStage.widthProperty().divide(SPACE_WIDTH_MULTIPLE));
         spaces[i].base.heightProperty().bind(primaryStage.heightProperty().divide(SPACE_HEIGHT_MULTIPLE));
         spaces[i].setStroke(Color.BLACK);
         spaces[i].setFill(Color.BEIGE);
      }
      
      //create the cross shape
      int col = 0;
      int row = 0;
      int tileNo = 1;
      int tileCount = 0;
      
      for(int i = 0; i < BOARD_SIZE; i++){
      
         //turn all spaces that are outside of the board invisible
         cross.add(spaces[i], col, row);
         if((col < 8 && (row < 8 || row >= 11)) || (col >= 11 && (row < 8 || row >= 11))){
            spaces[i].setStroke(Color.TRANSPARENT);
            spaces[i].setFill(Color.TRANSPARENT);
         }
         
         else if((col > 7 && col < 11) && (row > 7 && row < 11)) {
            spaces[i].setStroke(Color.TRANSPARENT);
            spaces[i].setFill(Color.TRANSPARENT);
         }
         
         else {
            gameTiles[tileCount] = spaces[i];
            tileCount++;
         }
         
         //change the colors of center lanes and give them midlane status
         if(col == 9) {
            if(row <= 7) {
               spaces[i].setFill(Color.rgb(203, 195, 227));
               spaces[i].isSafe = true;
               if(row != 0) {
                  if(tileNo > 7) {
                     tileNo = 1;
                  }
                  spaces[i].isMidLane = true;
                  spaces[i].tileNo = tileNo;
                  spaces[i].testTileCount();
                  tileNo++;
               }
            }
            else if(row >= 11) {
               spaces[i].setFill(Color.GREEN);
               spaces[i].isSafe = true;
               if(row != 18) {
                  if(tileNo < 1) {
                     tileNo = 7;
                  }
                  spaces[i].isMidLane = true;
                  spaces[i].tileNo = tileNo;
                  spaces[i].testTileCount();
                  tileNo--;
               }
            }
         }
         
         if(row == 9) {
            if(col <= 7) {
               spaces[i].setFill(Color.rgb(255,165,0));
               spaces[i].isSafe = true;
               if(col != 0) {
                  if(tileNo > 7) {
                     tileNo = 1;
                  }
                  spaces[i].isMidLane = true;
                  spaces[i].tileNo = tileNo;
                  spaces[i].testTileCount();
                  tileNo++;
               }
            }
            else if(col >= 11) {
               spaces[i].setFill(Color.YELLOW);
               spaces[i].isSafe = true;
               if(col != 18) {
                  if(tileNo > 7) {
                     tileNo = 7;
                  }
                  spaces[i].isMidLane = true;
                  spaces[i].tileNo = tileNo;
                  spaces[i].testTileCount();
                  tileNo--;
               }
            }
         }
         
         if(col == 18){
            col = 0;
            row++;
         }
         else {
            col++;
         }
      }
      
      //number the tiles in a way that makes it easy for the player to move their pieces around the board
      //number the main tiles players move around on
      

      //draw the board
      Rectangle center = new Rectangle();
      //center size based on multiple of window size
      center.widthProperty().bind(primaryStage.widthProperty().divide(SPACE_WIDTH_MULTIPLE).multiply(3));
      //center size based on multiple of window size
      center.heightProperty().bind(primaryStage.heightProperty().divide(SPACE_HEIGHT_MULTIPLE).multiply(3));
      center.setStroke(Color.RED);
      center.setFill(Color.LIGHTBLUE);
      center.setStrokeWidth(5);
      startSpaces.add(center, 1, 1);
      
      //add start spaces to board
      startSpaces.add(start[0], 0, 0);
      startSpaces.add(start[1], 2, 0);
      startSpaces.add(start[2], 0, 2);
      startSpaces.add(start[3], 2, 2);
      
      //add board pieces to stackpane
      //put startspaces before cross so circles don't cover the cross with an odd window size
      board.getChildren().addAll( startSpaces, cross);
      startSpaces.setAlignment(Pos.CENTER);
      cross.setAlignment(Pos.CENTER);
      
      //test placing tokens
      //spaces[8].placeTokens(2);
      //spaces[8].setStroke(Color.RED);
      
      GridPane game = new GridPane();
      game.add(board, 0, 0);
      game.setAlignment(Pos.CENTER);
      //test code to determine what the full gameboard looks like to the GUI
      //game.setGridLinesVisible(true);
      return game;
   }
   
    //code to move tokens around the board
   public void moveToken(Pawn token, int start, int destination) {
      //check to make sure all spaces between start and destination are passable
      //check to make sure destination is landable
      //place token on space
   }
   
   public void start(Stage primaryStage) {
      StackPane board = new StackPane(boardBuilder(primaryStage));
      
      Scene scene = new Scene(board);
      primaryStage.setTitle("Parcheesi Board");
      primaryStage.setScene(scene);
      //default stage size since all items are based on window size
      primaryStage.setWidth(1280);
      primaryStage.setHeight(720);
      primaryStage.show();
   }
}