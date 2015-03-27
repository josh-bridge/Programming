import java.util.*;

public class Bridge_Joshua_grp4_MainInputReader {
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
			//clear the scanner ready for the next input (next() doesn't skip the scanner to the next line automatically)
			sc.nextLine();
			
			String input = "";
			
			if(choice == 1) {
				System.out.println("Please enter a string: ");
		        input = sc.nextLine();
			} else if (choice == 2) {
				System.out.println("Please enter one or more filenames (Seperate by ', '; Files will be concatenated to one output): ");
				String fileNames = sc.nextLine();
				
				Bridge_Joshua_grp4_ReadFiles rf = new Bridge_Joshua_grp4_ReadFiles(fileNames);
				input = rf.readFile();
			} else if (choice == 0) {
				break;
			}
			
			Bridge_Joshua_grp4_TextAnalysis ta = new Bridge_Joshua_grp4_TextAnalysis(input);
			ta.analyzeText();
			
			System.out.println();
			System.out.println("Press Return to return to initial state:");
			sc.nextLine();
			
		} while (choice != 0);
		
		sc.close();
		System.out.println("===============================Program Quit==============================");
	}
}
