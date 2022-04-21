import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class HomeTile {
	private Rectangle base;
	private StackPane pane;
	private Circle[][] circles;

	public HomeTile(double width, double height, double radius) {
		this.base = new Rectangle(width, height);
		this.pane = new StackPane();
		this.circles = new Circle[4][3]; //if we decide to display a circle representing the fourth pawn for the winning player, we can make it it's own circle
		for(int i = 0; i < circles.length; i++) {
			for(int j = 0; j < circles[i].length; j++) {
				circles[i][j] = new Circle(radius);
			}
		}
	}

	public void drawHome() {
		this.base.setStroke(Color.RED);
		this.base.setStrokeWidth(1);
		this.base.setFill(Color.LIGHTBLUE);
	}

	public Pane getHome() {
		Text text = new Text("HOME");
		this.pane.getChildren().add(this.base);
		this.pane.getChildren().add(text);
		StackPane.setAlignment(text, Pos.CENTER);
		text.setFont(Font.font("Algerian", this.base.getHeight()/2));
		this.drawPawns();
		this.pane.setMaxWidth(this.base.getWidth());
		this.pane.setMaxHeight(this.base.getHeight());
		this.pane.setTranslateX(-0.55);
		return pane;
	}

	//move the Home space around as needed
	//TODO: add code to move the circles around with the Home space
	public void setTranslateX(double x) {
		this.base.setTranslateX(x);
		for(int i = 0; i < circles.length; i++) {
			for(int j = 0; j < circles[i].length; j++) {
				circles[i][j].setTranslateX(x);
			}
		}
	}

	public void setTranslateY(double y) {
		this.base.setTranslateY(y);
		for(int i = 0; i < circles.length; i++) {
			for(int j = 0; j < circles[i].length; j++) {
				circles[i][j].setTranslateY(y);
			}
		}
	}

	//draw circles for the home space to represent pawns
	public void drawPawns() {
		Pane pawns = new Pane();
		double centerX = this.base.getX() + (this.base.getWidth()/2);
		double centerY = this.base.getY() + (this.base.getHeight()/2);
		//only three circles per player are absolutely needed, since the game ends when one player moves four pawns to Home space
		//if we decide to have program show the fourth pawn upon victory, 4th pawn should be positioned in the very center of the Home space
		//for players 1 and 3, circle.centerY should remain the same, while players 2 and 4 will have the same circle.centerX

		//player 1 circles
		for(int i = 0; i < circles[0].length; i ++) {
			pawns.getChildren().add(circles[0][i]);
			circles[0][i].setCenterY(centerY - 25);
			circles[0][i].setFill(Color.TRANSPARENT);
			switch(i) {
			case 0:
				circles[0][i].setCenterX(centerX - 35);
				break;
			case 1:
				circles[0][i].setCenterX(centerX);
				break;
			case 2:
				circles[0][i].setCenterX(centerX + 35);
				break;
			}
		}

		//player 2 circles
		for(int i = 0; i < circles[1].length; i ++) {
			pawns.getChildren().add(circles[1][i]);
			circles[1][i].setCenterX(centerX - 60);
			circles[1][i].setFill(Color.TRANSPARENT);
			switch(i) {
			case 0:
				circles[1][i].setCenterY(centerY - 25);
				break;
			case 1:
				circles[1][i].setCenterY(centerY);
				break;
			case 2:
				circles[1][i].setCenterY(centerY + 25);
				break;
			}
		}

		//player 3 circles
		for(int i = 0; i < circles[2].length; i ++) {
			pawns.getChildren().add(circles[2][i]);
			circles[2][i].setCenterY(centerY + 25);
			circles[2][i].setFill(Color.TRANSPARENT);
			switch(i) {
			case 0:
				circles[2][i].setCenterX(centerX - 35);
				break;
			case 1:
				circles[2][i].setCenterX(centerX);
				break;
			case 2:
				circles[2][i].setCenterX(centerX + 35);
				break;
			}
		}

		//player 4 circles
		for(int i = 0; i < circles[3].length; i ++) {
			pawns.getChildren().add(circles[3][i]);
			circles[3][i].setCenterX(centerX + 60);
			circles[3][i].setFill(Color.TRANSPARENT);
			switch(i) {
			case 0:
				circles[3][i].setCenterY(centerY - 25);
				break;
			case 1:
				circles[3][i].setCenterY(centerY);
				break;
			case 2:
				circles[3][i].setCenterY(centerY + 25);
				break;
			}
		}
		this.pane.getChildren().add(pawns);
	}

	//reveal circles as pawns enter home, @param tokenNo used to prevent logic errors resulting from using pawn.getTokenNo()
	public void showPawn(int player, int tokenNo, Pawn pawn) {
		circles[player][tokenNo].setStroke(Color.BLACK);
		circles[player][tokenNo].setFill(pawn.getTokenColor());

	}
}
