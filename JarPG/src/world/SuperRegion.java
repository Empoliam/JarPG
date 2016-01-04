package world;

public class SuperRegion 
{

	Region[][] regions = new Region[10][10];
	
	SuperRegion(){}
	
	public void saveTile(int x, int y, Region in)
	{
		
		regions[x][y] = in;
		
	}
	public Region getTile(int x, int y)
	{
		
		return regions[x][y];
		
	}
	
}
