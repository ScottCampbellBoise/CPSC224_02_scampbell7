import javax.swing.*;

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
	 * 				Check if the components add up to 100. If not, offer a button to scale to 100
	 * 
	 * Update GlazeRecipePanel
	 * 		change fonts to be more appealing
	 * 		update the image to be what would be outputed - create PDF file and display that?
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
			
		GlazeRecipe baldwinGreen = new GlazeRecipe("Glaze Recipes/Baldwin Green");
		
		glazeSearchPanel = new GlazeSearchPanel();
		editPanel = new GlazeEditPanel(baldwinGreen);
		editPanel2 = new GlazeEditPanel();
		editPanel3 = new GlazeEditPanel(new GlazeRecipe("Glaze Recipes/Baldwin Blue"));

		//add(glazeSearchPanel);
		add(editPanel3);
		//add(testPanel2);
		
		pack();
		setVisible(true);
	}
}
