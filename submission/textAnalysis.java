import java.util.HashSet;
import java.util.Set;


public class TextAnalysis {
	private static String input;
    private static int totalPunctuation = 0;
	private static int zeroCount = 0;
	
	public TextAnalysis(String stringInput) {
		input = stringInput;
	}
	
	public void analyzeText () {
		if (inputValid()) {
			letterFrequency();
			printWordAnalysis();
			printSentenceAnalysis();
			System.out.println();
			System.out.println("-------------------------------Output Ended------------------------------");
		} else {
			System.out.println("-----Program Error - Empty or Invalid input: The program failed to run properly.-----");
		}
		

	}
	
	public boolean inputValid() {
		
		String fixedInput = trimInput();
		
		if(fixedInput.isEmpty() || fixedInput == null) {
			return false;
		} else {
			int alpha[] = countChars(), count = 0;
			
			for(int y : alpha) {
				count += y;
			}
			
			clearGlobalInts();
			
			if (count == 0) {
				return false;
			} else {
				return true;
			}
		}
		
	}
	
	private static String trimInput() {
		return input.trim();
	}
	
	private void letterFrequency() {
		int alphabet[] = countChars();
		double frequencies[] = countToFreq(alphabet);
		
		printFreqs(alphabet, frequencies);
		
		char mostUsed = mostUsedChar(alphabet);
		
		if (mostUsed != ' ') {
			System.out.println();
			System.out.println("The most used letter was: " + mostUsed);
		}
		
		System.out.println();
        charBarChart(frequencies);

        clearGlobalInts();
	}
	

	
	private static int[] countChars() {
		//initialize punctuation variable
        
        //convert input to char array
        char charArr[] = trimInput().toLowerCase().toCharArray();
        int alphabet[] = new int[26];
        int lettIndex;
        
        for (char c : charArr) {
        	if(isAtoZ(c) && c != ' ') {
        		lettIndex = ((int) c) - 'a';
        		alphabet[lettIndex]++;
        	} else if (isPunctuation(c) && c != ' ') {
        		totalPunctuation++;
        	}
        }
		
		return alphabet;
	}
	
