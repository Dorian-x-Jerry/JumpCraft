import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
public class slimeDude extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	Thread thread;
	int FPS = 60;
	int screenWidth = 700;
	int screenHeight = 900;
	Rectangle player = new Rectangle(0, 870, 30, 30);
	Rectangle[] walls = new Rectangle[21];
	Polygon[] rightramps = new Polygon[4];
	Polygon[] leftramps = new Polygon[5];
	Image[] backgrounds = new Image[5];
	Image[] buttons = new Image[5];
	Image playerIcon, netherPortal, oakPlatform, oakFence, netherBrick, netherBrickSlab, netherBrickTall, netherBrickShort, netherBrickLong, netherBrickFence,
	rampLeft, rampLeftSmall, rampRightSmall, endPortal, enderman, endermanSide, endermanLong, endermanSideLong, endermanRamp;
	boolean jump, left, right, win;
	double speed = 5;
	double jumpSpeed = 15;
	double xVel = 0;
	double yVel = 0;
	double gravity = 0.8;
	int mouseclickx = 0;
	int mouseclicky = 0;
	int mouseclickreleasex = 0;
	int mouseclickreleasey = 0;
	int mousehoverx = 0;
	int mousehovery = 0;
	int level = 0;
	boolean airborne = true;

	//ramps


	public slimeDude() {
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);

		jump = false;
		left = false;
		right = false;
		win = false;
		addMouseListener(this);
		addMouseMotionListener(this);
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mousehoverx=e.getX();
		mousehovery=e.getY();
		//System.out.println(mousehoverx+" "+mousehovery);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseclickx = e.getX();
		mouseclicky = e.getY();
		System.out.println("press" + mouseclickx + " " + mouseclicky);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseclickreleasex = e.getX();
		mouseclickreleasey = e.getY();
		//System.out.println("release" + mouseclickreleasex + " " + mouseclickreleasey);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {}


	@Override
	public void run() {
		initialize();
		while (level < 5) {
			if (level == 0) {
				try {
					Thread.sleep(1000/FPS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.repaint();
				if (mouseclickx > 30 && mouseclickx < 330 && mouseclicky > 530 && mouseclicky < 630
						&& mouseclickreleasex > 30 && mouseclickreleasex < 330 && mouseclickreleasey > 530 && mouseclickreleasey < 630)
					level++; 
			}
			else if (level==1){
				initialize();
				move();
				for (int i = 0; i < walls.length; i++)
					checkCollision(walls[i]);
				keepInBound();
				this.repaint();
				try {
					Thread.sleep(1000/FPS);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			else if (level==2) {
				initialize();
				move();
				for (int i = 0; i < walls.length; i++)
					checkCollision(walls[i]);
				for (int i = 0; i < leftramps.length; i++)
					checkSliderleft(leftramps[i]);
				for (int i = 0; i < rightramps.length; i++)
					checkSliderright(rightramps[i]);
				keepInBound();
				this.repaint();
				try {
					Thread.sleep(1000/FPS);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			else if (level==3) {
				initialize();
				move();
				for (int i = 0; i < walls.length; i++)
					checkCollision(walls[i]);
				for (int i = 0; i < rightramps.length; i++)
					checkSliderright(rightramps[i]);
				keepInBound();
				this.repaint();
				try {
					Thread.sleep(1000/FPS);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			else if (level==4){
				initialize();
				move();
				for (int i = 0; i < walls.length; i++)
					checkCollision(walls[i]);
				keepInBound();
				this.repaint();
				try {
					Thread.sleep(1000/FPS);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void initialize() {
		for (int i = 0; i < backgrounds.length; i++)
			backgrounds[i] = Toolkit.getDefaultToolkit().getImage("background" + i + ".gif");
		for (int i = 0; i < buttons.length; i++)
			buttons[i] = Toolkit.getDefaultToolkit().getImage("but" + i + ".gif");
		playerIcon = Toolkit.getDefaultToolkit().getImage("playerIcon.gif");
		oakPlatform = Toolkit.getDefaultToolkit().getImage("oak platform.gif");
		oakFence = Toolkit.getDefaultToolkit().getImage("oak fence.gif");
		netherPortal = Toolkit.getDefaultToolkit().getImage("nether portal.gif");
		netherBrick = Toolkit.getDefaultToolkit().getImage("nether brick.gif");
		netherBrickSlab = Toolkit.getDefaultToolkit().getImage("nether brick slab.gif");
		netherBrickTall = Toolkit.getDefaultToolkit().getImage("nether brick platform tall.gif");
		netherBrickShort = Toolkit.getDefaultToolkit().getImage("nether brick platform short.gif");
		netherBrickLong = Toolkit.getDefaultToolkit().getImage("nether brick platform long.gif");
		netherBrickFence = Toolkit.getDefaultToolkit().getImage("nether brick fence.gif");
		rampLeft = Toolkit.getDefaultToolkit().getImage("ramp left.gif");
		rampLeftSmall = Toolkit.getDefaultToolkit().getImage("ramp left small.gif");
		rampRightSmall = Toolkit.getDefaultToolkit().getImage("ramp right small.gif");
		endPortal = Toolkit.getDefaultToolkit().getImage("end portal.gif");
		enderman = Toolkit.getDefaultToolkit().getImage("enderman.gif");
		endermanSide = Toolkit.getDefaultToolkit().getImage("enderman side.gif");
		endermanLong = Toolkit.getDefaultToolkit().getImage("enderman long.gif");
		endermanSideLong = Toolkit.getDefaultToolkit().getImage("enderman side long.gif");
		endermanRamp = Toolkit.getDefaultToolkit().getImage("enderman ramp.gif");
		
		if (level == 1) {
			walls[0] = new Rectangle(70, 780, 100, 15);
			walls[1] = new Rectangle(180, 650, 100, 15);
			walls[2] = new Rectangle(350, 550, 15, 350);
			walls[3] = new Rectangle(0, 470, 600, 15);
			walls[4] = new Rectangle(407, 850, 42, 50);
			walls[5] = new Rectangle(491, 750, 42, 1000);
			walls[6] = new Rectangle(575, 650, 42, 10000);
			walls[7] = new Rectangle(659, 550, 42, 10000);
			walls[8] = new Rectangle(600, 370, 100, 15);
			walls[9] = new Rectangle(360, 390, 100, 15);
			walls[10] = new Rectangle(80, 380, 100, 15);
			walls[11] = new Rectangle(200, 270, 100, 15);
			walls[12] = new Rectangle(460, 280, 100, 15);
			walls[13] = new Rectangle(0, 210, 100, 15);
			walls[14] = new Rectangle(130, 120, 100, 15);
			walls[15] = new Rectangle(350, 170, 100, 15);
			walls[16] = new Rectangle(575, 160, 100, 15);
			walls[17] = new Rectangle(550, 75, 150, 15);
			walls[18] = new Rectangle(617, 775, 42, 125);
			walls[19] = new Rectangle(533, 825, 42, 75);
			walls[20] = new Rectangle(449, 875, 42, 25);
		}
		else if (level == 2) {
			int [] x1= {250, 250, 55};
			int [] y1= {670, 865, 865};//195

			int [] x2= {320, 320, 240};//80
			int [] y2= {440, 520, 520};

			int [] x3= {340, 340, 260};//80
			int [] y3= {300, 380, 380};

			int [] x4= {550, 550, 470};//80
			int [] y4= {220, 300, 300};

			int [] x5= {150, 150, 70};//80
			int [] y5= {220, 300, 300};

			int [] x6= {380, 380, 460};//80
			int [] y6= {440, 520, 520};

			int [] x7= {360, 360, 440};//80
			int [] y7= {300, 380, 380};

			int [] x8= {570, 570, 650};//80
			int [] y8= {220, 300, 300};

			int [] x9= {170, 170, 250};//80
			int [] y9= {220, 300, 300};

			leftramps[0] = new Polygon(x1,y1,3);
			leftramps[1] = new Polygon(x2,y2,3);
			leftramps[2] = new Polygon(x3,y3,3);
			leftramps[3] = new Polygon(x4,y4,3);
			leftramps[4] = new Polygon(x5,y5,3);

			rightramps[0] = new Polygon(x6,y6,3);
			rightramps[1] = new Polygon(x7,y7,3);
			rightramps[2] = new Polygon(x8,y8,3);
			rightramps[3] = new Polygon(x9,y9,3);

			walls[0] = new Rectangle(320, 750, 60, 150);
			walls[1] = new Rectangle(320, 580, 60, 140);
			walls[2] = new Rectangle(250, 670, 200, 15);
			walls[3] = new Rectangle(600, 750, 60, 120);
			walls[4] = new Rectangle(50, 670, 70, 15);
			walls[5] = new Rectangle(50, 520, 650, 15);
			walls[6] = new Rectangle(320, 440, 60, 80);
			walls[7] = new Rectangle(600, 370, 100, 15);
			walls[8] = new Rectangle(340, 300, 20, 80);
			walls[9] = new Rectangle(260, 200, 180, 15);
			walls[10] = new Rectangle(550, 220, 20, 80);
			walls[11] = new Rectangle(150, 220, 20, 80);
			walls[12] = new Rectangle(150, 75, 550, 15);
			walls[13] = new Rectangle(0, 210, 0, 15);
			walls[14] = new Rectangle(130, 120, 0, 15);
			walls[15] = new Rectangle(350, 170, 0, 15);
			walls[16] = new Rectangle(575, 160, 0, 15);
			walls[17] = new Rectangle(550, 75, 0, 15);
			walls[18] = new Rectangle(550, 75, 0, 15);
			walls[19] = new Rectangle(533, 825, 0, 75);
			walls[20] = new Rectangle(449, 875, 0, 25);
		}
		else if (level == 3) {
			int [] x1= {15, 15, 90};
			int [] y1= {0, 75, 75};//75
			rightramps[0]=new Polygon (x1,y1,3);
			rightramps[1]=new Polygon (x1,y1,0);
			rightramps[2]=new Polygon (x1,y1,0);
			rightramps[3]=new Polygon (x1,y1,0);

			walls[0] = new Rectangle(0, 855, 550, 15);
			walls[1] = new Rectangle(450, 220, 15, 645);
			walls[2] = new Rectangle(450, 725, 120, 15);
			walls[3] = new Rectangle(450, 595, 140, 15);
			walls[4] = new Rectangle(450, 465, 160, 15);
			walls[5] = new Rectangle(450, 335, 180, 15);
			walls[6] = new Rectangle(450, 205, 200, 15);
			walls[7] = new Rectangle(405, 85, 15, 740);
			walls[8] = new Rectangle(300, 700, 20, 100);
			walls[9] = new Rectangle(50, 650, 20, 100);
			walls[10] = new Rectangle(170, 600, 20, 100);
			walls[11] = new Rectangle(320, 520, 20, 100);
			walls[12] = new Rectangle(90, 470, 20, 100);
			walls[13] = new Rectangle(200, 400, 20, 100);
			walls[14] = new Rectangle(260, 270, 20, 100);
			walls[15] = new Rectangle(40, 280, 20, 100);
			walls[16] = new Rectangle(350, 160, 20, 100);
			walls[17] = new Rectangle(150, 180, 20, 100);
			walls[18] = new Rectangle(0, 75, 90, 15);
			walls[19] = new Rectangle(128, 75, 999, 15);
			walls[20] = new Rectangle(0, 0, 15, 75);
		}
		else {
			int [] x1= {0, 0, 75};
			int [] y1= {0, 75, 75};//75

			for (int e = 0; e < walls.length; e++)
				walls[e]= new Rectangle(0, 0, 0, 0);
			for (int e =0; e<rightramps.length;e++) {
				rightramps[e]=new Polygon(x1,y1,0);
			}
			for (int e =0; e<leftramps.length;e++) {
				leftramps[e]=new Polygon(x1,y1,0);
			}
		}
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 0));
		g2.drawImage(backgrounds[level], 0, 0, screenWidth, screenHeight, this);
		if (level==0) {
			g.drawImage(buttons[0], 30, 530, 300, 100, this);
			g.drawImage(buttons[2], 370, 530, 300, 100, this);
			if (mousehoverx >= 30 && mousehoverx <= 330 && mousehovery >= 530 && mousehovery <= 630) {
				g.drawImage(buttons[2], 370, 530, 300, 100, this);
				g.drawImage(buttons[1], 30, 530, 300, 100, this);
			}
			else if (mousehoverx >= 370 && mousehoverx <= 670 && mousehovery >= 530 && mousehovery <= 630) {
				g.drawImage(buttons[0], 30, 530, 300, 100, this);
				g.drawImage(buttons[3], 370, 530, 300, 100, this);
			}
			else {
				g.drawImage(buttons[0], 30, 530, 300, 100, this);
				g.drawImage(buttons[2], 370, 530, 300, 100, this);
			}
		}
		else if (level == 1) {
			for (int i = 0; i < walls.length; i++)
				g2.draw(walls[i]);
			
			g2.drawImage(netherPortal, 594, 0, 62, 75, this);
			g2.drawImage(oakPlatform, 70, 780, 100, 15, this);
			g2.drawImage(oakPlatform, 180, 650, 100, 15, this);
			g2.drawImage(oakFence, 350, 550, 15, 75, this);
			g2.drawImage(oakFence, 350, 625, 15, 75, this);
			g2.drawImage(oakFence, 350, 700, 15, 75, this);
			g2.drawImage(oakFence, 350, 775, 15, 75, this);
			g2.drawImage(oakFence, 350, 850, 15, 75, this);
			g2.drawImage(oakFence, 407, 850, 42, 175, this);
			g2.drawImage(oakFence, 449, 875, 42, 175, this);
			g2.drawImage(oakFence, 491, 750, 42, 175, this);
			g2.drawImage(oakFence, 533, 825, 42, 175, this);
			g2.drawImage(oakFence, 575, 825, 42, 175, this);
			g2.drawImage(oakFence, 575, 650, 42, 175, this);
			g2.drawImage(oakFence, 617, 775, 42, 175, this);
			g2.drawImage(oakFence, 659, 550, 42, 175, this);
			g2.drawImage(oakFence, 659, 725, 42, 175, this);
			g2.drawImage(oakPlatform, 0, 470, 100, 15, this);
			g2.drawImage(oakPlatform, 100, 470, 100, 15, this);
			g2.drawImage(oakPlatform, 200, 470, 100, 15, this);
			g2.drawImage(oakPlatform, 300, 470, 100, 15, this);
			g2.drawImage(oakPlatform, 400, 470, 100, 15, this);
			g2.drawImage(oakPlatform, 500, 470, 100, 15, this);
			g2.drawImage(oakPlatform, 600, 370, 100, 15, this);
			g2.drawImage(oakPlatform, 360, 390, 100, 15, this);
			g2.drawImage(oakPlatform, 80, 380, 100, 15, this);
			g2.drawImage(oakPlatform, 200, 270, 100, 15, this);
			g2.drawImage(oakPlatform, 460, 280, 100, 15, this);
			g2.drawImage(oakPlatform, 0, 210, 100, 15, this);
			g2.drawImage(oakPlatform, 130, 120, 100, 15, this);
			g2.drawImage(oakPlatform, 350, 170, 100, 15, this);
			g2.drawImage(oakPlatform, 575, 160, 100, 15, this);
			g2.drawImage(oakPlatform, 550, 75, 150, 15, this);
		
			g2.draw(player);
			g2.drawImage(playerIcon, player.x, player.y, 30, 30, this);
		}
		else if (level == 2) {
			for (int i = 0; i < walls.length; i++)
				g2.fill(walls[i]);
			for (int i = 0; i < leftramps.length; i++)
				g2.fill(leftramps[i]);
			for (int i = 0; i < rightramps.length; i++)
				g2.fill(rightramps[i]);
			
			g2.drawImage(endPortal, 590, 0, 75, 75, this);
			g2.drawImage(netherBrickTall, 320, 750, 60, 150, this);
			g2.drawImage(netherBrickTall, 320, 580, 60, 140, this);
			g2.drawImage(netherBrickTall, 600, 750, 60, 120, this);
			g2.drawImage(netherBrickShort, 250, 670, 70, 15, this);
			g2.drawImage(netherBrickShort, 380, 670, 70, 15, this);
			g2.drawImage(rampLeft, 55, 670, 195, 195, this);
			g2.drawImage(netherBrickShort, 50, 670, 70, 15, this);
			g2.drawImage(netherBrickLong, 50, 520, 217, 15, this);
			g2.drawImage(netherBrickLong, 267, 520, 217, 15, this);
			g2.drawImage(netherBrickLong, 484, 520, 217, 15, this);
			g2.drawImage(netherBrick, 320, 440, 60, 55, this);
			g2.drawImage(netherBrickSlab, 320, 495, 60, 25, this);
			g2.drawImage(rampLeftSmall, 240, 440, 80, 80, this);
			g2.drawImage(rampRightSmall, 380, 440, 80, 80, this);
			g2.drawImage(netherBrickLong, 600, 370, 100, 15, this);
			g2.drawImage(netherBrickTall, 340, 300, 20, 40, this);
			g2.drawImage(netherBrickTall, 340, 340, 20, 40, this);
			g2.drawImage(rampLeftSmall, 260, 300, 80, 80, this);
			g2.drawImage(rampRightSmall, 360, 300, 80, 80, this);
			g2.drawImage(netherBrickTall, 550, 220, 20, 40, this);
			g2.drawImage(netherBrickTall, 550, 260, 20, 40, this);
			g2.drawImage(rampLeftSmall, 470, 220, 80, 80, this);
			g2.drawImage(rampRightSmall, 570, 220, 80, 80, this);
			g2.drawImage(netherBrickLong, 260, 200, 180, 15, this);
			g2.drawImage(netherBrickTall, 150, 220, 20, 40, this);
			g2.drawImage(netherBrickTall, 150, 260, 20, 40, this);
			g2.drawImage(rampLeftSmall, 70, 220, 80, 80, this);
			g2.drawImage(rampRightSmall, 170, 220, 80, 80, this);
			g2.drawImage(netherBrickLong, 150, 75, 184, 15, this);
			g2.drawImage(netherBrickLong, 334, 75, 184, 15, this);
			g2.drawImage(netherBrickLong, 518, 75, 184, 15, this);
			
			g2.draw(player);
			g2.drawImage(playerIcon, player.x, player.y, 30, 30, this);
		}
		else if (level == 3) {
			for (int i = 0; i < walls.length; i++)
				g2.fill(walls[i]);
			for (int i = 0; i < rightramps.length; i++)
				g2.fill(rightramps[i]);
			
			g2.drawImage(endPortal, 590, 0, 75, 75, this);
			g2.drawImage(endermanSideLong, 0, 855, 550, 15, this);
			g2.drawImage(endermanLong, 450, 220, 15, 645, this);
			g2.drawImage(endermanSide, 450, 725, 120, 15, this);
			g2.drawImage(endermanSide, 450, 595, 140, 15, this);
			g2.drawImage(endermanSide, 450, 465, 160, 15, this);
			g2.drawImage(endermanSide, 450, 335, 180, 15, this);
			g2.drawImage(endermanSide, 450, 205, 200, 15, this);
			g2.drawImage(endermanLong, 405, 85, 15, 740, this);
			g2.drawImage(enderman, 300, 700, 20, 100, this);
			g2.drawImage(enderman, 50, 650, 20, 100, this);
			g2.drawImage(enderman, 170, 600, 20, 100, this);
			g2.drawImage(enderman, 320, 520, 20, 100, this);
			g2.drawImage(enderman, 90, 470, 20, 100, this);
			g2.drawImage(enderman, 200, 400, 20, 100, this);
			g2.drawImage(enderman, 260, 270, 20, 100, this);
			g2.drawImage(enderman, 40, 280, 20, 100, this);
			g2.drawImage(enderman, 350, 160, 20, 100, this);
			g2.drawImage(enderman, 150, 180, 20, 100, this);
			g2.drawImage(endermanSide, 0, 75, 90, 15, this);
			g2.drawImage(endermanSideLong, 128, 75, 572, 15, this);
			g2.drawImage(endermanRamp, 15, 0, 75, 75, this);
				
			g2.fill(player);
			g2.drawImage(playerIcon, player.x, player.y, 30, 30, this);
		}
		else if (level == 4) {
			g2.setColor(Color.GRAY);
			for (int i = 0; i < walls.length; i++)
				g2.fill(walls[i]);
			for (int i = 0; i < rightramps.length; i++)
				g2.fill(rightramps[i]);
//			g2.drawImage(door, 590, 6, 70, 70, this);
			g2.fill(player);
			g2.drawImage(playerIcon, player.x, player.y, 30, 30, this);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			left = true;
			right = false;
		}
		else if (key == KeyEvent.VK_D) {
			right = true;
			left = false;
		}
		else if (key == KeyEvent.VK_SPACE)
			jump = true;
		else if (key == KeyEvent.VK_E) {
			win = true;
			winner();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A)
			left = false;
		else if (key == KeyEvent.VK_D)
			right = false;
		else if (key == KeyEvent.VK_SPACE)
			jump = false;
	}

	void move() {
		if (left)
			xVel = -speed;
		else if (right)
			xVel = speed;
		else
			xVel = 0;

		if (airborne)
			yVel -= gravity;
		else if (jump) {
			airborne = true;
			yVel = jumpSpeed;
		}

		if (player.x < 870)
			airborne = true;

		if (yVel < -29)
			yVel = -29;

		player.x += xVel;
		player.y -= yVel;
	}

	void keepInBound() {
		if (player.x < 0)
			player.x = 0;
		else if (player.x > screenWidth - player.width)
			player.x = screenWidth - player.width;

		if (player.y < 0) {
			player.y = 0;
			yVel = 0;
		}
		else if (player.y > screenHeight - player.height) {
			player.y = screenHeight - player.height;
			airborne = false;
			yVel = 0;
		}
	}

	void checkCollision(Rectangle wall) {
		if (player.intersects(wall)) {
			//System.out.println("collision");
			double left1 = player.getX();
			double right1 = player.getX() + player.getWidth();
			double top1 = player.getY();
			double bottom1 = player.getY() + player.getHeight();
			double left2 = wall.getX();
			double right2 = wall.getX() + wall.getWidth();
			double top2 = wall.getY();
			double bottom2 = wall.getY() + wall.getHeight();


			if (right1 > left2 && left1 < left2 && right1 - left2 <= 5) {
				player.x = wall.x - player.width;
				airborne = true;
				//				System.out.println("left");
			}
			else if (left1 < right2 && right1 > right2 && right2 - left1 <= 5) {
				player.x = wall.x + wall.width;
				airborne = true;
				//				System.out.println("right");
			}
			else if (bottom1 > top2 && top1 < top2) {
				player.y = wall.y - player.height;
				airborne = false;
				//				System.out.println("top");
			}
			else if (top1 < bottom2 && bottom1 > bottom2) {
				player.y = wall.y + wall.height;
				airborne = true;
				yVel/=3;
				//				System.out.println("bottom");
			}
		}
	}

	void checkSliderleft (Polygon ramps) {
		Rectangle hitbox=ramps.getBoundingBox();
		int botleftcorny=(int) (hitbox.getY()+hitbox.height);
		int botleftcornx=(int) hitbox.getX();

		double left1 = player.getX();
		double right1 = player.getX() + player.getWidth();
		double top1 = player.getY();
		double bottom1 = player.getY() + player.getHeight();
		double left2 = hitbox.getX();
		double right2 = hitbox.getX() + hitbox.getWidth();
		double top2 = hitbox.getY();
		double bottom2 = hitbox.getY() + hitbox.getHeight();

		if (player.intersects(hitbox) && left1 < right2 && right1 > right2 && right2 - left1 <= 5) {
			player.x = hitbox.x + hitbox.width;
			airborne = true;
			//				System.out.println("right");
		}
		else if (player.intersects(hitbox) && top1 < bottom2 && bottom1 > bottom2) {
			player.y = hitbox.y + hitbox.height;
			airborne = true;
			yVel/=3;
			//				System.out.println("bottom");
		}
		else if (ramps.intersects(player.x, player.y, player.width, player.height)) {
			yVel=-3;
			player.x = (botleftcorny - player.y) + (botleftcornx - player.width - 31);
		}
	}

	void checkSliderright (Polygon ramps) {
		Rectangle hitbox=ramps.getBoundingBox();
		int botleftcorny=(int) (hitbox.getY()+hitbox.height);
		int botleftcornx=(int) hitbox.getX();

		double left1 = player.getX();
		double right1 = player.getX() + player.getWidth();
		double top1 = player.getY();
		double bottom1 = player.getY() + player.getHeight();
		double left2 = hitbox.getX();
		double right2 = hitbox.getX() + hitbox.getWidth();
		double top2 = hitbox.getY();
		double bottom2 = hitbox.getY() + hitbox.getHeight();

		if (player.intersects(hitbox) && right1 > left2 && left1 < left2 && right1 - left2 <= 5) {
			player.x = hitbox.x + hitbox.width;
			airborne = true;
			//				System.out.println("left");
		}
		else if (player.intersects(hitbox) && top1 < bottom2 && bottom1 > bottom2) {
			player.y = hitbox.y + hitbox.height;
			airborne = true;
			yVel/=3;
			//				System.out.println("bottom");
		}
		else if (ramps.intersects(player.x, player.y, player.width, player.height)) {
			yVel=-3;
			player.x = (hitbox.width-(botleftcorny - player.y))+(botleftcornx+31);
		}
	}


	public void winner () {
		if (level == 1) {
			if (player.x >= 594 && player.x <= 626 && player.y >= 0 && player.y <= 45 && win == true) {
				level++;
				player.y = 900 - player.height;
				player.x = 0;
				win = false;
				airborne = true;
			}
		}
		else if (level == 2 || level == 3) {
			if (player.x >= 590 && player.x <= 635 && player.y >= 0 && player.y <= 45 && win == true) {
				level++;
				player.y = 900 - player.height;
				player.x = 0;
				win = false;
				airborne = true;
			}
		}
		else	
			win = false;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame ("Jumpcraft");
		slimeDude myPanel = new slimeDude ();
		frame.add(myPanel);
		frame.addKeyListener(myPanel);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
