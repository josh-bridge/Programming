import java.util.*;
import java.io.*;

public class NewLetterFrequency {

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
	        int totalPunctuation = 0, totalSpaces = 0;
	        
	        //convert input to char array
	        char charArr[] = input.toCharArray();
	        
	        
	        int alphabet[] = new int[27], lettIndex;
	        
	        for (char c : charArr) {
	        	if(isAtoZ(c) && c != ' ') {
	        		lettIndex = ((int) c) - 97;
	        		alphabet[lettIndex] += 1;
	        	} else if (isPunctuation(c) && c != ' ') {
	        		totalPunctuation++;
	        	} else if (c == ' ') {
	        		totalSpaces++;
	        	}
	        }
	        
	        int count = 0;
	        
	        /*
	        
	        //initialize arrayList for arrayList of valid characters
	        ArrayList<Character> charList = new ArrayList<Character>();
	        
	        //sort through characters and find valid ones, also count punctuation
	        for (char c : charArr) {
	        	if(isAtoZ(c) && c != ' ') {
	        		charList.add(c);
	        	} else if (isPunctuation(c) && c != ' ') {
	        		totalPunctuation++;
	        	} else if (c == ' ') {
	        		totalSpaces++;
	        	} else if (!isAtoZ(c)) {
	        		//do nothing if not a-z
	        	}
	        }
	        
	        
	        //put final arrayList values into regular char array
	        Character[] validChar = charList.toArray(new Character[charList.size()]);
	        
	        char validChars[] = new char[validChar.length]; 
	        
	        
	    	//convert array from Character to char
	    	for (Character y : validChar) {
	    		validChars[count] = (char) y;
	    		count++;
	    	}
	        
	        
	        //create new array with letters in alphabetical order
	        char albetChars[] = toAlphabetical(validChars);
	        
	        //array with count of each letter
	        int alphabet[] = countChars(albetChars);
	        
	        */
	        
	        
	        
	        double alphabetFreq[] = new double [alphabet.length];
			count = 0;
			double totalLength = (double) (input.length() - (totalPunctuation + totalSpaces));
			
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
	        
			//ArrayList for getting only used letters
	        //transfer to normal array
	        //count frequency of each letter
	        //one array for letters
	        //one array for lengths
	        //when sorting, duplicate both, when swapping one, swap the other
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
    
    
    /*
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
    
    private static void swapChars (char[] input, int x, int y) {
    	char temp = input[x];
    	input[x] = input[y];
    	input[y] = temp;
    }
    */
    
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