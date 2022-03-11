//class used to build the game board

import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class Board {
	private static final int BOARD_SIZE = 361;

	//int values for tile width and height, and circle radius
	private int width;
	private int height;
	private float radius;
	
	//colors for midlanes
	private static final Color LIGHTPURPLE = Color.rgb(203, 195, 227);
	private static final Color LIGHTORANGE = Color.rgb(255,165,0);

	//create panes for the board
	private StackPane board;
	private GridPane startSpaces;
	private GridPane cross;
	
	//create and initialize arrays for each space and for player tokens, create separate array to hold only the tiles that are actually seen by the player
	private Circle[] start;
	private Tile[] spaces;
	protected Tile[] gameTiles;
	public Pawn[] playerTokens;

	public Board() {
		this.width = 28;
		this.height = 29;
		this.radius = 9.5f;
		this.board = new StackPane();
		this.startSpaces = new GridPane();
		this.cross = new GridPane();
		this.start = new Circle[4];
		this.spaces = new Tile[BOARD_SIZE];
		this.gameTiles = new Tile[96];
		this.playerTokens = new Pawn[16];
	}

	public GridPane build() {
		// TODO: Pull these from the settings class
		//create player tokens
		for(int i = 0; i < playerTokens.length; i++) {
			if(i < 4) {
				playerTokens[i] = new Pawn(10, Color.PURPLE, LIGHTPURPLE);
			}
			if(i >= 4 && i < 8) {
				playerTokens[i] = new Pawn(10, Color.YELLOW, Color.KHAKI);
			}
			if(i >= 8 && i < 12) {
				playerTokens[i] = new Pawn(10, Color.ORANGE, LIGHTORANGE);
			}
			if(i >= 12) {
				playerTokens[i] = new Pawn(10, Color.GREEN, Color.PALEGREEN);
			}
		}
		
		//create starting spaces
		for(int i = 0; i < start.length; i++){
			start[i] = new Circle();
			start[i].setStroke(Color.BLACK);
			start[i].setRadius(100);
			//set the colors of starting spaces to match player colors. later on we'll need to determine extra colors so that pieces can be more easily seen against spaces with matching colors (for example, green pieces at green start)
			switch(i) {
			case 0: start[i].setFill(LIGHTPURPLE);
				break;
			case 1: start[i].setFill(Color.KHAKI);
				break;
			case 2: start[i].setFill(LIGHTORANGE);
				break;
			case 3: start[i].setFill(Color.PALEGREEN);
				break;
			}
		}

		//create all tiles used for the game
		for(int i = 0; i < spaces.length; i++){
			spaces[i] = new Tile();
			//space size based on multiple of window sizes
			spaces[i].base.setWidth(50);
			spaces[i].base.setHeight(25);
		}

		//create the cross shape
		int col = 0;
		int row = 0;
		int tileNo = 1;
		int tileCount = 0;

		for(int i = 0; i < BOARD_SIZE; i++){
			cross.add(spaces[i], col, row);
			if(col == 18) {
				col = 0;
				row++;
			}
			else {
				col++;
			}
			
			//make all the main cross-shaped part of the board visible
			if(cross.getColumnIndex(spaces[i]) > 7 && cross.getColumnIndex(spaces[i]) < 11) {
				if(cross.getRowIndex(spaces[i]) < 8 || cross.getRowIndex(spaces[i]) > 10) {
					spaces[i].setStroke(Color.BLACK);
					spaces[i].setFill(Color.AZURE);
					spaces[i].defFill = Color.AZURE;
					spaces[i].tileNo = tileNo;
					gameTiles[tileCount] = spaces[i];
					tileNo++;
					tileCount++;
				}
			}
			
			else if(cross.getRowIndex(spaces[i]) > 7 && cross.getRowIndex(spaces[i]) < 11) {
				if(cross.getColumnIndex(spaces[i]) < 8 || cross.getColumnIndex(spaces[i]) > 10) {
					spaces[i].setStroke(Color.BLACK);
					spaces[i].setFill(Color.AZURE);
					spaces[i].defFill = Color.AZURE;
					spaces[i].tileNo = tileNo;
					gameTiles[tileCount] = spaces[i];
					tileNo++;
					tileCount++;
				}
			}
		}
		
		//give midlane tiles midlane status and number before numbering main tiles since they'll need a special numbering system
		//midlane numbering starts at 69 since there is a total of 68 tiles outside of the midlanes
		tileNo = 69;
		for(int i = 0; i < gameTiles.length; i++) {
			if(cross.getColumnIndex(gameTiles[i]) == 9) {
				if(cross.getRowIndex(gameTiles[i]) <= 7) {
					gameTiles[i].setFill(LIGHTPURPLE);
					gameTiles[i].defFill = LIGHTPURPLE;
					gameTiles[i].isSafe = true;
					if(cross.getRowIndex(gameTiles[i]) != 0) {
						if(tileNo > 75) {
							tileNo = 69;
						}
						gameTiles[i].isMidLane = true;
						gameTiles[i].tileNo = tileNo;
						tileNo++;
					}
				}
				else if(cross.getRowIndex(gameTiles[i]) >= 11) {
					gameTiles[i].setFill(Color.PALEGREEN);
					gameTiles[i].defFill = Color.PALEGREEN;
					gameTiles[i].isSafe = true;
					if(cross.getRowIndex(gameTiles[i]) != 18) {
						if(tileNo < 69) {
							tileNo = 75;
						}
						gameTiles[i].isMidLane = true;
						gameTiles[i].tileNo = tileNo;
						tileNo--;
					}
				}
			}

			if(cross.getRowIndex(gameTiles[i]) == 9) {
				if(cross.getColumnIndex(gameTiles[i]) <= 7) {
					gameTiles[i].setFill(LIGHTORANGE);
					gameTiles[i].defFill = LIGHTORANGE;
					gameTiles[i].isSafe = true;
					if(cross.getColumnIndex(gameTiles[i]) != 0) {
						if(tileNo > 75) {
							tileNo = 69;
						}
						gameTiles[i].isMidLane = true;
						gameTiles[i].tileNo = tileNo;
						tileNo++;
					}
				}
				else if(cross.getColumnIndex(gameTiles[i]) >= 11) {
					gameTiles[i].setFill(Color.KHAKI);
					gameTiles[i].defFill = Color.KHAKI;
					gameTiles[i].isSafe = true;
					if(cross.getColumnIndex(gameTiles[i]) != 18) {
						if(tileNo > 75) {
							tileNo = 75;
						}
						gameTiles[i].isMidLane = true;
						gameTiles[i].tileNo = tileNo;
						tileNo--;
					}
				}
			}
		}
		
		//test tile counting before proper tile numbering system is established
		//total of 68 tiles counted not including midlanes
//		for(int i = 0; i < gameTiles.length; i++) {
//			if(!gameTiles[i].isMidLane) {
//				gameTiles[i].tileNo = tileNo;
//				gameTiles[i].testTileCount();
//				tileNo++;
//			}
//		}

		//number the tiles so that they go in chronological order from 1 to 68, looping back to 1. tile 1 will be the top-left tile in the upper arm of the cross
		Tile currentTile;
		tileNo = 1;
		int reverseTileNo = 8;
		
		//number spaces in first column of upper arm
		for(int i = 0; i < 8; i++) {
			currentTile = getTile(cross, 8, i);
			currentTile.tileNo = tileNo;
			tileNo++;
			reverseTileNo++;
		}
		
		//number normal spaces in left arm
		for(int i = 8; i < 11; i++) {
			for(int j = 0; j < 8; j++) {
				currentTile = getTile(cross, j, i);
				if(cross.getRowIndex(currentTile) == 8) {
					currentTile.tileNo = reverseTileNo;
					reverseTileNo--;
					tileNo++;
				}
				else {
					if(!currentTile.isMidLane) {
						currentTile.tileNo = tileNo;
						tileNo++;
					}
				}
			}
		}
		
		//number normal spaces in lower arm
		reverseTileNo = 33;
		for(int i = 8; i < 11; i++) {
			for(int j = 11; j < 19; j++) {
				currentTile = getTile(cross, i, j);
				if(cross.getColumnIndex(currentTile) < 10 && !currentTile.isMidLane) {
					currentTile.tileNo = tileNo;
					tileNo++;
					reverseTileNo++;
				}
				else if(cross.getColumnIndex(currentTile) == 10) {
					currentTile.tileNo = reverseTileNo;
					tileNo++;
					reverseTileNo--;
				}
			}
		}
		
		//number normal spaces in right arm
		reverseTileNo = 59;
		for(int i = 8; i < 11; i++) {
			for(int j = 11; j < 19; j++) {
				currentTile = getTile(cross, j, i);
				if(cross.getRowIndex(currentTile) == 8) {
					currentTile.tileNo = reverseTileNo;
					reverseTileNo--;
				}
				else {
					if(!currentTile.isMidLane) {
						if(cross.getRowIndex(currentTile) == 9) {
							currentTile.tileNo = 51;
						}
						else {
						currentTile.tileNo = tileNo;
						tileNo++;
						}
					}
				}
			}
		}
		
		//number normal spaces in remainder of upper arm
		reverseTileNo = 67;
		for(int i = 9; i < 11; i++) {
			for(int j = 0; j < 8; j++) {
				currentTile = getTile(cross, i, j);
				if(cross.getColumnIndex(currentTile) == 9) {
					if(!currentTile.isMidLane) {
						currentTile.tileNo = 68;
					}
				}
				else {
					currentTile.tileNo = reverseTileNo;
					reverseTileNo--;
				}
			}
		}
		
		for(int i = 0; i < gameTiles.length; i++) {
			gameTiles[i].testTileCount();
		}
		
		//draw the board
		Rectangle center = new Rectangle();
		//center size based on multiple of window size
		center.setWidth(50 * 3);
		//center size based on multiple of window size
		center.setHeight(25 * 3);
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
		board.getChildren().addAll(startSpaces, cross);
		startSpaces.setAlignment(Pos.CENTER);
		cross.setAlignment(Pos.CENTER);

		//test placing tokens
		//spaces[8].placeTokens(2);
		//spaces[8].setStroke(Color.RED);

		GridPane game = new GridPane();
		game.add(board, 0, 0);
		game.setAlignment(Pos.CENTER);
		game.setGridLinesVisible(true); //test code to determine what the full gameboard looks like to the GUI
		return game;
	}
	
	//search for specific children within a gridpane, used for number the tiles of the main area of the cross board
	private Tile getTile(GridPane pane, int col, int row) {
		for(Node node : pane.getChildren()) {
			if(pane.getColumnIndex(node) == col && pane.getRowIndex(node) == row) {				
				return (Tile)node;
			}
		}
		return null;
	}
	
	//move tokens around the board
	public void moveToken(Pawn token, int start, int destination) {
		int x = 0;
		for(int i = 0; i < gameTiles.length; i++) {
			if(gameTiles[i].tileNo == destination) {
				gameTiles[i].placeToken(token);
				x++;
			}
			if(gameTiles[i].tileNo == start) {
				gameTiles[i].removeToken();
				x++;
			}
			if(x == 2) {
				break;
			}
		}
	}
}