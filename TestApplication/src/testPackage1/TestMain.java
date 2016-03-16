package testPackage1;

import testPackage2.*;
import testPackage3.*;

/**
 * Test javadoc 
 * Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
 * sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
 * Ut enim ad minim veniam,
 *  quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
 *   Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat
 * @author cwu323
 *
 */
public class TestMain {

	public static void main(String[] args) {
		//Random comment lines for removal. 
		
//===========================================================================================================================================
		//Test very basic classes. Tests renaming, minification and comment removal.
		//Will also test if for loop functionality is altered.
		
		TestClass1 exampleString = new TestClass1("This is a test");
		TestClass2 nameRenameTester = new TestClass2();
		
		
		for (int i = 0; i < 7; i++){
			exampleString.printPubField();
			exampleString.pubField = nameRenameTester.nextString();
		}
//===========================================================================================================================================

	}
}
