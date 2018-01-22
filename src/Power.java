import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Power {
	private String myPowerUp;
	private Image myImage;
	private int myPlayerDirection; //1 == left (for player 1); 2 == right (for player 2)
	private ImageView myImageView;
	private double mySpeed;
	private Bouncer myBouncer; //used if need to change ball speed
	
	public Power (int playerDirection, double x, double y, double speed, Bouncer b) {
		
		int rand = (int) Math.floor(Math.random()*5); //get random number to assign power-up
		switch (rand) {
		case 0: myPowerUp = "bigPaddle";
				myImage = new Image("bigPaddle.gif");
				break;
		case 1: myPowerUp = "smallPaddle";
				myImage = new Image("smallPaddle.gif");
				break;
		case 2: myPowerUp = "fastBall";
				myImage = new Image("fastBall.gif");
				break;
		case 3: myPowerUp = "slowBall";
				myImage = new Image("slowBall.gif");
				break;
//		case 4: myPowerUp = "invisibleBricks";
//				myImage = new Image("invisible.gif");
//				break;
		case 4: myPowerUp = "addBall";
				break;
		}
		myImageView = new ImageView(myImage);
		myImageView.setX(x);
		myImageView.setY(y);
		if (playerDirection == 1) {
			mySpeed = -speed;
		}
		else {
			mySpeed = speed;
		}
		myBouncer = b;
	}
	public int getPlayerDirection() {
		return myPlayerDirection;
	}
	
	public Bouncer getBouncer() {
		return myBouncer;
	}
	
	public String getPowerUp() {
		return myPowerUp;
	}
	
	public double getWidth() {
		return myImage.getWidth();
	}
	public double getHeight() {
		return myImage.getHeight();
	}

	public ImageView getImageView() {
		return myImageView;
	}
	public double getSpeed() {
		return mySpeed;
	}
	public double getX() {
		return myImageView.getX();
	}
	public double getY() {
		return myImageView.getY();
	}

	public void setX(double x) {
		myImageView.setX(x);
	}
	public void setY(double y) {
		myImageView.setY(y);
	}
}
