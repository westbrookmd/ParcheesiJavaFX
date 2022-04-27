package application;

import javafx.scene.image.Image;

public class Die {
	//Data Fields
	private int top;
	private Image[] dieImages;

	//Constructor
	public Die(){
		top = 6;
		dieImages = new Image[6];
		for(int i = 0; i < dieImages.length; i++) {
			dieImages[i] = new Image(imagePath(i));
		}
	}
	
	private String imagePath(int i) {
		return "/resources/dice" + (i + 1) + ".png";
	}

	public int getTop(){
		return top;
	}

	//GamePlay method
	public int roll() {
		top = 1 + (int)(Math.random() * 5);
		return top;
	}
	
	public Image showDie() {
		return dieImages[top - 1];
	}

	//test the die rolling method
	public static void main(String[] args){
		Die d = new Die();
		for (int i = 0; i < 10; i++){
			System.out.println(i + ": " + d.roll());
		}
	}

}