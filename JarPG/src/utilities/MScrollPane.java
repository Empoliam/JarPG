package utilities;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;;

public class MScrollPane extends JScrollPane 
{

	
	private static final long serialVersionUID = 1523164363463473L;

	public MScrollPane(Component c)
	{
		
		super(c);
		
		setMaximumSize(new Dimension(600,600));
		getVerticalScrollBar().setUnitIncrement(20);
		getHorizontalScrollBar().setUnitIncrement(20);
		
	}
	
}
