import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class GUI extends JFrame{

	private String allContent;
	private String[] bookAndPrice;
	private String[] cart;
	
	private JList bookList;
	private JList cartList;
	
	private JButton addToCart;
	
	public static void main(String[] args)
	{
		new GUI();
	}
	
	public GUI()
	{
		setTitle("Book Store");
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try{
			bookList = readFile("BookPrices.txt");
		} catch(IOException e) {
			System.out.println("Error reading the File ... ");
		}
		
		addToCart = new JButton("Add to Cart");
		addToCart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				//get selected items from the bookList and add to the cart list, ignoring duplicates
			}
		});
		
		JPanel browsePanel = new JPanel();
		browsePanel.setLayout(new BorderLayout());
		browsePanel.add(new JLabel("Books to Add:"), BorderLayout.NORTH);
		browsePanel.add(bookList, BorderLayout.CENTER);
		browsePanel.add(addToCart, BorderLayout.SOUTH);
		browsePanel.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new GridLayout(1,2));
		masterPanel.add(browsePanel);
		
		add(masterPanel);
		
		pack();
		setVisible(true);
	}
		
	public void addToCart(Object[] newItems)
	{
		int numNewItems = 0;
		for(int k = 0; k < newItems.length; k++)
		{
			if(allContent.contains((String) newItems[k])) { newItems[k] = null; }
			else { numNewItems++; }
		}
		
		String[] tempCart = new String[cart.length + numNewItems];
		
		for(int k = 0 ; k < cart.length; k++)
		{
			//add to temp cart
		}
		
		//add new items (non-null items) to the cart
		
		updateCart();
	}
	
	public void updateCart()
	{
		// Make a new JList component containing the contents of cart
		// add the list to a cartPanel
	}
	
	public JList readFile(String filePath) throws IOException
	{
		allContent = "";
		Scanner scnr = new Scanner(new File(filePath));
		while(scnr.hasNextLine())
		{
			allContent += scnr.nextLine() + ";";
		}
		
		bookAndPrice = allContent.split(";");
		String[] titles = new String[bookAndPrice.length];
		
		for(int k = 0; k < bookAndPrice.length; k++)
		{
			String[] info = bookAndPrice[k].split(",");
			titles[k] = info[0];
		}
		
		JList myList = new JList(titles);
		myList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		return myList;
	}
	
}
