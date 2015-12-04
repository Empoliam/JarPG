package unit;

public class Player extends Unit
{

	String fName, lName;
	
	public Player(String ifName, String ilName, int HPi, int MPi, int ATTi, int DEFi)
	{
		
		super(HPi, MPi, ATTi, DEFi);
		fName = ifName;
		lName = ilName;
		
	}
	
	public String getFName(){ return fName; }
	public String getLName(){ return lName; }
	
}
