package obfuscate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public interface Obfuscator {

	public HashMap<String,File> execute(HashMap<String, File> files,
			HashMap<String, File> blacklist, File manifest) throws IOException;
	
	public String getName();
}
