/*
Name: Dorian Chen, Jerry Wu
Assignment #: ISU
Date: January 26, 2021
Description: Welcome to Jumpcraft, a Minecraft-inspired platform game! This program creates a window allowing the user to play Jumpcraft, which features a main menu,
skin selection, three levels of progressively increasing difficulties, and a win screen, all with Minecraft-themed graphics. Will you be able to conquer the
Overworld, Nether, and End and become the Jumpcraft king B')
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class slimeDude extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	//defining all required global variables
	Thread thread;
	int FPS = 60;
	int screenWidth = 700;
	int screenHeight = 900;
	Rectangle player = new Rectangle(0, 870, 30, 30);
	Rectangle[] walls = new Rectangle[21];
	Polygon[] rightramps = new Polygon[4];
	Polygon[] leftramps = new Polygon[5];
	Image[] backgrounds = new Image[6];
	Image[] buttons = new Image[5];
	Image[] skins = new Image[8];
	Image cover;
	Image playerIcon = Toolkit.getDefaultToolkit().getImage("playerIcon0.gif");
	Image netherPortal, oakPlatform, oakFence, netherBrick, netherBrickSlab, netherBrickTall, netherBrickShort, netherBrickLong, netherBrickFence,
	rampLeft, rampLeftSmall, rampRightSmall, endPortal, enderman, endermanSide, endermanLong, endermanSideLong, endermanRamp, crown;
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
	boolean flag=true;
	boolean airborne = true;

	//defining and starting the thread
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
	public void mouseDragged(MouseEvent e) {}

	@Override
	//retrieves current coordinates of cursor when mouse is moved
	public void mouseMoved(MouseEvent e) {
		mousehoverx = e.getX();
		mousehovery = e.getY();
		//System.out.println(mousehoverx+" "+mousehovery);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	//retrieves current coordinates of cursor when mouse is pressed
	public void mousePressed(MouseEvent e) {
		mouseclickx = e.getX();
		mouseclicky = e.getY();
		//System.out.println("press" + mouseclickx + " " + mouseclicky);
	}

	@Override
	//retrieves current coordinates of cursor when mouse is released
	public void mouseReleased(MouseEvent e) {
		mouseclickreleasex = e.getX();
		mouseclickreleasey = e.getY();
		//System.out.println("release" + mouseclickreleasex + " " + mouseclickreleasey);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	/**
	 * Description: Main thread method that advances to levels from title screen, runs the game while within levels by calling other methods.
	 * Parameters: None.
	 */
	public void run() {
		initialize();
		while (level < 6) {
			//check if player pressed/released mouse in areas covered by the start/skins buttons, change level variable accordingly
			//int level 0 is main menu, level 1 is skin selection, 2 is in-game Level 1, etc.
			if (level == 0) {
				try {
					Thread.sleep(1000/FPS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.repaint();
				if (mouseclickx > 30 && mouseclickx < 330 && mouseclicky > 530 && mouseclicky < 630
						&& mouseclickreleasex > 30 && mouseclickreleasex < 330 && mouseclickreleasey > 530 && mouseclickreleasey < 630) {
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					level += 2; 
				}

				else if (mouseclickx > 370 && mouseclickx < 670 && mouseclicky > 530 && mouseclicky < 630
						&& mouseclickreleasex > 370 && mouseclickreleasex < 670 && mouseclickreleasey > 530 && mouseclickreleasey < 630) {
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					level++;
				}
			}
			//check for player mouse pressed/released input in skin selection screen, change player icon accordingly and return to main menu
			else if (level == 1) {
				try {
					Thread.sleep(1000/FPS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.repaint();
				if (mouseclickx > 100 && mouseclickx < 200 && mouseclicky > 500 && mouseclicky < 600
						&& mouseclickreleasex > 100 && mouseclickreleasex < 200 && mouseclickreleasey > 500 && mouseclickreleasey < 600) {
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					level--;
				}
				else if (mouseclickx > 300 && mouseclickx < 400 && mouseclicky > 500 && mouseclicky < 600
						&& mouseclickreleasex > 300 && mouseclickreleasex < 400 && mouseclickreleasey > 500 && mouseclickreleasey < 600) {
					playerIcon = skins[1];
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					level--;
				}
				else if (mouseclickx > 500 && mouseclickx < 600 && mouseclicky > 500 && mouseclicky < 600
						&& mouseclickreleasex > 500 && mouseclickreleasex < 600 && mouseclickreleasey > 500 && mouseclickreleasey < 600) {
					playerIcon = skins[2];
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					level--;
				}
				else if (mouseclickx > 100 && mouseclickx < 200 && mouseclicky > 700 && mouseclicky < 800
						&& mouseclickreleasex > 100 && mouseclickreleasex < 200 && mouseclickreleasey > 700 && mouseclickreleasey < 800) {
					playerIcon = skins[3];
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					level--;
				}
				else if (mouseclickx > 300 && mouseclickx < 400 && mouseclicky > 700 && mouseclicky < 800
						&& mouseclickreleasex > 300 && mouseclickreleasex < 400 && mouseclickreleasey > 700 && mouseclickreleasey < 800) {
					playerIcon = skins[4];
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					level--;
				}
				else if (mouseclickx > 500 && mouseclickx < 600 && mouseclicky > 700 && mouseclicky < 800
						&& mouseclickreleasex > 500 && mouseclickreleasex < 600 && mouseclickreleasey > 700 && mouseclickreleasey < 800) {
					playerIcon = skins[5];
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					level--;
				}
				else if (mouseclickx > 180 && mouseclickx < 280 && mouseclicky > 250 && mouseclicky < 350
						&& mouseclickreleasex > 180 && mouseclickreleasex < 280 && mouseclickreleasey > 250 && mouseclickreleasey < 350) {
					playerIcon = skins[6];
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					level--;
				}
				else if (mouseclickx > 420 && mouseclickx < 520 && mouseclicky > 250 && mouseclicky < 350
						&& mouseclickreleasex > 420 && mouseclickreleasex < 520 && mouseclickreleasey > 250 && mouseclickreleasey < 350) {
					playerIcon = skins[7];
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					level--;
				}
			}
			//run Level 1 by calling other methods and repainting
			else if (level==2){
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
				//cheat if mouse clicked on Nether portal :)
				if (flag && mouseclickx > 595 && mouseclickx < 655 && mouseclicky > 0 && mouseclicky < 70
						&& mouseclickreleasex > 595 && mouseclickreleasex < 655 && mouseclickreleasey > 0 && mouseclickreleasey < 70) {
					mouseclickx=0; mouseclicky=0; mouseclickreleasex=0; mouseclickreleasey=0;
					System.out.println("Hi, Ms. Wong :)\n\nWe just wanted to thank you for teaching us computer science this\nsemester, and always putting up with "
							+ "our 12 am emails.\nWe really really appreciate all that you have done for us throughout the entire year:\nrunning the competitions, "
							+ "extra help sessions, clubs, and more!!\nAs a thank you, we have also activated a cheat that makes the game a little easier.\nGood Luck!");
					gravity = 0.2;
				}
			}
			//run Level 2 by calling other methods and repainting
			else if (level==3) {
				initialize();
				move();
				for (int i = 0; i < walls.length; i++)
					checkCollision(walls[i]);
				for (int i = 0; i < leftramps.length; i++)
					checkSliderLeft(leftramps[i]);
				for (int i = 0; i < rightramps.length; i++)
					checkSliderRight(rightramps[i]);
				keepInBound();
				this.repaint();
				try {
					Thread.sleep(1000/FPS);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			//run Level 3 by calling other methods and repainting
			else if (level==4) {
				initialize();
				move();
				for (int i = 0; i < walls.length; i++)
					checkCollision(walls[i]);
				for (int i = 0; i < rightramps.length; i++)
					checkSliderRight(rightramps[i]);
				keepInBound();
				this.repaint();
				try {
					Thread.sleep(1000/FPS);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			//run win screen
			else if (level==5){
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
	
	/**
	 * Description: Set up levels by defining array and graphics variables and building Rectangles and Polygons.
	 * Parameters: N/A
	 * @return N/A
	 */
	public void initialize() {
		//retrieve all backgrounds, buttons, and skins images abd store in arrays
		for (int i = 0; i < backgrounds.length; i++)
			backgrounds[i] = Toolkit.getDefaultToolkit().getImage("background" + i + ".gif");
		for (int i = 0; i < buttons.length; i++)
			buttons[i] = Toolkit.getDefaultToolkit().getImage("but" + i + ".gif");
		for (int i = 0; i < skins.length; i++)
			skins[i] = Toolkit.getDefaultToolkit().getImage("playerIcon" + i + ".gif");
		
		
		//retrieve and define all in-game graphics
		cover = Toolkit.getDefaultToolkit().getImage("covering.gif");
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
		crown = Toolkit.getDefaultToolkit().getImage("crown.gif");
		
		//build Level 1 by defining elements of walls array with Rectangles
		if (level == 2) {
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
		//build Level 2
		else if (level == 3) {
			//store Polygon vertex coordinates in arrays for easier writing
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

			//define elements of ramp arrays with Polygons
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
		//build Level 3
		else if (level == 4) {
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
			for (int e =0; e<rightramps.length;e++)
				rightramps[e]=new Polygon(x1,y1,0);
			for (int e =0; e<leftramps.length;e++)
				leftramps[e]=new Polygon(x1,y1,0);
		}
	}

	/**
	 * Description: Draws the window and updates it.
	 * @param g
	 * @return N/A
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 0));
		g2.drawImage(backgrounds[level], 0, 0, screenWidth, screenHeight, this);

		//draw button images on main menu, change if mouse hovers over them
		if (level == 0) {
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
		//draw various skin options on skins selection screen
		else if (level == 1) {
			g2.drawImage(skins[0], 100, 500, 100, 100, this);
			g2.drawImage(skins[1], 300, 500, 100, 100, this);
			g2.drawImage(skins[2], 500, 500, 100, 100, this);
			g2.drawImage(skins[3], 100, 700, 100, 100, this);
			g2.drawImage(skins[4], 300, 700, 100, 100, this);
			g2.drawImage(skins[5], 500, 700, 100, 100, this);
			g2.drawImage(skins[6], 180, 250, 100, 100, this);
			g2.drawImage(skins[7], 420, 250, 100, 100, this);
			
			//draw yellow outline over skins if mouse hovers over them
			if (mousehoverx >= 100 && mousehoverx <= 200 && mousehovery >= 500 && mousehovery <= 600)
				g.drawImage(cover,100,500,100,100,this);
			else if (mousehoverx >= 300 && mousehoverx <= 400 && mousehovery >= 500 && mousehovery <= 600)
				g.drawImage(cover,300,500,100,100,this);
			else if (mousehoverx >= 500 && mousehoverx <= 600 && mousehovery >= 500 && mousehovery <= 600)
				g.drawImage(cover,500,500,100,100,this);
			else if (mousehoverx >= 100 && mousehoverx <= 200 && mousehovery >= 700 && mousehovery <= 800)
				g.drawImage(cover,100,700,100,100,this);
			else if (mousehoverx >= 300 && mousehoverx <= 400 && mousehovery >= 700 && mousehovery <= 800)
				g.drawImage(cover,300,700,100,100,this);
			else if (mousehoverx >= 500 && mousehoverx <= 600 && mousehovery >= 700 && mousehovery <= 800)
				g.drawImage(cover,500,700,100,100,this);
			else if (mousehoverx >= 180 && mousehoverx <= 280 && mousehovery >= 250 && mousehovery <= 350)
				g.drawImage(cover,180,250,100,100,this);
			else if (mousehoverx >= 420 && mousehoverx <= 520 && mousehovery >= 250 && mousehovery <= 350)
				g.drawImage(cover,420,250,100,100,this);
		}
		//draw Level 1 walls + graphics overlayed on the walls
		else if (level == 2) {
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

			//draw player Rectangle and player icon overlayed on player
			g2.draw(player);
			g2.drawImage(playerIcon, player.x, player.y, 30, 30, this);
		}
		//draw Level 2 walls and ramps and graphics
		else if (level == 3) {
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
		//draw Level 3 walls and ramps and graphics
		else if (level == 4) {
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
		//draw player wearing crown image
		else if (level == 5) {
			g2.setColor(Color.GRAY);
			for (int i = 0; i < walls.length; i++)
				g2.fill(walls[i]);
			for (int i = 0; i < rightramps.length; i++)
				g2.fill(rightramps[i]);
			g2.fill(player);
			g2.drawImage(playerIcon, player.x, player.y, 30, 30, this);
			g2.drawImage(crown, player.x-5, player.y-24, 40, 28, this);
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

	/**
	 * Description: Moves the player using keyPressed and keyReleased info.
	 * Parameters: N/A
	 * @return N/A
	 */
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

	/**
	 * Description: Checks player position and keeps it in the window.
	 * Parameters: N/A
	 * @return N/A
	 */
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

	/**
	 * Description: Checks player and rectangle wall position for intersection and prevents player from moving further into walls.
	 * @param
	 * @return N/A
	 */
	void checkCollision(Rectangle wall) {
		if (player.intersects(wall)) {
			//define double variables with player and wall vertex coordinates
			double left1 = player.getX();
			double right1 = player.getX() + player.getWidth();
			double top1 = player.getY();
			double bottom1 = player.getY() + player.getHeight();
			double left2 = wall.getX();
			double right2 = wall.getX() + wall.getWidth();
			double top2 = wall.getY();
			double bottom2 = wall.getY() + wall.getHeight();

			//check for and correct player collision with wall from left side
			if (right1 > left2 && left1 < left2 && right1 - left2 <= 5) {
				player.x = wall.x - player.width;
				airborne = true;
			}
			//check for and correct player collision with wall from right side
			else if (left1 < right2 && right1 > right2 && right2 - left1 <= 5) {
				player.x = wall.x + wall.width;
				airborne = true;
			}
			//check for and correct player collision with wall from top side
			else if (bottom1 > top2 && top1 < top2) {
				player.y = wall.y - player.height;
				airborne = false;
			}
			//check for and correct player collision with wall from bottom side
			else if (top1 < bottom2 && bottom1 > bottom2) {
				player.y = wall.y + wall.height;
				airborne = true;
				yVel/=3;
			}
		}
	}

	/**
	 * Description: Checks player and Polygon ramp position for intersection, prevents player from moving further into walls and makes player slide left down the ramp.
	 * @param
	 * @return N/A
	 */
	void checkSliderLeft (Polygon ramps) {
		Rectangle hitbox = ramps.getBoundingBox();
		//define int and double variables with player and ramp vertex coordinates
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

		//check for and correct player collision with ramp from right side
		if (player.intersects(hitbox) && left1 < right2 && right1 > right2 && right2 - left1 <= 5) {
			player.x = hitbox.x + hitbox.width;
			airborne = true;
		}
		//check for and correct player collision with ramp from bottom side
		else if (player.intersects(hitbox) && top1 < bottom2 && bottom1 > bottom2) {
			player.y = hitbox.y + hitbox.height;
			airborne = true;
			yVel/=3;
		}
		//check for player collision with ramp from sliding side and force player to slide down ramp
		else if (ramps.intersects(player.x, player.y, player.width, player.height)) {
			yVel=-3;
			player.x = (botleftcorny - player.y) + (botleftcornx - player.width - 31);
		}
	}
	
	/**
	 * Description: Checks player and Polygon ramp position for intersection, prevents player from moving further into walls and makes player slide right down the ramp.
	 * @param
	 * @return N/A
	 */
	void checkSliderRight (Polygon ramps) {
		Rectangle hitbox=ramps.getBoundingBox();
		//define int and double variables with player and ramp vertex coordinates
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

		//check for and correct player collision with ramp from left side
		if (player.intersects(hitbox) && right1 > left2 && left1 < left2 && right1 - left2 <= 5) {
			player.x = hitbox.x + hitbox.width;
			airborne = true;
		}
		//check for and correct player collision with ramp from bottom side
		else if (player.intersects(hitbox) && top1 < bottom2 && bottom1 > bottom2) {
			player.y = hitbox.y + hitbox.height;
			airborne = true;
			yVel/=3;
		}
		//check for player collision with ramp from sliding side and force player to slide down ramp
		else if (ramps.intersects(player.x, player.y, player.width, player.height)) {
			yVel=-3;
			player.x = (hitbox.width-(botleftcorny - player.y))+(botleftcornx+31);
		}
	}

	/**
	 * Description: Checks if player is in winning conditions using current player coordinates and advances levels if so.
	 * Parameters: N/A
	 * @return N/A
	 */
	public void winner () {
		//advance player from Level 1 to Level 2 if player is in front of portal's coordinates, reset player position to bottom left corner
		if (level == 2) {
			if (player.x >= 594 && player.x <= 626 && player.y >= 0 && player.y <= 45 && win == true) {
				level++;
				player.y = 900 - player.height;
				player.x = 0;
				win = false;
				airborne = true;
			}
		}
		//advance player from Level 2 to Level 3 and Level 3 to win screen if player is in front of portal's coordinates, reset player position to bottom left corner
		else if (level == 3 || level == 4) {
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
