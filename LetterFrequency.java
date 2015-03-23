import java.util.*;

public class LetterFrequency {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
        String input = sc.nextLine().toLowerCase();
        int alphaBet[] = new int[127];
        
        char charArr[] = input.toCharArray();
        char sortedCharArr[] = charArr;
         
        
        //sort the char array into alphabetical order
        boolean swapped;
        int max = sortedCharArr.length;
        do {
            swapped = false;
            for (int i = 1; i < max; i++) {
                if (sortedCharArr[i - 1] > sortedCharArr[i]) {
                	char tmp = sortedCharArr[i-1];
                	sortedCharArr[i-1] = sortedCharArr[i];
                	sortedCharArr[i] = tmp;
                    swapped = true;
                }
            }
            max--;
        } while (swapped);
        
        char prevChar = sortedCharArr[0]; int count = 0;
        ArrayList<Character> finalChars = new ArrayList<Character>();
        
        for (char y : sortedCharArr) {
        	if(count > 0 && y != prevChar) {
	        	finalChars.add(prevChar);
        	} 
        	count++;
        	prevChar = y;
        }
        Character[] finalCharsArr = finalChars.toArray(new Character[finalChars.size()]);

        
        //count occurences of each char
        int prevAmount = 0, spaceCount = 0;
        double charPercent, charFrequency;
        prevChar = sortedCharArr[0];
        for (char i : sortedCharArr) {
        	if(i == ' ') {
        		spaceCount++;
        	} else {
        		prevAmount = alphaBet[(int) i];
        		alphaBet[(int) i] = (prevAmount + 1);
        		if(i != prevChar && prevChar != ' ') {
        			charFrequency = (double) alphaBet[(int) prevChar] / ((double) input.length() - (double) spaceCount);
            		charPercent = Math.round((charFrequency * 100) * 100.0) / 100.0;
            		System.out.println("\'" + prevChar + "\' Frequency = " + Math.round(charFrequency * 100.0) / 100.0 + " Percentage = " + charPercent +"%");
        		}
        	}
    		prevChar = i;
        		
        }
        /*
        int counter = 0;
        
        //make array of only used chars
        ArrayList<Integer> finalCharAmounts = new ArrayList<Integer>();
        //add chars to ArrayList when usage > 0
        for(int y : alphaBet) {
        	if(y > 0) {
        		finalChars.add((char) counter);
        		finalCharAmounts.add(y);
        	}
        	counter++;
        }
        //convert new arraylist's to normal array's
        Integer[] finalCharsAmouArr = finalCharAmounts.toArray(new Integer[finalCharAmounts.size()]);
        
        counter = 0;
        //print occurences and percentage of string
        for(double x : finalCharsAmouArr) {
        	if(x > 0) {
        		System.out.println("\'" + finalCharsArr[counter] + "\'= " + (int) x + " times. Percentage = " + Math.round(((x / (double) input.length()) * 100) * 100.0) / 100.0+"%");
        	}
        	counter++;
        }
        */
        sc.close();

	}
	
    private static int[] sortArray(int input[]) {
        boolean swapped;
        int max = input.length;
        do {
            swapped = false;
            for (int i = 1; i < max; i++) {
                /* if this pair is out of order */
                if (input[i - 1] > input[i]) {
                    /* swap them and remember something changed */
                    swapNums(input, i - 1, i);
                    swapped = true;
                }
            }
            max--;
        } while (swapped);
        return input;

    }
    
    private static void swapNums(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

}
