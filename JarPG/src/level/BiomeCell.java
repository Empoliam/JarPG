package level;


public class BiomeCell 
{

	boolean grassland = false;
	boolean desert = false;
	boolean forest = false;
	boolean tundra = false;
	boolean jungle = false;

	BiomeCell(BiomeCell in)
	{
		
		this.grassland = in.grassland;
		this.desert = in.desert;
		this.forest = in.forest;
		this.tundra = in.tundra;
		this.jungle = in.jungle;
		
	}
	
	public BiomeCell() {}

	public boolean isGrassland() {
		return grassland;
	}
	public void setGrassland(boolean grassland) {
		this.grassland = grassland;
	}
	public boolean isDesert() {
		return desert;
	}
	public void setDesert(boolean desert) {
		this.desert = desert;
	}
	public boolean isForest() {
		return forest;
	}
	public void setForest(boolean forest) {
		this.forest = forest;
	}
	public boolean isTundra() {
		return tundra;
	}
	public void setTundra(boolean tundra) {
		this.tundra = tundra;
	}
	public boolean isJungle() {
		return jungle;
	}
	public void setJungle(boolean jungle) {
		this.jungle = jungle;
	}

	public void set(int sel, boolean set)
	{

		switch(sel)
		{

		case 1: 
			setGrassland(set);
			break;
		case 2: 
			setDesert(set);
			break;
		case 3:
			setForest(set);
			break;
		case 4: 
			setTundra(set);
			break;
		case 5:
			setJungle(set);
			break;

		}

	}

	public int getType()
	{
		
		int type = 0;
		if(grassland == true) type = 1;
		else if(desert == true) type = 2;
		else if(forest == true) type = 3;
		else if(tundra == true) type = 4;
		else if(jungle == true) type = 5;
		
		return type;
		
	}
	
}
