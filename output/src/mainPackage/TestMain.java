package mainPackage;

import java.util.ArrayList;

import testPackage1.*;
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

	public static void main(String[] l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1) {
		//Random comment lines for removal. 
		
		Log.d(TAG, "This is for testing");
		
//===========================================================================================================================================
		//Test very basic classes. Tests renaming, minification and comment removal.
		//Will also test if for loop functionality is altered.
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1();
		System.out.println("TEST 1:");
		
		
		TestClass1 l1l1l1l1l1l1l1l1l1l1l1l1 = new TestClass1("Testing renaming");
		TestClass2 l1l1l1l1l1l1l1l1l1l1l1l1l = new TestClass2();
		
		
		for (int l1l1l1l1l1l1l1l1l1l1l1l1l1 = 0; l1l1l1l1l1l1l1l1l1l1l1l1l1 < 7; l1l1l1l1l1l1l1l1l1l1l1l1l1++){
			l1l1l1l1l1l1l1l1l1l1l1l1.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1();
			l1l1l1l1l1l1l1l1l1l1l1l1.l1l1l1l1l1l1l1l1l1l1l1l1l1l = l1l1l1l1l1l1l1l1l1l1l1l1l.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1();
		}
//===========================================================================================================================================
		//Test enum, switch cases, interface and if/else statements with no wrapper.
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1();
		System.out.println("TEST 2:");
		
		Implementation1 l1l1l1l1l1l1l1l1l1l1l1l1l1l1 = new Implementation1();
		Implementation2 l1l1l1l1l1l1l1l1l1l1l1l1l1l1l = new Implementation2();
		
		ArrayList<TestInterface> l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1 = new ArrayList<TestInterface>();
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.add(l1l1l1l1l1l1l1l1l1l1l1l1l1l1);
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.add(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l);
		
		for(TestInterface x : l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1){
			x.l1l1l1l1l1l1l1l1l1l();
			for (TestEnum y: TestEnum.values()){
				x.l1l1l1l1l1l1l1l1l1l1l(y);
				x.l1l1l1l1l1l1l1l1l1l1(y);
			}
		}
		
//===========================================================================================================================================
		//Test abstract classes, annotations, super methods
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1();
		System.out.println("TEST 3:");
		
		TestAbstract l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l = new AbstractExtension1();
		
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.l();
		l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.l1();
//===========================================================================================================================================

	}

	public static void l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1(){
		System.out.println("=============================================================================");
	}
}