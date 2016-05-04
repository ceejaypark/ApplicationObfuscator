package fileReaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;

public class StringRetriever {
	private File input;
	private HashMap<Integer, String> stringWithLine = new HashMap<Integer, String>();
	private ArrayList<String> retrievedStrings = new ArrayList<String>();
	private JTable table = new JTable();
	
	public StringRetriever(File f){
		this.input = f;
		retrieveStrings();
		makeTable();
	}
	
	private void retrieveStrings(){
		try {
			Scanner scanner = new Scanner(input);
			String content = scanner.useDelimiter("\\Z").next();
			scanner.close();
			
			Pattern pattern = Pattern.compile("(\"([^\"]*)\")(((\n|\\s)*\\+(\n|\\s)*)(\"([^\"]*)\"))*");
			Matcher m = pattern.matcher(content);
			
			while(m.find()){
				retrievedStrings.add(m.group());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String reduceString(String s){
		return s.replaceAll("\"((\n|\\s)*\\+(\n|\\s)*)\"","");
	}
	
	private void makeTable(){
		
	}
	
	private JTable getTable(){
		return this.table;
	}
}
