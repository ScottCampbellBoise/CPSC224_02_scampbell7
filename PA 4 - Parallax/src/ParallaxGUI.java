/**
 * @Authors: Scott Campbell & Arron Cushing
 * @Version: 25 March 2019 - Assignment 4
 * @file: ParallaxGUI.java
 */

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ParallaxGUI extends JFrame{
	
	private ParallaxPanel masterPanel;
	private JFrame masterFrame;
	
	private BufferedImage layer1Image;
	private BufferedImage layer2Image;
	private BufferedImage layer3Image;
	private BufferedImage layer4Image;
	private BufferedImage layer5Image;
	
	public static void main(String[] args)
	{
		new ParallaxGUI();
	}
	
	//Prepares the window that will hold the parallax image
	//@pre none
	//@post window for the parallax image is set up and ready for interaction
	//@param none
	public ParallaxGUI()
	{
		//Setting JFrame Values
		setTitle("Parallax Motion Example");
		setSize(600,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creating a variable to hold the JFrame object for use later
		masterFrame = this;
		
		//uploading the scene photos from a remote file
		uploadPhotos();
		
		//create an instance of the Panel where the images are to be stored
		masterPanel = new ParallaxPanel();
				
		add(masterPanel);
		
		setVisible(true);
		validate();
		repaint();
	}
	
	//Uploads photos for use as the various layers in the parallax image
	//@pre images for use are in the appropriate file path
	//@post images are uploaded and ready to be used in the scene
	//@param none
	private void uploadPhotos()
	{
		try{
			layer1Image = ImageIO.read(new File("Images/layer1.png"));
			layer2Image = ImageIO.read(new File("Images/layer2.png"));
			layer3Image = ImageIO.read(new File("Images/layer3.png"));
			layer4Image = ImageIO.read(new File("Images/layer4.png"));
			layer5Image = ImageIO.read(new File("Images/layer5.png"));
			
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null,"Could not locate the necessary Scene Images!");
			System.exit(0);
		}
	}
	
	class ParallaxPanel extends JPanel implements ActionListener
	{
		protected Timer timer;
		private final int delay = 100;
		
		private int mouseX, mouseY;
		
		private int x1, x2, x3, x4, x5;
		private int y1, y2, y3, y4, y5;
		
		private double dx1 = 0.05;
		private double dx2 = 0.08;
		private double dx3 = 0.12;
		private double dx4 = 0.15;
		private double dx5 = 0.25;
		
		private double dy1 = 0.025;
		private double dy2 = 0.05;
		private double dy3 = 0.09;
		private double dy4 = 0.15;
		private double dy5 = 0.20;
		
		//Sets the panel that will hold the parallax image up
		//@pre none
		//@post panel with parallax image is set up and ready for use
		//@param none
		public ParallaxPanel()
		{
		    x1 = x2 = x3 = x4 = x5 = Math.abs(layer1Image.getWidth() - masterFrame.getWidth())/2;
		    y1 = y2 = y3 = y4 = y5 = Math.abs(layer1Image.getHeight() - masterFrame.getHeight())/2;
		    
		    addMouseListener(new MyMouseListener());
		    addMouseMotionListener(new MyMouseMotionListener());
		    
		    timer = new Timer(delay, this);
		    timer.start();
		}
		
		//Refreshes the image after a certain delay
		//@pre none
		//@post after the delay has passed, the panel image is refreshed
		//@param ActionEvent e - event that has been performed
		public void actionPerformed(ActionEvent e)
		{			
			repaint();
		}
		
		//Draws the parallax image onto the panel
		//@pre images have been loaded in
		//@post images are drawn onto the panel
		//@param Graphics g - paint component used for drawing to 
		@Override
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    
		    g.drawImage(layer1Image, -x1, -y1, null);
		    g.drawImage(layer2Image, -x2, -y2, null);
		    g.drawImage(layer3Image, -x3, -y3, null);
		    g.drawImage(layer4Image, -x4, -y4, null);
		    g.drawImage(layer5Image, -x5, -y5, null);
		}
		
		private class MyMouseMotionListener implements MouseMotionListener
		{
			//Handles action of mouse being dragged
			//@pre mouse has been dragged
			//@post image moves as specified
			//@param MouseEvent e - action of mouse being dragged
			public void mouseDragged(MouseEvent e)
		    {
		    	int mouse_dx = e.getX() - mouseX;
		    	int mouse_dy = e.getY() - mouseY;
		    	
		    	if(x5 - mouse_dx*dx5 < 0)
		    	{
		    		mouse_dx = 0;
		    	}
		    	else if (x5 - mouse_dx*dx5 > layer5Image.getWidth() - x5) {
		    		mouse_dx = 0;
		    	}
		    	
		    	if(y5-mouse_dy*dy5 < 0)
		    	{
		    		mouse_dy = 0;
		    	}
		    	else if (layer5Image.getHeight() - y5 + mouse_dy*dy5 < masterFrame.getHeight() - 15) {
		    		mouse_dy = 0;
		    	}
		    	
		    	x1 -= mouse_dx*dx1;
				x2 -= mouse_dx*dx2;
				x3 -= mouse_dx*dx3;
				x4 -= mouse_dx*dx4;
				x5 -= mouse_dx*dx5;
				
				y1 -= mouse_dy*dy1;
				y2 -= mouse_dy*dy2;
				y3 -= mouse_dy*dy3;
				y4 -= mouse_dy*dy4;
				y5 -= mouse_dy*dy5;
				
				repaint();
		    }
		    
		    //Handles action of mouse being moved
		    //@pre mouse has been moved
		    //@post image moves as specified
		    //@param MouseEvent e - action of mouse being moved
		    public void mouseMoved(MouseEvent e)
		    {
		    	/**
		    	int mouse_dx = e.getX() - mouseX;
		    	int mouse_dy = e.getY() - mouseY;
		    	
		    	if(x5 - mouse_dx*dx5 < 0)
		    	{
		    		mouse_dx = 0;
		    	}
		    	else if (x5 - mouse_dx*dx5 > layer5Image.getWidth() - x5) {
		    		mouse_dx = 0;
		    	}
		    	
		    	if(y5-mouse_dy*dy5 < 0)
		    	{
		    		mouse_dy = 0;
		    	}
		    	else if (layer5Image.getHeight() - y5 + mouse_dy*dy5 < masterFrame.getHeight() - 15) {
		    		mouse_dy = 0;
		    	}
		    	
		    	x1 -= mouse_dx*dx1;
				x2 -= mouse_dx*dx2;
				x3 -= mouse_dx*dx3;
				x4 -= mouse_dx*dx4;
				x5 -= mouse_dx*dx5;
				
				y1 -= mouse_dy*dy1;
				y2 -= mouse_dy*dy2;
				y3 -= mouse_dy*dy3;
				y4 -= mouse_dy*dy4;
				y5 -= mouse_dy*dy5;
				
				repaint();
				*/
		    }
		}
		
		private class MyMouseListener implements MouseListener
		{
		    //Handles action of mouse being moved
		    //@pre mouse has been pressed
		    //@post none
		    //@param MouseEvent e - action of mouse being pressed
		    public void mousePressed(MouseEvent e)
		    {
				 mouseX = e.getX();
				 mouseY = e.getY();
		    }
		
		    public void mouseClicked(MouseEvent e)
		    {
		    	 
		    }
		     
		    public void mouseReleased(MouseEvent e)
		    {
		    	 
		    }
		
		    //Handles action of mouse entering window
		    //@pre mouse has entered the window
		    //@post none
		    //@param MouseEvent e - action of mouse entering the window
		    public void mouseEntered(MouseEvent e)
		    {
				dx1 = 0.05;
				dx2 = 0.08;
				dx3 = 0.12;
				dx4 = 0.15;
				dx5 = 0.25;
				
				dy1 = 0.025;
				dy2 = 0.05;
				dy3 = 0.09;
				dy4 = 0.15;
				dy5 = 0.20;
		    }
		
		    //Handles action of mouse exiting the window
		    //@pre mouse has exited the window
		    //@post none
		    //@param MouseEvent e - action of mouse exiting the window
		    public void mouseExited(MouseEvent e)
		    {
		    	 dx1 = 0;
		    	 dx2 = 0;
		    	 dx3 = 0;
		    	 dx4 = 0;
		    	 dx5 = 0;
		    	 
		    	 dy1 = 0;
		    	 dy2 = 0;
		    	 dy3 = 0;
		    	 dy4 = 0;
		    	 dy5 = 0;
		    }
		}
	}
}
