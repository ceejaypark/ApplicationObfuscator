package mainGUI;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.Component;


public class MainWindow {

	private JFrame frame;

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
		frame.setBounds(100, 100, 515, 408);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		frame.getContentPane().add(new FileChoose("Input Folder:","Select input folder..."));
		frame.getContentPane().add(new FileChoose("OutputFolder:","Select output folder..."));
		JLabel label = new JLabel("Select the obfuscation techiniques to be used:");
		label.setAlignmentX(Component.RIGHT_ALIGNMENT);
		frame.getContentPane().add(label);
		frame.getContentPane().add(new ObfCheckList());
	}
	
}
