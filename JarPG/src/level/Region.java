package level;

public class Region 
{
	
	//state tags
	boolean solid = false;
	boolean polar = false;
	boolean beach = false;
	boolean ocean = true;
	boolean mountain = false;
	boolean snow = false;
	boolean lake = false;
	boolean river = false;
	
	/*biomes
	 * 0 = Desert
	 * 1 = Savanna
	 * 2 = Seasonal Forest
	 * 3 = Rainforest
	 * 4 = Plains
	 * 5 = Woods
	 * 6 = Forest
	 * 7 = Swamp
	 * 8 = Taiga
	 * 9 = Tundra
	 * 10 = Ocean
	 * 11 = Polar
	 * */
	
	int biometype;
	
	public Region() {}

	public Region(Region in)
	{

		this.solid = in.solid;
		this.polar = in.polar;
		this.beach = in.beach;
		this.ocean = in.ocean;
		this.mountain = in.mountain;
		this.snow = in.snow;
		this.lake = in.lake;
		this.river = in.river;

	}

	public void setOcean(boolean set)
	{
		
		ocean = set;
		solid = !set;
		polar = !set;
		beach = !set;
		mountain = !set;
		snow = !set;
		lake = !set;
		river = !set;
		
	}
	public boolean getOcean() {return ocean;}

	public void setSolid(boolean set)
	{
		
		solid = set;
		ocean = !set;
		
	}
	public boolean getSolid() {return solid;}

	public void setPolar(boolean set)
	{

		polar = set;
		solid = set;
		ocean = !set;
		beach = !set;
		snow = !set;

	}
	public boolean getPolar() 
	{
		
		return polar;
		
	}

	public void setBeach(boolean set)
	{
		
		beach = set;
	}
	public boolean getBeach() 
	{
		
		return beach;
		
	}

	public boolean getSnow() 
	{
		
		return snow;
		
	}
	public void setSnow(boolean setsnow) 
	{
		
		snow = setsnow;
		
	}

	public boolean getMountain() 
	{
		
		return mountain;
		
	}
	public void setMountain(boolean set)
	{
		
		mountain = set;
		
	}
	
	public boolean getLake() 
	{
		
		return lake;
		
	}
	public void setLake(boolean set) 
	{
		
		lake = set;
		solid = !set;
		
	}

	public boolean getRiver() 
	{
		
		return river;
		
	}
	public void setRiver(boolean set) 
	{
		
		this.river = set;
		this.solid = !set;
		this.mountain = !set;
		
	}

	public int getType()
	{
		
		return biometype;
		
	}
	
	public void setType(int biometype)
	{
		
		this.biometype = biometype;
		
	}
	
	public boolean[] getTags()
	{
		
		boolean[] tags = new boolean[] {solid, polar, beach, ocean, mountain, snow, lake, river};
		return tags;
		
	}
	
}
