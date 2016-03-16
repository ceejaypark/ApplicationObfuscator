package testPackage1;


/**
 * Simple class to contain public and private fields with 
 * @author cwu323
 *
 */

public class TestClass1 {

	//Public field holding random string
	public String pubField;
	private long privField;
	//Constructor for class 1
	public TestClass1(String randomInput){
		this.pubField = randomInput;
	}
	
	//Allow for overloading
	public TestClass1(String randomInput, int override){
		this.pubField = randomInput;
		this.privField = override;
	}
	
	public void printPubField(){
		System.out.println(this.pubField);
	}
}
