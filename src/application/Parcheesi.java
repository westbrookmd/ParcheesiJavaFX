package application;

/*Parcheesi Game programmed by:
 * 		Willie Page		
 * 		Christopher Smith
 * 		Marshall Westbrook
 * 		Natalie Young
 */


import javafx.application.*;
import javafx.stage.Stage;

public class Parcheesi extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		GUI gui = new GUI();
		TitleScreen title = new TitleScreen();
		primaryStage.setTitle("Parcheesi");
		primaryStage.setScene(title.setScreen(primaryStage, gui));
		primaryStage.setWidth(1280);
		primaryStage.setHeight(720);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}