/**
 * Sets up the board of buttons whenever the game is reset
 * Also checks the board when the game is in progress for whose turn it is and if someone has won
 *
 * @Authors: Scott Campbell & Arron Cushing
 * @Version: 24 February 2019 - Assignment 3
 * @file: BoardPanel.java
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardPanel extends JPanel
{
	private GamePanel masterPanel;
	
	public JButton[][] buttonGrid;
	public char[][] logicGrid;
		
	//Sets up the board panel with a 3-by-3 grid of buttons
	//@pre none
	//@post board is set up and initialized to hold nothing in its buttons
	//		also clears the board if there is anything in it beforehand
	//@param none
	public BoardPanel()
	{		
		fillButtonGrid();
		setLayout(new GridLayout(3,3));
		fillPanel();
		setEditable(false);
	}

	//Sets a button to display the appropriate image when it is selected
	//@pre button object exists and is empty
	//@post if previously empty, the appropriate image (based on player turn) is displayed in the button
	//		and the logic grid used for checking for a winner is updated
	//		if there was something in the button, nothing
	//@param row - the selected button's row
	//		 col - the selected button's column
	//		 c - character representing the current player (x if player 1, o if player 2)
	public void setButton(int row, int col, char c)
	{
		if(c == 'x') {
			buttonGrid[row][col].setIcon(new ImageIcon("x_icon.png"));
			logicGrid[row][col] = 'x';
		} else { //if c = o
			buttonGrid[row][col].setIcon(new ImageIcon("o_icon.png"));
			logicGrid[row][col] = 'o';
		}
	}
	//Sets the master panel of boardPanel
	//@pre object of type gamePanel exists
	//@post the game panel where boardPanel will be displayed, denoted masterPanel, is set 
	//@param masterPanel - object gamePanel, where the main interface is held
	public void setMasterPanel(GamePanel masterPanel) { this.masterPanel = masterPanel; }
	
	//Checks if the game has been won
	//@pre game is currently in progress
	//@post none
	//@param none
	//@return true if a player has successfully made 3 of their symbol in a line, false otherwise
	public boolean gameIsWon()
	{
		//Checking across rows
		boolean player1RowFilled = true;
		boolean player2RowFilled = true;
		
		for(int row = 0; row < 3; row++)
		{
			for(int col = 0; col < 3; col++)
			{
				if(logicGrid[row][col] == 'x')
				{
					player2RowFilled = false;
				} else if (logicGrid[row][col] == 'o') {
					player1RowFilled = false;
				} else { //the square is empty
					player1RowFilled = false;
					player2RowFilled = false;
				}
			}
			if(player1RowFilled || player2RowFilled) {
				return true;
			} else { //reset the boolean values to their original starting values
				player1RowFilled = true;
				player2RowFilled = true;
			}
		}
		
		//Checking the columns
		boolean player1ColFilled = true;
		boolean player2ColFilled = true;
		
		for(int col = 0; col < 3; col++)
		{
			for(int row = 0; row < 3; row++)
			{
				if(logicGrid[row][col] == 'x')
				{
					player2ColFilled = false;
				} else if (logicGrid[row][col] == 'o') {
					player1ColFilled = false;
				} else { //if space is empty
					player1ColFilled = false;
					player2ColFilled = false;
				}
			}
			if(player1ColFilled || player2ColFilled) {
				return true;
			} else { //reset the boolean values to their original starting values
				player1ColFilled = true;
				player2ColFilled = true;
			}
		}
		
		//Checking the negative diagonal (top left to bottom right)
		boolean player1NegFilled = true;
		boolean player2NegFilled = true;
		
		for(int row = 0; row < 3; row++)
		{
			if(logicGrid[row][row] == 'x')
			{
				player2NegFilled = false;
			} else if (logicGrid[row][row] == 'o') {
				player1NegFilled = false;
			} else { //the square is empty
				player1NegFilled = false;
				player2NegFilled = false;
			}
		}
		if(player1NegFilled || player2NegFilled)
			return true;
		
<<<<<<< HEAD
		//Checking the positive diagonal
=======
		//Checking the negative diagonal (top right to bottom left)
>>>>>>> 82c9288b28abe90ccca45d36387601b74c1f4dde
		boolean player1PosFilled = true;
		boolean player2PosFilled = true;
		
		int col = 0;
		for(int row = 2; row >= 0; row--)
		{
			if(logicGrid[row][col] == 'x')
			{
				player2PosFilled = false;
			} else if (logicGrid[row][col] == 'o') {
				player1PosFilled = false;
			} else { //if empty
				player1PosFilled = false;
				player2PosFilled = false;
			}
			col++;
		}
		if(player1PosFilled || player2PosFilled)
			return true;
		
		return false; //if no matches are found
	}
	//Checks if all possible squares are filled with no wins
	//@pre game is currently in progress
	//@post none
	//@param none
	//@return true if no one has won and there are no more available moves, false otherwise
	public boolean isDraw()
	{
		boolean allSelected = true;
		for(int row  = 0; row < 3; row++)
		{
			for(int col = 0; col < 3; col++) 
			{
				if(logicGrid[row][col] != 'x' && logicGrid[row][col] != 'o')
				{
					allSelected = false;
				}
			}
		}
		return allSelected;
	}
	//Fills the board panel with a 3x3 grid to hold buttons
	//@pre none
	//@post board panel now contains a grid to hold buttons
	//@param none
	private void fillPanel()
	{
		for(int row = 0; row < 3; row++)
		{
			for(int col = 0; col < 3; col++)
			{
				add(buttonGrid[row][col]);
			}
		}
	}
	//Fills in the button grid with empty buttons
	//@pre button grid is initialized, is 3x3
	//@post button grid is filled with empty buttons
	//@param none
	private void fillButtonGrid()
	{
		buttonGrid = new JButton[3][3];
		logicGrid = new char[3][3];
		for(int row = 0; row < 3; row++)
		{
			for(int col = 0; col < 3; col++)
			{
				buttonGrid[row][col] = new JButton("");
				buttonGrid[row][col].addActionListener(new BoardButtonListener()); //implement buttons
				logicGrid[row][col] = ' '; //parallel array used to check if a player has won
			}
		}
	}
	//Allows or prevents the board (and the buttons inside the board) to be edited
	//@pre buttons have been placed inside the board for the user to interact with
	//@post users can interact with the buttons to play the game
	//@param isEditable - boolean value, allows the board to be edited if true, disables editing if false
	public void setEditable(boolean isEditable)
	{
		for(int row = 0; row < 3; row++)
		{
			for(int col = 0; col < 3; col++)
			{
				buttonGrid[row][col].setEnabled(isEditable);
			}
		}
	}
	//Resets the buttons and the logic grid to their starting values (i.e. empty)
	//@pre buttons exist to be reset, buttons are placed in a 3x3 grid, logic grid exists
	//@post buttons now have no icon or text, logic grid is cleared
	//@param none
	public void resetButtons()
	{
		for(int row = 0; row < 3; row++)
		{
			for(int col = 0; col < 3; col++)
			{
<<<<<<< HEAD
=======
				buttonGrid[row][col].setText(" ");
>>>>>>> 82c9288b28abe90ccca45d36387601b74c1f4dde
				buttonGrid[row][col].setIcon(null);
				logicGrid[row][col] = ' ';
			}
		}
	}
	
	
	public class BoardButtonListener implements ActionListener
	{
		//Implements the buttons so that the user interactions are handled properly
		//@pre user has pressed a button
		//@post the appropriate action for the button (depending on whose turn it is) is taken
		//@param e - action of pressing the button
		public void actionPerformed(ActionEvent e)
		{
			for(int row = 0; row < 3; row++) {
				for(int col = 0; col < 3; col++) {
					if(e.getSource() == buttonGrid[row][col] && logicGrid[row][col] == ' ') { // Get the button that was pressed
						masterPanel.runGame(row, col);
					}
				}
			}
		}
	}
}