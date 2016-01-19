package unit;

public class Player extends Unit
{

	final static long serialVersionUID = 1L;
	
	int x, y;
	
	public Player(String fName, String lName, int HP, int MP, int x, int y)
	{
		
		super(fName, lName, HP, MP);
		this.x = x;
		this.y = y;
		
	}
	
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
