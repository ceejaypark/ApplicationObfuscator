package mainGUI;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MainWindow {
	
	private JFrame frame;
	private FileChoose inputFolder;
	private FileChoose outputFolder;
	private ObfCheckList checklist;
	private JButton exeButton;

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
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 536, 271);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		addComponents();
		addListeners();
	}
	
	private void addComponents(){
		this.inputFolder = new FileChoose("Input Folder:","Select input folder...");
		this.outputFolder = new FileChoose("OutputFolder:","Select output folder...");
		this.checklist = new ObfCheckList();
		
		frame.getContentPane().add(inputFolder);
		frame.getContentPane().add(outputFolder);
		JLabel label = new JLabel("Select the obfuscation techiniques to be used:");
		label.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frame.getContentPane().add(label);
		frame.getContentPane().add(checklist);
		
		exeButton = new JButton("Obfuscate");
		frame.getContentPane().add(exeButton);
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
