import javax.swing.JFrame;
import javax.swing.JLabel;
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
		 * Add functionality to all Panel Classes
		 * Add all instances of Panel Classes to a main Panel
		 * Check implementation
		 */
		
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
		
		private JButton newGameButton;
		private JButton resetButton;
		private JButton exitButton;
		private JLabel statusLabel;
		
		MenuPanel()
		{
			newGameButton = new JButton("New Game");
			resetButton = new JButton("Reset");
			exitButton = new JButton("Exit");
			
			statusLabel = new JLabel("Welcome to Tic Tac Toe!");
			
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
		
		void setStatus(String message) { statusLabel.setText(message); }
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
		
		PlayerPanel(String playerNumberString)
		{
			nameTitleLabel = new JLabel("Name:");
			winsTitleLabel = new JLabel("Wins:");
			lossesTitleLabel = new JLabel("Losses:");
			
			winsLabel = new JLabel("0");
			lossesLabel = new JLabel("0");
			
			nameField = new JTextField(8);
			nameField.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					name = nameField.getText();
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
		
		public String getName() { return name; }
		public int getWins() { return wins; }
		public int getLosses() { return losses; }
		public void addWin() { wins++; winsLabel.setText(""+wins); }
		public void addLoss() { losses++; lossesLabel.setText(""+losses); }
	}
	
	public class BoardPanel extends JPanel
	{
		
		public JButton[][] buttonGrid;

		BoardPanel()
		{
			fillButtonGrid(); //Fills the array with the appropriate buttons
			setLayout(new GridLayout(3,3));
			fillPanel();
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
	}
	
	public class BoardButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}

	public class MenuButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == menu.exitButton) { // Exit Game
				
			} else if (e.getSource() == menu.resetButton) { // Reset Game
				
			} else if (e.getSource() == menu.newGameButton) { // New Game
				
			}
		}
	}
}
