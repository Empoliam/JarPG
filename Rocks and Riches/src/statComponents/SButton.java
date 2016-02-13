package statComponents;
import java.awt.Insets;

import javax.swing.JButton;

public class SButton extends JButton
{

	private static final long serialVersionUID = -7059492459934273695L;

	public SButton(String name)
	{
		
		super(name);
		setMargin(new Insets(1, 2, 1, 2));
		
		
	}
	
}
