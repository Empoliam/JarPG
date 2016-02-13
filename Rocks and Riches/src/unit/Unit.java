package unit;

import java.io.Serializable;

public abstract class Unit implements Serializable 
{

	final static long serialVersionUID = 1L;
	
	String fName, lName;
	int HP, cHP, MP, cMP;
	
	Unit(int HPi, int MPi)
	{
		
		HP = HPi;
		cHP = HPi;
		MP = MPi;
		cMP = MPi;
		
	}
	
	Unit(String fName, String lName ,int HP, int MP)
	{
		
		this.HP = HP;
		this.cHP = HP;
		this.MP = MP;
		this.cMP = MP;

		
	}
	
	public int getHP(){ return HP; }
	public int getcHP(){ return cHP; }
	public int getMP(){ return MP; }
	public int getcMP(){ return cMP; }
	
}
