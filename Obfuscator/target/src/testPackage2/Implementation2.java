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

	private ArrayList<String> breakfast = new ArrayList<String>();
	private ArrayList<String> lunch = new ArrayList<String>();
	private ArrayList<String> dinner = new ArrayList<String>();
	private ArrayList<String> brunch = new ArrayList<String>();
	private ArrayList<String> dessert = new ArrayList<String>();
	
	public String breakfastTime;
	public String lunchTime;
	public String dinnerTime;
	public String brunchTime;
	public String dessertTime;
	
	
	public Implementation2(){
		populateMenuAndTimes();
	}
	
	@Override
	public void getFullMenu() {
		System.out.println("Restaurant 2 Full Menu:");
		for(String s : breakfast){
			System.out.println(s);
		}
		for(String s : lunch){
			System.out.println(s);
		}
		for(String s : dinner){
			System.out.println(s);
		}
		for(String s : brunch){
			System.out.println(s);
		}
		for(String s : dessert){
			System.out.println(s);
		}
	}

	@Override
	public void getTimeMenu(TestEnum meal) {
		System.out.println("Restaurant 2 " + meal.name() + " Menu:");

		switch(meal){
			case BREAKFAST:
				printMenu(breakfast);
				break;
			case LUNCH:
				printMenu(lunch);
				break;
			case DINNER:
				printMenu(dinner);
				break;
			case BRUNCH:
				printMenu(brunch);
				break;
			case DESSERT:
				printMenu(dessert);
				break;
		}
	}

	@Override
	public void getMenuHours(TestEnum meal) {
		System.out.println("Restaurant 2 " + meal.name() + " Time:");

		switch(meal){
			case BREAKFAST:
				System.out.println(breakfastTime);
				break;
			case LUNCH:
				System.out.println(lunchTime);
				break;
			case DINNER:
				System.out.println(dinnerTime);
				break;
			case BRUNCH:
				System.out.println(brunchTime);
				break;
			case DESSERT:
				System.out.println(dessertTime);	
				break;
		}
	}

	@Override
	public void populateMenuAndTimes() {
		//Populating menu
		this.breakfast.add("Eggs Benedict");
		this.breakfast.add("Cereal");
		this.lunch.add("Shepards Pie");
		this.dinner.add("Curry");
		this.dinner.add("Spaghetti");
		this.brunch.add("Muffins");
		this.dessert.add("Brownies");
		this.dessert.add("waffles");
		//Populating times
		this.breakfastTime = "6-9am";
		this.brunchTime = "9-11am";
		this.lunchTime = "11am-2pm";
		this.dessertTime = "All Day";
		this.dinnerTime = "5-11pm";
		
	}
	
	public void printMenu(ArrayList<String> menu){
		for (int i = 0; i<menu.size(); i++){
			System.out.println(menu.get(i));
		}
	}
}
