package testPackage2;

import java.util.HashMap;

public interface TestInterface {

	void getFullMenu(); /*Used to print whole hashmap*/
	
	void getTimeMenu(TestEnum meal); /*Used to print meals at time*/
	
	void getMenuHours(/*Rand comment in between*/TestEnum meal); /*Used to show times for meals*/
	
	void populateMenuAndTimes();
}
