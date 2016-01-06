import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.CreateWindow;
import gui.LoadWindow;
import gui.MainWindow;
import gui.WorldWindow;
import net.miginfocom.swing.MigLayout;

public class IntroWindow extends JFrame
{

	private static final long serialVersionUID = -7226369558478098514L;
	static IntroWindow titlePage;
	
	String PATH;
	
	boolean debug;
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
			new MainWindow(PATH);
			dispose();
			
		}
		
	};
	
	ActionListener aUse = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
		
			LoadWindow loadSave = new LoadWindow(titlePage);
			PATH = loadSave.getPath();
			loadSave.dispose();
			new MainWindow(PATH);
			dispose();
			
		}
	};
	
	public static void main(String[] args)
	{
		
		titlePage = new IntroWindow();
				
	}
	
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
		setVisible(true);
				
	}
	
	private void makePlayer()
	{

		CreateWindow create = new CreateWindow(this,PATH,spawn[0],spawn[1]);
		debug = create.Debug();
		create.dispose();

	}

	private void makeWorld()
	{

		WorldWindow createWorld = new WorldWindow(this);
		PATH = createWorld.getPath();
		spawn = createWorld.getSpawn();
		createWorld.dispose();

	}
	
}
