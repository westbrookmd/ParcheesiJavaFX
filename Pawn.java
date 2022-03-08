//Pawn class programmed by Christopher Smith
//Pawn class used to create player tokens and to resize them as needed

import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class Pawn {
   private Circle token;
   protected Color spaceColor;
   
/* token constructor; spaceColor is to indicate which spaces on the board match the tokens; spaceColors match to tokenColors in the following way:
      tokenColor  : spaceColor
      PURPLE      : TBA
      GREEN       : TBA
      YELLOW      : TBA
      ORANGE      : TBA
*/
   public Pawn(double radius, Color tokenColor, Color spaceColor) {
      this.token = new Circle(radius, tokenColor);
      this.spaceColor = spaceColor;
   }
   
   //method to automatically resize tokens as the screen resizes.
   protected void resizeToken(double radius) {
      this.token.setRadius(radius);
   }
}