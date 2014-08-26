package spam.or.ham;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	static List<String> _inputMsgs = new ArrayList<String>();

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		
		readFile();
		Classifier classifier = new Classifier(_inputMsgs);
		

	}

	private static void readFile() {
		// TODO Auto-generated method stub
		try {
		    FileReader inputFile = new FileReader("smsSpamCollection-train.txt");
		    BufferedReader bufferReader = new BufferedReader(inputFile);
		    
			String line;
			
			while ((line = bufferReader.readLine()) != null) {
				_inputMsgs.add(line);	
			}
			
			bufferReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
