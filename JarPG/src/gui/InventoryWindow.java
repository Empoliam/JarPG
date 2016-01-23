package gui;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import items.Item;
import net.miginfocom.swing.MigLayout;

public class InventoryWindow extends JDialog 
{
	
	private static final long serialVersionUID = 1L;
	
	JPanel mainPane = new JPanel();
	
	InventoryWindow(List<Item> in)
	{
		
		mainPane.setLayout(new MigLayout());
		
		for(Item i : in)
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
