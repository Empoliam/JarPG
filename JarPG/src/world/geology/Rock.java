package world.geology;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rock implements Serializable
{

	private static final long serialVersionUID = 6L;

	final static List<String> allrockstrings = new ArrayList<String>();
	public final static List<Rock> allrocks = new ArrayList<Rock>();
	
	public int id;
	public String name;
	public int yeild;
	public Color colour;
	
	private Rock(int id)
	{
		
		this.id = id;
		
		String[] line = allrockstrings.get(id).split(",");	
				
		name = line[1];
		yeild = Integer.parseInt(line[3]);
		colour = Color.decode(line[4]);
		
	}
	
	public static void getList()
	{
				
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("resources/rocks.csv"));
			
			String line = br.readLine();
			
			while(line != null)
			{
				
				allrockstrings.add(line);
				line = br.readLine();
				
			}
			
			br.close();
			
		}
		catch(java.io.FileNotFoundException e){}
		catch(IOException e2){};
		
	}
	
	public static void populateRockList()
	{
				
		for(int id = 0; id < allrockstrings.size(); id ++)
		{
			
			allrocks.add(new Rock(id));
			
		}
		
	}
	
}
