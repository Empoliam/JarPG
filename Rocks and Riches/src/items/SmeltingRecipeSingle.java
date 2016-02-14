package items;

public class SmeltingRecipeSingle 
{

	public int requires;
	public int yeild;
	
	public SmeltingRecipeSingle(String in)
	{
		
		String[] parse = in.split(",");
		requires = Integer.parseInt(parse[0]);
		yeild = Integer.parseInt(parse[1]);
		
	}
		
}
