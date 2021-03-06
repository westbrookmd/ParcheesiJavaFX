import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
	private static final int BOARD_SIZE = 361;
	private static final int CROSS_SIZE = 96;

	//colors used in the game for player tokens, starting/midlane spaces, and safe spaces
	private static final Color PURPLE = Color.PURPLE;
	private static final Color LIGHTPURPLE = Color.rgb(203, 195, 227);
	private static final Color DARKORANGE = Color.DARKORANGE;
	private static final Color LIGHTORANGE = Color.rgb(255, 179, 138);
	private static final Color GREEN = Color.GREEN;
	private static final Color PALEGREEN = Color.PALEGREEN;
	private static final Color YELLOW = Color.YELLOW;
	private static final Color KHAKI = Color.KHAKI;
	private static final Color SAFE = Color.PAPAYAWHIP;

	//int values for tile width and height, and circle radius
	private int width;
	private int height;
	private float radius;

	//create panes for the board
	private StackPane board;
	private GridPane startLayer;
	private GridPane cross;
	private GridPane game;
	protected HomeTile center;

	//create and initialize arrays for each space and for player tokens
	//create separate array to hold only the tiles that are actually seen by the player
	//create special array for midlane tiles to make it easier to sort them out later on
	private StartCircle[] startSpaces;
	private Tile[] spaces;
	private Tile[] midLanes;
	private String[] playerNames;
	protected Tile[] gameTiles;
	protected Player[] players;

	private Pawn lastPawnClicked;
	public int firstDieRoll;
	public int secondDieRoll;
	private boolean displayingMoves;
	public int currentPlayerTurn;
	Player currentPlayer;

	//class constructor
	public Board() {
		this.width = 50;
		this.height = 25;
		this.radius = 100f;
		this.board = new StackPane();
		this.startLayer = new GridPane();
		this.cross = new GridPane();
		this.game = new GridPane();
		this.startSpaces = new StartCircle[4];
		this.spaces = new Tile[BOARD_SIZE];
		this.gameTiles = new Tile[CROSS_SIZE];
		this.midLanes = new Tile[28];
		this.players = new Player[4];
		this.playerNames = new String[4];
	}

	//build the game board, including creating the main shape of the board, setting start spaces, and setting tokens on board
	//first-time setup when starting the game
	public GridPane build() {

		//create player tokens
		players[0] = new Player(PURPLE, LIGHTPURPLE, 68, 69, 5, 75);
		players[1] = new Player(DARKORANGE, LIGHTORANGE, 17, 76, 22, 82);
		players[2] = new Player(GREEN, PALEGREEN, 34, 90, 39, 96);
		players[3] = new Player(YELLOW, KHAKI, 51, 83, 56, 89);

		//create all tiles used for the game
		for (int i = 0; i < spaces.length; i++) {
			spaces[i] = new Tile();
		}

		drawStartSpaces(startLayer);
		drawCross(cross);
		setTiles();
		resizeBoard(width, height, radius);

		//test code for checking tile numbering system
		for (int i = 0; i < gameTiles.length; i++) {
			gameTiles[i].testTileCount();
		}

		//add home space to board
		center = drawHome();
		startLayer.add(center.getHome(), 1, 1);

		//combine all the board pieces together
		//put startLayer before cross so circles don't cover the cross with an odd window size
		board.getChildren().addAll(startLayer, cross);
		startLayer.setAlignment(Pos.CENTER);
		cross.setAlignment(Pos.CENTER);

		game.add(board, 0, 0);
		game.setAlignment(Pos.CENTER);
		return game;
	}

	//search for specific children within a gridpane, used for numbering the tiles of the main area of the cross board
	private Tile getTile(GridPane pane, int col, int row) {
		for (Node node : pane.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return (Tile) node;
			}
		}
		return null;
	}

	//create the cross-shaped part of the board that players will play on
	private GridPane drawCross(GridPane cross) {
		int col = 0;
		int row = 0;
		int tileCount = 0;

		for (int i = 0; i < BOARD_SIZE; i++) {
			cross.add(spaces[i], col, row);
			if (col == 18) {
				col = 0;
				row++;
			} else {
				col++;
			}

			//make all the tiles in the main cross-shaped part of the board visible
			if (GridPane.getColumnIndex(spaces[i]) > 7 && GridPane.getColumnIndex(spaces[i]) < 11) {
				if (GridPane.getRowIndex(spaces[i]) < 8 || GridPane.getRowIndex(spaces[i]) > 10) {
					spaces[i].setStroke(Color.BLACK);
					spaces[i].setFill(Color.AZURE);
					spaces[i].defFill = Color.AZURE;
					gameTiles[tileCount] = spaces[i];
					tileCount++;
				}
			} else if (GridPane.getRowIndex(spaces[i]) > 7 && GridPane.getRowIndex(spaces[i]) < 11) {
				if (GridPane.getColumnIndex(spaces[i]) < 8 || GridPane.getColumnIndex(spaces[i]) > 10) {
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
		for (int i = 0; i < gameTiles.length; i++) {
			if (GridPane.getColumnIndex(gameTiles[i]) == 9) {
				if (GridPane.getRowIndex(gameTiles[i]) <= 7) {
					gameTiles[i].setFill(LIGHTPURPLE);
					gameTiles[i].defFill = LIGHTPURPLE;
					gameTiles[i].setIsSafe(true);
					if (GridPane.getRowIndex(gameTiles[i]) != 0) {
						gameTiles[i].setMidLane(true);
						gameTiles[i].setTileNo(tileNo);
						tileNo++;
						midLanes[k] = gameTiles[i];
						k++;
					}
				} else if (GridPane.getRowIndex(gameTiles[i]) >= 11) {
					gameTiles[i].setFill(PALEGREEN);
					gameTiles[i].defFill = PALEGREEN;
					gameTiles[i].setIsSafe(true);
					if (GridPane.getRowIndex(gameTiles[i]) != 18) {
						if (GridPane.getRowIndex(gameTiles[i]) != 18) {
							gameTiles[i].setMidLane(true);
							gameTiles[i].setTileNo(tileNo);
							tileNo++;
							midLanes[k] = gameTiles[i];
							k++;
						}
					}
				}
			}

			if (GridPane.getRowIndex(gameTiles[i]) == 9) {
				if (GridPane.getColumnIndex(gameTiles[i]) <= 7) {
					gameTiles[i].setFill(LIGHTORANGE);
					gameTiles[i].defFill = LIGHTORANGE;
					gameTiles[i].setIsSafe(true);
					if (GridPane.getColumnIndex(gameTiles[i]) != 0) {
						gameTiles[i].setMidLane(true);
						gameTiles[i].setTileNo(tileNo);
						tileNo++;
						midLanes[k] = gameTiles[i];
						k++;
					}
				} else if (GridPane.getColumnIndex(gameTiles[i]) >= 11) {
					gameTiles[i].setFill(KHAKI);
					gameTiles[i].defFill = KHAKI;
					gameTiles[i].setIsSafe(true);
					if (GridPane.getColumnIndex(gameTiles[i]) != 18) {
						gameTiles[i].setMidLane(true);
						gameTiles[i].setTileNo(tileNo);
						tileNo++;
						midLanes[k] = gameTiles[i];
						k++;
					}
				}
			}
		}

		for (int i = 0; i < midLanes.length; i++) {
			for (int j = 0; j < midLanes.length; j++) {
				if (midLanes[i].getTileNo() > midLanes[j].getTileNo()) {
					if ((midLanes[i].defFill == PALEGREEN && midLanes[j].defFill == PALEGREEN) || midLanes[i].defFill == KHAKI && midLanes[j].defFill == KHAKI) {
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
		for (int i = 0; i < 8; i++) {
			currentTile = getTile(cross, 8, i);
			currentTile.setTileNo(tileNo);
			tileNo++;
			reverseTileNo++;
		}

		//number normal spaces in left arm
		for (int i = 8; i < 11; i++) {
			for (int j = 0; j < 8; j++) {
				currentTile = getTile(cross, j, i);
				if (GridPane.getRowIndex(currentTile) == 8) {
					currentTile.setTileNo(reverseTileNo);
					reverseTileNo--;
					tileNo++;
				} else {
					if (!currentTile.getMidLane()) {
						currentTile.setTileNo(tileNo);
						tileNo++;
					}
				}
			}
		}

		//number normal spaces in lower arm
		reverseTileNo = 33;
		for (int i = 8; i < 11; i++) {
			for (int j = 11; j < 19; j++) {
				currentTile = getTile(cross, i, j);
				if (GridPane.getColumnIndex(currentTile) < 10 && !currentTile.getMidLane()) {
					currentTile.setTileNo(tileNo);
					tileNo++;
					reverseTileNo++;
				} else if (GridPane.getColumnIndex(currentTile) == 10) {
					currentTile.setTileNo(reverseTileNo);
					tileNo++;
					reverseTileNo--;
				}
			}
		}

		//number normal spaces in right arm
		reverseTileNo = 59;
		for (int i = 8; i < 11; i++) {
			for (int j = 11; j < 19; j++) {
				currentTile = getTile(cross, j, i);
				if (GridPane.getRowIndex(currentTile) == 8) {
					currentTile.setTileNo(reverseTileNo);
					reverseTileNo--;
				} else {
					if (!currentTile.getMidLane()) {
						if (GridPane.getRowIndex(currentTile) == 9) {
							currentTile.setTileNo(51);
						} else {
							currentTile.setTileNo(tileNo);
							tileNo++;
						}
					}
				}
			}
		}

		//number normal spaces in remainder of upper arm
		reverseTileNo = 67;
		for (int i = 9; i < 11; i++) {
			for (int j = 0; j < 8; j++) {
				currentTile = getTile(cross, i, j);
				if (GridPane.getColumnIndex(currentTile) == 9) {
					if (!currentTile.getMidLane()) {
						currentTile.setTileNo(68);
					}
				} else {
					currentTile.setTileNo(reverseTileNo);
					reverseTileNo--;
				}
			}
		}

		//sort tiles to make it easier to determine where players can move to
		for (int i = 0; i < gameTiles.length; i++) {
			for (int j = i + 1; j < gameTiles.length; j++) {
				if (gameTiles[i].getTileNo() > gameTiles[j].getTileNo()) {
					currentTile = gameTiles[i];
					gameTiles[i] = gameTiles[j];
					gameTiles[j] = currentTile;
				}
			}
		}

		//set safe space and startFor status for starting spaces
		for (int i = 4; i < gameTiles.length; i += 17) {
			if (!gameTiles[i].getMidLane()) {
				gameTiles[i].setFill(players[i / 17].getColor());
				gameTiles[i].defFill = (players[i / 17].getColor());
				gameTiles[i].setIsSafe(true);
				gameTiles[i].setStartFor(i / 17);
			}
		}

		//set safe space status for non-starting, non-midlane spaces
		for (int i = 11; i < gameTiles.length; i += 17) {
			if (!gameTiles[i].getMidLane()) {
				gameTiles[i].setFill(SAFE);
				gameTiles[i].defFill = (SAFE);
				gameTiles[i].setIsSafe(true);
			}
		}
		for (Tile t : gameTiles) {
			t.setOnMouseClicked(new TileIndicator());
		}
	}	

	private HomeTile drawHome() {
		//draw the home space of the board
		HomeTile center = new HomeTile(this.width * 3.05, this.height * 3.05, 10);
		center.drawHome();
		return center;
	}

	private void drawStartSpaces(GridPane startLayer) {
		//create starting spaces
		//set the pawns to be in the starting area
		for (int i = 0; i < startSpaces.length; i++) {
			startSpaces[i] = new StartCircle();
			startSpaces[i].setStroke(Color.BLACK);
			//set the colors of starting spaces to match player colors.
			switch (i) {
			case 0:
				startSpaces[i].setFill(players[0].getColor());
				startSpaces[i].setColor(players[0].getColor());
				break;
			case 1:
				startSpaces[i].setFill(players[3].getColor());
				startSpaces[i].setColor(players[3].getColor());
				break;
			case 2:
				startSpaces[i].setFill(players[1].getColor());
				startSpaces[i].setColor(players[1].getColor());
				break;
			case 3:
				startSpaces[i].setFill(players[2].getColor());
				startSpaces[i].setColor(players[2].getColor());
				break;
			}
		}

		startLayer.add(startSpaces[0].drawStart(), 0, 0);
		startLayer.add(startSpaces[1].drawStart(), 2, 0);
		startLayer.add(startSpaces[2].drawStart(), 0, 2);
		startLayer.add(startSpaces[3].drawStart(), 2, 2);

		//setup pawn circle positions inside of starting circles. as game progresses, it will show/hide circles representing the pawns as the pawns enter/leave the starting circle
		for (int i = 0; i < startSpaces.length; i++) {
			startSpaces[i].drawBase();
			switch (i) {
			case 0:
				for (int j = 0; j < players[i].pawns.length; j++) {
					startSpaces[i].addPawn(players[0].pawns[j]);
				}
				break;
			case 1:
				for (int j = 0; j < players[i].pawns.length; j++) {
					startSpaces[i].addPawn(players[3].pawns[j]);
				}
				break;
			case 2:
				for (int j = 0; j < players[i].pawns.length; j++) {
					startSpaces[i].addPawn(players[1].pawns[j]);
				}
				break;
			case 3:
				for (int j = 0; j < players[i].pawns.length; j++) {
					startSpaces[i].addPawn(players[2].pawns[j]);
				}
				break;
			}
		}
	}

	//resize board to match game window size
	public void resizeBoard(int width, int height, float radius) {
		//resize the board tiles, including invisible tiles
		for (int i = 0; i < spaces.length; i++) {
			spaces[i].resizeTile(width, height);
		}

		//resize start spaces
		for (int i = 0; i < startSpaces.length; i++) {
			startSpaces[i].setRadius(radius);
		}
	}

	public void rollUpdate()
	{
		ResetBoardAppearance();
		currentPlayer = players[currentPlayerTurn];
		Pawn pawnInStartingArea = null;
		//search through the current player's pawns and look for pawns that are in the starting area
		if (Arrays.stream(currentPlayer.pawns).anyMatch(pawn -> pawn.inStartingArea)) {
			pawnInStartingArea = Arrays.stream(currentPlayer.pawns).filter(pawn -> pawn.inStartingArea).findFirst().get();
		}
		if(pawnInStartingArea != null) {
			if (firstDieRoll == 5) {
				//move a token from home onto board
				if(gameTiles[currentPlayer.getStartingTile() - 1].occupied) {
					if((gameTiles[currentPlayer.getStartingTile() - 1].occupier.size() < 2)) {
						if(pawnInStartingArea.getTokenColor() != gameTiles[currentPlayer.getStartingTile() - 1].occupier.get(0).getTokenColor()) {
							//capture
							moveToken(gameTiles[currentPlayer.getStartingTile() - 1].occupier.get(0), -1);
							moveToken(pawnInStartingArea, currentPlayer.getStartingTile());
							firstDieRoll = 0;
						}
						else {
							moveToken(pawnInStartingArea, currentPlayer.getStartingTile());
							firstDieRoll = 0;
						}
					}
					else {
						//already have 2 or more pawns in starting tile
					}
				}
				else {
					moveToken(pawnInStartingArea, currentPlayer.getStartingTile());
					firstDieRoll = 0;
				}
			}
			else if (secondDieRoll == 5) {
				//move a token from home onto board
				if(gameTiles[currentPlayer.getStartingTile() - 1].occupied)
				{
					if((gameTiles[currentPlayer.getStartingTile() - 1].occupier.size() < 2))
					{
						if(pawnInStartingArea.getTokenColor() != gameTiles[currentPlayer.getStartingTile() - 1].occupier.get(0).getTokenColor()) {
							//capture
							moveToken(gameTiles[currentPlayer.getStartingTile() - 1].occupier.get(0), -1);
							moveToken(pawnInStartingArea, currentPlayer.getStartingTile());
							secondDieRoll = 0;
						}
						else {
							moveToken(pawnInStartingArea, currentPlayer.getStartingTile());
							secondDieRoll = 0;
						}
					}
				}
				else {
					moveToken(pawnInStartingArea, currentPlayer.getStartingTile());
					secondDieRoll = 0;
				}

			}
		}
		else
		{
			//do nothing
		}

	}

	//move tokens around the board
	//@param token indicates which token must be moved, dest represents tileNo for destination tile
	public void moveToken(Pawn token, int dest) {
		//token is currently at starting circle
		int currentLocation = token.getLocation();
		int midlaneHomeTile = currentPlayer.getMidlaneHomeTile();
		if(currentLocation == -1) {
			//remove token from starting circle
			for (int i = 0; i < players.length;i++)
			{
				if (startSpaces[i].pawns.stream().anyMatch(t -> t.equals(token)))
				{
					startSpaces[i].removePawn(token);
				}
			}
			gameTiles[dest - 1].placeToken(token); //might be the problem
			ResetBoardAppearance();
		}
		//token is not at start currently
		else {
			gameTiles[currentLocation - 1].removeToken(token);
			//determine if token is going back to start or not
			if(dest == -1) {
				for (StartCircle sc : startSpaces)
				{
					if(sc.getColor() == token.getSpaceColor())
					{
						sc.addPawn(token);
					}
				}
			}
			else {
				if(gameTiles[dest - 1].occupied) {
					//check to see who is occupying the destination tile
					if(gameTiles[dest - 1].occupier.get(0).getSpaceColor() == token.getSpaceColor()) {
						//create blockade
						gameTiles[dest - 1].placeToken(token);
						if(gameTiles[dest - 1].getRollValue() != 0)
						{
							takeAwayDieUse(gameTiles[dest - 1]);
						}
					}
					else {
						//the token occupying the space does not belong to the player, send the current occupier back to start
						//sending the other player's token to their starting space
						moveToken(gameTiles[dest - 1].occupier.get(0),-1);

						//Moving current player's token there
						gameTiles[dest - 1].placeToken(token);
						if(gameTiles[dest - 1].getRollValue() != 0)
						{
							takeAwayDieUse(gameTiles[dest - 1]);
						}
						ResetBoardAppearance();
					}
				}
				else {
					if(dest == midlaneHomeTile)
					{
						//current token is already removed, but should be sent somewhere
						spaces[0].placeToken(token);
						token.token.setFill(Color.TRANSPARENT);
						token.token.setStroke(Color.TRANSPARENT);
						currentPlayer.finishToken(token);

						center.showPawn(currentPlayerTurn, currentPlayer.atHome(), token);
						if(gameTiles[dest - 1].getRollValue() != 0)
						{
							takeAwayDieUse(gameTiles[dest - 1]);
						}
					}
					else
					{
						gameTiles[dest - 1].placeToken(token);
						if(gameTiles[dest - 1].getRollValue() != 0)
						{
							takeAwayDieUse(gameTiles[dest - 1]);
						}
					}
					ResetBoardAppearance();
				}
			}
		}
	}

	private void takeAwayDieUse(Tile destinationTile) {
		int rollValue = destinationTile.getRollValue();
		if(rollValue == 1)
		{
			firstDieRoll = 0;
		}
		else if(rollValue == 2)
		{
			secondDieRoll = 0;
		}
		else if(rollValue == 3)
		{
			firstDieRoll = 0;
			secondDieRoll = 0;
		}
	}

	class TileIndicator implements EventHandler<MouseEvent> {
		public void handle(MouseEvent e) {

			Tile selectedTile = (Tile) e.getSource();
			if(!selectedTile.occupied) {
				if(displayingMoves) {
					if(selectedTile.base.getFill() == Color.RED) {
						// A normal movement that doesn't capture another piece
						if(selectedTile.getRollValue() != 0)
						{
							moveToken(lastPawnClicked, selectedTile.getTileNo());
							takeAwayDieUse(selectedTile);
						}

					}
					else
					{
						// we're showing movement options, we clicked a non-occupied space AND a blank space
						// we'll reset our board and act like we just clicked on our own pawn again
						ResetBoardAppearance();
					}
				}
				// we clicked a tile without clicking our pawn first
				else {
					ResetBoardAppearance();
				}
			}
			else if (selectedTile.occupied){
				// storing our clicked pawn so that we can move it later
				// Make sure it's our same colored pawn

				if(displayingMoves) {
					if(selectedTile.base.getFill() == Color.RED) {
						if(selectedTile.occupier.get(0).getTokenColor() == currentPlayer.getToken(0).getTokenColor())
						{
							moveToken(lastPawnClicked, selectedTile.getTileNo());
							lastPawnClicked = selectedTile.occupier.get(0);
							takeAwayDieUse(selectedTile);
						}
						else
						{
							//capture
							moveToken(selectedTile.occupier.get(0), -1);
							moveToken(lastPawnClicked, selectedTile.getTileNo());
							takeAwayDieUse(selectedTile);
						}
					}
				}

				if(selectedTile.occupier.get(0).getTokenColor() == currentPlayer.getToken(0).getTokenColor())
				{
					lastPawnClicked = selectedTile.occupier.get(0);
					displayMoves(currentPlayer, selectedTile.occupier.get(0), firstDieRoll, secondDieRoll);
				}

			}
		}
	}

	public void ResetBoardAppearance()
	{
		for(Tile t : gameTiles)
		{
			t.setFill(t.defFill);
			t.setRollValue(0);
		}
		displayingMoves = false;
	}

	//display spaces the selected token may move to
	public void displayMoves(Player player, Pawn pawn, int roll1, int roll2) {
		// set all variables to reduce complexity within the method
		ResetBoardAppearance();

		displayingMoves = true;
		lastPawnClicked = pawn;
		int currentLocation = pawn.getLocation();
		int lastGameTileNum = player.getLastGameTileNum();
		int midlaneTileStartNum = player.getMidlaneStartTile();
		int midlaneHomeTile = player.getMidlaneHomeTile();
		boolean firstRollWrapsAround = false;
		boolean secondRollWrapsAround = false;
		boolean combinedRollWrapsAround = false;

		ArrayList<Integer> blockade = new ArrayList<>();
		for(Tile t : gameTiles) {
			if(t.occupied && t.occupier.size() >= 2)
			{
				blockade.add(t.getTileNo());
			}
		}

		//Naive calculation for first location and then check if we overshoot our midlane
		if(roll1 != 0) {
			int firstRollLocation = (currentLocation  + roll1);
			if(currentPlayerTurn != 0)
			{
				if(firstRollLocation > 68 && firstRollLocation < midlaneTileStartNum)
				{
					firstRollWrapsAround = true;
					firstRollLocation = firstRollLocation - 68;
				}
			}
			boolean firstRollGoesToMidlane = (firstRollLocation > lastGameTileNum && (currentLocation >= lastGameTileNum - 12 && currentLocation <= lastGameTileNum));
			if (firstRollGoesToMidlane) {
				//calculates what location should be highlighted on the individual player's midlane (colored tiles)
				int distanceToMidlane = lastGameTileNum - (currentLocation) - 1;
				int midlaneLocation = ((roll1) - distanceToMidlane) + midlaneTileStartNum - 1;
				firstRollLocation = midlaneLocation;
			}
			if(blockadeNotInTheWay(currentLocation, blockade, firstRollLocation, firstRollWrapsAround)) {
				if(firstRollLocation - 1 < gameTiles.length) {
					Tile destination = gameTiles[firstRollLocation - 1];
					if (destination.occupier.size() < 2 && firstRollLocation <= midlaneHomeTile) {
						if(destination.occupied && destination.getIsSafe() && destination.occupier.get(0).getTokenColor() != player.getToken(0).getTokenColor())
						{
							//safe spot with an opponent in the way
						}
						else
						{
							destination.base.setFill(Color.RED);
							destination.active = true;
							destination.setRollValue(1);
						}

					}
				}
			}
		}
		//Naive calculation for second location and then check if we overshoot our midlane
		if(roll2 != 0) {
			int secondRollLocation = (currentLocation + roll2);
			if(currentPlayerTurn != 0) {
				if(secondRollLocation > 68 && secondRollLocation < midlaneTileStartNum) {
					secondRollWrapsAround = true;
					secondRollLocation = secondRollLocation - 68;
				}
			}
			boolean secondRollGoesToMidlane = (secondRollLocation > lastGameTileNum && (currentLocation >= lastGameTileNum - 12 && currentLocation <= lastGameTileNum));
			if (secondRollGoesToMidlane) {
				//calculates what location should be highlighted on the individual player's midlane (colored tiles)
				int distanceToMidlane = lastGameTileNum - (currentLocation) - 1;
				int midlaneLocation = ((roll2) - distanceToMidlane) + midlaneTileStartNum - 1;
				secondRollLocation = midlaneLocation;
			}
			if (blockadeNotInTheWay(currentLocation, blockade, secondRollLocation, secondRollWrapsAround)) {
				if(secondRollLocation - 1 < gameTiles.length) {
					Tile destination = gameTiles[secondRollLocation - 1];
					if (destination.occupier.size() < 2 && secondRollLocation <= midlaneHomeTile) {
						if (destination.occupied && destination.getIsSafe() && destination.occupier.get(0).getTokenColor() != player.getToken(0).getTokenColor()) {
							//safe spot with an opponent in the way
						} else {
							destination.base.setFill(Color.RED);
							destination.active = true;
							destination.setRollValue(2);
						}

					}
				}
			}
		}
		//Naive calculation for combined roll locations and then check if we overshoot our midlane
		if(!(roll1 == 0 || roll2 == 0)) {
			int combinedRollLocation = (currentLocation + roll1 + roll2);
			if(currentPlayerTurn != 0) {
				if(combinedRollLocation > 68 && combinedRollLocation < midlaneTileStartNum) {
					combinedRollWrapsAround = true;
					combinedRollLocation = combinedRollLocation - 68;
				}
			}
			boolean combinedRollGoesToMidlane = (combinedRollLocation > lastGameTileNum && (currentLocation >= lastGameTileNum - 12 && currentLocation <= lastGameTileNum));
			if (combinedRollGoesToMidlane) {
				//calculates what location should be highlighted on the individual player's midlane (colored tiles)
				int distanceToMidlane = lastGameTileNum - (currentLocation);
				int midlaneLocation = ((roll1 + roll2) - distanceToMidlane) + midlaneTileStartNum - 1;
				combinedRollLocation = midlaneLocation;
			}
			if(blockadeNotInTheWay(currentLocation, blockade, combinedRollLocation, combinedRollWrapsAround)) {
				if(combinedRollLocation - 1 < gameTiles.length) {
					Tile destination = gameTiles[combinedRollLocation - 1];
					if(destination.occupier.size() < 2 && combinedRollLocation <= midlaneHomeTile) {
						if(destination.occupied && destination.getIsSafe() && destination.occupier.get(0).getTokenColor() != player.getToken(0).getTokenColor()) {
							//safe spot with an opponent in the way
						}
						destination.base.setFill(Color.RED);
						destination.active = true;
						destination.setRollValue(3);
					}
				}

			}
		}
	}

	private boolean blockadeNotInTheWay(int currentLocation, ArrayList<Integer> blockade, int rollLocation, boolean wrapsToStart) {
		boolean result = false;
		for(Integer i : blockade)
		{
			//if the blockade is between us and the roll location
			if(currentLocation < i && rollLocation >= i)
			{
				result = true;
			}
			else if(wrapsToStart)
			{
				if(currentLocation < i)
				{
					//if we're wrapping between 68 -> 1 and the blockade is between the current location and the end
					if(i <= 68)
					{
						result = true;
					}
				}
				else if(currentLocation > i)
				{
					//if we're wrapping between 68 -> 1 and the blockade is between the start of the gameTiles and the roll location
					if(rollLocation >= i)
					{
						result = true;
					}
				}
			}
		}
		return !result;
	}
	
	public void namePlayers(String[] names) {
		for(int i = 0; i < this.playerNames.length; i++) {
			this.players[i].setName(names[i]);
		}
	}
}