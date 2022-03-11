//GUI Class programmed by Christopher Smith
//Class will be used to display the main components of the game, including the game board, dice images, and buttons for controlling gameplay

import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.beans.*;

public class GUI extends Application {
	private boolean test = false;
	private boolean showingMovementOptions = false;
	//integers to contain values of each die
	private int firstDieRoll = 6;
	private int secondDieRoll = 6;
	Pawn lastPawnClicked;
	private Board board;

	public void start(Stage primaryStage) {
		//create Borderpane to hold all components of GUI
		BorderPane program = new BorderPane();
		Die die1 = new Die();
		Die die2 = new Die();
		board = new Board();

		//create the game board
		GridPane game = board.build();
		//create the players
		Player player1 = new Player(board.playerTokens[0], board.playerTokens[1], board.playerTokens[2], board.playerTokens[3]);
		Player player2 = new Player(board.playerTokens[4], board.playerTokens[5], board.playerTokens[6], board.playerTokens[7]);
		Player player3 = new Player(board.playerTokens[8], board.playerTokens[9], board.playerTokens[10], board.playerTokens[11]);
		Player player4 = new Player(board.playerTokens[12], board.playerTokens[13], board.playerTokens[14], board.playerTokens[15]);
		game.setAlignment(Pos.CENTER);

		//create VBox that will hold images of the dice values
		VBox diceWindow = new VBox();
		Label placeholder = new Label("This is where the dice will be displayed to the player");
		diceWindow.setAlignment(Pos.CENTER);
		diceWindow.setFillWidth(true);
		diceWindow.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
		diceWindow.getChildren().add(new Label("This is where the dice will be displayed to the player"));
		//test code added to determine total size of vbox
		diceWindow.setStyle("-fx-border-color: black;"
				+ "-fx-border-width: 5;");

		//die images
		Image dieImage1 = new Image("dice1.png"); 
		Image dieImage2 = new Image("dice2.png");
		Image dieImage3 = new Image("dice3.png");
		Image dieImage4 = new Image("dice4.png");
		Image dieImage5 = new Image("dice5.png");
      Image dieImage6 = new Image("dice6.png");

		//Setting the image view 1 
		ImageView imageView1 = new ImageView(dieImage6);
		//setting the fit height and width of the image view 
		imageView1.setFitHeight(100); 
		imageView1.setFitWidth(100);         
		//Setting the preserve ratio of the image view 
		imageView1.setPreserveRatio(true); 


		//Setting the image view 2 
		ImageView imageView2 = new ImageView(dieImage6);
		//setting the fit height and width of the image view 
		imageView2.setFitHeight(100); 
		imageView2.setFitWidth(100);          
		//Setting the preserve ratio of the image view 
		imageView2.setPreserveRatio(true);

		//HBox to hold dice imageViews
		HBox dice = new HBox(10);

		dice.getChildren().addAll(imageView1, imageView2);
		dice.setAlignment(Pos.CENTER);
		diceWindow.getChildren().add(dice);

		//create HBox that will hold user commands
		HBox buttonBox = new HBox(10);
		buttonBox.setFillHeight(true);
		Button roll = new Button("Roll");
		Button rules = new Button("Rules");
		buttonBox.getChildren().addAll(roll, rules);
		buttonBox.setAlignment(Pos.CENTER);
		//test code added to determine total size of hbox; final goal is to try and have buttons scale in size along with the window
		buttonBox.setStyle("-fx-border-color: black;"
				+ "-fx-border-width: 5;");

		//create menubar which will allow players to exit the game or start a new one 
		//later on will also include a settings option to allow player to adjust various aspects of the game, including game music/sound effects, and window size
		MenuBar menu = new MenuBar();
		Menu file = new Menu("File");
		Menu help = new Menu("Help");

		MenuItem newGame = new MenuItem("New Game");
		MenuItem settings = new MenuItem("Configure");
		MenuItem exit = new MenuItem("Exit");
		file.getItems().addAll(newGame, settings, exit);

		MenuItem about = new MenuItem("About");
		help.getItems().addAll(about);

		menu.getMenus().addAll(file, help);
		menu.prefWidthProperty().bind(primaryStage.widthProperty());

		//set up event handlers for menu items
		newGame.setOnAction(e -> {

		});

		settings.setOnAction(e -> {

		});

		about.setOnAction(e -> {

		});

		exit.setOnAction(e -> {
			System.exit(0);
		});

		//set up event handlers for buttons
		roll.setOnMouseClicked(e -> {
			// TODO: log this action within an additional settings file
			firstDieRoll = die1.roll();
			secondDieRoll = die2.roll();

			
			for (int i = 0; i < board.gameTiles.length; i++) {
				board.gameTiles[i].base.setFill(board.gameTiles[i].defFill);
			}
			// TODO: remove this and add a turn-based system
			if(!test)
			{
				board.moveToken(player1.getPawn1(), 1, 2);
				board.moveToken(player1.getPawn2(), 1, 3);
				board.moveToken(player1.getPawn3(), 1, 4);
				board.moveToken(player1.getPawn4(), 1, 5);
				test = true;
			}
			
			//set first die image to show result
			switch(firstDieRoll) {
			case 1:
				imageView1.setImage(dieImage1);
				break;
			case 2:
				imageView1.setImage(dieImage2);
				break;
			case 3:
				imageView1.setImage(dieImage3);
				break;
			case 4:
				imageView1.setImage(dieImage4);
				break;
			case 5:
				imageView1.setImage(dieImage5);
				break;
			case 6:
				imageView1.setImage(dieImage6);
				break;
			}

			//set second die image to show result
			switch(secondDieRoll) {
			case 1:
				imageView2.setImage(dieImage1);
				break;
			case 2:
				imageView2.setImage(dieImage2);
				break;
			case 3:
				imageView2.setImage(dieImage3);
				break;
			case 4:
				imageView2.setImage(dieImage4);
				break;
			case 5:
				imageView2.setImage(dieImage5);
				break;
			case 6:
				imageView2.setImage(dieImage6);
				break;
			}
		});

		rules.setOnMouseClicked(e -> {

		});
		
		//setup tile click event handlers
		for(int i = 0; i < board.gameTiles.length; i++) {
			board.gameTiles[i].setOnMouseClicked(new TileIndicator());
		}

		//set components inside GUI borderbox
		program.setTop(menu);
		program.setMargin(menu, new Insets(0, 0, 15, 0));

		program.setRight(diceWindow);
		program.setMargin(diceWindow, new Insets(5));

		program.setCenter(game);
		program.setMargin(game, new Insets(25));

		program.setBottom(buttonBox);
		program.setMargin(buttonBox, new Insets(5));

		//need to find a way to ensure minimum size will always leave all components of the program visible, considering having min size be 1280 x 720 but final min size should be able to contain entire GUI while being readable

		Scene scene = new Scene(program);
		primaryStage.setTitle("Parcheesi Board");
		primaryStage.setScene(scene);
		primaryStage.setWidth(1280);
		primaryStage.setHeight(720);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	class TileIndicator implements EventHandler<MouseEvent> {
		public void handle(MouseEvent e) {
			Tile selectedTile = (Tile) e.getSource();
			if(!selectedTile.occupied) {
				if(showingMovementOptions) {
					if(selectedTile.base.getFill() == Color.RED) {
						// A normal movement that doesn't capture another piece
						// TODO: log this action within an additional settings file
						board.moveToken(lastPawnClicked, lastPawnClicked.currentSpace, selectedTile.tileNo);
						ResetBoardAppearance();
						// TODO: remove roll 'uses'. Calculate which option they selected or should we store this when we draw the movement options
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
					//selectedTile.base.setFill(Color.RED);
				}
			}
			// TODO: add an additional if statement to check to see if this is an opponent and we're showing movement options
			else if (selectedTile.occupied){
				// storing our clicked pawn so that we can move it later
				lastPawnClicked = selectedTile.occupier;
				ShowMovementOptions(selectedTile);
			}
		}
	}
	/**
	 * Shows all possible movement options for the given pawn
	 * within a tile.
	 * @param tileClicked the tile that contains the pawn.
	 *
	 */
	public void ShowMovementOptions(Tile tileClicked) {
		Pawn selectedPawn = tileClicked.occupier;
		// TODO: check if this is our pawn (it should be if it is active)
		if(selectedPawn.active) {
			ResetBoardAppearance();
			lastPawnClicked = null;
			showingMovementOptions = false;
		}
		else {
			selectedPawn.token.setStroke(Color.RED);
			selectedPawn.active = true;
			showingMovementOptions = true;

			for(int i = tileClicked.tileNo; i < board.gameTiles.length; i++) {
				// TODO: Add check for blockade
				if((board.gameTiles[i].tileNo == (tileClicked.tileNo + firstDieRoll)) || (board.gameTiles[i].tileNo == (tileClicked.tileNo + secondDieRoll)) || (board.gameTiles[i].tileNo == (tileClicked.tileNo + firstDieRoll + secondDieRoll))) {
					board.gameTiles[i].base.setFill(Color.RED);
					board.gameTiles[i].active = true;
				}
				else {
					board.gameTiles[i].base.setFill(board.gameTiles[i].defFill);
					board.gameTiles[i].active = false;
				}
			}

			for (int i = 0; i < tileClicked.tileNo; i++) {
				board.gameTiles[i].base.setFill(board.gameTiles[i].defFill);
			}
		}
	}

	/**
	 * Returns the board to the initial appearance and resets the appearance
	 * of the last pawn to be clicked.
	 */
	private void ResetBoardAppearance() {
		for (int i = 0; i < board.gameTiles.length; i++) {
			board.gameTiles[i].base.setFill(board.gameTiles[i].defFill);
		}
		if (lastPawnClicked != null)
		{
			lastPawnClicked.token.setStroke(Color.BLACK);
			lastPawnClicked.active = false;
		}
	}
}