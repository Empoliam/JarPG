package utilities;

public class Dice 
{

	int min, max;
	
	public Dice(int l, int h)
	{
		
		min = l - 1;
		max = h + 1;
		
	}
	
	public int Roll()
	{
		
		return (int)(min+(Math.random()*(max-min)));
		
	}
	
}
