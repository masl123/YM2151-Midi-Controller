/**
*	YM2151 - Midi Controller Software for Arduino Shield
*	(C) 2016  Marcel Weiﬂ
*
*	This program is free software : you can redistribute it and / or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation, either version 3 of the License, or
*	(at your option) any later version.
*
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
*	GNU General Public License for more details.
*
*	You should have received a copy of the GNU General Public License
*	along with this program.If not, see <http://www.gnu.org/licenses/>.
*/

package ym2151;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import net.miginfocom.swing.MigLayout;

import org.xml.sax.SAXException;

import themidibus.MidiBus;


import ym2151.DataModel.Channels;
import ym2151.DataModel.Instrument;
import ym2151.Preferences.Swing.MidiSettingsDialog;
import ym2151.Preferences.Swing.Properties;
import ym2151.Preferences.Swing.Properties.Entry;
import ym2151.Swing.InstrumentMapperPanel;
import ym2151.xml.ControlFile.PatchFileManager;

/**
 * This is the Main Window which opens when the Program is started.
 * */
public class MainJFrame extends JFrame {

	
	
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7566951928240167398L;
	public static final String VERSION = "V1.0.2";
	
	
	private JFileChooser chooser;
	private Properties props;
	
	
	public static void main(final String[] args) {
					MainJFrame frame = new MainJFrame();
					try {
						frame.loadPatch(args.length == 1 ? args[0] : null);
					} catch (Exception e) {
						e.printStackTrace();
					}
					frame.setVisible(true);
				
	}

	//MidiBus we use in the Program
	public final static MidiBus MIDIBUS = new MidiBus();
	
	
	//Polymode Active?
	public static boolean POLYMODE = true;
	
	
	private InstrumentMapperPanel instrumentMapperPanel;
	
