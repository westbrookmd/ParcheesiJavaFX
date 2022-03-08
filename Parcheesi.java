/*Parcheesi Game programmed by:
 * 		Willie Page		
 * 		Christopher Smith
 * 		Marshall Westbrook
 * 		Natalie Young
 */


import javafx.application.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Parcheesi extends Application {
	
	public void start(Stage primaryStage) {
		 GUI gui = new GUI();
		 gui.start(primaryStage);
	    primaryStage.setTitle("Parcheesi");
	    primaryStage.setWidth(1280);
	    primaryStage.setHeight(720);
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}
}