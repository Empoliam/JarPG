import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.google.gson.Gson;

import level.Region;

import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;

import unit.*;

public class MainWindow extends JFrame implements ActionListener
{

	private static final long serialVersionUID = -3241273755265444600L;
	
	//UI
	JPanel mainpanel = new JPanel();
	JTextArea textarea = new JTextArea(16,50);
	JTextField inputfield = new JTextField();
	JScrollPane scroll = new JScrollPane(textarea);
	JButton send = new JButton("Send");
	JButton log = new JButton("Log");
	JButton inv = new JButton("Inventory");
	JButton stat = new JButton("Status");

	//various constants
	Player player;
	boolean debug;
	int[] spawn = new int[2];
	
	//active entities
	Region activeRegion;
	
	public static void main(String[] args) 
	{

		new MainWindow();

	}

	MainWindow()
	{

		super("JarPG");

		send.addActionListener(this);

		textarea.setEditable(false);
		textarea.setLineWrap(true);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		mainpanel.setLayout(new MigLayout());

		mainpanel.add(scroll,"push,grow,wrap");
		mainpanel.add(send, "split 2");
		mainpanel.add(inputfield, "pushx,growx,wrap");
		mainpanel.add(log,"split 3,pushx,align center");
		mainpanel.add(inv, "pushx");
		mainpanel.add(stat, "pushx");

		log.setEnabled(false);
		inv.setEnabled(false);
		stat.setEnabled(false);

		add(mainpanel);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		setVisible(true);

		playgame();

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{

		String text = inputfield.getText();

		if(text.length() != 0)
		{

			textarea.append(text + "\n");
			inputfield.setText("");

		}

	}

	public void playgame()
	{

		/*
		CreateWindow create = new CreateWindow(this);
		player = create.generate();
		debug = create.Debug();
		create.dispose();
		textarea.append("Created player: " + player.getFName() + " " + player.getLName() + "\n");
		if(debug) textarea.append("Debug Mode Enabled");
		*/
		
		WorldWindow worldGen = new WorldWindow();
		spawn = worldGen.getSpawn();
		worldGen.dispose();
		loadTile(spawn[0], spawn[1]);
		
	}
	
	private void loadTile(int x, int y)
	{
		
		BufferedReader br;
		try {
			
			br = new BufferedReader(new FileReader(x + "-" + y +".json"));
			Gson gson = new Gson();
			activeRegion = gson.fromJson(br,Region.class);
			
		} 
		catch (FileNotFoundException e) 
		{
		
			textarea.append("Failed to load world. Please restart \n");
			
		}
				
	}


}
