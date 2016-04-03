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

public class Bloating implements Obfuscater {

    @Override
    public HashMap<String, File> execute(HashMap<String, File> files) throws IOException {

        FileReader fr = new FileReader("/Users/cjpark/Desktop/2016 Workspace/702_Group_Project/Obfuscator/resources/Dictionary");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        String[] dictionary = new String[58];
        for (int i = 0; i < dictionary.length; i++) {
            if(line != null) {
                dictionary[i] = line;
                line = br.readLine();
            }
        }

        List<String> linesOfCode = new ArrayList<String>();
        for (Map.Entry<String, File> fileEntry : files.entrySet()) {
            File file = fileEntry.getValue();
            FileReader fileReader = new FileReader(file);
            BufferedReader fileInput = new BufferedReader(fileReader);

            String lineInFile;

            while ((lineInFile = fileInput.readLine()) != null) {
                if (randomTrueOrFalse()) {
                    replaceSpace(lineInFile, dictionary);
                }
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

    private String replaceSpace(String lineInFile, String[] dictionary) {
        String[] lineArray = lineInFile.split("\\s+");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i<lineArray.length;i++) {
            sb.append(lineArray[i]);
            sb.append(randomCommentGenerator(dictionary));
        }
        String alteredLine = sb.toString();
        return alteredLine; 
    }


    private boolean randomTrueOrFalse() {
        double i = Math.random();
        if (i < 0.3) {
            return true;
        }
        return false;
    }

}
