package aoc2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Puzzle10 {

public static void main(String ... args) throws Exception{
		
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		
		
		String puzzleFilename = "c:\\data\\puzzle2021_10_test.txt";
		
		try
		{
			List<String> lines = Files.readAllLines(Paths.get(puzzleFilename));
		
			int solutionPart1 = 0;
			
			//list used to find solution for part 2
			List<Long> totalValues = new ArrayList<Long>();
			
			//iterate through all lines
			for(String line : lines) {
				
				//read through the line and remove completed chunks
				while(line.contains("()") || line.contains("[]") || line.contains("{}") || line.contains("<>")) {
					
					line = line.replace("()", "");
					line = line.replace("{}", "");
					line = line.replace("[]", "");
					line = line.replace("<>", "");
				}
				
			
				//for part 1 get all lines that are invalid. ie. containing a closed char 
				if((line.contains(")") || line.contains("]") || line.contains("}") || line.contains(">"))){
				
					//iterate through the list to see what's the invalid 
					for(char c : line.toCharArray()) {
					
						if(c == ')') {
							solutionPart1 += 3;
							break;
						}
						else if(c == ']') {
							solutionPart1 += 57;
							break;
						}
						else if(c == '}') {
							solutionPart1 += 1197;
							break;
							
						}else if(c == '>') {
							solutionPart1 += 25137;
							break;

						}
					}
				}else {
					
					//for part 2 we get all of the lines that are incomplete (ie. not containing closed char)
					String autocompleteStr = "";
					//iterate through the string backwards to get the string that completes the line
					//it's not necessary to get the autocomplete str but I needed it for debugging.
					
					long score = 0;
					for(int j= line.length()-1; j>=0; j--) {
						
						score *= 5;
						switch(line.charAt(j)) {
						
							case '(':
								autocompleteStr += ")";
								score += 1;
								break;
								
							case '[':
								autocompleteStr += "]";
								score += 2;
								break;
								
							case '{':
								autocompleteStr += "}";
								score += 3;
								break;
							case '<':
								autocompleteStr += ">";
								score += 4;
								break;
							default: break;
						}
						
					}
					
					totalValues.add(score);
					
					
				}
			}
			
			System.out.println("solutionPart1:  " + solutionPart1);
			
			//sort long scores and get median score
			Collections.sort(totalValues);
			System.out.println("solutionPart2:  " + totalValues.get(totalValues.size()/2));
			
			Timestamp endTime = new Timestamp(System.currentTimeMillis());
			
			//String timeLapsed = "" + Utils.convertToReadableTime(((endTime.getTime() - startTime.getTime()) / 1000));
			System.out.println("Processing Duration: " + ( endTime.getTime() - startTime.getTime()) + "msec");
			
		}catch(IOException ioe) {
			
			ioe.printStackTrace();
			
		}


}
	
	
}
