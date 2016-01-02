package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utilities.noise.NoiseMap;
import static utilities.ColourBank.*;

public class World 
{

	int size;
	double[][] data;
	Region[][] regions;

	public World(int sizein)
	{

		size = sizein;
		regions = new Region[size][size];
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

				if(regions[x][y].getHeight() > 0.55)
				{
					regions[x][y].setSolid(true);

					if(regions[x][y].getHeight() > 0.84)
					{

						regions[x][y].setSnow(true);
						
					}
					if(regions[x][y].getHeight() > 0.75)
					{

						regions[x][y].setMountain(true);

					}

				}
				if(regions[x][y].getHeight() <= 0.55)
				{

					regions[x][y].setSolid(false);

				}

			}

		}

	}

	private void drawImage()
	{

		BufferedImage image = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);

		for ( int y = 0; y < size; y ++)
		{

			int x = 0;

			for(; x < size; x ++)
			{

				if (regions[x][y].getSolid() == true)image.setRGB(x, y, LAND_COLOUR);
				if (regions[x][y].getMountain() == true)image.setRGB(x, y, MOUNTAIN_COLOUR);
				if (regions[x][y].getSnow() == true)image.setRGB(x, y, SNOW_COLOUR);
				if (regions[x][y].getSolid() == false)image.setRGB(x, y, SEA_COLOUR);

			}

		}

		File f = new File("newmap.bmp");
		try { ImageIO.write(image, "BMP", f); }
		catch(IOException e){System.out.println("Failed to print map");};

	}

}
