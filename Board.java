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
	private static final int CROSS_SIZE = 96;

	//colors used in the game for player tokens, starting/midlane spaces, and safe spaces
	private static final Color PURPLE = Color.PURPLE;
	private static final Color LIGHTPURPLE = Color.rgb(203, 195, 227);
	private static final Color ORANGE = Color.ORANGE;
	private static final Color LIGHTORANGE = Color.rgb(255,165,0);
	private static final Color GREEN = Color.GREEN;
	private static final Color PALEGREEN = Color.PALEGREEN;
	private static final Color YELLOW = Color.YELLOW;
	private static final Color KHAKI = Color.KHAKI;
	private static final Color SAFE = Color.PAPAYAWHIP; //TODO: Determine a good color to use for safe spaces that aren't starting spaces or midlane entries

	//int values for tile width and height, and circle radius
	private int width;
	private int height;
	private float radius;

	//create panes for the board
	private StackPane board;
	private GridPane startSpaces;
	private GridPane cross;
	private GridPane game;

	//create and initialize arrays for each space and for player tokens
	//create separate array to hold only the tiles that are actually seen by the player
	//create special array for midlane tiles to make it easier to sort them out later on
	private Circle[] start;
	private Tile[] spaces;
	private Tile[] midLanes;
	protected Tile[] gameTiles;
	protected Player[] players;

	//class constructor
	public Board() {
		this.width = 50;
		this.height = 25;
		this.radius = 100f;
		this.board = new StackPane();
		this.startSpaces = new GridPane();
		this.cross = new GridPane();
		this.game = new GridPane();
		this.start = new Circle[4];
		this.spaces = new Tile[BOARD_SIZE];
		this.gameTiles = new Tile[CROSS_SIZE];
		this.midLanes = new Tile[28];
		this.players = new Player[4];
	}

	//build the game board, including creating the main shape of the board, setting start spaces, and setting tokens on board
	public GridPane build() {

		//create player tokens
		//TODO: setup gameplay so that players go counter-clockwise.
		//TODO: take input from start screen and apply it here
		players[0] = new Player(PURPLE, LIGHTPURPLE);
		players[1] = new Player(ORANGE, LIGHTORANGE);
		players[2] = new Player(GREEN, PALEGREEN);
		players[3] = new Player(YELLOW, KHAKI);

		//create starting spaces
		for(int i = 0; i < start.length; i++){
			start[i] = new Circle();
			start[i].setStroke(Color.BLACK);
			//set the colors of starting spaces to match player colors.
			//TODO: rewrite this so it makes sense, move code to its own method
			switch(i) {
			case 0: start[i].setFill(players[0].getColor());
			break;
			case 1: start[i].setFill(players[3].getColor());
			break;
			case 2: start[i].setFill(players[1].getColor());
			break;
			case 3: start[i].setFill(players[2].getColor());
			break;
			}
		}

		//create all tiles used for the game
		for(int i = 0; i < spaces.length; i++){
			spaces[i] = new Tile();
		}

		//first-time setup stuff
		resizeBoard(width, height, radius);
		buildCross(cross);	
		setTiles();

		//test code for checking tile numbering system
		for(int i = 0; i < gameTiles.length; i++) {
			gameTiles[i].testTileCount();
		}

		//draw the home space of the board
		//TODO: create new method for this
		Rectangle center = new Rectangle();
		//center size based on multiple of window size
		center.setWidth(this.width * 3);
		//center size based on multiple of window size
		center.setHeight(this.height * 3);
		center.setStroke(Color.RED);
		center.setFill(Color.LIGHTBLUE);
		center.setStrokeWidth(5);

		//add start spaces and home space to board
		startSpaces.add(center, 1, 1);
		startSpaces.add(start[0], 0, 0);
		startSpaces.add(start[1], 2, 0);
		startSpaces.add(start[2], 0, 2);
		startSpaces.add(start[3], 2, 2);

		//add board pieces to stackpane
		//put start spaces before cross so circles don't cover the cross with an odd window size
		board.getChildren().addAll(startSpaces, cross);
		startSpaces.setAlignment(Pos.CENTER);
		cross.setAlignment(Pos.CENTER);

		game.add(board, 0, 0);
		game.setAlignment(Pos.CENTER);
		//TODO: Create a border around the game board? or would it be better to do so in the GUI?
		//game.setGridLinesVisible(true); //test code to determine what the full game board looks like to the GUI
		return game;
	}

	//search for specific children within a gridpane, used for numbering the tiles of the main area of the cross board
	private Tile getTile(GridPane pane, int col, int row) {
		for(Node node : pane.getChildren()) {
			if(GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {				
				return (Tile)node;
			}
		}
		return null;
	}

	//create the cross-shaped part of the board that players will play on
	private GridPane buildCross(GridPane cross) {
		int col = 0;
		int row = 0;
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

			//make all the tiles in the main cross-shaped part of the board visible
			if(GridPane.getColumnIndex(spaces[i]) > 7 && GridPane.getColumnIndex(spaces[i]) < 11) {
				if(GridPane.getRowIndex(spaces[i]) < 8 || GridPane.getRowIndex(spaces[i]) > 10) {
					spaces[i].setStroke(Color.BLACK);
					spaces[i].setFill(Color.AZURE);
					spaces[i].defFill = Color.AZURE;
					gameTiles[tileCount] = spaces[i];
					tileCount++;
				}
			}
			else if(GridPane.getRowIndex(spaces[i]) > 7 && GridPane.getRowIndex(spaces[i]) < 11) {
				if(GridPane.getColumnIndex(spaces[i]) < 8 || GridPane.getColumnIndex(spaces[i]) > 10) {
					spaces[i].setStroke(Color.BLACK);
					spaces[i].setFill(Color.AZURE);
					spaces[i].defFill = Color.AZURE;
					gameTiles[tileCount] = spaces[i];
					tileCount++;
				}
			}
		}

		return cross;
	}

	//set the tileNo for all tiles, give midlane and safe status to tiles that need it
	private void setTiles() {
		Tile currentTile;
		int tileNo = 69;
		int reverseTileNo = 8;
		int k = 0;

		//give midlane tiles midlane status, set color, set tileNo
		for(int i = 0; i < gameTiles.length; i++) {
			if(GridPane.getColumnIndex(gameTiles[i]) == 9) {
				if(GridPane.getRowIndex(gameTiles[i]) <= 7) {
					gameTiles[i].setFill(LIGHTPURPLE);
					gameTiles[i].defFill = LIGHTPURPLE;
					gameTiles[i].setIsSafe(true);
					if(GridPane.getRowIndex(gameTiles[i]) != 0) {
						gameTiles[i].setMidLane(true);
						gameTiles[i].setTileNo(tileNo);
						tileNo++;
						midLanes[k] = gameTiles[i];
						k++;
					}
				}
				else if(GridPane.getRowIndex(gameTiles[i]) >= 11) {
					gameTiles[i].setFill(PALEGREEN);
					gameTiles[i].defFill = PALEGREEN;
					gameTiles[i].setIsSafe(true);
					if(GridPane.getRowIndex(gameTiles[i]) != 18) {
						if(GridPane.getRowIndex(gameTiles[i]) != 18) {
							gameTiles[i].setMidLane(true);
							gameTiles[i].setTileNo(tileNo);
							tileNo++;
							midLanes[k] = gameTiles[i];
							k++;
						}
					}
				}
			}

			if(GridPane.getRowIndex(gameTiles[i]) == 9) {
				if(GridPane.getColumnIndex(gameTiles[i]) <= 7) {
					gameTiles[i].setFill(LIGHTORANGE);
					gameTiles[i].defFill = LIGHTORANGE;
					gameTiles[i].setIsSafe(true);
					if(GridPane.getColumnIndex(gameTiles[i]) != 0) {
						gameTiles[i].setMidLane(true);
						gameTiles[i].setTileNo(tileNo);
						tileNo++;
						midLanes[k] = gameTiles[i];
						k++;
					}
				}
				else if(GridPane.getColumnIndex(gameTiles[i]) >= 11) {
					gameTiles[i].setFill(KHAKI);
					gameTiles[i].defFill = KHAKI;
					gameTiles[i].setIsSafe(true);
					if(GridPane.getColumnIndex(gameTiles[i]) != 18) {
						gameTiles[i].setMidLane(true);
						gameTiles[i].setTileNo(tileNo);
						tileNo++;
						midLanes[k] = gameTiles[i];
						k++;
					}
				}
			}
		}

		for(int i = 0; i < midLanes.length; i++) {
			for(int j = 0; j < midLanes.length; j++) {
				if(midLanes[i].getTileNo() > midLanes[j].getTileNo()) {
					if((midLanes[i].defFill == PALEGREEN && midLanes[j].defFill == PALEGREEN) || midLanes[i].defFill == KHAKI && midLanes[j].defFill == KHAKI) {
						tileNo = midLanes[i].getTileNo();
						midLanes[i].setTileNo(midLanes[j].getTileNo());
						midLanes[j].setTileNo(tileNo);
					}
				}
			}
		}

		//number the tiles so that they go in chronological order from 1 to 68, looping back to 1. tile 1 will be the top-left tile in the upper arm of the cross
		tileNo = 1;

		//number spaces in first column of upper arm
		for(int i = 0; i < 8; i++) {
			currentTile = getTile(cross, 8, i);
			currentTile.setTileNo(tileNo);
			tileNo++;
			reverseTileNo++;
		}

		//number normal spaces in left arm
		for(int i = 8; i < 11; i++) {
			for(int j = 0; j < 8; j++) {
				currentTile = getTile(cross, j, i);
				if(GridPane.getRowIndex(currentTile) == 8) {
					currentTile.setTileNo(reverseTileNo);
					reverseTileNo--;
					tileNo++;
				}
				else {
					if(!currentTile.getMidLane()) {
						currentTile.setTileNo(tileNo);
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
				if(GridPane.getColumnIndex(currentTile) < 10 && !currentTile.getMidLane()) {
					currentTile.setTileNo(tileNo);
					tileNo++;
					reverseTileNo++;
				}
				else if(GridPane.getColumnIndex(currentTile) == 10) {
					currentTile.setTileNo(reverseTileNo);
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
				if(GridPane.getRowIndex(currentTile) == 8) {
					currentTile.setTileNo(reverseTileNo);
					reverseTileNo--;
				}
				else {
					if(!currentTile.getMidLane()) {
						if(GridPane.getRowIndex(currentTile) == 9) {
							currentTile.setTileNo(51);
						}
						else {
							currentTile.setTileNo(tileNo);
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
				if(GridPane.getColumnIndex(currentTile) == 9) {
					if(!currentTile.getMidLane()) {
						currentTile.setTileNo(68);
					}
				}
				else {
					currentTile.setTileNo(reverseTileNo);
					reverseTileNo--;
				}
			}
		}

		//sort tiles to make it easier to determine where players can move to
		for(int i = 0; i < gameTiles.length; i++){
			for(int j = i + 1; j < gameTiles.length; j++) {
				if(gameTiles[i].getTileNo() > gameTiles[j].getTileNo()) {
					currentTile = gameTiles[i];
					gameTiles[i] = gameTiles[j];
					gameTiles[j] = currentTile;
				}
			}
		}

		//set safe space and startFor status for starting spaces
		for(int i = 4; i < gameTiles.length; i += 17) {
			if(!gameTiles[i].getMidLane()) {
				gameTiles[i].setFill(players[i / 17].getColor());
				gameTiles[i].setIsSafe(true);
				gameTiles[i].setStartFor(i / 17);
			}
		}

		//set safe space status for non-starting, non-midlane spaces
		for(int i = 11; i < gameTiles.length; i += 17) {
			if(!gameTiles[i].getMidLane()) {
				gameTiles[i].setFill(SAFE);
				gameTiles[i].setIsSafe(true);
			}
		}
	}

	//TODO: draw the Home space	
	private void buildHome() {
		//TODO: flesh out method
		//Home space should take up entire center portion of board, the point where each arm of the cross meets
		//Should have diagonal dividers to split Home into four sections: one for each arm of the cross
		//Each section should be able to hold four tokens
	}

	//TODO: draw the starting spaces
	private void drawStart() {

	}

	//resize board to match game window size
	public void resizeBoard(int width, int height, float radius) {
		//resize the board tiles, including invisible tiles
		for(int i = 0; i < spaces.length; i++) {
			spaces[i].resizeTile(width, height);
		}

		//resize start spaces
		for(int i = 0; i < start.length; i++) {
			start[i].setRadius(radius);
		}

		//resize pawns
		//TODO: figure out what size radius the pawns should be set to

		//TODO: reposition start spaces if needed. ideally, they should be connected to the first tile players can move onto from the start spaces (tiles 5, 22, 39, and 56)
	}

	//move tokens around the board
	//@param token indicates which token must be moved, dest represents tileNo for destination tile
	public void moveToken(Pawn token, int dest) {
		//token is currently at starting circle
		if(token.getLocation() == -1) {
			//remove token from starting circle
		}
		//token is not at start currently
		else {
			gameTiles[token.getLocation()].removeToken(token);
			//determine if token is going back to start or not
			if(dest == -1) {
				//send token to starting circle
			}
			else {
				//check to see if the destination is occupied
				//use index dest - 1 since array starts at index 0
				if(gameTiles[dest - 1].occupied) {
					//check to see who is occupying the destination tile
					if(gameTiles[dest - 1].occupier.getSpaceColor() == token.getSpaceColor()) {
						//create blockade
					}
					else {
						//the token occupying the space does not belong to the player, send the current occupier back to start
						//TODO: check needs to be done to make sure the tile isn't a safe space which prevents capturing, should probably be done in moveIndicator() method
						moveToken(gameTiles[dest - 1].occupier, -1);
						gameTiles[dest - 1].placeToken(token);
					}
				}
				else {
					gameTiles[dest - 1].placeToken(token);
				}
			}
		}
		//TODO: place tokens in starting spaces
	}

	//display spaces the selected token may move to
	public void displayMoves(Player player, int pawn, int roll1, int roll2) {
		Pawn clickedPawn = player.getToken(pawn);
		//TODO: figure out how to code this method so it displays valid spaces the player may move to
		//TODO: Add check for blockade
		//TODO: Call board refresh method

		//TODO: Add LastGameTileNum (or something more descriptive) to the Player class to reduce complexity
		int player0LastGameTileNum = 68;
		int player1LastGameTileNum = 17;
		int player2LastGameTileNum = 34;
		int player3LastGameTileNum = 51;

		//Naive calculation for location and then check if we overshoot our personal route
		int firstRollLocation = (clickedPawn.getLocation()  + roll1);
		boolean firstRollSendsToPersonalRoute;
		if (clickedPawn.getLocation() + firstRollLocation > player0LastGameTileNum)
		{
			firstRollSendsToPersonalRoute = true;
			//calculates what location should be highlighted on the individual player's route (colored tiles)
			firstRollLocation = (clickedPawn.getLocation() + firstRollLocation) - player0LastGameTileNum;
		}

		int secondRollLocation = (clickedPawn.getLocation() + roll2);
		boolean secondRollSendsToPersonalRoute;
		if (clickedPawn.getLocation() + secondRollLocation > player0LastGameTileNum)
		{
			secondRollSendsToPersonalRoute = true;
			secondRollLocation = (clickedPawn.getLocation() + secondRollLocation) - player0LastGameTileNum;
		}
		int combinedRollLocation = (clickedPawn.getLocation() + roll1 + roll2);
		boolean combinedRollSendsToPersonalRoute;
		if (clickedPawn.getLocation() + combinedRollLocation > player0LastGameTileNum)
		{
			combinedRollSendsToPersonalRoute = true;
			combinedRollLocation = (clickedPawn.getLocation() + combinedRollLocation) - player0LastGameTileNum;
		}
		
		//TODO: Use roll location boolean values
		// and LastGameTileNum to determine whether we are coloring a game tile or a personal route
		gameTiles[firstRollLocation].base.setFill(Color.RED);
		gameTiles[firstRollLocation].active = true;
		gameTiles[secondRollLocation].base.setFill(Color.RED);
		gameTiles[secondRollLocation].active = true;
		gameTiles[combinedRollLocation].base.setFill(Color.RED);
		gameTiles[combinedRollLocation].active = true;
		//the trickiest part will be figuring out how to loop from space 68 to space 1
		//determine how to display when the player can enter their mid-lanes
		//determine how to display when play can enter HOME
		//determine how to check if player is at start. If so, check to see if player can leave start.
		}
	}
