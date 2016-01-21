package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import utilities.noise.NoiseMap;
import world.CC.RegionLabel;
import world.geology.NativeLayer;
import world.geology.Rock;
import world.geology.SedimentLayer;

import static utilities.ColourBank.*;

public class World 
{

	int WORLD_SIZE;
	int spawnX, spawnY;
	String PATH;

	double[][] data;
	Region[][] regions;
	SuperRegion[][] superRegions;
	BiomeMap biomes;
	int[][] biomedata;
	
	public World(int sizein, String path)
	{

		WORLD_SIZE = sizein;
		PATH = path;

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
		generateGeology();
		generateSpawn();
		
		new RegionLabel(biomes.getArray(), WORLD_SIZE);

	}

	private void generateLand()
	{

		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;
			for(; x < WORLD_SIZE; x++)
			{

				regions[x][y] = new Region(x,y);
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
				else
				{

					switch(regions[x][y].getBiome())
					{

					case -1:
						image.setRGB(x, y, SEA_COLOUR);
						break;
					case 0:
						image.setRGB(x, y, DESERT_COLOUR);

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

		biomes = new BiomeMap(WORLD_SIZE, PATH);
		biomedata = biomes.getArray();
		
		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;

			for(; x < WORLD_SIZE; x ++)
			{

				if(regions[x][y].get("solid") == false)
				{
					regions[x][y].setBiome(-1);
					biomedata[x][y] = -1;
				}
				else
				{
					regions[x][y].setBiome(biomedata[x][y]);
				}

			}

		}

	}

	public void save()
	{
		new File(PATH + "/regions").mkdir();
		superRegions = new SuperRegion[WORLD_SIZE/40][WORLD_SIZE/40];

		try {
			FileWriter writer = new FileWriter(PATH + "/params.dat");
			writer.write("WORLD_SIZE:" + Integer.toString(WORLD_SIZE));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for(int Y = 0; Y < WORLD_SIZE/40; Y ++)
		{

			for(int X = 0; X < WORLD_SIZE/40; X ++)
			{

				superRegions[X][Y] = new SuperRegion();

				for(int y = 0; y < 40; y ++)
				{

					int  x = 0;

					for(; x < 40; x++)
					{

						superRegions[X][Y].saveTile(x, y, regions[x+(X*40)][y+(Y*40)]);

					}

				}

				try 
				{

					ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(PATH + "/regions/" + (X) + "-" + (Y) +".region"));
					os.writeObject(superRegions[X][Y]);
					os.close();


				}
				catch(IOException e)
				{
					e.printStackTrace();
				}

			}

		}

	}

	private void generateSpawn()
	{

		Random rand = new Random(System.currentTimeMillis());

		do
		{

			spawnX = rand.nextInt(WORLD_SIZE);
			spawnY = rand.nextInt(WORLD_SIZE);

		}
		while(regions[spawnX][spawnY].get("solid") == false);

	}

	public int getSpawn(char axis)
	{

		int ret = 0;

		switch(axis)
		{

		case 'x': 
			ret =  spawnX;
			break;
		case 'y':
			ret= spawnY;
			break;
		}

		return ret;

	}
	
	private void generateGeology()
	{
		
		Rock.getList();
		
		SedimentLayer sediment01 = new SedimentLayer(WORLD_SIZE, PATH, "Sediment01");
		SedimentLayer sediment02 = new SedimentLayer(WORLD_SIZE, PATH, "Sediment02");
		SedimentLayer sediment03 = new SedimentLayer(WORLD_SIZE, PATH, "Sediment03");
		NativeLayer native01 = new NativeLayer(WORLD_SIZE, PATH, "Native01");
		NativeLayer native02 = new NativeLayer(WORLD_SIZE, PATH, "Native02");
		
		for(int y = 0; y < WORLD_SIZE; y ++)
		{
			
			for(int x = 0; x < WORLD_SIZE; x ++)
			{
				
				regions[x][y].setRock("sediment", 0, new Rock(sediment01.getData(x, y)));
				regions[x][y].setRock("sediment", 1, new Rock(sediment02.getData(x, y)));
				regions[x][y].setRock("sediment", 2, new Rock(sediment03.getData(x, y)));
				regions[x][y].setRock("native", 0, new Rock(native01.getData(x, y)));
				regions[x][y].setRock("native", 1, new Rock(native02.getData(x, y)));
				
			}
			
		}
		
	}
	
}
