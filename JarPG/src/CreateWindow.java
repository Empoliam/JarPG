import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

	private static final long serialVersionUID = 8218447679269415077L;
	private int MAX_POINTS = 30;
	private int HP_MULT = 3;
	private int MP_MULT = 5;
	
	JPanel panel = new JPanel();

	String fName, lName;
	int HP, MP, ATT, DEF;

	JLabel head = new JLabel("Character Creation");
	JLabel fNameLabel = new JLabel("First Name: ");
	JLabel lNameLabel = new JLabel("Last Name: ");
	JLabel HPLabel = new JLabel("HP: ");
	JLabel MPLabel = new JLabel("MP: ");
	JLabel ATTLabel = new JLabel("Attack: ");
	JLabel DEFLabel = new JLabel("Defence: ");
	JLabel PointLabel = new JLabel("Points left: " + MAX_POINTS);
	JLabel debugLabel = new JLabel("Debug Mode:");
	
	JTextField fNameField = new JTextField(10);
	JTextField lNameField = new JTextField(10);
	STextField HPField = new STextField();
	STextField MPField = new STextField();
	STextField ATTField = new STextField();
	STextField DEFField = new STextField();

	SButton HPp = new SButton("+");
	SButton MPp = new SButton("+");
	SButton ATTp = new SButton("+");
	SButton DEFp = new SButton("+");	
	SButton HPm = new SButton("-");
	SButton MPm = new SButton("-");
	SButton ATTm = new SButton("-");
	SButton DEFm = new SButton("-");

	JButton go = new JButton("Go!");
	
	JCheckBox debug = new JCheckBox();

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
	ActionListener ATTa = new ActionListener() 
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			int value = Integer.parseInt(ATTField.getText());

			switch( e.getActionCommand() )
			{

			case "+" :
				value ++;
				break;

			case "-" :
				value --;
				break;

			}

			ATTField.setText(Integer.toString(value));

		}
	};
	ActionListener DEFa = new ActionListener() 
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			int value = Integer.parseInt(DEFField.getText());

			switch( e.getActionCommand() )
			{

			case "+" :
				value ++;
				break;

			case "-" :
				value --;
				break;

			}

			DEFField.setText(Integer.toString(value));

		}
	};
	
	ActionListener GOa = new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		
			setVisible(false);
			
			
		}
	};
	
	
	public CreateWindow(JFrame parent)
	{

		super(parent, true);
		
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

		panel.add(ATTLabel, "align right");
		panel.add(ATTm,"split 3, align center");
		panel.add(ATTField);
		panel.add(ATTp,"wrap");

		panel.add(DEFLabel, "align right");
		panel.add(DEFm,"split 3, align center");
		panel.add(DEFField);
		panel.add(DEFp,"wrap");

		panel.add(debugLabel,"align right");
		panel.add(debug,"wrap,align center");
		
		panel.add(go,"span 2, align center");

		HPp.addActionListener(HPa);
		HPm.addActionListener(HPa);

		MPp.addActionListener(MPa);
		MPm.addActionListener(MPa);

		ATTp.addActionListener(ATTa);
		ATTm.addActionListener(ATTa);

		DEFp.addActionListener(DEFa);
		DEFm.addActionListener(DEFa);

		go.addActionListener(GOa);
		
		HPField.setText("30");
		MPField.setText("30");
		ATTField.setText("7");
		DEFField.setText("7");
		
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
				ATT = Integer.parseInt(ATTField.getText());
				DEF = Integer.parseInt(DEFField.getText());

				if(HP <= 0) HPm.setEnabled(false); else HPm.setEnabled(true);
				if(MP <= 0) MPm.setEnabled(false); else MPm.setEnabled(true);
				if(ATT <= 0) ATTm.setEnabled(false); else ATTm.setEnabled(true);
				if(DEF <= 0) DEFm.setEnabled(false); else DEFm.setEnabled(true);
				
				if((HP/HP_MULT)+(MP/MP_MULT)+ATT+DEF >= MAX_POINTS)	allButtonState(false);
				else allButtonState(true);
				
				int pointsLeft = MAX_POINTS-((HP/HP_MULT)+(MP/MP_MULT)+ATT+DEF);
				
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
		ATTp.setEnabled(state);
		DEFp.setEnabled(state);
		
	}
	
	public Player generate()
	{
		
		fName = fNameField.getText();
		lName = lNameField.getText();
		
		Player player = new Player(fName, lName, HP, MP, ATT, DEF);
		return player;
		
	}
	
	public boolean Debug()
	{
		
		return debug.isSelected();
		
	}
	
}
