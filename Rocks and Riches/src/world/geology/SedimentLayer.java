package world.geology;

import utilities.noise.NoiseMap;
import static utilities.ColourBank.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SedimentLayer{

	int size;
	int data[][];

	double[][] A;
	double[][] B;

	public SedimentLayer(int size, String PATH, String name) 
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
				
				if((pointDataA <= 0.32 && pointDataB >= 0.68) || ((pointDataA >= 0.44 && pointDataA <= 0.56) && (pointDataB >= 0.44 && pointDataB <= 0.56)))
				{
					
					data[x][y] = 25;
					image.setRGB(x, y, BORAX_COLOUR);
					
				}
				else if ((((pointDataA >= 0.32 && pointDataA <= 0.44 ) && pointDataB >= 0.68)) || ((pointDataA >= 0.56 && pointDataA <= 0.68) && (pointDataB >= 0.44 && pointDataB <= 0.56)))
				{
					
					data[x][y] = 27;
					image.setRGB(x, y, BRECCIA_COLOUR);
					
				}
				else if ((((pointDataA >= 0.44 && pointDataA <= 0.56 ) && pointDataB >= 0.68)) || (pointDataA >= 0.68 && (pointDataB >= 0.44 && pointDataB <= 0.56)))
				{
					
					data[x][y] = 28;
					image.setRGB(x, y, CONGLOMERATE_COLOUR);
					
				}
				else if((((pointDataA >= 0.56 && pointDataA <= 0.68 ) && pointDataB >= 0.68)) || (pointDataA <= 0.32 && (pointDataB >= 0.32 && pointDataB <= 0.44)))
				{
					
					data[x][y] = 29;
					image.setRGB(x, y, ARKOSE_COLOUR);
					
				}
				else if(((pointDataA >= 0.68 && pointDataB >= 0.68)) || ((pointDataA >= 0.32 && pointDataA <= 0.44) && (pointDataB >= 0.32 && pointDataB <= 0.44)))
				{
					
					data[x][y] = 30;
					image.setRGB(x, y, GREYWACKE_COLOUR);
					
				}
				else if((pointDataA <= 0.32 && (pointDataB >= 0.56 && pointDataB <= 0.68)) || ((pointDataA >= 0.44 && pointDataA <= 0.56) && (pointDataB >= 0.32 && pointDataB <= 0.44)))
				{
					
					data[x][y] = 31;
					image.setRGB(x, y, SILTSTONE_COLOUR);
					
				}
				else if(((pointDataA >= 0.32 && pointDataA <= 0.44)&& (pointDataB >= 0.56 && pointDataB <= 0.68)) || ((pointDataA >= 0.56 && pointDataA <= 0.68) && (pointDataB >= 0.32 && pointDataB <= 0.44)))
				{
					
					data[x][y] = 32;
					image.setRGB(x, y, SANDSTONE_COLOUR);
					
				}
				else if(((pointDataA >= 0.44 && pointDataA <= 0.56)&& (pointDataB >= 0.56 && pointDataB <= 0.68)) || (pointDataA >= 0.68 && (pointDataB >= 0.32 && pointDataB <= 0.44)))
				{
					
					data[x][y] = 33;
					image.setRGB(x, y, CLAYSTONE_COLOUR);
					
				}
				else if(((pointDataA >= 0.56 && pointDataA <= 0.68)&& (pointDataB >= 0.56 && pointDataB <= 0.68)) || ((pointDataA >= 0.32 && pointDataA <= 0.44) && pointDataB <= 0.32))
				{
					
					data[x][y] = 34;
					image.setRGB(x, y, MUDSTONE_COLOUR);
					
				}
				else if((pointDataA >= 0.68 && (pointDataB >= 0.56 && pointDataB <= 0.68)) || ((pointDataA >= 0.44 && pointDataA <= 0.56) && pointDataB <= 0.32))
				{
					
					data[x][y] = 35;
					image.setRGB(x, y, SHALE_COLOUR);
					
				}
				else if((pointDataA <= 0.32 && (pointDataB <= 0.56 && pointDataB >= 0.44)) || ((pointDataA >= 0.56 && pointDataA <= 0.68) && pointDataB <= 0.32))
				{
					
					data[x][y] = 36;
					image.setRGB(x, y, LOESS_COLOUR);
					
				}
				else if(((pointDataA >= 0.32 && pointDataA <= 0.44) && (pointDataB <= 0.56 && pointDataB >= 0.44)) || (pointDataA >= 0.68 && pointDataB <= 0.32))
				{
					
					data[x][y] = 37;
					image.setRGB(x, y, LIMESTONE_COLOUR);	
					
				}
				else
				{
					
					data[x][y] = 26;
					image.setRGB(x, y, ANHYDRITE_COLOUR);
					
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
