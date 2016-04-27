package obfuscate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public interface Obfuscator {

	/**
	 * Execute function to run the obfuscation technique on an input folder
	 * 
	 * @param files
	 * @param blacklist
	 * @param manifest
	 * 
	 * @return newFileHash
	 * @throws IOException
	 */
	public HashMap<String,File> execute(HashMap<String, File> files,
			HashMap<String, File> blacklist, File manifest) throws IOException;
	
	/**
	 * Method to return the name of the obfuscation technique
	 * 
	 * @return name
	 */
	public String getName();
}
