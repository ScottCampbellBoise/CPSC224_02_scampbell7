import java.util.Scanner;

public class TestPet {

	public static void main(String[] args)
	{
		Pet myPet = new Pet("Addy", "Dog", 3);
		Scanner scnr = new Scanner(System.in);

		System.out.println("Your pet's name is: " + myPet.getName());
		System.out.println("Your pet is a: " + myPet.getAnimal());
		System.out.println("Your pet is " + myPet.getAge() + " years old");
		
		System.out.print("\n\nEnter a new name for your pet: ");
		myPet.setName(scnr.nextLine());
		System.out.println("Your pet's new name is: " + myPet.getName());

		System.out.print("Enter a new animal for your pet to be: ");
		myPet.setAnimal(scnr.nextLine());
		System.out.println("Your pet is now a: " + myPet.getAnimal());

		System.out.print("Enter a new age for your pet: ");
		myPet.setAge(scnr.nextDouble());
		System.out.println("Your pet's new age is: " + myPet.getAge());
	}
}
