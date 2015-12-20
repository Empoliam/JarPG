package geology;

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

	public NativeLayer(int size) 
	{

		this.size = size;

		A = new double[size][size];
		B = new double[size][size];
		
		A = new NoiseMap(size).getResult();
		B = new NoiseMap(size).getResult();
		
		data = new int[size][size];
		populateLayer("worlds/world" , "NativeLayer01");

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
				
				boolean goldConstraint = -(0.25*pointDataA)-(0.25*pointDataB) > -0.44;
				boolean silverConstraint = (-(0.25*pointDataA)-(0.25*pointDataB) <= -0.44) && ((0.5*pointDataA)+(0.5*pointDataB) > 0.75);
				boolean copperConstraint = ((0.5*pointDataA)+(0.5*pointDataB) <= 0.75) && ((0.9*pointDataA)+(0.9*pointDataB) > 0.81);
				boolean ironConstraint = (0.9*pointDataA)+(0.9*pointDataB) <= 0.81 && ((0.3*pointDataA)+(0.3*pointDataB) > 0.09);
				boolean sulphurConstraint = ((0.3*pointDataA)+(0.3*pointDataB) <= 0.09);
								
				if (sulphurConstraint == true)
				{

					data[x][y] = 4;
					image.setRGB(x, y, SULPHUR_COLOUR);

				}
				else if (ironConstraint == true)
				{

					data[x][y] = 3;
					image.setRGB(x, y, IRON_COLOUR);

				}
				else if (copperConstraint == true)
				{

					data[x][y] = 2;
					image.setRGB(x, y, COPPER_COLOUR);

				}
				else if (silverConstraint == true)
				{

					data[x][y] = 1;
					image.setRGB(x, y, SILVER_COLOUR);

				}
				else if (goldConstraint == true)
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

}
