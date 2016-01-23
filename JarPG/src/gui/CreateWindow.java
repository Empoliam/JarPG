package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;
import statComponents.*;
import unit.Player;

public class CreateWindow extends JDialog
{

	private static final long serialVersionUID = 1L;
	String PATH;

	private int MAX_POINTS = 30;
	private int HP_MULT = 3;
	private int MP_MULT = 5;

	JPanel panel = new JPanel();

	String fName, lName;
	int HP, MP, x, y;

	Player player;

	JLabel head = new JLabel("Character Creation");
	JLabel fNameLabel = new JLabel("First Name: ");
	JLabel lNameLabel = new JLabel("Last Name: ");
	JLabel HPLabel = new JLabel("HP: ");
	JLabel MPLabel = new JLabel("MP: ");
	JLabel PointLabel = new JLabel("Points left: " + MAX_POINTS);

	JTextField fNameField = new JTextField(10);
	JTextField lNameField = new JTextField(10);
	STextField HPField = new STextField();
	STextField MPField = new STextField();

	SButton HPp = new SButton("+");
	SButton MPp = new SButton("+");	
	SButton HPm = new SButton("-");
	SButton MPm = new SButton("-");

	JButton go = new JButton("Go!");

	ActionListener HPa = new ActionListener() 
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			int value = Integer.parseInt(HPField.getText());

			switch( e.getActionCommand() )
			{

			case "+" :
				value = value + HP_MULT;
				break;

			case "-" :
				value = value - HP_MULT;
				break;

			}

			HPField.setText(Integer.toString(value));

		}
	};
	
	ActionListener MPa = new ActionListener() 
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			int value = Integer.parseInt(MPField.getText());

			switch( e.getActionCommand() )
			{

			case "+" :
				value = value + MP_MULT;
				break;

			case "-" :
				value = value - MP_MULT;
				break;

			}

			MPField.setText(Integer.toString(value));

		}
	};

	ActionListener GOa = new ActionListener() 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{

			generate();
			save();
			setVisible(false);


		}
	};

	public CreateWindow(JFrame parent, String PATH, int x, int y)
	{

		super(parent, true);
		this.PATH = PATH;
		this.x = x;
		this.y = y;
		
		panel.setLayout(new MigLayout());

		panel.add(head,"span 2, wrap, align center");

		panel.add(fNameLabel);
		panel.add(fNameField,"wrap");

		panel.add(lNameLabel);
		panel.add(lNameField,"wrap");

		panel.add(PointLabel,"span 2,wrap,align center");

		panel.add(HPLabel,"align right");
		panel.add(HPm,"split 3, align center");
		panel.add(HPField);
		panel.add(HPp,"wrap");

		panel.add(MPLabel, "align right");
		panel.add(MPm,"split 3, align center");
		panel.add(MPField);
		panel.add(MPp,"wrap");

		panel.add(go,"span 2, align center");

		HPp.addActionListener(HPa);
		HPm.addActionListener(HPa);

		MPp.addActionListener(MPa);
		MPm.addActionListener(MPa);

		go.addActionListener(GOa);

		HPField.setText("60");
		MPField.setText("50");

		add(panel);

		pack();
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		setLocationRelativeTo(null);

		new Timer(10, new ActionListener() 
		{

			public void actionPerformed(ActionEvent e) {

				HP = Integer.parseInt(HPField.getText());
				MP = Integer.parseInt(MPField.getText());
				
				if(HP <= 0) HPm.setEnabled(false); else HPm.setEnabled(true);
				if(MP <= 0) MPm.setEnabled(false); else MPm.setEnabled(true);

				if((HP/HP_MULT)+(MP/MP_MULT) >= MAX_POINTS)	allButtonState(false);
				else allButtonState(true);

				int pointsLeft = MAX_POINTS-((HP/HP_MULT)+(MP/MP_MULT));

				PointLabel.setText("Points remaining: " + pointsLeft); 

				if(fNameField.getText().length() != 0 && lNameField.getText().length() != 0) go.setEnabled(true && pointsLeft == 0);
				else go.setEnabled(false);

			}
		}).start();

		setVisible(true);

	}

	private void allButtonState(boolean state)
	{

		HPp.setEnabled(state);
		MPp.setEnabled(state);

	}

	private void generate()
	{

		fName = fNameField.getText();
		lName = lNameField.getText();

		player = new Player(fName, lName, HP, MP, x, y);

	}

	private void save()
	{

		try {
			ObjectOutputStream  os = new ObjectOutputStream(new FileOutputStream(PATH + "/player.dat"));
			os.writeObject(player);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
