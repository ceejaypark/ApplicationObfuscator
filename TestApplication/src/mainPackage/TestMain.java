package mainPackage;

import java.util.ArrayList;

import testPackage1.*;
import testPackage2.*;

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
		printDivide();
		System.out.println("TEST 1:");
		
		
		TestClass1 exampleString = new TestClass1("Testing renaming");
		TestClass2 nameRenameTester = new TestClass2();
		
		
		for (int i = 0; i < 7; i++){
			exampleString.printPubField();
			exampleString.pubField = nameRenameTester.nextString();
		}
//===========================================================================================================================================
		//Test enum, switch cases, interface and if/else statements with no wrapper.
		printDivide();
		System.out.println("TEST 2:");
		
		Implementation1 imp1 = new Implementation1();
		Implementation2 imp2 = new Implementation2();
		
		ArrayList<TestInterface> testImp = new ArrayList<TestInterface>();
		testImp.add(imp1);
		testImp.add(imp2);
		
		for(TestInterface x : testImp){
			x.getFullMenu();
			for (TestEnum y: TestEnum.values()){
				x.getMenuHours(y);
				x.getTimeMenu(y);
			}
		}
	}

	public static void printDivide(){
		System.out.println("=============================================================================");
	}
}
