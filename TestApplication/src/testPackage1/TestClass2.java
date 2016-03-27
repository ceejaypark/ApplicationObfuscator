package testPackage1;

import java.util.*;

/**
 * This class is made to test to see if renaming different
 * variables, methods or classes will have any effect on strings
 * with the same strings as the names.
 * @author cwu323
 *
 */
public class TestClass2 {

	//private ArrayList
	private ArrayList<String> temp = new ArrayList<String>();	
	protected int currentPos;
	public TestClass2(){
		temp.add("Placeholder");
		temp.add("TestClass2.temp");
		temp.add("String with space and //randComment");
		temp.add("TestClass2");
		temp.add("TestClass.currentPos");
		temp.add("TestClass1.pubField");
		
		currentPos = 0;
	}
	
	public String nextString(){
		currentPos++;
		if(currentPos == temp.size()){
			currentPos = 1;
		}
		
		return temp.get(currentPos);
	}
}
