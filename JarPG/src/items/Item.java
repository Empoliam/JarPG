package items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable 
{

	final static long serialVersionUID = 2L;
	final static List<String> allitems = new ArrayList<String>();
	final static List<String> prefixes = new ArrayList<String>();
		
	public String name;
	public final int id;
	int baseweight;
	int basevalue;
	public final int maxstacksize;
	public int stacksize;
	public int meta;
	public int prefix;
	
	public Item(int id)
	{
		
		this.id = id;
		
		String[] load = allitems.get(id).split(",");
				
		name = load[1];
		baseweight = Integer.parseInt(load[2]);
		basevalue = Integer.parseInt(load[3]);
		maxstacksize = Integer.parseInt(load[4]);
		stacksize = 1;
		prefix = 0;
		
	}
		
	public void setPrefix(int id)
	{
		
		prefix = id;
		name = prefixes.get(id).split(",")[1] + " " + name;
		
	}
	
	public static void getList()
	{
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("resources/items.csv"));
			
			String line = br.readLine();
			
			while(line != null)
			{
				
				allitems.add(line);
				line = br.readLine();
				
			}
			
			br.close();
			
		}
		catch(java.io.FileNotFoundException e){}
		catch(IOException e2){};
		
	}
	
	public static void getPrefixes()
	{
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("resources/prefixes.csv"));
			
			String line = br.readLine();
			
			while(line != null)
			{
				
				prefixes.add(line);
				line = br.readLine();
				
			}
			
			br.close();
			
		}
		catch(java.io.FileNotFoundException e){}
		catch(IOException e2){};
	}

}
