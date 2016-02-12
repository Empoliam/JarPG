package gui;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import main.Dig;
import net.miginfocom.swing.MigLayout;
import utilities.GeologySample;

import static main.Main.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DigWindow extends JFrame 
{

	private static final long serialVersionUID = 1L;

	JPanel mainPane = new JPanel();

	JPanel layerPane = new JPanel();
	JLabel layerMap = new JLabel(new GeologySample().getIcon());

	JPanel selectPane = new JPanel();
	JButton bDig = new JButton("Dig");
	JButton bDone = new JButton("Done");
	ButtonGroup bLayers = new ButtonGroup();
	JRadioButtonMenuItem rAny = new JRadioButtonMenuItem("Any");
	JRadioButtonMenuItem rOrganic = new JRadioButtonMenuItem("Organic");
	JRadioButtonMenuItem rSediment = new JRadioButtonMenuItem("Sediment");
	JRadioButtonMenuItem rNative = new JRadioButtonMenuItem("Native");
	JRadioButtonMenuItem rOre = new JRadioButtonMenuItem("Ore");

	ActionListener aDig = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if(rAny.isSelected()) Dig.digRandom();
			else if(rOrganic.isSelected()) Dig.digSpecific("Organic");		
			else if(rSediment.isSelected()) Dig.digSpecific("Sediment");
			else if(rNative.isSelected()) Dig.digSpecific("Native");
			else if(rOre.isSelected()) Dig.digSpecific("Ore");

		}
	};

	ActionListener aExit = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			dispose();

		}
	};

	public DigWindow()
	{

		mainPane.setLayout(new MigLayout());
		selectPane.setLayout(new MigLayout());

		bLayers.add(rAny);
		bLayers.add(rOrganic);
		bLayers.add(rSediment);
		bLayers.add(rNative);
		bLayers.add(rOre);

		selectPane.add(bDig, "align center, wrap");
		selectPane.add(rAny,"wrap");
		selectPane.add(rOrganic,"wrap");
		selectPane.add(rSediment,"wrap");
		selectPane.add(rNative,"wrap");
		selectPane.add(rOre,"wrap");

		rAny.setSelected(true);
		bDig.addActionListener(aDig);

		if(player.mining < 5) 
		{

			layerPane.add(new JLabel("<html><div style=\"text-align:center;\">You aren't skilled enough <br> to take a sample</div></html>"), "align center");

		}
		else
		{

			layerPane.add(layerMap,"align center");
						
		}

		if(player.mining < 10)
		{

			rOrganic.setEnabled(false);
			rSediment.setEnabled(false);
			rNative.setEnabled(false);
			rOre.setEnabled(false);
			selectPane.add(new JLabel("<html><div style=\"text-align:center;\">You aren't skilled enough <br> to dig a certain layer yet</div></html>"), "align center");

		}

		selectPane.setBorder(BorderFactory.createEtchedBorder());
		layerPane.setBorder(BorderFactory.createEtchedBorder());

		mainPane.add(selectPane);
		mainPane.add(layerPane, "wrap");
		mainPane.add(bDone, "align center");

		bDone.addActionListener(aExit);

		add(mainPane);

		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

	}

}
