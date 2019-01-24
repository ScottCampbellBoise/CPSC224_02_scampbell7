import java.util.Scanner;

public class Convert {

	public static void main(String[] args)
	{
		new Convert();
	}

	public Convert()
	{
		Scanner scnr = new Scanner(System.in);
		boolean isRunning = true;
		int meters;
		int choice;

		System.out.print("Enter a distance in meters: ");
		meters = scnr.nextInt();

		while(isRunning)
		{
			menu();
			System.out.print("Enter your choice: ");
			choice = scnr.nextInt();

			if(choice == 1)
			{
				showKilometers(meters);
			} else if (choice == 2) {
				showInches(meters);
			} else if (choice == 3) {
				showFeet(meters);
			} else {
				isRunning = false;
				System.out.println("Goodbye! Thanks for using the converter!");
			}

			System.out.print("\n\n");
		}
	}

	public void showKilometers(double meters)
	{
		double kilometers = meters * 0.001;
		System.out.println(meters + " meters converted into Kilometers: " + kilometers + " km.");
	}

	public void showInches(double meters)
	{
		double inches = meters * 39.37;
		System.out.println(meters + " meters converted into inches: " + inches + " in.");
	}

	public void showFeet(double meters)
	{
		double feet = meters * 3.281;
		System.out.println(meters + " meters converted into Feet: " + feet + " ft.");
	}

	public void menu()
	{
		System.out.println("1. Convert to Kilometers (km)");
		System.out.println("2. Convert to Inches (in)");
		System.out.println("3. Convert to Feet (ft)");
		System.out.println("4. Quit the program");
		System.out.print("\n\n");
	}
}
