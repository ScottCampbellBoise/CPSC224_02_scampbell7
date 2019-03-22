import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		private int delay = 100;
		protected Timer timer;
		
		private int x = 0;
		private int y = 0;
		
		private int dx;
		private int dy;
		
		public ParallaxPanel()
		{
			x = Math.abs(layer1Image.getWidth() - this.getWidth())/2;
		    y = Math.abs(layer1Image.getHeight() - this.getHeight())/8;
		    
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
		    
		    g.drawImage(layer1Image, -x, -y, null);
		    g.drawImage(layer2Image, -x, -y, null);
		    g.drawImage(layer3Image, -x, -y, null);
		    g.drawImage(layer4Image, -x, -y, null);
		    g.drawImage(layer5Image, -x, -y, null);
		}
	}
	
}
