//Tile class programmed by Christopher Smith
//Class dictates properties of the game board

import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

public class Tile extends StackPane {
	//create variables used by class. separate checks for safe spaces and middle-lane spaces are needed because not all safe spaces are in middle lanes. all mid-lane spaces are safe however
	private boolean blocked;
	private int tokenCount;
	private int tileNo;
	private int startFor;
	private boolean midLane;
	private boolean isSafe;
	private GridPane grid;
	
	protected Pawn occupier;
	protected boolean occupied;	
	protected Rectangle base;	
	protected Color defFill;
	protected boolean active;

	//default constructor for default spaces
	public Tile() {
		this.isSafe = false;
		this.midLane = false;
		this.blocked = false;
		this.tokenCount = 0;
		this.tileNo = 0;
		this.startFor = -1;
		this.occupier = null;
		this.defFill = Color.TRANSPARENT;
		this.occupied = false;
		this.active = false;

		this.base = new Rectangle();
		this.base.setFill(defFill);
		this.base.setStroke(defFill);

		this.grid = new GridPane();
		this.getChildren().addAll(base, grid);
	}
	
	//getter and setter methods
	
	public int getTileNo() {
		return this.tileNo;
	}
	
	public void setTileNo(int num) {
		this.tileNo = num;
	}
	
	public boolean getMidLane() {
		return this.midLane;
	}
	
	public void setMidLane(boolean status) {
		this.midLane = status;
	}
	
	public boolean getIsSafe() {
		return this.isSafe;
	}
	
	public void setIsSafe(boolean status) {
		this.isSafe = status;
	}
	
	public int getStartFor() {
		return this.startFor;
	}
	
	public void setStartFor(int player) {
		this.startFor = player;
	}

	//method that will eventually determine whether or not a player can land on this tile
	public boolean passable(Pawn token, boolean landing) {
		/*    conditions needed for passable to be true:
             the space must have less than two pieces currently occupying it
             if the space is a safe space, players may pass it freely if it is not blockaded; if the player wishes to land there then there must not be any opposing players currently occupying the space;
             if the space is a mid-lane, it must match the player's color.
		 */
		
		//TODO: Rewrite this method so it only looks to see if the tile is passable. Potentially write new method to check who is currently on the tile when landing
		if(this.blocked || (landing && this.isSafe && tokenCount == 1 && token.getSpaceColor() != this.occupier.getSpaceColor()) || (this.midLane && this.base.getFill() != token.getSpaceColor())) {
			return false;
		}

		else {
			return true;
		}
	}

	//place tokens on a valid space
	public void placeToken(Pawn token) {
		if(this.occupied) {
			grid.add(token, 0, 2);
		}
		else {
			grid.add(token, 0, 1);
		}
	}

	//method to remove tokens from tiles
	public void removeToken(Pawn token) {
		grid.getChildren().remove(token);
	}

	//code to test numbering board spaces   
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
	
	public void resizeTile(int width, int height) {
		this.base.setWidth(width);
		this.base.setHeight(height);
	}
}