import java.io.*;


public class ReadFiles {
	
	private static String file;
	
	public ReadFiles(String inputFile) {
		file = inputFile;
	}
	
	public String readFile () {
		String files[] = file.split(", "), output = "";
		
		for (String y : files) {
			output += " " + accessFile(y);
		}
		
		return output;
		
	}

	private static String accessFile (String fileName) {
		
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
			System.out.println();
			System.out.println("Error: \"" + fileName + "\" File Not Found (No such file or directory)");
			
			return "";
		}
	}
}
