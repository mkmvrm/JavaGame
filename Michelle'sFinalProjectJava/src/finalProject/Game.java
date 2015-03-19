package finalProject;

import java.applet.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import javax.imageio.ImageIO;

public class Game extends Applet implements KeyListener, Runnable {

	private static final long serialVersionUID = 6401150581881825414L;

	// CONSTANTS
	private static final int NUM_OF_ENEMIES = 3;

	// CLASS OBJECTS
	Player player;
	GameWindow gw;

	// IMAGES
	Image[] ihealth = new Image[4];
	Image iplayer; // USED ONLY FOR GETTING BOUNDS OF IMAGE FOR COLLISION
					// DETECTION
	Image ibg1;
	Image ibg2;
	Image ibg3;
	Image ibg3o; // GAME OVER BACKGROUND

	// ARRAYLISTS OF PROJECTILES AND ENEMIES
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	// ENEMY STARTING POSITIONS
	int[] enemyStartingPos = new int[NUM_OF_ENEMIES];

	// MISC
	URL base;
	Timer timer;
	Dimension appletSize; // APPLET SIZE
	Image buffer = null;
	Image fBuf;
	Graphics bBuf;
	MediaTracker mt;

	Random rand = new Random();

	boolean standingl = true; // DEFAULT TRUE
	boolean zombles = false;
	boolean leftKey = false;
	boolean rightKey = false;
	boolean upKey = false;
	boolean spaceKey = false;
	boolean standingr = false;
	boolean runLeft = false;
	boolean runRight = false;
	boolean inGame;

	int ndx = 0; // INDEX FOR PLAYER RUNNING ANIMATION
	int startingPosition = -400; // FIRST ENEMY STARTING POSITION
	long timingProjectiles = 0;

	public void init() {
		setSize(1900, 990);
		appletSize = getSize();
		fBuf = createImage(appletSize.width, appletSize.height);
		bBuf = fBuf.getGraphics();
		setBackground(Color.black);
		base = getDocumentBase();

		// INITIALIZE CLASS OBJECTS
		player = new Player(1500, 575, 10, 3, base);
		gw = new GameWindow("MUSIC", base);
		
		gw.sound = getAudioClip(base, "music/01.au");

		// LOAD BACKGROUND IMAGE
		try {
			URL url = new URL(getCodeBase(), "img/1.png");
			iplayer = ImageIO.read(url);
		} catch (IOException e) {
		}

		ibg1 = getImage(base, "img/bg1.jpg");
		ibg2 = getImage(base, "img/bg2.jpg");
		ibg3 = getImage(base, "img/bg3.jpg");
		ibg3o = getImage(base, "img/bg3o.png");
		ihealth[0] = getImage(base, "img/h0.png");
		ihealth[1] = getImage(base, "img/h1.png");
		ihealth[2] = getImage(base, "img/h2.png");
		ihealth[3] = getImage(base, "img/h3.png");

		inGame = true;
		zombles = true;

		// SET HEIGHT AND WIDTH FOR COLLISION DETECTION FOR PLAYER
		player.setHeight(iplayer.getHeight(null));
		player.setWidth(iplayer.getWidth(null));

		// INITIALIZE STARTING ENEMY POSITIONS
		for (int i = 0; i < NUM_OF_ENEMIES; i++) {
			enemyStartingPos[i] = startingPosition;
			startingPosition = startingPosition - 500;
		}

		addKeyListener(this);
		Thread t = new Thread(this);
		t.start();
	}

