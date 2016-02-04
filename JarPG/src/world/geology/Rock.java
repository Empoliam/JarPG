package world.geology;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rock implements Serializable
{

	private static final long serialVersionUID = 3L;

	final static List<String> allrocks = new ArrayList<String>();
	
	public int id;
	public String name;
	public int yeild;
	public int meta;
	public String colour;
	
	public Rock(int id)
	{
		
		this.id = id;
		
		String[] line = allrocks.get(id).split(",");	
				
		name = line[1];
		yeild = Integer.parseInt(line[3]);
		meta = Integer.parseInt(line[4]);
		colour = line[5];
		
	}
	
	public static void getList()
	{
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("resources/rocks.csv"));
			
			String line = br.readLine();
			
			while(line != null)
			{
				
				allrocks.add(line);
				line = br.readLine();
				
			}
			
			br.close();
			
		}
		catch(java.io.FileNotFoundException e){}
		catch(IOException e2){};
		
	}
	
}
