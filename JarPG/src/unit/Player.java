package unit;

import java.util.List;
import java.util.ArrayList;

import items.Item;

public class Player extends Unit
{

	final static long serialVersionUID = 2L;
	
	public List<Item> inventory = new ArrayList<Item>();
	
	public int x, y;
	
	//Player Skills
	public int mining = 5;
	
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
		
	public void addItem(Item in)
	{
		
		boolean newItem = true;
		
		for(Item i : inventory)
		{
			
			if(i.id == in.id && i.stacksize < i.maxstacksize)
			{
				
				if(i.meta == in.meta && i.prefixid == in.prefixid)
				{
					i.stacksize ++;
					newItem = false;
					break;
				}
					
			}
						
		}
		
		if(newItem == true)
		{
			
			inventory.add(in);
			
		}
			
	}
		
}
