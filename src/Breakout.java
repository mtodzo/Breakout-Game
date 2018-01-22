import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

public class Breakout extends Application{
	public static final String TITLE = "Breakout Pong";
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 500;
	public static final Paint BACKGROUND = Color.AZURE;
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final Paint PADDLE_COLOR = Color.BLACK;
	public static final int PADDLE_WIDTH = 20;
	public static final int PADDLE_HEIGHT = 75;
	public static final double BRICK_WIDTH = 10;
	public static final double BRICK_SEPARATION = 1;
	public static final int NUM_BRICKS_COLUMN = 10; //the number of bricks in each column -- determines brick length
	public static final double BRICK_LENGTH = (HEIGHT - (NUM_BRICKS_COLUMN-1)*BRICK_SEPARATION)/NUM_BRICKS_COLUMN;
	public static final double POWER_UP_PERCENTAGE = .2; //percentage of bricks that drop power-ups
	public static final double TOUGH_BRICKS_PERCENTAGE = .2; //percentage of bricks that require multiple hits
	public static final String BOUNCER_IMAGE = "ball.gif";
	public static final double INITIAL_SPEED = 100.0;
	public static final double PADDLE_SPEED = 20.0;
	public static final double POWER_SPEED = 50.0;
	
	private Stage myStage = new Stage();
	private Group root = new Group();
	private Scene myScene;
	private Scene startScene;
	private Group startGroup = new Group();
	private boolean startScreen = true;	//indicates whether the start screen should be displayed
	private boolean game = false;//indicates whether the game should be displayed
	private Paddle paddle1;
	private Paddle paddle2;
	private int level = 1;
	private ArrayList<Brick> bricks = new ArrayList<Brick>();
	private ArrayList<Bouncer> bouncers = new ArrayList<Bouncer>();
	private ArrayList<Power> powerUps = new ArrayList<Power>();
	private ArrayList<Paddle> paddles = new ArrayList<Paddle>();
	private double bouncerHeight;
	private double bouncerWidth;
	private boolean catchAndRelease1 = false; //indicates if catch/release cheat code is initiated for p1
	private boolean catchAndRelease2 = false; //indicates if catch/release cheat code is initiated for p1
	private int p1Score = 0; //p1 controls left paddle
	private int p2Score = 0; //p2 controls right paddle
	private Text intro;
	private Text score1;
	private Text score2;
	private Text winner;
	private boolean start; //controls whether ball moves --> false until screen is clicked to start game
	
