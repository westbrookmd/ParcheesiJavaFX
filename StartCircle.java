import java.util.ArrayList;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class StartCircle extends StackPane {
	private Circle base;
	public ArrayList<Pawn> pawns;
	private Circle[] tokens;
	private StackPane pane;
	private Color color;

	public StartCircle() {
		this.pawns = new ArrayList<Pawn>();
		this.base = new Circle();
		this.pane = new StackPane();
		this.tokens = new Circle[4];
	}
	
	public void setStroke(Color color) {
		this.color = color;
		this.base.setStroke(color);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public void setFill(Color color) {
		this.base.setFill(color);
	}

	public void setRadius(double radius) {
		this.base.setRadius(radius);
	}

	// check if any pawns are at start
	public boolean isEmpty() {
		return this.pawns.isEmpty();
	}

	public StackPane drawStart() {
		return this.pane;
	}

	public void drawBase() {
		this.pane.getChildren().add(base);
	}

	// first-time setup for start circles; draw each pawn and position them inside circle
	public void setupPawn(Pawn pawn) {
		pawn.inStartingArea = true;
		tokens[pawn.getTokenNo()] = pawn.token;
		this.drawPawn(pawn.token, pawn.getTokenColor());
		positionPawn(pawn.getTokenNo(), pawn.token);
	}

	// initial setup for pawns, draw them then hide/reveal as needed
	private void drawPawn(Circle pawn, Color color) {
		pawn.setStroke(Color.BLACK);
		pawn.setFill(color);
	}

	// add a pawn to start space
	public void addPawn(Pawn pawn) {
		setupPawn(pawn);
		this.pawns.add(pawn);
		pawn.setLocation(-1);
		tokens[pawn.getTokenNo()] = pawn.token;
		pawn.inStartingArea = true;
		pane.getChildren().add(pawn.token);
		this.showPawn(pawn);
	}

	// when returning a pawn to start, display the pawn inside circle
	public void showPawn(Pawn pawn) {
		Circle token = tokens[pawn.getTokenNo()];
		token.setStroke(Color.BLACK);
		token.setFill(pawn.getTokenColor());
	}

	// remove a pawn from start
	public void removePawn(Pawn token) {
		token.inStartingArea = false;
		this.pawns.remove(token);
		this.tokens[token.getTokenNo()] = null;
		dePositionPawn(token.getTokenNo(), token.token);
	}

	// hide the pawn once it leaves start
	public void hidePawn(int x) {
		Circle pawn = tokens[x];
		pawn.setStroke(Color.TRANSPARENT);
		pawn.setFill(Color.TRANSPARENT);
	}

	// position pawns inside base circle
	public void positionPawn(int x, Circle token) {
		tokens[x] = token;

		if(x == 0) {
		token.setTranslateX(-this.base.getScaleX()*25);
		token.setTranslateY(-this.base.getScaleY()*25);
		}
		else if(x == 1) {
		token.setTranslateX(+this.base.getScaleX()*25);
		token.setTranslateY(-this.base.getScaleY()*25);
		}
		else if(x == 2) {
		token.setTranslateX(-this.base.getScaleX()*25);
		token.setTranslateY(+this.base.getScaleY()*25);
		}
		else {
		token.setTranslateX(+this.base.getScaleX()*25);
		token.setTranslateY(+this.base.getScaleY()*25);
		}
	}

	public void dePositionPawn(int x, Circle token) {
		tokens[x] = token;
		token.setTranslateX(0);
		token.setTranslateY(0);
	}

	// resize pawns inside start circle
	public void resizePawns(double radius) {
		for (int i = 0; i < tokens.length; i++) {
			tokens[i].setRadius(radius);
		}
	}
}