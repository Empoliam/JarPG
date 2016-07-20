package unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import items.Item;

public class Unit implements Serializable 
{

	final static long serialVersionUID = 2L;
	
	public List<Item> inventory = new ArrayList<Item>();
	
	public int x, y;
	
	//skills
	public int mining = 0;
	
	String fName, lName;
	
	Unit()
	{
				
	}
	
	public Unit(String fName, String lName, int x, int y)
	{
		
		this.fName = fName;
		this.lName = lName;
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
	
	public void addItem(Item in, int quantity)
	{

		boolean newItem = true;

		for(Item i : inventory)
		{

			if(i.id == in.id && i.stacksize < i.maxstacksize)
			{

				if(i.prefixid == in.prefixid)
				{

					i.stacksize += quantity;

					newItem = false;
					break;
				}

			}

		}

		if(newItem == true)
		{

			in.stacksize = quantity;
			inventory.add(in);

		}

	}
	
	public int removeItem(Item out, int no)
	{

		int remain = 0;
		
		for(; no > 0; no --)
		{
			for(Item i : inventory)
			{

				if(i.id == out.id)
				{

					if(i.stacksize > 1)
					{

						i.stacksize --;
						remain = i.stacksize;

					}
					else
					{

						i.stacksize --;
						inventory.remove(inventory.indexOf(i));
						remain = 0;

					}

					break;

				}

			}

		}

		return remain;

		}
	
}
