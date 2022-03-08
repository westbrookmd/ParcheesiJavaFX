//Tile class programmed by Christopher Smith
//Class dictates properties of individual tiles on the game board

import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

public class Tile extends StackPane {
   //create variables used by class. separate checks for safe spaces and middle-lane spaces are needed because not all safe spaces are in middle lanes. all mid-lane spaces are safe however
   private boolean blocked;
   private int tokenCount;
   private Pawn occupier;
   private GridPane grid;
   protected Rectangle base;
   protected int tileNo;
   protected boolean isMidLane;
   protected boolean isSafe;
   
   //default constructor for default spaces
   public Tile() {
      this.isSafe = false;
      this.isMidLane = false;
      this.blocked = false;
      this.tokenCount = 0;
      this.tileNo = 0;
      this.occupier = null;
      
      this.base = new Rectangle();
      this.base.setFill(Color.BLUE);
      this.base.setStroke(Color.BLACK);
      
      this.grid = new GridPane();
      this.getChildren().addAll(base, grid);
   }
   
   //method that will eventually determine whether or not a player can land on this tile
   public boolean passable(Pawn token, boolean landing) {
/*    conditions needed for passable to be true:
               the space must have less than two pieces currently occupying it
               if the space is a safe space, players may pass it freely if it is not blockaded; if the player wishes to land there then there must not be any opposing players currently occupying the space;
               if the space is a mid-lane, it must match the player's color.
*/
      if(this.blocked || (landing && this.isSafe && tokenCount == 1 && token.spaceColor != this.occupier.spaceColor) || (this.isMidLane && this.base.getFill() != token.spaceColor)) {
         return false;
      }
      else {
         return true;
      }
   }
   
   //place tokens on a valid space
   public void placeToken(Pawn token) {
      if(this.tokenCount < 1) {
         this.occupier = token;
         this.tokenCount = 1;
	   }
	   else if(this.occupier.spaceColor != token.spaceColor) {
		   //capture the token
		   //grid.add(token.token, 0, 0);
	   }
	   else {
		   this.tokenCount = 2;
		   //grid.add(token.token, 0, 1);
		   this.block();
	   }
   }
   
   //method that will put a blockade on the current space
   public boolean block() {
/*
    check to see if there are two pieces of the same color currently on the space. if so, set blocked to true. if a blockade is broken, set blocked to true
    should be run twice when player moves their token; once for the space they land on, and once for the space they're leaving in order to break the blockade
*/
      if(this.tokenCount == 2){
         this.blocked = true;
      }
    
      else {
         this.blocked = false;
      }
    
      return this.blocked;
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