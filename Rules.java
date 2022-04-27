import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Rules implements EventHandler<ActionEvent> {
	final ScrollPane sp = new ScrollPane();

	@Override
	public void handle(ActionEvent arg0) {
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
		Button rLbl6 = new Button("Capturing an Opponent�s Pawn");
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
				tOut.setText("� Be first to move all four of your pawns from your START circle to HOME.");
			}
		});		      	
		btnArray[1].setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				tOut.setText("");			        	 
				tOut.setText("� All players roll two dice.\r\n \r\n� Highest roller starts.\r\n \r\n� Play then continues to the left.\r\n"
						+ "\r\n� Players use the roll button to roll the dice.\r\n \r\n� Once pawns are on the board player can decide which pawn to move dependent on the amount shown on the dice.");
			}
		});				
		btnArray[2].setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				tOut.setText("");			        	 
				tOut.setText("� Each pawn must enter before it can start around the gameboard path.\r\n \r\n� On each of your turns, try to enter your pawns by rolling FIVES.\r\n"
						+ "\r\n� Pawns are entered only on die rolls of FIVE; a 5 on one or both dice; or any combination totaling 5 i.e.(4+1 or 3+2).\r\n"
						+ "\r\n� When possible you must enter a pawn. However, if you can't use a five to enter, try to use it for movement. *See Moving Your Entered Pawns*");
			}
		});
		btnArray[3].setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				tOut.setText("");			        	 
				tOut.setText("� Move your entered pawns counterclockwise along the path the number of spaces you roll on the dice; see the arrow on the gameboard diagram. "
						+ "� Move your pawns by the rules below:\r\n"
						+ "\r\n� You may move one or two pawns on your turn. For example, if you roll 4 + 3, you can move one pawn 4 + 3 spaces; or you can move one pawn 4 spaces and another pawn 3 spaces.\r\n"
						+ "\r\n� You may move one or two pawns on your turn. For example, if you roll 4 + 3, you can move one pawn 4 + 3 spaces; or you can move one pawn 4 spaces and another pawn 3 spaces.\r\n"
						+ "\r\n� You may move one or two pawns on your turn. For example, if you roll 4 + 3, you can move one pawn 4 + 3 spaces; or you can move one pawn 4 spaces and another pawn 3 spaces.\r\n");
			}
		});
		btnArray[4].setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				tOut.setText("");			        	 
				tOut.setText("� A roll of matching dice is called doublets.\r\n \r\n� A roll of doublets entitles you to another roll - and may also entitle you to a bonus move.\r\n"
						+ "\r\n� If you roll doublets before all of you pawns are entered, take your turn as usual, then roll again.\r\n"
						+ "\r\n� Doublets Bonus\r\n"
						+ "\t� If you roll doublets after all four of your pawns are entered, use the four numbers on the tops and the bottom of the dice of movement.\r\n"
						+ "\t \r\n� The total of this four-part move is always 14, and can be taken by one pawn or split among 2 or more pawns.\r\n"
						+ "\r\n� Doublets Penalty\r\n"
						+ "\t� The third consecutive time you roll doublets, don�t move your pawns at all, Instead, move your pawn closest to HOME (even if it�s on your Home Path) and return it to your "
						+ "START circle; it must be re-entered later. This ends your turn.\r\n");
			}
		});
		btnArray[5].setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				tOut.setText("");			        	 
				tOut.setText("� When you land on an opponent�s pawn by the count of any die, you capture it.\r\n"
						+ "\r\n� Return the captured pawn to its START circle; it must be re-entered later.\r\n"
						+ "\r\n� Pawns cannot be captured on their HOME PATH spaces, or on most Safety spaces.\r\n"
						+ "\r\n� If you capture a pawn after moving on the count on one die, you may continue your move with the same pawn or with another pawn.\r\n"
						+ "\r\n� Capture Bonus: \r\n"
						+ "\t� After capturing a pawn, move any one of yours an additional 20 spaces at the end of you turn. If you capture during a Doublets Bonus move, complete your capture "
						+ "bonus before moving again.\r\n"
						+ "\t\r\n� If you can�t move one pawn the full 20 spaces, you forfeit the bonus.\r\n");
			}
		});
		btnArray[6].setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				tOut.setText("");			        	 
				tOut.setText("� All gray spaces with circles (including ENTER spaces) are Safety spaces. Two \t pawns of different colors can never share a safety space.\r\n"
						+ "\r\n� Pawns cannot be captured on Safety spaces. Exception: if an opponent�s \t pawn occupies your ENTER space, when you enter a pawn, you capture it.\r\n");
			}
		});
		btnArray[7].setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				tOut.setText("");			        	 
				tOut.setText("� Two pawns of the same color on any path space form a blockade. A blockade cannot be landed on, passed or captured by any pawn.\r\n"
						+ "\r\n� If a blockade occupies your ENTER space, you cannot enter a pawn. You may be forced to break up your own blockade if you can�t move any of you other pawns.\r\n"
						+ "\r\n� The two pawns in a blockade cannot be moved forward to form a blockade together on a new space.\r\n");
			}
		});
		btnArray[8].setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				tOut.setText("");			        	 
				tOut.setText("� To reach HOME, move your pawns up your HOME PATH (the red path in front of you) and into the HOME square. You may not move your pawns onto any opponent�s "
						+ "HOME PATH spaces.\r\n"
						+ "\r\n� Each pawn must enter hoe by exact die roll, counting the HOME square as a space. \r\n"
						+ "\r\n� Home Bonus:\r\n"
						+ "\t� After moving a pawn into home, move any one of you pawns an additional 10 spaces at the end of your turn. If you can�t move one pawn the full 10 spaces, you forfeit the bonus.\r\n");
			}
		});
		btnArray[9].setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				tOut.setText("");			        	 
				tOut.setText("� The first player to move all four pawns to HOME wins!");
			}
		});

		tabs.getChildren().addAll(rLbl1,rLbl2,rLbl3,rLbl4,rLbl5,rLbl6,rLbl7,rLbl8,rLbl9,rLbl10);

		// makes tabs clickable and links their data to output window
		
		sRules.setScene(scRules);
		sRules.show();
	}

}