	public void start(Stage stage) {
		//Scene is a place to see everything
		if (startScreen) {
			startScene = new Scene(startGroup, WIDTH, HEIGHT, BACKGROUND);
			setupStartScreen();
			myStage.setScene(startScene);
			myStage.setTitle("Welcome!");
			myStage.show();
			KeyFrame startFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
					e -> startStep(SECOND_DELAY));
			Timeline startAnimation = new Timeline();
			startAnimation.setCycleCount(Timeline.INDEFINITE);
			startAnimation.getKeyFrames().add(startFrame);
			startAnimation.play();
		}
		else if (game) {
			myScene = new Scene(root, WIDTH, HEIGHT, BACKGROUND);
			setupGame();
			myStage.setScene(myScene);
			myStage.setTitle(TITLE);
			myStage.show();
			// attach "game loop" to time line to play it
			KeyFrame mainFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
					e -> mainStep(SECOND_DELAY));
			Timeline animation = new Timeline();
			animation.setCycleCount(Timeline.INDEFINITE);
			animation.getKeyFrames().add(mainFrame);
			animation.play();
		}
	}

	private void setupStartScreen() {
		startScene.setOnMouseClicked(e -> handleMouseInputStart(e.getX(), e.getY())); //calls handleMouseInput() on screen clicked event 
		
	}
	
	private void startStep(double elapsedTime) {
		//start screen text
		Text welcome = new Text(WIDTH/2 - 300, HEIGHT/2 - 100, "Welcome to Breakout Pong! Find a worthy opponent because this spin-off of Breakout and Pong "
				+ "is two player! \n\n" + "Player 1: use the WASD keys to control your paddle. \n" + "Player 2: use the arrow keys to control your paddle.\n \n");
		Text powers = new Text(WIDTH/2 - 350, HEIGHT/2,"Power-Ups (green bricks): \n-Big Paddle \n-Small Paddle \n-Fast Ball \n-Slow Ball");
		Text bricks = new Text(WIDTH/2 - 120, HEIGHT/2, "Bricks: \n1 hit = Blue \n2 hits = Coral \n3 hits = Crimson \n4 hits = Gold \n5 hits = Brown");
		Text cheats = new Text(WIDTH/2 + 50, HEIGHT/2, "Cheat Codes: \nQ/P - initiates Catch and Release (Q for player 1, P for player 2)\n" + 
				"E/O - paddle takes up the entire side (invincible)\n" + 
				"SPACE - increase ball speed");
		startGroup.getChildren().add(welcome);
		startGroup.getChildren().add(powers);
		startGroup.getChildren().add(bricks);
		startGroup.getChildren().add(cheats);
	}
	
	private void setupGame() {
		//check if either player has won the required 2 out of 3 games
		if (p1Score == 2) {
			winner = new Text(WIDTH/2-100, HEIGHT/2, "PLAYER 1 IS THE WINNER!!! \nFinal Score: " + p1Score + " to "+ p2Score);
			root.getChildren().add(winner);
			return;
		}
		else if (p2Score == 2) {
			winner = new Text(WIDTH/2-50, HEIGHT/2, "PLAYER 2 IS THE WINNER!!! \nFinal Score: " + p2Score + " to "+ p1Score);
			root.getChildren().add(winner);
			return;
		}
		start = false; //don't want the ball to move until screen is clicked
		
		
		
		//create the paddle1 and set properties
		paddle1 = new Paddle (1, 0, HEIGHT / 2 - PADDLE_HEIGHT/2, PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_SPEED, PADDLE_COLOR);
	
		//create the paddle2 and set properties
		paddle2 = new Paddle (2, WIDTH - PADDLE_WIDTH, HEIGHT/2-PADDLE_HEIGHT/2, PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_SPEED, PADDLE_COLOR);
		
		paddles.add(paddle1);
		paddles.add(paddle2);
		
		//add paddles to group
		root.getChildren().add(paddle1.getRect());
		root.getChildren().add(paddle2.getRect());
		
		//get arrayList of bricks
		bricks = getBrickArrayList(bricks, level);
		
		//add bricks to group
		for (int x=0; x<bricks.size(); x++) {
			root.getChildren().add(bricks.get(x).getBrick());
		}
		
		Image bouncerImage = new Image(getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        bouncerHeight = bouncerImage.getHeight();
        bouncerWidth = bouncerImage.getWidth();
        
        //add bouncers to array list
        bouncers = getBouncers(bouncerImage, bouncerWidth, bouncerHeight, bouncers);
		
		//add bouncers to group
		root.getChildren().add(bouncers.get(0).getImage());
		root.getChildren().add(bouncers.get(1).getImage());
		
		//display scores
		score1 = new Text(WIDTH/4 - 100, HEIGHT - 50, "Player 1 score: " + p1Score);
		score2 = new Text(3*WIDTH/4 - 100, HEIGHT - 50, "Player 2 score: " + p2Score);
		root.getChildren().add(score1);
		root.getChildren().add(score2);
		
		intro = new Text(WIDTH/2 - 100, HEIGHT/2, "Click on the screen to start!");
		root.getChildren().add(intro); //add last so that it is in front
		
		myScene.setOnKeyPressed(e -> handleKeyPadInput(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInputGame(e.getX(), e.getY())); //calls handleMouseInput() on screen clicked event 
		
	}
	
	private void mainStep (double elapsedTime) {
		moveBalls(elapsedTime); //move balls and bounce off walls
		checkForPaddleCollisions(); //check if ball hits paddle -- call paddleCollision()
		checkForBrickCollisions(); //check if ball hits brick -- call brickCollision()
		checkForPowerCollisions(); //check if power-up hits paddle
		movePowerUps(elapsedTime);
		if (checkForWin()) { //check to see if a ball has gone off the screen
			level+=1;
			for (Paddle p: paddles) 
				root.getChildren().remove(p.getRect());
			for (Bouncer b: bouncers)
				root.getChildren().remove(b.getImage());
			for (Brick b: bricks)
				root.getChildren().remove(b.getBrick());
			for (Power p: powerUps)
				root.getChildren().remove(p.getImageView());
			root.getChildren().remove(score1);
			root.getChildren().remove(score2);
			bricks.removeAll(bricks);
			bouncers.removeAll(bouncers);
			paddles.removeAll(paddles);
			powerUps.removeAll(powerUps);
			setupGame();
		}
		
		
	}
	
	private void movePowerUps(double time) {
		for (int x=0; x<powerUps.size(); x++) {
			powerUps.get(x).setX(powerUps.get(x).getX() + powerUps.get(x).getSpeed() * time);
		}
		
	}
	private void moveBalls(double time) {
		if (start) { //only move if screen has been clicked
			for (int x=0; x<bouncers.size(); x++) {
				if (!bouncers.get(x).getCaught()) {
					bouncers.get(x).setX(bouncers.get(x).getX() + bouncers.get(x).getXSpeed() * time);
					bouncers.get(x).setY(bouncers.get(x).getY() + bouncers.get(x).getYSpeed() * time);
					if (bouncers.get(x).getY() > HEIGHT - bouncerHeight || bouncers.get(x).getY() < 0) 
						bouncers.get(x).setYSpeed(-1*bouncers.get(x).getYSpeed());
				}
			}
		}
	}
	
	private boolean checkForWin() {
		for (int x=0 ; x<bouncers.size(); x++) {
			if (bouncers.get(x).getX() > WIDTH) {
				p1Score+=1;
				return true;
			}
			else if (bouncers.get(x).getX() < 0 - bouncerWidth) {
				p2Score+=1;
				return true;
			}
		}
		return false;
	}


	private void checkForPaddleCollisions() {
		//checks for intersection between bouncers and paddles
		for (int x=0; x<bouncers.size(); x++) {
			for (int y=0; y<paddles.size(); y++) {
				boolean x_overlap = (bouncers.get(x).getX() + bouncerWidth >= paddles.get(y).getX()) && (bouncers.get(x).getX() <= paddles.get(y).getX() + paddles.get(y).getWidth());
				boolean y_overlap = (bouncers.get(x).getY() + bouncerHeight >= paddles.get(y).getY()) && (bouncers.get(x).getY() <= paddles.get(y).getY() + paddles.get(y).getLength());
				
				if ((bouncers.get(x).getMyLastHit() == 1 && !catchAndRelease1) || (bouncers.get(x).getMyLastHit() == 2 && !catchAndRelease2)) {
					bouncers.get(x).setCaught(false);
				}
				if (x_overlap && y_overlap){
					if (catchAndRelease1 && paddles.get(x).getPlayer() == 1) {
						bouncers.get(x).setCaught(true);
					}
					
					else if (catchAndRelease2 && paddles.get(x).getPlayer() == 2) {
						bouncers.get(x).setCaught(true);
					}
					else {
						paddleCollision(bouncers.get(x), paddles.get(y));
					}
				}
			}
		}
	}
	
	private void checkForBrickCollisions() {
		//checks for intersection between bouncers and bricks
		for (int x=0; x<bouncers.size(); x++) {
			for (int y=0; y<bricks.size(); y++) {
				boolean x_overlap = bouncers.get(x).getX() + bouncerWidth >= bricks.get(y).getX() && bouncers.get(x).getX() <= bricks.get(y).getX() + BRICK_WIDTH;
				boolean y_overlap = bouncers.get(x).getY() >= bricks.get(y).getY() && bouncers.get(x).getY() <= bricks.get(y).getY() + BRICK_LENGTH;
				if (x_overlap && y_overlap) {
					brickCollision(bouncers.get(x), bricks.get(y));
				}
			}
		}
	}

	private void checkForPowerCollisions() {
		for (int x=0; x<powerUps.size(); x++) {
			for (int y=0; y<paddles.size(); y++) {
				boolean x_overlap = powerUps.get(x).getX() >= paddles.get(y).getX() && powerUps.get(x).getX() <= paddles.get(y).getX() + paddles.get(y).getWidth();
				boolean y_overlap = powerUps.get(x).getY() <= paddles.get(y).getY() + paddles.get(y).getLength() && powerUps.get(x).getY() + powerUps.get(x).getHeight() >= paddles.get(y).getY();
				if (x_overlap && y_overlap) {
					usePower(powerUps.get(x), paddles.get(y));
					break;
				}
			}
		}
	}
	
	//deploys the actual power after the paddle has collected it
	public void usePower(Power power, Paddle paddle) {
		if (power.getPowerUp().equals("bigPaddle")) { 
			root.getChildren().remove(paddle.getRect());
			paddles.remove(paddle);
			if(paddle.getPlayer() == 1) {
				paddle1 = new Paddle(1, paddle.getX(), paddle.getY() - paddle.getLength()/2 , paddle.getLength()*2, paddle.getWidth(), paddle.getSpeed(), paddle.getColor());
				root.getChildren().add(paddle1.getRect());
				paddles.add(paddle1);
			}
			else {
				paddle2 = new Paddle(2, paddle.getX(), paddle.getY() - paddle.getLength()/2 , paddle.getLength()*2, paddle.getWidth(), paddle.getSpeed(), paddle.getColor());
				root.getChildren().add(paddle2.getRect());
				paddles.add(paddle2);
			}
		}
		if (power.getPowerUp().equals("smallPaddle")) {
			root.getChildren().remove(paddle.getRect());
			paddles.remove(paddle);
			if(paddle.getPlayer() == 1) {
				paddle1 = new Paddle(1, paddle.getX(), paddle.getY() + paddle.getLength()/4 , paddle.getLength()/2, paddle.getWidth(), paddle.getSpeed(), paddle.getColor());
				root.getChildren().add(paddle1.getRect());
				paddles.add(paddle1);
			}
			else {
				paddle2 = new Paddle(2, paddle.getX(), paddle.getY() + paddle.getLength()/4 , paddle.getLength()/2, paddle.getWidth(), paddle.getSpeed(), paddle.getColor());
				root.getChildren().add(paddle2.getRect());
				paddles.add(paddle2);
			}
		}
		if (power.getPowerUp().equals("fastBall")) {
			power.getBouncer().setXSpeed(2.5*power.getBouncer().getXSpeed());
			power.getBouncer().setYSpeed(2.5*power.getBouncer().getYSpeed());
		}
		if (power.getPowerUp().equals("slowBall")) {
			power.getBouncer().setXSpeed(.3*power.getBouncer().getXSpeed());
			power.getBouncer().setYSpeed(.3*power.getBouncer().getYSpeed());
		}
		//removed invisible brick power up b/c it interfered with having specific brick colors for number of hits left
//		if (power.getPowerUp().equals("invisible")) {
//			for (int x=0; x<bricks.size(); x++) {
//				root.getChildren().remove(bricks.get(x).getBrick());
//				Brick b = new Brick(bricks.get(x).getX(), bricks.get(x).getY(), BRICK_LENGTH, BRICK_WIDTH, 
//						POWER_UP_PERCENTAGE, TOUGH_BRICKS_PERCENTAGE);
//				bricks.remove(x);
//				bricks.add(x, b);
//				root.getChildren().add(bricks.get(x).getBrick());
//			}
//		}
		powerUps.remove(power);
		root.getChildren().remove(power.getImageView());
	}
	
	//what to do when mouse is clicked on start screen
	private void handleMouseInputStart(double x, double y) {
		if (x<WIDTH && y<HEIGHT) { //check that click is within the window
			startScreen = false;
			game = true;
			start(myStage);
		}
	}
	
	//what to do when mouse is clicked on game screen
		private void handleMouseInputGame(double x, double y) {
			if (x<WIDTH && y<HEIGHT) { //check that click is within the window
				start = true;
				root.getChildren().remove(intro); //remove intro text
			}
		}
	// What to do each time a key is pressed
	private void handleKeyPadInput (KeyCode code) {
		if (code == KeyCode.RIGHT && (paddle2.getX() + paddle2.getWidth()) < WIDTH) {
			for (int x=0; x<bouncers.size(); x++) {
				if (catchAndRelease2 && bouncers.get(x).getCaught() == true && bouncers.get(x).getMyLastHit() == 2) {
					bouncers.get(x).setX(bouncers.get(x).getX() + paddle2.getSpeed());
				}
			}

			paddle2.setX(paddle2.getX() + paddle2.getSpeed());
		}
		else if (code == KeyCode.LEFT && paddle2.getX() > 0) {
			for (int x=0; x<bouncers.size(); x++) {
				if (catchAndRelease2 && bouncers.get(x).getCaught() == true && bouncers.get(x).getMyLastHit() == 2) {
					bouncers.get(x).setX(bouncers.get(x).getX() - paddle2.getSpeed());
				}
			}
			paddle2.setX(paddle2.getX() - paddle2.getSpeed());
		}
		else if (code == KeyCode.UP && paddle2.getY() > 0) {
			for (int x=0; x<bouncers.size(); x++) {
				if (catchAndRelease2 && bouncers.get(x).getCaught() == true && bouncers.get(x).getMyLastHit() == 2) {
					bouncers.get(x).setY(bouncers.get(x).getY() - paddle2.getSpeed());
				}
			}
			paddle2.setY(paddle2.getY() - paddle2.getSpeed());
		}
		else if (code == KeyCode.DOWN &&  paddle2.getY() + paddle2.getLength()< HEIGHT) {
			for (int x=0; x<bouncers.size(); x++) {
				if (catchAndRelease2 && bouncers.get(x).getCaught() == true && bouncers.get(x).getMyLastHit() == 2) {
					bouncers.get(x).setY(bouncers.get(x).getY() + paddle2.getSpeed());
				}
			}
			paddle2.setY(paddle2.getY() + paddle2.getSpeed());
		}

		else if (code == KeyCode.D && (paddle1.getX() + paddle1.getWidth()) < WIDTH) {
			for (int x=0; x<bouncers.size(); x++) {
				if (catchAndRelease1 && bouncers.get(x).getCaught() == true && bouncers.get(x).getMyLastHit() == 1) {
					bouncers.get(x).setX(bouncers.get(x).getX() + paddle1.getSpeed());
				}
			}
			paddle1.setX(paddle1.getX() + paddle1.getSpeed());
		}
		else if (code == KeyCode.A && paddle1.getX() > 0) {
			for (int x=0; x<bouncers.size(); x++) {
				if (catchAndRelease1 && bouncers.get(x).getCaught() == true && bouncers.get(x).getMyLastHit() == 1) {
					bouncers.get(x).setX(bouncers.get(x).getX() - paddle1.getSpeed());
				}
			}
			paddle1.setX(paddle1.getX() - paddle1.getSpeed());
		}
		else if (code == KeyCode.W && paddle1.getY() > 0) {
			for (int x=0; x<bouncers.size(); x++) {
				if (catchAndRelease1 && bouncers.get(x).getCaught() == true && bouncers.get(x).getMyLastHit() == 1) {
					bouncers.get(x).setY(bouncers.get(x).getY() - paddle1.getSpeed());
				}
			}
			paddle1.setY(paddle1.getY() - paddle1.getSpeed());
		}
		else if (code == KeyCode.S && paddle1.getY() + paddle1.getLength() < HEIGHT) {
			for (int x=0; x<bouncers.size(); x++) {
				if (catchAndRelease1 && bouncers.get(x).getCaught() == true && bouncers.get(x).getMyLastHit() == 1) {
					bouncers.get(x).setY(bouncers.get(x).getY() + paddle1.getSpeed());
				}
			}
			paddle1.setY(paddle1.getY() + paddle1.getSpeed());
		}
		else if (code == KeyCode.Q) {
			catchAndRelease1 = !catchAndRelease1;
		}
		else if (code == KeyCode.P) {
			catchAndRelease2 = !catchAndRelease2;
		}
		else if (code == KeyCode.SPACE) {
			for (int x=0; x<bouncers.size(); x++) {
				if (bouncers.get(x).getXSpeed() < 0)
					bouncers.get(x).setXSpeed(bouncers.get(x).getXSpeed() - 50);
				else {
					bouncers.get(x).setXSpeed(bouncers.get(x).getXSpeed() + 50);
				}
				if (bouncers.get(x).getYSpeed() < 0)
					bouncers.get(x).setYSpeed(bouncers.get(x).getYSpeed() - 50);
				else {
					bouncers.get(x).setYSpeed(bouncers.get(x).getYSpeed() + 50);
				}
			}
		}
		else if (code == KeyCode.E) {
			root.getChildren().remove(paddle1.getRect());
			paddles.remove(paddle1);
			paddle1 = new Paddle(1, paddle1.getX(), 0 , HEIGHT, paddle1.getWidth(), paddle1.getSpeed(), paddle1.getColor());
			root.getChildren().add(paddle1.getRect());
			paddles.add(paddle1);
		}
		
		else if (code == KeyCode.O) {
			root.getChildren().remove(paddle2.getRect());
			paddles.remove(paddle2);
			paddle2 = new Paddle(2, paddle2.getX(), 0 , HEIGHT, paddle2.getWidth(), paddle2.getSpeed(), paddle2.getColor());
			root.getChildren().add(paddle2.getRect());
			paddles.add(paddle2);
		}
	}


	private void paddleCollision (Bouncer b, Paddle p) {
		b.setMyLastHit(p.getPlayer());
		//if the ball hits one of the outer thirds of the paddle redirect it back where it came from
		if (b.getY() >= p.getY() + p.getLength()/3 || b.getY() <= p.getY() + 2*p.getLength()/3) {
			b.setXSpeed(-1*b.getXSpeed());
			b.setYSpeed(-1*b.getYSpeed());
		}
		//if the ball hits the middle third of the paddle continue in same y-direction
		else if (b.getY() > p.getY() + p.getLength()/3 && b.getY() < p.getY() + 2*p.getLength()/3) {
			b.setXSpeed(-1*b.getXSpeed());
		}
	}
	
	//handle collisions between ball and brick
	//remove brick/reduce num hits left
	private void brickCollision (Bouncer b, Brick brick) {
		b.setXSpeed(-1*b.getXSpeed());
		if (brick.getColor().equals(Color.DARKGREEN)) { // if it is a power brick
			Power p = new Power(b.getMyLastHit(), brick.getX(), brick.getY(), POWER_SPEED, b);
			if (p.getPowerUp().equals("addBall")){ //handle addBall here since no power icon is dropped
				Bouncer bounce = new Bouncer (new Image(BOUNCER_IMAGE), b.getX(), b.getY(), bouncerWidth, bouncerHeight, b.getMyLastHit(), -1*b.getXSpeed(), false);
				bouncers.add(bounce);
				root.getChildren().add(bounce.getImage());
			}
			else{
				powerUps.add(p);
				root.getChildren().add(p.getImageView()); //adding the last element of the arrayList (new power-up) to group
			}
				
		}
		brick.setHits(brick.getHits() - 1);
		if (brick.getHits() == 0) {
			bricks.remove(brick);
			root.getChildren().remove(brick.getBrick());
		}
		brick.setColor(brick.getHitColor(brick.getHits()));
	}
	
	//adding bricks to ArrayList of bricks -- power/tough bricks set in brick class
	private ArrayList<Brick> getBrickArrayList(ArrayList<Brick> bricks, int level) {
			for (int col=1; col<=level*2; col++) {
				for(int row=0; row<HEIGHT/BRICK_LENGTH - 1; row++) {
					//x location of the brick -> function of level (determines how many columns there are)
					double xloc = WIDTH/2 - PADDLE_WIDTH + (col-level)*PADDLE_WIDTH;
					//y location of the brick -> function of brickLength and how row (how many bricks are already in column)
					double yloc = (BRICK_LENGTH+BRICK_SEPARATION)*row;
					bricks.add(new Brick(xloc, yloc, BRICK_LENGTH, BRICK_WIDTH, POWER_UP_PERCENTAGE*level, TOUGH_BRICKS_PERCENTAGE*level));
				}
			}
			return bricks;
	}
	//create bouncers with properties
	private ArrayList<Bouncer> getBouncers (Image image, double bWidth, double bHeight, ArrayList<Bouncer> bouncer){
		//get position of first brick column and place ball halfway between that and the close edge of window
        double x1 = bricks.get(0).getX()/2; 
		Bouncer ball1 = new Bouncer (image, x1, HEIGHT/2- bHeight/2, bouncerWidth, bouncerHeight, 1, INITIAL_SPEED, false);
		
		//get location of further edge of last column of bricks and then move it over half the distance between that and the far edge of window
		double x2 = (bricks.get(bricks.size()-1).getX() + BRICK_WIDTH) + (WIDTH - bricks.get(bricks.size()-1).getX() + BRICK_WIDTH)/2 - bouncerWidth;
		Bouncer ball2 = new Bouncer (image, x2, HEIGHT/2 - bHeight/2, bouncerWidth, bouncerHeight, 2, INITIAL_SPEED, false);
		bouncer.add(ball1);
		bouncer.add(ball2);
		return bouncer;
	}

	public static void main (String[] args) {
		launch(args);
	}
}
