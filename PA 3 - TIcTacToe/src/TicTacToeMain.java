/**
 * Runs the Tic Tac Toe game, using JButtons to interface with the user.
 * Requires two players, who alternate turns to play.
 *
 * @Authors: Scott Campbell & Arron Cushing
 * @Version: 24 February 2019 - Assignment 3
 * @file: TicTacToeMain.java
 */

import javax.swing.JFrame;

public class TicTacToeMain extends JFrame
{
	GamePanel gamePanel;
	PlayerPanel player1;
	PlayerPanel player2;
	MenuPanel menu;
	BoardPanel board;

	public static void main(String[] args) { new TicTacToeMain(); }
	
	//Runs the tic tac toe game
	//@pre none
	//@post game is run, stops when user decides
	//@param none
	public TicTacToeMain()
	{
		player1 = new PlayerPanel("Player 1 (X):"); //set up the player panels
		player2 = new PlayerPanel("Player 2 (O):");
		menu = new MenuPanel(); //set up the menu (new game/reset/exit)
		board = new BoardPanel(); //set up the board
		gamePanel = new GamePanel(player1, player2, menu, board); //put all the panels in one panel that can interface with all the panels
		menu.setMasterPanel(gamePanel); // allow the game panel to work with it
		board.setMasterPanel(gamePanel);
		
		setTitle("Tic Tac Toe");
		setSize(500,500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(gamePanel); 
		
		revalidate();
		repaint();
		setVisible(true);
	}
}
