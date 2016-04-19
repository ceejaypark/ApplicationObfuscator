package testPackage2;

import java.util.ArrayList;

/**
 * This is an implementation of TestInferface
 * uses switch case statements and also
 * arraylists
 * @author cwu323
 *
 */

public class Implementation2 implements TestInterface {

	private ArrayList<String> l1l = new ArrayList<String>();
	private ArrayList<String> l1l1 = new ArrayList<String>();
	private ArrayList<String> l1l1l = new ArrayList<String>();
	private ArrayList<String> l1l1l1 = new ArrayList<String>();
	private ArrayList<String> l1l1l1l = new ArrayList<String>();
	
	public String l1l1l1l1;
	public String l1l1l1l1l;
	public String l1l1l1l1l1;
	public String l1l1l1l1l1l;
	public String l1l1l1l1l1l1;
	
	
	public Implementation2(){
		l1l1l1l1l1l1l1l1l1l1l1();
	}
	
	@Override
	public void l1l1l1l1l1l1l1l1l1l() {
		System.out.println("Restaurant 2 Full Menu:");
		for(String s : l1l){
			System.out.println(s);
		}
		for(String s : l1l1){
			System.out.println(s);
		}
		for(String s : l1l1l){
			System.out.println(s);
		}
		for(String s : l1l1l1){
			System.out.println(s);
		}
		for(String s : l1l1l1l){
			System.out.println(s);
		}
	}

	@Override
	public void l1l1l1l1l1l1l1l1l1l1(TestEnum l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l) {
		System.out.println("Restaurant 2 " + l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.name() + " Menu:");

		switch(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l){
			case BREAKFAST:
				l1l1l1l1l1l1l1l1l1l1l1l(l1l);
				break;
			case LUNCH:
				l1l1l1l1l1l1l1l1l1l1l1l(l1l1);
				break;
			case DINNER:
				l1l1l1l1l1l1l1l1l1l1l1l(l1l1l);
				break;
			case BRUNCH:
				l1l1l1l1l1l1l1l1l1l1l1l(l1l1l1);
				break;
			case DESSERT:
				l1l1l1l1l1l1l1l1l1l1l1l(l1l1l1l);
				break;
		}
	}

	@Override
	public void l1l1l1l1l1l1l1l1l1l1l(TestEnum l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l) {
		System.out.println("Restaurant 2 " + l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.name() + " Time:");

		switch(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l){
			case BREAKFAST:
				System.out.println(l1l1l1l1);
				break;
			case LUNCH:
				System.out.println(l1l1l1l1l);
				break;
			case DINNER:
				System.out.println(l1l1l1l1l1);
				break;
			case BRUNCH:
				System.out.println(l1l1l1l1l1l);
				break;
			case DESSERT:
				System.out.println(l1l1l1l1l1l1);	
				break;
		}
	}

	@Override
	public void l1l1l1l1l1l1l1l1l1l1l1() {
		//Populating l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l
		this.l1l.add("Eggs Benedict");
		this.l1l.add("Cereal");
		this.l1l1.add("Shepards Pie");
		this.l1l1l.add("Curry");
		this.l1l1l.add("Spaghetti");
		this.l1l1l1.add("Muffins");
		this.l1l1l1l.add("Brownies");
		this.l1l1l1l.add("waffles");
		//Populating times
		this.l1l1l1l1 = "6-9am";
		this.l1l1l1l1l1l = "9-11am";
		this.l1l1l1l1l = "11am-2pm";
		this.l1l1l1l1l1l1 = "All Day";
		this.l1l1l1l1l1 = "5-11pm";
		
	}
	
	public void l1l1l1l1l1l1l1l1l1l1l1l(ArrayList<String> l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l){
		for (int l1l1l1l1l1l1l1l1l1 = 0; l1l1l1l1l1l1l1l1l1<l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.size(); l1l1l1l1l1l1l1l1l1++){
			System.out.println(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l.get(l1l1l1l1l1l1l1l1l1));
		}
	}
}