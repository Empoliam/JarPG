package gui.utility;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import items.Item;
import net.miginfocom.swing.MigLayout;

import static main.Main.player;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryWindow extends JDialog 
{

	private static final long serialVersionUID = 1L;

	JPanel mainPane = new JPanel();
	JPanel inventoryPane = new JPanel();

	JScrollPane itemScroll = new JScrollPane(inventoryPane);

	JButton bClose = new JButton("Done");

	ActionListener aQuit = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) 
		{

			dispose();

		}
	};

	ActionListener aDrop = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			int index = Integer.parseInt(e.getActionCommand());
			player.removeItem(player.inventory.get(index),1);
			inventoryPane.removeAll();
			initializeTable();
			inventoryPane.revalidate();
			inventoryPane.repaint();

		}
	};

	public InventoryWindow()
	{

		setTitle("Inventory");
		setMaximumSize(new Dimension(1028,500));

		mainPane.setLayout(new MigLayout());

		inventoryPane.setLayout(new MigLayout());
		itemScroll.setPreferredSize(new Dimension(512,250));
		initializeTable();

		mainPane.add(itemScroll,"growy,push,wrap");
		mainPane.add(bClose, "align center");

		bClose.addActionListener(aQuit);

		add(mainPane);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private void initializeTable()
	{

		inventoryPane.add(new JLabel("Quantity"),"pushx,alignx center");
		inventoryPane.add(new JLabel("Item"),"pushx,alignx center");
		inventoryPane.add(new JLabel("Quality"),"pushx,alignx center");
		inventoryPane.add(new JLabel("Weight"),"pushx,alignx center");
		inventoryPane.add(new JLabel("Basic Value"),"pushx,alignx center");
		inventoryPane.add(new JLabel("Total Value"),"wrap, pushx,alignx center");
		inventoryPane.add(new JSeparator(),"span 7, growx, wrap");

		for(Item i : player.inventory)
		{

			JButton dButton = new JButton("Drop");
			dButton.setActionCommand(Integer.toString(player.inventory.indexOf(i)));
			dButton.addActionListener(aDrop);

			inventoryPane.add(new JLabel(Integer.toString(i.stacksize)),"alignx center");
			inventoryPane.add(new JLabel(i.name),"alignx center");
			inventoryPane.add(new JLabel(i.prefix),"alignx center"); 
			inventoryPane.add(new JLabel(Integer.toString(i.baseweight * i.stacksize)),"alignx center"); 
			inventoryPane.add(new JLabel("¤" + Integer.toString(i.basevalue)),"alignx center"); 
			inventoryPane.add(new JLabel("¤" + Integer.toString(i.basevalue*i.stacksize)),"alignx center");
			inventoryPane.add(dButton,"wrap , alignx center");

		}

	}
}

