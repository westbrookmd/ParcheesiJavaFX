//BoardBuilder programmed by Christopher Smith
//Use this to create the game board

import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.beans.*;

public class BoardBuilder extends Application {

   //initialize constants used to count total number of spaces (including invisible), as well as the width and height of a single space
   private static final int BOARD_SIZE = 361;
   private static final int SPACE_WIDTH_MULTIPLE = 22;
   private static final int SPACE_HEIGHT_MULTIPLE = 23;
   private static final float CIRCLE_RADIUS_MULTIPLE = 5.5f;
   
   public void start(Stage primaryStage) {
   
      //create panes for the boards
      StackPane board = new StackPane();
      GridPane startSpaces = new GridPane();
      startSpaces.setPadding(new Insets(10));
      GridPane cross = new GridPane();
      cross.setPadding(new Insets(10));
      
      //create and initialize arrays for each space
      Circle[] start = new Circle[4];
      Rectangle[] spaces = new Rectangle[BOARD_SIZE];
      
      for(int i = 0; i < start.length; i++){
         start[i] = new Circle();
         //circle size based on multiple of window size
         start[i].radiusProperty().bind(Bindings.min(primaryStage.widthProperty().divide(CIRCLE_RADIUS_MULTIPLE), primaryStage.heightProperty().divide(CIRCLE_RADIUS_MULTIPLE)));
         start[i].setStroke(Color.BLACK);
         //start[i].setFill(Color.PINK);
         //set the colors of starting spaces to match player colors. later on we'll need to determine extra colors so that pieces can be more easily seen against spaces with matching colors (for example, green pieces at green start)
         switch(i) {
            case 0: start[i].setFill(Color.PURPLE); break;
            case 1: start[i].setFill(Color.YELLOW); break;
            case 2: start[i].setFill(Color.ORANGE); break;
            case 3: start[i].setFill(Color.GREEN); break;
         }
      }
      
      //set default color for visible spaces
      for(int i = 0; i < spaces.length; i++){
         spaces[i] = new Rectangle();
         //space size based on multiple of window size
         spaces[i].widthProperty().bind(primaryStage.widthProperty().divide(SPACE_WIDTH_MULTIPLE));
         spaces[i].heightProperty().bind(primaryStage.heightProperty().divide(SPACE_HEIGHT_MULTIPLE));
         spaces[i].setStroke(Color.BLACK);
         spaces[i].setFill(Color.BEIGE);
      }
      
      //create the cross shape
      int col = 0;
      int row = 0;
      
      for(int i = 0; i < BOARD_SIZE; i++){
      
         //turn all spaces that are outside of the board invisible
         cross.add(spaces[i], col, row);
         if((col < 8 && (row < 8 || row >= 11)) || (col >= 11 && (row < 8 || row >= 11))){
            spaces[i].setStroke(Color.TRANSPARENT);
            spaces[i].setFill(Color.TRANSPARENT);
         }
         
         if((col > 7 && col < 11) && (row > 7 && row < 11)) {
            spaces[i].setStroke(Color.TRANSPARENT);
            spaces[i].setFill(Color.TRANSPARENT);
         }
         
         //change the colors of center lanes
         if(col == 9) {
            if(row <= 7) {
               spaces[i].setFill(Color.PURPLE);
            }
            else if(row >= 11) {
               spaces[i].setFill(Color.GREEN);
            }
         }
         
         if(row == 9) {
            if(col <= 7) {
               spaces[i].setFill(Color.ORANGE);
            }
            else if(col >= 11) {
               spaces[i].setFill(Color.YELLOW);
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
   
      Scene scene = new Scene(board);
      primaryStage.setTitle("Parcheesi Board");
      primaryStage.setScene(scene);
      //default stage size since all items are based on window size
      primaryStage.setWidth(600);
      primaryStage.setHeight(600);
      primaryStage.show();
   }
}