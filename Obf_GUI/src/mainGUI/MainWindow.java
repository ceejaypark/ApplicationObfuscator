package mainGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;



public class MainWindow {
	
	private JFrame frame;
	private FileChoose inputFolder;
	private FileChoose outputFolder;
	private ObfCheckList checklist;
	private JButton exeButton;
	private FileList fl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public MainWindow() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 720, 291);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addComponents();
		addListeners();
	}
	
	private void addComponents() throws IOException{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{508, 176, 0, 0};
		gridBagLayout.rowHeights = new int[]{257, 19, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		this.inputFolder = new FileChoose("Input Folder:","Select input folder...");
		this.outputFolder = new FileChoose("OutputFolder:","Select output folder...");
		this.checklist = new ObfCheckList();
		
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.add(inputFolder);
		wrapper.add(outputFolder);
		JLabel label = new JLabel("Select the obfuscation techiniques to be used:");
		label.setAlignmentX(Component.RIGHT_ALIGNMENT);
		wrapper.add(label);
		wrapper.add(checklist);
		
		GridBagConstraints gbc_wrapper = new GridBagConstraints();
		gbc_wrapper.fill = GridBagConstraints.BOTH;
		gbc_wrapper.insets = new Insets(0, 0, 5, 5);
		gbc_wrapper.gridx = 0;
		gbc_wrapper.gridy = 0;
		frame.getContentPane().add(wrapper, gbc_wrapper);
		
		fl = new FileList();
		GridBagConstraints gbc_fl = new GridBagConstraints();
		gbc_fl.gridwidth = 2;
		gbc_fl.fill = GridBagConstraints.BOTH;
		gbc_fl.insets = new Insets(0, 0, 5, 5);
		gbc_fl.gridx = 1;
		gbc_fl.gridy = 0;
		frame.getContentPane().add(fl, gbc_fl);
		
		exeButton = new JButton("Obfuscate");
		GridBagConstraints gbc_exeButton = new GridBagConstraints();
		gbc_exeButton.insets = new Insets(0, 0, 0, 5);
		gbc_exeButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_exeButton.gridx = 1;
		gbc_exeButton.gridy = 2;
		frame.getContentPane().add(exeButton, gbc_exeButton);
	}
	
	private void addListeners(){
		exeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ExecuteObf obfuscate = ExecuteObf.getInstance();
				if(!obfuscate.isRunning()){
					obfuscate.exe(inputFolder.getFolderLoc(), outputFolder.getFolderLoc(), checklist, exeButton );
				}
				else{
					JOptionPane.showMessageDialog(null, "A project is currently being obfuscated, please wait until process has finished.");
				}
			}
		});
		
	}
}
