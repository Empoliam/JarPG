package worldV2;

public class Region 
{

	double height;
	int biome;
	boolean solid;
	
	public Region()
	{
		
	}
	
	public void setHeight(double heightin)
	{
		
		height = heightin;
		
	}
	
	public double getHeight()
	{
		
		return height;
		
	}

	public boolean getSolid() 
	{
		
		return solid;
		
	}

	public void setSolid(boolean solid) 
	{
		
		this.solid = solid;
		
	}
	
}
