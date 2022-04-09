import javafx.scene.paint.Color;

public class Player {
	//initialize array of tokens for each player
	private Pawn[] pawns;
	private Color color;
	private int lastGameTileNumberBeforePersonalRoute;
	
	//@param token indicates token color, @param space indicates color of starting space and accessible midlane
	public Player(Color token, Color space, int lastGameTileNumberBeforePersonalRoute)
	{	
		pawns = new Pawn[4];
		for(int i = 0; i < pawns.length; i++) {
			pawns[i] = new Pawn(i, 10, token, space);
		}
		color = space;
		this.lastGameTileNumberBeforePersonalRoute = lastGameTileNumberBeforePersonalRoute;
	}
	
	public Color getColor() {
		return this.color;
	}

	//allow program to grab individual tokens; @param to indicate which token should be moved
	//TODO: Error checking
	public Pawn getToken(int token) {
		return pawns[token];
	}

	public int getLastGameTileNum()
	{
		return lastGameTileNumberBeforePersonalRoute;
	}

	//TODO: Implement method that allows player to move their tokens around based on dice roll
	public void moveToken(int token, Tile start, Tile dest) {
		Pawn selected = this.getToken(token);
		start.removeToken(selected);
		dest.placeToken(selected);
	}
}