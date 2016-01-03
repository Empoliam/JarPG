package world;

public class Region 
{

	double height;
	int biome;
	boolean solid;
	boolean mountain;
	boolean snow;
	
	public Region()
	{
		
	}
	
	public void setHeight(double heightin) {	
		height = heightin;	
	}
	
	public double getHeight() {		
		return height;	
	}

	public boolean getSolid() {	
		return solid;	
	}

	public void setSolid(boolean solid){	
		this.solid = solid;	
	}

	public boolean getMountain() {
		return mountain;
	}

	public void setMountain(boolean mountain) {
		this.mountain = mountain;
	}

	public boolean getSnow() {
		return snow;
	}

	public void setSnow(boolean snow) {
		this.snow = snow;
	}
	
}
