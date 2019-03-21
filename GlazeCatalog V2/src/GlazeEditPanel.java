import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * TO DO:
 * 
 * IF A DIFFERENT RELIABILITY OR STABILITY IS CHOSEN, JSUT OVERWRITE THE OLD ONE!!!!
 * UPDATE THE DIFFERENT FIRING STYLES AS WELL
 * IF A NEW FIRING IS ADDED FROM THE ATTRIBUTES PANEL, UPDATE UP TOP, NOT BELOW!!!
 * 
 * Implement the updating of the recipe 
 * Add the save, duplicate, preview and export buttons
 */
public class GlazeEditPanel extends JPanel
{
	private GlazeRecipe recipe;
		
	String[] lowerConeArray = {"010-","09","08","07","06","05","04","03","02","01","1","2","3","4","5","6","7","8","9","10","11"};
	String[] upperConeArray = {"09","08","07","06","05","04","03","02","01","1","2","3","4","5","6","7","8","9","10","11","12+"};
	String[] firingArray = {"Ox.", "Red.", "Salt", "Soda", "Wood", "Other"};
	JComboBox<String> lowerConeComboBox, upperConeComboBox;
		
	private JPanel titlePanel;
	private JTextField nameField;
	private JPanel firingPanel;
	private JPanel firingContents;
	private JPanel coneSelectPanel;
	private JButton addFiringButton;
		
	private JPanel allCompPanel;
	private JPanel componentPanel;
	private JPanel[] components;
	private JPanel addPanel;
	private JPanel[] adds;
	private JButton addComponentButton;
	private JButton addAddButton;
	private EditablePhotoPanel imagePanel;
		
	private JPanel commentsPanel;
	private JTextArea commentsTextArea;
		
	private final int NUM_ATTRIBUTES = 24;
	private GlazeAttribute[] attributes;
	private JPanel attributesPanel;
	private JButton addAttributeButton;
	private AttributeSelectionPanel attributeSelectPanel;
		
	private final int MAX_FIRING_TYPES = 6;
	private FiringLabel[] firingLabels;
		
	public GlazeEditPanel() // new glaze constructor
	{
		this.recipe = new GlazeRecipe();
		createPanel();
		validate();
		repaint();
	}
	public GlazeEditPanel(GlazeRecipe recipe) { // Edit an existing recipe
		this.recipe = recipe;
		createPanel();
		validate();
		repaint();
	} 
	
