import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Power {
	private String myPowerUp;
	private Image myImage;
	private int myPlayerDirection; //1 == left (for player 1); 2 == right (for player 2)
	private ImageView myImageView;
	private Bouncer myBouncer; //used if need to change ball speed
	private double mySpeed = 50.0;
	
	public Power (int playerDirection, double x, double y, Bouncer b) {
		
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
		case 4: myPowerUp = "addBall";
				break;
		}
		myImageView = new ImageView(myImage);
		myImageView.setX(x);
		myImageView.setY(y);
		if (playerDirection == 1) {
			mySpeed = -mySpeed;
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
	public void usePower(Bouncer b) {
//		if (myPowerUp.equals("bigPaddle")) { 
//			root.getChildren().remove(paddle.getRect());
//			paddles.remove(paddle);
//			if(paddle.getPlayer() == 1) {
//				paddle1 = new Paddle(1, paddle.getX(), paddle.getY() - paddle.getLength()/2 , paddle.getLength()*2, paddle.getWidth(), paddle.getSpeed(), paddle.getColor());
//				root.getChildren().add(paddle1.getRect());
//				paddles.add(paddle1);
//			}
//			else {
//				paddle2 = new Paddle(2, paddle.getX(), paddle.getY() - paddle.getLength()/2 , paddle.getLength()*2, paddle.getWidth(), paddle.getSpeed(), paddle.getColor());
//				root.getChildren().add(paddle2.getRect());
//				paddles.add(paddle2);
//			}
//		}
//		if (myPowerUp.getPowerUp().equals("smallPaddle")) {
//			root.getChildren().remove(paddle.getRect());
//			paddles.remove(paddle);
//			if(paddle.getPlayer() == 1) {
//				paddle1 = new Paddle(1, paddle.getX(), paddle.getY() + paddle.getLength()/4 , paddle.getLength()/2, paddle.getWidth(), paddle.getSpeed(), paddle.getColor());
//				root.getChildren().add(paddle1.getRect());
//				paddles.add(paddle1);
//			}
//			else {
//				paddle2 = new Paddle(2, paddle.getX(), paddle.getY() + paddle.getLength()/4 , paddle.getLength()/2, paddle.getWidth(), paddle.getSpeed(), paddle.getColor());
//				root.getChildren().add(paddle2.getRect());
//				paddles.add(paddle2);
//			}
//		}
//		if (myPowerUp.equals("fastBall")) {
//			power.getBouncer().setXSpeed(2.5*power.getBouncer().getXSpeed());
//			power.getBouncer().setYSpeed(2.5*power.getBouncer().getYSpeed());
//		}
//		if (myPowerUp.equals("slowBall")) {
//			power.getBouncer().setXSpeed(.3*power.getBouncer().getXSpeed());
//			power.getBouncer().setYSpeed(.3*power.getBouncer().getYSpeed());
//		}
//		powerUps.remove(power);
//		root.getChildren().remove(power.getImageView());
//		
	}
}
