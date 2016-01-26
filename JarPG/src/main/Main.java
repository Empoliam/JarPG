package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gui.IntroWindow;
import gui.MainWindow;
import items.Item;
import items.Stone;
import unit.Player;
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
	public static Player player;
	public static SuperRegion activeSuperRegion;
	public static Region activeRegion;
	
	static MainWindow mainWindow;
	
	public static void main(String[] args) 
	{
		
		Rock.getList();
		Item.getList();
		Item.getPrefixes();
		Stone.getList();
		
		loadGame();
		
	}

	static private void loadGame()
	{
		
		new IntroWindow();
		loadPlayer();
		loadRegion();
		mainWindow = new MainWindow();
		
	}
	
	/*
	 * 0:north
	 * 1:west 
	 * 2:south
	 * 3:east
	 */
	 
	public static void move(int direction)
	{

		int reverse = 0; 

		try
		{


			switch(direction)
			{

			case 0:
				if(currenty != 0)
				{				
					currenty --;
					reverse = 2;
				}
				else mainWindow.textarea.append("You can go no further in this direction.\n");
				break;
			case 1:
				if(currentx != 0)
				{
					currentx --;	
					reverse = 3;
					break;
				}
				else mainWindow.textarea.append("You can go no further in this direction.\n");
				break;
			case 2:
				if(currenty != WORLD_SIZE-1)
				{				
					currenty ++;	
					reverse = 0;
				}
				else mainWindow.textarea.append("You can go no further in this direction.\n");
				break;
			case 3:
				if(currentx != WORLD_SIZE-1)
				{
					currentx ++;				
					reverse = 1;
				}
				else mainWindow.textarea.append("You can go no further in this direction.\n");
				break;

			}
			loadRegion();	
			if(activeRegion.getBiome() == -1)
			{
				mainWindow.textarea.append("You find an expanse of water, and can proceed no further.\n");
				move(reverse);
			}

			player.setXY(currentx, currenty);

		}
		catch(ArrayIndexOutOfBoundsException e)
		{

			mainWindow.textarea.append("Format unrecognised. Use 'move [direction]'.\n");

		}
		
		mainWindow.drawPlayer();

	}

	public static void dig(String in)
	{

		Rock mined = null;
		boolean hit = true;

		try
		{

			String layer = in.split(" ")[1];

			if(player.mining >= 5 && !layer.equals("any"))
			{

				mainWindow.textarea.append("You dig into the " + layer + " layers.\n");

				switch(layer)
				{

				case "sediment": 
					mined = activeRegion.getRock("sediment", new Random().nextInt(3));
					break;
				case "native":
					mined = activeRegion.getRock("native", new Random().nextInt(2));
					break;
				case "organic":
					mined = activeRegion.getRock("organics", new Random().nextInt(2));
					break;
				default:
					mainWindow.textarea.append("Such a layer does not exist.\n");
					hit = false;
					break;
				}

			}
			else
			{

				int ranLayer = new Random().nextInt(3);

				switch(ranLayer)
				{

				case 0: 
					mined = activeRegion.getRock("sediment", new Random().nextInt(3));
					break;
				case 1:
					mined = activeRegion.getRock("native", new Random().nextInt(2));
					break;
				case 2:
					mined = activeRegion.getRock("organics", new Random().nextInt(2));

				}

			}

			if(hit == true)
			{
				mainWindow.textarea.append("You strike " + mined.name + "!\n");
				if(mined.yeild == 10) player.addItem(new Stone(mined.meta,new Random().nextInt(6)));
				else player.addItem(new Item(mined.yeild));
			}

		}

		catch(ArrayIndexOutOfBoundsException e)
		{

			mainWindow.textarea.append("Format unrecognised. Use 'dig [sediment/native/organic/any]'.\n");

		}

	}
	
	private static void loadRegion()
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
	
	private static void loadPlayer()
	{

		try {

			ObjectInputStream is = new ObjectInputStream(new FileInputStream(PATH + "/player.dat"));
			Main.player = (Player) is.readObject();
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
