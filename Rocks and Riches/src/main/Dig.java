package main;

import java.util.Random;

import items.Item;
import world.geology.Rock;

import static main.Main.*;

public abstract class Dig 
{

	public static void digLayer(int in)
	{

		Rock mined = null;

		switch(in)
		{
		case 0:
			int ranLayer = new Random().nextInt(4)+1;
			digLayer(ranLayer);
			return;
		case 1:
			mined = activeRegion.getRock("sediment", new Random().nextInt(3));
			break;
		case 2:
			mined = activeRegion.getRock("native", new Random().nextInt(2));
			break;
		case 3:
			mined = activeRegion.getRock("organics", new Random().nextInt(2));
			break;
		case 4:
			mined = activeRegion.getRock("ore", new Random().nextInt(4));
			break;
		default:
			mainWindow.textarea.append("Such a layer does not exist.\n");
			break;
		}

		double chance = (double)player.mining / 20.0;

		if(Math.random() <= chance)
		{
			mainWindow.textarea.append("You strike " + mined.name + "!\n");
			player.addItem(new Item(mined.yeild),1);
		}
		else
		{
			mainWindow.textarea.append("You fail to find anything of value.\n");					
		}

	}

}