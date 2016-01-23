package gui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import unit.Player;
import world.Region;
import world.SuperRegion;
import world.geology.Rock;
import items.Item;

public class MainWindow extends JFrame
{

	private static final long serialVersionUID = 1L;

	String PATH;
	int WORLD_SIZE;

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	//various constants
	boolean debug;
	int[] spawn = new int[2];

	//variables
	int currentx, currentX;
	int currenty, currentY;

	//active stuff
	Player player;
	SuperRegion activeSuperRegion;
	Region activeRegion;

	//UI
	JPanel mainpanel = new JPanel();
	JTextArea textarea = new JTextArea(16,50);
	JTextField inputfield = new JTextField();
	JScrollPane scroll = new JScrollPane(textarea);
	JButton send = new JButton("Send");
	JButton save = new JButton("Save");
	JButton inv = new JButton("Inventory");
	JButton stat = new JButton("Status");
	JButton map = new JButton("Map");

	ActionListener aMap = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			new MapWindow(PATH, currentx, currenty);

		}
	};

	ActionListener aSend = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			String command = inputfield.getText();
			textarea.append(">: " + command + "\n");
			inputfield.setText("");
			command(command);

		}
	};

	ActionListener aSave = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(PATH + "/player.dat"));
				os.writeObject(player);
				os.close();
				textarea.append("Save successful.");
			} catch (IOException e1) {
				e1.printStackTrace();
				textarea.append("Save failed.");
			}

		}
	};

	ActionListener aInv = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			new InventoryWindow(player.inventory);

		}
	};

	public MainWindow(String PATH, int WORLD_SIZE)
	{

		super("JarPG");

		this.PATH = PATH;
		this.WORLD_SIZE = WORLD_SIZE;

		textarea.setEditable(false);
		textarea.setLineWrap(true);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		mainpanel.setLayout(new MigLayout());

		mainpanel.add(scroll,"push,grow,wrap");
		mainpanel.add(send, "split 2");
		mainpanel.add(inputfield, "pushx,growx,wrap");
		mainpanel.add(save,"split 4,pushx,align center");
		mainpanel.add(inv, "pushx");
		mainpanel.add(stat, "pushx");
		mainpanel.add(map, "pushx");

		save.addActionListener(aSave);
		inv.addActionListener(aInv);
		stat.setEnabled(false);
		map.addActionListener(aMap);

		send.addActionListener(aSend);
		inputfield.addActionListener(aSend);

		add(mainpanel);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		loadPlayer();
		currentx = player.getX();
		currenty = player.getY();
		loadRegion();

	}

	private void loadPlayer()
	{

		try {

			ObjectInputStream is = new ObjectInputStream(new FileInputStream(PATH + "/player.dat"));
			player = (Player) is.readObject();
			is.close();

		}
		catch (FileNotFoundException e) {  e.printStackTrace();	textarea.append("Failed to load save. Please restart.\n");}
		catch (IOException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }

	}

	private void loadRegion()
	{

		currentX = (int) Math.floor(currentx/40);
		currentY = (int) Math.floor(currenty/40);

		try {

			ObjectInputStream is = new ObjectInputStream(new FileInputStream(PATH + "/regions/" + currentX + "-" + currentY + ".region"));
			activeSuperRegion = (SuperRegion) is.readObject();
			is.close();
			activeRegion = activeSuperRegion.getTile((currentx-(currentX*40)),(currenty-(currentY*40)));

		} 
		catch (FileNotFoundException e) {  e.printStackTrace();	}
		catch (IOException e) 
		{ 
			e.printStackTrace();
			textarea.append("Save out of date and no longer compatible. Please create a new world.");
		}
		catch (ClassNotFoundException e) { e.printStackTrace(); }

	}

	private void command(String in)
	{

		in = in.toLowerCase();
		String[] command = in.split(" ");

		switch(command[0])
		{

		case "move" :
			move(in);
			break;
		case "dig" :
			dig(in);
			break;
		default :
			textarea.append("Command not recognised.\n");
			break;

		}

	}

	/*	0: north
	 *	1: west
	 *	2: south
	 *	3: east
	 */
	private void move(String in)
	{

		String reverse = null; 

		try
		{

			String direction = in.split(" ")[1];

			switch(direction)
			{

			case "north":
				if(currenty != 0)
				{				
					currenty --;
					textarea.append("You walk north.\n");
					reverse = "south";
				}
				else textarea.append("You can go no further in this direction.\n");
				break;
			case "west":
				if(currentx != 0)
				{
					currentx --;	
					textarea.append("You walk west.\n");
					reverse = "east";
					break;
				}
				else textarea.append("You can go no further in this direction.\n");
				break;
			case "south":
				if(currenty != WORLD_SIZE-1)
				{				
					currenty ++;	
					textarea.append("You walk south.\n");
					reverse = "north";
				}
				else textarea.append("You can go no further in this direction.\n");
				break;
			case "east":
				if(currentx != WORLD_SIZE-1)
				{
					textarea.append("You walk east.\n");
					currentx ++;				
					reverse = "west";
				}
				else textarea.append("You can go no further in this direction.\n");
				break;
			default:
				textarea.append("'" + direction + "'" + " is not a direction.\n");

			}
			loadRegion();	
			if(activeRegion.getBiome() == -1)
			{
				textarea.append("You find an expanse of water, and can proceed no further. ");
				move(reverse);
			}

			player.setXY(currentx, currenty);

		}
		catch(ArrayIndexOutOfBoundsException e)
		{

			textarea.append("Format unrecognised. Use 'move [direction]'.\n");

		}

	}

	private void dig(String in)
	{

		Rock mined = null;
		boolean hit = true;

		try
		{

			String layer = in.split(" ")[1];

			if(player.mining >= 5 && !layer.equals("any"))
			{

				textarea.append("You dig into the " + layer + " layers.\n");

				switch(layer)
				{

				case "sediment": 
					mined = activeRegion.getRock("sediment", new Random().nextInt(3));
					break;
				case "native":
					mined = activeRegion.getRock("native", new Random().nextInt(2));
					break;
				case "organic":
					mined = activeRegion.getRock("organics", new Random().nextInt(2));
					break;
				default:
					textarea.append("Such a layer does not exist.\n");
					hit = false;
					break;
				}

			}
			else
			{

				int ranLayer = new Random().nextInt(3);

				switch(ranLayer)
				{

				case 0: 
					mined = activeRegion.getRock("sediment", new Random().nextInt(3));
					break;
				case 1:
					mined = activeRegion.getRock("native", new Random().nextInt(2));
					break;
				case 2:
					mined = activeRegion.getRock("organics", new Random().nextInt(2));

				}

			}

			if(hit == true)
			{
				textarea.append("You strike " + mined.name + "!\n");
				player.addItem(new Item(mined.yeild));
			}

		}

		catch(ArrayIndexOutOfBoundsException e)
		{

			textarea.append("Format unrecognised. Use 'dig [sediment/native/organic/any]'.\n");

		}

	}

}
