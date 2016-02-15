package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import items.Item;
import items.SmeltingRecipeSingle;
import net.miginfocom.swing.MigLayout;

import static main.Smelt.singleSmelting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static main.Main.player;

public class SmeltingWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	Item currentIn;
	Item currentOut;
	SmeltingRecipeSingle currentRecipe;
	
	JPanel mainPane = new JPanel();
	JPanel inventoryList = new JPanel();
	JScrollPane inventoryPane = new JScrollPane(inventoryList);
	
	JPanel craftingPane = new JPanel();
	JLabel lIn = new JLabel();
	JLabel lOut = new JLabel();
	JButton bSmelt = new JButton("Smelt");
	
	JButton bExit = new JButton("Close");
	
	ActionListener aExit = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			
		}
	};
	
	ActionListener aAdd = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			currentIn = player.inventory.get(Integer.parseInt(e.getActionCommand()));
			
			lIn.setText(currentIn.name);
			
			for(SmeltingRecipeSingle s : singleSmelting)
			{
				
				if (currentIn.id == s.requires)
				{
					
					currentRecipe = s;
					currentOut = new Item(s.yeild);
					currentOut.stacksize = s.nYeild;
					lOut.setText(currentOut.name);
					break;
					
				}
				
			}
			
			bSmelt.setEnabled(true);
			craftingPane.repaint();
			craftingPane.revalidate();
			
		}
	};
	
	ActionListener aSmelt = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			player.removeItem(currentIn,currentRecipe.nQuantity);
			player.addItem(currentOut,currentRecipe.nYeild);
			
			if(currentIn.stacksize == 0)
			{
				lIn.setText("");
				lOut.setText("");
				bSmelt.setEnabled(false);
				
			}
			
			getInventory();
			inventoryList.revalidate();
			inventoryList.repaint();
			
		}
	};
	
	public SmeltingWindow()
	{
		
		mainPane.setLayout(new MigLayout());
		inventoryList.setLayout(new MigLayout());
		craftingPane.setLayout(new MigLayout());
		
		getInventory();
		
		craftingPane.add(new JLabel("Input:"));
		craftingPane.add(lIn,"wrap");
		craftingPane.add(new JLabel("Result:"));
		craftingPane.add(lOut,"wrap");
		craftingPane.add(bSmelt,"span 2, alignx center");
		
		bSmelt.setEnabled(false);
		bSmelt.addActionListener(aSmelt);
		
		mainPane.add(inventoryPane,"grow,push,wrap,height max(256),width min(256)");
		mainPane.add(craftingPane, "wrap,alignx center");
		mainPane.add(bExit,"alignx center");
		
		bExit.addActionListener(aExit);
		
		add(mainPane);
				
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	private void getInventory()
	{
		
		inventoryList.removeAll();
		
		for(Item i : player.inventory)
		{
			
			for(SmeltingRecipeSingle s : singleSmelting)
			{
				
				if(i.id == s.requires && i.stacksize >= s.nQuantity)
				{
					
					JButton button = new JButton("Add");
					button.setActionCommand(Integer.toString(player.inventory.indexOf(i)));
					button.addActionListener(aAdd);
					
					inventoryList.add(new JLabel(Integer.toString(i.stacksize)),"pushx,alignx center");
					inventoryList.add(new JLabel(i.name),"pushx,alignx center");
					inventoryList.add(button,"wrap, pushx,alignx center");
					
				}
				
			}
			
		}
		
	}
	
}
