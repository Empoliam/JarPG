package world.CC;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class RegionLabel 
{

	int size;
	List<Label> labels = new ArrayList<Label>();
	int[][] inputData;
	int[][] outputLabels;
	int currentLabel = 0;
	
	public RegionLabel(int[][] datain, int size)
	{
		
		this.size = size;
		this.inputData = datain;
		
		outputLabels = new int[size][size];
		
		outputLabels[0][0] = currentLabel;
		labels.add(new Label(currentLabel));
		
		for(int y = 0; y < size; y ++)
		{
						
			for(int x = 1; x < size; x++)
			{
								
				int currentType = inputData[x][y];
				
				boolean north = true, west = true;
				int northval = 0, westval = 0;
				
				try { northval = inputData[x][y-1];	}
				catch(ArrayIndexOutOfBoundsException e){ north = false; }
				try { westval = inputData[x-1][y];	}
				catch(ArrayIndexOutOfBoundsException e){ west = false; }
								
				if(west == true && currentType == westval)
				{
					
					outputLabels[x][y] = outputLabels[x-1][y];
					if(north == true && currentType == northval)
					{
												
						labels.get(outputLabels[x][y-1]).addNeigbour(outputLabels[x][y]);
						for(Integer n : labels.get(outputLabels[x][y-1]).getAllNeighbours())
						{
							
							labels.get(n).addNeigbour(outputLabels[x][y]);
							labels.get(n).addNeigbours(labels.get(outputLabels[x][y-1]).getAllNeighbours());
							System.out.println(n);
							
						}
											
					}
					
				}
				else if(north == true && currentType == northval)
				{
					
					outputLabels[x][y] = outputLabels[x][y-1];
					
				}
				else
				{
					
					currentLabel++;
					labels.add(new Label(currentLabel));
					outputLabels[x][y] = currentLabel;
					
				}
								
			}
			
		}
		
		for(Label l : labels)
		{
			
			l.findLowestNeighbour();
			
		}
		
		for(int y = 0; y < size; y ++)
		{
			
			for(int x = 0; x < size; x ++)
			{
				
				outputLabels[x][y] = labels.get(outputLabels[x][y]).getLowestNeighbour();
				
			}
			
		}
		
		draw();
		
	}
	
	public int[][] getLabels()
	{
		
		return outputLabels;
		
	}
	
	private void draw()
	{
		
		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		
		for(int y = 0; y < size; y++)
		{
			
			for(int x = 0; x < size; x ++)
			{
				
				img.setRGB(x, y, outputLabels[x][y] * outputLabels[x][y] * outputLabels[x][y]);
				
			}
			
		}
		
		File f = new File("worlds/world/labels.bmp");
		try { ImageIO.write(img, "BMP", f); }
		catch(IOException e){};
		
	}
	
}
