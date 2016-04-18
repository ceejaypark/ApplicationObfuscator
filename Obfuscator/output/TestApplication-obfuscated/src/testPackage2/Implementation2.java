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

	private ArrayList<String> l1l1l1 = new ArrayList<String>();
	private ArrayList<String> l1l1l1l = new ArrayList<String>();
	private ArrayList<String> l1l1l1l1 = new ArrayList<String>();
	private ArrayList<String> l1l1l1l1l = new ArrayList<String>();
	private ArrayList<String> l1l1l1l1l1 = new ArrayList<String>();
	
	public String l1l1l1l1l1l;
	public String l1l1l1l1l1l1;
	public String l1l1l1l1l1l1l;
	public String l1l1l1l1l1l1l1;
	public String l1l1l1l1l1l1l1l;
	
	
	public Implementation2(){
		l1l1l1l1l1l1l1l1l1l1l1l1l();
	}
	
	@Override
	public void l1l1l1l1l1l1l1l1l1l1l1() {
		System.out.println("Restaurant 2 Full Menu:");
		for(String s : l1l1l1){
			System.out.println(s);
		}
		for(String s : l1l1l1l){
			System.out.println(s);
		}
		for(String s : l1l1l1l1){
			System.out.println(s);
		}
		for(String s : l1l1l1l1l){
			System.out.println(s);
		}
		for(String s : l1l1l1l1l1){
			System.out.println(s);
		}
	}

	@Override
	public void l1l1l1l1l1l1l1l1l1l1l1l(TestEnum l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1) {
		System.out.println("Restaurant 2 " + l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.name() + " Menu:");

		switch(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1){
			case BREAKFAST:
				l1l1l1l1l1l1l1l1l1l1l1l1l1(l1l1l1);
				break;
			case LUNCH:
				l1l1l1l1l1l1l1l1l1l1l1l1l1(l1l1l1l);
				break;
			case DINNER:
				l1l1l1l1l1l1l1l1l1l1l1l1l1(l1l1l1l1);
				break;
			case BRUNCH:
				l1l1l1l1l1l1l1l1l1l1l1l1l1(l1l1l1l1l);
				break;
			case DESSERT:
				l1l1l1l1l1l1l1l1l1l1l1l1l1(l1l1l1l1l1);
				break;
		}
	}

	@Override
	public void l1l1l1l1l1l1l1l1l1l1l1l1(TestEnum l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1) {
		System.out.println("Restaurant 2 " + l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.name() + " Time:");

		switch(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1){
			case BREAKFAST:
				System.out.println(l1l1l1l1l1l);
				break;
			case LUNCH:
				System.out.println(l1l1l1l1l1l1);
				break;
			case DINNER:
				System.out.println(l1l1l1l1l1l1l);
				break;
			case BRUNCH:
				System.out.println(l1l1l1l1l1l1l1);
				break;
			case DESSERT:
				System.out.println(l1l1l1l1l1l1l1l);	
				break;
		}
	}

	@Override
	public void l1l1l1l1l1l1l1l1l1l1l1l1l() {
		//Populating l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1
		this.l1l1l1.add("Eggs Benedict");
		this.l1l1l1.add("Cereal");
		this.l1l1l1l.add("Shepards Pie");
		this.l1l1l1l1.add("Curry");
		this.l1l1l1l1.add("Spaghetti");
		this.l1l1l1l1l.add("Muffins");
		this.l1l1l1l1l1.add("Brownies");
		this.l1l1l1l1l1.add("waffles");
		//Populating times
		this.l1l1l1l1l1l = "6-9am";
		this.l1l1l1l1l1l1l1 = "9-11am";
		this.l1l1l1l1l1l1 = "11am-2pm";
		this.l1l1l1l1l1l1l1l = "All Day";
		this.l1l1l1l1l1l1l = "5-11pm";
		
	}
	
	public void l1l1l1l1l1l1l1l1l1l1l1l1l1(ArrayList<String> l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1){
		for (int l1l1l1l1l1l1l1l1l1l1l = 0; l1l1l1l1l1l1l1l1l1l1l<l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.size(); l1l1l1l1l1l1l1l1l1l1l++){
			System.out.println(l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1l1.get(l1l1l1l1l1l1l1l1l1l1l));
		}
	}
}