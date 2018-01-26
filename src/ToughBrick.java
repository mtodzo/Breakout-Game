import javafx.scene.paint.Color;

/*
 * The ToughBrick class extends Brick to handle all other Bricks (including the normal, one-hit Bricks). It has a handleCollision method which redirects the ball,
 * adjusts myHits and adjusts the color of the brick. The constructor takes an extra parameter which is a random double to determine the intial value of 
 * myHits.
 */
public class ToughBrick extends Brick {
	public ToughBrick (double xloc, double yloc,  double brickLength, double brickWidth, double rand) {
		super (xloc, yloc, brickLength, brickWidth);
		myHits = (int) Math.floor(Math.random()*6);
		
		myColor = Color.CORNFLOWERBLUE;
		switch (myHits) {

		case 2: myColor = Color.CORAL;
				break;
		case 3: myColor = Color.CRIMSON;
				break;
		case 4: myColor = Color.GOLD;
				break;
		case 5: myColor = Color.BROWN;
				break;
		}
		myBrick.setFill(myColor);
	}
	
	/*
	 * redirects bouncer
	 * adjusts myHits
	 * resets color
	 * retturns true if brick is out of hits and needs to be removed
	 */
	@Override
	public boolean handleCollision(Bouncer b) {
		b.setXSpeed(-1*b.getXSpeed());
		myHits -=1;
		setHitsColor(myHits);
		return (myHits <= 0);
	}
	
	/*
	 * takes number of hits left as a parameter and adjusts the color of the brick accordingly.
	 */
	private void setHitsColor(int hits) {
		switch (hits) {
		case 2:myColor = Color.CORAL;
		break;
		case 3: myColor = Color.CRIMSON;
		break;
		case 4: myColor = Color.GOLD;
		break;
		case 5: myColor = Color.BROWN;
		break;
		}
		myBrick.setFill(myColor);
	}
}
