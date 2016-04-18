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
	private ArrayList<String> l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l = new ArrayList<String>();	
	protected int l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1;
	public TestClass2(){
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.add("Placeholder");
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.add("TestClass2.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l");
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.add("String with space and //randComment");
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.add("TestClass2");
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.add("TestClass.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1");
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.add("TestClass1.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1");
		
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1 = 0;
	}
	
	public String l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1(){
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1++;
		if(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1 == l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.size()){
			l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1 = 1;
		}
		
		return l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.get(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1);
	}
}