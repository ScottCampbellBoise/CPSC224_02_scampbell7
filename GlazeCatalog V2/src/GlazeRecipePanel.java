import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

//This class displays a glaze recipe and comments on an optimized panel
	public class GlazeRecipePanel extends JPanel
	{
		private GlazeRecipe recipe;
		
		private JPanel titlePanel;
		private JLabel nameLabel;
		private JLabel coneLabel;
		private JLabel firingLabel;
		
		private JPanel allCompPanel;
		private JPanel componentPanel;
		private JPanel[] components;
		private JPanel addPanel;
		private JPanel[] adds;
		
		private JPanel commentsPanel;
		private JPanel attributesPanel;
		
		public GlazeRecipePanel(GlazeRecipe recipe)
		{
			this.recipe = recipe;
						
			titlePanel = new JPanel();
			nameLabel = new JLabel("~ "  + recipe.getName());
			nameLabel.setFont(new Font(nameLabel.getName(), Font.PLAIN, 25));
			nameLabel.setBorder(new EmptyBorder(5,10,15,0)); //Pad the JLabel (top,left,bottom,right)
			
			coneLabel = new JLabel("∆ " + recipe.getLowerCone().trim() + "-" + recipe.getUpperCone().trim() + ", ");
			coneLabel.setFont(new Font(coneLabel.getName(), Font.PLAIN, 16));
			coneLabel.setBorder(new EmptyBorder(10,5,15,0)); //Pad the JLabel (top,left,bottom,right)
			coneLabel.setHorizontalAlignment(JLabel.RIGHT);
			
			firingLabel = parseFiring();
			firingLabel.setFont(new Font(firingLabel.getName(), Font.PLAIN, 16));
			firingLabel.setBorder(new EmptyBorder(10,5,15,5)); //Pad the JLabel (top,left,bottom,right)
			
			JPanel coneAndFiringPanel = new JPanel();
			coneAndFiringPanel.setLayout(new GridLayout(1,2));
			coneAndFiringPanel.add(coneLabel);
			coneAndFiringPanel.add(firingLabel);
			
			titlePanel.setLayout(new BorderLayout());
			titlePanel.add(nameLabel, BorderLayout.CENTER);
			titlePanel.add(coneAndFiringPanel, BorderLayout.EAST);
			
			componentPanel = new JPanel();
			TitledBorder componentBorder = new TitledBorder("Components (%)");
			componentBorder.setTitleJustification(TitledBorder.LEFT);
			componentBorder.setTitlePosition(TitledBorder.TOP);
			componentPanel.setBorder(componentBorder);
			components = parseComponents(recipe.getComponents());
			componentPanel.setLayout(new GridLayout(components.length,1));
			for(int k = 0; k < components.length; k++) { componentPanel.add(components[k]); }
			
			addPanel = new JPanel();
			TitledBorder addBorder = new TitledBorder("Add (%)");
			addBorder.setTitleJustification(TitledBorder.LEFT);
			addBorder.setTitlePosition(TitledBorder.TOP);
			addPanel.setBorder(addBorder);
			adds = parseComponents(recipe.getAdds());
			addPanel.setLayout(new GridLayout(adds.length,1));
			for(int k = 0; k < adds.length; k++) { addPanel.add(adds[k]); }
			
			//Make a panel containing the components and adds
			JPanel ingredientsPanel = new JPanel();
			ingredientsPanel.setLayout(new BorderLayout());
			ingredientsPanel.add(componentPanel, BorderLayout.CENTER);
			ingredientsPanel.add(addPanel, BorderLayout.SOUTH);
			
			GlazePhotoPanel imagePanel = new GlazePhotoPanel(recipe.getPhotos(), recipe.getNumPhotos());
			
			JPanel ingrAndImagePanel = new JPanel();
			ingrAndImagePanel.setLayout(new BorderLayout());
			ingrAndImagePanel.add(ingredientsPanel, BorderLayout.CENTER);
			ingrAndImagePanel.add(imagePanel, BorderLayout.EAST);
			
			commentsPanel = new JPanel();
			TitledBorder commentsBorder = new TitledBorder("Comments");
			commentsBorder.setTitleJustification(TitledBorder.LEFT);
			commentsBorder.setTitlePosition(TitledBorder.TOP);
			commentsPanel.setBorder(commentsBorder);
			commentsPanel.add(new JLabel(recipe.getComments()[0]));
			
			
			allCompPanel = new JPanel();
			allCompPanel.setLayout(new BorderLayout());
			allCompPanel.add(titlePanel, BorderLayout.NORTH);
			allCompPanel.add(ingrAndImagePanel, BorderLayout.CENTER);
			
			setLayout(new BorderLayout());
			add(allCompPanel, BorderLayout.CENTER);
			add(commentsPanel, BorderLayout.SOUTH);
		}
		
		private JPanel[] parseComponents(GlazeComponent[] components)
		{
			if(components != null)
			{
				JPanel[] compPanels = new JPanel[components.length];
				
				for(int k = 0; k < components.length; k++)
				{
					JPanel panel = new JPanel();
					JLabel componentLabel = new JLabel(components[k].getName());
					componentLabel.setBorder(new EmptyBorder(0,15,0,15)); //Pad the JLabel (top,left,bottom,right)
					JLabel addLabel = new JLabel("" + components[k].getAmount());
					addLabel.setBorder(new EmptyBorder(0,15,0,15)); //Pad the JLabel (top,left,bottom,right)
					addLabel.setHorizontalAlignment(JLabel.RIGHT);
					addLabel.setVerticalAlignment(JLabel.CENTER);
					
					panel.setLayout(new BorderLayout());
					panel.add(componentLabel, BorderLayout.WEST);
					panel.add(addLabel, BorderLayout.CENTER);
					compPanels[k] = panel;
				}
				
				return compPanels;
			} else {
				return new JPanel[] {new JPanel()};
			}
		}
		private JLabel parseFiring()
		{
			String content = "";
			String[] attributes = recipe.getFiringAttribute();
			
			for(int k = 0; k < attributes.length; k++)
			{
				content += (attributes[k] + " ");
			}
			return new JLabel(content);
		}
	}