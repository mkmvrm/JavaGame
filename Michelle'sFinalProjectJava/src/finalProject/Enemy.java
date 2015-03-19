package finalProject;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Enemy {

	int posX; // X COORDINATE PLAYER POSITION
	int posY; // Y COORDINATE PLAYER POSITION
	int speed; // SPEED OF PLAYER MOVEMENT (HOW MANY PIXELS PLAYER MOVES PER
				// UPDATE)
	int width, height;
	int currentHealth;
	int maxHealth;
	
	boolean visible;

	Image img;

	public Enemy(int posX, int posY, int speed, int maxHealth, URL base) {

		this.speed = speed;
		this.posX = posX;
		this.posY = posY;
		this.maxHealth = maxHealth;
		
		visible = true;
		
		try {
			URL url = new URL(base, "img/lsp.png");
			img = ImageIO.read(url);
		} catch (IOException e) {
		}

		height = img.getHeight(null);
		width = img.getWidth(null);
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

	public Image getImage() {
		return img;
	}

	public Rectangle getBounds() {
		return new Rectangle(posX, posY, width, height);
	}

	public void LeftUp() {
		if ((posX - speed) > -150) {
			posX -= speed;
			posY -= 1;
		} else {
		}
	}

	public void LeftDown() {
		if ((posX - speed) > -150) {
			posX -= speed;
			posY += 1;
		} else {
			
		}
	}

	public void RightUp() {
		if ((posX - speed) < 1950) {
			posX += speed;
			posY -= 1;
		} else {
			visible = false;
		}
	}

	public void RightDown() {
		if ((posX - speed) < 1950) {
			posX += speed; 
			posY += 1;
		} else {
			visible = false;
		}
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

}