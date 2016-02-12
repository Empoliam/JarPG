package main;

import java.util.Random;

import items.Item;
import items.Stone;
import world.geology.Rock;

import static main.Main.*;

public class Dig 
{

	public static void digSpecific(String in)
	{

		Rock mined = null;
		boolean hit = true;

		switch(in)
		{

		case "Sediment": 
			mined = activeRegion.getRock("sediment", new Random().nextInt(3));
			break;
		case "Native":
			mined = activeRegion.getRock("native", new Random().nextInt(2));
			break;
		case "Organic":
			mined = activeRegion.getRock("organics", new Random().nextInt(2));
			break;
		case "Ore":
			mined = activeRegion.getRock("ore", new Random().nextInt(4));
			break;
		default:
			mainWindow.textarea.append("Such a layer does not exist.\n");
			hit = false;
			break;
		}


		if(hit == true)
		{
			mainWindow.textarea.append("You strike " + mined.name + "!\n");
			if(mined.yeild == 10) player.addItem(new Stone(mined.meta,new Random().nextInt(6)));
			else player.addItem(new Item(mined.yeild));
		}

	}

	public static void digRandom()
	{

		int ranLayer = new Random().nextInt(4);
		Rock mined = null;
		boolean hit = true;

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
			break;
		case 3:
			mined = activeRegion.getRock("ore", new Random().nextInt(4));
			break;
		}

		if(hit == true)
		{
			mainWindow.textarea.append("You strike " + mined.name + "!\n");
			if(mined.yeild == 10) player.addItem(new Stone(mined.meta,new Random().nextInt(6)));
			else player.addItem(new Item(mined.yeild));
		}

	}

}