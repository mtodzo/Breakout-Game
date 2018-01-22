import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Brick {

	private Rectangle myBrick;
	private double myXLoc;
	private double myYLoc;
	//private String myPowerUp;
	private int myHits; //number of hits required to eliminate the brick
	private Paint myColor;
	//private String POWERUP_IMAGE = "";
	//private Image myImage;
	
	public Brick (double xloc, double yloc, double brickLength, double brickWidth, 
			double powerUpPercent, double toughPercent) {
		myBrick = new Rectangle(xloc, yloc, brickWidth, brickLength);
		myXLoc = xloc;
		myYLoc = yloc;
		myBrick.setX(xloc);
		myBrick.setY(yloc);
		myHits = 1;
		myColor = Color.CORNFLOWERBLUE;
		
		if (Math.random() < powerUpPercent) {	
			myColor = Color.DARKGREEN;
		}
		
		else if (Math.random() < toughPercent) {
			myHits = (int) Math.floor(Math.random()*4) + 2;

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
		}

		myBrick.setFill(myColor);

	}
	
	public Rectangle getBrick() {
		return myBrick;
	}
	
	public double getX() {
		return myXLoc;
	}
	
	public double getY() {
		return myYLoc;
	}
	
	public Paint getColor() {
		return myColor;
	}
	
//	public String getPowerUp() {
//		return myPowerUp;
//	}

	public int getHits() {
		return myHits;
	}

	public void setHits(int hits) {
		myHits = hits;
	}
	
	public void setColor(Paint color) {
		this.getBrick().setFill(color);
	}

	//returns the color which corresponds to the number of hits the brick has left
	public Paint getHitColor(int hits) {
		switch (hits) {
		case 2: return Color.CORAL;
		case 3: return Color.CRIMSON;
		case 4: return Color.GOLD;
		case 5: return Color.BROWN;
		}
		return Color.CORNFLOWERBLUE;
	}
	
//	//drop the brick's power-up
//	public ImageView getPowerImageView() {
//		ImageView power = new ImageView(new Image(POWERUP_IMAGE));
//		power.setX(this.getX());
//		power.setY(this.getY());
//		return power;
//	}
}
