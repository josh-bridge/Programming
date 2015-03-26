import java.util.HashSet;
import java.util.Set;


public class wordAndSentAnalysis {
	
	private static String input;
	
	public wordAndSentAnalysis(String stringInput) {
		input = stringInput;
	}
	
	public void printWordAnalysis() {
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
	
	public void printSentenceAnalysis() {
		int lengths[] = sentenceLengths();
		int sortedLengths[] = sortIntArray(lengths);
		int lengthsNoDuplicates[] = removeDuplicates(sortedLengths);
		int sentenceLengthOccurences[] = countOccurences(lengthsNoDuplicates, sortedLengths);
		int modeNum = modeLength(sentenceLengthOccurences, lengthsNoDuplicates);
		
		System.out.println();
		System.out.println("Mean Sentence Length: " + meanLength(lengths));
		System.out.println("Median Sentence Length: " + medianLength(sortedLengths));
		System.out.println("Mode Sentence Length: " + modeNum);
		System.out.println();
		System.out.println("Note: Sentence lengths means how many words there are (I thought this was the most meaningful data)");
		System.out.println();
		
		int sortedSentenceLengths[][] = sortArrBViaArrA(lengthsNoDuplicates, sentenceLengthOccurences);
		
		toBarChart(sortedSentenceLengths, "Sentence Length", 2);
	}
	
	private static int[] wordLengths() {
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
	
	
	private static int[] sentenceLengths() {
		String inputFixed = input.replace(System.getProperty("line.separator"), " ");
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
        
        int total = 0;
        
        for(int y=0; y < words.length; y++) {
        	total++;
        }
		
		return total;
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
    
    private static boolean isAtoZ(char cInput) {
        if (cInput >= (char) 97 && cInput <= (char) 122) 
            return true;
        return false;
    }
	
	private static void space(int x) {
        for(int y=0; y<x; y++)
            System.out.print(' ');
    }
	
    private static void spaces(double x, double max) {
        int spaces = (int) (String.valueOf(max).length()) - (String.valueOf(x).length());
        space(spaces);
    }
}
