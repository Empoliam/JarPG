package main;

import static main.Main.*;

import java.io.InvalidClassException;

public class Move 
{

	/*
	 * 0:north
	 * 1:west 
	 * 2:south
	 * 3:east
	 */

	public static void move(int direction)
	{

		int reverse = 0; 

		switch(direction)
		{

		case 0:
			if(currenty != 0)
			{				
				currenty --;
				reverse = 2;
			}
			else mainWindow.textarea.append("You can go no further in this direction.\n");
			break;
		case 1:
			if(currentx != 0)
			{
				currentx --;	
				reverse = 3;
				break;
			}
			else mainWindow.textarea.append("You can go no further in this direction.\n");
			break;
		case 2:
			if(currenty != WORLD_SIZE-1)
			{				
				currenty ++;	
				reverse = 0;
			}
			else mainWindow.textarea.append("You can go no further in this direction.\n");
			break;
		case 3:
			if(currentx != WORLD_SIZE-1)
			{
				currentx ++;				
				reverse = 1;
			}
			else mainWindow.textarea.append("You can go no further in this direction.\n");
			break;

		}
		try {
			loadRegion();
		} catch (InvalidClassException e) {
			e.printStackTrace();
		}	
		if(activeRegion.getBiome() == -1)
		{
			mainWindow.textarea.append("You find an expanse of water, and can proceed no further.\n");
			move(reverse);
		}

		player.setXY(currentx, currenty);

		mainWindow.drawPlayer();

	}

}
