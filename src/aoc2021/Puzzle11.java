package aoc2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

public class Puzzle11 {

	//int grid of all octupuses
	static int[][] octupus = new int[10][10];
	
	//you have to keep track of if the flash happened within the step
	//this was the tricky part for me since I had to figure out which 0s  
	//had to stay and which had to be incremented to 1
	static boolean[][] flashed = new boolean[10][10];
	
	//method used to increment the grid
	//more recursive functions
	public static void incrementOctupus(int x, int y) {
		
		//check grid boundaries
		if(x>9 || x<0 || y>9 || y<0)
			return;
		
		//check if octupus flashed already
		if(flashed[x][y])
			return;
		
		//flash
		if(octupus[x][y] == 9) {
			
			octupus[x][y] = 0;
			flashed[x][y] = true;
			
			incrementOctupus(x-1, y);
			incrementOctupus(x-1, y-1);
			incrementOctupus(x-1, y+1);
			incrementOctupus(x, y-1);
			incrementOctupus(x, y+1);
			incrementOctupus(x+1, y);
			incrementOctupus(x+1, y-1);
			incrementOctupus(x+1, y+1);
			
		}else {
			
			//increment the value
			octupus[x][y] ++;
		}
		
		
	}
	
	public static void printGrid() {
		
		//print grid
		for(int y = 0; y<10; y++) {
			for(int x = 0; x<10; x++) {
						
				System.out.print(octupus[y][x]);
			}
			System.out.println();
		}
	}
	
	

	public static void main(String ... args) throws Exception{
		
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		
		
		String puzzleFilename = "c:\\data\\puzzle2021_11.txt";
		
		try
		{
			List<String> lines = Files.readAllLines(Paths.get(puzzleFilename));
			
			//initialize grid
			int row = 0;
			for(String line : lines) {
				
				for(int i=0; i< line.length(); i++)
					octupus[row][i] = Integer.parseInt("" + line.charAt(i));
				
				row ++;
			}
			
			//start of part 1
			
			int days = 100;
			int flashes = 0;
			//for debugging
			printGrid();
			
			
			for(int i = 0; i< days; i++) {
				
				//reset flashes each day
				for(int y = 0; y<10; y++) {
					for(int x = 0; x<10; x++) {
						flashed[y][x] = false;
					}
				}
				
				//increment grid
				for(int y = 0; y<10; y++) {
					for(int x = 0; x<10; x++) {
						
						incrementOctupus(x, y);
					}
				}
				
				//count flashes
				for(int y = 0; y<10; y++) {
					for(int x = 0; x<10; x++) {
						
						if(octupus[y][x] == 0)
							flashes ++;
					}
				}
			}
			//printGrid();
			System.out.println("Part 1 Solution: " + flashes);
			
			// end of part 1
			
			//part 2 - find where all 100 octupuses flashed
			//if executing this comment out part 1 or remember to add 100 steps to the answer
			int flashCount = 0;
			int step = 1;
			
			while(flashCount != 100) {
				
				//reset flashed
				for(int y = 0; y<10; y++) {
					for(int x = 0; x<10; x++) {
						flashed[y][x] = false;
					}
				}
				
				//increment grid
				for(int y = 0; y<10; y++) {
					for(int x = 0; x<10; x++) {
						
						incrementOctupus(x, y);
					}
				}
				
				//count flashes
				for(int y = 0; y<10; y++) {
					for(int x = 0; x<10; x++) {
						
						if(octupus[y][x] == 0)
							flashCount ++;
					}
				}
				
				//check count
				if(flashCount == 100)
					break;
				else {
					//increment step and reset count
					flashCount = 0;
					step++;
				}
				
				
				
			}
			//print grid
			printGrid();
			
			System.out.println("Part 2 Solution: " + step);
			
			Timestamp endTime = new Timestamp(System.currentTimeMillis());
			System.out.println("Processing Duration: " + ( endTime.getTime() - startTime.getTime()) + "msec");
			
		}catch(IOException ioe) {
			
			ioe.printStackTrace();
			
		}


}
	
}
