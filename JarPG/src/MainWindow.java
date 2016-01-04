import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;

import unit.*;

public class MainWindow extends JFrame
{

	private static final long serialVersionUID = -3241273755265444600L;
	
	String PATH;
	
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

	//various constants
	Player player;
	boolean debug;
	int[] spawn = new int[2];
	
	//active entities
	
	public static void main(String[] args) 
	{

		new MainWindow();

	}

	MainWindow()
	{

		super("JarPG");

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
		map.setEnabled(false);

		add(mainpanel);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		setVisible(true);

		playgame();

	}

	public void playgame()
	{
		
		makePlayer();
		WorldWindow createWorld = new WorldWindow(this);
		createWorld.dispose();
		
	}
	
	private void makePlayer()
	{
		
		CreateWindow create = new CreateWindow(this);
		player = create.generate();
		debug = create.Debug();
		create.dispose();
		textarea.append("Created player: " + player.getFName() + " " + player.getLName() + "\n");
		if(debug) textarea.append("Debug Mode Enabled");
		
	}

}
