import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * TO DO:
 * 		Add update of photo to the save function!
 *		Add update of comments to save func
 */
public class GlazeRecipe
{
		private String filePath;
	
		private String name = "Glaze Name";
		private GlazeComponent[] glazeComponents = { new GlazeComponent("Comp 1." , 0.0) };
		private GlazeComponent[] glazeAdds = {new GlazeComponent("Add. 1" , 0.0) };
		private String[] colors = { "Other" };
		private String[] firing = { "Ox." };
		private String lowerCone = "6";
		private String upperCone = "6";
		private String[] finishes = { "Glossy" };
		private String reliability = "5";
		private String[] functionality = { "General" };
		private String stability = "5";
		private String[] combination = { "Other" };
		private String[] comments = { "No Comments..." };
		private GlazePhoto[] photos = { new GlazePhoto() };
		
		public GlazeRecipe() { }
		public GlazeRecipe(String filePath)
		{
			this.filePath = filePath;
			try
			{
				String fileContents = new String(Files.readAllBytes( Paths.get(filePath)));
				String[] lines = fileContents.split("@");
				if(lines.length >= 14)
				{
					name = lines[0];
					glazeComponents = parseComponents(lines[1].split("~"));
					glazeAdds = parseComponents(lines[2].split("~"));
					colors = lines[3].split("~");
					firing = lines[4].split("~");
					lowerCone = lines[5];
					upperCone = lines[6];
					finishes = lines[7].split("~");
					reliability = lines[8].trim();
					functionality = lines[9].split("~");
					stability = lines[10].trim();
					combination = lines[11].split("~");
					comments = lines[12].split("~");
					photos = parsePhotos(lines[13].split("~"));
				}
			} catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Error reading words from the file " + filePath+ " ...");
			}
		}
		
		public GlazePhoto[] parsePhotos(String[] lines)
		{
			GlazePhoto[] allPhotos = new GlazePhoto[lines.length];
			//each line is split into photo filepath and photo description - description <= 15 chars
			for(int k = 0; k < lines.length; k++)
			{
				String[] vals = lines[k].split(";");
				try{
					BufferedImage photo = ImageIO.read(new File(vals[0].trim()));
					String desc = vals[1];
					allPhotos[k] = new GlazePhoto(photo, desc);
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("Error Reading Image from file...");
					//Display JOptionPane....
				}
			}
			return allPhotos;
		}
		public GlazeComponent[] parseComponents(String[] lines)
		{
			GlazeComponent[] components = new GlazeComponent[lines.length];
			for(int k = 0; k < lines.length; k++)
			{
				String[] vals = lines[k].split(";");
				if(vals[0].length() < 3) { return null; }
				components[k] = new GlazeComponent(vals[0],Double.parseDouble(vals[1].trim()));
			}
			return components;
		}
		
		public String getName() { return name; }
		public GlazeComponent[] getComponents() { return glazeComponents; }
		public GlazeComponent[] getAdds() { return glazeAdds; }
		public String[] getFiringAttribute() { return firing; }
		public String[] getColorAttribute() { return colors; }
		public String getLowerCone() { return lowerCone; }
		public String getUpperCone() { return upperCone; }
		public String[] getFinishAttribute() { return finishes; }
		public String getReliabilityAttribute() { return reliability; }
		public String[] getFunctionalityAttribute() { return functionality; }
		public String getStabilityAttribute() { return stability; }
		public String[] getCombinationAttribute() { return combination; }
		public String[] getComments() { return comments; }
		public GlazePhoto[] getPhotos() {return photos; }
		public int getNumPhotos() { return photos.length; }
		
		public void removeComponent(String componentName) 
		{
			GlazeComponent[] updatedArray = new GlazeComponent[glazeComponents.length - 1];
			int count = 0;
			for(int k = 0; k < glazeComponents.length && count < glazeComponents.length - 1; k++)
			{
				if(!glazeComponents[k].getName().equals(componentName)) 
				{
					updatedArray[count] = glazeComponents[k];
					count++;
				}
			}
			this.glazeComponents = updatedArray;
		}
		public void removeAdd(String componentName) 
		{
			if(glazeAdds != null)
			{
				if(glazeAdds.length == 1) { 
					glazeAdds = null;
				} else {
					GlazeComponent[] updatedArray = new GlazeComponent[glazeAdds.length - 1];
					int count = 0;
					for(int k = 0; k < glazeAdds.length && count < glazeAdds.length - 1; k++)
					{
						if(!glazeAdds[k].getName().equals(componentName)) 
						{
							updatedArray[count] = glazeAdds[k];
							count++;
						}
					}
					this.glazeAdds = updatedArray;
				}
			}
		}
		
		public void setName(String newName) { this.name = newName; }
		public void addComponent(GlazeComponent newComponent)
		{
			GlazeComponent[] updatedArray = new GlazeComponent[glazeComponents.length + 1];
			for(int k = 0; k < glazeComponents.length; k++) { updatedArray[k] = glazeComponents[k]; }
			updatedArray[glazeComponents.length] = newComponent;
			glazeComponents = updatedArray;
		}
		public void addAdd(GlazeComponent newComponent)
		{
			if(glazeAdds == null)
			{
				glazeAdds = new GlazeComponent[1];
				glazeAdds[0] = newComponent;
			} else {
				GlazeComponent[] updatedArray = new GlazeComponent[glazeAdds.length + 1];
				for(int k = 0; k < glazeAdds.length; k++) { updatedArray[k] = glazeAdds[k]; }
				updatedArray[glazeAdds.length] = newComponent;
				glazeAdds = updatedArray;
			}
		}
		public void setColor(String[] newColor) { colors = newColor; }
		public void setFiring(String[] newFiring) { firing = newFiring; }
		public void setConeRange(String newLowerCone, String newUpperCone) {lowerCone = newLowerCone; upperCone = newUpperCone; }
		public void setFinish(String[] newFinish) { finishes = newFinish; }
		public void setReliability(String newReliability) { reliability = newReliability; }
		public void setFunctionality(String[] newFunctionality) { functionality = newFunctionality; }
		public void setStability(String newStability) { stability = newStability; }
		public void setCombination(String[] newCombo) { combination = newCombo; }
		public void setComment(String[] newComment) { comments = newComment; }
		public void addPhoto(GlazePhoto newPhoto) 
		{
			GlazePhoto[] temp = new GlazePhoto[photos.length + 1];
			temp[temp.length] = newPhoto;
			photos = temp;
		}
		
		public void updateFile() { updateFile(filePath); } // Use the original file
		public void updateFile(String otherPath) // Save to different location than the original
		{
			BufferedWriter writer = null;
	        try {
	            File glazeFile = new File(otherPath);
	            writer = new BufferedWriter(new FileWriter(glazeFile));
	            
	            writer.write(name + "@\n");
	            
	            String compString = "";
	            for(GlazeComponent gc : glazeComponents) { compString += (gc.getName() + " ; " + gc.getAmount() + " ~ "); }
	            compString = compString.trim().substring(0, compString.length() - 2) + "@\n"; 
	            writer.write(compString);
	            
	            String addString = "";
	            if(glazeAdds != null)
	            {
	            	for(GlazeComponent gc : glazeAdds) { addString += (gc.getName() + " ; " + gc.getAmount() + " ~ "); }
	 	            addString = addString.substring(0, addString.length() - 2) + "@\n"; 
	 	            writer.write(addString);
	            } else {
	            	writer.write("~@\n");
	            }
	           
	            String colorString = "";
	            for(String gc : colors) { colorString += (gc + " ~ "); }
	            colorString = colorString.substring(0, colorString.length() - 2) + "@\n"; 
	            writer.write(colorString);
	            
	            String firingString = "";
	            for(String gc : firing) { firingString += (gc + " ~ "); }
	            firingString = firingString.substring(0, firingString.length() - 2); 
	            writer.write(firingString + "@\n");
	            
	            writer.write(lowerCone + "@\n");
	            writer.write(upperCone + "@\n");
	            
	            String finishString = "";
	            for(String gc : finishes) { finishString += (gc + " ~ "); }
	            finishString = finishString.substring(0, finishString.length() - 2) + "@\n"; 
	            writer.write(finishString);
	            
	            writer.write(reliability + "@\n");
	            
	            String funcString = "";
	            for(String gc : functionality) { funcString += (gc + " ~ "); }
	            funcString = funcString.substring(0, funcString.length() - 2) + "@\n"; 
	            writer.write(funcString);
	            
	            writer.write(stability + "@\n");
	            
	            String comboString = "";
	            for(String gc : combination) { comboString += (gc + " ~ "); }
	            comboString = comboString.substring(0, comboString.length() - 2) + "@\n"; 
	            writer.write(comboString);
	            
	            //comments
	            writer.write("No Comments ... " + "@\n");
	            
	            // photos
	            writer.write("null_image.png ; No Photos ..." + "@\n");
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                writer.close();
	            } catch (Exception e) { e.printStackTrace(); }
	        }
		}
	
}