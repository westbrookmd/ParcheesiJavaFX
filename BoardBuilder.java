//BoardBuilder programmed by Christopher Smith
//Use this to create the game board

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.beans.*;

public class BoardBuilder extends Application {

   //initialize constants used to count total number of spaces (including invisible), as well as the width and height of a single space
   private static final int BOARD_SIZE = 361;
   private static final int WIDTH = 50;
   private static final int HEIGHT = 30;
   
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
         start[i].setStroke(Color.BLACK);
         //start[i].setFill(Color.PINK);
         start[i].setRadius(125);
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
         spaces[i] = new Rectangle(0,0, WIDTH, HEIGHT);
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
      
      //add transparent dividers to board to allow start spaces to be properly added to board, as well as a piece to be added to the center
      Rectangle vDiv1 = new Rectangle(0, 0, (WIDTH * 3), (HEIGHT * 8.23));
      Rectangle vDiv2 = new Rectangle(0, 0, (WIDTH * 3), (HEIGHT * 8.23));
      Rectangle hDiv = new Rectangle(0, 0, (WIDTH * 3), (HEIGHT * 3.07));
      Rectangle center = new Rectangle(0, 0, (WIDTH * 3), (HEIGHT * 3));
      
      vDiv1.setStroke(Color.TRANSPARENT);
      vDiv1.setFill(Color.TRANSPARENT);
      vDiv1.setStrokeWidth(5); 
      
      vDiv2.setStroke(Color.TRANSPARENT);     
      vDiv2.setFill(Color.TRANSPARENT);
      vDiv2.setStrokeWidth(5); 
      
      hDiv.setStroke(Color.TRANSPARENT);
      hDiv.setFill(Color.TRANSPARENT);
      hDiv.setStrokeWidth(5);
      
      center.setStroke(Color.RED);
      center.setFill(Color.LIGHTBLUE);
      center.setStrokeWidth(5);
      
      startSpaces.add(vDiv1, 2, 0);
      startSpaces.add(hDiv, 0, 1);
      startSpaces.add(vDiv2, 1, 2);      
      startSpaces.add(center, 2, 1);
      
      //add start spaces to board
      startSpaces.add(start[0], 1, 0);
      startSpaces.add(start[1], 3, 0);
      startSpaces.add(start[2], 1, 2);
      startSpaces.add(start[3], 3, 2);
      
      //add board pieces to stackpane
      board.getChildren().addAll(cross, startSpaces);
      cross.setAlignment(Pos.CENTER);
   
      Scene scene = new Scene(board);
      primaryStage.setTitle("Parcheesi Board");
      primaryStage.setScene(scene);
      primaryStage.show();
   }
}