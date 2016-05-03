package user_interface;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fileReaders.JavaFileRetriever;
import fileReaders.StringRetriever;

public class Main {

	private JFrame frame;
	private FileChoose inputFolder;
	private JButton startButton = new JButton("Decode");
	
	private File current;
	private ArrayList<File> javaFiles = new ArrayList<File>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addComponents();
		addListeners();
	}

	private void addComponents(){
		this.inputFolder = new FileChoose("Input Directory", "Please select an input directory");
		this.frame.add(inputFolder);
		this.frame.add(startButton, JFrame.LEFT_ALIGNMENT);
		
		
	}
	
	private void addListeners(){
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				File inputDir = new File(inputFolder.getTextField().getText());
				
				if(!inputDir.isDirectory()){
					JOptionPane.showMessageDialog(frame, "Input folder is invalid.");
					return;
				}
				if(current == inputDir){
					return;
				}
				current = inputDir;
				
				JavaFileRetriever dr = new JavaFileRetriever(inputDir);
				javaFiles = dr.getJavaFiles();
				
				for(File f : javaFiles){
					StringRetriever fr = new StringRetriever(f);
					
				}
			}
		});
	}
}
