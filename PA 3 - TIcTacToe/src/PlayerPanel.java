import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PlayerPanel extends JPanel
{
	private JTextField nameField;
	private JLabel nameTitleLabel;
	private JLabel winsTitleLabel;
	private JLabel lossesTitleLabel;
	private JLabel winsLabel;
	private JLabel lossesLabel;

	private String playerNumberString;
	private String name;
	private int wins;
	private int losses;
	private String playerID;

	PlayerPanel(String playerNumberString)
	{
		this.playerNumberString = playerNumberString;
		createPanel();
	}

	private void createPanel()
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
	public void reset() { name = playerID; wins = 0; losses = 0; winsLabel.setText(""+wins); lossesLabel.setText(""+losses); }
	public void enableEditing() { nameField.setEnabled(true); }
	public void disableEditing() { nameField.setEnabled(false); }
}