	/**
	 * Create the frame.
	 */
	public MainJFrame() {
		
		// save/load Midi Settings when Window Opens/Closes
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				storeProperties(props); 
			}
			@Override
			public void windowOpened(WindowEvent arg0) {
				props = loadProperties();
			}
		});
		
		//set some Frame Properties
		setTitle("YM2151 Arduino Shield Controller - "+VERSION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1145, 698);
		
		
		//create the FileChooser, used for Opening ym2151 Files
		chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("*.ym2151 - File", "ym2151"));
		chooser.setMultiSelectionEnabled(false);
		
		//add a MenuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//add a File Menu
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		//add Open Menu Button
		JMenuItem mntmOpen = new JMenuItem("Open ...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					loadFromXml();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Could not Load File!", "ERROR" , JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} 
			}
		});
		
		//add new Menu Button
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instrumentMapperPanel.setAllInstruments(new Instrument[0]);
			}
		});
		mnFile.add(mntmNew);
		mnFile.add(mntmOpen);
		
		//add Save Menu Button
		JMenuItem mntmSave = new JMenuItem("Save As...");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					saveToXml();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Could not Save File!", "ERROR" , JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} 
			}
		});
		mnFile.add(mntmSave);
		
		//add Exit Menu Button
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				storeProperties(props);
				System.exit(0);
			}
		});
		
		//add Import From OPM Menu Option
		JMenuItem mntmImportFromOpm = new JMenuItem("Import OPM File ...");
		mntmImportFromOpm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instrumentMapperPanel.loadFromOPM();
			}
		});
		mnFile.add(mntmImportFromOpm);
		
		//add Export From OPM Menu Option
		JMenuItem mntmExportToOpm = new JMenuItem("Export OPM File ...");
		mntmExportToOpm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instrumentMapperPanel.saveToOPM();
			}
		});
		mnFile.add(mntmExportToOpm);
		
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		mnFile.add(mntmExit);
		
		
		//add a Midi Menu to the MenuBar
		JMenu mnOptions = new JMenu("Midi");
		menuBar.add(mnOptions);
		
		
		//Button to Enable and disable Polymode
		final JCheckBoxMenuItem chckbxmntmPolyMode = new JCheckBoxMenuItem("Poly Mode");
		chckbxmntmPolyMode.setSelected(POLYMODE);
		chckbxmntmPolyMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				POLYMODE = chckbxmntmPolyMode.isSelected();
				MIDIBUS.sendControllerChange(Channels.CH1.num, 74, POLYMODE ? 127 : 0);
				System.out.println("POLY\t"+(POLYMODE ? 127 : 0)+"\t74\t"+Channels.CH1);
			}
		});
		mnOptions.add(chckbxmntmPolyMode);
		
		
		//add the Main Tabbed Frame
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.LEFT);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		
		//add the Mapper
		JPanel mapperPanel = new JPanel();
		mapperPanel.setBorder(null);
		tabbedPane.addTab("Mapper", null, mapperPanel, null);
		tabbedPane.setEnabledAt(0, true);
		mapperPanel.setLayout(new MigLayout("", "[grow,fill][center][grow,fill]", "[grow,fill][center][grow,fill]"));
		
		
		JScrollPane scrollPane = new JScrollPane();
		mapperPanel.add(scrollPane, "cell 1 1,grow");
		
		instrumentMapperPanel = new InstrumentMapperPanel(tabbedPane, MIDIBUS);
		scrollPane.setViewportView(instrumentMapperPanel);

		//Button which sends all Midi data to the Arduino
		JMenuItem mntmSyncInstrument = new JMenuItem("Sync Instruments");
		mntmSyncInstrument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Instrument[] a = instrumentMapperPanel.getAllInstruments();
				for(Instrument ins : a){
					ins.syncALL();
				}
			} 
		});
		mnOptions.add(mntmSyncInstrument);
		
		JSeparator separator_3 = new JSeparator();
		mnOptions.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		mnOptions.add(separator_4);
		
		
		//Opens Midi Settings (Midi Device Selection)
		JMenuItem mntmSettings = new JMenuItem("Midi Settings");
		mnOptions.add(mntmSettings);
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MidiSettingsDialog p = new MidiSettingsDialog(props, MIDIBUS);
				p.setVisible(true);
				
			}
		});
		
		//add About Message
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About...");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				JScrollPane scr = new JScrollPane();
				scr.setMaximumSize(new Dimension(500,500));
				scr.setPreferredSize(new Dimension(500,500));
				
				JTextArea area = new JTextArea();
				area.setText(LicenseText.LicenseText);
				area.setCaretPosition(0);
				scr.setViewportView(area);

				JOptionPane.showMessageDialog(MainJFrame.this, scr, "About...", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		
		mnHelp.add(mntmAbout);
		
		
		
		//is just doing Midi Through
		MidiInHandler handler = new MidiInHandler(MIDIBUS);
	}

	
	/**
	 * Saves the Current state of the Controller to a File (all Instruments and Mapping)
	 * */
	private void saveToXml() throws IOException, ParserConfigurationException, TransformerException{
		
		
		PatchFileManager mgr = new PatchFileManager();
		
		//create a Save File Dialog
		int ret = MainJFrame.this.chooser.showSaveDialog(MainJFrame.this);
		if (ret == JFileChooser.CANCEL_OPTION || ret == JFileChooser.ERROR_OPTION){
			return;
		}

		//get the Selected File
		File path = chooser.getSelectedFile();
		
		//add the right File Extension if necessary
		if(!Utils.getExtension(path).equals("ym2151")){
			path = new File(path.getPath()+".ym2151");
		}
		
		//dont try to save a dir
		if(path.isDirectory()){
			JOptionPane.showMessageDialog(null, "Please don't select a Folder!", "ERROR" , JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//ask to Overwrite a File if necessary
		if(path.exists()){
			int a = JOptionPane.showConfirmDialog(null, "Overwrite File:" +path, "Overwrite?",JOptionPane.YES_NO_OPTION ,JOptionPane.INFORMATION_MESSAGE);
			if(a == JOptionPane.CLOSED_OPTION){
				return;
			}
			path.delete();
		}else{
			File par = path.getParentFile();
			if(par!= null)
				par.mkdirs();
		}
		
		
		path.createNewFile();
		
		mgr.saveDocument(mgr.createDocument(instrumentMapperPanel.getAllInstruments()), path);
		
		JOptionPane.showMessageDialog(null, "Saving Done!", "DONE", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	/**
	 * Loads a File Patch and sets the Current state of the Controller
	 * */
	private void loadFromXml() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{

		
		
		PatchFileManager mgr = new PatchFileManager();
		int ret = MainJFrame.this.chooser.showOpenDialog(MainJFrame.this);
		if (ret == JFileChooser.CANCEL_OPTION || ret == JFileChooser.ERROR_OPTION){
			return;
		}
		
		
		File path = chooser.getSelectedFile();
		if(path.isDirectory()){
			JOptionPane.showMessageDialog(null, "Please don't select a Folder!", "ERROR" , JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(!path.exists()){
			JOptionPane.showMessageDialog(null, "File not Found!", "ERROR" , JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		
		instrumentMapperPanel.setAllInstruments(mgr.getInstruments(path));
		
		JOptionPane.showMessageDialog(null, "Loading Done!", "DONE", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Saves selected Midi Devices to the File props.properties in the directory where the jar file is located
	 * */
	private void storeProperties(Properties p){		
		try{
			File f = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			if(Utils.getExtension(f).equals("jar")){
				f = new File(new File(f.getParent()).getPath()+"\\props.properties");	
			}else{
				f = new File(f.getPath()+"\\props.properties");	
			}
			
			if(f.exists()){	
				f.delete();
			}
			f.createNewFile();
			
			p.storeToXML(new FileOutputStream(f), null );
		}catch(Exception e){
			e.printStackTrace(); //Its not too bad if we don't save the Midi Settings
		}
	}
	
	/**
	 * Loads the selected Midi Devices from the File props.properties in the directory where the jar file is located
	 * */
	private Properties loadProperties(){
		
		Properties p = new Properties();
		try {
			File f = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			
			if(Utils.getExtension(f).equals("jar")){
				f = new File(new File(f.getParent()).getPath()+"\\props.properties");	
			}else{
				f = new File(f.getPath()+"\\props.properties");	
			}
			
			if(f.exists()){
				p.load(new FileInputStream(f));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//remove all Instrument Entries
		List<Entry<String,String>> entries = new LinkedList<Entry<String,String>>();
		for(Entry<String, String> e  : p.getList()){
			
			if(e.key.equalsIgnoreCase("IN")){
				if(!MIDIBUS.addInput(e.value)){
					entries.add(e);
				}
			}else if(e.key.equalsIgnoreCase("OUT")){
				if(!MIDIBUS.addOutput(e.value)){
					entries.add(e);	
				}
			}
		}
		p.getList().removeAll(entries);
		return p;
	}
	
	
	/**
	 * tries to load a Patch from the Path
	 * @param arg the Path to the Patch
	 * */
	public void loadPatch(String arg) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		//parse arguments (if any)
			
		if(arg == null){
			return;
		}
		PatchFileManager mgr = new PatchFileManager();
		instrumentMapperPanel.setAllInstruments(mgr.getInstruments(new File(arg)));
	}
}
















