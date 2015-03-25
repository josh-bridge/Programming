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
				letterFrequency(input);
				printWordAnalysis(input);
				
				System.out.println();
				System.out.println("-------------------------------Output Ended------------------------------");
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
	
	private static void letterFrequency(String input) {
		int alphabet[] = countChars(input);
		printOutput(alphabet, countToFreq(alphabet, input));
	}
	
	private static int[] countChars(String input) {
		//initialize punctuation variable
        
        //convert input to char array
        char charArr[] = input.toLowerCase().toCharArray();
        int alphabet[] = new int[26];
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
		
		return alphabet;
	}
	

	
	private static int[] removeDuplicates(int input[]) {
		Set<Integer> mySet = new HashSet<Integer>();
		
		for(int x : input) {
			mySet.add((Integer) x);
		}
		
		int output[] = new int[mySet.size()], count = 0;
		
		for(Integer y : mySet) {
			output[count] = (int) y;
			count++;
		}
		
		return output;
	}
	
	private static int[] countOccurences(int inputA[], int inputB[]) {
		
		
		//count how many of each num in output array are in the input array
				int output[] = new int[inputA.length];
				int count = 0;
				
				
				for(int x : inputA) {
					for(int z=0; z < inputB.length; z++) {
						if(inputB[z] == x)
							output[count]++;
					}
					count++;
				}
				
		return output;
	}
	
	private static double[] countToFreq (int alphabet[], String input) {
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
	
	public static void printWordAnalysis(String input) {
		int lengths[] = wordLengths(input);
		int sortedLengths[] = sortIntArray(lengths);
		
		System.out.println();
		System.out.println("Mean Word Length: " + meanLength(lengths));
		System.out.println("Median Word Length: " + medianLength(sortedLengths));
		System.out.println("Mode Word Length: " + modeLength(sortedLengths));
		
		int inputNoDuplicates[] = removeDuplicates(sortedLengths);
		int wordLengthOccurences[] = countOccurences(inputNoDuplicates, sortedLengths);
		int count = 0;
		
		for(int i : wordLengthOccurences) {
			System.out.println(inputNoDuplicates[count] + ": " + i + " occurences");
			count++;
		}
		
		
	}
	
	
	private static int[] wordLengths(String input) {
		//run through and remove everything that's not a letter
		//make sure only one space between words
		//split string via " "
		//put each length into separate output array
		input = input.replace(System.getProperty("line.separator"), " ");
		
		char charArr[] = input.toLowerCase().toCharArray();
        String finalString = "";
        
        for (char c : charArr) {
        	if(isAtoZ(c) || c == ' ' ) {
        		finalString += c;
        	}
        }
        //make sure only one space between words using regex
        //doing it after is better as removing punctuation may leave double spaces
        finalString = finalString.replaceAll("\\s+", " ");
        
        String words[] = finalString.split(" ");
        int count = 0, lengths[] = new int [words.length];
        
        for(String x : words) {
        	lengths[count] = x.length();
        	count++;
        }
		
		return lengths;
	}
	
	private static double meanLength(int input[]) {
		int total = 0;
		for(int i : input) {
			total += i;
		}
		
		double result = (Math.round(((double) total / (double) input.length) * 100.0) / 100.0);
		
		return result;
	}
	
	private static double medianLength(int input[]) {
		
		double median;
		if (input.length % 2 == 0) {
			// even number of words
			median = 0.5 * (input[input.length / 2] + input[input.length / 2 + 1]);
		} else
			// odd number of words
			median = input[input.length / 2 + 1];
		return median;

	}
	
	private static int[] sortIntArray(int[] input) {

		int output[] = new int[input.length];
		for (int i = 0; i < input.length; i++)
			output[i] = input[i];
		
		boolean swapped;
		int max = output.length;
		do {
			swapped = false;
			for (int i = 1; i < max; i++) {
				if (output[i - 1] > output[i]) {
					swap(output, i - 1, i);
					swapped = true;
				}
			}
			max--;
		} while (swapped);
		return output;

	}
	
    public static int modeLength(int input[]) {
        int modeLen = 0, bigCount = 0, countNum = 1, count = 0, prevLen = 0;

        for(int l : input) {
            if(prevLen == l && countNum > bigCount && count > 0) {
                countNum++;
                modeLen = prevLen;
                bigCount = countNum;
            } else if (prevLen != l & count > 0) 
                countNum = 1;
            else if (count == 0)
                modeLen = l;
            count++;
            prevLen = l;
        }
        return modeLen;
    }
	
	private static void swap(int[] input, int a, int b) {
		int tmp = input[a];
		input[a] = input[b];
		input[b] = tmp;
	}
	
	private static void printOutput (int alphabet[], double input[]) {
		printFreqs(alphabet, input);
		System.out.println();
        toBarChart(input);

        clearGlobalInts();

	}
	
	private static void printFreqs (int alphabet[], double input[]) {
		System.out.println(); //clear a line for the output

		int count = 0;
        for (int b : alphabet) {
        	if (b > 0)
        		System.out.println(((char) (count + 'a')) + ": " + input[count] + " (" + b + " occurances)");
        	count++;
        }
        System.out.println();
        if (zeroCount > 0) {
     		System.out.println(zeroCount + " letters were omitted (they occured zero times)");
     		System.out.println();
        }
        
        System.out.println("Punctuation count: " + totalPunctuation);
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
	
    private static void spaces(double x, double max) {
        int spaces = (int) (String.valueOf(max).length()) - (String.valueOf(x).length());
        space(spaces);
    }
    
	
	private static void clearGlobalInts (){
		totalPunctuation = 0;
		totalSpaces = 0;
		totalOtherChars = 0;
		zeroCount = 0;
	}
	

}