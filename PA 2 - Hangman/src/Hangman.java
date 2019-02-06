import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Hangman {
	
	//TO DO:
	/**
	 * Crop and resize images
	 * Check if word is complete
	 * Check if word is too long
	 * Check if entered char is a char and is valid/not already guessed
	 * Way to end the game
	 * Convert all inputed strings to lower case
	 */
	
	final String filePath = "words.txt";
	String[] words;

	public static void main(String[] args)
	{
		new Hangman();
	}
	
	public Hangman()
	{
		importWords(filePath);
		
		String word = displayMenu();
		String guessedChars = "";
		int numStrikes = 0;
		
		while(numStrikes <= 6)
		{
			char enteredChar = displayHangman(word,guessedChars, numStrikes);
			
			if(word.contains(""+enteredChar))
			{
				guessedChars += enteredChar;
			} else {
				numStrikes++;
			}
			
		}
	}
	
	// Displays 3 options - rand word, user-spec. word, exit 
	public String displayMenu()
	{
		String[] buttons = {"Exit Game", "Enter your own Word", "Use Random Word"};
		String title = "Hangman Main Menu";
		String body = "Select an option from below to get started!";
		int option = JOptionPane.showOptionDialog(null, body, title, JOptionPane.INFORMATION_MESSAGE, 0, new ImageIcon("icon.png"), buttons, buttons[0]);
	
		//Check if quit
		//Get the word -> by input doalog or file
		//Play the game 
		
		if(option == 0) // Quit game
		{
			JOptionPane.showMessageDialog(null, "Goodbye! Thanks for playing!");
			System.exit(0);
		} else {
			String enteredWord;
			
			if(option == 1) { // Enter own word
				enteredWord = getWordFromUser();
			} else { // get random word
				enteredWord = getRandomWord();
			}
			
			return enteredWord;
		}
		return null;
	}
	
	public String getRandomWord()
	{
		int position = (int)(Math.random()*words.length);
		return words[position];
	}
	
	public String getWordFromUser()
	{
		return JOptionPane.showInputDialog("Please enter a single word for the game:");
	}
	
	public void importWords(String filePath)
	{
		String allWords = readFile(filePath);
		this.words = allWords.split("\n");
	}
	
	public String readFile(String filePath)
	{
		try
		{
			return new String (Files.readAllBytes( Paths.get(filePath)));
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Error reading words from the file " + filePath+ " ...");
			return null;
		}
	}
	
	public boolean checkWord(String word)
	{
		boolean isValid = true;
		for(int k = 0; k < word.length(); k++)
		{
			char c = word.charAt(k);
			if(!((c >= 64 && c <= 90) || (c >= 97 && c<= 122))) // If c is a valid character
			{
				isValid = false;
			}
		}
		return isValid;
	}
	
	public char displayHangman(String word, String guessedChars, int numStrikes)
	{
		String guessedWord = "\t";
		String title = 		"\t**Hang-man**\t";
		String starHeader = "\t****************\t";
		String dotHeader = 	"\t------------\t";
		String iconPath = "strike"+numStrikes+".png";
		
		for(int k = 0; k < word.length(); k++)
		{
			if(guessedChars.contains(word.substring(k,k+1)))
				guessedWord += " " + word.charAt(k) + " ";
			 else 
				 guessedWord += " _ "; 
		}
		guessedWord += "\t";
		
		if(guessedWord.length() < 20) //min length for headers
		{
			int extra = 20 - guessedWord.length();
			int leftPad = extra / 2;
			int rightPad = extra - leftPad;
			
			for(int k = 0; k < leftPad; k++)
			{
				guessedWord = " " + guessedWord;
			}
			
			for(int k = 0; k < rightPad; k++)
			{
				guessedWord += " ";
			}
		} else {
			int extra = guessedWord.length() - 20;
			int leftPad = extra / 2;
			int rightPad = extra - leftPad;
			
			for(int k = 0; k < leftPad; k++)
			{
				 title = " " + title;
				 starHeader = " " + title;
				 dotHeader = " " + dotHeader;
			}
			
			for(int k = 0; k < rightPad; k++)
			{
				title += " ";
				starHeader += " ";
				dotHeader += " ";
			}
		}
		
		String message = title + "\n" + starHeader + "\n" + dotHeader + "\n" + guessedWord + "\n" + dotHeader + "\n" + starHeader;
		
		String response = (String) JOptionPane.showInputDialog(null, message, "Title", 0, new ImageIcon(iconPath), null, null);
		
		//MAKE CHECK FOR THE CHARACTER RETURNED
		//CHECK IF WORD IS COMPLETE
		
		return response.charAt(0);
	}
	
}
