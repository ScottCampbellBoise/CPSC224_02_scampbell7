import javax.swing.*;

public class GlazeGUI extends JFrame
{
	/**
	 * Master TO DO:
	 * 
	 * Implement an SQL database to store all the glazes and search by attributes
	 * 
	 * Make a Panel to display/add Glaze Layering Tests - IF TIME!!!
	 * 		Allow user to choose which glazes they want to see layered
	 * 		Allow print/export image file
	 * 
	 * GlazeEditPanel ...
	 * 		Check if the user has editing privlages before allowing them to make changes - IF TIME!!!
	 * 		Add a button to finilize changes
	 * 				Check if the components add up to 100. If not, offer a button to scale to 100
	 * 		Add a button to make a duplicate of the glaze
	 * 		Add a print preview button
	 * 		Add a export option
	 * 
	 * Update GlazeRecipePanel
	 * 		change fonts to be more appealing
	 * 		
	 * add print / export capability to GlazeRecipePanel
	 * 
	 * Make a 'main page' 
	 * 		has a sign in option that allows the user to edit the recipes - IF TIME!!!
	 * 		has a search bar to find a glaze
	 * 		has a export button to open a Panel with different export options
	 * 			select glazes, organization, etc...
	 */
	
	GlazeSearchPanel glazeSearchPanel;
	GlazeRecipePanel testPanel;
	GlazeRecipePanel testPanel2;
	GlazeEditPanel editPanel;
	GlazeEditPanel editPanel2;
	
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
			
		glazeSearchPanel = new GlazeSearchPanel();
		//testPanel = new GlazeRecipePanel(new GlazeRecipe("/Users/ScottCampbell/Documents/workspace/GlazeCatalog/glaze1.txt"));
		testPanel2 = new GlazeRecipePanel(new GlazeRecipe("/Users/ScottCampbell/Documents/workspace/GlazeCatalog/Glaze Recipes/Spearmint.txt"));
		editPanel = new GlazeEditPanel(new GlazeRecipe("/Users/ScottCampbell/Documents/workspace/GlazeCatalog/glaze1.txt"));
		editPanel2 = new GlazeEditPanel();

		//add(glazeSearchPanel);
		add(editPanel);
		//add(testPanel2);
		
		pack();
		setVisible(true);
	}
}