import java.util.*;
import java.io.*;

public class NewLetterFrequency {
	
	/*
	 * Choose input (line, file, quit)
	 * input chosen option (string, filename)
	 * 
	 * if string then
	 * 		take input
	 * 		convert to lower-case
	 * else-if file then
	 * 		open file
	 * 		put contents into a single string
	 * 		convert to lower-case
	 * end-if
	 * if resulting input != blank
	 * 		create alphabet array[27]
	 * 		convert input to char array
	 * 		for each char in char array
	 * 			if char = a-z
	 * 				alphabet[char]++;
	 * 			if char = punctuation
	 * 				punctuation counter++
	 * 			if char = ' '
	 * 				space counter++
	 * 			if char = anything else
	 * 				other counter++
	 * 		end-for
	 * 		create alphabet frequencies array
	 * 		for each char in alphabet[]
	 * 			alphabet frequencies[char] = char occurances / (totalchars - number of everything thats not a-z)
	 * 		end-for
	 * 		print out data
	 * 		print out bar chart
	 * end-if
	 *
	 */

	public static void main(String[] args) {
		//define scanner
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			do {
				System.out.println("String input [1], Parse File [2] or Quit [0]?");
				choice = sc.nextInt();
				if (choice != 1 && choice != 2 && choice != 0)
					System.out.println("Invalid option, please enter a value of 1, 2 or 3");
				System.out.println();
			} while (!(choice == 1 || choice == 2 || choice == 0));
			//clear the scanner ready for the next input (nextInt doesn't skip the scanner to the next line automatically)
			sc.nextLine();
			//make sure input is initialized so everything doesn't throw a fit
			String input = null;
			
			if(choice == 1) {
				System.out.println("Please enter a string: ");
				
				//take next line input and convert it to lower-case
		        input = sc.nextLine().toLowerCase();
		        
			} else if (choice == 2) {
				do {
					try {
						System.out.println("Please enter a filename: ");
						String file = sc.nextLine(), d;
						System.out.println();
						//reset the input
						input = "";
				        
				        FileReader a = new FileReader(file);
				        BufferedReader b = new BufferedReader(a);
				        
				        while ((d = b.readLine()) != null) {
				        	input += d;
				        }
				        
				        input = input.toLowerCase();
				        
				        b.close();
					} catch (IOException e) {
						System.out.println("Error: File Not Found (No such file or directory)");
					}
				} while (input != "");
			} else if (choice == 0) {
				break;
			} else {
				input = "";
			}
			
			if(input != "" && input != null) {
		        //initialize punctuation variable
		        int totalPunctuation = 0, totalSpaces = 0, totalOtherChars = 0;
		        
		        //convert input to char array
		        char charArr[] = input.toCharArray();
		        
		        int alphabet[] = new int[27], lettIndex;
		        
		        for (char c : charArr) {
		        	if(isAtoZ(c) && c != ' ') {
		        		lettIndex = ((int) c) - 97;
		        		alphabet[lettIndex]++;
		        	} else if (isPunctuation(c) && c != ' ') {
		        		totalPunctuation++;
		        	} else if (c == ' ') {
		        		totalSpaces++;
		        	} else {
		        		totalOtherChars++;
		        	}
		        }
		        
		        int count = 0;
		        
		        double alphabetFreq[] = new double [alphabet.length];
				count = 0;
				double totalLength = (double) (input.length() - (totalPunctuation + totalSpaces + totalOtherChars));
				
				//put frequencies in new array
				for (int b : alphabet) {
					if(b > 0)
						alphabetFreq[count] = (Math.round(((double) b / totalLength) * 100.0) / 100.0) ;
		        	count++;
		        }
				
				count = 0;
		        for (int b : alphabet) {
		        	if (b > 0)
		        		System.out.println(((char) (count + 97)) + ": " + alphabetFreq[count] + " (" + b + " times)");
		        	count++;
		        }
		        System.out.println();
		        System.out.println("Punctuation: " + totalPunctuation);
		        System.out.println();
		        toBarChart(alphabetFreq);
	        
			} else {
				System.out.println("Error - Invalid input: The program quit");
			}
			
			System.out.println();
			
		} while (choice != 0);
		
		sc.close();
	}
	
	
    private static boolean isAtoZ(char input) {
        if (input >= (char) 97 && input <= (char) 122) 
            return true;
        return false;
    }
    
    private static boolean isPunctuation(char input) {
    	if ((input >= (char) 33 && input <= (char) 47) || (input >= (char) 58 && input <= (char) 64) || 
    			(input >= (char) 91 && input <= (char) 96) || (input >= (char) 123 && input <= (char) 126) || input == (char) 8217) //8127 is a formal apostrophe: ( ’ ) as opposed to: ( ' )
    		return true;
		return false;
    	
    }
    
    private static void toBarChart (double input[]) {
		int count = 0;
		double maxLeng = input[0], maxnum = 0;
		for(double i : input) {
			if(String.valueOf(i).length() > String.valueOf(maxLeng).length()) 
				maxLeng = i;
			if(i > maxnum) 
				maxnum = i;
			count++;
		}
		
		count = 0;

		System.out.print(" Character | ");
		System.out.print(" Frequency  ");
		spaces(1, maxLeng);
		System.out.println("|");
		//print lines
		
		for(int i = 0; i < (int) ((maxnum * 100) + 30); i++) { 
            //System.out.print("-");
            System.out.print("=");
		}
		
        System.out.println(); //new line
        
		char current;
        		
		for (double i : input) {
			if (i > 0) {
				System.out.print("     ");
				//print counter
				current = (char) (count + 97);
				System.out.print(current);
				System.out.print("     |     ");
				spaces(i, maxLeng);
				//print value
				System.out.print(i);
				System.out.print("     | ");
				
				//for 0 - value print char
				for(int x = 0; x < (int) (i * 100); x++) {
					System.out.print("*");
				}
				
				System.out.println(); //new line
			}
			count++;
		}
    }
    
	private static void space(int x) {
        for(int y=0; y<x; y++)
            System.out.print(' ');
    }
	
    private static void spaces(double x, double y) {
        int spaces = (int) (String.valueOf(y).length()) - (String.valueOf(x).length());
        space(spaces);
    }

}