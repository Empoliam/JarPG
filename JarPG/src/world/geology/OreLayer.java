package world.geology;

import utilities.noise.NoiseMap;
import static utilities.ColourBank.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OreLayer{

	int size;
	int data[][];

	double[][] A;
	double[][] B;

	public OreLayer(int size, String PATH, String name) 
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
				
				if(pointDataA <= 0.32 && pointDataB >= 0.68)
				{
					
					data[x][y] = 5;
					image.setRGB(x, y, PYRITE_COLOUR);
					
				}
				else if ((pointDataA >= 0.32 && pointDataA <= 0.44 ) && pointDataB >= 0.68)
				{
					
					data[x][y] = 6;
					image.setRGB(x, y, CHALCOPYRITE_COLOUR);
					
				}
				else if ((pointDataA >= 0.44 && pointDataA <= 0.56 ) && pointDataB >= 0.68)
				{
					
					data[x][y] = 7;
					image.setRGB(x, y, SPHALERITE_COLOUR);
					
				}
				else if((pointDataA >= 0.56 && pointDataA <= 0.68 ) && pointDataB >= 0.68)
				{
					
					data[x][y] = 8;
					image.setRGB(x, y, STIBNITE_COLOUR);
					
				}
				else if(pointDataA >= 0.68 && pointDataB >= 0.68)
				{
					
					data[x][y] = 9;
					image.setRGB(x, y, CINNABAR_COLOUR);
					
				}
				else if(pointDataA <= 0.32 && (pointDataB >= 0.56 && pointDataB <= 0.68))
				{
					
					data[x][y] = 10;
					image.setRGB(x, y, REALGAR_COLOUR);
					
				}
				else if((pointDataA >= 0.32 && pointDataA <= 0.44) && (pointDataB >= 0.56 && pointDataB <= 0.68))
				{
					
					data[x][y] = 11;
					image.setRGB(x, y, GALENA_COLOUR);
					
				}
				else if((pointDataA >= 0.44 && pointDataA <= 0.56)&& (pointDataB >= 0.56 && pointDataB <= 0.68))
				{
					
					data[x][y] = 12;
					image.setRGB(x, y, HEMATITE_COLOUR);
					
				}
				else if((pointDataA >= 0.56 && pointDataA <= 0.68)&& (pointDataB >= 0.56 && pointDataB <= 0.68))
				{
					
					data[x][y] = 13;
					image.setRGB(x, y, SPINEL_COLOUR);
					
				}
				else if(pointDataA >= 0.68 && (pointDataB >= 0.56 && pointDataB <= 0.68))
				{
					
					data[x][y] = 14;
					image.setRGB(x, y, MAGNETITE_COLOUR);
					
				}
				else if(pointDataA <= 0.32 && (pointDataB <= 0.56 && pointDataB >= 0.44))
				{
					
					data[x][y] = 15;
					image.setRGB(x, y, ILMENITE_COLOUR);
					
				}
				else if((pointDataA >= 0.32 && pointDataA <= 0.44) && (pointDataB <= 0.56 && pointDataB >= 0.44))
				{
					
					data[x][y] = 16;
					image.setRGB(x, y, CHROMITE_COLOUR);	
					
				}
				else if((pointDataA >= 0.44 && pointDataA <= 0.56) && (pointDataB >= 0.44 && pointDataB <= 0.56))
				{
					
					data[x][y] = 17;
					image.setRGB(x, y, CASSITERITE_COLOUR);					
					
				}
				else if((pointDataA >= 0.56 && pointDataA <= 0.68) && (pointDataB >= 0.44 && pointDataB <= 0.56))
				{
					
					data[x][y] = 18;
					image.setRGB(x, y, RUTILE_COLOUR);					
					
				}
				else if(pointDataA >= 0.68 && (pointDataB >= 0.44 && pointDataB <= 0.56))
				{
					
					data[x][y] = 19;
					image.setRGB(x, y, URANINITE_COLOUR);
					
				}
				else if(pointDataA <= 0.32 && (pointDataB >= 0.32 && pointDataB <= 0.44))
				{
					
					data[x][y] = 20;
					image.setRGB(x, y, GOETHITE_COLOUR);			
					
				}
				else if((pointDataA >= 0.32 && pointDataA <= 0.44) && (pointDataB >= 0.32 && pointDataB <= 0.44))
				{
					
					data[x][y] = 21;
					image.setRGB(x, y, MALACHITE_COLOUR);
					
				}
				else if((pointDataA >= 0.44 && pointDataA <= 0.56) && (pointDataB >= 0.32 && pointDataB <= 0.44))
				{
					
					data[x][y] = 22;
					image.setRGB(x, y, WOLFRAMITE_COLOUR);
					
				}
				else if((pointDataA >= 0.56 && pointDataA <= 0.68) && (pointDataB >= 0.32 && pointDataB <= 0.44))
				{
					
					data[x][y] = 23;
					image.setRGB(x, y, WULFENITE_COLOUR);				
					
				}
				else if(pointDataA >= 0.68 && (pointDataB >= 0.32 && pointDataB <= 0.44))
				{
					
					data[x][y] = 24;
					image.setRGB(x, y, CARNOTITE_COLOUR);
					
				}
				else if((pointDataA >= 0.32 && pointDataA <= 0.44) && pointDataB <= 0.32)
				{
					
					data[x][y] = 96;
					image.setRGB(x, y, BAUXITE_COLOUR);				
					
				}
				else if((pointDataA >= 0.44 && pointDataA <= 0.56) && pointDataB <= 0.32)
				{
					
					data[x][y] = 97;
					image.setRGB(x, y, SPERRYLITE_COLOUR);
					
				}
				else if((pointDataA >= 0.56 && pointDataA <= 0.68) && pointDataB <= 0.32)
				{
					
					data[x][y] = 98;
					image.setRGB(x, y, BORNITE_COLOUR);			
					
				}
				else if (pointDataA >= 0.68 && pointDataB <= 0.32)
				{
					
					data[x][y] = 99;
					image.setRGB(x, y, COBALTITE_COLOUR);
					
				}
				else
				{
					
					data[x][y] = 100;
					image.setRGB(x, y, SCHEELITE_COLOUR);
					
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
