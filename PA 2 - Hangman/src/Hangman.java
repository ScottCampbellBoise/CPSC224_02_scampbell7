import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Hangman {
	
	//TO DO:
	/**
	 * Check if word is complete
	 * Way to end the game
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
		
		while(numStrikes <= 6 && word != null)
		{
			String enteredChar = displayHangman(word,guessedChars, numStrikes);
			if(enteredChar != null)
			{
				guessedChars += enteredChar;
				
				if(!word.contains(""+enteredChar))
					numStrikes++;
			} else {
				word = displayMenu();
				guessedChars = "";
				numStrikes = 0;
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
	
		if(option == 0) // Quit game
			JOptionPane.showMessageDialog(null, "Goodbye! Thanks for playing!");
		else {			
			if(option == 1)
				return getWordFromUser();
			else
				return getRandomWord();
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
	
	public String displayHangman(String word, String guessedChars, int numStrikes)
	{	
		String guessedWord = 	"\t";
		String title = 			"\t**Hang-man**\t";
		String starHeader = 	"\t****************\t";
		String dotHeader = 		"\t------------\t";
		String guessedHeader = 	"\tUsed Letters\t";
		String usedLetters = 	"\t";
		String iconPath = "strike" + numStrikes + ".png";
		
		guessedWord = parseWordToDisplay(word, guessedWord, guessedChars);
		usedLetters = parseUsedLettersToDisplay(word, usedLetters, guessedChars);
		
		if(guessedWord.length() < 20) //min length for headers
		{
			int extra = 20 - guessedWord.length();
			int leftPad = extra / 2;
			int rightPad = extra - leftPad;
			
			guessedWord = pad(guessedWord, leftPad, rightPad);
		} else {
			int extra = guessedWord.length() - 20;
			int leftPad = extra / 2;
			int rightPad = extra - leftPad;
			
			title = pad(title, leftPad, rightPad);
			starHeader = pad(starHeader, leftPad, rightPad);
			dotHeader = pad(dotHeader, leftPad, rightPad);
			usedLetters = pad(usedLetters, leftPad, rightPad);
			guessedHeader = pad(guessedHeader, leftPad, rightPad);
		}
		
		if(numStrikes < 6)
		{
			String message = title + "\n" + starHeader + "\n" + dotHeader + "\n" + guessedWord + "\n" + dotHeader + "\n\n" + guessedHeader + "\n" + usedLetters + "\n" + starHeader;
			String response = (String) JOptionPane.showInputDialog(null, message, "Hang Man", 0, new ImageIcon(iconPath), null, null);
			
			while(response != null && response.length() > 1 && !checkWord(response))
			{
				response = JOptionPane.showInputDialog(null, "Please enter a single valid character!");
			}
		
			return response;
		} else { //Striked out
			String message = title + "\n" + starHeader + "\n" + dotHeader + "\n" + word + "\n" + dotHeader + "\n\n" + guessedHeader + "\n" + usedLetters + "\n" + starHeader;
			JOptionPane.showMessageDialog(null, message, "You Struck Out!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("strike6.png"));
			return null;
		}
	}
	
	String parseUsedLettersToDisplay(String word, String usedLetters, String guessedChars)
	{
		for(int k = 0; k < guessedChars.length(); k++)
		{
			if(!word.contains(guessedChars.substring(k,k+1)))
			{
				usedLetters += " " + guessedChars.charAt(k) + " ";
			}
		}
		return (usedLetters + "\t");
	}
	
	String parseWordToDisplay(String word, String guessedWord, String guessedChars)
	{
		for(int k = 0; k < word.length(); k++)
		{
			if(guessedChars.contains(word.substring(k,k+1)))
			{
				guessedWord += " " + word.charAt(k) + " ";
			} else {
				 guessedWord += " _ "; 
			}
			
		}
		return (guessedWord + "\t");
	}
	
	String pad(String original, int leftPad, int rightPad)
	{
		for(int k = 0; k < leftPad; k++)
		{
			original = " " + original;
		}
		
		for(int k = 0; k < rightPad; k++)
		{
			original += " ";
		}
		return original;
	}
	
}
