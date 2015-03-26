import java.io.*;


public class ReadFiles {

	public String readFile (String fileName) {
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
}
