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
		private String[] finishes = { "Unknown" };
		private String reliability = "Unknown";
		private String[] functionality = { "Unknown" };
		private String stability = "Unknown";
		private String[] combination = { "Unknown" };
		private String comments =  "No Comments...";
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
					comments = lines[12];
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
					allPhotos[k] = new GlazePhoto(vals[0].trim(), photo, desc);
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("GlazeRecipe: Error Reading Image from file: "+filePath);
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
		public String getComments() { return comments; }
		public GlazePhoto[] getPhotos() {return photos; }
		public int getNumPhotos() { return photos.length; }
		
		public void removeComponent(String componentName, double amount) 
		{
			if(glazeComponents != null)
			{
				if(glazeComponents.length == 1 && glazeComponents[0].getName().trim().toLowerCase().equals(componentName) && glazeComponents[0].getAmount() == amount) { 
					glazeComponents = null;
				} else {
					//Make sure that the componet to remove exists
					boolean exists = false;
					for(int k = 0; k < glazeComponents.length; k++) { 
						if(glazeComponents[k].getAmount() == amount && glazeComponents[k].getName().toLowerCase().trim().equals(componentName.trim().toLowerCase())) {
							exists = true;
							break;
						}
					}
					
					if(exists) {
						GlazeComponent[] updatedArray = new GlazeComponent[glazeComponents.length - 1];
						int count = 0;
						for(int k = 0; k < glazeComponents.length; k++) {
							if(!glazeComponents[k].getName().equals(componentName) && glazeComponents[k].getAmount() != amount) {
								updatedArray[count] = glazeComponents[k];
								count++;
							}
						}
						this.glazeComponents = updatedArray;
						System.out.println("Succesfully Removed!");
					}
				}
			}
		}
		public void removeAdd(String componentName, double amount) 
		{
			if(glazeAdds != null)
			{
				if(glazeAdds.length == 1 && glazeAdds[0].getName().trim().toLowerCase().equals(componentName) && glazeAdds[0].getAmount() == amount) { 
					glazeAdds = null;
					System.out.println("Succesfully Removed!");
				} else {
					//Make sure that the componet to remove exists
					boolean exists = false;
					for(int k = 0; k < glazeAdds.length; k++) { 
						if(glazeAdds[k].getAmount() == amount && glazeAdds[k].getName().toLowerCase().trim().equals(componentName.trim().toLowerCase())) {
							exists = true;
							break;
						}
					}
					
					if(exists) {
						GlazeComponent[] updatedArray = new GlazeComponent[glazeAdds.length - 1];
						int count = 0;
						for(int k = 0; k < glazeAdds.length; k++) {
							if(!glazeAdds[k].getName().equals(componentName) && glazeAdds[k].getAmount() != amount) {
								updatedArray[count] = glazeAdds[k];
								count++;
							}
						}
						this.glazeAdds = updatedArray;
						System.out.println("Succesfully Removed!");
					}
				}
			}
		}
		public void removePhoto(String path)
		{
			for(int k = 0; k < photos.length; k++)
			{
				//set the remove picture to null
				if(photos[k].getPath().contains(path)) { photos[k] = null; }
			}
			//resize the array to remove the null
			if(photos.length == 1) {
				
				photos = new GlazePhoto[1]; photos[1] = new GlazePhoto();
			} else if (photos.length > 1) {
				GlazePhoto[] temp = new GlazePhoto[photos.length - 1];
				int count = 0;
				for(int k = 0; k < photos.length; k++)
				{
					if(photos[k] != null) {
						temp[count] = photos[k];
						count++;
					}
				}
				photos = temp;
			}
			
		}
		
		public void setName(String newName) { this.name = newName; }
		public void addComponent(GlazeComponent newComponent)
		{
			if(glazeComponents == null)
			{
				glazeComponents = new GlazeComponent[1];
				glazeComponents[0] = newComponent;
			} else {
				//Check if duplicate first ... if there is a component with the same name and amount, then do not add it
				boolean isDuplicate = false;
				for(int k = 0; k < glazeComponents.length; k++) { 
					if(glazeComponents[k].getName().toLowerCase().trim().equals(newComponent.getName().toLowerCase().trim())
							&& glazeComponents[k].getAmount() == newComponent.getAmount()) 
					{
						isDuplicate = true; break; 
					}
				}
				
				//check if there is a component with the same name already. if so, replace it with the new component
				boolean isUpdate = false;
				for(int k = 0; k < glazeComponents.length; k++) { 
					if(glazeComponents[k].getName().trim().toLowerCase().equals(newComponent.getName().trim().toLowerCase()) 
							&& glazeComponents[k].getAmount() != newComponent.getAmount()) 
					{
						isUpdate = true;
						glazeComponents[k] = newComponent;
						break;
					}
				}
				
				//If it isn't a duplicate, and if it wasn't updated, add to the array
				if(!isDuplicate && !isUpdate)
				{
					GlazeComponent[] updatedArray = new GlazeComponent[glazeComponents.length + 1];
					for(int k = 0; k < glazeComponents.length; k++) { updatedArray[k] = glazeComponents[k]; }
					updatedArray[glazeComponents.length] = newComponent;
					glazeComponents = updatedArray;
				}
			}
		}
		public void addAdd(GlazeComponent newComponent)
		{
			if(glazeAdds == null)
			{
				glazeAdds = new GlazeComponent[1];
				glazeAdds[0] = newComponent;
			} else {
				//Check if duplicate first ... if there is a component with the same name and amount, then do not add it
				boolean isDuplicate = false;
				for(int k = 0; k < glazeAdds.length; k++) { 
					if(glazeAdds[k].getName().toLowerCase().trim().equals(newComponent.getName().toLowerCase().trim())
							&& glazeAdds[k].getAmount() == newComponent.getAmount()) 
					{
						isDuplicate = true; break; 
					}
				}
				
				//check if there is a component with the same name already. if so, replace it with the new component
				boolean isUpdate = false;
				for(int k = 0; k < glazeAdds.length; k++) { 
					if(glazeAdds[k].getName().trim().toLowerCase().equals(newComponent.getName().trim().toLowerCase()) 
							&& glazeAdds[k].getAmount() != newComponent.getAmount()) 
					{
						isUpdate = true;
						glazeAdds[k] = newComponent;
						break;
					}
				}
				
				//If it isn't a duplicate, and if it wasn't updated, add to the array
				if(!isDuplicate && !isUpdate)
				{
					GlazeComponent[] updatedArray = new GlazeComponent[glazeAdds.length + 1];
					for(int k = 0; k < glazeAdds.length; k++) { updatedArray[k] = glazeAdds[k]; }
					updatedArray[glazeAdds.length] = newComponent;
					glazeAdds = updatedArray;
				}
			}
		}
		public void setComponents(GlazeComponent[] newComps) {this.glazeComponents = newComps;}
		public void setAdds(GlazeComponent[] newAdds) {this.glazeAdds = newAdds;}
		public void setColor(String[] newColor) { colors = newColor; }
		public void setFiring(String[] newFiring) { firing = newFiring; }
		public void setConeRange(String newLowerCone, String newUpperCone) {lowerCone = newLowerCone; upperCone = newUpperCone; }
		public void setFinish(String[] newFinish) { finishes = newFinish; }
		public void setReliability(String newReliability) { reliability = newReliability; }
		public void setFunctionality(String[] newFunctionality) { functionality = newFunctionality; }
		public void setStability(String newStability) { stability = newStability; }
		public void setCombination(String[] newCombo) { combination = newCombo; }
		public void setComment(String newComment) { comments = newComment; }
		public void addPhoto(GlazePhoto newPhoto) 
		{
			GlazePhoto[] temp = new GlazePhoto[photos.length + 1];
			for(int k = 0; k < photos.length; k++) {temp[k] = photos[k];}
			temp[temp.length - 1] = newPhoto;
			photos = temp;
		}
		public void setPhotoDesc(String path, String newDesc)
		{
			//Will set the photo description corresponding to path
			for(GlazePhoto gp : photos)
			{
				if(gp.getPath().contains(path)) { gp.setDesc(newDesc); }
			}
		}
		
		public void updateFile() { updateFile(filePath); } // Use the original file
		public void updateFile(String otherPath) // Save to different location than the original
		{
			BufferedWriter writer = null;
	        try {
	            File glazeFile = new File(otherPath);
	            writer = new BufferedWriter(new FileWriter(glazeFile));
	            
	            writer.write(name.trim() + "@\n");
	            
	            String compString = "";
	            for(GlazeComponent gc : glazeComponents) { compString += (gc.getName().trim() + " ; " + gc.getAmount() + " ~ "); }
	            compString = compString.trim().substring(0, compString.length() - 3).trim() + "@\n"; 
	            writer.write(compString);
	            
	            String addString = "";
	            if(glazeAdds != null)
	            {
	            	for(GlazeComponent gc : glazeAdds) { addString += (gc.getName().trim() + " ; " + gc.getAmount() + " ~ "); }
	 	            addString = addString.substring(0, addString.length() - 3).trim() + "@\n"; 
	 	            writer.write(addString);
	            } else {
	            	writer.write("@\n");
	            }
	           
	            String colorString = "";
	            for(String gc : colors) { colorString += (gc.trim() + " ~ "); }
	            colorString = colorString.substring(0, colorString.length() - 3).trim() + "@\n"; 
	            writer.write(colorString);
	            
	            String firingString = "";
	            for(String gc : firing) { if(gc != null) {firingString += (gc.trim() + " ~ ");} }
	            firingString = firingString.substring(0, firingString.length() - 3).trim() + "@\n"; 
	            writer.write(firingString);
	            
	            writer.write(lowerCone + "@\n");
	            writer.write(upperCone + "@\n");
	            
	            String finishString = "";
	            for(String gc : finishes) { finishString += (gc.trim() + " ~ "); }
	            finishString = finishString.substring(0, finishString.length() - 3).trim() + "@\n"; 
	            writer.write(finishString);
	            
	            writer.write(reliability.trim() + "@\n");
	            
	            String funcString = "";
	            for(String gc : functionality) { funcString += (gc.trim() + " ~ "); }
	            funcString = funcString.substring(0, funcString.length() - 3).trim() + "@\n"; 
	            writer.write(funcString);
	            
	            writer.write(stability.trim() + "@\n");
	            
	            String comboString = "";
	            for(String gc : combination) { comboString += (gc.trim() + " ~ "); }
	            comboString = comboString.substring(0, comboString.length() - 3).trim() + "@\n"; 
	            writer.write(comboString);
	            
	            //comments
	            writer.write(comments + "@\n");
	            
	            // photos
	            String photoString = "";
	            for(GlazePhoto gp : this.photos)
	            {
	            	photoString += (gp.getPath() + " ; " + gp.getDesc().trim() + " ~ ");
	            }
	            photoString = photoString.substring(0, photoString.length() - 3).trim() + "@\n"; 
	            writer.write(photoString);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                writer.close();
	            } catch (Exception e) { e.printStackTrace(); }
	        }
		}
	
}