import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class GlazeSearchPanel extends JPanel
{
	final private int NUM_ATTRIBUTES = 15;
	private GlazeAttribute[] attributes = new GlazeAttribute[NUM_ATTRIBUTES];
		
	private JPanel attributesPanel;
	private AttributeSelectionPanel searchPanel;
	private JPanel resultsPanel;
	private JLabel infoLabel;
	
	public GlazeSearchPanel()
	{	
		setBorder(new EmptyBorder(30,30,30,30));
		
		resultsPanel = new JPanel();
		TitledBorder resultsBorder = new TitledBorder("Search Results");
		resultsBorder.setTitleJustification(TitledBorder.CENTER);
		resultsBorder.setTitlePosition(TitledBorder.TOP);
	    resultsPanel.setBorder(resultsBorder);
	    resultsPanel.add(new JButton("THIS BUTTON IS A PLACE HOLDER"));
		
		attributesPanel = new JPanel();
	    infoLabel = new JLabel("Click 'x' to remove from search");
	    infoLabel.setBorder(new EmptyBorder(5,5,15,5));
		updateAttributes();
		
		
		searchPanel = new AttributeSelectionPanel(this);
		TitledBorder searchBorder = new TitledBorder("Search Attributes");
		searchBorder.setTitleJustification(TitledBorder.CENTER);
		searchBorder.setTitlePosition(TitledBorder.TOP);
	    searchPanel.setBorder(searchBorder);
		
		setLayout(new BorderLayout());
		add(resultsPanel, BorderLayout.SOUTH);
		add(attributesPanel, BorderLayout.EAST);
		add(searchPanel, BorderLayout.CENTER);
	}
	
	public void addAttribute(String attributeName)
	{
		boolean isAlreadyAdded = false;
		
		for(int k = 0; k < NUM_ATTRIBUTES; k++)
		{
			if(attributes[k] != null && attributes[k].getName().equals(attributeName)) { isAlreadyAdded = true; }
		}
		
		if(!isAlreadyAdded) {
			for(int k = 0; k < NUM_ATTRIBUTES; k++) {
				if(attributes[k] == null)  {
					attributes[k] = new GlazeAttribute(attributeName);
					break;
				}
			}
		}
		updateAttributes();
	}
	public void updateAttributes()
	{
		remove(attributesPanel);
		
		attributesPanel = new JPanel();
		TitledBorder border = new TitledBorder("Selected Glaze Attributes");
		border.setTitleJustification(TitledBorder.LEFT);
	    border.setTitlePosition(TitledBorder.TOP);
	    attributesPanel.setBorder(border);
	    attributesPanel.add(infoLabel);
		attributesPanel.setLayout(new GridLayout(13,4));
		
		for(int k = 0; k < NUM_ATTRIBUTES; k++) 
		{
			if( attributes[k] != null ) { attributesPanel.add(attributes[k]); }
		}
		
		add(attributesPanel, BorderLayout.EAST);
		validate();
		repaint();
	}

	public class GlazeAttribute extends JPanel
	{
		private JLabel label;
		private JButton button;
		private String attributeName;
		
		public GlazeAttribute(String attributeName)
		{
			setBorder(BorderFactory.createEtchedBorder());
			this.attributeName = attributeName;
			label = new JLabel(attributeName+"   ");
			button = new JButton("x");
			button.setPreferredSize(new Dimension(20,20));
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					//remove attribute from the array
					//update the Panel
					for(int k = 0; k < NUM_ATTRIBUTES; k++) {
						if(attributes[k].getName().equals(attributeName)) {
							for(int i = k; i < NUM_ATTRIBUTES - 1; i++) { attributes[i] = attributes[i+1]; }
							break;
						}
					}
					updateAttributes();
				}
			});
		
			setLayout(new BorderLayout());
			add(label, BorderLayout.CENTER);
			add(button, BorderLayout.EAST);
		}
		public String getName() {return attributeName;}
	}
}