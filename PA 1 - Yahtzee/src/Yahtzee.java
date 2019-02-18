
/*
 * Homework #1
 * Due Date: Thursday, January 31st
 * Names: Scott Campbell & Arron Cushing
 */

import java.util.Scanner;

public class Yahtzee {

	public static void main(String[] args)
	{
		new Yahtzee();
	}

	public Yahtzee()
	{
		final int DICE_IN_PLAY = 5;
		int[] hand = new int[DICE_IN_PLAY];
		char playAgain = 'y';
		Scanner scnr = new Scanner(System.in);

		while(playAgain == 'y')
		{
			String keep = "nnnnn";
			int turn = 1;

			while(turn < 4 && !keep.equals("yyyyy"))
			{
				for(int dieNumber = 0; dieNumber < DICE_IN_PLAY; dieNumber++)
				{
					if(keep.charAt(dieNumber) != 'y')
					{
						hand[dieNumber] = rollDie();
					}
				}

				System.out.print("Your roll was: ");
				for(int dieNumber = 0; dieNumber < DICE_IN_PLAY; dieNumber++)
				{
					System.out.print(hand[dieNumber] + " ");
				}
				System.out.print("\n");

				if(turn < 3)
				{
					System.out.print("enter dice to keep (y or n) ");
					keep += scnr.nextLine();
				}
				turn++;
			}

			sortArray(hand, DICE_IN_PLAY);
			System.out.print("Here is your sorted hand: ");
			for(int dieNumber = 0; dieNumber < DICE_IN_PLAY; dieNumber++)
			{
				System.out.print(hand[dieNumber] + " ");
			}
			System.out.print("\n");

			for (int dieValue = 1; dieValue <=6; dieValue++)
	        {
	            int currentCount = 0;
	            for (int diePosition = 0; diePosition < 5; diePosition++)
	            {
	                if (hand[diePosition] == dieValue)
	                    currentCount++;
	            }
	            System.out.print("Score " + (dieValue * currentCount) + " on the ");
	            System.out.print(dieValue + " line\n");
	        }
			if (maxOfAKindFound(hand) >= 3)
	        {
				System.out.print("Score " + totalAllDice(hand) + " on the ");
				System.out.print("3 of a Kind line\n");
	        }
	        else System.out.print("Score 0 on the 3 of a Kind line\n");

	        if (maxOfAKindFound(hand) >= 4)
	        {
	        	System.out.print("Score " + totalAllDice(hand) + " on the ");
	        	System.out.print("4 of a Kind line\n");
	        }
	        else System.out.print("Score 0 on the 4 of a Kind line\n");

	        if (fullHouseFound(hand))
	            System.out.print("Score 25 on the Full House line\n");
	        else
	            System.out.print("Score 0 on the Full House line\n");

	        if (maxStraightFound(hand) >= 4)
	            System.out.print("Score 30 on the Small Straight line\n");
	        else
	            System.out.print("Score 0 on the Small Straight line\n");

	        if (maxStraightFound(hand) >= 5)
	            System.out.print("Score 40 on the Large Straight line\n");
	        else
	            System.out.print("Score 0 on the Large Straight line\n");

	        if (maxOfAKindFound(hand) >= 5)
	            System.out.print("Score 50 on the Yahtzee line\n");
	        else
	            System.out.print("Score 0 on the Yahtzee line\n");

	        System.out.print("Score " + totalAllDice(hand) + " on the ");
	        System.out.print("Chance line");
	        System.out.print("\nEnter 'y' to play again ");

	        playAgain = scnr.nextLine().charAt(0);
	    }

		scnr.close();
	}

	public int rollDie()
	{
		int roll = (int) (Math.random() * 6) + 1;
		return roll;
	}

	public int maxOfAKindFound(int[] hand)
	{
		int maxCount = 0;
		int currentCount;

		for(int dieValue = 1; dieValue <= 6; dieValue++)
		{
			currentCount = 0;

			for(int diePosition = 0; diePosition < 5; diePosition++)
			{
				if(hand[diePosition] == dieValue)
				{
					currentCount++;
				}
			}

			if(currentCount > maxCount)
			{
				maxCount = currentCount;
			}
		}
		return maxCount;
	}

	public int maxStraightFound(int[] hand)
	{
		int maxLength = 1;
		int curLength = 1;

		for(int counter = 0; counter < 4; counter++)
		{
			if(hand[counter] + 1 == hand[counter + 1])
			{
				curLength++;
			} else if(hand[counter] + 1 == hand[counter + 1]) {
				curLength = 1;
			}

			if(curLength > maxLength)
			{
				maxLength = curLength;
			}
		}

		return maxLength;
	}

	public boolean fullHouseFound(int[] hand)
	{
		boolean foundFH = false;
		boolean found3K = false;
		boolean found2K = false;
		int currentCount;

		for(int dieValue = 1; dieValue <= 6; dieValue++)
		{
			currentCount = 0;
			for(int diePosition = 0; diePosition < 5; diePosition++)
			{
				if(hand[diePosition] == dieValue)
				{
					currentCount++;
				}
			}

			if(currentCount == 2)
			{
				found2K = true;
			}

			if(currentCount == 3)
			{
				found3K = true;
			}
		}

		if(found2K && found3K)
		{
			foundFH = true;
		}

		return foundFH;
	}

	public int totalAllDice(int[] hand)
	{
		int total = 0;

		for(int diePosition = 0; diePosition < 5; diePosition++)
		{
			total += hand[diePosition];
		}

		return total;
	}

	public void sortArray(int[] array, int size)
	{
		boolean swap;
		int temp;

		do
		{
			swap = false;
			for(int count = 0; count < (size - 1); count++)
			{
				if(array[count] > array[count + 1])
				{
					temp = array[count];
					array[count] = array[count + 1];
					array[count + 1] = temp;
					swap = true;
				}
			}
		} while (swap);
	}

}
