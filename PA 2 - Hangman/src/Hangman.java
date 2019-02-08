/**
 * Runs the Hangman game, using JDialogBoxes to interface with the user.
 * Allows for a user selected word, or for a random word to be used.
 *
 * @Authors: Scott Campbell & Arron Cushing
 * @Version: 7 February 2019 - Assignment 2
 * @file: Hangman.java
 */

import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Hangman {

	final String filePath = "words.txt";
	String[] words;

	public static void main(String[] args)
	{
		new Hangman();
	}

	//Runs the hangman game
	//@pre
	//@post
	//@param
	public Hangman()
	{
		importWords(filePath); // Import the 'random' words from a file

		String word = displayMenu(); // Get the word to be used from the user
		String guessedChars = ""; // Set a string to hold all of the characters the user has guessed
		int numStrikes = 0; // record the number of strikes the user has

		while(numStrikes <= 6 && word != null)
		{
			String enteredChar = displayHangman(word, guessedChars, numStrikes); // Get the guessed character from the player

			if(enteredChar == null) {
				System.exit(0); // if word is null, exit the game.
			} else if(enteredChar.equals("*")) { // if an asterisk, restart the game by going back to main menu
					word = displayMenu();
					guessedChars = "";
					numStrikes = 0;
			} else {
				guessedChars += enteredChar; // add the guessed character to the list
				if(!word.contains(""+enteredChar)) // if the character is not in the word, increment the strikes
					numStrikes++;
			}
		}
	}

	//@pre
	//@post
	//@param
	//@return
	public String displayMenu()
	{
		String[] buttons = {"Exit Game", "Enter your own Word", "Use Random Word"}; // different options for main menu
		String title = "Hangman Main Menu";
		String body = "Select an option from below to get started!";
		int option = JOptionPane.showOptionDialog(null, body, title, JOptionPane.INFORMATION_MESSAGE, 0, new ImageIcon("icon.png"), buttons, buttons[0]);

		if(option == 0) // Quit game
			JOptionPane.showMessageDialog(null, "Goodbye! Thanks for playing!");
		else {
			if(option == 1) // Ask for a word from the user
				return getWordFromUser();
			else if (option == 2) // Get a word from the random word list
				return getRandomWord();
			else // Quit the game (exit button pressed)
				System.exit(0);
		}
		return null;
	}

	//@pre
	//@post
	//@param
	//@return
	public String getRandomWord()
	{
		int position = (int)(Math.random()*words.length); // Generate a random position to get the word from
		return words[position]; // return the word from the array
	}

	//@pre
	//@post
	//@param
	//@return
	public String getWordFromUser()
	{
		return JOptionPane.showInputDialog("Please enter a single word for the game:");
	}

	//@pre
	//@post
	//@param
	//@return
	public void importWords(String filePath)
	{
		String allWords = readFile(filePath); // get all words as single String
		this.words = allWords.split("\n"); // put each word into a spot in an array
	}

	//@pre
	//@post
	//@param
	//@return
	public String readFile(String filePath)
	{
		try
		{
			return new String (Files.readAllBytes( Paths.get(filePath))); // return the entire file as a single string
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Error reading words from the file " + filePath+ " ...");
			return null;
		}
	}

	//@pre
	//@post
	//@param
	//@return
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

	//@pre
	//@post
	//@param
	//@return
	public String displayHangman(String word, String guessedChars, int numStrikes)
	{
		//Strings to hold header messages to be displayed on the JOptionPane
		String guessedWord = 	"\t";
		String title = 			"\t**Hang-man**\t";
		String starHeader = 	"\t****************\t";
		String dotHeader = 		"\t------------\t";
		String guessedHeader = 	"\tUsed Letters\t";
		String usedLetters = 	"\t";
		String iconPath = "icons/strike" + numStrikes + ".png";

		//Prepare the strings for a clean display
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

		if(numStrikes < 6 && !wordFound(word, guessedChars))
		{
			String message = title + "\n" + starHeader + "\n" + dotHeader + "\n" + guessedWord + "\n" + dotHeader + "\n\n" + guessedHeader + "\n" + usedLetters + "\n" + starHeader;
			String response = (String) JOptionPane.showInputDialog(null, message, "Hang Man", 0, new ImageIcon(iconPath), null, null);

			while(response != null && response.length() != 1 && !checkWord(response))
			{
				response = JOptionPane.showInputDialog(null, "Please enter a single valid character!");
			}

			return response;
		} else if(numStrikes < 6 && wordFound(word, guessedChars)) {
			String message = title + "\n" + starHeader + "\n" + dotHeader + "\n" + word + "\n" + dotHeader + "\n\n" + guessedHeader + "\n" + usedLetters + "\n" + starHeader;
			JOptionPane.showMessageDialog(null, message, "Congratulations!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons/happy.png"));
			return "*";
		} else { //Striked out
			String message = title + "\n" + starHeader + "\n" + dotHeader + "\n" + word + "\n" + dotHeader + "\n\n" + guessedHeader + "\n" + usedLetters + "\n" + starHeader;
			JOptionPane.showMessageDialog(null, message, "You Struck Out!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons/strike6.png"));
			return "*";
		}
	}

	//@pre
	//@post
	//@param
	//@return
	boolean wordFound(String word, String guessedChars)
	{
		boolean isFound = true;
		for(int k = 0; k < word.length(); k++)
		{
			if(!guessedChars.contains(word.substring(k, k+1)))
			{
				isFound = false;
			}
		}
		return isFound;
	}

	//@pre
	//@post
	//@param
	//@return
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

	//@pre
	//@post
	//@param
	//@return
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

	//@pre
	//@post
	//@param
	//@return
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
