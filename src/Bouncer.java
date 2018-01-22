import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bouncer {

	private ImageView myImage;
	private double myXSpeed;
	private double myYSpeed;
	private double myXLoc;
	private double myYLoc;
	private int myLastHit;
	private boolean isCaught; //used for catch/release functionality of paddle -- initiated by cheat code
	
	//lastHit indicates which paddle hit the ball last so that power-ups will fall in the correct direction
	public Bouncer(Image image, double xloc, double yloc, double imageHeight, double imageWidth, int lastHit, double initialSpeed, boolean caught) {
		myImage = new ImageView(image);
		myXLoc = xloc;
		myYLoc = yloc;
		setX(xloc);
		setY(yloc);
		//lastHit initially indicates ball 1 or 2 so Xspeed -> negative for player 1 (left), positive for player 2 (right)
		myLastHit = lastHit;
		if (lastHit == 1) {
			myXSpeed = -1*initialSpeed;
		}
		else {
			myXSpeed = initialSpeed;
		}
		myYSpeed = Math.rint((Math.random()+1))*-1*initialSpeed*Math.random();
	}

	public ImageView getImage() {
		return myImage; 
	}

	public double getXSpeed() {
		return myXSpeed;
	}

	public void setXSpeed(double xSpeed) {
		myXSpeed = xSpeed;
	}

	public double getYSpeed() {
		return myYSpeed;
	}

	public void setYSpeed(double ySpeed) {
		myYSpeed = ySpeed;
	}

	public void setX(double x) {
		myImage.setX(x);
	}

	public void setY(double y) {
		myImage.setY(y);
	}

	public double getX() {
		return myImage.getX();
	}

	public double getY() {
		return myImage.getY();
	}
	
	public int getMyLastHit() {
		return myLastHit;
	}
	
	public void setMyLastHit(int last) {
		myLastHit = last;
	}
	public boolean getCaught() {
		return isCaught;
	}
	public void setCaught(boolean caught) {
		isCaught = caught;
	}
}
