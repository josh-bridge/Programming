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
				choice = Character.getNumericValue(sc.next().trim().charAt(0));
				if (choice != 1 && choice != 2 && choice != 0)
					System.out.println("----------Invalid option, please enter a value of 1, 2 or 0----------");
				System.out.println();
			} while (!(choice == 1 || choice == 2 || choice == 0));
			//clear the scanner ready for the next input (nextInt doesn't skip the scanner to the next line automatically)
			sc.nextLine();
			
			String input;
			
			if(choice == 1) {
				System.out.println("Please enter a string: ");
				
				//take next line input and convert it to lower-case
		        input = sc.nextLine().trim();
		        
			} else if (choice == 2) {
				System.out.println("Please enter a filename: ");
				String file = sc.nextLine().trim();
				input = readFile(file);
			} else if (choice == 0) {
				break;
			} else {
				input = "";
			}
			
			if(input.isEmpty() || input == null) {
				System.out.println("-----Program Error - Empty or Invalid input: The program failed to run. It will restart.-----");
			} else {
				double alphabetFreq[] = analyzeText(input);
				printOutput(alphabetFreq);
			}
			
			System.out.println();
			
		} while (choice != 0);
		
		sc.close();

		System.out.println("===============================Program Quit==============================");
	}

	
	
	
    private static int totalPunctuation = 0;
	private static int totalSpaces = 0;
	private static int totalOtherChars = 0;
	private static int zeroCount = 0;
	private static int alphabet[] = new int[26];
	
	private static double[] analyzeText(String input) {
		//initialize punctuation variable
        
        //convert input to char array
        char charArr[] = input.toLowerCase().toCharArray();
        
        int lettIndex;
        
        for (char c : charArr) {
        	if(isAtoZ(c) && c != ' ') {
        		lettIndex = ((int) c) - 'a';
        		alphabet[lettIndex]++;
        	} else if (isPunctuation(c) && c != ' ') {
        		totalPunctuation++;
        	} else if (c == ' ') {
        		totalSpaces++;
        	} else {
        		totalOtherChars++;
        	}
        }
		
		return countToFreq(input);
	}
	
	private static void clearAlphabet (){
		int count = 0;
		for(int i : alphabet) {
			if (i > 0)
				alphabet[count] = 0;
			count++;
		}
		
		totalPunctuation = 0;
		totalSpaces = 0;
		totalOtherChars = 0;
		zeroCount = 0;
	}
	
	
	private static double[] countToFreq (String input) {
		int count = 0;
        
        double output[] = new double [alphabet.length];
		count = 0;
		double totalLength = (double) (input.length() - (totalPunctuation + totalSpaces + totalOtherChars));
		
		//put frequencies in new array
		for (int b : alphabet) {
			if(b > 0)
				output[count] = (Math.round(((double) b / totalLength) * 100.0) / 100.0) ;
			else {
				zeroCount++;
			}
        	count++;
        }
		
		return output;
	}
	
	private static void printOutput (double input[]) {
		printFreqs(input);
		System.out.println();
        toBarChart(input);

		clearAlphabet();
		

        System.out.println();
		System.out.println("------------------------------Program Ended------------------------------");
	}
	
	private static void printFreqs (double input[]) {
		 System.out.println(); //clear a line for the output

			int count = 0;
	        for (int b : alphabet) {
	        	if (b > 0)
	        		System.out.println(((char) (count + 'a')) + ": " + input[count] + " (" + b + " occurances)");
	        	count++;
	        }
	        
	        if (zeroCount > 0) {
	     		System.out.println();
	     		System.out.println(zeroCount + " letters were omitted (they occured zero times)");
	        }
     		System.out.println();
	        System.out.println("Punctuation count: " + totalPunctuation);
     	
	}
	
	private static String readFile (String fileName) {
		String output = "", d;
		try {
	        FileReader a = new FileReader(fileName);
	        BufferedReader b = new BufferedReader(a);
	        String eol = System.getProperty("line.separator");
	        
	        while ((d = b.readLine()) != null) {
	        	output += d + eol;
	        }
	        
	        b.close();
	        
	        output = output.toLowerCase();
	        
	        return output;
	        
		} catch (IOException e) {
			System.out.println("Error: File Not Found (No such file or directory)");
			System.out.println();
			return "";
		}
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