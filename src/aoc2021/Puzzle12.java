package aoc2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Puzzle12 {

	
	public static void main(String ... args) throws Exception {  

		String puzzleFilename = "c:\\data\\puzzle2021_12.txt";
		List<String> lines = Files.readAllLines(Paths.get(puzzleFilename));
		
		//initialize map 
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();   
		
		for (String line : lines) {  
			String[] data = line.split("-");  
			
			if(!map.containsKey(data[0]))
				map.put(data[0], new ArrayList<String>());
			
			map.get(data[0]).add(data[1]);
			
			
			//all paths are bi directional except if it starts with start 
			//or ends with end.
			if(!(data[0].equals("start") || data[1].equals("end")) ) {
				
				if(!map.containsKey(data[1]))
					map.put(data[1], new ArrayList<String>());
				map.get(data[1]).add(data[0]);
				
			}
		}  
		
		//create a path starting with start
		ArrayList<String> path = new ArrayList<String>(Arrays.asList("start"));

//		System.out.println("part 1: " + countPathPart1(path, map));

		
		System.out.println("part 1: " + countPaths(path, map, false));  
		System.out.println("part 2: " + countPaths(path, map, true));  
		
		
		
	}  
	
	//ugh.. another exercise in recursive functions
	//after adding the boolean for part 2, I found that I can also use this function 
	//for part 1
	public static int countPaths(ArrayList<String> path, HashMap<String, List<String>> map, boolean allowTwice) {  
		
		//if the path reaches end it is a valid path
		if (path.get(path.size() - 1).equals("end")) {  
			
			//debugging print path
			for(int i=0; i< path.size(); i++) {
				System.out.print(path.get(i) + "-");
			}
			System.out.println("");
			return 1;  
		}
		
		
		int cnt = 0;  
		
		//get the last added cave
		String lastAddedCave = path.get(path.size() - 1);
		
		//iterate through each possible next cave
		for (String nextCave : map.get(lastAddedCave)) {  
			
			//check the case of nextCave
			//if uppercase you can add multiple times
			if (StringUtils.isAllUpperCase(nextCave)){  
				path.add(nextCave);  
				cnt += countPaths(path, map, allowTwice);  
				path.remove(path.size() - 1);  
			}else {
				
				//if it's lowercase 
				//only add if it's already been visited
				//for part 1
				if (!path.contains(nextCave)){  
					path.add(nextCave);  
					cnt += countPaths(path, map, allowTwice);  
					path.remove(path.size() - 1);  
				}
				
				//this is added for part 2
				//allow to visit one small cave twice
				else if (allowTwice ) {  
					path.add(nextCave);  
					cnt += countPaths(path, map, false);  
					path.remove(path.size() - 1);  
				}				
			}
			  
		}
		return cnt;  
	}  
	
	
//	public static int countPathPart1(ArrayList<String> path, HashMap<String, List<String>> map) {  
//		
//		//if the path reaches end it is a valid path
//		if (path.get(path.size() - 1).equals("end")) {  
//			
//			//debugging print path
////			for(int i=0; i< path.size(); i++) {
////				System.out.print(path.get(i) + "-");
////			}
////			System.out.println("");
//			return 1;  
//		}
//		
//		
//		int cnt = 0;  
//		
//		//get the last added cave
//		String lastAddedCave = path.get(path.size() - 1);
//		
//		//iterate through each possible next cave
//		for (String nextCave : map.get(lastAddedCave)) {  
//			
//			//check the case of nextCave
//			//if uppercase you can add multiple times
//			if (StringUtils.isAllUpperCase(nextCave)){  
//				path.add(nextCave);  
//				cnt += countPathPart1(path, map);  
//				path.remove(path.size() - 1);  
//			}else {
//				
//				//if it's lowercase 
//				//only add if it's already been visited
//				if (!path.contains(nextCave)){  
//					path.add(nextCave);  
//					cnt += countPathPart1(path, map);  
//					path.remove(path.size() - 1);  
//				}
//			}
//			  
//		}
//		return cnt;  
//	}  
	
	
	
}
