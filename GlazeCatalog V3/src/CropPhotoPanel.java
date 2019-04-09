import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** 
 * This class should accept in a BufferedImage as an argument and allow the
 * 		user to move the photo around in a fixed region, and resize the photo
 * 		* uses a mouse listener to implement the dragging aspect and a timer 
 * 			to update the panel if the photo has been moved
 */
public class CropPhotoPanel extends JFrame{
	private JFrame masterFrame;
	
	private final int FRAME_WIDTH = 480;
	private final int FRAME_HEIGHT = 600;
	
	private final int IMAGE_WIDTH = 240; // NOTE: these values are twice as large as they need to be for compression sake
	private final int IMAGE_HEIGHT = 300;
	
	private BufferedImage rawImage;
	private String rawPath;
	private int RAW_IMAGE_WIDTH;
	private int RAW_IMAGE_HEIGHT;
	
	private ResizeablePhoto resizeablePhoto;
	private JLabel photoLabel;
	private JSlider scaleSlider;
	
	public static void main(String[] args)
	{
		try{
			
			new CropPhotoPanel("/Users/ScottCampbell/Desktop/img.png");
		} catch(Exception e) {
			System.out.println("Error Reading a the file ... ");
		}
		
		
	}
	
	public CropPhotoPanel(String rawPath)
	{
		try{
			this.rawPath = rawPath;
			this.rawImage = ImageIO.read(new File(rawPath));
			RAW_IMAGE_WIDTH = rawImage.getWidth();
			RAW_IMAGE_HEIGHT = rawImage.getHeight();
			
			setTitle("Upload a Photo");
			setSize(FRAME_WIDTH, FRAME_HEIGHT);
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			masterFrame = this;
			
			createPanel();
			
			setVisible(true);
		} catch(Exception e) {
			System.out.println("Error reading in file!!!");
		}
	}
	
	private void createPanel()
	{
		resizeablePhoto = new ResizeablePhoto();
		
		scaleSlider = new JSlider(JSlider.HORIZONTAL, 25, 300, 100);
		scaleSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e)
			{
				rawImage = getScaledImage(rawImage, RAW_IMAGE_WIDTH*scaleSlider.getValue()/100, RAW_IMAGE_HEIGHT*scaleSlider.getValue()/100);
				resizeablePhoto.repaintPanel();
			}
		});
		scaleSlider.setMajorTickSpacing(25);
		scaleSlider.setMinorTickSpacing(5);
		scaleSlider.setPaintTicks(true);
		scaleSlider.setPaintLabels(true);
		
		setLayout(new BorderLayout());
		add(resizeablePhoto, BorderLayout.CENTER);
		add(scaleSlider, BorderLayout.SOUTH);
	}
	private BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
	
	/**
	 * This class alters the dimensions of a photo on a JLabel
	 */
	private class ResizeablePhoto extends JPanel implements ActionListener
	{
		protected Timer timer;
		private final int delay = 50;
		
		private int mouse_x;
		private int mouse_y;
		private int imgX = 0;
		private int imgY = 0;
		
		private int dx;
		private int dy;
		
		private final int screenColor = new Color(30, 30, 30, 200).getRGB();
		private final int transColor = new Color(0,0,0,0).getRGB();
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
		    		if( col > ((screenImg.getWidth() / 2) - (IMAGE_WIDTH / 2)) &&
		    				col < ((screenImg.getWidth() / 2) + (IMAGE_WIDTH / 2)) &&
		    				row > ((screenImg.getHeight() / 2) - (IMAGE_HEIGHT / 2)) &&
		    				row < ((screenImg.getHeight() / 2) + (IMAGE_HEIGHT / 2))) 
		    		{
		    			screenImg.setRGB(col,row,transColor);
		    		} else {
		    			screenImg.setRGB(col,row,screenColor);
		    		}
		    	}
		    }
		    
		    Graphics g = screenImg.getGraphics();
		    g.setColor(Color.BLACK);
		    g.drawRect((screenImg.getWidth() / 2) - (IMAGE_WIDTH / 2) , (screenImg.getHeight() / 2) - (IMAGE_HEIGHT / 2) , IMAGE_WIDTH, IMAGE_HEIGHT);
		    g.dispose();
		    
		    imgX = (rawImage.getWidth()/2 - FRAME_WIDTH/2);
		    imgY = (rawImage.getHeight()/2 - FRAME_HEIGHT/2); 
		    
		    //timer = new Timer(delay, this);
		    //timer.start();
		}
		
		public void repaintPanel() { repaint(); }
		
		public void actionPerformed(ActionEvent e)
		{
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    
		    //Cover the panel in a half transparent screen except for a section in the middle for the photo
		    g.drawImage(rawImage, -imgX, -imgY, null);
		    g.drawImage(screenImg, -(screenImg.getWidth()/2 - FRAME_WIDTH/2), -(screenImg.getHeight()/2 - FRAME_HEIGHT/2), null);
		}
		
		private class MyMouseListener implements MouseListener
		{
			public void mouseClicked(MouseEvent e) { }
			
			public void mousePressed(MouseEvent e) {
				mouse_x = e.getX();
				mouse_y = e.getY();
			}
			
			public void mouseReleased(MouseEvent e) { }
			
			public void mouseEntered(MouseEvent e) { }
			
			public void mouseExited(MouseEvent e) { }
		}
		private class MyMouseMotionListener implements MouseMotionListener
		{
			public void mouseDragged(MouseEvent e) { 
				int mouse_dx = e.getX() - mouse_x;
		    	int mouse_dy = e.getY() - mouse_y;
		    	
		    	if(imgX - mouse_dx < 0)
		    	{
		    		mouse_dx = 0;
		    	}
		    	else if (imgX - mouse_dx > rawImage.getWidth() - imgX) {
		    		mouse_dx = 0;
		    	}
		    	
		    	if(imgY - mouse_dy < 0)
		    	{
		    		mouse_dy = 0;
		    	}
		    	else if (rawImage.getHeight() - imgY + mouse_dy < masterFrame.getHeight()) {
		    		mouse_dy = 0;
		    	}
				imgX -= mouse_dx;
				imgY -= mouse_dx;
				
				repaint();
			}

			public void mouseMoved(MouseEvent e) { }
		}
		
	}
}
