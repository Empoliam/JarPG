package items;

public class SmeltingRecipeSingle 
{

	public int requires;
	public int nQuantity;
	public int yeild;
	public int nYeild;
	
	public SmeltingRecipeSingle(String in)
	{
		
		String[] parse = in.split(",");
		requires = Integer.parseInt(parse[0]);
		nQuantity = Integer.parseInt(parse[1]);
		yeild = Integer.parseInt(parse[2]);
		nYeild = Integer.parseInt(parse[3]);
		
	}
		
}
