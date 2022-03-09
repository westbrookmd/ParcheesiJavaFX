package application;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.beans.*;


public class Main extends Application {

   
   public void start(Stage primaryStage) {
   
      
      TitleScreen scene2 = new TitleScreen();
      
      //Scene scene = new Scene(board);
      primaryStage.setTitle("Parcheesi");
      primaryStage.setScene(scene2.setScreen());
      //primaryStage.setScene(scene);
      primaryStage.show();
   }
	public static void main(String[] args) {
		launch(args);
		
		
	}
}
