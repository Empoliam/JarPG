package items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Stone extends Item
{

	final static List<String> allgems = new ArrayList<String>();
	
	private static final long serialVersionUID = 3L;
	
	int sharpness;
	
	public Stone(int metaid) 
	{
		
		super(10);	
		meta = metaid;
		
		String[] load = allgems.get(meta).split(",");
		
		name = load[1];
		basevalue = Integer.parseInt(load[2]);
			
	}
	
	public Stone(int metaid, int sharpness)
	{
		
		this(metaid);
		this.sharpness = sharpness;
		setPrefix(getSharpnessPrefix());
		
	}

	public static void getList()
	{
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("resources/stones.csv"));
			
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
	
	private int getSharpnessPrefix()
	{		
		
		int out = 0;
		
		if(sharpness <= 2) out = 4;
		else if(sharpness <= 4) out = 3;
		else if(sharpness <= 6) out = 2;
		else if(sharpness <= 8) out = 1;
		else if(sharpness <= 10) out = 0;
		
		return out;
	}
	
}
