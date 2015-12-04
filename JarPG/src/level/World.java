package level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
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

	Region[][] regions;

	public World(int sizein, int continentsin, int generationsin,int tempin ,boolean polesin, boolean beachin)
	{

		WORLD_SIZE = sizein;
		NUM_CONTINENTS = continentsin;
		GENERATE_POLES = polesin;
		GENERATE_BEACHES = beachin;
		CONTINENT_GENERATIONS = generationsin;

		getPolarDiv(tempin);

		generateRegions();
		buildLand();
		if(GENERATE_POLES == true) buildPoles();
		cleanLand();
		if(GENERATE_BEACHES == true) buildBeaches();
		seedMountains();

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

					boolean polepresent = checkPolar(x, y);

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

					boolean solidpresent = checkSolid(x, y);

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

				if(solid && !polar) image.setRGB(x, y, LAND_COLOUR);
				if(polar) image.setRGB(x, y, ICE_COLOUR);
				if(ocean) image.setRGB(x, y, SEA_COLOUR);
				if(beach) image.setRGB(x, y, BEACH_COLOUR);
				if(mountain) image.setRGB(x, y, MOUNTAIN_COLOUR);

			}

		}

		File f = new File("map.bmp");
		try { ImageIO.write(image, "BMP", f); }
		catch(IOException e){System.out.println("Failed to print map");};

	}

	private boolean checkSolid(int x, int y)
	{

		boolean solidpresent = false;
		try { if(regions[x-1][y-1].getSolid() == true) solidpresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x][y-1].getSolid() == true) solidpresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y-1].getSolid() == true) solidpresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x-1][y].getSolid() == true) solidpresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y].getSolid() == true) solidpresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x-1][y+1].getSolid() == true) solidpresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x][y+1].getSolid() == true) solidpresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y+1].getSolid() == true) solidpresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};

		return solidpresent;

	}

	private int countSolid(int x, int y)
	{

		int solidcount = 0;
		try { if(regions[x-1][y-1].getSolid() == true) solidcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x][y-1].getSolid() == true) solidcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y-1].getSolid() == true) solidcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x-1][y].getSolid() == true) solidcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y].getSolid() == true) solidcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x-1][y+1].getSolid() == true) solidcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x][y+1].getSolid() == true) solidcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y+1].getSolid() == true) solidcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};

		return solidcount;

	}

	private boolean checkPolar(int x, int y)
	{

		boolean polepresent = false;

		try { if(regions[x-1][y-1].getPolar() == true) polepresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x][y-1].getPolar() == true) polepresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y-1].getPolar() == true) polepresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x-1][y].getPolar() == true) polepresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y].getPolar() == true) polepresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x-1][y+1].getPolar() == true) polepresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x][y+1].getPolar() == true) polepresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y+1].getPolar() == true) polepresent = true; } catch(java.lang.ArrayIndexOutOfBoundsException e){};

		return polepresent;

	}

	private void cleanLand()
	{

		for(int y = 0; y < WORLD_SIZE; y ++)
		{

			int x = 0;

			for(; x < WORLD_SIZE; x++)
			{

				int landcount = countSolid(x, y);

				if (landcount < 4)
				{

					regions[x][y].setOcean(true);

				}
				if (landcount == 8)
				{

					regions[x][y].setSolid(true);

				}

				int polarcount = countPolar(x, y);

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

				int count = countSolid(x, y);
				boolean polar = regions[x][y].getPolar();
				boolean ocean = !regions[x][y].getSolid();				

				if (count != 8 && polar !=true && ocean == false)
				{

					regions[x][y].setBeach(true);

				}

			}

		}

	}

	private int countPolar(int x, int y)
	{

		int polarcount = 0;
		try { if(regions[x-1][y-1].getPolar() == true) polarcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x][y-1].getPolar() == true) polarcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y-1].getPolar() == true) polarcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x-1][y].getPolar() == true) polarcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y].getPolar() == true) polarcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x-1][y+1].getPolar() == true) polarcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x][y+1].getPolar() == true) polarcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};
		try { if(regions[x+1][y+1].getPolar() == true) polarcount ++; } catch(java.lang.ArrayIndexOutOfBoundsException e){};

		return polarcount;

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

	private void seedMountains()
	{

		for(int x = 0; x < MOUNTAIN_COUNT; x ++)
		{

			Dice Coord = new Dice(0,WORLD_SIZE-1);

			int setx = Coord.Roll();
			int sety = Coord.Roll();
			
			boolean ocean = regions[setx][sety].getOcean();
			
			while(ocean == true)
			{

				setx = Coord.Roll();
				sety = Coord.Roll();
				
				regions[setx][sety].getOcean();

			}

			regions[setx][sety].setMountain(true);

		}

	}

}
