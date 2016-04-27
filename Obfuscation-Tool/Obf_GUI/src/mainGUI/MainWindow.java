package mainGUI;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import supportClasses.ExecuteObf;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

public class MainWindow {

	private JFrame frame;
	private FileChoose inputFolder;
	private FileChoose outputFolder;
	private ObfCheckList checklist;
	private JButton exeButton;
	private MyTree tree = new MyTree();
	private MyProgBar mpb = new MyProgBar();
	
	public static File sourceFolderPath;

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
	 * 
	 * @throws IOException
	 */
	public MainWindow() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 670, 408);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponents();
		addListeners();
	}

	
	/**
	 * This method initialises any GUI elements required
	 * to be displayed by the main window
	 * 
	 * @param
	 * @return
	 * @throws IOException
	 */
	private void addComponents() throws IOException {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 16, 389, 194, 5, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 55, 121, 19, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		this.inputFolder = new FileChoose("Input Folder:",
				"Select input folder...");
		this.outputFolder = new FileChoose("Output Folder:",
				"Select output folder...");
		
		this.checklist = new ObfCheckList();

		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.add(inputFolder);
		wrapper.add(outputFolder);
		JLabel label = new JLabel(
				"Select the level of obfuscation desired:");
		label.setAlignmentX(Component.RIGHT_ALIGNMENT);
		wrapper.add(label);
		wrapper.add(checklist);

		GridBagConstraints gbc_wrapper = new GridBagConstraints();
		gbc_wrapper.gridheight = 2;
		gbc_wrapper.fill = GridBagConstraints.BOTH;
		gbc_wrapper.insets = new Insets(0, 0, 5, 5);
		gbc_wrapper.gridx = 1;
		gbc_wrapper.gridy = 1;
		frame.getContentPane().add(wrapper, gbc_wrapper);

		GridBagConstraints gbc_fl = new GridBagConstraints();
		gbc_fl.gridheight = 2;
		gbc_fl.fill = GridBagConstraints.BOTH;
		gbc_fl.insets = new Insets(0, 0, 5, 5);
		gbc_fl.gridx = 2;
		gbc_fl.gridy = 1;
		frame.getContentPane().add(tree, gbc_fl);

		GridBagConstraints gbc_mpb = new GridBagConstraints();
		gbc_mpb.gridwidth = 2;
		gbc_mpb.fill = GridBagConstraints.BOTH;
		gbc_mpb.insets = new Insets(0, 0, 5, 5);
		gbc_mpb.gridx = 1;
		gbc_mpb.gridy = 3;
		frame.getContentPane().add(mpb, gbc_mpb);

		exeButton = new JButton("Obfuscate");
		GridBagConstraints gbc_exeButton = new GridBagConstraints();
		gbc_exeButton.insets = new Insets(0, 0, 0, 5);
		gbc_exeButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_exeButton.gridx = 2;
		gbc_exeButton.gridy = 4;
		frame.getContentPane().add(exeButton, gbc_exeButton);
	}

	/**
	 * This method adds the appropriate listeners to the swing elements
	 * present in the GUI
	 * 
	 * @param
	 * @return
	 */
	private void addListeners() {
		//This listener causes changes in input folder to cause changes in the black list selector
		inputFolder.getTextField().getDocument()
				.addDocumentListener(new DocumentListener() {

					private void Process(){
						File f = new File(inputFolder.getFolderLoc());
						if (f.isDirectory()) {
							tree.update(f);
						}
					}
					
					@Override
					public void changedUpdate(DocumentEvent arg0) {
						Process();
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						Process();
					}

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						Process();
					}
				});
		
		//This listener is added to the execution button to begin the obfuscation process
		exeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ExecuteObf obfuscate = ExecuteObf.getInstance();
				if (!obfuscate.isRunning()) {
					try {
						obfuscate.exe(inputFolder.getFolderLoc(),
								outputFolder.getFolderLoc(), checklist,
								exeButton, tree.getCheckList(), mpb);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"A project is currently being obfuscated, please wait until process has finished.");
				}
			}
		});
	}
}
