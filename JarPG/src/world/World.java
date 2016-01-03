package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import utilities.noise.NoiseMap;
import static utilities.ColourBank.*;

public class World 
{

	int WORLD_SIZE;
	String PATH;
	
	double[][] data;
	Region[][] regions;

	public World(int sizein, String path)
	{

		WORLD_SIZE = sizein;
		PATH = "worlds/" + path;
		
		try {
			FileUtils.deleteDirectory(new File(PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new File(PATH).mkdirs();
		
		regions = new Region[WORLD_SIZE][WORLD_SIZE];
		data = new NoiseMap(WORLD_SIZE,0.63).getResult();
		
		generateLand();
		erode(1,"snow");
		erode(1,"mountain");
		erode(1,"solid");
		createBiomes();
		drawImage();

	}

	private void generateLand()
	{

		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;
			for(; x < WORLD_SIZE; x++)
			{

				regions[x][y] = new Region();
				regions[x][y].setHeight(data[x][y]);

				if(regions[x][y].getHeight() > 0.55)
				{
					
					regions[x][y].set("solid",true);

					if(regions[x][y].getHeight() > 0.84)
					{

						regions[x][y].set("snow",true);
						
					}
					if(regions[x][y].getHeight() > 0.75)
					{

						regions[x][y].set("mountain",true);

					}

				}
				if(regions[x][y].getHeight() <= 0.55)
				{

					regions[x][y].set("solid",false);

				}

			}

		}

	}

	private void drawImage()
	{

		BufferedImage image = new BufferedImage(WORLD_SIZE,WORLD_SIZE,BufferedImage.TYPE_INT_RGB);

		for ( int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;

			for(; x < WORLD_SIZE; x ++)
			{
				
				if (regions[x][y].get("mountain") == true)
				{
					
					if(regions[x][y].get("snow")) image.setRGB(x, y, SNOW_COLOUR);
					else image.setRGB(x, y, MOUNTAIN_COLOUR);
					
				}
				else if (regions[x][y].get("solid") == true)
				{
					
					switch(regions[x][y].getBiome())
					{
					
					case 0:
						image.setRGB(x, y, DESERT_COLOUR);
						break;
					case 1: 
						image.setRGB(x, y, SAVANNA_COLOUR);
						break;
					case 2: 
						image.setRGB(x, y, SEASONAL_FOREST_COLOUR);
						break;
					case 3: 
						image.setRGB(x, y, RAINFOREST_COLOUR);
						break;
					case 4: 
						image.setRGB(x, y, PLAINS_COLOUR);
						break;
					case 5: 
						image.setRGB(x, y, WOODS_COLOUR);
						break;
					case 6: 
						image.setRGB(x, y, FOREST_COLOUR);
						break;
					case 7: 
						image.setRGB(x, y, SWAMP_COLOUR);
						break;
					case 8: 
						image.setRGB(x, y, TAIGA_COLOUR);
						break;
					case 9: 
						image.setRGB(x, y, TUNDRA_COLOUR);
						break;
					
					}
					
				}
				else image.setRGB(x, y, SEA_COLOUR);

			}

		}

		File f = new File(PATH + "/map.bmp");
		try { ImageIO.write(image, "BMP", f); }
		catch(IOException e){System.out.println("Failed to print map");};

	}

	private void erode(int times, String tag)
	{
		
		for(int t = 0; t < times; t ++)
		{
			
			for(int y = 0; y < WORLD_SIZE; y++)
			{
				
				int x = 0;
				for(; x < WORLD_SIZE; x ++)
				{
					
					int count = countTag(x,y,tag);
					if ( count < 3 ) regions[x][y].set(tag,true);
					else if ( count >= 5 ) regions[x][y].set(tag,false);
					
				}
				
			}
			
		}
			
	}
	
	private int countTag(int x, int y, String tag)
	{
		int count = 0;
		try 
		{ 
			Region active = regions[x-1][y-1];
			if(active.get(tag) == false) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x][y-1];
			if(active.get(tag) == false) count ++;		
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 

			Region active = regions[x+1][y-1];
			if(active.get(tag) == false) count ++;		
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x-1][y];
			if(active.get(tag) == false) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x+1][y];
			if(active.get(tag) == false) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x-1][y+1];
			if(active.get(tag) == false) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x][y+1];
			if(active.get(tag) == false) count ++;				
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x+1][y+1];
			if(active.get(tag) == false) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		return count;
		
	}
	
	private void createBiomes()
	{
		
		BiomeMap biomes = new BiomeMap(WORLD_SIZE, PATH);
		
		for(int y = 0; y < WORLD_SIZE; y ++)
		{
			
			int x = 0;
			
			for(; x < WORLD_SIZE; x ++)
			{
				
				regions[x][y].setBiome(biomes.getData(x, y));
				
			}
			
		}
		
	}
	
}
