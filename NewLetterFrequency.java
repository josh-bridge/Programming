import java.util.*;
import java.io.*;

public class NewLetterFrequency {

	public static void main(String[] args) throws FileNotFoundException {
		//define scanner
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			System.out.println("String input [1] or Parse File [2]?");
			choice = sc.nextInt();
			if (choice != 1 && choice != 2)
				System.out.println("Invalid option, please enter a value of 1 or 2");
			System.out.println();
		} while (!(choice == 1 || choice == 2));
		//clear the scanner ready for the next input (nextInt doesn't skip the scanner to the next line automatically)
		sc.nextLine();
		//make sure input is initialised so everything doesn't throw a fit
		String input = null;
		
		if(choice == 1) {
			System.out.println("Please enter a string: ");
			
			//take next line input and convert it to lowercase
	        input = sc.nextLine().toLowerCase();
	        
		} else if (choice == 2) {
			try {
				System.out.println("Please enter a filename: ");
				String file = sc.nextLine(), d;
				input = "";
		        
		        FileReader a = new FileReader(file);
		        BufferedReader b = new BufferedReader(a);
		        
		        while ((d = b.readLine()) != null) {
		            for(int c = 0; c < d.length(); c++) {
		                input += d;
		            }
		        }
		        
		        b.close();
			} catch (IOException e) {
				System.out.println("Error: File Not Found");
			}
		} else {
			input = "";
		}
        
        //initialise punctuation variable
        int totalPunctuation = 0, totalSpaces = 0;
        
        //convert input to char array
        char charArr[] = input.toCharArray();
        
        //initialise arraylist for arraylist of valid characters
        ArrayList<Character> charList = new ArrayList<Character>();
        
        //sort through characters and find valid ones, also count punctuation
        for (char c : charArr) {
        	if(isAtoZ(c) && c != ' ') {
        		charList.add(c);
        	} else if (isPunctuation(c) && c != ' ') {
        		totalPunctuation++;
        	} else if (c == ' ') {
        		totalSpaces++;
        	}
        }
        
        //put final arraylist values into regular char array
        Character[] validChar = charList.toArray(new Character[charList.size()]);
        
        char validChars[] = new char[validChar.length]; 
        int count = 0;
        
    	//convert array from Character to char
    	for (Character y : validChar) {
    		validChars[count] = (char) y;
    		count++;
    	}
        
        
        //create new array with letters in alphabetical order
        char albetChars[] = toAlphabetical(validChars);
        
        //array with count of each letter
        int alphaBet[] = countChars(albetChars);
		count = 0;
		double totalLength = (double) (input.length() - (totalPunctuation + totalSpaces));
		
        for (int b : alphaBet) {
        	if (b > 0)
        		System.out.println(((char) (count + 97)) + ": " + Math.round(((double) b / totalLength) * 100.0) / 100.0 + " (" + b + " times)");
        	count++;
        }
        
        System.out.println("Punctuation: " + totalPunctuation);
        System.out.println();
        toBarChart(alphaBet);
        
		//ArrayList for getting only used letters
        //transfer to normal array
        //count frequency of each letter
        //one array for letters
        //one array for lengths
        //when sorting, duplicate both, when swapping one, swap the other
        
        sc.close();

	}
	
	
    private static boolean isAtoZ(char input) {
        if (input >= (char) 97 && input <= (char) 122) 
            return true;
        return false;
    }
    
    private static boolean isPunctuation(char input) {
    	if ((input >= (char) 33 && input <= (char) 47) || (input >= (char) 58 && input <= (char) 64) || 
    			(input >= (char) 91 && input <= (char) 96) || (input >= (char) 123 && input <= (char) 126)) 
    		return true;
		return false;
    	
    }
    
    private static char[] toAlphabetical(char[] input) {
    	char output[] = input;
    	
    	//sort the char array into alphabetical order
        boolean swapped;
        int max = output.length;
        do {
            swapped = false;
            for (int i = 1; i < max; i++) {
                if (output[i - 1] > output[i]) {
                	swapChars(output, i - 1, i);
                    swapped = true;
                }
            }
            max--;
        } while (swapped);
    	return output;
    }
    
    private static int[] countChars (char input[]) {
    	int alphaBet[] = new int[26];
    	int alphIndex;
        
        for (char i : input) {
        	alphIndex = ((int) i) - 97;
        	alphaBet[alphIndex] += 1;
        }
    	
    	return alphaBet;
    }
    
    /*
    private static void swapNums (int[] input, int x, int y) {
    	int tmp = input[x];
    	input[x] = input[y];
    	input[y] = tmp;
    }
    */
    
    private static void swapChars (char[] input, int x, int y) {
    	char tmp = input[x];
    	input[x] = input[y];
    	input[y] = tmp;
    }
    
    private static void toBarChart (int input[]) {
		int count = 0, maxnum = 0;
		char current;
		for(int i : input) {
			if(i > maxnum) 
				maxnum = i;
		}
		
		//print  "position   value"
		spaces(1, input.length);
		System.out.print("Character | ");
		spaces(1, maxnum);
		System.out.println(" Occurences  |");
		//print lines
		
		for(int i = 0; i < maxnum + 35; i++) { 
            //System.out.print("-");
            System.out.print("=");
		}
		
        System.out.println(); //new line
		for (int i : input) {
			if (i > 0) {
				System.out.print("     ");
				//print counter
				current = (char) (count + 97);
				System.out.print(current);
				System.out.print("     |       ");
				spaces(i, maxnum);
				//print value
				System.out.print(i);
				System.out.print("      | ");
				
				//for 0 - value print char
				for(int x = 0; x < i; x++) {
					System.out.print("*");
				}
				
				System.out.println(); //new line
			}
			count++;
		}
    }
    
	public static void space(int x)
    {
        for(int y=0; y<x; y++)
            System.out.print(' ');
    }
	
    public static void spaces(int x, int y)
    {
        int spaces = (String.valueOf(y).length()) - (String.valueOf(x).length()) ;
        space(spaces);
    }

}
