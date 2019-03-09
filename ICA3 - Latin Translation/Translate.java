import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Translate extends JFrame{

	private JPanel mainPanel;
	private JPanel buttonPanel;
	
	private JLabel englishLabel;
	
	private JButton sinisterButton;
	private JButton dexterButton;
	private JButton mediumButton;
	
	public static void main(String[] args)
	{
		new Translate();
	}
	
	public Translate()
	{
		//Set the JFrame Parameters
		setTitle("Latin to English Converter");
		setSize(400,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Declare the three buttons
		sinisterButton = new JButton("SINISTER");
		dexterButton = new JButton("DEXTER");
		mediumButton = new JButton("MEDIUM");

		//Add action listeners to the three buttons
		sinisterButton.addActionListener(new ButtonListener());
		dexterButton.addActionListener(new ButtonListener());
		mediumButton.addActionListener(new ButtonListener());
		
		//Define and center the JLabel
		englishLabel = new JLabel("Click a Button to Translate");
		englishLabel.setHorizontalAlignment(JLabel.CENTER);
		englishLabel.setVerticalAlignment(JLabel.CENTER);
		
		//Create a panel to hold the buttons (Using Flow Layout)
		buttonPanel = new JPanel();
		
		//Add the buttons to the Button JPanel
		buttonPanel.add(sinisterButton, BorderLayout.SOUTH);
		buttonPanel.add(dexterButton, BorderLayout.SOUTH);
		buttonPanel.add(mediumButton, BorderLayout.SOUTH);
		
		//Define a JPanel to hold both the message and buttons
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		//Add the label and the button panel to the main panel
		mainPanel.add(englishLabel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		//Add the main panel to the JFrame
		add(mainPanel);
		
		setVisible(true);
		
	}
	
	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == sinisterButton) {
				englishLabel.setText("LEFT");
			} else if(e.getSource() == dexterButton) {
				englishLabel.setText("RIGHT");
			} else if(e.getSource() == mediumButton) {
				englishLabel.setText("CENTER");
			}
		}
	}
	
}
