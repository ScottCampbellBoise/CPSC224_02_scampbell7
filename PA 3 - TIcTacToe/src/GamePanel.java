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

		public GamePanel(PlayerPanel player1, PlayerPanel player2, MenuPanel menu, BoardPanel board)
		{
			this.player1 = player1;
			this.player2 = player2;
			this.menu = menu;
			this.board = board;
			
			createPanel();
		}
		
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
		public void exitGame()
		{
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the game?", "Warning - You are about to exit out of the game!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
			{
				System.exit(0);
			}
		}
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
		public void newGame()
		{
			board.resetButtons();
			board.setEditable(true);
			player1.disableEditing();
			player2.disableEditing();
			
			isPlayer1Turn = true;
			menu.setStatus(player1.getName() + "'s turn.");
		}
		public void runGame(int row, int col)
		{
			if(isPlayer1Turn) {
				board.buttonGrid[row][col].setText("x");
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
				board.buttonGrid[row][col].setText("o");
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