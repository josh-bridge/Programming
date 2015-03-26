
public class FixedInput {
	public static void main(String[] args) {
		//define scanner
		String input = "Theirs not to make reply,\nTheirs not to reason why,\nTheirs but to do and die:\nInto the valley of Death\n Rode the six hundred.";
		
		System.out.println("Input:");
		System.out.println("\""+ input + "\"");
		
		LetterFreq lf = new LetterFreq(input);
		lf.letterFrequency();
		
		wordAndSentAnalysis wsa = new wordAndSentAnalysis(input);
		wsa.printWordAnalysis();
		wsa.printSentenceAnalysis();
	}


}
