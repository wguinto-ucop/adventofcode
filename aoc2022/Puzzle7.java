package aoc2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import lombok.Data;

public class Puzzle7 {
	
	public static final String ROOT_DIR = "/";
	public static final String UP_DIR = "..";
	public static final String CHG_DIR_CMD = "$ cd ";
	public static final String DIR_DESC = "dir ";
	
	
	@Data
	public class directory{
		
		public directory(String name, String parent) {
			
			this.name = name;
			this.parent = parent;
		}
		
		private String name;
		private String parent;
		private long size;
		
	}
	
	public static String getPath(String currDir, String dirName) {
	
		return currDir.equals(ROOT_DIR)?(currDir += dirName):(currDir + "/" + dirName);
	}
	
	public static void main(String ... args) throws Exception{
			
		Puzzle7 puzzle = new Puzzle7();
		
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		
		String puzzleFilename = "c:\\data\\puzzle2022_7_test.txt";
		
		//This is a map of all the directories keyed by dir name
		Map<String, directory> directoryMap = new HashMap<String, directory>();
			
		try
		{
			
			List<String> lines = Files.readAllLines(Paths.get(puzzleFilename));
			
			//create root directory
			directoryMap.put(Puzzle7.ROOT_DIR, puzzle.new directory(Puzzle7.ROOT_DIR, null));
			
			
			String currentDirectory ="";
			
			//iterate through all lines
			for(String line : lines) {
				
				//change directory
				if(line.startsWith(Puzzle7.CHG_DIR_CMD)) {
				
					String newDir = line.substring(Puzzle7.CHG_DIR_CMD.length());
					
					switch(newDir) {
					
						case Puzzle7.ROOT_DIR:
							currentDirectory = "/";
							break;
						
						case Puzzle7.UP_DIR:
							//go back one dir or root if curr dir is root sub dir
							currentDirectory = (currentDirectory.lastIndexOf('/') == 0)?Puzzle7.ROOT_DIR:currentDirectory.substring(0, currentDirectory.lastIndexOf('/'));
							break;
						
						default:
							//go to new dir
							currentDirectory = getPath(currentDirectory, newDir);
							break;
								
					}
				}else if(line.startsWith("$")) {
					
					//ignore other commands
					
				}else {
					
					//create an entry in the directory map
					if(line.startsWith(Puzzle7.DIR_DESC)) {
						
						String directoryName = line.substring(Puzzle7.DIR_DESC.length());
						
						if(!directoryMap.containsKey(getPath(currentDirectory, directoryName))){
							directoryMap.put(getPath(currentDirectory, directoryName), puzzle.new directory(directoryName, currentDirectory));
						}
						
					}else {
						
						String[] fileData = line.split(" ");
						Long fileSize = Long.parseLong(fileData[0]);
						
						//add file size to current directory 
						directoryMap.get(currentDirectory).size += fileSize;
						
						if(directoryMap.get(currentDirectory).parent != null) {
							
							String parentPath = directoryMap.get(currentDirectory).parent;
							
							//add size to parent dir(s)
							while(parentPath.length() > 0) {
								
								directoryMap.get(parentPath).size += Long.parseLong(fileData[0]);
								parentPath = parentPath.substring(0, parentPath.lastIndexOf('/'));
							}
							
							//add size to root
							directoryMap.get("/").size += Long.parseLong(fileData[0]);
						}
						
					}
				}
			}
			
			//Problem 1 - 	Get all directories that are less than or equal to 100000 bytes
			//				add the total of those directories and print the answer
			
			long total = 0;
			for(String directory: directoryMap.keySet()) {
				
				//directories that are <= 100K
				if(directoryMap.get(directory).size <= 100000)
					total += directoryMap.get(directory).size;
			}
			
			//print solution 1
			System.out.println("solutionPart1:  " + total);
			
			//Problem 2 - 	Total space is 700K, Free space needed is 300K
			//				Calculate the current free space and 
			//				find the smallest directory that can be deleted 
			//				and meet the 300K necessary  
			
			//current free space = total space - root dir space 
			long currFreeSpace = 70000000 -  directoryMap.get(Puzzle7.ROOT_DIR).size;
			
			//Free space needed is 300K
			long neededSpace = 30000000 - currFreeSpace;
			
			//find smallest directory that can be deleted and meet 300K space requirement 
			long minSpace = 30000000;
			for(String directory: directoryMap.keySet()) {
				
				if(directoryMap.get(directory).size > neededSpace && directoryMap.get(directory).size < minSpace) {
					
					minSpace = directoryMap.get(directory).size;
				}
			}
			
			//print size of the directory.
			System.out.println("solutionPart2:  " + minSpace);
			
			
			Timestamp endTime = new Timestamp(System.currentTimeMillis());
			
			System.out.println("Processing Duration: " + ( endTime.getTime() - startTime.getTime()) + "msec");
			
		}catch(IOException ioe) {
			
			ioe.printStackTrace();
			
		}
	
	
	}
	
	
}



