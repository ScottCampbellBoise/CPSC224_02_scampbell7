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

	char boardStatus[][];
	boolean gameInProgress;
	boolean isPlayer1Turn;
	public JButton[][] buttonGrid;

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

		//TO DO:
		/**
		 * Arron:
		 *
		 * make a function to check if there is a win. Takes a 2D array as arg
		 *
		 * implement reset button - MenuButtonListener
		 * 		Confirmation Dialog Box
		 * 		reset stats, AND names, AND Buttons
		 *
		 *
		 * Scott:
		 *
		 * implement new game button - MenuButtonListener		DONE
		 * 		enable board, disable player text fields
		 *
		 * implement exit button - MenuButtonListener			DONE
		 * 		close the window
		 *
		 * CHECK IF PLAYER NAME IMP. IS OK
		 */

		setVisible(true);
	}

	public void startNewGame()
	{
		// Start a new game and update all information
		gameInProgress = true;
		boardStatus = new char[3][3];

		//Clear the board status array
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				boardStatus[row][col] = ' '; // Empty spot character
			}
		}

		/*
		while(gameInProgress)
		{
			//Player 1's Turn
			isPlayer1Turn = true;
			menu.setStatus(player1.getName() + "'s turn.");
			//Wait for a button to be selected

			//Player 2's turn
			isPlayer1Turn = false;
			menu.setStatus(player2.getName() + "'s turn.");
			//Wait for a button to be selected
		} */
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

		private JButton newGameButton;
		private JButton resetButton;
		private JButton exitButton;
		private JLabel statusLabel;


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
		public void reset() { name = playerID; wins = 0; losses = 0; }
		public void enableEditing() { nameField.setEnabled(true); }
		public void disableEditing() { nameField.setEnabled(false); }
	}

	public class BoardPanel extends JPanel
	{


		BoardPanel()
		{
			fillButtonGrid();
			setLayout(new GridLayout(3,3));
			fillPanel();
			setEditable(false);
		}

		public boolean isValidSpot(int row, int col)
		{
			return (boardStatus[row][col] == ' ');
		}
		public void setButtonCharacter(char c, int row, int col)
		{
			if(!isValidSpot(row, col))
			{
				if(isPlayer1Turn)
				{
					boardStatus[row][col] = 'x';
					isPlayer1Turn = false;
				}
				else
				{
					boardStatus[row][col] = 'o';
					isPlayer1Turn = true;
				}
			}
			else {
				System.out.println("not valid");
			}
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

			if(actionCommand.equals(" "))
			{
				for(int row = 0; row < 3; row++)
				{
					for(int col = 0; col < 3; col++)
					{
						if(e.getSource() == buttonGrid[row][col])
						{
							if(isPlayer1Turn)
								buttonGrid[row][col].setText("x");
							else
								buttonGrid[row][col].setText("o");
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
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the game?", "Warning - You are about to exit out of the game!",
				        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);

			} else if (e.getSource() == menu.resetButton) { // Reset Game


			} else if (e.getSource() == menu.newGameButton) { // New Game
				board.resetButtons();
				board.setEditable(true);
				player1.disableEditing();
				player2.disableEditing();
				repaint();
				startNewGame();
			}
		}
	}
	}
}
