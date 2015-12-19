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

	private String PATH;
	
	private int NUM_CONTINENTS;
	private int CONTINENT_GENERATIONS;
	private boolean GENERATE_POLES;
	private boolean GENERATE_BEACHES;
	private int WORLD_SIZE;
	private double POLE_DIVISOR;
	private int MOUNTAIN_COUNT;
	private int LAKE_COUNT;
	private double MOUNTAIN_DIVISOR;
	private double LAKE_DIVISOR;
	private int RIVER_COUNT;
	private int RIVER_TWISTINESS;

	//Colours
	Color sea = new Color(30, 98, 168); int SEA_COLOUR = sea.getRGB();
	Color land = new Color(0, 163, 22); int LAND_COLOUR = land.getRGB();
	Color ice = new Color(200,200,255); int ICE_COLOUR = ice.getRGB();
	Color beach = new Color(255,223,128); int BEACH_COLOUR = beach.getRGB();
	Color river = new Color(60,60,190); int RIVER_COLOUR = river.getRGB();
	Color mountian = new Color(170,170,170); int MOUNTAIN_COLOUR = mountian.getRGB();
	Color snow = new Color(204, 255, 255); int SNOW_COLOUR = snow.getRGB();
	Color lake = new Color (50,50,190); int LAKE_COLOUR = lake.getRGB();

	Region[][] regions;

	public World(String path, int sizein, int continentsin, int generationsin,int tempin ,boolean polesin, boolean beachin, int mountains, int mountainsizein, int lakesin, int lakesizein, int twistinessin, int nriversin)
	{

		PATH = path;
		
		WORLD_SIZE = sizein;
		NUM_CONTINENTS = continentsin;
		GENERATE_POLES = polesin;
		GENERATE_BEACHES = beachin;
		CONTINENT_GENERATIONS = generationsin;
		MOUNTAIN_COUNT = mountains;
		LAKE_COUNT = lakesin;
		RIVER_TWISTINESS = twistinessin;
		RIVER_COUNT = nriversin;

		getPolarDiv(tempin);
		getMountainDiv(mountainsizein);
		getLakeDiv(lakesizein);

		generateRegions();
		buildLand();
		if(GENERATE_POLES == true) buildPoles();
		cleanLand();
		recordLand();
		seedMountains();
		buildMountains();
		cleanMountains();
		createRivers();
		seedLakes();
		buildLakes();
		cleanLakes();
		if(GENERATE_BEACHES == true) buildBeaches();

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

		for(int t = 0; t < (int)WORLD_SIZE / (MOUNTAIN_DIVISOR * 1.25); t ++)
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

		for(int t = 0; t < (int)WORLD_SIZE / (MOUNTAIN_DIVISOR / 1.25); t ++)
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
			regions[randCoord.Roll()][randCoord.Roll()].setSolid(true);			

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

			if(new Dice(0,1000).Roll()%7 == 0)
			{
				Dice randCoord = new Dice(0, WORLD_SIZE-1);
				regions[randCoord.Roll()][randCoord.Roll()].setSolid(true);
			}

			if(t < (int) 0.9 * CONTINENT_GENERATIONS)
			{

				if(new Dice(0,1000).Roll()%3 == 0)
				{
					Dice randCoord = new Dice(0, WORLD_SIZE-1);
					regions[randCoord.Roll()][randCoord.Roll()].setSolid(true);
				}

			}

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
				boolean lake = regions[x][y].getLake();
				boolean river = regions[x][y].getRiver();

				if(solid && !polar) image.setRGB(x, y, LAND_COLOUR);
				if(polar) image.setRGB(x, y, ICE_COLOUR);
				if(ocean) image.setRGB(x, y, SEA_COLOUR);
				if(beach) image.setRGB(x, y, BEACH_COLOUR);
				if(mountain) image.setRGB(x, y, MOUNTAIN_COLOUR);
				if(snow) image.setRGB(x, y, SNOW_COLOUR);
				if(river) image.setRGB(x, y, RIVER_COLOUR);
				if(lake) image.setRGB(x, y, LAKE_COLOUR);

			}

		}

		File f = new File(PATH + "/map.bmp");
		try { ImageIO.write(image, "BMP", f); }
		catch(IOException e){System.out.println("Failed to print map");};

	}

	private void cleanLand()
	{

		for(int t = 1; t <= 2; t ++)
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
					if (landcount >= 5)
					{

						regions[x][y].setSolid(true);

					}

					int polarcount = countTags(1, x, y);

					if (polarcount >= 5)
					{

						regions[x][y].setSolid(true);
						regions[x][y].setPolar(true);

					}

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

	private void getMountainDiv(int mountin)
	{

		switch(mountin)
		{

		case 0 : MOUNTAIN_DIVISOR = 50; break;
		case 1 : MOUNTAIN_DIVISOR = 30; break;
		case 2 : MOUNTAIN_DIVISOR = 20; break;

		}

	}

	public void generateJSON()
	{

		new File(PATH + "/regions").mkdir();

		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;
			for(;x < WORLD_SIZE; x++)
			{

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(regions[x][y]);
				try 
				{

					FileWriter writer = new FileWriter(PATH + "/regions/" + x + "-" + y +".json");
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

		File f = new File(PATH + "/land.txt");
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

		File f = new File(PATH + "/land.txt");

		long maxlines = countLines(f);

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
					if (mountainCount >= 5)
					{

						regions[x][y].setMountain(true);

					}

				}

			}

		}

	}

	private void seedLakes()
	{

		File f = new File(PATH + "/land.txt");

		long maxlines = countLines(f);

		for(int w = 0; w < LAKE_COUNT; w ++)
		{

			try 
			{

				boolean loop = true;

				while(loop)
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

					if(regions[x][y].getMountain() == false && checkTags(3, x, y) == false)
					{

						regions[x][y].setLake(true);
						loop = false;

					}

					reader.close();

				}

			}
			catch (IOException e)
			{

				e.printStackTrace();

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

	private long countLines(File f)
	{

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

		return maxlines;

	}

	private void buildLakes()
	{

		for(int t = 0; t < (int)WORLD_SIZE / LAKE_DIVISOR; t ++)
		{

			Region[][] dummy = new Region[WORLD_SIZE][WORLD_SIZE];

			for (int y = 0; y < WORLD_SIZE; y ++)
			{

				int x = 0;

				for(;x < WORLD_SIZE; x ++)
				{

					dummy[x][y] = new Region(regions[x][y]);

					boolean mountainpresent = checkTags(4, x, y);
					boolean oceanpresent = checkTags(3, x, y);
					boolean lakepresent = checkTags(6,x,y);

					if (oceanpresent == false && mountainpresent == false && lakepresent == true)
					{

						if((new Dice(0,1000).Roll()%2) == 0) 
						{

							dummy[x][y].setLake(true);

						}

					}

				}

			}

			regions = dummy;

		}

	}

	private void cleanLakes()
	{

		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;

			for(; x < WORLD_SIZE; x++)
			{

				if(regions[x][y].getSolid() == true)
				{

					int lakeCount = countTags(6, x, y);

					if (lakeCount < 3)
					{

						regions[x][y].setLake(false);

					}
					if (lakeCount >= 4)
					{

						regions[x][y].setLake(true);

					}

				}

			}

		}

	}

	private void getLakeDiv(int lakein)
	{

		switch(lakein)
		{

		case 0 : LAKE_DIVISOR = 100; break;
		case 1 : LAKE_DIVISOR = 75; break;
		case 2 : LAKE_DIVISOR = 50; break;

		}

	}

	private void createRivers()
	{

		File f = new File(PATH + "/land.txt");

		long maxlines = countLines(f);

		for(int t = 0; t < RIVER_COUNT; t ++)
		{

			int x = 0, y = 0;

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
				x = Integer.parseInt(activeCoords[0]);
				y = Integer.parseInt(activeCoords[1]);

				regions[x][y].setRiver(true);

				reader.close();

			}
			catch (IOException e)
			{

				e.printStackTrace();

			}

			int direction = new Dice(1,8).Roll();

			try
			{

				while(regions[x][y].getOcean() == false)
				{

					switch(direction)
					{

					case 1: 
						x = x - 1;
						y = y - 1;
						regions[x][y].setRiver(true);
						break;
					case 2: 
						y = y - 1;
						regions[x][y].setRiver(true);
						break;
					case 3: 
						x = x + 1;
						y = y - 1;
						regions[x][y].setRiver(true);
						break;
					case 4: 
						x = x + 1;
						regions[x][y].setRiver(true);
						break;
					case 5: 
						x = x + 1;
						y = y + 1;
						regions[x][y].setRiver(true);
						break;
					case 6: 
						y = y + 1;
						regions[x][y].setRiver(true);
						break;
					case 7: 
						x = x - 1;
						y = y + 1;
						regions[x][y].setRiver(true);
						break;
					case 8: 
						x = x - 1;
						regions[x][y].setRiver(true);
						break;

					}

					int change = new Dice(1,20-RIVER_TWISTINESS).Roll();

					if(change == 1) direction ++;
					else if(change == 2) direction --;
					if(direction == 9) direction = 1;
					else if(direction == -1) direction = 8;

				}

			}
			catch(ArrayIndexOutOfBoundsException e){};

		}

	}

	public int[] getSpawn()
	{

		int[] spawn = new int[2];

		File f = new File(PATH + "/land.txt");

		long maxlines = countLines(f);

		int x = 0, y = 0;

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
			x = Integer.parseInt(activeCoords[0]);
			y = Integer.parseInt(activeCoords[1]);

			reader.close();

		}
		catch (IOException e)
		{

			e.printStackTrace();

		}

		spawn[0] = x;
		spawn[1] = y;

		return spawn;

	}

}
