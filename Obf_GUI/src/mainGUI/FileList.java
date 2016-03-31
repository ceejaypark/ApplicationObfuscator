package mainGUI;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import supportClasses.FileTreeModel;

@SuppressWarnings("serial")
public class FileList extends JPanel {

	private File targetLoc;
	private JTree tree = new JTree();

	public FileList() throws IOException {
		this.setLayout(new BorderLayout());
		JLabel title = new JLabel("File Blacklist:");
		this.add(title, BorderLayout.NORTH);
		this.add(tree, BorderLayout.CENTER);
		// http://stackoverflow.com/questions/6966589/gui-component-for-folder-choosing-with-select-option
		
		changeLoc("H://");

	}

	public void changeLoc(String loc) {

		targetLoc = new File(loc);
		if (!targetLoc.isDirectory()) {
			return;
		}

		TreeModel model = new FileTreeModel(targetLoc);
		tree.setModel(model);

	}
}