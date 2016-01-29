package utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import static main.Main.*;

public class GeologySample 
{

	private ImageIcon sampleIcon;
	
	public GeologySample()
	{
		
		BufferedImage GeologyImg = new BufferedImage(60, 60, BufferedImage.TYPE_INT_RGB);

		
		
		Graphics2D g =  GeologyImg.createGraphics();
		g.setColor(Color.decode(activeRegion.getRock("organics", 0).colour));
		g.fillRect(0, 0, 20, 20);
		g.setColor(Color.decode(activeRegion.getRock("organics", 1).colour));
		g.fillRect(20, 0, 20, 20);
		g.setColor(Color.decode(activeRegion.getRock("sediment", 0).colour));
		g.fillRect(00, 20, 20, 20);
		g.setColor(Color.decode(activeRegion.getRock("sediment", 1).colour));
		g.fillRect(20, 20, 20, 20);
		g.setColor(Color.decode(activeRegion.getRock("sediment", 2).colour));
		g.fillRect(40, 20, 20, 20);
		g.setColor(Color.decode(activeRegion.getRock("native", 0).colour));
		g.fillRect(20, 40, 20, 20);
		g.setColor(Color.decode(activeRegion.getRock("native", 1).colour));
		g.fillRect(20, 40, 20, 20);
		
		sampleIcon = new ImageIcon(GeologyImg);
		
		activeRegion.getRock("organics", 0);
		
	}
	
	public ImageIcon getIcon()
	{
		
		return sampleIcon;
		
	}
	
}
