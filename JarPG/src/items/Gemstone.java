package items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gemstone extends Item
{

	final static List<String> allgems = new ArrayList<String>();
	
	private static final long serialVersionUID = 1L;
	
	public Gemstone(int metaid) 
	{
		
		super(73);	
		meta = metaid;
		
		String[] load = allgems.get(meta).split(",");
		
		name = load[1];
		basevalue = Integer.parseInt(load[2]);
			
	}
	
	public Gemstone(int metaid, int prefix)
	{
		
		this(metaid);
		setPrefix(prefix);
		
	}

	public static void getList()
	{
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("resources/gemstones.csv"));
			
			String line = br.readLine();
			
			while(line != null)
			{
				
				allgems.add(line);
				line = br.readLine();
				
			}
			
			br.close();
			
		}
		catch(java.io.FileNotFoundException e){}
		catch(IOException e2){};
		
	}
	
}
