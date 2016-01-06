package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import statComponents.SButton;

public class LoadWindow extends JDialog 
{

	private static final long serialVersionUID = 4792387293446509940L;

	String PATH;
	File[] directories = new File("worlds").listFiles(File::isDirectory);
			
	JPanel mainPanel = new JPanel();
	JPanel worldsPanel = new JPanel();
	
	ActionListener worldButton = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			PATH = "worlds/" + directories[Integer.parseInt(arg0.getActionCommand())-1].getName();
			setVisible(false);
			
		}
	};
	
	public LoadWindow(JFrame parent)
	{
		
		super(parent, true);
		
		mainPanel.setLayout(new MigLayout());
		worldsPanel.setLayout(new MigLayout());
		worldsPanel.setBorder(BorderFactory.createEtchedBorder());
				
		int worldNo = 1;
		
		for(File f : directories)
		{
			
			SButton button = new SButton(Integer.toString(worldNo));
			button.addActionListener(worldButton);
			worldsPanel.add(button);
			worldsPanel.add(new JLabel(f.getName()),"wrap, pushx");
			worldNo++;
			
		}
		
		mainPanel.add(new JLabel("Select a world: "), "align center, pushx, wrap");
		mainPanel.add(worldsPanel,"align center");
		
		add(mainPanel);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public String getPath()
	{
		
		return PATH;
		
	}

}
