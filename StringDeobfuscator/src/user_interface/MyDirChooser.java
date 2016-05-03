package user_interface;

import java.io.File;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
/**
 * Implementation of JFileChooser
 * @author cwu323
 *
 */
public class MyDirChooser extends JFileChooser{

	public MyDirChooser(){
		//Only allow selections from directories.
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
	
	/**
	 * Restricting the fileChooser to a directory only
	 * @param loc
	 */
	
	public void setRestriction(String loc){
		File location = new File(loc);
		if (location.isDirectory()){
			this.setCurrentDirectory(location);
		}
	}
}	
