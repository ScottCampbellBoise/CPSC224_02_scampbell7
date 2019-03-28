
public class Pet {

	String name;
	String animal;
	double age;

	public Pet(String name, String animal, double age)
	{
		this.name = name;
		this.animal = animal;
		this.age = age;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setAnimal(String animal)
	{
		this.animal = animal;
	}

	public void setAge(double age)
	{
		this.age = age;
	}

	public String getName()
	{
		return name;
	}

	public String getAnimal()
	{
		return animal;
	}

	public double getAge()
	{
		return age;
	}
}
