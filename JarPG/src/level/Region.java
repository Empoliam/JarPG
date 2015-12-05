package level;

public class Region 
{

	boolean solid = false;
	boolean polar = false;
	boolean beach = false;
	boolean ocean = true;
	boolean mountain = false;
	boolean snow = false;

	public Region() {}

	public Region(Region in)
	{

		this.solid = in.solid;
		this.polar = in.polar;
		this.beach = in.beach;
		this.ocean = in.ocean;
		this.mountain = in.mountain;
		this.snow = in.snow;

	}

	public void setOcean(boolean set)
	{
		ocean = set;
		solid = !set;
		polar = !set;
		beach = !set;

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
	public boolean getPolar() {return polar;}

	public void setBeach(boolean set){
		beach = set;
	}
	public boolean getBeach() {return beach;}

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
	
}
