package main;

import java.util.Random;

import items.Item;
import items.Stone;
import world.geology.Rock;

import static main.Main.*;

public class Dig 
{

	public static void dig(String in)
	{

		Rock mined = null;
		boolean hit = true;

		try
		{

			if(player.mining >= 5 && !in.equals("any"))
			{

				mainWindow.textarea.append("You dig into the " + in + " layers.\n");

				switch(in)
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

}