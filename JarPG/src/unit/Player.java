package unit;

public class Player extends Unit
{

	String fName, lName;
	String superX, superY;
	
	public Player(String ifName, String ilName, int HPi, int MPi, int ATTi, int DEFi)
	{
		
		super(HPi, MPi, ATTi, DEFi);
		fName = ifName;
		lName = ilName;
		
	}
	
	public String getFName(){ return fName; }
	public String getLName(){ return lName; }

	public String getSuperX() {
		return superX;
	}

	public String getSuperY() {
		return superY;
	}

	public void setSuperX(String superX) {
		this.superX = superX;
	}

	public void setSuperY(String superY) {
		this.superY = superY;
	}
	
}
