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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{434, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 200, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		this.inputFolder = new FileChoose("Input Directory", "Please select an input directory");
		GridBagConstraints gbc_inputFolder = new GridBagConstraints();
		gbc_inputFolder.insets = new Insets(0, 0, 5, 0);
		gbc_inputFolder.anchor = GridBagConstraints.NORTH;
		gbc_inputFolder.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputFolder.gridx = 0;
		gbc_inputFolder.gridy = 1;
		this.frame.getContentPane().add(inputFolder, gbc_inputFolder);
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.anchor = GridBagConstraints.EAST;
		gbc_startButton.gridx = 0;
		gbc_startButton.gridy = 2;
		this.frame.getContentPane().add(startButton, gbc_startButton);
		
		
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
