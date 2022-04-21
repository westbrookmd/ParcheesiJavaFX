/*Class will be used to display the main components of the game, including the game board, dice images, and buttons for controlling gameplay
 *
 *TODO: 
 *	Figure out which class should handle moving tokens around, rewrite code to call method from said class
 *  Cleanup TileIndicator code if possible
 *  Rewrite ShowMovementOptions code
 *  Write a resize method to resize all components of the GUI
 */
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.io.File;
import javafx.scene.Group;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class GUI extends Application {
	// TODO: Cleanup unneeded variables
	private boolean test = false;
	private boolean showingMovementOptions = false;
	// integers to contain values of each die
	private int firstDieRoll = 5;
	private int secondDieRoll = 5;
	private Board board;

	private boolean hasRolled;
	private int currentPlayer;
	private Label whoseTurn;
	private AudioClip buzzer;

	 //public static void main(String[] args) {
	 //launch(args);
	 //}

	public void start(Stage primaryStage) {
		// create Borderpane to hold all components of GUI
		BorderPane program = new BorderPane();
		Die die1 = new Die();
		Die die2 = new Die();
		board = new Board();
		whoseTurn = new Label("");
		buzzer = new AudioClip(getClass().getResource("/diceroll.wav").toExternalForm());

		// create the game board
		GridPane game = board.build();

		// TODO: create the players

		game.setAlignment(Pos.CENTER);

		// create Vbox to hold media player
		// TODO: create the music box
		// VBox musicBox = new VBox();

		// create VBox that will hold images of the dice values
		// TODO: Have diceWindow resize with the main window
		VBox diceWindow = new VBox();
		diceWindow.setAlignment(Pos.CENTER);
		diceWindow.setFillWidth(true);
		diceWindow.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
		// diceWindow.getChildren().add(new Label("This is where the dice will be
		// displayed to the player"));
		showCurrentPlayer();
		diceWindow.getChildren().add(whoseTurn);
		// test code added to determine total size of vbox
		diceWindow.setStyle("-fx-border-color: black;"
				+ "-fx-border-width: 5;");

		// Setting the image view 1
		ImageView imageView1 = new ImageView(die1.showDie());
		// setting the fit height and width of the image view
		imageView1.setFitHeight(100);
		imageView1.setFitWidth(100);
		// Setting the preserve ratio of the image view
		imageView1.setPreserveRatio(true);

		// Setting the image view 2
		ImageView imageView2 = new ImageView(die2.showDie());
		// setting the fit height and width of the image view
		imageView2.setFitHeight(100);
		imageView2.setFitWidth(100);
		// Setting the preserve ratio of the image view
		imageView2.setPreserveRatio(true);

		// HBox to hold dice imageViews
		HBox dice = new HBox(10);
		dice.getChildren().addAll(imageView1, imageView2);
		dice.setAlignment(Pos.CENTER);
		diceWindow.getChildren().add(dice);

		// create HBox that will hold user commands
		// TODO: configure buttonBox to resize with the window, without getting cut off
		// TODO: have buttons themselves change size to match window
		HBox buttonBox = new HBox(10);
		buttonBox.setFillHeight(true);
		Button roll = new Button("Roll");
		Button rules = new Button("Rules");
		buttonBox.getChildren().addAll(roll, rules);
		buttonBox.setAlignment(Pos.CENTER);
		// test code added to determine total size of hbox; final goal is to try and
		// have buttons scale in size along with the window
		buttonBox.setStyle("-fx-border-color: black;"
				+ "-fx-border-width: 5;");

		// create menubar which will allow players to exit the game or start a new one
		// TODO: Create save game option?
		MenuBar menu = new MenuBar();
		Menu file = new Menu("File");
		Menu help = new Menu("Help");

		MenuItem newGame = new MenuItem("New Game");
		MenuItem settings = new MenuItem("Configure"); // TODO: Create settings window: will allow players to change
														// window size, game music & sound effects, and possibly other
														// stuff
		MenuItem exit = new MenuItem("Exit");
		file.getItems().addAll(newGame, settings, exit);

		MenuItem about = new MenuItem("About");
		help.getItems().addAll(about);

		menu.getMenus().addAll(file, help);
		menu.prefWidthProperty().bind(primaryStage.widthProperty());

		// TODO: set up event handlers for menu items
		newGame.setOnAction(e -> {
			// TODO: set to display the start screen, start brand new game, prompt player to
			// save current game?

		});

		settings.setOnAction(e -> {
			/*
			 * TODO: allow players to adjust window size
			 * TODO: allow players to adjust volume for music & sound effects
			 */

		});

		about.setOnAction(e -> {
			// TODO: set button to display credits

		});

		exit.setOnAction(e -> {
			System.exit(0);
		});

		// set up event handlers for buttons
		roll.setOnMouseClicked(e -> {
			buzzer.play();

			hasRolled = true;
			System.out.println("Current Player: " + currentPlayer);
			board.currentPlayerTurn = currentPlayer;

			// roll.setDisable(true);

			// TODO: log this action within an additional settings file
			firstDieRoll = die1.roll();
			board.firstDieRoll = firstDieRoll + 1;
			secondDieRoll = die2.roll();
			board.secondDieRoll = secondDieRoll + 1;
			imageView1.setImage(die1.showDie());
			imageView2.setImage(die2.showDie());

			// TODO: where do we check the rolls and do that logic?
			board.rollUpdate();
			// set first die image to show result
			currentPlayer += 1;

			if (currentPlayer == 4) {
				currentPlayer = 0;
			}
			showCurrentPlayer();
		});

		rules.setOnMouseClicked(e -> {
			// TODO: Display new window for rules page(s)

		});

		// set components inside GUI borderbox
		// TODO: set something on the left side to center the game board?
		program.setTop(menu);
		BorderPane.setMargin(menu, new Insets(0, 0, 15, 0));

		program.setRight(diceWindow);
		BorderPane.setMargin(diceWindow, new Insets(5));

		program.setCenter(game);
		BorderPane.setMargin(game, new Insets(25));

		program.setBottom(buttonBox);
		BorderPane.setMargin(buttonBox, new Insets(5));

		// need to find a way to ensure minimum size will always leave all components of
		// the program visible, considering having min size be 1280 x 720 but final min
		// size should be able to contain entire GUI while being readable

		Scene scene = new Scene(program);
		primaryStage.setTitle("Parcheesi Board");
		primaryStage.setScene(scene);
		primaryStage.setWidth(1280);
		primaryStage.setHeight(720);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	// inner class handles mouse events; need to figure out what all it needs to be
	// doing
	class TileIndicator implements EventHandler<MouseEvent> {
		// TODO: rewrite the entire class?
		public void handle(MouseEvent e) {
			Tile selectedTile = (Tile) e.getSource();
		}
	}

	/**
	 * Shows all possible movement options for the given pawn
	 * within a tile.
	 * 
	 * @param tileClicked the tile that contains the pawn.
	 *                    TODO: Change @param to look at the pawn itself?
	 */
	public void ShowMovementOptions(Tile tileClicked) {
		// TODO: Move method to Board class, or rewrite method so it relies more on the
		// board class method
		Pawn selectedPawn = tileClicked.occupier;
	}

	public void showCurrentPlayer() {
		String message = "It is Player " + (this.currentPlayer + 1) + "'s turn.";
		whoseTurn.setText(message);
	}

	/**
	 * Returns the board to the initial appearance and resets the appearance
	 * of the last pawn to be clicked.
	 * TODO: Move this method to Board class
	 */
}