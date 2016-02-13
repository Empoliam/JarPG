package utilities;

public class Dice 
{

	int min, max;
	long lMin, lMax;
	
	public Dice(int l, int h)
	{
		
		min = l;
		max = h + 1;
		
	}
	
	public Dice(long l, long h)
	{
		
		lMin = l;
		lMax = h + 1;
		
	}
	
	public int Roll()
	{
		
		return (int)(min+(Math.random()*(max-min)));
		
	}
	
	public long RollLong()
	{
		
		return (long)(lMin+(Math.random()*(lMax-lMin)));		
	}
	
}
