package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import gui.utility.InventoryWindow;
import gui.utility.MapWindow;
import gui.utility.RecipeBook;
import main.Main;
import main.Move;

import net.miginfocom.swing.MigLayout;

public class MainWindow extends JFrame
{

	private static final long serialVersionUID = 1L;

	Border etched = BorderFactory.createEtchedBorder();
	
	JPanel mainpanel = new JPanel();

	JPanel topPanel = new JPanel();
	JLabel mapLabel = new JLabel();

	JPanel movementPanel = new JPanel();
	JButton buttonUp = new JButton("↑");
	JButton buttonLeft = new JButton("←");
	JButton buttonRight = new JButton("→");
	JButton buttonDown = new JButton("↓");

	JPanel actionPanel = new JPanel();
	JButton digButton = new JButton("Dig");
	JButton invButton = new JButton("Inventory");
	JButton smeltButton = new JButton("Smelting");
	
	JPanel systemPanel = new JPanel();
	JButton saveButton = new JButton("Save");
	JButton mapButton = new JButton("Map");
	
	JPanel codexPanel = new JPanel();
	JButton recipeButton = new JButton("Recipes");
	
	public JTextArea textarea = new JTextArea(24,50);
	JScrollPane scroll = new JScrollPane(textarea);

	ActionListener aMove = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			switch(e.getActionCommand())
			{

			case "↑":
				Move.move(0);
				break;
			case "←":
				Move.move(1);
				break;
			case "↓":
				Move.move(2);
				break;
			case "→":
				Move.move(3);
				break;
			}

		}
	};

	ActionListener aAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch(e.getActionCommand())
			{
			
			case "Dig" : new DigWindow(); break;
			case "Inventory" : new InventoryWindow(); break;
			case "Map" : new MapWindow(); break;
			case "Smelting" : new SmeltingWindow(); break;
			case "Recipes" : new RecipeBook(); break;
			
			}
			
		}
	};
	
	ActionListener aSave = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(Main.PATH + "/player.dat"));
				os.writeObject(Main.player);
				os.close();
				textarea.append("Save successful.\n");
			} catch (IOException e1) {
				e1.printStackTrace();
				textarea.append("Save failed.\n");
			}

		}
	};
	
	public MainWindow()
	{

		super("JarPG");

		textarea.setEditable(false);
		textarea.setLineWrap(true);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		drawPlayer();

		mainpanel.setLayout(new MigLayout());
		topPanel.setLayout(new MigLayout());
		movementPanel.setLayout(new MigLayout());
		actionPanel.setLayout(new MigLayout());
		systemPanel.setLayout(new MigLayout());
		
		topPanel.add(scroll);
		topPanel.add(mapLabel);
		topPanel.setBorder(etched);

		//MOVEMENT
		movementPanel.add(buttonUp,"span 2, align center, wrap");
		movementPanel.add(buttonLeft,"align left");
		movementPanel.add(buttonRight, "align right,wrap");
		movementPanel.add(buttonDown, "span 2, align center");
		movementPanel.setBorder(BorderFactory.createTitledBorder(etched, "Move"));
		buttonUp.addActionListener(aMove);
		buttonLeft.addActionListener(aMove);
		buttonDown.addActionListener(aMove);
		buttonRight.addActionListener(aMove);
		
		//ACTION
		actionPanel.add(digButton,"aligny top, alignx center, wrap");
		actionPanel.add(invButton, "aligny top, alignx center, wrap");
		actionPanel.add(smeltButton, "aligny top, alignx center");
		
		invButton.addActionListener(aAction);
		digButton.addActionListener(aAction);
		smeltButton.addActionListener(aAction);
		actionPanel.setBorder(BorderFactory.createTitledBorder(etched,"Actions"));		
		
		//SYSTEM
		systemPanel.add(saveButton,"aligny top, alignx center,wrap");
		systemPanel.add(mapButton,"aligny top, alignx center");
				
		saveButton.addActionListener(aSave);
		mapButton.addActionListener(aAction);
		systemPanel.setBorder(BorderFactory.createTitledBorder(etched,"Menu"));
		
		//CODEX
		codexPanel.add(recipeButton,"aligny top, alignx center, wrap");
		
		recipeButton.addActionListener(aAction);
		codexPanel.setBorder(BorderFactory.createTitledBorder(etched,"Codex"));
		
		mainpanel.add(topPanel,"span 2,wrap");
		mainpanel.add(movementPanel,",growy");
		mainpanel.add(actionPanel,"growy,wrap");
		mainpanel.add(systemPanel,"growy,growx");		
		mainpanel.add(codexPanel,"growy,wrap");
		
		add(mainpanel);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);


	}

	public void drawPlayer()
	{
		
		BufferedImage mapImg = null;
		
		try {
			mapImg = ImageIO.read(new File(Main.PATH + "/map.bmp"));			
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("CATCH");
		}
		
		Graphics2D g =  mapImg.createGraphics();
		g.setColor(new Color(255,0,255));
		g.drawOval(Main.currentx-1, Main.currenty-1, 2, 2);

		ImageIcon mapIcon = new ImageIcon(mapImg);
		mapLabel.setIcon(mapIcon);

	}

}
