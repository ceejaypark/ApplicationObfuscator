package testPackage2;

import java.util.HashMap;
import java.util.Set;

/**
 * This is an implementation of TestInferface
 * uses single line if and else statements and also
 * hashmaps and sets.
 * @author cwu323
 *
 */

public class Implementation1 implements TestInterface {

	private HashMap<TestEnum, String> l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1 = new HashMap<TestEnum, String>();
	private HashMap<TestEnum, String> l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l = new HashMap<TestEnum, String>();
	
	public Implementation1(){
		l1l1l1l1l1l1l1l1l1l1l1l1l();
        TestClass2 l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1 = new TestClass2();
        l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l;
	}
	
	@Override
	public void l1l1l1l1l1l1l1l1l1l1l1() {
		//No spacing in some lines
		System.out.println("Restaurant 1 Full Menu:");
		Set<TestEnum> l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l = l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.keySet();
		for(TestEnum x:l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l){
			System.out.println(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.get(x));
		}
	}

	@Override
	public void l1l1l1l1l1l1l1l1l1l1l1l(TestEnum l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1) {
		//No block if and else statements
		System.out.println("Restaurant 1 " + l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.name() + " Menu:");
		if(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.get(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1)!= null)	
			System.out.println(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.get(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1));
		else
			System.out.println("No items for this l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1");
	}

	@Override
	public void l1l1l1l1l1l1l1l1l1l1l1l1(TestEnum l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1) {
		System.out.println("Restaurant 1 " + l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.name() + " Time:");

		if(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.get(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1)!=null)	
			System.out.println(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.get(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1));
		else
			System.out.println("No items for this l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1");
	}

	@Override
	public void l1l1l1l1l1l1l1l1l1l1l1l1l() {
		//Populating l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1
		this.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.put(TestEnum.BREAKFAST, "Bacon And Eggs");
		this.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.put(TestEnum.BRUNCH, "French Toast");
		this.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.put(TestEnum.DESSERT, "Icecream");
		this.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.put(TestEnum.DINNER, "Steak");
		
		//Populating l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l
		this.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.put(TestEnum.BREAKFAST, "7-10");
		this.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.put(TestEnum.BRUNCH, "10-11.30");
		this.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.put(TestEnum.DESSERT, "ALL DAY");
		this.l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.put(TestEnum.DINNER, "7-10");

		
		
	}

}