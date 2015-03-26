

public class LetterFreq {

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
	
	private static String input;
	
	public LetterFreq(String stringInput) {
		input = stringInput;
	}
		
    private static int totalPunctuation = 0;
	private static int totalSpaces = 0;
	private static int totalOtherChars = 0;
	private static int zeroCount = 0;
	
	public void letterFrequency() {
		int alphabet[] = countChars();
		printOutput(alphabet, countToFreq(alphabet));
	}
	
	private static int[] countChars() {
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
	

	
	private static double[] countToFreq (int alphabet[]) {
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
	
	private static void printOutput (int alphabet[], double input[]) {
		printFreqs(alphabet, input);
		System.out.println();
        charBarChart(input);

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
    
    private static boolean isPunctuation(char cInput) {
    	if ((cInput >= (char) 33 && cInput <= (char) 47) || (cInput >= (char) 58 && cInput <= (char) 64) || 
    			(cInput >= (char) 91 && cInput <= (char) 96) || (cInput >= (char) 123 && cInput <= (char) 126) || cInput == (char) 8217 || 
    					cInput == (char) 8212) //8127 is a formal apostrophe: ( ’ ) as opposed to: ( ' ); 8212 is dash(–) rather than hyphen(-) 
    		return true;
		return false;
    }
    
    private static void charBarChart (double input[]) {
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
		System.out.println("| '*' = 1%");
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
