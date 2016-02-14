package main;

import java.util.ArrayList;
import java.util.List;

import items.Item;

import static main.Main.player;

public class Smelt 
{

	public static List<String> singleSmelting = new ArrayList<String>();
	
	public static void smelt(Item iA)
	{
		
		player.removeItem(iA);
		
	}
	
}
