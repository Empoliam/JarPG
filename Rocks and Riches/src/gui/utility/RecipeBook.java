package gui.utility;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import items.Item;
import items.SmeltingRecipeSingle;
import net.miginfocom.swing.MigLayout;

import static main.Smelt.singleSmelting;

public class RecipeBook extends JFrame 
{

	private static final long serialVersionUID = 1L;

	JPanel mainPane = new JPanel();

	JTabbedPane tabbedPane = new JTabbedPane();

	JPanel smeltingPane = new JPanel();
	JScrollPane smeltScroll = new JScrollPane(smeltingPane);

	public RecipeBook()
	{

		mainPane.setLayout(new MigLayout());
		smeltingPane.setLayout(new MigLayout());

		for(SmeltingRecipeSingle s : singleSmelting)
		{

			smeltingPane.add(new JLabel(s.nQuantity + " " + new Item(s.requires).name));
			smeltingPane.add(new JLabel(">>>"),"gapleft 40, gapright 40");
			smeltingPane.add(new JLabel(s.nYeild + " " + new Item(s.yeild).name),"wrap");

		}

		tabbedPane.add("Smelting", smeltScroll);
		
		String pref = "width pref+" + smeltScroll.getVerticalScrollBar().getPreferredSize().width + "px";		
		
		mainPane.add(tabbedPane,"height max(400),pushx,growx," + pref);
		add(mainPane);


		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

}
