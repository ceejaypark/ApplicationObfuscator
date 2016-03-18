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
		// TODO Auto-generated method stub

	}

	@Override
	public void getTimeMenu(TestEnum meal) {
		switch(meal){
			case BREAKFAST:
				printMenu(breakfast);
			case LUNCH:
				printMenu(lunch);
			case DINNER:
				printMenu(dinner);
			case BRUNCH:
				printMenu(brunch);
			case DESSERT:
				printMenu(dessert);
		}
	}

	@Override
	public void getMenuHours(TestEnum meal) {
		switch(meal){
			case BREAKFAST:
				System.out.println(breakfastTime);
			case LUNCH:
				System.out.println(lunchTime);
			case DINNER:
				System.out.println(dinnerTime);
			case BRUNCH:
				System.out.println(brunchTime);
			case DESSERT:
				System.out.println(dessertTime);		
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
