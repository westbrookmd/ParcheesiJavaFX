package application;

import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

public class Tile extends StackPane {
   //create variables used by class. separate checks for safe spaces and middle-lane spaces are needed because not all safe spaces are in middle lanes. all mid-lane spaces are safe however
   private boolean blocked;
   private int tokenCount;
   private GridPane grid;
   protected Rectangle base;
   protected int tileNo;
   protected boolean isMidLane;
   protected boolean isSafe;
      
   //variables for testing
   //private Circle[] testTokens;
   
   //default constructor for default spaces
   public Tile() {
      this.isSafe = false;
      this.isMidLane = false;
      this.blocked = false;
      this.tokenCount = 0;
      this.tileNo = 0;
      
      this.base = new Rectangle();
      this.base.setFill(Color.BLUE);
      this.base.setStroke(Color.BLACK);
      
      this.grid = new GridPane();
      this.getChildren().addAll(base, grid);
      
      // testTokens = new Circle[2];
//       for(int i = 0; i < testTokens.length; i++) {
//          testTokens[i] = new Circle();
//          testTokens[i].setStroke(Color.BLACK);
//          testTokens[i].setFill(Color.BLACK);
//          testTokens[i].setRadius(10);
//       }
   }
   
   //method that will eventually determine whether or not a player can land on this tile
   public void landable() {
/*    conditions needed for landable to be true:
               if the space is a mid-lane, it must match the player's color.
               if the space is a safe space, then there must not be any opposing players currently occupying the space
               the space must have less than two pieces currently occupying it               
               
*/
   }
   
   //method that will put a blockade on the current space
   public boolean block() {
/*
      check to see if there are two pieces of the same color currently on the space. if so, set blocked to true. if a blockade is broken, set blocked to true
      should be run twice when player moves their token; once for the space they land on, and once for the space they're leaving in order to break the blockade
*/
      if(tokenCount < 2){
         blocked = true;
      }
      
      else {
         blocked = false;
      }
      
      return blocked;
   }
   
   public void placeTokens(int tokens) {
//    code for testing method
//       for(int i = 0; i < tokens; i++) {
//          grid.add(testTokens[i], i, 0);
//          grid.setAlignment(Pos.CENTER);
//       }
   }

// code to test numbering board spaces   
   public void testTileCount() {
      Text test = new Text(0, 0, String.valueOf(tileNo));
      grid.add(test, 0, 0);
   }
   
   //code to alter the fill and stroke colors of the base rectangle
   public void setFill(Color color){
      this.base.setFill(color);
   }
   
   public void setStroke(Color color){
      this.base.setStroke(color);
   }
}