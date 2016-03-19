package testPackage3;

public abstract class TestAbstract {

	String parentField = "parentField";
	String randField;
	public TestAbstract(String x){
		randField = x;
	}
	
	
	public void templateMethod(){
		System.out.println("parent template");
	}
	
	public void overrideMethod(){
		System.out.println("Override this");
	}
	
}
