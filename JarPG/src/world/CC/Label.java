package world.CC;

import java.util.ArrayList;
import java.util.List;

public class Label 
{

	int id, lowestNeighbour;
	List<Integer> neighbours = new ArrayList<Integer>();

	Label(int id)
	{

		this.id = id;

	}

	public void addNeigbour(int add)
	{

		boolean isNew = true;

		for(Integer i : neighbours)
		{
			if (i.equals(add)) isNew = false;	
		}

		if(isNew == true) neighbours.add(add);

	}

	public void addNeigbours(List<Integer> add)
	{

		boolean isNew = true;

		for(Integer a : add)
		{
			for(Integer i : neighbours)
			{
				if (i.equals(a)) isNew = false;	
			}

			if(isNew == true) neighbours.add(a);
			
		}
	}

	public void findLowestNeighbour()
	{

		lowestNeighbour = id;

		for(Integer i : neighbours)
		{	
			if(i < lowestNeighbour) lowestNeighbour = i;	
		}

	}

	public int getLowestNeighbour()
	{

		return lowestNeighbour;

	}
	
	public List<Integer> getAllNeighbours()
	{
		
		return neighbours;
		
	}

}
