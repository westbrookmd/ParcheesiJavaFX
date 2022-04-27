package application;

//Class dictates properties of the tiles of the game board

import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

import java.util.ArrayList;

public class Tile extends StackPane {
	// create variables used by class. separate checks for safe spaces and
	// middle-lane spaces are needed because not all safe spaces are in middle
	// lanes. all mid-lane spaces are safe however
	private boolean blocked;
	private int tokenCount;
	private int tileNo;
	private int startFor;
	private boolean midLane;
	private boolean isSafe;
	private GridPane grid;

	protected ArrayList<Pawn> occupier;
	protected boolean occupied;
	protected Rectangle base;
	protected Color defFill;
	protected boolean active;
	private int rollValue;

	// default constructor for default spaces
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
		this.rollValue = 0;
		this.occupier = new ArrayList<Pawn>();

		this.base = new Rectangle();
		this.base.setFill(defFill);
		this.base.setStroke(defFill);

		this.grid = new GridPane();
		this.getChildren().addAll(base, grid);
	}
	
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

	public void setRollValue(int value) {
		this.rollValue = value;
	}

	public int getRollValue() {
		return this.rollValue;
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

	// method that will eventually determine whether or not a player can land on
	// this tile
	public boolean passable(Pawn token, boolean landing) {
		/*
		 * conditions needed for passable to be true:
		 * the space must have less than two pieces currently occupying it
		 * if the space is a safe space, players may pass it freely if it is not
		 * blockaded; if the player wishes to land there then there must not be any
		 * opposing players currently occupying the space;
		 * if the space is a mid-lane, it must match the player's color.
		 */
		if (this.blocked
				|| (landing && this.isSafe && tokenCount == 1
						&& token.getSpaceColor() != this.occupier.get(0).getSpaceColor())
				|| (this.midLane && this.base.getFill() != token.getSpaceColor())) {
			return false;
		}

		else {
			return true;
		}
	}

	// place tokens on a valid space
	public void placeToken(Pawn token) {
		if (this.occupied) {
			if (GridPane.getRowIndex(this.occupier.get(0).token) == 0
					&& GridPane.getColumnIndex(this.occupier.get(0).token) == 1) {
				grid.add(token.token, 0, 0);
			} else {
				grid.add(token.token, 1, 0);
			}
		} else {
			grid.add(token.token, 1, 0);
		}
		token.setLocation(this.tileNo);
		this.occupied = true;
		this.occupier.add(token);
		token.inStartingArea = false;
	}

	// method to remove tokens from tiles
	public void removeToken(Pawn token) {
		grid.getChildren().remove(token);
		this.occupier.remove(token);
		if (occupier.size() == 0) {
			this.occupied = false;
		}
	}

	// code to test numbering board spaces
	public void testTileCount() {
		Text test = new Text(0, 0, String.valueOf(tileNo));
		grid.add(test, 0, 0);
	}

	// code to alter the fill and stroke colors of the base rectangle
	public void setFill(Color color) {
		this.base.setFill(color);
	}

	public void setStroke(Color color) {
		this.base.setStroke(color);
	}

	public void resizeTile(int width, int height) {
		this.base.setWidth(width);
		this.base.setHeight(height);
	}
}