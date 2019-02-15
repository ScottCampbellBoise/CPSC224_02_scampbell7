import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeMain extends JFrame
{
	
	public static void main(String[] args)
	{
		new TicTacToeMain();
	}
	
	public TicTacToeMain()
	{
		setTitle("Tic Tac Toe");
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TO DO:
		/**
		 * Add functionality to all Panel Classes
		 * Add all instances of Panel Classes to a main Panel
		 * Check implementation
		 */
		
		setVisible(true);
		repaint();
	}
	
	/**
	 * This Panel Class holds the bottom row of Buttons as well as the 
	 * JLabel that indicates who's turn it is and other info
	 * 
	 * Should use the BorderLayout (North and South)
	 */
	public class MenuPanel extends JPanel
	{
		private JPanel buttonPanel;
		
		private JButton newGameButton;
		private JButton resetButton;
		private JButton exitButton;
		
		private JLabel statusLabel;
		
		MenuPanel()
		{
			//button panel should use the predefined flow layout
			//The class panel should use border layout (North and South)
		}
		
		void setStatus()
		{
			
		}
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
					nameField.setText(name); // Might not need this...
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
	
	/**
	 * This panel class holds the 3x3
	 * 
	 * Should use the GridLayout 
	 */
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

	public class PlayerActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}

	public class MenuButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}
}