	public void createPanel()
	{
		titlePanel = new JPanel();
		nameField = new JTextField("~ " + recipe.getName(),12);
		nameField.setFont(new Font(nameField.getName(), Font.PLAIN, 24));
		
		lowerConeComboBox = new JComboBox<String>(lowerConeArray);
		lowerConeComboBox.setSelectedItem(recipe.getLowerCone().trim());
		lowerConeComboBox.setFont(new Font(lowerConeComboBox.getName(), Font.PLAIN, 16));
		
		upperConeComboBox = new JComboBox<String>(upperConeArray);
		upperConeComboBox.setSelectedItem(recipe.getUpperCone().trim());
		upperConeComboBox.setFont(new Font(upperConeComboBox.getName(), Font.PLAIN, 16));
		
		addFiringButton = new JButton("Add");
		addFiringButton.setPreferredSize(new Dimension(70,25));
		addFiringButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> firingTypeComboBox = new JComboBox<String>(firingArray);
				firingTypeComboBox.setEditable(true);
				JOptionPane.showMessageDialog( null, firingTypeComboBox, "Select a Firing Type", JOptionPane.QUESTION_MESSAGE);
				String selectedFiringType = (String) firingTypeComboBox.getSelectedItem();
				if(selectedFiringType != null) 
				{
					addFiringLabel(selectedFiringType);
				}
			}
		});
		
		firingPanel = new JPanel();
		firingPanel.setBorder(BorderFactory.createTitledBorder("Firing Types"));
		firingContents = parseFiring();
		firingPanel.add(firingContents);
		
		coneSelectPanel = new JPanel();
		JLabel coneLabel = new JLabel("to");
		coneLabel.setHorizontalAlignment(JLabel.CENTER);
		coneSelectPanel.setLayout(new BorderLayout());
		coneSelectPanel.add(lowerConeComboBox, BorderLayout.WEST);
		coneSelectPanel.add(coneLabel, BorderLayout.CENTER);
		coneSelectPanel.add(upperConeComboBox, BorderLayout.EAST);
		coneSelectPanel.setBorder(BorderFactory.createTitledBorder("Cone Range:"));
		
		JPanel coneAndFiringPanel = new JPanel();
		coneAndFiringPanel.setLayout(new GridLayout(1,2));
		coneAndFiringPanel.add(coneSelectPanel);
		coneAndFiringPanel.add(firingPanel);
		
		titlePanel.setLayout(new BorderLayout());
		titlePanel.add(nameField, BorderLayout.WEST);
		titlePanel.add(coneAndFiringPanel, BorderLayout.EAST);
		
		addComponentButton = new JButton("Insert New Component");
		addComponentButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				JPanel optionPanel = new JPanel();
				JTextField compField = new JTextField(12);
				JTextField amtField = new JTextField(12);
				JLabel compLabel = new JLabel("Component");
				compLabel.setHorizontalAlignment(JLabel.RIGHT);
				JLabel amtLabel = new JLabel("Amount (%)");
				amtLabel.setHorizontalAlignment(JLabel.RIGHT);
				
				optionPanel.setLayout(new GridLayout(2,2));
				optionPanel.add(compLabel);
				optionPanel.add(compField);
				optionPanel.add(amtLabel);
				optionPanel.add(amtField);
				
				int result = JOptionPane.showConfirmDialog(null, optionPanel, 
			               "Please Enter the New Component and Amount", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			    	  try {
			    		  String compName = compField.getText();
			    		  double compAmount = Double.parseDouble(amtField.getText());
			    		  recipe.addComponent(new GlazeComponent(compName, compAmount));
			    		  updateComponentsPanel();
			    	  } catch (Exception ex) {
			    		  JOptionPane.showMessageDialog(null,"Please enter a valid number for the amount","Error!",JOptionPane.ERROR_MESSAGE);
			    	  }
			      }
			}
		});
		
		componentPanel = new JPanel();
		TitledBorder componentBorder = new TitledBorder("Components (%)");
		componentBorder.setTitleJustification(TitledBorder.LEFT);
		componentBorder.setTitlePosition(TitledBorder.TOP);
		componentPanel.setBorder(componentBorder);
		components = parseComponents(recipe.getComponents(), false);
		componentPanel.setLayout(new GridLayout(components.length + 1,1));
		for(int k = 0; k < components.length; k++) { componentPanel.add(components[k]); }
		componentPanel.add(addComponentButton);
		
		addAddButton = new JButton("Insert New Add");
		addAddButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				JPanel optionPanel = new JPanel();
				JTextField compField = new JTextField(12);
				JTextField amtField = new JTextField(12);
				JLabel compLabel = new JLabel("Add");
				compLabel.setHorizontalAlignment(JLabel.RIGHT);
				JLabel amtLabel = new JLabel("Amount (%)");
				amtLabel.setHorizontalAlignment(JLabel.RIGHT);
				
				optionPanel.setLayout(new GridLayout(2,2));
				optionPanel.add(compLabel);
				optionPanel.add(compField);
				optionPanel.add(amtLabel);
				optionPanel.add(amtField);
				
				int result = JOptionPane.showConfirmDialog(null, optionPanel, 
			               "Please Enter the New Add and Amount", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			    	  try {
			    		  String compName = compField.getText();
			    		  double compAmount = Double.parseDouble(amtField.getText());
			    		  recipe.addAdd(new GlazeComponent(compName, compAmount));
			    		  updateAddsPanel();
			    	  } catch (Exception ex) {
			    		  JOptionPane.showMessageDialog(null,"Please enter a valid number for the amount","Error!",JOptionPane.ERROR_MESSAGE);
			    		  ex.printStackTrace();
			    	  }
			      }
			}
		});
		
		addPanel = new JPanel();
		TitledBorder addBorder = new TitledBorder("Add (%)");
		addBorder.setTitleJustification(TitledBorder.LEFT);
		addBorder.setTitlePosition(TitledBorder.TOP);
		addPanel.setBorder(addBorder);
		adds = parseComponents(recipe.getAdds(), true);
		addPanel.setLayout(new GridLayout(adds.length + 1,1));
		for(int k = 0; k < adds.length; k++) { addPanel.add(adds[k]); }
		addPanel.add(addAddButton);
		
		JPanel ingredientsPanel = new JPanel();
		ingredientsPanel.setLayout(new BorderLayout());
		ingredientsPanel.add(componentPanel, BorderLayout.CENTER);
		ingredientsPanel.add(addPanel, BorderLayout.SOUTH);
		
		imagePanel = new EditablePhotoPanel(recipe.getPhotos());
		
		JPanel ingrAndImagePanel = new JPanel();
		ingrAndImagePanel.setLayout(new BorderLayout());
		ingrAndImagePanel.add(ingredientsPanel, BorderLayout.CENTER);
		ingrAndImagePanel.add(imagePanel, BorderLayout.EAST);
		
		commentsPanel = new JPanel();
		TitledBorder commentsBorder = new TitledBorder("Comments");
		commentsBorder.setTitleJustification(TitledBorder.LEFT);
		commentsBorder.setTitlePosition(TitledBorder.TOP);
		commentsPanel.setBorder(commentsBorder);
		commentsTextArea = new JTextArea(3,50);
		commentsTextArea.setText(recipe.getComments()[0]);
		commentsPanel.add(commentsTextArea);
		
	    attributesPanel = new JPanel();
		TitledBorder border = new TitledBorder("Glaze Attributes");
		border.setTitleJustification(TitledBorder.LEFT);
	    border.setTitlePosition(TitledBorder.TOP);
	    attributesPanel.setBorder(border);
	    addAttributeButton = new JButton("Add New Attribute");
	    addAttributeButton.setPreferredSize(new Dimension(50,20));
	    addAttributeButton.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		requestAttributes();
	    	}
	    });
	    parseAttributes();
	    updateAttributes();
	    
	    JPanel commentsAndAttributesPanel = new JPanel();
	    commentsAndAttributesPanel.setLayout(new BorderLayout());
	    commentsAndAttributesPanel.add(commentsPanel, BorderLayout.NORTH);
	    commentsAndAttributesPanel.add(attributesPanel, BorderLayout.SOUTH);
		
		allCompPanel = new JPanel();
		allCompPanel.setLayout(new BorderLayout());
		allCompPanel.add(titlePanel, BorderLayout.NORTH);
		allCompPanel.add(ingrAndImagePanel, BorderLayout.CENTER);
		
		JButton saveButton = new JButton("Save Glaze");
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				saveChanges();
			}
		});
		
		
		setLayout(new BorderLayout());
		add(saveButton, BorderLayout.NORTH);
		add(allCompPanel, BorderLayout.CENTER);
		add(commentsAndAttributesPanel, BorderLayout.SOUTH);
	}
	/**
	 * UPDATE TO BE MORE CAREFUL AND FOOLPROOF
	 */
	private void saveChanges()
	{
		//get all the qualities current values and update the recipe object
		String rawName = nameField.getText();
		if(rawName.substring(0,1).equals("~")) { rawName = rawName.substring(1,rawName.length()).trim();}
		recipe.setName(rawName); //Update glaze name
		// Components and adds are already updated to the recipe object
		recipe.setConeRange((String)lowerConeComboBox.getSelectedItem(),(String)upperConeComboBox.getSelectedItem());
		
		ArrayList<String> newColors = new ArrayList<String>();
		ArrayList<String> newFinishes = new ArrayList<String>();
		ArrayList<String> newFunctionality = new ArrayList<String>();
		ArrayList<String> newCombos = new ArrayList<String>();
		
		for(GlazeAttribute ga : attributes)
		{
			if(ga != null && ga.getName().contains("Color:")) { newColors.add(ga.getName().substring(6, ga.getName().length()).trim()); }
			else if(ga != null && ga.getName().contains("Finish:")) { newFinishes.add(ga.getName().substring(7, ga.getName().length()).trim()); }
			else if(ga != null && ga.getName().contains("Reliability:")) { recipe.setReliability(ga.getName().substring(12, ga.getName().length()).trim()); }
			else if(ga != null && ga.getName().contains("Functionality:")) { newFunctionality.add(ga.getName().substring(14, ga.getName().length()).trim()); }
			else if(ga != null && ga.getName().contains("Stability:")) { recipe.setStability(ga.getName().substring(10, ga.getName().length()).trim()); }
			else if(ga != null && ga.getName().contains("As Combo:")) { newCombos.add(ga.getName().substring(9, ga.getName().length()).trim()); }
		}
		
		String[] colorArr = new String[newColors.size()];
		colorArr = newColors.toArray(colorArr);
		recipe.setColor(colorArr);
		
		String[] finishesArr = new String[newFinishes.size()];
		finishesArr = newFinishes.toArray(finishesArr);
		recipe.setFinish(finishesArr);
		
		String[] funcArr = new String[newFunctionality.size()];
		funcArr = newFunctionality.toArray(funcArr);
		recipe.setFunctionality(funcArr);
		
		String[] comboArr = new String[newCombos.size()];
		comboArr = newCombos.toArray(comboArr);
		recipe.setCombination(comboArr);
		
		recipe.updateFile("Glaze Recipes/" + rawName + ".txt");
	}
	private void requestAttributes()
	{
		attributeSelectPanel = new AttributeSelectionPanel(this);
		JOptionPane.showMessageDialog(null, attributeSelectPanel, "Select Attributes", JOptionPane.INFORMATION_MESSAGE);
		
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
	private void parseAttributes()
	{
		attributes = new GlazeAttribute[NUM_ATTRIBUTES];
		String[] colorAttributes = recipe.getColorAttribute();
		String[] finishesAttributes = recipe.getFinishAttribute();
		String reliabilityAttributes = recipe.getReliabilityAttribute();
		String[] functionalityAttribute = recipe.getFunctionalityAttribute();
		String stabilityAttributes = recipe.getStabilityAttribute();
		String[] combinationAttributes = recipe.getCombinationAttribute();

		int count = 0;
		for(int k = 0; k < colorAttributes.length && count < NUM_ATTRIBUTES; k++) { attributes[count] = new GlazeAttribute("Color: " + colorAttributes[k].trim()); count++; }
		for(int k = 0; k < finishesAttributes.length && count < NUM_ATTRIBUTES; k++) { attributes[count] = new GlazeAttribute("Finish: " + finishesAttributes[k].trim()); count++; }
		for(int k = 0; k < functionalityAttribute.length && count < NUM_ATTRIBUTES; k++) { attributes[count] = new GlazeAttribute("Functionality: " + functionalityAttribute[k].trim()); count++; }
		for(int k = 0; k < combinationAttributes.length && count < NUM_ATTRIBUTES; k++) { attributes[count] = new GlazeAttribute("As Combo: " + combinationAttributes[k].trim()); count++; }
		if(count < NUM_ATTRIBUTES) { attributes[count] = new GlazeAttribute("Reliability: " + reliabilityAttributes); count++; }
		if(count < NUM_ATTRIBUTES) { attributes[count] = new GlazeAttribute("Stability: " + stabilityAttributes+""); count++; }
	} 
	private void updateAddsPanel()
	{
		addPanel.removeAll();
		addPanel.revalidate();
		addPanel.repaint();
		
		adds = parseComponents(recipe.getAdds(), true);
		addPanel.setLayout(new GridLayout(adds.length + 1,1));
		for(int k = 0; k < adds.length; k++) { addPanel.add(adds[k]); }
		addPanel.add(addAddButton);
		
		addPanel.revalidate();
		addPanel.repaint();
		super.revalidate();
		super.repaint();
	}
	private void updateComponentsPanel()
	{
		componentPanel.removeAll();
		componentPanel.revalidate();
		componentPanel.repaint();
		
		components = parseComponents(recipe.getComponents(), false);
		componentPanel.setLayout(new GridLayout(components.length  + 1,1));
		for(int k = 0; k < components.length; k++) { componentPanel.add(components[k]); }
		componentPanel.add(addComponentButton);
		
		componentPanel.revalidate();
		componentPanel.repaint();
		super.revalidate();
		super.repaint();
	}
	private JPanel[] parseComponents(GlazeComponent[] components, boolean isAdd)
	{
		if(components != null)
		{
			JPanel[] compPanels = new JPanel[components.length];
			
			for(int k = 0; k < components.length; k++)
			{
				compPanels[k] = new ComponentPanel(components[k].getName(), components[k].getAmount(), isAdd);
			}
			
			return compPanels;
		} else {
			return new JPanel[] {new JPanel()};		
		}
	}
	private JPanel parseFiring()
	{
		firingLabels = new FiringLabel[MAX_FIRING_TYPES];
		JPanel myPanel = new JPanel();
		String[] attributes = recipe.getFiringAttribute();
		
		for(int k = 0; k < attributes.length; k++)
		{
			FiringLabel myLabel = new FiringLabel(attributes[k]+"  ");
			myPanel.add(myLabel);
			firingLabels[k] = myLabel;
		}
		myPanel.add(addFiringButton);
		return myPanel;
	}
	private void updateFiringLabels()
	{
		firingPanel.remove(firingContents);
		firingContents = new JPanel();
	    
		firingPanel.setLayout(new GridLayout(1, MAX_FIRING_TYPES + 1));
		
		for(int k = 0; k < MAX_FIRING_TYPES; k++) 
		{
			if( firingLabels[k] != null ) { firingContents.add(firingLabels[k]); }
		}
		firingContents.add(addFiringButton);
		
		firingPanel.add(firingContents, BorderLayout.EAST);
		firingPanel.validate();
		firingPanel.repaint();
		super.validate();
		super.repaint();
	}
	private void addFiringLabel(String firingType)
	{
		boolean isAlreadyAdded = false;
		for(int k = 0; k < MAX_FIRING_TYPES; k++) { 
			if(firingLabels[k] != null && firingLabels[k].getName().trim().equals(firingType)) {isAlreadyAdded = true;} 
		}
		
		boolean isValidLabel = false;
		for(int k = 0; k < firingArray.length; k++) { 
			if(firingArray[k].equals(firingType)) {isValidLabel = true;} 
		}
		
		if(!isAlreadyAdded && isValidLabel) {
			for(int k = 0; k < MAX_FIRING_TYPES; k++) {
				if(firingLabels[k] == null)  {
					firingLabels[k] = new FiringLabel(firingType+"  ");
					break;
				}
			}
		}
		updateFiringLabels();
	}
	private void updateAttributes()
	{
		attributesPanel.removeAll();
		
		TitledBorder border = new TitledBorder("Glaze Attributes");
		border.setTitleJustification(TitledBorder.LEFT);
	    border.setTitlePosition(TitledBorder.TOP);
	    attributesPanel.setBorder(border);
	    attributesPanel.setLayout(new BorderLayout());
	    
		JPanel lowerPanel = new JPanel();
	    lowerPanel.setLayout(new GridLayout(3,8));
		
		for(int k = 0; k < NUM_ATTRIBUTES; k++) 
		{
			if( attributes[k] != null ) { lowerPanel.add(attributes[k]); }
		}
		
		attributesPanel.add(addAttributeButton, BorderLayout.SOUTH);
		attributesPanel.add(lowerPanel, BorderLayout.CENTER);
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
			button = new JButton();
			button.setIcon(new ImageIcon("exit_icon.png"));
			button.setPreferredSize(new Dimension(20,20));
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					for(int k = 0; k < NUM_ATTRIBUTES; k++) {
						if(attributes[k] != null && attributes[k].getName().equals(attributeName)) {
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
	private class ComponentPanel extends JPanel
	{
		JTextField componentField;
		JTextField addField;
		JButton removeButton;
		
		String name;
		double amount;
		boolean isAdd;
		
		public ComponentPanel(String name, double amount, boolean isAdd)
		{
			this.name = name;
			this.amount = amount;
			this.isAdd = isAdd;
			
			componentField = new JTextField(name, 25);
			//componentField.setEditable();
			//Add Action Listener
			
			addField = new JTextField("" + amount, 8);
			//addField.setEditable(hasEditPrivlages);
			//Add Action Listener
			
			removeButton = new JButton();
			removeButton.setIcon(new ImageIcon("exit_icon.png"));
			removeButton.setPreferredSize(new Dimension(20,20));
			removeButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					if(!isAdd) { 
						recipe.removeComponent(name);
						updateComponentsPanel();
					} else { 
						recipe.removeAdd(name); 
						updateAddsPanel();
					}
				}
			});
			
			setLayout(new BorderLayout());
			add(componentField, BorderLayout.WEST);
			add(addField, BorderLayout.CENTER);
			add(removeButton, BorderLayout.EAST);
		}
		public String getName() { return name; }
		public double getAmount() { return amount; } 
	}
	private class FiringLabel extends JPanel
	{
		private JLabel label;
		private JButton button;
		private String firingType;
		
		public FiringLabel(String firingType)
		{
			setBorder(BorderFactory.createEtchedBorder());
			this.firingType = firingType;
			label = new JLabel(firingType);
			button = new JButton();
			button.setIcon(new ImageIcon("exit_icon.png"));
			button.setPreferredSize(new Dimension(20,20));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					for(int k = 0; k < MAX_FIRING_TYPES; k++) {
						if(firingLabels[k].getName().equals(firingType)) {
							for(int i = k; i < MAX_FIRING_TYPES - 1; i++) { firingLabels[i] = firingLabels[i+1]; }
							break;
						}
					}
					updateFiringLabels();
				}
			});
		
			setLayout(new BorderLayout());
			add(label, BorderLayout.CENTER);
			add(button, BorderLayout.EAST);
		}
		public String getName() {return firingType;}
	}
	private class NewPhotoDescPanel extends JPanel
	{
		private int charCount;
		private JTextField messageField;
		private JLabel keyCount;		
		
		public NewPhotoDescPanel()
		{
			messageField = new JTextField(15);
			keyCount = new JLabel("Char Count: ");
			charCount = 0;
			
			setLayout(new BorderLayout());
			add(new JLabel("Enter a New Description - Max. 15 Characters"), BorderLayout.NORTH);
			add(messageField, BorderLayout.CENTER);
			add(keyCount, BorderLayout.SOUTH);
			
			messageField.addKeyListener(new KeyAdapter() {
	            public void keyReleased(KeyEvent e) {
	                charCount = messageField.getText().length();
	                if(charCount > 15) {
	                	charCount = 15;
	                	messageField.setText( messageField.getText().substring(0,15) );
	                }
	                keyCount.setText("Char Count: " + charCount);
	                validate();
	                repaint();
	            }
	        });  
		}
		public String getNewDesc() { return messageField.getText(); }
	}
	private class EditablePhotoPanel extends JPanel
	{	
		private GlazePhoto[] originalPhotos;
		private JButton prevPhotoButton;
		private JButton nextPhotoButton;
		
		private int photoPosition;
		
		public EditablePhotoPanel(GlazePhoto[] originalPhotos)
		{
			this.originalPhotos = originalPhotos;
			this.photoPosition = 0;
			
			TitledBorder border = new TitledBorder("Glaze Photos");
			border.setTitleJustification(TitledBorder.CENTER);
		    border.setTitlePosition(TitledBorder.TOP);
		    setBorder(border);
			
			prevPhotoButton = new JButton("<");
			prevPhotoButton.setPreferredSize(new Dimension(30,30));
			prevPhotoButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					photoPosition = (originalPhotos.length + photoPosition - 1) % originalPhotos.length;
					updatePanel();
				}
			});
			
			nextPhotoButton = new JButton(">");
			nextPhotoButton.setPreferredSize(new Dimension(30,30));
			nextPhotoButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					photoPosition = (photoPosition + 1) % originalPhotos.length;
					updatePanel();
				}
			});
			
			updatePanel();
		}
		
		private void updatePanel()
		{
			removeAll();
			EditablePhoto currentPhoto = new EditablePhoto(originalPhotos[photoPosition]);
			setLayout(new BorderLayout());
			add(prevPhotoButton, BorderLayout.WEST);
			add(nextPhotoButton, BorderLayout.EAST);
			add(currentPhoto, BorderLayout.CENTER);
			validate();
			repaint();
		}

		private class EditablePhoto extends JPanel
		{
			private GlazePhoto photo;
			private String desc;
			private BufferedImage img;
			private TitledBorder border;
			private final int PHOTO_DIMENSION = 300;
			
			public EditablePhoto(GlazePhoto photo)
			{
				this.photo = photo;
				this.desc = photo.getDesc();
				this.img = resizePhoto(photo.getPhoto(), PHOTO_DIMENSION);
				createPhotoDescPanel();
			}
			
			private BufferedImage resizePhoto(BufferedImage original, int newDim)
			{
				int w = original.getWidth();  
			    int h = original.getHeight();  
			    BufferedImage dimg = new BufferedImage(newDim, newDim, original.getType());  
			    Graphics2D g = dimg.createGraphics();  
			    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			    RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
			    g.drawImage(original, 0, 0, newDim, newDim, 0, 0, w, h, null);  
			    g.dispose();  
			    return dimg;  
			}
			private void createPhotoDescPanel()
			{
				JLabel photoLabel = new JLabel(new ImageIcon(img));
				JPanel buttonPanel = new JPanel();
				JButton changeDescButton = new JButton("Change Description");
				JButton removePhotoButton = new JButton("Remove");
				JButton addPhotoButton = new JButton("Add Photo");
				
				addPhotoButton.setPreferredSize(new Dimension( 100 , 20 ));
				addPhotoButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						System.out.println("IMPLEMENT ADD PHOTO FUNCTION IN GLAZE EDIT PANEL");
					}
				});
				
				changeDescButton.setPreferredSize(new Dimension( 140 , 20 ));
				changeDescButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						NewPhotoDescPanel inputPanel = new NewPhotoDescPanel();
						JOptionPane.showMessageDialog(null,inputPanel,"Update Description",JOptionPane.INFORMATION_MESSAGE);
						String newDesc = inputPanel.getNewDesc();
						if(newDesc != null)
						{
							System.out.println("Description set to: "+newDesc);
							photo.setDesc(newDesc);
							border.setTitle(newDesc);
							validate();
							repaint();
							/**
							 * IMPLEMENT THIS IN FUTURE TO CHANGE THE RECIPE FILE...
							 */
						}
					}
				});
				
				removePhotoButton.setPreferredSize(new Dimension( 90 , 20 ));
				removePhotoButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						int dialogResult = JOptionPane.showConfirmDialog (null, "This will permanently delete the photo... Are you sure?","Warning",JOptionPane.YES_NO_OPTION);
						if(dialogResult == JOptionPane.YES_OPTION){
							/**
							 * Remove the photo
							 */
						}
					}
				});
				
				buttonPanel.add(addPhotoButton);
				buttonPanel.add(changeDescButton);
				buttonPanel.add(removePhotoButton);
				
				border = new TitledBorder(desc);
				border.setTitleJustification(TitledBorder.CENTER);
			    border.setTitlePosition(TitledBorder.BOTTOM);
			    photoLabel.setBorder(border);
				
				setLayout(new BorderLayout());
				add(photoLabel, BorderLayout.CENTER);
				add(buttonPanel, BorderLayout.SOUTH);	
			}
		}
	}
}