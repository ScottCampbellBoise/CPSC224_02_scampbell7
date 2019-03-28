// This class stores a component name and its amount
	//TO DO: check if the component has a warning and display it ...
	public class GlazeComponent
	{
		private String name;
		private double amount;
		
		public GlazeComponent(String name, double amount)
		{
			this.name = name;
			this.amount = amount;
		}
		
		public String getName() { return name; }
		public double getAmount() { return amount; }
	}