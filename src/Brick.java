import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import javafx.scene.paint.Paint;

/*
 * The Brick superclass is used to manage all of the bricks that are created. This class existed when I first submitted my project, but it's
 * function was to store variables and information for it's Rectangle, x and y locations, power-Up, number of hits left, etc. In this refactor,
 * I worked to make this class for active in order to abide by the Do It Yourself (DIY) principal of Object Oriented Programming. Rather than
 * keep most of my methods in the executable Breakout class, I chose to move some of them into this class. The checkForCollision and 
 * handleCollision methods were previously in the Breakout class, but I moved them in here. This gave my code more organization and also
 * enabled me to override handleCollision in each of the subclasses (ToughBrick and PowerBrick) in order to specialize the reaction. 
 * I also was able to remove a number of my getter and setter methods because they had been in use by the Breakout collision checking and 
 * handling methods and were no longer needed.
 * 
 * I wanted to implement inheritance into my project to help with organization, increase flexibility by keeping things in one class, and to
 * practice this skill which is relatively new to me.
 */
public class Brick {

	protected Rectangle myBrick;
	protected double myXLoc;
	protected double myYLoc;
	protected int myHits; //number of hits required to eliminate the brick
	protected Paint myColor;
	
	public Brick (double xloc, double yloc,  double brickLength, double brickWidth) {
		myBrick = new Rectangle(xloc, yloc, brickWidth, brickLength);
		myXLoc = xloc;
		myYLoc = yloc;
	}
	/*
	 * This method checks to see if any of the Bouncers have collided with the Brick. Returns true if a brick needs to be removed.
	 */
	public boolean checkForCollision(ArrayList<Bouncer> bouncers) {
		double bouncerWidth = bouncers.get(0).getImage().getWidth();
		
		for (int x=0; x<bouncers.size(); x++) {
				boolean x_overlap = bouncers.get(x).getX() + bouncerWidth >= myXLoc && bouncers.get(x).getX() <= myXLoc + myBrick.getWidth();
				boolean y_overlap = bouncers.get(x).getY()  >= myYLoc && bouncers.get(x).getY() <= myYLoc + myBrick.getHeight();
				if (x_overlap && y_overlap) {
					return handleCollision(bouncers.get(x));
				}
		}
		return false;
	}
	/*
	 * This method is overridden in the subclasses, but serves to handle the collision between bouncer and brick. It returns true if 
	 * a brick needs to be removed.
	 */
	public boolean handleCollision(Bouncer b) {
		return false;
	}
	/*
	 * This method returns the Rectangle object
	 */
	public Rectangle getBrick() {
		return myBrick;
	}
	/*
	 * This method returns the x-coordinate of the Brick
	 */
	public double getX() {
		return myXLoc;
	}
}
