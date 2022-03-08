//Natalie Young

public class Die {
	//Data Fields
	private int top;

	//Constructor
	public Die() {

	}

	public int getTop(){
		return top;
	}

	//GamePlay method
	public int roll() {
      top = 1 + (int)(Math.random() * 6);
      return top;
	}

   //test the die rolling method
	public static void main(String[] args){
		Die d = new Die();
		for (int i = 0; i < 10; i++){
			System.out.println(i + ": " + d.roll());
		}
	}

}