	public void paint(Graphics g) {

		if (inGame) {
			bBuf.drawImage(ibg3, 0, 0, this);

			// DRAW HEALTH
			if (player.getHealth() == 3)
				bBuf.drawImage(ihealth[3], 1400, -130, this);
			if (player.getHealth() == 2)
				bBuf.drawImage(ihealth[2], 1400, -130, this);
			if (player.getHealth() == 1)
				bBuf.drawImage(ihealth[1], 1400, -130, this);
			if (player.getHealth() == 0)
				bBuf.drawImage(ihealth[0], 1400, -130, this);

			// DRAW PROJECTILES
			for (int i = 0; i < projectiles.size(); i++) {
				if (projectiles.get(i).isVisible()) {
					bBuf.drawImage(projectiles.get(i).getImage(), (projectiles
							.get(i).getPX()-120),
							(projectiles.get(i).getPY() + 150), this);
				}
			}
			// DRAW ENEMIES
			for (int i = 0; i < enemies.size(); i++) {
				if (enemies.get(i).isVisible()) {
					bBuf.drawImage(enemies.get(i).getImage(), enemies.get(i)
							.getPX(), (enemies.get(i).getPY() + 100), this);
				} else
					enemies.remove(i);
			}

			if (standingl) {
				bBuf.drawImage(player.getMoveLeftImgs()[2], player.getPX(),
						(player.getPY()+100), this);
			}
			if (standingr) {
				bBuf.drawImage(player.getMoveRightImgs()[2], player.getPX(),
						(player.getPY()+100), this);
			}
			if (runLeft) {
				bBuf.drawImage(player.getMoveLeftImgs()[ndx], player.getPX(),
						(player.getPY()+100), this);
				if (ndx < 2) {
					try {
						Thread.sleep(75);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					++ndx;
				}
				if (ndx >= 2)
					ndx = 0;
			}
			if (runRight) {
				bBuf.drawImage(player.getMoveRightImgs()[ndx], player.getPX(),
						(player.getPY()+100), this);
				if (ndx < 2) {
					try {
						Thread.sleep(75);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					++ndx;

				}
				if (ndx >= 2)
					ndx = 0;
			}

		} else {
			String msg = "Game Over";
			Font small = new Font("Helvetica", Font.BOLD, 100);
			FontMetrics metr = this.getFontMetrics(small);

			bBuf.drawImage(ibg3o, 0, 0, this);
			bBuf.setColor(Color.white);
			bBuf.setFont(small);
			bBuf.drawString(msg,
					(appletSize.width - metr.stringWidth(msg)) / 2,
					appletSize.height / 2);
		}

		g.drawImage(fBuf, 0, 0, this); // DRAWS EVERYTHING

	}

	public void update(Graphics g) {

		paint(g);
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			leftKey = true;
			runLeft = true;
			standingl = false;
			standingr = false;
			break;
		case KeyEvent.VK_RIGHT:
			rightKey = true;
			runRight = true;
			standingr = false;
			standingl = false;
			break;
		case KeyEvent.VK_UP:
			upKey = true;
			break;
		case KeyEvent.VK_SPACE:
			spaceKey = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			leftKey = false;
			runLeft = false;
			standingl = true;
			standingr = false;
			ndx = 0;
			break;
		case KeyEvent.VK_RIGHT:
			rightKey = false;
			runRight = false;
			standingr = true;
			standingl = false;
			ndx = 0;
			break;
		case KeyEvent.VK_UP:
			upKey = false;
			break;
		case KeyEvent.VK_SPACE:
			spaceKey = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void run() {

		int c1 = 0;
		int c2 = -1;
		int pos = 0;

		int ILEIKPIE = 0;

		while (true) {

			if (upKey) {

				if (player.getToggleJump())
					player.JumpUp();
				else
					player.JumpDown();
			} else {
				player.JumpDown();
			}
			if (leftKey) {
				player.Left();
			}
			if (rightKey) {
				player.Right();
			}
			if (zombles) {
				if (enemies.size() < NUM_OF_ENEMIES) {
					Enemy enemy = new Enemy(enemyStartingPos[pos], 500, 3, 1,
							getDocumentBase());
					enemies.add(enemy);
					ILEIKPIE++;

					pos++;
					pos %= 3;

					if (ILEIKPIE == 2) {
						enemies.remove(0);
					}

				}

				for (int i = 0; i < enemies.size(); i++) {
					if (enemies.get(i).isVisible()) {

						// ENEMIES FLOAT UP AND DOWN
						if (c1 < 120) {
							c1++;
							c2 = c1;
							enemies.get(i).RightUp();
						} else {
							if (c2 > 0) {
								c2--;
								enemies.get(i).RightDown();
							} else {
								c1 = 0;
								c2 = -1;
							}
						}
					}
				}
			}

			if (spaceKey && (System.currentTimeMillis() > timingProjectiles+500)) {
				timingProjectiles = System.currentTimeMillis();
				Projectile pro = new Projectile(player.getPX(), player.getPY(),
						15, getDocumentBase());
				projectiles.add(pro);
			}
			// END GAME IF ALL ENEMIES DESTROYED
			if (enemies.size() == 0) {
				inGame = false;
			}
			for (int i = 0; i < projectiles.size(); i++) {
				if (projectiles.get(i).isVisible()) {
					projectiles.get(i).Left();
				} else
					projectiles.remove(projectiles.get(i));
			}
			checkCollisions();
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
		}
	}

	public void checkCollisions() {

		Rectangle r3 = player.getBounds();

		for (int j = 0; j < enemies.size(); j++) {
			Rectangle r2 = enemies.get(j).getBounds();

			if (r3.intersects(r2)) {
				player.setHealth(player.getHealth() - 1);
				enemies.get(j).setVisible(false);
				if (player.getHealth() == 0)
					inGame = false;
			}
		}

		for (int j = 0; j < enemies.size(); j++) {
			Rectangle r2 = enemies.get(j).getBounds();
			for (int i = 0; i < projectiles.size(); i++) {
				Rectangle r1 = projectiles.get(i).getBounds();

				if (r1.intersects(r2)) {
					projectiles.clear();
					enemies.get(j).setVisible(false);
				}
			}
		}
	}
}
