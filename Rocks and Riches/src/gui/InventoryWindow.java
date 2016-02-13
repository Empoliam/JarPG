package gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import items.Item;
import net.miginfocom.swing.MigLayout;

import static main.Main.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryWindow extends JDialog 
{

	private static final long serialVersionUID = 1L;

	JPanel mainPane = new JPanel();

	DefaultTableModel model = new DefaultTableModel();
	JTable itemTable = new JTable(model)
	{

		private final static long serialVersionUID = 1l;

		@Override
		public boolean isCellEditable(int row, int column)
		{

			return false;

		}

	};

	JScrollPane itemScroll = new JScrollPane(itemTable);

	JButton bClose = new JButton("Done");

	ActionListener aQuit = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) 
		{

			dispose();

		}
	};

	InventoryWindow()
	{

		setTitle("Inventory");

		mainPane.setLayout(new MigLayout());

		initializeTable();

		mainPane.add(itemScroll,"grow,pushx,wrap");
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

		model.addColumn("Quantity");
		model.addColumn("Item");
		model.addColumn("Quality");
		model.addColumn("Weight");
		model.addColumn("Basic Value");
		model.addColumn("Total Value");

		for(Item i : player.inventory)
		{

			model.addRow(new Object[] { i.stacksize, i.name, i.prefix, i.baseweight * i.stacksize, i.basevalue, (i.basevalue*i.stacksize) });

		}

	}

}
