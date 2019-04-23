import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GlazeGUI extends JFrame
{
	/**
	 * V3
	 * 
	 * Master TO DO:
	 * 
	 * Clean up all the code!!!
	 * Comment add the Classes!!!
	 * 
	 * Make a Panel to display/add Glaze Layering Tests - IF TIME!!!
	 * 		Allow user to choose which glazes they want to see layered
	 * 		Allow print/export image file
	 * 
	 * GlazeEditPanel ...
	 * 		Check if the user has editing privlages before allowing them to make changes - IF TIME!!!
	 * 		Check if the components add up to 100. If not, offer a button to scale to 100

	 * Make a 'main page' 
	 * 		has a sign in option that allows the user to edit the recipes - IF TIME!!!
	 * 		has a search bar to find a glaze
	 * 		has a export button to open a Panel with different export options
	 * 			select glazes, organization, etc...
	 */
	
	GlazeSearchPanel glazeSearchPanel;
	GlazeEditPanel editPanel;
	GlazeEditPanel editPanel2;
	GlazeEditPanel editPanel3;

	
	private boolean hasEditPrivlages = true;
	
	
	public static void main(String[] args)
	{			
		new GlazeGUI();
	}
	
	
	public GlazeGUI()
	{	
		setTitle("Glaze Catalog - Search for a Glaze");
		setSize(540,360);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainPanel mp = new MainPanel();
		add(mp);
		
		pack();
		setVisible(true);
	}
	
	private void openEditPanel(GlazeRecipe theRecipe)
	{
		JFrame editFrame = new JFrame("Recipe Editor: " + theRecipe.getName());
		editFrame.setSize(540, 360);
		editFrame.setResizable(true);
		editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		GlazeEditPanel ep = new GlazeEditPanel(theRecipe);
		editFrame.add(ep);
		
		editFrame.pack();
		editFrame.setVisible(true);
	}
	
	private class MainPanel extends JPanel
	{
		GlazePreviewPanel previewPanel;
		GlazeSearchPanel searchPanel;
		
		public MainPanel()
		{
			previewPanel = new GlazePreviewPanel();
			searchPanel = new GlazeSearchPanel();
			
			setLayout(new BorderLayout());
			add(previewPanel, BorderLayout.NORTH);
			add(searchPanel, BorderLayout.CENTER);
		}
		
		private class GlazePreviewPanel extends JPanel implements ActionListener, MouseListener
		{
			private GlazeRecipe[] allRecipes;
			protected Timer timer;
			private int delay = 6000;
			
			private final int IMAGE_WIDTH = 100;
			private final int IMAGE_HEIGHT = 120;
			
			private JLabel nameLabel;
			private JLabel coneLabel;
			private JLabel firingLabel;
			private JPanel imagePanel;
			private JLabel leftLabel;
			private JLabel rightLabel;
			
			private Font titleFont = new Font("Helvetica",Font.PLAIN, 24);
			private Font infoFont = new Font("Helvetica",Font.PLAIN, 15);
			
			private int imagePos = 0;
			
			public GlazePreviewPanel()
			{
				setBackground(Color.WHITE);
				setBorder(new EmptyBorder(20,20,20,20));
				
				allRecipes = uploadRecipes();
				allRecipes = shuffle(allRecipes);
				allRecipes = shuffle(allRecipes);
				allRecipes = shuffle(allRecipes);
				addMouseListener(this);
				
				createPanel();

				timer = new Timer(delay, this);
			    timer.start();
			}
			
			private void createPanel()
			{
				leftLabel = new JLabel("");
				rightLabel = new JLabel("");
				imagePanel = new JPanel();
				imagePanel.setLayout(new BorderLayout());
				imagePanel.add(leftLabel, BorderLayout.EAST);
				imagePanel.add(rightLabel, BorderLayout.WEST);
				
				nameLabel = new JLabel("");
				nameLabel.setFont(titleFont);
				coneLabel = new JLabel("");
				coneLabel.setFont(infoFont);
				firingLabel = new JLabel("");
				firingLabel.setFont(infoFont);
				
				JPanel infoPanel = new JPanel();
				JPanel subInfoPanel = new JPanel();
				subInfoPanel.setLayout(new GridLayout(2,1));
				infoPanel.setBorder(new EmptyBorder(15,25,15,15));
				infoPanel.setLayout(new BorderLayout());
				subInfoPanel.add(coneLabel);
				subInfoPanel.add(firingLabel);
				infoPanel.add(nameLabel, BorderLayout.NORTH);
				infoPanel.add(subInfoPanel, BorderLayout.SOUTH);
				
				setLayout(new BorderLayout());
				add(infoPanel, BorderLayout.CENTER);
				add(imagePanel, BorderLayout.EAST);
				
				updatePanel(imagePos);
			}
			public void actionPerformed(ActionEvent e)
			{
				imagePos = (imagePos + 1) % allRecipes.length;
				updatePanel(imagePos);
			}
			private BufferedImage resize(BufferedImage orig)
			{
				BufferedImage resized = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
				Graphics g = resized.getGraphics();
				g.drawImage(orig, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
				g.dispose();
				return resized;
			}
			private void updatePanel(int pos)
			{
				GlazeRecipe theRecipe = allRecipes[pos];
				nameLabel.setText(theRecipe.getName());
				coneLabel.setText("Cone Range: " + theRecipe.getLowerCone() + " to " + theRecipe.getUpperCone());
				firingLabel.setText("Firing Types: " + parseAtmoshpere(theRecipe));
				parseImages(theRecipe);
				repaint();
			}
			private void parseImages(GlazeRecipe theRecipe)
			{
				GlazePhoto[] images = theRecipe.getPhotos();
				if(images == null) {
					leftLabel.setIcon(new ImageIcon(GlazeRecipe.NULL_IMAGE));
				} else if(images.length == 1) {
					leftLabel.setIcon(new ImageIcon(resize(theRecipe.getPhotos()[0].getPhoto())));
					rightLabel.setIcon(new ImageIcon(resize(GlazeRecipe.NULL_IMAGE)));
				} else {
					leftLabel.setIcon(new ImageIcon(resize(theRecipe.getPhotos()[0].getPhoto())));
					rightLabel.setIcon(new ImageIcon(resize(theRecipe.getPhotos()[1].getPhoto())));
				}
			}
			private String parseAtmoshpere(GlazeRecipe recipe)
			{
				String atms = "";
				String[] firings = recipe.getFiringAttribute();
				
				if(firings.length > 2) {
					for(int k = 0; k < firings.length - 1; k++) { 
						atms += firings[k].trim() + ", "; 
					}
					atms += "& " + firings[firings.length - 1].trim();
				} else {
					if(firings.length == 1) {
						if(firings[0].trim().contains("Ox.")) {
							atms = "Oxidation" ;
						}
					} else {
						if(firings[0].contains("Ox.")) {
							atms = "Oxidation" ;
						} else if(firings[0].contains("Red.")) {
							atms = "Reduction";
						} else {
							atms = firings[0].trim();
						}
						
						if(firings[1].contains("Ox.")) {
							atms += " & Oxidation" ;
						} else if(firings[1].contains("Red.")) {
							atms += " & Reduction";
						} else {
							atms += " & " + firings[1].trim();
						}
					}
					
				}
				return atms;
			}
			private GlazeRecipe[] shuffle(GlazeRecipe[] original)
			{
				for(int k = 0; k < original.length; k++) {
					int randPos = (int)(Math.random()*original.length);
					GlazeRecipe temp = original[k];
					original[k] = original[randPos];
					original[randPos] = temp;
				}
				return original;
			}
			private GlazeRecipe[] uploadRecipes()
			{
				File directory = new File("Glaze Recipes/");
				ArrayList<GlazeRecipe> allRecipes = new ArrayList<GlazeRecipe>();

				File[] fList = directory.listFiles();
			    if(fList != null) {
			        for (File file : fList) {      
			            if (file.isDirectory()) { allRecipes.add(new GlazeRecipe(file.getAbsolutePath())); }
			        }
			    }
				GlazeRecipe[] recipeAry = new GlazeRecipe[allRecipes.size()];
				recipeAry = allRecipes.toArray(recipeAry);
			  
				return recipeAry;
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) { 
				timer.stop();
				System.out.println("Opening an Edit Panel ... ");
				openEditPanel(allRecipes[imagePos]);
				timer.start();
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		}
	}
}
