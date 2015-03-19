package finalProject;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Player {

	int posX; // X COORDINATE PLAYER POSITION
	int posY; // Y COORDINATE PLAYER POSITION
	int speed; // SPEED OF PLAYER MOVEMENT (HOW MANY PIXELS PLAYER MOVES PER UPDATE)
	
	int width, height;
	
	int maxHealth = 3;
    int currentHealth = maxHealth;
	
	static final int MOVEMAXIMGS = 3;
	
	boolean visible;
	boolean toggleJump;
	
	Image[] moveLeftImgs = new Image[MOVEMAXIMGS];
	Image[] moveRightImgs = new Image[MOVEMAXIMGS];

	public Player(int posX, int posY, int speed, int maxHealth, URL base) {

		this.speed = speed;
		this.posX = posX;
		this.posY = posY;
		this.maxHealth = maxHealth;
		
		visible = true;
		toggleJump = true;
		
		for(int i = 0; i < 3; i++){
			try {
				URL url = new URL(base, "img/" + (i+1) + ".png");
				moveLeftImgs[i] = ImageIO.read(url);
			} catch (IOException e) {
			}	
		}
		for(int i = 0; i < 3; i++){
			try {
				URL url = new URL(base, "img/r" + (i+1) + ".png");
				moveRightImgs[i] = ImageIO.read(url);
			} catch (IOException e) {
			}	
		}	
		
		height = moveLeftImgs[0].getHeight(null);
		width =  moveLeftImgs[0].getWidth(null);
	}
	
	public boolean getToggleJump(){
		return toggleJump;
	}
	public boolean setToggleJump(){
		return toggleJump;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getBounds() {
		return new Rectangle(posX, posY, width, height);
	}
	
	public Image[] getMoveLeftImgs(){
		return moveLeftImgs;
	}
	
	public Image[] getMoveRightImgs(){
		return moveRightImgs;
	}

	public int getPX() {
		return posX;
	}

	public int getPY() {
		return posY;
	}
	
	public void setHealth(int health){
		this.currentHealth = health;
	}
	
	public int getHealth(){
		return currentHealth;
	}

	public void JumpUp() {
		if ((posY - speed) > 400) {	
			posY -= speed;
		} else
		toggleJump = false;		
	}
	
	public void JumpDown() {
		if ((posY - speed) < 560) {
			
		posY += speed;
		} else
		toggleJump = true;
	}

	public void Left() {
		if ((posX - speed) > -150) {
			posX -= speed;
		} else {
		}
	}

	public void Right() {
		if ((posX - speed) < 1550) {
			posX += speed;
		} else {
		}
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}
}