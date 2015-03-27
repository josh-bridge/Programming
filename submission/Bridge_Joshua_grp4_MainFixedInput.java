public class Bridge_Joshua_grp4_MainFixedInput {
	public static void main(String[] args) {
		String input = "Theirs not to make reply,\nTheirs not to reason why,\nTheirs but to do and die:\nInto the valley of Death\n Rode the six hundred.";
		
		System.out.println("Input:");
		System.out.println("\""+ input + "\"");
		
		Bridge_Joshua_grp4_TextAnalysis ta = new Bridge_Joshua_grp4_TextAnalysis(input);
		
		ta.analyzeText();
	}
}

