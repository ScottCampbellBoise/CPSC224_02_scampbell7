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

	final String filePath = "words.txt"; // File containing words to choose from when user requests a random word
	String[] words;

	public static void main(String[] args)
	{
		new Hangman();
	}

	//Runs the hangman game
	//@pre none
	//@post game is run, stops when user decides
	//@param none
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
			} else { // if the user enters a valid character, checks if the character is in the word 
				guessedChars += enteredChar; // add the guessed character to the list
				if(!word.contains(""+enteredChar)) // if the character is not in the word, increment the strikes
					numStrikes++;
			}
		}
	}

	//@pre game is running
	//@post menu is displayed in a window pane, user can select what to do from there
	//@param none
	//@return null
	public String displayMenu()
	{
		String[] buttons = {"Exit Game", "Enter your own Word", "Use Random Word"}; // different options for main menu
		String title = "Hangman Main Menu"; // window label
		String body = "Select an option from below to get started!"; // window contents
		int option = JOptionPane.showOptionDialog(null, body, title, JOptionPane.INFORMATION_MESSAGE, 0, new ImageIcon("icon.png"), buttons, buttons[0]); // displays buttons, and obtains the selected option from the user
 
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

	//@pre user selected option to use a random word
	//@post random word is selected from the list for a user to guess
	//@param none
	//@return random word from the list
	public String getRandomWord()
	{
		int position = (int)(Math.random()*words.length); // Generate a random position to get the word from
		return words[position]; // return the word from the array
	}

	//@pre user selected option to enter their own word in
	//@post user-entered word is selected for guessing
	//@param none
	//@return user-entered word
	public String getWordFromUser()
	{ 
		return JOptionPane.showInputDialog("Please enter a single word for the game:"); // User enters word for guessing here
	}

	//@pre word list exists, file to read words from also exists
	//@post word list contains all the words from the file
	//@param string for file to read words from
	//@return none
	public void importWords(String filePath)
	{
		String allWords = readFile(filePath); // get all words as single String
		this.words = allWords.split("\n"); // put each word into a spot in an array
	}

	//@pre file for reading exists
	//@post file contents are placed into a single string for placement into array of words
	//@param filePath - name of file to be read from
	//@return contents of file as a single string if successful, null if not
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

	//@pre user has entered a word
	//@post none
	//@param word - word that user has entered
	//@return true if all characters in word are letters, false otherwise
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

	//@pre word has been selected, is a valid word
	//@post window displaying current state of game is shown to user
	//@param word - word that user has entered, or has been randomly selected from the file
	//       guessedChars - all characters that the user has guessed
	//       numStrikes - number of incorrect guesses the user has made
	//@return next window to display if game is not over, * if game is over
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
		} else { //if word to guess is longer than the min length
			int extra = guessedWord.length() - 20;
			int leftPad = extra / 2;
			int rightPad = extra - leftPad;

			//Pads everything else to center everything
			title = pad(title, leftPad, rightPad);
			starHeader = pad(starHeader, leftPad, rightPad);
			dotHeader = pad(dotHeader, leftPad, rightPad);
			usedLetters = pad(usedLetters, leftPad, rightPad);
			guessedHeader = pad(guessedHeader, leftPad, rightPad);
		}

		// if user has not struck out and the word has not been completely guessed yet
		if(numStrikes < 6 && !wordFound(word, guessedChars))
		{
			String message = title + "\n" + starHeader + "\n" + dotHeader + "\n" + guessedWord + "\n" + dotHeader + "\n\n" + guessedHeader + "\n" + usedLetters + "\n" + starHeader;
			String response = (String) JOptionPane.showInputDialog(null, message, "Hang Man", 0, new ImageIcon(iconPath), null, null);

			// if user enters a response longer than 1 character
			while(response != null && response.length() != 1 && !checkWord(response))
			{
				response = JOptionPane.showInputDialog(null, "Please enter a single valid character!");
			}

			return response;
		} else if(numStrikes < 6 && wordFound(word, guessedChars)) { // word has been completely guessed
			String message = title + "\n" + starHeader + "\n" + dotHeader + "\n" + word + "\n" + dotHeader + "\n\n" + guessedHeader + "\n" + usedLetters + "\n" + starHeader;
			JOptionPane.showMessageDialog(null, message, "Congratulations!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons/happy.png"));
			return "*";
		} else { //Struck out
			String message = title + "\n" + starHeader + "\n" + dotHeader + "\n" + word + "\n" + dotHeader + "\n\n" + guessedHeader + "\n" + usedLetters + "\n" + starHeader;
			JOptionPane.showMessageDialog(null, message, "You Struck Out!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons/strike6.png"));
			return "*";
		}
	}

	//@pre user has made (a) guess(es)
	//@post none
	//@param word - word to be guessed by user
	//       guessedChars - all the characters that have been guessed by the user
	//@return true if all characters in word have been found, false otherwise
	boolean wordFound(String word, String guessedChars)
	{
		boolean isFound = true;
		for(int k = 0; k < word.length(); k++)
		{
			if(!guessedChars.contains(word.substring(k, k+1)))
			{ // if the character in the word at index k has not been guessed 
				isFound = false;
			}
		}
		return isFound;
	}

	//@pre word has been selected
	//@post incorrectly guessed characters are displayed in the window, under the word to be guessed
	//@param word - word to be guessed by user
	//	 usedLetters - incorrect characters guessed by the user
	//       guessedChars - all the characters the user has guessed
	//@return array of characters that have been guessed by user and are not in the word
	String parseUsedLettersToDisplay(String word, String usedLetters, String guessedChars)
	{
		for(int k = 0; k < guessedChars.length(); k++)
		{
			if(!word.contains(guessedChars.substring(k,k+1)))
			{ // if the word does not contain the character at index k
				usedLetters += " " + guessedChars.charAt(k) + " ";
			}
		}
		return (usedLetters + "\t");
	}

	//@pre word has been selected
	//@post word is displayed in the window, with dashes replacing characters that the user has not successfully guessed yet
	//@param word - word to be guessed by the user
	//       guessedWord - word, with letters that have been guessed filled in, and dashes otherwise
	//       guessedChars - all the characters the user has guessed
	//@return the word, with dashes replacing unguessed letters
	String parseWordToDisplay(String word, String guessedWord, String guessedChars)
	{
		for(int k = 0; k < word.length(); k++)
		{
			if(guessedChars.contains(word.substring(k,k+1)))
			{ // if word contains character in guessedChars at index k
				guessedWord += " " + word.charAt(k) + " ";
			} else {
				 guessedWord += " _ ";
			}
		}
		return (guessedWord + "\t");
	}

	//@pre none
	//@post the string we enter has been padded with spaces
	//@param original - the original string we want to pad
	//       leftPad - the number of spaces we want to add to the left of the string
	//       rightPad - number of spaces we want to add to the right of the string
	//@return original string, with added spaces
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
