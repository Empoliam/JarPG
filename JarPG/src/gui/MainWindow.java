package gui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import main.Main;

public class MainWindow extends JFrame
{

	private static final long serialVersionUID = 1L;

	//UI
	JPanel mainpanel = new JPanel();
	public JTextArea textarea = new JTextArea(16,50);
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

			new MapWindow(Main.PATH, Main.currentx, Main.currenty);

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

			Main.savePlayer();

		}
	};

	ActionListener aInv = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			new InventoryWindow(Main.player.inventory);

		}
	};

	public MainWindow()
	{

		super("JarPG");

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


	}

	private void command(String in)
	{

		in = in.toLowerCase();
		String[] command = in.split(" ");

		switch(command[0])
		{

		case "move" :
			Main.move(in);
			break;
		case "dig" :
			Main.dig(in);
			break;
		default :
			textarea.append("Command not recognised.\n");
			break;

		}

	}


}
