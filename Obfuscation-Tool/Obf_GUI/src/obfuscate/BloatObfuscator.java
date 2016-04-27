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
import java.util.Map;

/**
 * Obfuscation technique to "bloat" the source code of the project to be obfuscated.
 * This technique inserts comments in code in such a way it is hard to be removed
 * or made inline.
 * 
 * @author Chan Jun Park
 *
 */
public class BloatObfuscator implements Obfuscator {

    @Override
	public HashMap<String,File> execute(HashMap<String,File> files, HashMap<String,File> blacklist,  File manifest ) throws IOException{

        //Takes in the dictionary with bohemian rhapsody lyrics
        FileReader fr = new FileReader("resources/Dictionary");
        BufferedReader br = new BufferedReader(fr);
        // Reads the first line
        String line = br.readLine();
        // Creates an array with as many indexes as lines of lyrics
        String[] dictionary = new String[58];
        // put each line of lyrics into array
        for (int i = 0; i < dictionary.length; i++) {
            if(line != null) {
                dictionary[i] = line;
                line = br.readLine();
            }
        }


        for (Map.Entry<String, File> fileEntry : files.entrySet()) {
            List<String> linesOfCode = new ArrayList<String>();
            File file = fileEntry.getValue();
            FileReader fileReader = new FileReader(file);
            BufferedReader fileInput = new BufferedReader(fileReader);
            String lineInFile;

            // if the line isn't null replace the spaces
            while ((lineInFile = fileInput.readLine()) != null) {
            	if(!(lineInFile.contains("if") || lineInFile.contains("for")))
            		lineInFile = replaceSpace(lineInFile, dictionary);
                linesOfCode.add(lineInFile);
            }

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter fileOutput = new BufferedWriter(fileWriter);

            for (String s: linesOfCode) {
                s = s + "\n";
                fileOutput.write(s);
            }

            fileOutput.flush();
            fileOutput.close();
            fileInput.close();
        }

        return files;
    }

    private String randomCommentGenerator(String[] dictionary)
    {
        int max = dictionary.length-1;
        int min = 0;
        int random = min + (int)(Math.random() * ((max-min)+1));
        return "/*" + dictionary[random] + "*/";
    }

    /*
	 * Method that goes through a line of code and has 30% chance of replacing each space with
	 * a line from bohemian rhapsody
	 */
	private String replaceSpace(String lineInFile, String[] dictionary) {
		if (!lineInFile.contains("\"")){
			String[] lineArray = lineInFile.split("\\s+");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i<lineArray.length;i++) {
				if (randomTrueOrFalse()) {
					sb.append(lineArray[i]);
					sb.append(randomCommentGenerator(dictionary));
				}
				else  {
					sb.append(lineArray[i]);
					sb.append(" ");
				}
			}
			String alteredLine = sb.toString();
			return alteredLine; 
		}
		return lineInFile;
	}



    // Random true or false
    private boolean randomTrueOrFalse() {
        double i = Math.random();
        if (i < 0.3) {
            return true;
        }
        return false;
    }
    
	@Override
	public String getName() {
		return "Bloat Obfuscation";
	}

}
