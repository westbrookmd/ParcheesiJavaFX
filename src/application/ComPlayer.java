package application;

public class ComPlayer {
	public ComPlayer() {
		//TODO: Have ComPlayer extend Player class
	}
	
	public void takeTurn() {
		/*TODO: Write method for computer player to take their turn
		Computer player will need to roll the dice on their turn and move tokens around
		Check to see if CPU needs to roll again because of doubles
		Some sort of logic to dictate how they move their pieces around. Factors to consider will include:
			Whether they prioritize moving a single piece as far forward as they can, or having multiple pieces move
				If multiple pieces move, how does the CPU determine which piece will move when? will they try to keep pieces relatively close together or will the furthest piece get the higher roll?
			When do they make the move to capture a piece? Should they always do so when the option is there, or should they focus on other moves like creating blockades?
			When do they create blockades, and how do they determine when to destroy a blockade? At the very least, on the turn they break a blockade they should not create any new blockades
			When tokens are in the middle lane, they should prioritze moving those tokens before anything else, especially if they are able to place a piece at Home
			Whenever they are able to do so, they need to always move a piece out of the start position
		Turn ends for CPU either when they are unable to move any more pawns (due to using both dice rolls, because there are no valid moves they can make, etc.) or because they have rolled their third double in a row
		
		Pseudocode for figuring out CPU logic
		if a potential destination can result in a blockade or a capture, AI goes for it
		if(gameTiles[roll1 || roll2 || roll3 + pawn.getLocation()].occupied) {
			if(occupier == thisPlayer) {
				createBlockade();
			}
			else {
				sendToStart();
			}
		}
		
		AI figures out which of it's tokens are the furthest
		*/
	}
}
