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


	MenuPanel() { createPanel(); }
	
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
	public void setStatus(String message) { statusLabel.setText(message); repaint(); }
	public void setMasterPanel(GamePanel gamePanel) { this.masterPanel = gamePanel; }
	
	public class MenuButtonListener implements ActionListener
	{
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