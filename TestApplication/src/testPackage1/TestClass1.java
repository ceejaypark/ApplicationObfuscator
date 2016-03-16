package testPackage1;

public class TestClass1 {

	//Public field holding random string
	public String pubField;
	
	//Constructor for class 1
	public TestClass1(String randomInput){
		this.pubField = randomInput;
	}
	
	
	public void printPubField(){
		System.out.println(this.pubField);
	}
	
}
