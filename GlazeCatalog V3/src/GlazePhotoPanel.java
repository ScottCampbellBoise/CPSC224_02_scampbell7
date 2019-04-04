import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

// This class stores an optimized panel to display the glaze photos
	public class GlazePhotoPanel extends JPanel
	{
		private final int SINGLE_DIMENSION = 250; // max dimension if only one photo
		private final int MULTIPLE_DIMENSION = 120; // max dimension if 2-4 photos
		
		private GlazePhoto[] originalPhotos;
		private BufferedImage[] displayPhotos;
		private String[] displayPhotoDesc;
		
		public GlazePhotoPanel(GlazePhoto[] photos, int maxNum)
		{
			originalPhotos = photos;
			if(maxNum == 1 || photos.length == 1) {
				uploadPhotos(1);
				createPanel(1);
			} else if (maxNum > 0 && maxNum <= 4) {
				uploadPhotos(maxNum);
				createPanel(maxNum);
			} else if(maxNum <= 0) {
				try {
					BufferedImage img = ImageIO.read(new File("null_image.png"));
					img = resizePhoto(img,SINGLE_DIMENSION);
					displayPhotos = new BufferedImage[1];
					displayPhotos[0] = img;
					
					displayPhotoDesc = new String[1];
					displayPhotoDesc[0] = "No desc ...";
					
					createPanel(1);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Could not find the null photo icon ... ");
				}
			} else { 
				uploadPhotos(4);
				createPanel(4);
			}
		}
		
		private void createPanel(int num)
		{
			JPanel topPhotoPanel = new JPanel(); // contains top 2
			JPanel botPhotoPanel = new JPanel(); // contains bot 2
					
			if(num == 1) {
				setLayout(new BorderLayout());
				add(createPhotoDescPanel(displayPhotos[0], displayPhotoDesc[0], 1), BorderLayout.CENTER); 
			} else if (num == 2) {
				setLayout(new GridLayout(1,2));
				add(createPhotoDescPanel(displayPhotos[0], displayPhotoDesc[0], 1)); 
				add(createPhotoDescPanel(displayPhotos[1], displayPhotoDesc[1], 2)); 
			} else if (num == 3) {
				topPhotoPanel.setLayout(new GridLayout(1,2));
				topPhotoPanel.add(createPhotoDescPanel(displayPhotos[0], displayPhotoDesc[0], 1)); 
				topPhotoPanel.add(createPhotoDescPanel(displayPhotos[1], displayPhotoDesc[1], 2));
				
				botPhotoPanel.setLayout(new BorderLayout());
				botPhotoPanel.add(createPhotoDescPanel(displayPhotos[2], displayPhotoDesc[2], 3), BorderLayout.CENTER); 
				
				setLayout(new GridLayout(2,1));
				add(topPhotoPanel);
				add(botPhotoPanel);
			} else {
				topPhotoPanel.setLayout(new GridLayout(1,2));
				topPhotoPanel.add(createPhotoDescPanel(displayPhotos[0], displayPhotoDesc[0], 1)); 
				topPhotoPanel.add(createPhotoDescPanel(displayPhotos[1], displayPhotoDesc[1], 2));
				
				botPhotoPanel.setLayout(new GridLayout(1,2));
				botPhotoPanel.add(createPhotoDescPanel(displayPhotos[2], displayPhotoDesc[2], 3)); 
				botPhotoPanel.add(createPhotoDescPanel(displayPhotos[3], displayPhotoDesc[3], 4));
				
				setLayout(new GridLayout(2,1));
				add(topPhotoPanel);
				add(botPhotoPanel);
			}
		}
		private JPanel createPhotoDescPanel(BufferedImage img, String desc, int pos)
		{
			JPanel myPanel = new JPanel();
			JLabel photoLabel = new JLabel(new ImageIcon(img));
			
			TitledBorder border = new TitledBorder(pos + ": " + desc);
			border.setTitleJustification(TitledBorder.CENTER);
		    border.setTitlePosition(TitledBorder.BOTTOM);
		    myPanel.setBorder(border);
			
			myPanel.setLayout(new BorderLayout());
			myPanel.add(photoLabel, BorderLayout.CENTER);
			
			return myPanel;
			
		}
		private void uploadPhotos(int num)
		{
			displayPhotos = new BufferedImage[num];
			displayPhotoDesc = new String[num];
			int dim;
			if(num == 1) { dim = SINGLE_DIMENSION; }
			else { dim = MULTIPLE_DIMENSION; }
			
			for(int k = 0; k < num; k++)
			{
				displayPhotos[k] = resizePhoto(originalPhotos[k].getPhoto(), dim);
				displayPhotoDesc[k] = originalPhotos[k].getDesc();
			}
		}
		private BufferedImage resizePhoto(BufferedImage original, int newDim)
		{
			int w = original.getWidth();  
		    int h = original.getHeight();  
		    BufferedImage dimg = new BufferedImage(newDim, newDim, original.getType());  
		    Graphics2D g = dimg.createGraphics();  
		    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		    RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
		    g.drawImage(original, 0, 0, newDim, newDim, 0, 0, w, h, null);  
		    g.dispose();  
		    return dimg;  
		}
	}