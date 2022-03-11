//Pawn class programmed by Christopher Smith

import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class Pawn extends Circle {
 protected Circle token;
 protected Color defColor;
 protected Color spaceColor;
 protected int currentSpace;
 protected boolean active;
 
/* token constructor; spaceColor is to indicate which spaces on the board match the tokens; spaceColors match to tokenColors in the following way:
    tokenColor  : spaceColor
    PURPLE      : LIGHTPURPLE
    GREEN       : PALEGREEN
    YELLOW      : KHAKI
    ORANGE      : LIGHTORANGE
*/
 public Pawn(double radius, Color tokenColor, Color spaceColor) {
    //this.currentSpace = null;
    this.token = new Circle(radius, tokenColor);
    this.defColor = tokenColor;
    this.spaceColor = spaceColor;
    this.currentSpace = 0;
    this.active = false;
 }
 
 //method to automatically resize tokens as the screen resizes.
 protected void resizeToken(double radius) {
    this.token.setRadius(radius);
 }
}