package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;
import statComponents.*;
import unit.Player;

public class CreateWindow extends JDialog
{

	private static final long serialVersionUID = 2L;
	String PATH;

	private int MAX_POINTS = 10;

	JPanel panel = new JPanel();

	String fName, lName;
	int Mining, x, y;

	Player player;

	JLabel PointLabel = new JLabel("Points left: " + MAX_POINTS);

	JTextField fNameField = new JTextField(10);
	JTextField lNameField = new JTextField(10);
	STextField MiningField = new STextField();

	SButton miningP = new SButton("+");
	SButton miningM = new SButton("-");

	JButton go = new JButton("Go!");

	ActionListener miningA = new ActionListener() 
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			int value = Integer.parseInt(MiningField.getText());

			switch( e.getActionCommand() )
			{

			case "+" :
				value ++;
				break;

			case "-" :
				value --;
				break;

			}

			MiningField.setText(Integer.toString(value));

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

	public CreateWindow(IntroWindow introWindow, String PATH, int x, int y)
	{

		super(introWindow, true);
		this.PATH = PATH;
		this.x = x;
		this.y = y;
		
		panel.setLayout(new MigLayout());

		panel.add(new JLabel("Character creation"),"span 2, wrap, align center");

		panel.add(new JLabel("First name"));
		panel.add(fNameField,"wrap");

		panel.add(new JLabel("Last name"));
		panel.add(lNameField,"wrap");

		panel.add(PointLabel,"span 2,wrap,align center");

		panel.add(new JLabel("Mining"),"align right");
		panel.add(miningM,"split 3, align center");
		panel.add(MiningField);
		panel.add(miningP,"wrap");

		panel.add(go,"span 2, align center");

		miningP.addActionListener(miningA);
		miningM.addActionListener(miningA);

		go.addActionListener(GOa);

		MiningField.setText("10");

		add(panel);

		pack();
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		setLocationRelativeTo(null);

		new Timer(10, new ActionListener() 
		{

			public void actionPerformed(ActionEvent e) {

				Mining = Integer.parseInt(MiningField.getText());
				
				if(Mining <= 0) miningM.setEnabled(false); else miningM.setEnabled(true);

				if(Mining >= MAX_POINTS) allButtonState(false);
				else allButtonState(true);

				int pointsLeft = MAX_POINTS  - Mining;

				PointLabel.setText("Points remaining: " + pointsLeft); 

				if(fNameField.getText().length() != 0 && lNameField.getText().length() != 0) go.setEnabled(true && pointsLeft == 0);
				else go.setEnabled(false);

			}
		}).start();

		setVisible(true);

	}

	private void allButtonState(boolean state)
	{

		miningP.setEnabled(state);

	}

	private void generate()
	{

		fName = fNameField.getText();
		lName = lNameField.getText();

		player = new Player(fName, lName, x, y);
		player.mining = Mining;
		
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
