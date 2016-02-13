package unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import items.Item;

public abstract class Unit implements Serializable 
{

	final static long serialVersionUID = 2L;
	
	public List<Item> inventory = new ArrayList<Item>();
	
	String fName, lName;
	
	Unit()
	{
				
	}
	
	Unit(String fName, String lName)
	{
		
		this.fName = fName;
		this.lName = lName;
		
	}
	
}
