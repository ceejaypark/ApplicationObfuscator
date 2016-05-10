package fileReaders;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import decoders.Decoder;

public class PropertiesFileStringRetriever implements StringRetriever {
	private File input;
	private ArrayList<String> retrievedStrings = new ArrayList<String>();
	private JPanel tableContainer = new JPanel();
	private JTable table;
	private DefaultTableModel model;

	public PropertiesFileStringRetriever(File f) {
		this.input = f;
		retrieveStrings();
		makeTable();
	}

	@Override
	public void retrieveStrings() {
		String line = "";
		FileReader fr;
		try {
			fr = new FileReader(input);

			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				String[] split = line.split(":");
				addToList(split[split.length -1 ]);
				
			}
			br.close();
			fr.close();
		} catch (Exception e) {

		}

	}

	@SuppressWarnings("serial")
	@Override
	public void makeTable() {
		tableContainer.setLayout(new FlowLayout());
		tableContainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder (),input.getName(),TitledBorder.CENTER,TitledBorder.TOP));
		
		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		model.addColumn("Original String");
		model.addColumn("Base 64 Decrypted");
		model.addColumn("G3 Decryption");
		
		Decoder decoder = new Decoder();
		
		for(String s : retrievedStrings){
			String base64 = "";
			String g3 = "";
			
			if(decoder.Base64Decode(s)!=null){
				base64 = decoder.Base64Decode(s);
			}
			if(decoder.G3Decode(s)!= null){
				g3 = decoder.G3Decode(s);
			}
			
			model.addRow(new Object[] {s, base64, g3});
		}
		
		
		table = new JTable(model);
		tableContainer.add(new JScrollPane(table));
	}

	@Override
	public JPanel getTable() {
		if(model.getRowCount() == 0){
			return null;
		}
		return this.tableContainer;
	}
	
	private void addToList(String s){
		if(s.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$")){
			retrievedStrings.add(s);
		}
	}
}
