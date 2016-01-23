package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class MapWindow extends JDialog
{

	private static final long serialVersionUID = 1L;

	String PATH;
	int x, y;
	
	JPanel mainPane = new JPanel();
	JLabel mapPane = new JLabel();
	
	public MapWindow(String PATH, int x, int y)
	{

		this.PATH = PATH;
		this.x = x;
		this.y = y;
		
		mainPane.setLayout(new MigLayout());

		drawPlayer();

		mainPane.add(mapPane);

		add(mainPane);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setModal(true);

	}

	private void drawPlayer()
	{

		BufferedImage mapImg = null;

		try {

			mapImg = ImageIO.read(new File(PATH + "/map.bmp"));			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Graphics2D g =  mapImg.createGraphics();
		g.setColor(new Color(255,0,255));
		g.drawOval(x-1, y-1, 2, 2);
		
		ImageIcon mapIcon = new ImageIcon(mapImg);
		mapPane.setIcon(mapIcon);

	}

}
