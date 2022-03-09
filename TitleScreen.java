package application;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TitleScreen 
{
	//This method when called, will recieve the original primary stage
	public Scene setScreen()
	{
		// Create panes to work with 
		BorderPane root = new BorderPane();
		GridPane grid = new GridPane();
		VBox menu = new VBox(100);
		
		// Set final variables to be used titlescreen
		final ScrollPane sp = new ScrollPane();
		final Image pics[] = new Image[2];
		final ImageView[] images = new ImageView[2];
		final Label filename = new Label();
		final String[] imageNames = new String[] {"p1", "p2"};		
		final VBox box = new VBox();
		
		//Creates an auto adjust background-- got this from the web needs to be tweaked down the line
		BackgroundSize bgS = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO, false, false, true, true);
		menu.setPadding(new Insets(5,5,5,100));
		
		//Images from resource folder that will be used for rules and titlescreen
		Image image = new Image("game_Title.JPG");
		Image image2 = new Image("parcheesi_pic.JPG");
		Image imageR1 = new Image("rules_1.JPG");
		Image imageR2 = new Image("rules_2.JPG");
		
		// sets the auto adjusting image in the desired postion of background
		root.setBackground(new Background(new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgS)));
		
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
		lblP1.setStyle("-fx-border-color: red; -fx-background-color: aqua;");
		lblP2.setPadding(new Insets(1));
		lblP2.setFont(Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 18));
		lblP2.setStyle("-fx-border-color: red; -fx-background-color: aqua;");
		lblP3.setPadding(new Insets(1));
		lblP3.setFont(Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 18));
		lblP3.setStyle("-fx-border-color: red; -fx-background-color: aqua;");
		lblP4.setPadding(new Insets(1));
		lblP4.setFont(Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 18));
		lblP4.setStyle("-fx-border-color: red; -fx-background-color: aqua;");
		
		// create textfields so players can insert their name
		TextField txtP1 = new TextField("Player 1");
		TextField txtP2 = new TextField("Player 2");
		TextField txtP3 = new TextField("Player 3");
		TextField txtP4 = new TextField("Player 4");
		
		//sets padding for the textfields
		txtP1.setPadding(new Insets(5));
		txtP2.setPadding(new Insets(5));
		txtP3.setPadding(new Insets(5));
		txtP4.setPadding(new Insets(5));
		
		//creates buttons for players to choose their color as well as buttons for start and rules
		Button btnColor1 = new Button();
		Button btnColor2 = new Button();
		Button btnColor3 = new Button();
		Button btnColor4 = new Button();
		Button btnStart = new Button("Start");
		Button btnRules = new Button("Rules");
		
		//button array that will be used to cycle through colors 
		Button[] btnN = new Button[] {btnColor1, btnColor2, btnColor3, btnColor4};
		
		//loop to change button appearance and its effects
		for(int i = 0; i < 4; i++) 
		{
			btnN[i] = new Button("btnColor" + (i)); //probably unnecessary since the loop is still within the scope of the method and not inside of an inner method
			btnN[i].setPrefSize(15, 15);			//sets preferred size along with padding
			btnN[i].setPadding(new Insets(5));
					
			// create an event handler to change the color of the button to cycle through colors..... or rather styles since button objects apparently don't take a color-- needs work to work
			btnN[i].setOnAction(new EventHandler<ActionEvent>()
		      {
		         public void handle(ActionEvent e) 
		         {
		        	Color[] colors = new Color[] {Color.GREEN, Color.ORANGE, Color.PURPLE, Color.YELLOW};
		        	String[] style = new String[] {".setStyle('-fx-background-color: green')", ".setStyle('-fx-background-color: orange')", ".setStyle('-fx-background-color: purple')", ".setStyle('-fx-background-color: yellow')"};
		 			String curColor;
		 			int index = 0;	
		 			
		 			//btnN[i].addActionListener();
		 				for (int i = 0;i < colors.length;i++) 
			        	 {
			        	 switch(i) 
			        	 {
			        	 case 0: btnN[i].setStyle("-fx-background-color: green");
			        	 case 1: btnN[i].setStyle("-fx-background-color: orange");
			        	 case 2: btnN[i].setStyle("-fx-background-color: purple");
			        	 case 3: btnN[i].setStyle("-fx-background-color: yellow");
			        	 }
			        	 }	        	 
		         }
			});
		}
		
		// adds nodes to selected gridpanes
		grid.add(lblP1, 0, 0);
		grid.add(lblP2, 0, 1);
		grid.add(lblP3, 0, 2);
		grid.add(lblP4, 0, 3);
		grid.add(txtP1, 1, 0);
		grid.add(txtP2, 1, 1);
		grid.add(txtP3, 1, 2);
		grid.add(txtP4, 1, 3);
		grid.add(btnColor1, 2, 0);
		grid.add(btnColor2, 2, 1);
		grid.add(btnColor3, 2, 2);
		grid.add(btnColor4, 2, 3);
		
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
		root.setTop(new ImageView(image));
		root.setCenter(grid);
		root.setLeft(menu);
		
		// event handler that creates a new window for rules and allows player to scroll through properly-- got this from the web 
		btnRules.setOnAction(e-> 
		{			
			VBox layout = new VBox();
			Stage sRules = new Stage();
			sRules.setTitle("Rules");
			Scene scRules = new Scene(layout, 730,800);						
			layout.getChildren().addAll(sp, filename);			
			VBox.setVgrow(sp, Priority.ALWAYS);		
			
			
			for(int i = 0; i < 2; i++) 
			{
				pics[i] = new Image("rules_" + (i+1) + ".JPG");						
				images[i] = new ImageView(pics[i]);
				images[i].setFitWidth(images[i].getFitWidth());
				images[i].setPreserveRatio(true);
				box.getChildren().add(images[i]);
			}
			sp.setVmax(1000);
			sp.setPrefSize(150, 200);
			sp.setContent(box);
			sp.vvalueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {filename.setText(imageNames[(new_val.intValue() - 1)/100]);
			});
			
			
			scRules.setFill(Color.BLACK);
			sRules.setScene(scRules);
			sRules.show();		
			
		});
		
		// start button should close title screen and then start game by setting new primary stage
		btnStart.setOnAction(e-> {
			
			//Scene scene = new Scene();
			//Main.start(primaryStage);
		});
		
		// since title screen is a method in class it returns a scene object to be used in main class
		Scene scene = new Scene(root);
		return scene;
		//Scene titleScreen = new SubScene(root)  // Use this for diceroll animation 
	}
}
