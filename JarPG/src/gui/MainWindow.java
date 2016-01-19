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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import unit.Player;
import world.Region;
import world.SuperRegion;

public class MainWindow extends JFrame
{

	private static final long serialVersionUID = -3241273755265444600L;
	
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
	JButton log = new JButton("Log");
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
		mainpanel.add(log,"split 4,pushx,align center");
		mainpanel.add(inv, "pushx");
		mainpanel.add(stat, "pushx");
		mainpanel.add(map, "pushx");

		log.setEnabled(false);
		inv.setEnabled(false);
		stat.setEnabled(false);
		map.addActionListener(aMap);
		send.addActionListener(aSend);
		
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
			
			BufferedReader r = new BufferedReader(new FileReader(PATH + "/player.json"));
			player = gson.fromJson(r, Player.class);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private void loadRegion()
	{
				
		currentX = (int) Math.floor(currentx/40);
		currentY = (int) Math.floor(currenty/40);
							
		try {
			
			BufferedReader r = new BufferedReader(new FileReader(PATH + "/regions/" + currentX + "-" + currentY + ".json"));
			activeSuperRegion = gson.fromJson(r, SuperRegion.class);
			activeRegion = activeSuperRegion.getTile((int)Math.floor(currentx/10),(int) Math.floor(currenty/10));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		
		}
		
	}
	
	private void command(String in)
	{
		
		in = in.toLowerCase();
		String[] command = in.split(" ");
		
		switch(command[0])
		{
		
		case "move" :
			move(command[1]);
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
	private void move(String direction)
	{
		
		String reverse = null; 
		
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
			textarea.append("'" + direction + "'" + " is not a direction.");
			
		}
		loadRegion();	
		if(activeRegion.getBiome() == -1)
		{
			textarea.append("You find an expanse of water, and can proceed no further. ");
			move(reverse);
		}
		
	}
	
}
