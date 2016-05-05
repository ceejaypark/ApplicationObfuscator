package user_interface;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fileReaders.FileRetriever;
import fileReaders.JavaFileStringRetriever;
import fileReaders.PropertiesFileStringRetriever;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import java.awt.GridLayout;

public class Main {

	private JFrame frame;
	private FileChoose inputFolder;
	private JButton startButton = new JButton("Decode");
	private JPanel contentPane = new JPanel();
	private JScrollPane scrollPane;
	private JPanel tableWrapper = new JPanel();
	
	private File current;
	private ArrayList<File> javaFiles = new ArrayList<File>();
	private ArrayList<File> propFiles = new ArrayList<File>();
	
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
		frame.setResizable(false);
		frame.setBounds(100, 100, 878, 636);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addComponents();
		addListeners();
	}

	private void addComponents(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{580, 0};
		gridBagLayout.rowHeights = new int[]{0, 51, 43, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		gbc_startButton.insets = new Insets(0, 0, 5, 0);
		gbc_startButton.anchor = GridBagConstraints.EAST;
		gbc_startButton.gridx = 0;
		gbc_startButton.gridy = 2;
		this.frame.getContentPane().add(startButton, gbc_startButton);
		GridBagConstraints gbc_contentPane = new GridBagConstraints();
		gbc_contentPane.fill = GridBagConstraints.BOTH;
		gbc_contentPane.gridx = 0;
		gbc_contentPane.gridy = 3;
		this.frame.getContentPane().add(contentPane, gbc_contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{291, 29, 0};
		gbl_contentPane.rowHeights = new int[]{43, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		contentPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder (),"Decoded Strings",TitledBorder.CENTER,TitledBorder.TOP));
		scrollPane.setViewportView(tableWrapper);
		tableWrapper.setLayout(new GridLayout(1, 0, 0, 0));
		
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
				tableWrapper.removeAll();
				
				FileRetriever dr = new FileRetriever(inputDir);
				javaFiles = dr.getJavaFiles();
				propFiles = dr.getPropertyFiles();
				
				startButton.setEnabled(false);
				for(File f : javaFiles){
					JavaFileStringRetriever fr = new JavaFileStringRetriever(f);
					if (fr.getTable()!= null){
						tableWrapper.add(fr.getTable());
					}
				}
				for(File f: propFiles){
					PropertiesFileStringRetriever pr = new PropertiesFileStringRetriever(f);
					if(pr.getTable()!= null){
						tableWrapper.add(pr.getTable());
					}
				}
				
				tableWrapper.repaint();
				scrollPane.setViewportView(tableWrapper);
				startButton.setEnabled(true);
			}
		});
	}
}
