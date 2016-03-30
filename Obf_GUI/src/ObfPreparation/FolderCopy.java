package ObfPreparation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FolderCopy {
	
	
	public void beginCopy(File inputDir, File outputDir) throws IOException{
		String inputFolderName = inputDir.getName();
		String outputFolderName = "\\" + inputFolderName + "-obfuscated";
		outputDir = new File(outputDir.getCanonicalPath() + outputFolderName);
		
		copyFolder(inputDir,outputDir);
	}
	
	private void copyFolder(File inputDir, File outputDir)throws IOException{
		

		
		if (inputDir.isDirectory()) {

			// if directory not exists, create it
			if (!outputDir.exists()) {
				outputDir.mkdir();
				//System.out.println("Directory copied from " + src + "  to " + dest);
			}

			// list all the directory contents
			String files[] = inputDir.list();

			for (String file : files) {
				// construct the inputDir and dest file structure
				File srcFile = new File(inputDir, file);
				File destFile = new File(outputDir, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(inputDir);
			OutputStream out = new FileOutputStream(outputDir);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			//System.out.println("File copied from " + src + " to " + dest);
		}
	}
}
