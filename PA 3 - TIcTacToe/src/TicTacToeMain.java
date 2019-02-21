import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeMain extends JFrame
{
	GamePanel gamePanel;
	PlayerPanel player1 = new PlayerPanel("Player 1 (X):");
	PlayerPanel player2 = new PlayerPanel("Player 2 (O):");
	MenuPanel menu = new MenuPanel();
	BoardPanel board = new BoardPanel();

	boolean gameInProgress;
	boolean isPlayer1Turn;

	public static void main(String[] args)
	{
		new TicTacToeMain();
	}

	public TicTacToeMain()
	{
		setTitle("Tic Tac Toe");
		setSize(500,500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new GamePanel(player1, player2, menu, board);
		add(gamePanel);
		
		revalidate();
		repaint();
		setVisible(true);
	}

	public class GamePanel extends JPanel
	{
		public JPanel players;

		public JPanel player1Panel;
		public JPanel player2Panel;
		public JPanel menuPanel;
		public JPanel boardPanel;

		public GamePanel(JPanel player1Panel, JPanel player2Panel, JPanel menuPanel, JPanel boardPanel)
		{
			this.player1Panel = player1Panel;
			this.player2Panel = player2Panel;
			this.menuPanel = menuPanel;
			this.boardPanel = boardPanel;

			players = new JPanel();
			players.setLayout(new FlowLayout(FlowLayout.CENTER));
			players.add(player1Panel);
			players.add(player2Panel);

			setLayout(new BorderLayout());
			add(players, BorderLayout.NORTH);
			add(boardPanel, BorderLayout.CENTER);
			add(menuPanel, BorderLayout.SOUTH);
		}
	}

	public class MenuPanel extends JPanel
	{
		private JPanel buttonPanel;
		private JPanel labelPanel;

		public JButton newGameButton;
		public JButton resetButton;
		public JButton exitButton;
		public JLabel statusLabel;


		MenuPanel()
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

		public void setStatus(String message) { statusLabel.setText(message); repaint(); }
	}

	public class PlayerPanel extends JPanel
	{
		private JTextField nameField;
		private JLabel nameTitleLabel;
		private JLabel winsTitleLabel;
		private JLabel lossesTitleLabel;

		private JLabel winsLabel;
		private JLabel lossesLabel;

		private String name;
		private int wins;
		private int losses;
		private String playerID;

		PlayerPanel(String playerNumberString)
		{
			nameTitleLabel = new JLabel("Name:");
			winsTitleLabel = new JLabel("Wins:");
			lossesTitleLabel = new JLabel("Losses:");

			winsLabel = new JLabel("0");
			lossesLabel = new JLabel("0");

			playerID = playerNumberString.substring(0,playerNumberString.length() - 5);
			name = playerID;

			nameField = new JTextField(8);
			nameField.setText(playerID);
			nameField.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					name = nameField.getText();
					if(name.length() < 1) { name = playerID; }
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

		public String getName()
		{
			if(name != null)
				return name;
			return playerID;
		}
		public int getWins() { return wins; }
		public int getLosses() { return losses; }
		public void addWin() { wins++; winsLabel.setText(""+wins); }
		public void addLoss() { losses++; lossesLabel.setText(""+losses); }
		public void reset() { name = playerID; wins = 0; losses = 0; winsLabel.setText(""+wins); lossesLabel.setText(""+losses);}
		public void enableEditing() { nameField.setEnabled(true); }
		public void disableEditing() { nameField.setEnabled(false); }
	}

	public class BoardPanel extends JPanel
	{
		public JButton[][] buttonGrid;
		
		BoardPanel()
		{
			fillButtonGrid();
			setLayout(new GridLayout(3,3));
			fillPanel();
			setEditable(false);
		}

		public boolean gameIsWon()
		{
			//Checking across rows
			boolean player1RowFilled = true;
			boolean player2RowFilled = true;
			
			for(int row = 0; row < 3; row++)
			{
				for(int col = 0; col < 3; col++)
				{
					if(buttonGrid[row][col].getText().equals("x"))
					{
						player2RowFilled = false;
					} else if (buttonGrid[row][col].getText().equals("o")) {
						player1RowFilled = false;
					} else {
						player1RowFilled = false;
						player2RowFilled = false;
					}
				}
				if(player1RowFilled || player2RowFilled) {
					return true;
				} else {
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
					if(buttonGrid[row][col].getText().equals("x"))
					{
						player2ColFilled = false;
					} else if (buttonGrid[row][col].getText().equals("o")) {
						player1ColFilled = false;
					} else {
						player1ColFilled = false;
						player2ColFilled = false;
					}
				}
				if(player1ColFilled || player2ColFilled) {
					return true;
				} else {
					player1ColFilled = true;
					player2ColFilled = true;
				}
			}
			
			//Checking the negative diagonal
			boolean player1NegFilled = true;
			boolean player2NegFilled = true;
			
			for(int row = 0; row < 3; row++)
			{
				if(buttonGrid[row][row].getText().equals("x"))
				{
					player2NegFilled = false;
				} else if (buttonGrid[row][row].getText().equals("o")) {
					player1NegFilled = false;
				} else {
					player1NegFilled = false;
					player2NegFilled = false;
				}
			}
			if(player1NegFilled || player2NegFilled)
				return true;
			
			//Checking the negative diagonal
			boolean player1PosFilled = true;
			boolean player2PosFilled = true;
			
			for(int row = 2; row >= 0; row--)
			{
				if(buttonGrid[row][row].getText().equals("x"))
				{
					player2PosFilled = false;
				} else if (buttonGrid[row][row].getText().equals("o")) {
					player1PosFilled = false;
				} else {
					player1PosFilled = false;
					player2PosFilled = false;
				}
			}
			if(player1PosFilled || player2PosFilled)
				return true;
			
			return false;
		}
		public boolean isDraw()
		{
			boolean allSelected = true;
			for(int row  = 0; row < 3; row++)
			{
				for(int col = 0; col < 3; col++) 
				{
					if(!buttonGrid[row][col].getText().equals("x") && !buttonGrid[row][col].getText().equals("o"))
					{
						allSelected = false;
					}
				}
			}
			return allSelected;
		}
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
		private void fillButtonGrid()
		{
			buttonGrid = new JButton[3][3];
			for(int row = 0; row < 3; row++)
			{
				for(int col = 0; col < 3; col++)
				{
					buttonGrid[row][col] = new JButton("");
					buttonGrid[row][col].addActionListener(new BoardButtonListener());
				}
			}
		}
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
		public void resetButtons()
		{
			for(int row = 0; row < 3; row++)
			{
				for(int col = 0; col < 3; col++)
				{
					buttonGrid[row][col].setText(" ");
				}
			}
		}
	}

	public class BoardButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String actionCommand = e.getActionCommand();

			if(actionCommand.equals(" ")) // make sure it is a valid spot
			{
				for(int row = 0; row < 3; row++)
				{
					for(int col = 0; col < 3; col++)
					{
						if(e.getSource() == board.buttonGrid[row][col]) // get the button that was pressed
						{
							if(isPlayer1Turn)
							{
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
							}
							else
							{
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
				}
			}
		}
	}

	public class MenuButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == menu.exitButton) { // Exit Game
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the game?", "Warning - You are about to exit out of the game!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
				{
					System.exit(0);
				}
			} else if (e.getSource() == menu.resetButton) { // Reset Game
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
			} else if (e.getSource() == menu.newGameButton) { // New Game
				board.resetButtons();
				board.setEditable(true);
				player1.disableEditing();
				player2.disableEditing();
				
				isPlayer1Turn = true;
				menu.setStatus(player1.getName() + "'s turn.");
			}
		}
	}
}
