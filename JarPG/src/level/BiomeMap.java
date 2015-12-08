package level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import utilities.Dice;

public class BiomeMap 
{

	int WORLD_SIZE;
	int TOTAL_BIOMES = 5;
	
	int GRASSLAND_COLOUR = new Color(51, 204, 51).getRGB();
	int DESERT_COLOUR = new Color(255, 204, 102).getRGB();
	int FOREST_COLOUR = new Color(0, 102, 0).getRGB();
	int TUNDRA_COLOUR = new Color(0, 204, 153).getRGB();
	int JUNGLE_COLOUR = new Color(102, 153, 0).getRGB();

	BiomeCell[][] cells;

	BiomeMap(int inWorldSize)
	{

		WORLD_SIZE = inWorldSize;
		cells = new BiomeCell[WORLD_SIZE][WORLD_SIZE];

		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;
			for(; x < WORLD_SIZE; x ++)
			{

				cells[x][y] = new BiomeCell();

			}

		}

		seed();
		grow();
		drawMap();

	}

	private void seed()
	{

		int noSeeds = (WORLD_SIZE/10) + new Dice(0,10).Roll();
		Dice coordPicker = new Dice(0,WORLD_SIZE-1);
		for (int k = 0; k < noSeeds; k ++)
		{

			int type = new Dice(1,5).Roll();
			cells[coordPicker.Roll()][coordPicker.Roll()].set(type,true);

		}

	}

	private void grow()
	{

		for(int t = 0; t < (int)(WORLD_SIZE); t ++)
		{

			BiomeCell[][] dummy = new BiomeCell[WORLD_SIZE][WORLD_SIZE];

			for (int y = 0; y < WORLD_SIZE; y ++)
			{

				int x = 0;

				for(;x < WORLD_SIZE; x ++)
				{

					dummy[x][y] = new BiomeCell(cells[x][y]);
					int type = commonNeighbour(x, y);		

					if (type != 0)
					{

						dummy[x][y].set(type, true);

					}

				}

			}

			cells = dummy;

		}

	}

	private int commonNeighbour(int x, int y)
	{
		
		int[] totals = new int[TOTAL_BIOMES];
		int type = 0;

		try 
		{ 
				int active = cells[x-1][y-1].getType();
				totals[active-1] ++;
			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			int active = cells[x-1][y-1].getType();
			totals[active-1] ++;
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			int active = cells[x-1][y-1].getType();
			totals[active-1] ++;
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			int active = cells[x-1][y-1].getType();
			totals[active-1] ++;
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			int active = cells[x-1][y-1].getType();
			totals[active-1] ++;
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			int active = cells[x-1][y-1].getType();
			totals[active-1] ++;
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			int active = cells[x-1][y-1].getType();
			totals[active-1] ++;
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			int active = cells[x-1][y-1].getType();
			totals[active-1] ++;
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};
		
		int total = Arrays.stream(totals).sum();
		
		if(total > 0)
		{
			
			System.out.println(total);
			type = 1;
			for(int k = 1; k < TOTAL_BIOMES; k++)
			{
				
				if(totals[k] > totals[k-1])
				{
					
					type = k + 1;
					
				}
				
			}
			
		}
			
		return type;

	}

	private void drawMap()
	{

		BufferedImage image = new BufferedImage(WORLD_SIZE, WORLD_SIZE, BufferedImage.TYPE_INT_RGB);
		for( int y = 0; y < WORLD_SIZE; y ++)
		{
			int x = 0;		
			for(; x < WORLD_SIZE; x ++)
			{

				boolean grassland = cells[x][y].isGrassland();
				boolean desert = cells[x][y].isDesert();
				boolean forest = cells[x][y].isForest();
				boolean tundra = cells[x][y].isTundra();
				boolean jungle = cells[x][y].isJungle();

				if(grassland) image.setRGB(x, y, GRASSLAND_COLOUR);
				else if(desert) image.setRGB(x, y, DESERT_COLOUR);
				else if(forest) image.setRGB(x, y, FOREST_COLOUR);
				else if(tundra) image.setRGB(x, y, TUNDRA_COLOUR);
				else if(jungle) image.setRGB(x, y, JUNGLE_COLOUR);

			}

		}

		new File("world").mkdirs();
		File f = new File("world/biomemap.bmp");
		try { ImageIO.write(image, "BMP", f); }
		catch(IOException e){System.out.println("Failed to print map");};

	}
	
}
