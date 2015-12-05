import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import level.World;
import net.miginfocom.swing.MigLayout;

public class WorldWindow extends JDialog
{

	private static final long serialVersionUID = -6672888944649399921L;

	String[] TEMPERATURES = { "Very Cold", "Cold", "Average", "Hot", "Very Hot" };
	
	int worldSize;
	int nContinents;
	boolean poles;
	boolean beaches;
	World world;	
	int nGen;
	int sTemp;
	int mountains;
	
	JPanel panel = new JPanel();
	
	JLabel imgPane = new JLabel();
	
	JLabel lWorldSize = new JLabel("World size (n*x): ");
	JLabel lContinents = new JLabel("Number of continents: ");
	JLabel lPoles = new JLabel("Generate poles?");
	JLabel lGen = new JLabel("Number of generation passes: ");
	JLabel lBeach = new JLabel("Generate beaches?");
	JLabel lTemperature = new JLabel("Temperature: ");
	JLabel lMountains = new JLabel ("Number of mountains: ");
	
	JTextField fWorldSize = new JTextField(4);
	JTextField fContinents = new JTextField(4);
	JTextField fGen = new JTextField(4);
	JTextField fMountains = new JTextField(4);
	
	JCheckBox cPoles = new JCheckBox();
	JCheckBox cBeach = new JCheckBox();
	
	JComboBox<String> bTemperature = new JComboBox<>(TEMPERATURES);
	
	JButton newWorld = new JButton("Generate new");
	JButton useWorld = new JButton("Use this world");
	
	JScrollPane mapScroll = new JScrollPane(imgPane);
	
	ActionListener aNewWorld = new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
						
			worldSize = Integer.parseInt(fWorldSize.getText());
			nContinents = Integer.parseInt(fContinents.getText());
			nGen = Integer.parseInt(fGen.getText()); 
			poles = cPoles.isSelected();
			beaches = cBeach.isSelected();
			sTemp = bTemperature.getSelectedIndex();
			mountains = Integer.parseInt(fMountains.getText());
			
			world = new World(worldSize,nContinents,nGen,sTemp,poles,beaches,mountains);
			world.imgOut();
						
			try 
			{
				BufferedImage img = ImageIO.read(new File("map.bmp"));
				ImageIcon icon = new ImageIcon(img);
				imgPane.setIcon(icon);
				pack();
			} 
			catch (IOException e1){e1.printStackTrace();}
						
		}
	};
	
	ActionListener aUseWorld = new ActionListener(
			) {
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {FileUtils.deleteDirectory(new File("world"));} 
			catch (IOException e1) {e1.printStackTrace();}
			saveWorld();
			setVisible(false);
			
		}
	};
	
	WorldWindow()
	{
		
		panel.setLayout(new MigLayout());
		
		panel.add(lWorldSize,"align right, push");
		panel.add(fWorldSize,"align left, push, wrap");
		panel.add(lContinents,"align right, push");
		panel.add(fContinents,"align left, push, wrap");
		panel.add(lGen,"align right, push");
		panel.add(fGen,"align left, push, wrap");
		panel.add(lMountains,"align right, push");
		panel.add(fMountains,"align left, push, wrap");
		panel.add(lPoles, "align right, push");
		panel.add(cPoles, "align left, push, wrap");
		panel.add(lBeach, "align right, push");
		panel.add(cBeach, "align left, push, wrap");
		panel.add(lTemperature, "align right, push");
		panel.add(bTemperature, "align left, push, wrap");
		
		panel.add(mapScroll, "span 2, align center, wrap");
		mapScroll.setMaximumSize(new Dimension(600,600));
		mapScroll.getVerticalScrollBar().setUnitIncrement(20);
		mapScroll.getHorizontalScrollBar().setUnitIncrement(20);
		
		panel.add(newWorld, "align left");
		panel.add(useWorld, "wrap, align right");
		
		fWorldSize.setText("400");
		fContinents.setText("6");
		fGen.setText("50");
		fMountains.setText("5");
		cBeach.setSelected(true);
		cPoles.setSelected(true);
		bTemperature.setSelectedIndex(2);
		
		add(panel);
		
		newWorld.addActionListener(aNewWorld);
		useWorld.addActionListener(aUseWorld);
		
		pack();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		setVisible(true);
		
	}
	
	private void saveWorld()
	{
		
		world.generateJSON();
		
	}
	
}
