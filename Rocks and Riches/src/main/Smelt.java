package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import items.SmeltingRecipeSingle;

public class Smelt 
{

	public static List<SmeltingRecipeSingle> singleSmelting = new ArrayList<SmeltingRecipeSingle>();

	public static final void populateLists()
	{

		try {

			BufferedReader br = new BufferedReader(new FileReader("resources/singleSmelting.csv"));
			String read = br.readLine();
			while(read != null)
			{

				singleSmelting.add(new SmeltingRecipeSingle(read));
				read = br.readLine();

			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
