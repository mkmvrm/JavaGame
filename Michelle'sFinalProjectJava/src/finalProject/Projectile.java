package finalProject;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Projectile {

	int posX; // X COORDINATE PROJECTILE POSITION
	int posY; // Y COORDINATE PROJECTILE POSITION
	int speed = 10; // SPEED OF PROJECTILE MOVEMENT

	int width, height;

	int size = 20;
	boolean visible;
	
	Image img;

	public Projectile(int posX, int posY, int speed, URL base) {

		this.speed = speed;
		this.posX = posX;
		this.posY = posY;
		
		visible = true;

		try {
			URL url = new URL(base, "img/c.png");
			img = ImageIO.read(url);
		} catch (IOException e) {
		}

		height = img.getHeight(null);
		width = img.getWidth(null);
	}

	public Image getImage() {
		return img;
	}

	public Rectangle getBounds() {
		return new Rectangle(posX, posY, width, height);
	}

	public int getPX() {
		return posX;
	}

	public int getPY() {
		return posY;
	}

	public void Left() {
		if ((posX - speed) > -500) {
			posX -= speed;
		} else {
			visible = false;
		}
	}

	public void Right() {
		if ((posX - speed) < 2000) {
			posX += speed;
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
