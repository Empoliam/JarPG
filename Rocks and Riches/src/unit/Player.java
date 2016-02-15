package unit;

import items.Item;

public class Player extends Unit
{

	final static long serialVersionUID = 3L;

	public int x, y;

	//Player Skills
	public int mining = 0;

	public Player(String fName, String lName, int x, int y)
	{

		super(fName, lName);
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

				if(i.prefixid == in.prefixid)
				{

					for(int x = in.stacksize; x > 0; x--)
					{
						i.stacksize ++;
					}
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

	public void removeItem(Item out, int no)
	{

		for(; no > 0; no --)
		{
			for(Item i : inventory)
			{

				if(i.id == out.id)
				{

					if(i.stacksize > 1)
					{

						i.stacksize --;

					}
					else
					{

						i.stacksize --;
						inventory.remove(inventory.indexOf(i));

					}

					break;

				}

			}

		}

	}

}