//Natalie Young

public class Die {
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