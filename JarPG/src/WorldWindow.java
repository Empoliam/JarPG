import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import world.World;

public class WorldWindow extends JDialog 
{

	private static final long serialVersionUID = 3998702201167042838L;

	//Data
	World currentWorld;
	String PATH;
	
	//Components
	JPanel mainPanel = new JPanel();
	
	JTextField nameField = new JTextField(20);
	
	JButton newWorld = new JButton("New");
	
	JLabel nameLabel = new JLabel("World Name: ");
	JLabel mapPane = new JLabel();
		
	ActionListener aNewWorld = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			PATH = nameField.getText();
			currentWorld = new World(400,PATH);
			try {
				
				BufferedImage mapImg = ImageIO.read(new File("worlds/" + PATH + "/map.bmp"));
				ImageIcon mapIcon = new ImageIcon(mapImg);
				mapPane.setIcon(mapIcon);			
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			pack();
		}
	};
	
	public WorldWindow(JFrame parent)
	{
		
		super(parent,true);
		
		mainPanel.setLayout(new MigLayout());
		
		mainPanel.add(nameLabel);
		mainPanel.add(nameField,"wrap");
		mainPanel.add(mapPane,"span 2, wrap");
		mainPanel.add(newWorld);
		
		add(mainPanel);
		
		nameField.setText("world");
		newWorld.addActionListener(aNewWorld);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setModal(true);
		
	}
	
}
