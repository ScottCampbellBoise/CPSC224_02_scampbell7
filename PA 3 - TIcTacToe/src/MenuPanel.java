/**
 * Sets up the menu panel with buttons to choose actions that the player can do
 * Actions: New Game - start a new game
 * 			Reset - reset the game to default starting status
 * 			Exit - exits game
 *
 * @Authors: Scott Campbell & Arron Cushing
 * @Version: 24 February 2019 - Assignment 3
 * @file: MenuPanel.java
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel
{
	private GamePanel masterPanel;
	
	private JPanel buttonPanel;
	private JPanel labelPanel;

	public JButton newGameButton;
	public JButton resetButton;
	public JButton exitButton;
	public JLabel statusLabel;

	//Sets the menu panel up
	//@pre none
	//@post menu panel is set up
	//@param none
	MenuPanel() { createPanel(); }
	
	//Creates the panel and sets up the buttons that can be selected to control the game
	//@pre none
	//@post menu panel is set up with buttons for game setup
	//@param none
	private void createPanel()
	{
		newGameButton = new JButton("New Game");
		resetButton = new JButton("Reset");
		exitButton = new JButton("Exit");

		newGameButton.addActionListener(new MenuButtonListener());
		resetButton.addActionListener(new MenuButtonListener());
		exitButton.addActionListener(new MenuButtonListener());

		statusLabel = new JLabel("Welcome to Tic-Tac-Toe!");

		buttonPanel = new JPanel();
		buttonPanel.add(newGameButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(exitButton);

		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.setBorder(BorderFactory.createEtchedBorder());
		labelPanel.add(statusLabel);

		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.NORTH);
		add(labelPanel, BorderLayout.SOUTH);
	}
	
	//Sets the status of the game depending on whose turn it currently is if the game is in progress
	//Else sets status to default "Welcome to Tic Tac Toe!"
	//@pre none
	//@post status is set to the message passed in
	//@param message - message to write to the status label
	public void setStatus(String message) { statusLabel.setText(message); repaint(); }
	
	//Sets the master panel of menuPanel
	//@pre object of type gamePanel exists
	//@post the game panel where menuPanel will be displayed, denoted masterPanel, is set 
	//@param masterPanel - object gamePanel, where the main interface is held
	public void setMasterPanel(GamePanel gamePanel) { this.masterPanel = gamePanel; }
	
	public class MenuButtonListener implements ActionListener
	{
		//Implements the buttons so that the user interactions are handled properly
		//@pre user has pressed a button
		//@post the appropriate action for the button is taken
		//@param e - action of pressing the button
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == exitButton) { // Exit Game
				masterPanel.exitGame();
			} else if (e.getSource() == resetButton) { // Reset Game
				masterPanel.resetGame();
			} else if (e.getSource() == newGameButton) { // New Game
				masterPanel.newGame();
			}
		}
	}

}