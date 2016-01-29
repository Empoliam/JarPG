package gui;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import net.miginfocom.swing.MigLayout;
import utilities.GeologySample;

import static main.Main.player;

public class DigWindow extends JFrame 
{

	private static final long serialVersionUID = 1L;
	
	JPanel mainPane = new JPanel();
	
	JPanel layerPane = new JPanel();
	JLabel layerMap = new JLabel(new GeologySample().getIcon());
	
	JPanel select = new JPanel();
	JButton bDig = new JButton("Dig");
	JButton bDone = new JButton("Done");
	JMenu mLayer = new JMenu();
	ButtonGroup bLayers = new ButtonGroup();
	JRadioButtonMenuItem rAny = new JRadioButtonMenuItem("Any");
	JRadioButtonMenuItem rOrganic = new JRadioButtonMenuItem("Organic");
	JRadioButtonMenuItem rSediment = new JRadioButtonMenuItem("Sediment");
	JRadioButtonMenuItem rNative = new JRadioButtonMenuItem("Native");
	
	public DigWindow()
	{
		
		mainPane.setLayout(new MigLayout());
		select.setLayout(new MigLayout());
				
		bLayers.add(rAny);
		bLayers.add(rOrganic);
		bLayers.add(rSediment);
		bLayers.add(rNative);
		
		select.add(bDig, "align center, wrap");
		select.add(rAny,"wrap");
		select.add(rOrganic,"wrap");
		select.add(rSediment,"wrap");
		select.add(rNative,"wrap");
		
		rAny.setSelected(true);
		
		if(player.mining < 10)
		{
			
			rOrganic.setEnabled(false);
			rSediment.setEnabled(false);
			rNative.setEnabled(false);
			select.add(new JLabel("<html><div style=\"text-align:center;\">You aren't skilled enough <br> to dig a certain layer yet</div></html>"), "align center");
			
		}
				
		select.setBorder(BorderFactory.createEtchedBorder());
		
		layerPane.add(layerMap);
		
		mainPane.add(select);
		mainPane.add(layerPane,"wrap");
		mainPane.add(bDone, "align center");
		
		add(mainPane);
		
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
}
