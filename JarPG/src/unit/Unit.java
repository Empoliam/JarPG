package unit;

public class Unit {

	int HP, cHP, MP, cMP, ATT, DEF;
	
	Unit(int HPi, int MPi, int ATTi, int DEFi)
	{
		
		HP = HPi;
		cHP = HPi;
		MP = MPi;
		cMP = MPi;
		ATT = ATTi;
		DEF = DEFi;
		
	}
	
	public int getHP(){ return HP; }
	public int getcHP(){ return cHP; }
	public int getMP(){ return MP; }
	public int getcMP(){ return cMP; }
	public int getATT(){ return ATT; }
	public int getDEF(){ return DEF; }
	
}
