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
	Rectangle[] walls = new Rectangle[18];
	//Polygon[]ramps = new Polygon[1];
	Image[] backgrounds = new Image[5];
	Image[] buttons = new Image[5];
	Image playerIcon, door;
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
	int mousehoverx=0;
	int mousehovery=0;
	int level = 0;
	boolean airborne = true;

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
		//System.out.println("press" + mouseclickx + " " + mouseclicky);
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
						&& mouseclickreleasex > 30 && mouseclickreleasex < 330 && mouseclickreleasey > 530 && mouseclickreleasey < 630) {
					level++; 
				}
			}
			else {
				initialize();
				move();
				for (int i = 0; i < walls.length; i++)
					checkCollision(walls[i]);
				//				for (int i = 0; i < ramps.length; i++)
				//					checkSlide(ramps[i]);
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
		for (int i = 0; i < backgrounds.length; i++)//gets all the backgrounds
			backgrounds[i] = Toolkit.getDefaultToolkit().getImage("background" + i + ".gif");
		for (int i = 0; i < buttons.length; i++)//gets all the backgrounds
			buttons[i] = Toolkit.getDefaultToolkit().getImage("but" + i + ".gif");
		playerIcon = Toolkit.getDefaultToolkit().getImage("playerIcon.gif");
		door = Toolkit.getDefaultToolkit().getImage("door.gif");

		int []x= {330,630,630};
		int []y= {900,900,600};
		if (level == 1) {
			//ramps[0] = new Polygon(x,y,3 );
			walls[0] = new Rectangle(70, 780, 100, 15);
			walls[1] = new Rectangle(180, 650, 100, 15);
			walls[2] = new Rectangle(350, 550, 15, 400);
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
		}
		else if (level == 2)
			walls[0] = new Rectangle(200, 200, 100, 100);
		else if (level == 3)
			walls[0] = new Rectangle(200, 200, 200, 200);
		else
			for (int e = 0; e < walls.length; e++)
				walls[e]= new Rectangle(0, 0, 0, 0);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(backgrounds[level], 0, 0, screenWidth, screenHeight, this);
		if (level==0) {
			g.drawImage(buttons[0], 30, 530, 300, 100, this);
			g.drawImage(buttons[2], 370, 530, 300, 100, this);
			if (mousehoverx>=30 && mousehoverx<=330 && mousehovery>=530 && mousehovery<=630) {
				g.drawImage(buttons[2], 370, 530, 300, 100, this);
				g.drawImage(buttons[1], 30, 530, 300, 100, this);
			}
			else if (mousehoverx>=370 && mousehoverx<=670 && mousehovery>=530 && mousehovery<=630) {
				g.drawImage(buttons[0], 30, 530, 300, 100, this);
				g.drawImage(buttons[3], 370, 530, 300, 100, this);
			}
			else {
				g.drawImage(buttons[0], 30, 530, 300, 100, this);
				g.drawImage(buttons[2], 370, 530, 300, 100, this);
			}
		}
		g2.setColor(Color.GRAY);
		if (level >= 1) {
			for (int i = 0; i < walls.length; i++)
				g2.fill(walls[i]);
			//			for (int i = 0; i < ramps.length; i++)
			//				g2.fill(ramps[i]);
			g2.drawImage(door, 590, 6, 70, 70, this);
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
		else if (key == KeyEvent.VK_E)
			win = true;
		winner();
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
				//System.out.println("left");
			}
			else if (left1 < right2 && right1 > right2 && right2 - left1 <= 5) {
				player.x = wall.x + wall.width;
				airborne = true;
				//System.out.println("right");
			}
			else if (bottom1 > top2 && top1 < top2) {
				player.y = wall.y - player.height;
				airborne = false;
				//System.out.println("top");
			}
			else if (top1 < bottom2 && bottom1 > bottom2) {
				player.y = wall.y + wall.height;
				airborne = true;
				yVel/=3;
				//System.out.println("bottom");
			}
		}
	}
	void checkSlide(Polygon ramps) {
		double right1 = player.getX() + player.getWidth();
		if (ramps.intersects(player.x, player.y, player.width, player.height)) {
			player.x = (900 - player.y) + (330 - player.width - 30);
		}
	}


	public void winner () {
		if (player.x >= 590 && player.x <= 630 && player.y >= 6 && player.y <= 36 && win == true) {
			level++;
			player.y = 900 - player.height;
			player.x = 0;
			win = false;
			airborne = true;
		}
		else
			win = false;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame ("Jump Dude");
		slimeDude myPanel = new slimeDude ();
		frame.add(myPanel);
		frame.addKeyListener(myPanel);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
