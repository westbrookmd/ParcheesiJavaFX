package application;

//Class will be used to display the main components of the game, including the game board, dice images, and buttons for controlling gameplay

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GUI {
	// integers to contain values of each die
	private int firstDieRoll = 5;
	private int secondDieRoll = 5;
	private Board board;
	private boolean hasRolled;
	private int currentPlayer;
	private Label whoseTurn;
	
	//components for music/sound effects
	private AudioClip buzzer;
	private Media[] songs;
	private MediaPlayer[] playlist;
	private boolean singing;
	private int trackNo = 0;
	private MediaPlayer player;
	private Slider volume;

	public void start(Stage primaryStage, String[] names) {
		// create Borderpane to hold all components of GUI
		BorderPane program = new BorderPane();
		Die die1 = new Die();
		Die die2 = new Die();
		board = new Board();
		whoseTurn = new Label("");
		buzzer = new AudioClip(getClass().getResource("/resources/diceroll.wav").toExternalForm());
		songs = new Media[5];
		playlist = new MediaPlayer[5];
		singing = true;
		
		//add songs to playlist
		songs[0] = new Media(getClass().getResource("/resources/bensound-acousticbreeze.WAV").toExternalForm());
		songs[1] = new Media(getClass().getResource("/resources/bensound-instinct.wav").toExternalForm());
		songs[2] = new Media(getClass().getResource("/resources/bensound-love.wav").toExternalForm());
		songs[3] = new Media(getClass().getResource("/resources/bensound-thelounge.wav").toExternalForm());
		songs[4] = new Media(getClass().getResource("/resources/bensound-tomorrow.wav").toExternalForm());
		for(int i = 0; i < playlist.length; i++) {
			playlist[i] = new MediaPlayer(songs[i]);
		}
		player = playlist[trackNo];
		player.setAutoPlay(true);
		
		// create the game board
		GridPane game = board.build();
		game.setAlignment(Pos.CENTER);

		//name the players based on title screen input
		this.namePlayers(names);

		// create VBox that will hold images of the dice values
		VBox diceWindow = new VBox();
		diceWindow.setAlignment(Pos.CENTER);
		diceWindow.setFillWidth(true);
		diceWindow.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
		showCurrentPlayer();
		diceWindow.getChildren().add(whoseTurn);
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
		
		//add roll button to diceWindow
		Button roll = new Button("Roll");
		diceWindow.getChildren().add(roll);

		// create HBox that will hold music controls
		HBox musicBox = new HBox(10);
		musicBox.setFillHeight(true);
		Label musicLabel = new Label("Music Controls");
		Button playMusic = new Button("||");
		Button nextTrack = new Button("Next");
		Button prevTrack = new Button("Previous");
		Label volLabel = new Label(" Volume:");
		volume = new Slider();
		volume.setPrefWidth(100);
		volume.setValue(50);
		player.volumeProperty().bind(volume.valueProperty().divide(100));
		//set player to automatically play the next song in the list when the current song finishes
		player.setOnEndOfMedia(() -> {
			nextTrack();
		});
		
		musicBox.getChildren().addAll(musicLabel, playMusic, nextTrack, prevTrack, volLabel, volume);
		musicBox.setAlignment(Pos.CENTER);
		musicBox.setStyle("-fx-border-color: black;"
				+ "-fx-border-width: 5;");

		// create menubar which will allow players to exit the game or start a new one
		MenuBar menu = new MenuBar();
		Menu file = new Menu("File");
		Menu help = new Menu("Help");

		MenuItem newGame = new MenuItem("New Game");
		MenuItem exit = new MenuItem("Exit");
		file.getItems().addAll(newGame, exit);

		MenuItem about = new MenuItem("About");
		MenuItem rules = new MenuItem("Rules");
		help.getItems().addAll(about, rules);

		menu.getMenus().addAll(file, help);
		menu.prefWidthProperty().bind(primaryStage.widthProperty());

		newGame.setOnAction(e -> {
			player.stop();
			primaryStage.close();
			primaryStage.setScene(new TitleScreen().setScreen(primaryStage, new GUI()));
			primaryStage.show();
		});

		exit.setOnAction(e -> {
			System.exit(0);
		});
		
		about.setOnAction(e -> {
			Stage stage = new Stage();
			StackPane pane = new StackPane();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			Text credits = new Text("Parcheesi Game programmed by:\r\n\r\n"
					+ "Willie Page\r\n"
					+ "Christopher Smith\r\n"
					+ "Marshall Westbrook\r\n"
					+ "Natalie Young");
			credits.setWrappingWidth(200);
			pane.getChildren().add(credits);
			pane.setAlignment(Pos.CENTER);
			stage.setWidth(250);
			stage.setHeight(250);
			stage.setResizable(false);
			stage.show();
		});
		
		rules.setOnAction(e -> {
			Rules rulesPage = new Rules();
			rulesPage.handle(e);
		});

		// set up event handlers for buttons
		roll.setOnMouseClicked(e -> {
			buzzer.play();
			
			firstDieRoll = die1.roll();
			board.firstDieRoll = firstDieRoll;
			secondDieRoll = die2.roll();
			board.secondDieRoll = secondDieRoll;
			imageView1.setImage(die1.showDie());
			imageView2.setImage(die2.showDie());

			if (hasRolled) {
				currentPlayer += 1;
			}
			hasRolled = true;
			if (currentPlayer == 4) {
				currentPlayer = 0;
			}
			showCurrentPlayer();
			board.currentPlayerTurn = currentPlayer;
			board.rollUpdate();
		});
		
		playMusic.setOnAction(e -> {
			if(singing) {
				player.pause();
				playMusic.setText(">");
				singing = false;
			}
			else {
				player.play();
				playMusic.setText("||");
				singing = true;
			}
		});
		
		nextTrack.setOnAction(e -> {
			nextTrack();
		});
		
		prevTrack.setOnAction(e -> {
			prevTrack();
		});

		// set components inside GUI borderbox
		program.setTop(menu);
		BorderPane.setMargin(menu, new Insets(0, 0, 15, 0));

		program.setRight(diceWindow);
		BorderPane.setMargin(diceWindow, new Insets(5));

		program.setCenter(game);
		BorderPane.setMargin(game, new Insets(25));

		program.setBottom(musicBox);
		BorderPane.setMargin(musicBox, new Insets(5));

		Scene scene = new Scene(program);
		primaryStage.setTitle("Parcheesi Board");
		primaryStage.setScene(scene);
		primaryStage.setWidth(1280);
		primaryStage.setHeight(720);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void showCurrentPlayer() {
		String message = "It is Player " + (this.currentPlayer + 1) + "'s turn.";
		whoseTurn.setText(message);
	}

	public void namePlayers(String[] names) {
		board.namePlayers(names);
	}

	public void nextTrack() {
		player.stop();
		trackNo++;
		if(trackNo == playlist.length) {
			trackNo = 0;
		}
		player = playlist[trackNo];
		player.volumeProperty().bind(volume.valueProperty().divide(100));
		player.setAutoPlay(true);
		player.setOnEndOfMedia(() -> {
			nextTrack();
		});
	}
	
	public void prevTrack() {
		player.stop();
		
		if(trackNo == 0) {
			trackNo = playlist.length;
		}
		trackNo--;
		player = playlist[trackNo];
		player.volumeProperty().bind(volume.valueProperty().divide(100));
		player.setAutoPlay(true);
		player.setOnEndOfMedia(() -> {
			nextTrack();
		});
	}
}