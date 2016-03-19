package testPackage3;

public class AbstractExtension1 extends TestAbstract{
	
	public void templateMethod(){
		super.templateMethod();
		System.out.println("child template");
	}
	
	@Override
	public void overrideMethod(){
		System.out.println("Testing for @Override annotation");
	}

}
