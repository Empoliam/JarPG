package level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

import utilities.Dice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class World 
{

	private int NUM_CONTINENTS = 6;
	private int CONTINENT_GENERATIONS = 50;
	private boolean GENERATE_POLES = false;
	private boolean GENERATE_BEACHES = false;
	private int WORLD_SIZE = 400;
	private double POLE_DIVISOR = 2;
	private int MOUNTAIN_COUNT = 5;

	//Colours
	Color sea = new Color(30, 98, 168); int SEA_COLOUR = sea.getRGB();
	Color land = new Color(0, 163, 22); int LAND_COLOUR = land.getRGB();
	Color ice = new Color(200,200,255); int ICE_COLOUR = ice.getRGB();
	Color beach = new Color(255,223,128); int BEACH_COLOUR = beach.getRGB();
	Color river = new Color(60,60,190); int RIVER_COLOUR = river.getRGB();
	Color mountian = new Color(170,170,170); int MOUNTAIN_COLOUR = mountian.getRGB();
	Color snow = new Color(204, 255, 255); int SNOW_COLOUR = snow.getRGB();

	Region[][] regions;

	public World(int sizein, int continentsin, int generationsin,int tempin ,boolean polesin, boolean beachin, int mountains)
	{

		WORLD_SIZE = sizein;
		NUM_CONTINENTS = continentsin;
		GENERATE_POLES = polesin;
		GENERATE_BEACHES = beachin;
		CONTINENT_GENERATIONS = generationsin;
		MOUNTAIN_COUNT = mountains;

		getPolarDiv(tempin);

		generateRegions();
		buildLand();
		if(GENERATE_POLES == true) buildPoles();
		cleanLand();
		if(GENERATE_BEACHES == true) buildBeaches();
		recordLand();
		seedMountains();
		buildMountains();
		cleanMountains();

	}

	private void generateRegions()
	{

		regions = new Region[WORLD_SIZE][WORLD_SIZE];

		for( int y = 0; y < WORLD_SIZE; y ++)
		{
			int x = 0;		
			for(; x < WORLD_SIZE; x ++)
			{

				regions[x][y] = new Region();

			}

		}

	}

	private void buildPoles()
	{

		for(int x = 0; x < WORLD_SIZE; x ++)
		{

			regions[x][0].setPolar(true);
			regions[x][WORLD_SIZE-1].setPolar(true);

		}

		for(int t = 0; t < (int)WORLD_SIZE / POLE_DIVISOR; t ++)
		{

			Region[][] dummy = new Region[WORLD_SIZE][WORLD_SIZE];

			for (int y = 0; y < WORLD_SIZE; y ++)
			{

				int x = 0;

				for(;x < WORLD_SIZE; x ++)
				{

					dummy[x][y] = new Region(regions[x][y]);

					boolean polepresent = checkTags(1, x, y);

					if (polepresent == true)
					{

						if((new Dice(0,1000).Roll())%5 == 0) 
						{

							dummy[x][y].setPolar(true);

						}

					}

				}

			}

			regions = dummy;

		}

	}

	private void buildMountains()
	{

		for(int t = 0; t < (int)WORLD_SIZE / 50; t ++)
		{

			Region[][] dummy = new Region[WORLD_SIZE][WORLD_SIZE];

			for (int y = 0; y < WORLD_SIZE; y ++)
			{

				int x = 0;

				for(;x < WORLD_SIZE; x ++)
				{

					dummy[x][y] = new Region(regions[x][y]);

					boolean mountainpresent = checkTags(4, x, y);
					boolean ocean = checkTags(3, x, y);

					if (mountainpresent == true && ocean == false)
					{

						if((new Dice(0,1000).Roll())%3 == 0) 
						{

							dummy[x][y].setMountain(true);
							dummy[x][y].setSnow(true);
							dummy[x][y].setBeach(false);

						}

					}

				}

			}

			regions = dummy;

		}

		for(int t = 0; t < (int)WORLD_SIZE / 40; t ++)
		{

			Region[][] dummy = new Region[WORLD_SIZE][WORLD_SIZE];

			for (int y = 0; y < WORLD_SIZE; y ++)
			{

				int x = 0;

				for(;x < WORLD_SIZE; x ++)
				{

					dummy[x][y] = new Region(regions[x][y]);

					boolean mountainpresent = checkTags(4 ,x, y);
					boolean ocean = regions[x][y].getOcean();

					if (mountainpresent == true && ocean == false)
					{

						if((new Dice(0,1000).Roll())%3 == 0) 
						{

							dummy[x][y].setMountain(true);
							dummy[x][y].setBeach(false);

						}

					}

				}

			}

			regions = dummy;

		}

	}

	private void buildLand()
	{

		for(int x = 0; x < NUM_CONTINENTS; x ++)
		{

			Dice randCoord = new Dice(0, WORLD_SIZE-1);
			regions[randCoord.Roll()][randCoord.Roll()].setSolid(true);;			

		}

		for(int t = 0; t < CONTINENT_GENERATIONS; t ++)
		{

			Region[][] dummy = new Region[WORLD_SIZE][WORLD_SIZE];

			for (int y = 0; y < WORLD_SIZE; y ++)
			{

				int x = 0;

				for(;x < WORLD_SIZE; x ++)
				{

					boolean solidpresent = checkTags(0, x, y);

					dummy[x][y] = new Region(regions[x][y]);

					if (solidpresent == true)
					{

						if((new Dice(0,1000).Roll())%5 == 0) dummy[x][y].setSolid(true);

					}

				}

			}

			regions = dummy;

		}

	}

	public void imgOut()
	{

		BufferedImage image = new BufferedImage(WORLD_SIZE, WORLD_SIZE, BufferedImage.TYPE_INT_RGB);
		for( int y = 0; y < WORLD_SIZE; y ++)
		{
			int x = 0;		
			for(; x < WORLD_SIZE; x ++)
			{

				boolean solid = regions[x][y].getSolid();
				boolean polar = regions[x][y].getPolar();
				boolean beach = regions[x][y].getBeach();
				boolean ocean = regions[x][y].getOcean();
				boolean mountain = regions[x][y].getMountain();
				boolean snow = regions[x][y].getSnow();

				if(solid && !polar) image.setRGB(x, y, LAND_COLOUR);
				if(polar) image.setRGB(x, y, ICE_COLOUR);
				if(ocean) image.setRGB(x, y, SEA_COLOUR);
				if(beach) image.setRGB(x, y, BEACH_COLOUR);
				if(mountain) image.setRGB(x, y, MOUNTAIN_COLOUR);
				if(snow) image.setRGB(x, y, SNOW_COLOUR);

			}

		}

		new File("world").mkdirs();
		File f = new File("world/map.bmp");
		try { ImageIO.write(image, "BMP", f); }
		catch(IOException e){System.out.println("Failed to print map");};

	}

	private void cleanLand()
	{

		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;

			for(; x < WORLD_SIZE; x++)
			{

				int landcount = countTags(0, x, y);

				if (landcount < 4)
				{

					regions[x][y].setOcean(true);

				}
				if (landcount == 8)
				{

					regions[x][y].setSolid(true);

				}

				int polarcount = countTags(1, x, y);

				if (polarcount == 8)
				{

					regions[x][y].setSolid(true);
					regions[x][y].setPolar(true);

				}

			}

		}

	}

	private void buildBeaches()
	{

		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;

			for(; x < WORLD_SIZE; x++)
			{

				boolean nearocean = checkTags(3, x, y);
				boolean polar = regions[x][y].getPolar();
				boolean ocean = !regions[x][y].getSolid();				

				if (nearocean == true && polar != true && ocean == false)
				{

					regions[x][y].setBeach(true);

				}

			}

		}

	}

	private void getPolarDiv(int tempin)
	{

		switch(tempin)
		{

		case 0 : POLE_DIVISOR = 3; break;
		case 1 : POLE_DIVISOR = 6; break;
		case 2 : POLE_DIVISOR = 12; break;
		case 3 : POLE_DIVISOR = 24; break;
		case 4 : POLE_DIVISOR = 48; break;

		}

	}

	public void generateJSON()
	{

		new File("world").mkdirs();

		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;
			for(;x < WORLD_SIZE; x++)
			{

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(regions[x][y]);
				try 
				{

					System.out.println("Saving " + x + "," + y);
					FileWriter writer = new FileWriter("world/tile" + x + "-" + y +".json");
					writer.write(json);
					writer.close();

				}
				catch(IOException e)
				{


				}

			}

		}

	}

	private void recordLand()
	{

		new File("world").mkdirs();
		File f = new File("world/land.txt");
		try {
			f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try 
		{ 
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));

			for (int y = 0; y < WORLD_SIZE; y ++)
			{

				int x = 0;

				for(;x < WORLD_SIZE; x ++)
				{

					boolean isLand = regions[x][y].getSolid();
					boolean isPolar = regions[x][y].getPolar();
					boolean isBeach = regions[x][y].getBeach();

					if(isLand == true && isPolar == false && isBeach == false)
					{

						writer.write(x + "," + y);
						writer.newLine();

					}

				}

			}

			writer.close();

		}
		catch (IOException e) { e.printStackTrace();}

	}

	private void seedMountains()
	{

		File f = new File("world/land.txt");
		long maxlines = 0;

		try 
		{

			BufferedReader lineCount = new  BufferedReader(new FileReader(f));
			while (lineCount.readLine() != null)
			{

				maxlines ++;

			}
			lineCount.close();

		}
		catch (IOException e)
		{

			e.printStackTrace();

		}

		for(int w = 0; w < MOUNTAIN_COUNT; w ++)
		{

			try 
			{

				BufferedReader reader = new BufferedReader(new FileReader(f));
				Dice dice = new Dice(0L,maxlines);

				long lineNo = dice.RollLong();

				for(long z = 0; z < lineNo - 1; z++)
				{

					reader.readLine();

				}
				String activeLine = reader.readLine();
				String[] activeCoords = activeLine.split(",");
				int x = Integer.parseInt(activeCoords[0]);
				int y = Integer.parseInt(activeCoords[1]);

				regions[x][y].setMountain(true);

				reader.close();

			}
			catch (IOException e)
			{

				e.printStackTrace();

			}

		}

	}

	private void cleanMountains()
	{

		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;

			for(; x < WORLD_SIZE; x++)
			{

				if(regions[x][y].getSolid() == true)
				{

					int mountainCount = countTags(4, x, y);

					if (mountainCount < 4)
					{

						regions[x][y].setMountain(false);

					}
					if (mountainCount == 8)
					{

						regions[x][y].setMountain(true);

					}

				}

			}

		}

	}

	private boolean checkTags(int tag, int x, int y)
	{

		boolean presence = false;

		try 
		{ 
			Region active = regions[x-1][y-1];
			if(active.getTags()[tag] == true) presence = true;			

		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x][y-1];
			if(active.getTags()[tag] == true) presence = true;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x+1][y-1];
			if(active.getTags()[tag] == true) presence = true;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x-1][y];
			if(active.getTags()[tag] == true) presence = true;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x+1][y];
			if(active.getTags()[tag] == true) presence = true;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x-1][y+1];
			if(active.getTags()[tag] == true) presence = true;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x][y+1];
			if(active.getTags()[tag] == true) presence = true;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x+1][y+1];
			if(active.getTags()[tag] == true) presence = true;			

		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		return presence;

	}

	private int countTags(int tag, int x, int y)
	{

		int count = 0;

		try 
		{ 
			Region active = regions[x-1][y-1];
			if(active.getTags()[tag] == true) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x][y-1];
			if(active.getTags()[tag] == true) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 

			Region active = regions[x+1][y-1];
			if(active.getTags()[tag] == true) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x-1][y];
			if(active.getTags()[tag] == true) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x+1][y];
			if(active.getTags()[tag] == true) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x-1][y+1];
			if(active.getTags()[tag] == true) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x][y+1];
			if(active.getTags()[tag] == true) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		try 
		{ 
			Region active = regions[x+1][y+1];
			if(active.getTags()[tag] == true) count ++;			
		} 
		catch(java.lang.ArrayIndexOutOfBoundsException e){};

		return count;

	}

}
