package utilities.noise;

import java.util.Random;
import utilities.noise.SimplexNoise;

public class NoiseMap 
{

	private double[][] result;
	private int size;
	private double persistence;
	
	public NoiseMap(int size)
	{

		this.size = size;
		persistence = 0.6;
		createMap();

	}
	
	public NoiseMap(int size, double persistence)
	{
		
		this.size = size;
		this.persistence = persistence;
		createMap();
		
	}

	private void createMap()
	{

		SimplexNoise noise = new SimplexNoise(1000, persistence, new Random().nextInt());

		double xStart=0;
		double XEnd=500;
		double yStart=0;
		double yEnd=500;

		result = new double[size][size];

		for(int i=0;i<size;i++)
		{

			for(int j=0;j<size;j++)
			{

				int x=(int)(xStart+i*((XEnd-xStart)/size));
				int y=(int)(yStart+j*((yEnd-yStart)/size));
				result[i][j]=(1+(noise.getNoise(x,y)))*0.5;

			}

		}		

	}

	public double getValue(int x, int y)
	{

		return result[x][y];

	}

	public double[][] getResult()
	{

		return result;

	}

}
