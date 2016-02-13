package statComponents;

import javax.swing.JTextField;

public class STextField extends JTextField 
{

	private static final long serialVersionUID = -5060469502364883743L;

	public STextField()
	{
		
		super(2);
		setEditable(false);
		setText("0");
		setHorizontalAlignment(CENTER);
	
	}
	
}