	private static double[] countToFreq (int alphabet[]) {
		int count = 0;
        
        double output[] = new double [alphabet.length], totalLength = 0, temp;
		
		for(int x : alphabet) {
			totalLength+=(double) x;
		}
		
		//put frequencies in new array

		for (int b : alphabet) {
			temp = ((double) b / totalLength);
			if(b > 0) {
				if(temp >= 0.01)
					output[count] = (Math.round(temp * 100.0) / 100.0);
				else
					output[count] = (Math.round(temp * 1000.0) / 1000.0);
			} else {
				zeroCount++;
			}
        	count++;
        }
		
		return output;
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
	
	private static char mostUsedChar(int alphabet[]) {
		char highest = ' ';
		int biggest = 0, count = 0;;
		
		for (int i : alphabet) {
			if (i > biggest) {
				biggest = i;
				highest = (char) (count + 'a');
			}
			count++;
		}
		
		return highest;
	}
	
    private static boolean isAtoZ(char input) {
        return (input >= 'a' && input <= 'z');
    }
    
    private static boolean isPunctuation(char cInput) {
    	return ((cInput >= (char) 33 && cInput <= (char) 47) || (cInput >= (char) 58 && cInput <= (char) 64) || 
    			(cInput >= (char) 91 && cInput <= (char) 96) || (cInput >= (char) 123 && cInput <= (char) 126) || cInput == (char) 8217 || 
    					cInput == (char) 8212); //8127 is a formal apostrophe: ( ’ ) as opposed to: ( ' ); 8212 is dash(–) rather than hyphen(-) 
    		
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
		spaces(1, maxLeng);
		System.out.print(" Frequency  ");
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
				
				if(i >= 0.01) {
					//for 0 - value print char
					for(int x = 0; x < (int) (i * 100); x++) {
						System.out.print("*");
					}
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
		zeroCount = 0;
	}
	
	//word and sentence analysis
	
	private static void printWordAnalysis() {
		int lengths[] = wordLengths();
		int sortedLengths[] = sortIntArray(lengths);
		int lengthsNoDuplicates[] = removeDuplicates(sortedLengths);
		int wordLengthOccurences[] = countOccurences(lengthsNoDuplicates, sortedLengths);
		int modeNum = modeLength(wordLengthOccurences, lengthsNoDuplicates);
		
		System.out.println();
		System.out.println("Mean Word Length: " + meanLength(lengths));
		System.out.println("Median Word Length: " + medianLength(sortedLengths));
		System.out.println("Mode Word Length: " + modeNum);
		System.out.println();
		
		int sortedWordLengths[][] = sortArrBViaArrA(lengthsNoDuplicates, wordLengthOccurences);
		
		toBarChart(sortedWordLengths, "Word Length", 4);
		
		
	}
	
	private static void printSentenceAnalysis() {
		int lengths[] = sentenceLengths();
		int sortedLengths[] = sortIntArray(lengths);
		int lengthsNoDuplicates[] = removeDuplicates(sortedLengths);
		int sentenceLengthOccurences[] = countOccurences(lengthsNoDuplicates, sortedLengths);
		int modeNum = modeLength(sentenceLengthOccurences, lengthsNoDuplicates);

		System.out.println();
		System.out.println("Mean Sentence Length: " + meanLength(lengths) + " words");
		System.out.println("Median Sentence Length: " + medianLength(sortedLengths) + " words");
		System.out.println("Mode Sentence Length: " + modeNum + " words");
		System.out.println();
		
		int sortedSentenceLengths[][] = sortArrBViaArrA(lengthsNoDuplicates, sentenceLengthOccurences);
		
		toBarChart(sortedSentenceLengths, "Sentence Length", 2);

	}
	
	private static int[] wordLengths() {
		//run through and remove everything that's not a letter
		//make sure only one space between words
		//split string via " "
		//put each length into separate output array
		String fixedInput = trimInput().replace(System.getProperty("line.separator"), " ");
		
		char charArr[] = fixedInput.toLowerCase().toCharArray();
        String finalString = "";
        
        for (char c : charArr) {
        	if(isAtoZ(c) || c == ' ' ) {
        		finalString += c;
        	} else if (c == '.') {
        		finalString += ' ';
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
	
	
	private static int[] sentenceLengths() {
		String inputFixed = trimInput().replace(System.getProperty("line.separator"), " ");
		inputFixed = inputFixed.replaceAll("\\s+", " ");
		
		char charArr[] = inputFixed.toLowerCase().toCharArray();
		int count = 0;
		
		for (char c : charArr) {
			if (c == '!' || c == '?') {
				charArr[count] = '.';
			}
			count++;
		}
		
		String finalInput = new String(charArr);
		
		String sentences[] = finalInput.split("\\.");
		int lengths[] = new int[sentences.length];
		count = 0;
		
		for (String sentence : sentences) {
			lengths[count] += sentenceLeng(sentence);
			count++;
		}
		
		return lengths;
	}
	
	private static int sentenceLeng (String inputSentence) {
		
		char charArr[] = inputSentence.toLowerCase().toCharArray();
        String finalString = "";
        
        for (char c : charArr) {
        	if(isAtoZ(c) || c == ' ') {
        		finalString += c;
        	}
        }
        //make sure only one space between words using regex
        //doing it after is better as removing punctuation may leave double spaces
        finalString = finalString.replaceAll("\\s+", " ").trim();
        
        String words[] = finalString.split(" ");
        
		return words.length;
	}
	
	
	private static void toBarChart (int inputA[][], String header, int header1Padding) {
    	int count = 0, total = 0;
    	
    	for (int i[] : inputA) {
    		total += i[1];
    	}
    	
    	double freq[] = new double[inputA.length], temp;
    	for (int i[] : inputA) {
    		temp = ((double) i[1] / (double) total);
    		if(temp >= 0.01)
    			freq[count] = (Math.round(temp * 100.0) / 100.0);
    		else
    			freq[count] = (Math.round(temp * 1000.0) / 1000.0);
    		count++;
    	}
    	
    	int maxnum = inputA[0][0];
		for(int i[] : inputA) {
			if(i[0] > maxnum) 
				maxnum = i[0];
		}

		count = 0;
    	
		double maxLeng = freq[0], maxNumb = 0;
		for(double i : freq) {
			if(String.valueOf(i).length() > String.valueOf(maxLeng).length()) 
				maxLeng = i;
			if(i > maxNumb) 
				maxNumb = i;
			count++;
		}
		

		spaces(1, maxnum);
		space(header1Padding/2);
		System.out.print(header);
		space(header1Padding/2);
		System.out.print("| ");
		spaces(1, maxLeng);
		System.out.print(" Frequency  ");
		System.out.println("| '*' = 1%");
		//print lines
		
		for(int i = 0; i < (int) ((maxNumb * 100) + 36); i++) { 
            //System.out.print("-");
            System.out.print("=");
		}
		
        System.out.println(); //new line
        
        count = 0;
        
		for (int i[] : inputA) {
				System.out.print("     ");
				space(header.length() / 4);
				spaces(i[0], maxnum);
				//print counter
				System.out.print(i[0]);
				space(header.length() / 4);
				System.out.print("     |     ");
				spaces(freq[count], maxLeng);
				//print value
				System.out.print(freq[count]);
				System.out.print("     | ");
				
				if(freq[count] >= 0.01) {
					//for 0 - value print char
					for(int x = 0; x < (int) (freq[count] * 100); x++) {
						System.out.print("*");
					}
				}
				System.out.println(); //new line
			
			
			count++;
		}
		
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
	
	private static int[][] sortArrBViaArrA (int inputA[], int inputB[]) {
		int output[][] = new int[inputA.length][2];
		
		
		for (int i = 0; i < inputA.length; i++) {
			for(int y = 0; y < inputB.length; y++) {
				output[i][1] = inputB[i];
			}
			output[i][0] = inputA[i];
		}
		
		boolean swapped;
		int max = inputA.length;
		do {
			swapped = false;
			for (int i = 1; i < max; i++) {
				if (output[i - 1][0] > output[i][0]) {
					swap2(output, i - 1, i, 0);
					swap2(output, i - 1, i, 1);
					swapped = true;
				}
			}
			max--;
		} while (swapped);
		
		return output;
		
	}
	
	private static double meanLength(int input[]) {
		int total = 0;
		for(int i : input) {
			total += i;
		}
		
		double result = (Math.round(((double) total / (double) input.length) * 100.0) / 100.0);
		
		return result;
	}
	//pre: input array is ordered numerically (ascending)
	private static double medianLength(int input[]) {
		
		double median;
		if (input.length % 2 == 0) {
			// even number of words
			median = 0.5 * (input[(input.length-1) / 2] + input[(input.length-1) / 2 + 1]);
		} else
			// odd number of words
			median = input[(input.length-1) / 2];
		return median;

	}
	//pre: input arrays are ordered numerically (ascending)
    private static int modeLength(int inputA[], int inputB[]) {
    	int modenum[][] = sortArrBViaArrA(inputA, inputB);
		int max = 0, modeNum = 0;;
		for (int[] i : modenum) {
			if (i[0] > max) {
				max = i[0];
				modeNum = i[1];
			}
		}
        return modeNum;
    }
    
	private static void swap2 (int[][] input, int a, int b, int c) {
		int tmp = input[a][c];
		input[a][c] = input[b][c];
		input[b][c] = tmp;
		
	}
	
	private static void swap(int[] input, int a, int b) {
		int tmp = input[a];
		input[a] = input[b];
		input[b] = tmp;
	}
    
	
}
