package mainGUI;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class MyDirChooser extends JFileChooser{

	public MyDirChooser(){
		this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.setAcceptAllFileFilterUsed(false);
	}
	
	
	public String activate(){
		this.showOpenDialog(this);
		return this.getSelectedFile().getAbsolutePath();
	}
}	
