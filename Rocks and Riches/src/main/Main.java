package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gui.MainWindow;
import gui.intro.IntroWindow;
import items.Item;
import unit.Unit;
import world.Region;
import world.SuperRegion;
import world.geology.Rock;

public abstract class Main 
{

	public static String PATH;
	public static int WORLD_SIZE;

	static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	//various constants
	public static int[] spawn = new int[2];

	//variables
	public static int currentx, currentX;
	public static int currenty, currentY;

	//active stuff
	public static Unit player;
	public static SuperRegion activeSuperRegion;
	public static Region activeRegion;

	public static MainWindow mainWindow;

	public static void main(String[] args) 
	{

		Rock.getList();
		Rock.populateRockList();
		Item.getList();
		Item.getPrefixes();
		Smelt.populateLists();

		loadGame();

	}

	static private void loadGame()
	{

		new IntroWindow();
		try
		{
			loadPlayer();
			loadRegion();
			mainWindow = new MainWindow();
		}
		catch(InvalidClassException e)
		{

			JOptionPane.showMessageDialog(null, "World out of date.\nPlease create a new world.");

		}

	}

	static void loadRegion() throws InvalidClassException
	{

		int oldX = currentX;
		int oldY = currentY;

		currentX = (int) Math.floor(currentx/40);
		currentY = (int) Math.floor(currenty/40);

		try {

			if(oldX != currentX || oldY != currentY)
			{

				ObjectInputStream is = new ObjectInputStream(new FileInputStream(PATH + "/regions/" + currentX + "-" + currentY + ".region"));
				activeSuperRegion = (SuperRegion) is.readObject();
				is.close();

			}
			activeRegion = activeSuperRegion.getTile((currentx-(currentX*40)),(currenty-(currentY*40)));

		} 
		catch (FileNotFoundException e) {  e.printStackTrace();	}
		catch (IOException e) 
		{ 
			e.printStackTrace();
			mainWindow.textarea.append("Save out of date and no longer compatible. Please create a new world.");
		}
		catch (ClassNotFoundException e) { e.printStackTrace(); }

	}

	private static void loadPlayer() throws InvalidClassException
	{

		try {

			ObjectInputStream is = new ObjectInputStream(new FileInputStream(PATH + "/player.dat"));
			Main.player = (Unit) is.readObject();
			is.close();
			currentx = player.getX();
			currenty = player.getY();

		}
		catch (FileNotFoundException e) {  e.printStackTrace();	mainWindow.textarea.append("Failed to load save. Please restart.\n");}
		catch (IOException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }

	}	

	public static void savePlayer()
	{

		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(Main.PATH + "/player.dat"));
			os.writeObject(Main.player);
			os.close();
			mainWindow.textarea.append("Save successful.");
		} catch (IOException e1) {
			e1.printStackTrace();
			mainWindow.textarea.append("Save failed.");
		}

	}

}
