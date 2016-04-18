package testPackage3;

public class AbstractExtension1 extends TestAbstract{
	
	public void l(){
		super.l();
		System.out.println("child template");
	}
	
	@Override
	public void l1(){
		System.out.println("Testing for @Override annotation");
	}

}