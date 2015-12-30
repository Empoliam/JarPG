package worldV2;

import utilities.noise.NoiseMap;

public class World 
{

	int size;
	double[][] data;
	Region[][] regions;
	
	public World(int sizein)
	{
		
		size = sizein;
		data = new NoiseMap(size).getResult();
		generateLand();
		drawImage();
		
	}
	
	private void generateLand()
	{
		
		for(int y = 0; y < size; y ++)
		{
			
			int x = 0;
			for(; x < size; x++)
			{
				
				regions[x][y] = new Region();
				regions[x][y].setHeight(data[x][y]);
				if(regions[x][y].getHeight() > 0.5)
				{
					
					regions[x][y].setSolid(true);
					
				}
				else
				{
					
					regions[x][y].setSolid(false);
					
				}
				
			}
			
		}
		
	}
	
	private void drawImage()
	{
		
		
		
	}
	
}
