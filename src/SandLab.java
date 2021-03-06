import java.awt.*;
import java.util.*;

public class SandLab
{
	// Step 4,6
	// add constants for particle types here
	public static final int EMPTY = 0;
	public static final int METAL = 1;
	public static final int SAND = 2;
	public static final int WATER = 3;
	public static final int GRASS = 4;

	// do not add any more fields below
	private int[][] grid;
	private SandDisplay display;

	/**
	 * Constructor for SandLab
	 * 
	 * @param numRows
	 *            The number of rows to start with
	 * @param numCols
	 *            The number of columns to start with;
	 */
	public SandLab(int numRows, int numCols)
	{
		String[] names;
		// Change this value to add more buttons
		// Step 4,6
		names = new String[5];
		// Each value needs a name for the button
		names[EMPTY] = "Empty";
		names[METAL] = "Metal";
		names[SAND] = "Sand";
		names[WATER] = "Water";
		names[GRASS] = "Grass";

		// 1. Add code to initialize the data member grid with same dimensions
		grid = new int[numRows][numCols];

		display = new SandDisplay("Falling Sand", numRows, numCols, names);
	}

	// called when the user clicks on a location using the given tool
	private void locationClicked(int row, int col, int tool)
	{
		// 2. Assign the values associated with the parameters to the grid
		grid[row][col] = tool;
	}

	// copies each element of grid into the display
	public void updateDisplay()
	{
		// Step 3
		// Hint - use a nested for loop
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				Color grey = Color.GRAY;
				Color black = Color.BLACK;
				Color yellow = Color.YELLOW;
				Color blue = Color.BLUE;
				Color green = Color.GREEN;
				if (grid[i][j] == EMPTY)
				{
					display.setColor(i, j, black);
				} else if (grid[i][j] == METAL)
				{
					display.setColor(i, j, grey);
				} else if (grid[i][j] == SAND)
				{
					display.setColor(i, j, yellow);
				} else if (grid[i][j] == WATER)
				{
					display.setColor(i, j, blue);
				} else
				{
					display.setColor(i, j, green);
				}
			}
		}
	}

	// Step 5,7
	// called repeatedly.
	// causes one random particle in grid to maybe do something.
	public void step()
	{
		int randRow = (int)(Math.random() * grid.length - 1); 
		int randCol = (int)(Math.random() * grid[0].length - 1);
		
		Random rand = new Random();
		int direction = rand.nextInt(2);
		  
		if(grid[randRow][randCol] == SAND && grid[randRow + 1][randCol] == EMPTY) 
		{
			grid[randRow][randCol] = EMPTY;
			grid[randRow + 1][randCol] = SAND;
		}
		
		if(grid[randRow][randCol] == SAND && grid[randRow + 1][randCol] == WATER) 
		{
			grid[randRow][randCol] = WATER;
			grid[randRow + 1][randCol] = SAND;
		}
		
		if(grid[randRow][randCol] == WATER && grid[randRow + 1][randCol] == EMPTY) 
		{
			grid[randRow][randCol] = EMPTY;
			grid[randRow + 1][randCol] = WATER;
			
			if(randCol -1 != -1 && direction == 0 && grid[randRow + 1][randCol - 1] == EMPTY) 
			{
				grid[randRow + 1][randCol - 1] = WATER;
				grid[randRow + 1][randCol] = EMPTY;
			}else if(direction == 1 && grid[randRow + 1][randCol + 1] == EMPTY)
			{
				grid[randRow + 1][randCol + 1] = WATER;
				grid[randRow + 1][randCol] = EMPTY;
			}else 
			{
				
			}
			}
		}
	// Remember, you need to access both row and column to specify a spot in the array
	// The scalar refers to how big the value could be
	// int someRandom = (int) (Math.random() * scalar)
	// remember that you need to watch for the edges of the array

	// do not modify this method!
	public void run()
	{
		while (true) // infinite loop
		{
			for (int i = 0; i < display.getSpeed(); i++)
			{
				step();
			}
			updateDisplay();
			display.repaint();
			display.pause(1); // wait for redrawing and for mouse
			int[] mouseLoc = display.getMouseLocation();
			if (mouseLoc != null) // test if mouse clicked
			{
				locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
			}
		}
	}
}
