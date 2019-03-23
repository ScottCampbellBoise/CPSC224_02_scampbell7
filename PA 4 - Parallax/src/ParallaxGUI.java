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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ParallaxGUI extends JFrame{
	
	private ParallaxPanel masterPanel;
	
	private BufferedImage layer1Image;
	private BufferedImage layer2Image;
	private BufferedImage layer3Image;
	private BufferedImage layer4Image;
	private BufferedImage layer5Image;
	
	public static void main(String[] args)
	{
		new ParallaxGUI();
	}
	
	public ParallaxGUI()
	{
		setTitle("Parallax Motion Example");
		setSize(600,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		uploadPhotos();
		
		masterPanel = new ParallaxPanel();
				
		add(masterPanel);
		
		setVisible(true);
		validate();
		repaint();
	}
	
	private void uploadPhotos()
	{
		try{
			layer1Image = ImageIO.read(new File("Images/layer1.png"));
			layer2Image = ImageIO.read(new File("Images/layer2.png"));
			layer3Image = ImageIO.read(new File("Images/layer3.png"));
			layer4Image = ImageIO.read(new File("Images/layer4.png"));
			layer5Image = ImageIO.read(new File("Images/layer5.png"));
			
		}catch(IOException e) {System.out.println("Error Uploading scene photos!");}
	}
	
	class ParallaxPanel extends JPanel implements ActionListener
	{
		protected Timer timer;
		private final int delay = 50;
		
		private int mouseX, mouseY;
		
		private int x1, x2, x3, x4, x5;
		private int y1, y2, y3, y4, y5;
		
		private final double dx1 = 0.1;
		private final double dx2 = 0.2;
		private final double dx3 = 0.35;
		private final double dx4 = 0.6;
		private final double dx5 = 1.0;
		
		public ParallaxPanel()
		{
			x1 = x2 = x3 = x4 = x5 = Math.abs(layer1Image.getWidth() - this.getWidth())/2;
		    y1 = y2 = y3 = y4 = y5 = Math.abs(layer1Image.getHeight() - this.getHeight())/8;
		    
		    addMouseListener(new MyMouseListener());
		    
		    timer = new Timer(delay, this);
			timer.start();
		}
		
		public void actionPerformed(ActionEvent e)
		{
			/**
			x1 += 10*dx1;
			x2 += 10*dx2;
			x3 += 10*dx3;
			x4 += 10*dx4;
			x5 += 10*dx5;
			*/
			
			repaint();
		}
		
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
			public void mouseDragged(MouseEvent e)
		    {
		    	int mouse_dx = e.getX() - mouseX;
		    	int mouse_dy = e.getY() - mouseY;
		    	
		    	x1 += mouse_dx*dx1;
				x2 += mouse_dx*dx2;
				x3 += mouse_dx*dx3;
				x4 += mouse_dx*dx4;
				x5 += mouse_dx*dx5;
				
				repaint();
		    }
		      
		    public void mouseMoved(MouseEvent e)
		    {
		    	 
		    }
		}
		
		private class MyMouseListener implements MouseListener
		{
			public void mousePressed(MouseEvent e)
		    {
				 mouseX = e.getX();
				 mouseY = e.getY();
				 System.out.println("X: " + mouseX + "\tY: " + mouseY );
		    }
		
		    public void mouseClicked(MouseEvent e)
		    {
		    	 
		    }
		     
		    public void mouseReleased(MouseEvent e)
		    {
		    	 
		    }
		
		    public void mouseEntered(MouseEvent e)
		    {
		    	 
		    }
		
		    public void mouseExited(MouseEvent e)
		    {
		    	 
		    }
		}
	}
}
