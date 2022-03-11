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
	private boolean active = false;
	//integers to contain values of each die
	private int x = 6;
	private int y = 6;
	
	private Board board;

	public void start(Stage primaryStage) {
		//create Borderpane to hold all components of GUI
		BorderPane program = new BorderPane();
		Die die1 = new Die();
		Die die2 = new Die();
		board = new Board();
		Pawn testToken1 = new Pawn(10, Color.GREEN, Color.PALEGREEN);
		testToken1.token.setStroke(Color.BLACK);
		Pawn testToken2 = new Pawn(10, Color.GREEN, Color.PALEGREEN);
		testToken2.token.setStroke(Color.BLACK);

		//create the game board
		GridPane game = board.build();
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
			x = die1.roll();
			y = die2.roll();
			
			for (int i = 0; i < board.gameTiles.length; i++) {
				board.gameTiles[i].base.setFill(board.gameTiles[i].defFill);
			}
			
			//test placing tokens			
			if(!test) {
				board.moveToken(testToken1, 1, 5);
				test = true;
			}
			else
			{
				board.moveToken(testToken2, 1, 5);
			}
			
			//set first die image to show result
			switch(x) {
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
			switch(y) {
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
				if(active) {
					selectedTile.base.setFill(selectedTile.defFill);
					active = false;
				}
				else {
					selectedTile.base.setFill(Color.RED);
					active = true;
				}
			}
			else if (selectedTile.occupied){
				showMovable(selectedTile);
			}
		}
	}
	
	public void showMovable(Tile start) {
		Pawn selectedPawn = start.occupier;
		if(selectedPawn.active) {
			selectedPawn.token.setStroke(Color.BLACK);
			selectedPawn.active = false;
			
			for (int i = 0; i < board.gameTiles.length; i++) {
				board.gameTiles[i].base.setFill(board.gameTiles[i].defFill);
			}
		}
		else {
			selectedPawn.token.setStroke(Color.RED);
			selectedPawn.active = true;

			for(int i = start.tileNo; i < board.gameTiles.length; i++) {
				if((board.gameTiles[i].tileNo == (start.tileNo + x)) || (board.gameTiles[i].tileNo == (start.tileNo + y)) || (board.gameTiles[i].tileNo == (start.tileNo + x + y))) {
					board.gameTiles[i].base.setFill(Color.RED);
				}
				else {
					board.gameTiles[i].base.setFill(board.gameTiles[i].defFill);
				}
			}

			for (int i = 0; i < start.tileNo; i++) {
				board.gameTiles[i].base.setFill(board.gameTiles[i].defFill);
			}
		}
	}
}