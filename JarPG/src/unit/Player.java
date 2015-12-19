package unit;

public class Player extends Unit
{

	String fName, lName;
	int x, y;
	
	public Player(String ifName, String ilName, int HPi, int MPi, int ATTi, int DEFi)
	{
		
		super(HPi, MPi, ATTi, DEFi);
		fName = ifName;
		lName = ilName;
		
	}
	
	public String getFName(){ return fName; }
	public String getLName(){ return lName; }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y)
	{
		
		this.x = x;
		this.y = y;
		
	}
	
}
