package items;

public class SmeltingRecipeSingle 
{

	public int requires;
	public int nQuantity;
	public int yield;
	public int nYield;
	
	public SmeltingRecipeSingle(String in)
	{
		
		String[] parse = in.split(",");
		requires = Integer.parseInt(parse[0]);
		nQuantity = Integer.parseInt(parse[1]);
		yield = Integer.parseInt(parse[2]);
		nYield = Integer.parseInt(parse[3]);
		
	}
		
}
