/**
 * Sets up the panels for each player with a field to enter a name, and the number of wins and losses the player has
 *
 * @Authors: Scott Campbell & Arron Cushing
 * @Version: 24 February 2019 - Assignment 3
 * @file: PlayerPanel.java
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PlayerPanel extends JPanel
{
	private JTextField nameField;
	private JLabel nameTitleLabel;
	private JLabel winsTitleLabel;
	private JLabel lossesTitleLabel;
	private JLabel winsLabel;
	private JLabel lossesLabel;

	private String playerNumberString;
	private String name;
	private int wins;
	private int losses;
	private String playerID;

	//Sets the player panel up
	//@pre none
	//@post player panel for the corresponding player number is set up
	//@param playerNumberString - name of the panel to be set up	
	PlayerPanel(String playerNumberString)
	{
		this.playerNumberString = playerNumberString;
		createPanel();
	}

	//Creates the player panel
	//@pre none
	//@post player panel is set up, contains the name of the player and how many times the player has won or lost
	//@param none
	private void createPanel()
	{
		nameTitleLabel = new JLabel("Name:");
		winsTitleLabel = new JLabel("Wins:");
		lossesTitleLabel = new JLabel("Losses:");

		winsLabel = new JLabel("0");
		lossesLabel = new JLabel("0");

		playerID = playerNumberString.substring(0,playerNumberString.length() - 5); //removes the ending " (X):" or " (O):"
		name = playerID; //set by default to the player number (Player 1 for X, Player 2 for O)

		nameField = new JTextField(8);
		nameField.setText(playerID);
		nameField.addActionListener(new ActionListener(){
			
			//Implements the text field so that the user can edit their name
			//@pre none
			//@post name field contains player-entered name, or the default "Player #" if nothing
			//@param e- action of entering a name into the text box
			public void actionPerformed(ActionEvent e)
			{
				name = nameField.getText();
				if(name.length() < 1) { name = playerID; } //if user has entered nothing
				nameField.setText(name);
			}
		});

		setLayout(new GridLayout(3,2));

		TitledBorder border = new TitledBorder(playerNumberString);
		border.setTitleJustification(TitledBorder.LEFT);
	    border.setTitlePosition(TitledBorder.TOP);
	    setBorder(border);

		add(nameTitleLabel);
		add(nameField);
		add(winsTitleLabel);
		add(winsLabel);
		add(lossesTitleLabel);
		add(lossesLabel);
	}
	
	//Gets the name of the player
	//@pre none
	//@post none
	//@param none
	//@return player's name, or the player number if name does not exist
	public String getName()
	{
		if(name != null)
			return name;
		return playerID;
	}
	
	//Gets the number of wins
	//@pre none
	//@post none
	//@param none
	//@return number of wins
	public int getWins() { return wins; }
	
	//Gets the number of losses
	//@pre none
	//@post none
	//@param none
	//@return number of losses
	public int getLosses() { return losses; }
	
	//Adds a win to the player
	//@pre: player has won the game
	//@post: wins count is incremented and panel section for wins is updated
	//@param: none
	public void addWin() { wins++; winsLabel.setText(""+wins); }
	
	//Adds a loss to the player
	//@pre: other player has won the game
	//@post: losses count is incremented and panel section for losses is updated
	//@param: none
	public void addLoss() { losses++; lossesLabel.setText(""+losses); }
	
	//Resets the player panels to their default values
	//@pre: none
	//@post: player panel is reset, with name set to default, wins and losses set to 0
	//@param: none
	public void reset() { name = playerID; wins = 0; losses = 0; winsLabel.setText(""+wins); lossesLabel.setText(""+losses); }
	
	//Allows the user to edit the name field
	//@pre: none
	//@post: user can edit the name field and set their name
	//@param: none
	public void enableEditing() { nameField.setEnabled(true); }
	
	//Disables the ability of the user to edit the name field
	//@pre: none
	//@post: user cannot edit the name field
	//@param: none	
	public void disableEditing() { nameField.setEnabled(false); }
}