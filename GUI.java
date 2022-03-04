//GUI Class programmed by Christopher Smith

import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.beans.*;

public class GUI extends Application {
   
   public void start(Stage primaryStage) {
      //create Borderpane to hold all components of GUI
      BorderPane program = new BorderPane();
      
      //create the game board
      GridPane game = new BoardBuilder().boardBuilder(primaryStage);
      
      //code for testing the size and resizing of the main game grid
      //final goal is to set the game where all nodes are visible when window is set to minimum size of 480 x 360
//       game.setPrefWidth(480);
//       game.setPrefHeight(360);
//       game.setMaxWidth(480);
//       game.setMaxHeight(360);
      //game.setStyle("-fx-border-color: green;"
      //  + "-fx-border-width: 5;");
      
      //create VBox that will hold images of the dice values
      VBox diceWindow = new VBox();
      Label placeholder = new Label("This is where the dice will be displayed to the player");
      Rectangle test = new Rectangle(100, 500, Color.RED);
      diceWindow.getChildren().add(test);
      diceWindow.setAlignment(Pos.CENTER);
      //test code added to determine total size of vbox
      diceWindow.setStyle("-fx-border-color: black;"
        + "-fx-border-width: 5;");
      
      //create HBox that will hold user commands
      HBox buttonBox = new HBox(10);
      Button roll = new Button("Roll");
      Button help = new Button("Rules");
      buttonBox.getChildren().addAll(roll, help);
      buttonBox.setAlignment(Pos.CENTER);
      //test code added to determine total size of hbox; final goal is to try and have buttons scale in size along with the window
      buttonBox.setStyle("-fx-border-color: black;"
        + "-fx-border-width: 5;");
      
      //create menubar which will allow players to exit the game or start a new one 
      //later on might also include a settings option to allow player to adjust various aspects of the game, primarily game music/sound effects
      Menu file = new Menu("File");
      MenuItem newGame = new MenuItem("New Game");
      MenuItem exit = new MenuItem("Exit");
      file.getItems().addAll(newGame, exit);
      MenuBar menu = new MenuBar();
      menu.getMenus().add(file);
      menu.prefWidthProperty().bind(primaryStage.widthProperty());
      
      //set components inside GUI borderbox
      program.setTop(menu);
      program.setMargin(menu, new Insets(0, 0, 15, 0));
      
      program.setRight(diceWindow);
      program.setMargin(diceWindow, new Insets(5));
      
      program.setCenter(game);
      program.setMargin(game, new Insets(0, 5, 0, 50));
      
      buttonBox.setPrefHeight(100);
      program.setBottom(buttonBox);
      program.setMargin(buttonBox, new Insets(5));
      
      program.setPrefWidth(480);
      program.setPrefHeight(480);
      
      //need to find a way to ensure minimum size will always leave all components of the program visible, considering having min size be 1280 x 720 but final min size should be able to contain entire GUI while being readable
   
      Scene scene = new Scene(program);
      primaryStage.setTitle("Parcheesi Board");
      primaryStage.setScene(scene);
      primaryStage.show();
   }
}