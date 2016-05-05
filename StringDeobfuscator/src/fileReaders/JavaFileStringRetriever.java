package fileReaders;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import decoders.Decoder;

public class JavaFileStringRetriever implements StringRetriever{
	private File input;
	private ArrayList<String> retrievedStrings = new ArrayList<String>();
	private JPanel tableContainer = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	
	
	public JavaFileStringRetriever(File f){
		this.input = f;
		retrieveStrings();
		makeTable();
	}
	
	@Override
	public void retrieveStrings(){
		try {
			StringBuilder sb = new StringBuilder();
			
			String line = "";
			FileReader fr = new FileReader(input);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine())!=null){
				sb.append(line);
			}
			br.close();
			fr.close();
			
			
			
			String content = sb.toString();
			Pattern pattern = Pattern.compile("(\"([^\"]*)\")(((\n|\\s)*\\+(\n|\\s)*)(\"([^\"]*)\"))*");
			Matcher m = pattern.matcher(content);
			
			while(m.find()){
				retrievedStrings.add(m.group());
			}
			
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String reduceString(String s){
		s = s.replaceAll("\"((\n|\\s)*\\+(\n|\\s)*)\"","");
		s = s.replaceAll("\"", "");
		
		return s;
	}
	
	
	@Override
	public JPanel getTable(){
		if(model.getRowCount() == 0){
			return null;
		}
		return this.tableContainer;
	}
	
	@Override
	@SuppressWarnings("serial")
	public void makeTable(){
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
		model.addColumn("AES Decrypted (G4)");
		Decoder decoder = new Decoder();
		
		
		for(String s: retrievedStrings){
			String base64 = "";
			String AES = "";
			String AESKey = "qktsccxutnfzlzbdoujgxjgodkejtzxtrlrczmdpuypreophdivwyfwafyfqyqfyjgemucirsdbvsnsmyoktocrdajlvzgqxoiyxxwokdliaamihtddxfkytiurfcyapablyicixozhtqgilaylctxfbshejcklwzdfmhspeedptexxuerslgfxtpzvttxydkbasnvpgtabxjakzmspaooufcpjpclkfxbgbcpmiflblmyzrtitxqdncpsdnnucn";
			if(decoder.Base64Decode(reduceString(s))!= null){
				base64 = decoder.Base64Decode(reduceString(s));
			}
			if(decoder.AESDecode(reduceString(s), AESKey)!= null){
				AES =decoder.AESDecode(reduceString(s), AESKey);
			}
			
			if(AES.equals("") && base64.equals("")){
				continue;
			}
			
			model.addRow(new Object[] {s, base64, AES});
		}
		
		table = new JTable(model);
		tableContainer.add(new JScrollPane(table));
	}
}
