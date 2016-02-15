package world.geology;

import utilities.noise.NoiseMap;
import static utilities.ColourBank.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NativeLayer{

	int size;
	int data[][];

	double[][] A;
	double[][] B;

	public NativeLayer(int size, String PATH, String name) 
	{

		this.size = size;

		A = new double[size][size];
		B = new double[size][size];
		
		A = new NoiseMap(size).getResult();
		B = new NoiseMap(size).getResult();
		
		data = new int[size][size];
		data = populateLayer(PATH, name);

	}

	public int[][] populateLayer(String PATH, String filename)
	{

		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

		for(int y = 0; y < size; y ++)
		{

			int x = 0;
			for(; x < size; x ++)
			{

				double pointDataA = A[x][y];
				double pointDataB = B[x][y];
											
				if (pointDataA <= 0.6 && pointDataB > 0.7)
				{

					data[x][y] = 4;
					image.setRGB(x, y, SULPHUR_COLOUR);

				}
				else if (pointDataA <= 0.45 && pointDataB <= 0.7)
				{

					data[x][y] = 3;
					image.setRGB(x, y, IRON_COLOUR);

				}
				else if ((pointDataA <= 0.7 && pointDataA > 0.45)&& pointDataB <= 0.7)
				{

					data[x][y] = 2;
					image.setRGB(x, y, COPPER_COLOUR);

				}
				else if (pointDataA >= 0.7 && pointDataB <= 0.7)
				{

					data[x][y] = 1;
					image.setRGB(x, y, SILVER_COLOUR);

				}
				else if (pointDataA >= 0.6 && pointDataB >= 0.7)
				{

					data[x][y] = 0;
					image.setRGB(x, y, GOLD_COLOUR);

				}

			}

		}

		File f = new File(PATH + "/" + filename + ".bmp");
		try { ImageIO.write(image, "BMP", f); }
		catch(IOException e){System.out.println("Failed to print map");};

		return data;

	}
	
	public int getData(int x, int y)
	{
		
		return data[x][y];
		
	}

}
