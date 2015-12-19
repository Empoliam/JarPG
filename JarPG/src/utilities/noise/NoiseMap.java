package utilities.noise;

import java.util.Random;
import utilities.noise.SimplexNoise;

public class NoiseMap 
{

	double[][] result;
	
	public NoiseMap(int size)
	{
		
		SimplexNoise noise = new SimplexNoise(100, 0.3, new Random().nextInt());
		
		double xStart=0;
		double XEnd=500;
		double yStart=0;
		double yEnd=500;

		double[][] result = new double[size][size];

		for(int i=0;i<size;i++)
		{
			
			for(int j=0;j<size;j++)
			{
				
				int x=(int)(xStart+i*((XEnd-xStart)/size));
				int y=(int)(yStart+j*((yEnd-yStart)/size));
				result[i][j]=0.5*(1+noise.getNoise(x,y));
				
			}
			
		}
		
	}
	
	public double getValue(int x, int y)
	{
		
		return result[x][y];
		
	}
	
}
