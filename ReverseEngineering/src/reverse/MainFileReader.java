package reverse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFileReader {

	private static String mainFolderDirectory = "H:\\Desktop\\4th";
	private static List<File> listOfFiles = new ArrayList<File>();
	
	public static void main(String[] args) {
		File mainFolder = new File(mainFolderDirectory);
		
		getFiles(mainFolder);
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
