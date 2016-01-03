package world;

public class Region 
{

	double height;
	int biome;
	boolean solid;
	boolean mountain;
	boolean snow;

	public Region()	{}

	public void setHeight(double heightin) {	
		height = heightin;	
	}

	public double getHeight() {		
		return height;	
	}

	public int getBiome() {
		return biome;
	}

	public void setBiome(int biome) {
		this.biome = biome;
	}

	public boolean get(String tag)
	{

		boolean val = false;

		switch(tag)
		{

		case "solid" : val = solid;
		break;
		case "mountain" : val = mountain;
		break;
		case "snow" : val = snow;
		break;
		}

		return val;
		
	}
	
	public void set(String tag, boolean val)
	{

		switch(tag)
		{

		case "solid" : solid = val;
		break;
		case "mountain" : mountain = val;
		break;
		case "snow" : snow = val;
		break;
		}
	
	}

}
