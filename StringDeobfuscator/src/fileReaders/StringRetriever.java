package fileReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRetriever {
	private File input;
	private HashMap<Integer, String> retrievedStrings = new HashMap<Integer, String>();
	
	public StringRetriever(File f){
		this.input = f;
		retrieveStrings();
	}
	
	private void retrieveStrings(){
		try {
			Scanner scanner = new Scanner(input);
			String content = scanner.useDelimiter("\\Z").next();
			scanner.close();
			
			Pattern pattern = Pattern.compile("(\"([^\"]*)\")(((\n|\\s)*\\+(\n|\\s)*)(\"([^\"]*)\"))*");
			Matcher m = pattern.matcher(content);
			
			while(m.find()){
				System.out.println(m.group());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
