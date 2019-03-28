import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/** 
 * This class should accept in a BufferedImage as an argument and allow the
 * 		user to move the photo around in a fixed region, and resize the photo
 * 		* uses a mouse listener to implement the dragging aspect and a timer 
 * 			to update the panel if the photo has been moved
 */
public class CropPhotoPanel extends JPanel{
	
	private final int IMAGE_WIDTH = 160; // NOTE: these values are twice as large as they need to be for compression sake
	private final int IMAGE_HEIGH = 200;
	
	private BufferedImage rawImage;
	
	private JLabel photoLabel;
	
	public CropPhotoPanel(BufferedImage rawImage)
	{
		this.rawImage = rawImage;
		
		createPanel();
	}
	
	private void createPanel()
	{
		
	}
	
	/**
	 * This class alters the dimensions of a photo on a JLabel
	 */
	private class ResizeablePhoto extends JPanel implements ActionListener
	{
		protected Timer timer;
		private final int delay = 100;
		
		private final int screenColor = new Color(50, 100, 200, 30).getRGB();
		private final int transColor = new Color(256,256,256,0).getRGB();
		private BufferedImage screenImg;
		
		public ResizeablePhoto()
		{
			addMouseListener(new MyMouseListener());
		    addMouseMotionListener(new MyMouseMotionListener());

		    screenImg = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
		    for(int row = 0; row < screenImg.getWidth(); row++)
		    {
		    	for(int col = 0; col < screenImg.getHeight(); col++)
		    	{
		    		if(		col > ((screenImg.getWidth() / 2) - (IMAGE_WIDTH / 2)) &&
		    				col < ((screenImg.getWidth() / 2) + (IMAGE_WIDTH / 2)) &&
		    				row > ((screenImg.getHeight() / 2) - (IMAGE_WIDTH / 2)) &&
		    				row < ((screenImg.getHeight() / 2) + (IMAGE_WIDTH / 2))) 
		    		{
		    			screenImg.setRGB(col,row,transColor);
		    		} else {
		    			screenImg.setRGB(col,row,screenColor);
		    		}
		    	}
		    }
		    
		    timer = new Timer(delay, this);
		    timer.start();
		}
		
		public void actionPerformed(ActionEvent e)
		{
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    
		    //Cover the panel in a half transparent screen except for a section in the middle for the photo
		}
		
		private class MyMouseListener implements MouseListener
		{
			public void mouseClicked(MouseEvent e) { }
			
			public void mousePressed(MouseEvent e) { }
			
			public void mouseReleased(MouseEvent e) { }
			
			public void mouseEntered(MouseEvent e) { }
			
			public void mouseExited(MouseEvent e) { }
		}
		private class MyMouseMotionListener implements MouseMotionListener
		{
			public void mouseDragged(MouseEvent e) { }

			public void mouseMoved(MouseEvent e) { }
		}
		
	}
}
