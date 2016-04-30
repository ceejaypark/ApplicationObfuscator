package reverse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ParseException;

public class MainFileReader {

	private static String mainFolderDirectory = "C:\\Users\\cybreus\\Desktop\\Uni\\702\\reverse_engineer\\Apps\\1\\COMPSCI-702-2016-G1_source_from_JADX\\com\\se702\\hider\\enigma";
	private static List<File> listOfFiles = new ArrayList<File>();
	
	public static void main(String[] args) {
		File mainFolder = new File(mainFolderDirectory);
		
		getFiles(mainFolder);
		
		MethodLineDetector mld = new MethodLineDetector(listOfFiles);
		try {
			mld.detectSuspiciousMethods();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void getFiles(File folder) {
		File[] listOfFilesInFolder = folder.listFiles();
		
		for (File f: listOfFilesInFolder) {
			if (f.isDirectory()) {
				getFiles(f);
			} else {
				listOfFiles.add(f);
			}
		}
	}
}
