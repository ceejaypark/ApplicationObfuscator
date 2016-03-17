package obfuscate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentRemover implements Obfuscater{

	public static void main(String[] args) throws IOException{
		List<String> lines = new ArrayList<String>();
		
		File f = new File("test.java");
		
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		
		boolean isComment = false;
		
		while ((line = br.readLine()) != null) {
			String original = line;
			line = line.trim();
			
			if (line.length() == 0) {
				lines.add(original);
				continue;
			}
			
			//if the line begins with // 
			if (line.charAt(0) == '/' && line.charAt(1) == '/' ) {
				continue;
			}
			
			//if the line begins with /*
			if (line.charAt(0) == '/' && line.charAt(1) == '*') {
				isComment = true;
				if (line.charAt(line.length() - 1) == '/' && line.charAt(line.length()-2) == '*') {
					isComment = false;
				}
				continue;
			}
			
			if (line.charAt(line.length() - 1) == '/' && line.charAt(line.length()-2) == '*') {
				isComment = false;
			}
			
			if (isComment) {
				continue;
			}
			
			lines.add(original);
		}
		
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for (String s : lines) {
			s = s + "\n";
			bw.write(s);
		}
		bw.flush();
		bw.close();
	}
	
	@Override
	public HashMap<String, File> execute(HashMap<String, File> Files) {
		// TODO Auto-generated method stub
		return null;
	}
	//comment
	/**
	 * comment
	 */
	/** comment */
	/* comment */
	/* comment
	 * comment
	 */
	
}
