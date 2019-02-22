import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardPanel extends JPanel
{
	private GamePanel masterPanel;
	
	public JButton[][] buttonGrid;
		
	public BoardPanel()
	{		
		fillButtonGrid();
		setLayout(new GridLayout(3,3));
		fillPanel();
		setEditable(false);
	}

	public void setMasterPanel(GamePanel masterPanel) { this.masterPanel = masterPanel; }
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
						if(e.getSource() == buttonGrid[row][col]) // get the button that was pressed
						{
							masterPanel.runGame(row, col);
						}
					}
				}
			}
		}
	}
}