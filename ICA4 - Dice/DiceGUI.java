
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceGUI extends JFrame{

	private Random rand;
	private int di1Number;
	private int di2Number;
	
	JPanel masterPanel;
	JPanel dicePanel;
	JLabel di1Label;
	JLabel di2Label;
	JButton rollButton;
	
	public static void main(String[] args)
	{
		new DiceGUI();
	}
	
	public DiceGUI()
	{
		rand = new Random();
		
		setTitle("Dice Simulator");
		setSize(300,300);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		di1Label = new JLabel();
		di2Label = new JLabel();
		
		dicePanel = new JPanel();
		dicePanel.setLayout(new GridLayout(1,2));
		dicePanel.add(di1Label);
		dicePanel.add(di1Label);
		
		rollButton = new JButton();
		rollButton = new JButton("Roll the Dice");
		rollButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				updateGUI();
			}
		});
		
		masterPanel = new JPanel();
		masterPanel.setLayout(new BorderLayout());
		masterPanel.add(dicePanel, BorderLayout.CENTER);
		masterPanel.add(rollButton, BorderLayout.SOUTH);
		
		updateGUI();
		
		add(masterPanel);
		pack();
		setVisible(true);
	}
	
	public void updateGUI()
	{
		di1Number = getRandRoll();
		di2Number = getRandRoll();
		
		String di1Path = "Dice/Die"+di1Number+".png";
		String di2Path = "Dice/Die"+di2Number+".png";
		
		di1Label.setIcon(new ImageIcon(di1Path));
		di2Label.setIcon(new ImageIcon(di2Path));
		
		dicePanel.removeAll();
		dicePanel.add(di1Label);
		dicePanel.add(di2Label);
		
		dicePanel.validate();
		dicePanel.repaint();
	}
	
	public int getRandRoll()
	{
		return (int)(rand.nextDouble() * 6) + 1;
	}
}
