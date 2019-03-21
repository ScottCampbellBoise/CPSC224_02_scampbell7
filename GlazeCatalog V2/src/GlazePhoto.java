import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

// This class stores both a photo and a description of the photo
	public class GlazePhoto
	{
		BufferedImage photo;
		String desc;
		
		public GlazePhoto()
		{
			try {
				this.photo = ImageIO.read(new File("null_image.png"));
				this.desc = "No Desc";
			} catch(Exception e) {
				System.out.println("Error reading null_image.png in GlazePhoto Class ...");
			}
		}
		public GlazePhoto(BufferedImage photo, String desc)
		{
			this.photo = photo;
			this.desc = desc;
		}
		
		public BufferedImage getPhoto(){ return photo; }
		public String getDesc() { return desc; }
		public void setPhoto(BufferedImage newPhoto) { photo = newPhoto; }
		public void setDesc(String newDesc) { desc = newDesc; }
	}