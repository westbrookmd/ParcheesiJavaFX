import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class Pawn extends Circle {
	protected Circle token;
	private Color tokenColor;
	private Color spaceColor;
	private int tokenNo;
	private int location;
	protected boolean active;
	protected boolean inMidlane;
	protected boolean inStartingArea;

	/* token constructor; spaceColor is to indicate which spaces on the board match the tokens; spaceColors match to tokenColors in the following way:
    tokenColor  : spaceColor
    PURPLE      : LIGHTPURPLE
    GREEN       : PALEGREEN
    YELLOW      : KHAKI
    ORANGE      : LIGHTORANGE
	 */
	public Pawn(int num, double radius, Color tokenColor, Color spaceColor) {
		this.token = new Circle(radius, tokenColor);
		this.tokenNo = num;
		this.tokenColor = tokenColor;
		this.spaceColor = spaceColor;
		this.location = -1;
		this.active = false;
	}
	
	public int getTokenNo() {
		return this.tokenNo;
	}
	
	public Color getTokenColor() {
		return this.tokenColor;
	}

	public Color getSpaceColor() {
		return spaceColor;
	}
	
	public int getLocation() {
		return this.location;
	}
	
	public void setLocation (int dest) {
		this.location = dest;
	}

	//method to automatically resize tokens as the screen resizes.
	public void resizeToken(double radius) {
		this.token.setRadius(radius);
	}
}