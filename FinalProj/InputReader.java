import java.util.*;

public class InputReader {
	public static void main(String[] args) {
		//define scanner
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			do {
				System.out.println("Input string [1], Parse File(s) [2] or Quit [0]?");
				choice = Character.getNumericValue(sc.next().trim().charAt(0));
				if (choice != 1 && choice != 2 && choice != 0)
					System.out.println("----------Invalid option, please enter a value of 1, 2 or 0----------");
				System.out.println();
			} while (!(choice == 1 || choice == 2 || choice == 0));
			//clear the scanner ready for the next input (nextInt doesn't skip the scanner to the next line automatically)
			sc.nextLine();
			
			String input = "";
			
			if(choice == 1) {
				System.out.println("Please enter a string: ");
		        input = sc.nextLine().trim();
				LetterFreq lftest = new LetterFreq(input);
		        input = lftest.testInput();
		        
			} else if (choice == 2) {
				System.out.println("Please enter one or more filenames (Seperate by ', '; Files will be concatenated to one output): ");
				String fileNames = sc.nextLine().trim();
				
				ReadFiles rf = new ReadFiles(fileNames);
				input = rf.readFile().trim();

				LetterFreq lftest = new LetterFreq(input);
		        input = lftest.testInput();
				
			} else if (choice == 0) {
				break;
			} else {
				input = "";
			}
			
			if(input.isEmpty() || input == null) {
				System.out.println("-----Program Error - Empty or Invalid input: The program failed to run. It will restart.-----");
			} else {
				
				LetterFreq lf = new LetterFreq(input);
				lf.letterFrequency();
				
				wordAndSentAnalysis wsa = new wordAndSentAnalysis(input);
				wsa.printWordAnalysis();
				wsa.printSentenceAnalysis();
			
				
				System.out.println();
				System.out.println("-------------------------------Output Ended------------------------------");
				System.out.println();
				System.out.println("Press Return to return to initial state:");
				sc.nextLine();
			}
			
			
			System.out.println();
			
		} while (choice != 0);
		
		sc.close();

		System.out.println("===============================Program Quit==============================");
	}
}
