/**
 * Sets up the game panel to hold the player panels, board, and menu panels
 * Also runs the game and checks if moves made are valid and/or result in a win
 *
 * @Authors: Scott Campbell & Arron Cushing
 * @Version: 24 February 2019 - Assignment 3
 * @file: GamePanel.java
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel
{
		public JPanel players;

		public PlayerPanel player1;
		public PlayerPanel player2;
		public MenuPanel menu;
		public BoardPanel board;
		
		boolean isPlayer1Turn;

		//Sets up the game panel with the player panels, the board, the menu, and a status message
		//@pre player panels, menu panel, and board panels have been made
		//@post game panel is set up, all values are initialized to defaults
		//@param player1 - panel for Player 1 (X), containing the player's name and the number of wins and losses the player has
		//		 player2 - panel for Player 2 (O), containing the player's name and the number of wins and losses the player has
		//		 menu - panel holding the menu, containing a button for New Game, for Reset, and for Exit
		//		 board - panel containing a 3x3 grid of buttons to play the game on
		public GamePanel(PlayerPanel player1, PlayerPanel player2, MenuPanel menu, BoardPanel board)
		{
			this.player1 = player1;
			this.player2 = player2;
			this.menu = menu;
			this.board = board;
			
			createPanel();
		}
		
		//Creates the game panel and sets the layout for the panel
		//@pre none
		//@post game panel is set to a layout
		//@param none
		private void createPanel()
		{
			players = new JPanel();
			players.setLayout(new FlowLayout(FlowLayout.CENTER));
			players.add(player1);
			players.add(player2);

			setLayout(new BorderLayout());
			add(players, BorderLayout.NORTH);
			add(board, BorderLayout.CENTER);
			add(menu, BorderLayout.SOUTH);
		}
		
		//Confirms if the user wants to quit the game, then exits if user confirms
		//@pre none
		//@post game exits if user confirms, else nothing
		//@param none
		public void exitGame()
		{
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the game?", "Warning - You are about to exit out of the game!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
			{
				System.exit(0);
			}
		}
		//Resets the game - sets all stats to 0, resets buttons, and reverts to default state
		//@pre none
		//@post game is fully reset to starting status
		//@param none
		public void resetGame()
		{
			if (JOptionPane.showConfirmDialog(null, "This will end the game and set the win/loss stats to 0. Are you sure?", "Warning - You are about to reset the game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
			{
				player1.reset();
				player2.reset();
				player1.enableEditing();
				player2.enableEditing();
				board.resetButtons();
				board.setEditable(false);
				menu.setStatus("Welcome to Tic Tac Toe!");
			}
		}
		
		//Makes a new game without resetting statistics
		//@pre none
		//@post board is cleared and ready for a new game
		//@param none
		public void newGame()
		{
			board.resetButtons();
			board.setEditable(true);
			player1.disableEditing();
			player2.disableEditing();
			
			isPlayer1Turn = true;
			menu.setStatus(player1.getName() + "'s turn.");
		}
		
		//Runs the tic tac toe game
		//@pre game is in progress
		//@post places the player's character on that space, and switches turn over to other player
		//@param row - row of button that has been selected
		//       col - column of button that has been selected
		public void runGame(int row, int col)
		{
			if(isPlayer1Turn) {
				board.setButton(row, col, 'x');
				//Check if a win
				if(board.gameIsWon())
				{
					menu.setStatus(player1.getName() + " has won the game!!!");
					//Update scoreboards and disable buttons
					board.setEditable(false);
					player1.addWin();
					player2.addLoss();
				} else if(board.isDraw()) {
					menu.setStatus("Draw!");
					board.setEditable(false);
				} else {
					//switch the state to be player 2's turn
					menu.setStatus(player2.getName() + "'s turn.");
					isPlayer1Turn = false;
				}
			} else {
				board.setButton(row, col, 'o');
				//Check if a win
				if(board.gameIsWon())
				{
					menu.setStatus(player2.getName() + " has won the game!!!");
					//Update scoreboards and disable buttons
					board.setEditable(false);
					player2.addWin();
					player1.addLoss();
				} else if(board.isDraw()) {
					menu.setStatus("Draw!");
					board.setEditable(false);
				} else {
					//switch the state to be player 2's turn
					menu.setStatus(player1.getName() + "'s turn.");
					isPlayer1Turn = true;
				}
			}
		}
	}