package world;

import java.io.Serializable;

public class SuperRegion implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	Region[][] regions = new Region[40][40];
	
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
