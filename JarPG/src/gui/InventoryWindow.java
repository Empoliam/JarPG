package gui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import items.Item;
import net.miginfocom.swing.MigLayout;

import static main.Main.player;

public class InventoryWindow extends JDialog 
{
	
	private static final long serialVersionUID = 1L;
	
	JPanel mainPane = new JPanel();
	
	InventoryWindow()
	{
		
		setTitle("Inventory");
		
		mainPane.setLayout(new MigLayout());
		
		for(Item i : player.inventory)
		{
			
			mainPane.add(new JLabel(i.stacksize + " " + i.name),"wrap");
			
		}
		
		add(mainPane);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
}
