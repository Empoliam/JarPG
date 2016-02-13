package items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable 
{

	final static long serialVersionUID = 4L;
	final static List<String> allitems = new ArrayList<String>();
	final static List<String> prefixes = new ArrayList<String>();
		
	public String name;
	public String prefix = "";
	public final int id;
	public int baseweight;
	public int basevalue;
	public final int maxstacksize;
	public int stacksize;
	public int prefixid;
	
	public Item(int id)
	{
		
		this.id = id;
		
		String[] load = allitems.get(id).split(",");
				
		name = load[1];
		baseweight = Integer.parseInt(load[2]);
		basevalue = Integer.parseInt(load[3]);
		maxstacksize = Integer.parseInt(load[4]);
		stacksize = 1;
		prefixid = 0;
		
	}
		
	public void setPrefix(int id)
	{
		
		prefixid = id;
		prefix = prefixes.get(id).split(",")[1];
		
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
