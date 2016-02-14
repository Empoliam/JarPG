package gui.intro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;
import net.miginfocom.swing.MigLayout;

public class IntroWindow extends JDialog
{

	private static final long serialVersionUID = 1L;
	static IntroWindow titlePage;
	
	String PATH;
	int WORLD_SIZE;
	
	int[] spawn = new int[2];
	
	JPanel mainPane = new JPanel();
	
	JLabel titleImage = new JLabel();
	
	JButton newButton = new JButton("Create new world");
	JButton useButton = new JButton("Use existing world");
	
	ActionListener aNew = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			makeWorld();
			makePlayer();
			Main.PATH = PATH;
			Main.WORLD_SIZE = WORLD_SIZE;
			dispose();
			
		}
		
	};
	
	ActionListener aUse = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
		
			LoadWindow loadSave = new LoadWindow(titlePage);
			Main.PATH = loadSave.getPath();
			loadSave.dispose();
			dispose();
			
		}
	};
		
	public IntroWindow()
	{
				
		try {

			BufferedImage titleImg = ImageIO.read(new File("resources/title.png"));
			ImageIcon titleIcon = new ImageIcon(titleImg);
			titleImage.setIcon(titleIcon);	
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		newButton.addActionListener(aNew);
		useButton.addActionListener(aUse);
		
		mainPane.setLayout(new MigLayout());
		mainPane.add(titleImage,"wrap");
		mainPane.add(newButton,"align center, split 2");
		mainPane.add(useButton, "align center");
		
		add(mainPane);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
				
	}
	
	private void makePlayer()
	{

		PlayerCreateWindow create = new PlayerCreateWindow(this,PATH,spawn[0],spawn[1]);
		create.dispose();

	}

	private void makeWorld()
	{

		WorldWindow createWorld = new WorldWindow(this);
		PATH = createWorld.getPath();
		spawn = createWorld.getSpawn();
		WORLD_SIZE = 0;
		createWorld.dispose();

	}
	
}
