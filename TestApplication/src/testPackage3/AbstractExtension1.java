package testPackage3;

public class AbstractExtension1 extends TestAbstract{

	public AbstractExtension1(String x) {
		super(x);
		// TODO Auto-generated constructor stub
	}
	
	public void TemplateMethod(){
		super.templateMethod();
		System.out.println("child template");
	}
	
	@Override
	public void overrideMethod(){
		System.out.println("Testing for @Override annotation");
	}

}
