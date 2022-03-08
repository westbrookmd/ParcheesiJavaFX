import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.beans.*;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.image.Image;  
import javafx.scene.image.ImageView; 
import javafx.application.Application; 
import javafx.scene.Group;  
import javafx.scene.Scene; 
import javafx.scene.image.PixelReader; 
import javafx.scene.image.PixelWriter; 
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color; 
import javafx.stage.Stage; 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;


public class RollDice extends Application {

@Override
   public void start(Stage primaryStage){
   
      //create Borderpane to hold all components of GUI
      BorderPane program = new BorderPane();
          
      //create VBox that will hold images of the dice values
      VBox diceWindow = new VBox();
  //    Label placeholder = new Label("This is where the dice will be displayed to the player");
      Button myButton = new Button("Roll"); 
      myButton.setAlignment(Pos.CENTER);
      Rectangle test = new Rectangle(100, 500, Color.RED);
      diceWindow.getChildren().add(test);
      diceWindow.setAlignment(Pos.CENTER);
      //test code added to determine total size of vbox
      diceWindow.setStyle("-fx-border-color: black;"
        + "-fx-border-width: 5;");
    /* 
     
      HBox hbox1 = new HBox();
      Label label1 = new Label("Display Dice Below"); //show text
      Button myButton = new Button("Roll"); //My addition button
      hbox1.getChildren().addAll(label1);
      BorderPane borderPane1 = new BorderPane();
      borderPane1.setTop(hbox1);
      borderPane1.setRight(myButton);
      int width = 300;
      int height = 300;
  //   Scene scene = new Scene(borderPane1,width,height);
    // primaryStage.setScene(scene);
    //  primaryStage.show(); //show the Stage
     
      
        //set components inside GUI borderbox
      program.setRight(diceWindow);
      program.setMargin(diceWindow, new Insets(5));
            
      program.setPrefWidth(480);
      program.setPrefHeight(480);
      */
      
      //need to find a way to ensure minimum size will always leave all components of the program 
      //visible, considering having min size be 1280 x 720 but final min size should be able to 
      //contain entire GUI while being readable
   /*
      Scene scene = new Scene(program);
      primaryStage.setTitle("Parcheesi Board");
      primaryStage.setScene(scene);
      primaryStage.show();
     */ 
    
  //die images
   Image die1 = new Image("C:/Parcheesi/dice1.png"); 
   Image die2 = new Image("C:/Parcheesi/dice2.png");
   Image die3 = new Image("C:/Parcheesi/dice3.png");
   Image die4 = new Image("C:/Parcheesi/dice4.png");
   Image die5 = new Image("C:/Parcheesi/dice5.png");
   Image die6 = new Image("C:/Parcheesi/dice6.png");
 
  
        //Setting the image view 1 
      ImageView imageView1 = new ImageView(die1); 
            //Setting the position of the image 
      imageView1.setX(0); 
      imageView1.setY(0); 
            //setting the fit height and width of the image view 
      imageView1.setFitHeight(100); 
      imageView1.setFitWidth(100);         
            //Setting the preserve ratio of the image view 
      imageView1.setPreserveRatio(true); 
      
         
      //Setting the image view 2 
      ImageView imageView2 = new ImageView(die2);
            //Setting the position of the image 
      imageView2.setX(200); 
      imageView2.setY(200); 
            //setting the fit height and width of the image view 
      imageView2.setFitHeight(100); 
      imageView2.setFitWidth(100);          
            //Setting the preserve ratio of the image view 
      imageView2.setPreserveRatio(true); 
         
         
      //Setting the image view 3 
      ImageView imageView3 = new ImageView(die3);  
            //Setting the position of the image 
      imageView3.setX(300); 
      imageView3.setY(200); 
            //setting the fit height and width of the image view 
      imageView3.setFitHeight(100); 
      imageView3.setFitWidth(100);         
            //Setting the preserve ratio of the image view 
      imageView3.setPreserveRatio(true);  
      
      
        //Setting the image view 4 
      ImageView imageView4 = new ImageView(die4); 
            //Setting the position of the image 
      imageView1.setX(300); 
      imageView1.setY(200); 
            //setting the fit height and width of the image view 
      imageView1.setFitHeight(100); 
      imageView1.setFitWidth(100);         
            //Setting the preserve ratio of the image view 
      imageView1.setPreserveRatio(true); 


        //Setting the image view 5 
      ImageView imageView5 = new ImageView(die5); 
        //Setting the position of the image 
      imageView1.setX(250); 
      imageView1.setY(500); 
           //setting the fit height and width of the image view 
      imageView1.setFitHeight(100); 
      imageView1.setFitWidth(100);         
            //Setting the preserve ratio of the image view 
      imageView1.setPreserveRatio(true); 


  //Setting the image view 6 
      ImageView imageView6 = new ImageView(die6); 
            //Setting the position of the image 
      imageView1.setX(500); 
      imageView1.setY(500); 
            //setting the fit height and width of the image view 
      imageView1.setFitHeight(100); 
      imageView1.setFitWidth(100);         
            //Setting the preserve ratio of the image view 
      imageView1.setPreserveRatio(true); 

           //setting button action
      myButton.setOnAction(new EventHandler<ActionEvent>(){
         @Override
         public void handle(ActionEvent event){
            System.out.println("The button action is working.");
         }
      });
      

  
      //Creating a Group object  
      Group root = new Group(imageView3, imageView2, myButton);  
      
      //Creating a scene object 
      Scene scene = new Scene(root, 600, 600);  
      
      primaryStage.setTitle("Roll Dice");
      primaryStage.setScene(scene);
      primaryStage.show();

/*
      HBox hbox1 = new HBox();
      Label label1 = new Label("Display Dice Below"); //show text
     // Button myButton = new Button("Roll"); //My addition button
      hbox1.getChildren().addAll(label1);
      BorderPane borderPane1 = new BorderPane();
      borderPane1.setTop(hbox1);
      borderPane1.setRight(myButton);
      int width = 300;
      int height = 300;
      Scene scene1 = new Scene(borderPane1,width,height);
      primaryStage.setScene(scene1);
      primaryStage.show(); //show the Stage
*/
}
}


//Natalie Young
      class  Die {
   //Data Fields
   private int sides;
   private int top;
   
   //Constructor
  public Die(int sides, int top){
   this.sides = sides;
   this.top = top;
  }
   
  public int getTop(){
   return top;
   } 
   
  public void setTop(int top){
   if (top > 0 && top <= sides){
      this.top = top;
   }
  }
  
  //GamePlay method
  public void roll() {  
       top = 1 + (int)(Math.random() * sides);
    }
  
  public static void main(String[] args){
   Die d = new Die(6, 1);
   System.out.println(d.getTop());
   for (int i = 0; i < 10; i++){
      d.roll();
      System.out.println(i + ": " + d.getTop());
   }
  }  
}






//public static void main (String args[]){ launch(args);}
//  private Roller clock;
   /*
   private class Roller extends AnimationTimer{
   
    private Roller clock;
     Button die = new Button();
     die.setGraphic(dieView);
     private long FRAMES_PER_SEC = 50L;
     private long INTERVALS = 1000000000L / FRAMES_PER_SEC;
     
     private long last = 0;
     
     @Override
     public void handle (long now){
      if (now - last > INTERVAL) {
         int r = 2 + (int)(Math.random() * 5);
         setDieImage9r0; 
         last = now;
      }
     } 
   }
   */
   
