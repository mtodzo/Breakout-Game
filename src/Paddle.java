import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle {
	private double myLength;
	private double myWidth;
	private double mySpeed;
	private Paint myColor;
	private Rectangle myRect;
	private int myPlayer; //indicates player 1 or player 2 -- used so that power can fall in the right direction
	
	public Paddle (int player, double x, double y, double length, double width, double speed, Paint color) {
		myRect = new Rectangle (x, y, width, length);
		myLength = length;
		myWidth = width;
		mySpeed = speed;
		myColor = color;
		myRect.setFill(myColor);
		myPlayer = player;
	}

	public int getPlayer() {
		return myPlayer;
	}
	public double getX() {
		return myRect.getX();
	}
	
	public double getY() {
		return myRect.getY();
	}
	
	public Rectangle getRect() {
		return myRect;
	}
	public double getSpeed() {
		return mySpeed;
	}
	
	public double getWidth() {
		return myWidth;
	}
	
	public double getLength() {
		return myLength;
	}
	
	public Paint getColor() {
		return myColor;
	}
	
	public void setLength(double l) {
		myLength = l;
	}
	
	public void setSpeed(double l) {
		myLength = l;
	}
	
	public void setX(double x) {
		myRect.setX(x);
	}
	
	public void setY(double y) {
		myRect.setY(y);
	}
}
