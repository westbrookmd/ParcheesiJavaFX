package application;

import java.lang.reflect.Array;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
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
		
		//Creates an auto adjust background
		BackgroundSize bgS = new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO, false, false, true, true);
		
		// sets padding for VBox 
		menu.setPadding(new Insets(5,5,5,100));				
		
		//Images from resource folder that will be used for rules and titlescreen
		Image image = new Image("game_Title.JPG");
		Image image2 = new Image("parcheesi_pic.JPG");
		
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
		lblP1.setStyle("-fx-border-color: red; -fx-background-color: green;");
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
			//btnN[i] = new Button("btnColor" + (i)); //probably unnecessary since the loop is still within the scope of the method and not inside of an inner method
			btnN[i].setPrefSize(15, 15);			//sets preferred size along with padding
			btnN[i].setPadding(new Insets(5));
		}	
	
		//Create a drop down list instead
    	Color[] colors = new Color[] {Color.GREEN, Color.ORANGE, Color.PURPLE, Color.YELLOW};
    	String[] style = new String[] {"-fx-background-color: green", "-fx-background-color: orange", "-fx-background-color: purple", "-fx-background-color: yellow"};
		//var curColor = btnN[x].getStyle();
		
			
		// create an event handler to change the color of the button to cycle through colors..... or rather styles since button objects apparently don't take a color-- needs work to work
		btnN[0].setOnAction(new EventHandler<ActionEvent>()
	      {	        	 
			@Override public void handle(ActionEvent e) 
	         {	       
				lblP1.getStyle();
				
				if(lblP1.getStyle().contains("green")) 
				{
					lblP1.setStyle(style[1]);
				}
				
				if(lblP1.getStyle().contains("orange")) 
				{
					lblP1.setStyle(style[2]);
				}
				if(lblP1.getStyle().contains("purple")) 
				{
					lblP1.setStyle(style[3]);
				}
				if(lblP1.getStyle().contains("yellow")) 
				{
					lblP1.setStyle(style[0]);
				}
				/*
				else if(lblP1.getStyle().contains("null"))
				{
					lblP1.setStyle(style[0]);
				}
				*/
				
         	 }
	      });	

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
		
		// event handler that creates a new window for rules and allows player to scroll through properly-- got this from the web -- change to new window with tabs for sections
		btnRules.setOnAction(e-> 
		{		
			// creates the new rule window
			BorderPane rLayout = new BorderPane();
			Stage sRules = new Stage();
			sRules.setTitle("Rules");
			Scene scRules = new Scene(rLayout, 1000,400);
			
			// sets left side of border box in rule window
			TilePane tabs = new TilePane(Orientation.VERTICAL);
			tabs.setPrefSize(200, 200);
			tabs.setAlignment(Pos.CENTER_LEFT);
			tabs.setPadding(new Insets(10));	
			tabs.setHgap(5);
			tabs.setVgap(10);
			
			rLayout.setLeft(tabs);
			
			// creates an output window in middle of border box within a scrollpane
			TextArea tOut = new TextArea();
			tOut.setPrefHeight(390);
			tOut.setPrefWidth(780);
			sp.setContent(tOut);
			rLayout.setCenter(sp);
			tOut.autosize();
			tOut.setEditable(false);
			tOut.setWrapText(true);
			tOut.setFont(Font.font("Arial", 20));
			tOut.setStyle("-fx-line-spacing: 100em;");
			
			
			Button rLbl1 = new Button("Object of the Game");
			Button rLbl2 = new Button("Gameplay");
			Button rLbl3 = new Button("Entering your Pawns");
			Button rLbl4 = new Button("Moving your Entered Pawns");
			Button rLbl5 = new Button("Doublets");
			Button rLbl6 = new Button("Capturing an Opponent’s Pawn");
			Button rLbl7 = new Button("Safety Spaces");
			Button rLbl8 = new Button("Blockades");
			Button rLbl9 = new Button("Reaching Home");
			Button rLbl10 = new Button("End of game");
			
			Button[] btnArray = new Button[] {rLbl1,rLbl2,rLbl3,rLbl4,rLbl5,rLbl6,rLbl7,rLbl8,rLbl9,rLbl10};
			
			for(int i = 0; i < btnArray.length; i++) 
			{
				btnArray[i].setMaxWidth(Double.MAX_VALUE);
				btnArray[i].setMaxHeight(Double.MAX_VALUE);
				btnArray[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
				
				btnArray[0].setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e) 
						{
				        	tOut.setText("");			        	 
							tOut.setText("• Be first to move all four of your pawns from your START circle to HOME.");
				      	}
		      	});		      	
				btnArray[1].setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e) 
						{
				        	tOut.setText("");			        	 
				        	tOut.setText("• All players roll two dice.\r\n \r\n• Highest roller starts.\r\n \r\n• Play then continues to the left.\r\n"
									+ "\r\n• Players use the roll button to roll the dice.\r\n \r\n• Once pawns are on the board player can decide which pawn to move dependent on the amount shown on the dice.");
				      	}
		      	});				
				btnArray[2].setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e) 
						{
				        	tOut.setText("");			        	 
				        	tOut.setText("• Each pawn must enter before it can start around the gameboard path.\r\n \r\n• On each of your turns, try to enter your pawns by rolling FIVES.\r\n"
									+ "\r\n• Pawns are entered only on die rolls of FIVE; a 5 on one or both dice; or any combination totaling 5 i.e.(4+1 or 3+2).\r\n"
									+ "\r\n• When possible you must enter a pawn. However, if you can't use a five to enter, try to use it for movement. *See Moving Your Entered Pawns*");
				      	}
		      	});
				btnArray[3].setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e) 
						{
				        	tOut.setText("");			        	 
				        	tOut.setText("• Move your entered pawns counterclockwise along the path the number of spaces you roll on the dice; see the arrow on the gameboard diagram. "
									+ "• Move your pawns by the rules below:\r\n"
									+ "\r\n• You may move one or two pawns on your turn. For example, if you roll 4 + 3, you can move one pawn 4 + 3 spaces; or you can move one pawn 4 spaces and another pawn 3 spaces.\r\n"
									+ "\r\n• You may move one or two pawns on your turn. For example, if you roll 4 + 3, you can move one pawn 4 + 3 spaces; or you can move one pawn 4 spaces and another pawn 3 spaces.\r\n"
									+ "\r\n• You may move one or two pawns on your turn. For example, if you roll 4 + 3, you can move one pawn 4 + 3 spaces; or you can move one pawn 4 spaces and another pawn 3 spaces.\r\n");
				      	}
		      	});
				btnArray[4].setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e) 
						{
				        	tOut.setText("");			        	 
				        	tOut.setText("• A roll of matching dice is called doublets.\r\n \r\n• A roll of doublets entitles you to another roll - and may also entitle you to a bonus move.\r\n"
									+ "\r\n• If you roll doublets before all of you pawns are entered, take your turn as usual, then roll again.\r\n"
									+ "\r\n• Doublets Bonus\r\n"
									+ "\t• If you roll doublets after all four of your pawns are entered, use the four numbers on the tops and the bottom of the dice of movement.\r\n"
									+ "\t \r\n• The total of this four-part move is always 14, and can be taken by one pawn or split among 2 or more pawns.\r\n"
									+ "\r\n• Doublets Penalty\r\n"
									+ "\t• The third consecutive time you roll doublets, don’t move your pawns at all, Instead, move your pawn closest to HOME (even if it’s on your Home Path) and return it to your "
									+ "START circle; it must be re-entered later. This ends your turn.\r\n");
				      	}
		      	});
				btnArray[5].setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e) 
						{
				        	tOut.setText("");			        	 
				        	tOut.setText("• When you land on an opponent’s pawn by the count of any die, you capture it.\r\n"
				        			+ "\r\n• Return the captured pawn to its START circle; it must be re-entered later.\r\n"
									+ "\r\n• Pawns cannot be captured on their HOME PATH spaces, or on most Safety spaces.\r\n"
									+ "\r\n• If you capture a pawn after moving on the count on one die, you may continue your move with the same pawn or with another pawn.\r\n"
									+ "\r\n• Capture Bonus: \r\n"
									+ "\t• After capturing a pawn, move any one of yours an additional 20 spaces at the end of you turn. If you capture during a Doublets Bonus move, complete your capture "
									+ "bonus before moving again.\r\n"
									+ "\t\r\n• If you can’t move one pawn the full 20 spaces, you forfeit the bonus.\r\n");
				      	}
		      	});
				btnArray[6].setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e) 
						{
				        	tOut.setText("");			        	 
				        	tOut.setText("• All gray spaces with circles (including ENTER spaces) are Safety spaces. Two \t pawns of different colors can never share a safety space.\r\n"
									+ "\r\n• Pawns cannot be captured on Safety spaces. Exception: if an opponent’s \t pawn occupies your ENTER space, when you enter a pawn, you capture it.\r\n");
				      	}
		      	});
				btnArray[7].setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e) 
						{
				        	tOut.setText("");			        	 
				        	tOut.setText("• Two pawns of the same color on any path space form a blockade. A blockade cannot be landed on, passed or captured by any pawn.\r\n"
									+ "\r\n• If a blockade occupies your ENTER space, you cannot enter a pawn. You may be forced to break up your own blockade if you can’t move any of you other pawns.\r\n"
									+ "\r\n• The two pawns in a blockade cannot be moved forward to form a blockade together on a new space.\r\n");
				      	}
		      	});
				btnArray[8].setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e) 
						{
				        	tOut.setText("");			        	 
				        	tOut.setText("• To reach HOME, move your pawns up your HOME PATH (the red path in front of you) and into the HOME square. You may not move your pawns onto any opponent’s "
									+ "HOME PATH spaces.\r\n"
									+ "\r\n• Each pawn must enter hoe by exact die roll, counting the HOME square as a space. \r\n"
									+ "\r\n• Home Bonus:\r\n"
									+ "\t• After moving a pawn into home, move any one of you pawns an additional 10 spaces at the end of your turn. If you can’t move one pawn the full 10 spaces, you forfeit the bonus.\r\n");
				      	}
		      	});
				btnArray[9].setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e) 
						{
				        	tOut.setText("");			        	 
				        	tOut.setText("• The first player to move all four pawns to HOME wins!");
				      	}
		      	});

				tabs.getChildren().addAll(rLbl1,rLbl2,rLbl3,rLbl4,rLbl5,rLbl6,rLbl7,rLbl8,rLbl9,rLbl10);
				
				// makes tabs clickable and links their data to output window
								
				scRules.setFill(Color.BLACK);
				sRules.setScene(scRules);
				sRules.show();		
			
			});
		
		// start button should close title screen and then start game by setting new primary stage
		btnStart.setOnAction(e-> 
		{			
			//System.
			
		});
		
		// since title screen is a method in class it returns a scene object to be used in main class
		Scene scene = new Scene(root);
		return scene;
		//Scene titleScreen = new SubScene(root)  // Use this for diceroll animation 
	}
}