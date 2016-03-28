package mainGUI;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class MyDirChooser extends JFileChooser{

	public MyDirChooser(){
		this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.setDialogTitle("Select A Folder");
		this.setApproveButtonText("Select Folder");
		this.setAcceptAllFileFilterUsed(false);
	}
	
	
	public String activate(){
		int result = this.showOpenDialog(this);
		if(this.getSelectedFile()!= null && result == JFileChooser.APPROVE_OPTION)
			return this.getSelectedFile().getAbsolutePath();
		else
			return "";
	}
}	
