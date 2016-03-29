package obfuscate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public interface Obfuscater {

	public HashMap<String,File> execute(HashMap<String,File> files) throws IOException;
	
}
