package world;

import java.io.Serializable;

import world.geology.Rock;

public class Region implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	int x, y;
	double height;
	int biome;
	boolean solid;
	boolean mountain;
	boolean snow;

	Rock[] sediment = new Rock[3];
	Rock[] nativemetal = new Rock[2];

	public Region()	{}

	public Region(int x, int y)
	{

		this.x = x;
		this.y = y;

	}

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

	public void setXY(int x, int y)
	{

		this.x = x;
		this.y = y;

	}

	public void setRock(String layer, int number, Rock rock)
	{

		switch(layer)
		{

		case "sediment":
			sediment[number] = rock;
			break;
		case "native":
			nativemetal[number] = rock;
			break;
			
		}

	}

}
