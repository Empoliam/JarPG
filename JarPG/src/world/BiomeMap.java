package world;

import utilities.noise.NoiseMap;
import static utilities.ColourBank.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BiomeMap{

	int size;
	int data[][];

	public BiomeMap(int size, String PATH) 
	{

		this.size = size;
		data = new int[size][size];
		populateLayer(PATH);

	}

	public void populateLayer(String PATH)
	{

		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

		NoiseMap temperature = new NoiseMap(size);
		NoiseMap rainfall = new NoiseMap(size);

		data = new int[size][size];

		for(int y = 0; y < size; y ++)
		{

			int x = 0;

			for(;x < size; x ++)
			{

				double vTemp = temperature.getValue(x, y);
				double vRain = rainfall.getValue(x, y);

				if((vTemp <= 1.00 && vTemp > 0.70) && (vRain >= 0 && vRain < 0.25)) data[x][y] = 0;
				else if((vTemp <= 1 && vTemp > 0.75) && (vRain >= 0.25 && vRain < 0.50)) data[x][y] = 1;
				else if((vTemp <= 1 && vTemp > 0.75) && (vRain >= 0.50 && vRain < 0.75)) data[x][y] = 2;
				else if((vTemp <= 1 && vTemp > 0.75) && (vRain >= 0.75 && vRain <= 1)) data[x][y] = 3;
				else if((vTemp <= 0.70 && vTemp > 0.25) && (vRain >= 0 && vRain < 0.25)) data[x][y] = 4;
				else if((vTemp <= 0.75 && vTemp > 0.40) && (vRain >= 0.25 && vRain < 0.50)) data[x][y] = 5;
				else if((vTemp <= 0.75 && vTemp > 0.40) && (vRain >= 0.50 && vRain < 0.75)) data[x][y] = 6;
				else if((vTemp <= 0.75 && vTemp > 0.40) && (vRain >= 0.75 && vRain <= 1)) data[x][y] = 7;
				else if((vTemp <= 0.40 && vTemp > 0.25) && (vRain >= 0.25 && vRain <= 1)) data[x][y] = 8;
				else if(vTemp <= 0.25 && vTemp >= 0) data[x][y] = 9;

				switch(data[x][y])
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

		}

		File f = new File(PATH + "/biomemap.bmp");
		try { ImageIO.write(image, "BMP", f); }
		catch(IOException e){System.out.println("Failed to print map");};

	}

	public int getData(int x, int y)
	{

		return data[x][y];

	}

}
