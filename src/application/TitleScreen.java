package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class TitleScreen
{
	//This method when called, will receive the original primary stage
	public Scene setScreen(Stage stage, GUI gui)
	{	
		// Create panes to work with 
		BorderPane root = new BorderPane();
		GridPane grid = new GridPane();
		VBox menu = new VBox(100);
		
		Scene scene = new Scene(root);
		
		//Creates an auto adjust background
		BackgroundSize bgS = new BackgroundSize(1280, 850, false, false, false, false);
		
		// sets padding for VBox 
		menu.setPadding(new Insets(5,5,5,100));				
		
		//Image from resource folder that will be used for titlescreen
		Image boxArt = new Image("/resources/parcheesi_pic.JPG");
		
		// sets the auto adjusting image in the desired position of background
		root.setBackground(new Background(new BackgroundImage(boxArt, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgS)));
		
		//size the gridpane
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(10));
		grid.setScaleX(2);
		grid.setScaleY(2);
		
		// create labels for players that will be playing -- would like to make it so that labels only appear when asked if other players will play
		Label lblP1 = new Label("Player 1");
		Label lblP2 = new Label("Player 2");
		Label lblP3 = new Label("Player 3");
		Label lblP4 = new Label("Player 4");
		
		// sets the padding and styles for the labels 
		lblP1.setPadding(new Insets(1));
		lblP1.setFont(Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 18));
		lblP1.setStyle("-fx-border-color: red; -fx-background-color: plum;");
		lblP2.setPadding(new Insets(1));
		lblP2.setFont(Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 18));
		lblP2.setStyle("-fx-border-color: red; -fx-background-color: orange;");
		lblP3.setPadding(new Insets(1));
		lblP3.setFont(Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 18));
		lblP3.setStyle("-fx-border-color: red; -fx-background-color: lightgreen;");
		lblP4.setPadding(new Insets(1));
		lblP4.setFont(Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 18));
		lblP4.setStyle("-fx-border-color: red; -fx-background-color: yellow;");
		
		// create textfields so players can insert their name
		TextField txtP1 = new TextField("Violet");
		TextField txtP2 = new TextField("Topaz");
		TextField txtP3 = new TextField("Greene");
		TextField txtP4 = new TextField("Sunny");
		
		//sets padding for the textfields
		txtP1.setPadding(new Insets(5));
		txtP2.setPadding(new Insets(5));
		txtP3.setPadding(new Insets(5));
		txtP4.setPadding(new Insets(5));
		
		Button btnStart = new Button("Start");
		Button btnRules = new Button("Rules");		

		// adds nodes to selected gridpanes
		grid.add(lblP1, 0, 0);
		grid.add(lblP2, 0, 1);
		grid.add(lblP3, 0, 2);
		grid.add(lblP4, 0, 3);
		grid.add(txtP1, 1, 0);
		grid.add(txtP2, 1, 1);
		grid.add(txtP3, 1, 2);
		grid.add(txtP4, 1, 3);
		
		menu.getChildren().addAll(btnStart, btnRules);
		menu.setAlignment(Pos.CENTER);
		grid.setAlignment(Pos.CENTER);
		
		// sets button preferences for start and rules
		btnStart.setPrefSize(100, 50);
		btnStart.setFont(Font.font("Broadway", FontWeight.BOLD, FontPosture.REGULAR, 20));
		btnRules.setPrefSize(100, 50);
		btnRules.setFont(Font.font("Broadway", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		// sets preferred width and height of grid
		grid.setPrefHeight(500);
		grid.setPrefWidth(500);
		
		// sets panes to root borderpane
		root.setCenter(grid);
		root.setLeft(menu);
		grid.setTranslateY(50);
		menu.setTranslateY(50);
		
		// event handler that creates a new window for rules and allows player to scroll through properly-- got this from the web -- change to new window with tabs for sections
		btnRules.setOnAction(e-> 
		{		
			Rules rules = new Rules();
			rules.handle(e);
			});
		
		// start button should close title screen and then start game by setting new primary stage
		btnStart.setOnAction(e-> 
		{
			//Send array of names to the GUI so players' names are displayed on their turn
			String[] names = new String[4];
			names[0] = txtP1.getText();
			names[1] = txtP2.getText();
			names[2] = txtP3.getText();
			names[3] = txtP4.getText();
			
			//close the title screen and launch the GUI window
			stage.close();
			gui.start(stage, names);
		});
		
		// since title screen is a method in class it returns a scene object to be used in main class
		return scene;
	}
}