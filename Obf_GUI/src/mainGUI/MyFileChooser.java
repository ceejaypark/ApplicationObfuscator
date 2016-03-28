package mainGUI;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class MyFileChooser extends JFileChooser{

	public MyFileChooser(){
		this.showOpenDialog(this);
	}
}
