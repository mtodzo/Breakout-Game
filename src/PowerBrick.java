import javafx.scene.paint.Color;
/*
 * The PowerBrick class is a subclass of the Brick class intended to handle the Bricks with power-ups. It has a handleCollision method which
 * overrides that of the Brick class, it sets myColor specifically to Dark Green, and it calls the new usePower method of the Power class (this method
 * also used to be a part of the main class, but I moved it to help with DIY). 
 */
public class PowerBrick extends Brick {
	
	
	public PowerBrick (double xloc, double yloc,  double brickLength, double brickWidth) {
		super (xloc, yloc, brickLength, brickWidth);
		myColor = Color.DARKGREEN;
		myBrick.setFill(myColor);
		
	}
	/*
	 * creates a new Power
	 * calls usePower
	 * returns true because power bricks are always removed after just one hit
	 */
	@Override
	public boolean handleCollision(Bouncer b) {
		b.setXSpeed(-1*b.getXSpeed());
			Power p = new Power(b.getMyLastHit(), myXLoc, myYLoc, b);
			p.usePower(b);
		return true;	
		}
}
