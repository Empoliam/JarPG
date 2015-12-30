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
		
		setMinimumSize(new Dimension(400,400));
		setPreferredSize(new Dimension(400,400));
		getVerticalScrollBar().setUnitIncrement(20);
		getHorizontalScrollBar().setUnitIncrement(20);
		
	}
	
}
