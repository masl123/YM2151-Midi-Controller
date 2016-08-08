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

package ym2515.Swing;

import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.LineBorder;

import java.awt.Color;

import themidibus.MidiBus;
import ym2515.MainJFrame;
import ym2515.Utils;
import ym2515.DataModel.Channels;
import ym2515.DataModel.Instrument;
import ym2515.DataModel.OPMFile;
import ym2515.Swing.ListenerHashMap.HashMapListener;
import ym2515.xml.MidiMapping.MidiMapping;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;

import net.miginfocom.swing.MigLayout;

import java.io.File;
import java.io.IOException;




/**
 * A Panel to set the mapping between Instruments and Midi Channels. You can also set the Selected LFO and the Poly Instrument.
 * */
public class InstrumentMapperPanel extends JPanel {


	private JList<Channels> listMidiChannel; //free Midi Channels
	private JList<Channels> listSelMidiChannel; //list of Selected Midi Channels of the Selected Instrument
	private JList<Instrument> listInstrument; //list of Instruments
	
	private DefaultListModel<Channels> modelMidiChannel; //Contains the free Midi Channels
	private DefaultListModel<Instrument> modelInstrument; //list of Instruments
	private DefaultListModel<Channels> modelSelMidiChannel; //list of Selected Midi Channels of the Selected Instrument
	
	//TODO get rid of this map
	//maps the Instrument to the corresponding ControlPanel
	private Map<Instrument, ControlPanel> mapInstrToContrPanel = new HashMap<Instrument, ControlPanel>();
	
	private JTabbedPane instr;
	private MidiBus bus;
	private JFileChooser chooser; //the FileChooser to load/save OPM Files
	private MidiMapping mapping; //Mapping between the Midi CC Numbers and the KEY Values for the Knobs
		
	/**
	 * Creates a new InstrumentMapperPanel
	 * @param instr the Panel to add the ControlPanel for the Instruments to
	 * @param bus the MidibBus to use
	 * */
	public InstrumentMapperPanel(JTabbedPane instr, MidiBus bus) {
		
		//set Values
		this.instr = instr;
		this.bus=bus;
		
		//setup FileChooser
		chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("OPM - File", "opm"));
		
		//setup Models
		modelInstrument = new DefaultListModel<Instrument>();
		modelMidiChannel =  new DefaultListModel<Channels>();
		Utils.addAll(modelMidiChannel, Channels.values());
		modelSelMidiChannel = null;
		
		
		
		//Load Midi Mapping
		try {
			mapping = MidiMapping.loadMapping(new File(MidiMapping.class.getResource("/mapping.xml").toURI()));
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		
		
		//setup Layout
		setLayout(new MigLayout("", "[][150px:150px,grow,fill][150px:150px,grow,fill][100px:100px,fill][150px:150px,grow,fill]", "[272px,grow,center]"));
		
		//setup Buttons
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, "cell 0 0,grow");
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnAddInstrument = new JButton("Add New Instrument");
		btnAddInstrument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNewInstrument();
			}
		});
		buttonPanel.add(btnAddInstrument);
		
		JButton btnRemoveInstrument = new JButton("Remove Instrument");
		btnRemoveInstrument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeSelectedInstrument();
			}
		});
		buttonPanel.add(btnRemoveInstrument);
		
		JButton btnSelectLfo = new JButton("Set LFO");
		btnSelectLfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showLFOSelectorPanel();	
			}
		});
		buttonPanel.add(btnSelectLfo);
		
		JButton btnSetPolyInstrument = new JButton("Set Poly Instrument");
		btnSetPolyInstrument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showPolySelectorPanel();	
			}
			
		});
		buttonPanel.add(btnSetPolyInstrument);
		
		
		//Setup Instrument List
		JScrollPane scrlInstrument = new JScrollPane();
		scrlInstrument.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrlInstrument.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrlInstrument, "cell 1 0,grow");
		
		
		JPanel listInstrumentScrollFix = new JPanel();
		listInstrumentScrollFix.setLayout(new BorderLayout(0, 0));
		
		
		listInstrument = new JList<Instrument>();
		listInstrumentScrollFix.add(listInstrument);
		scrlInstrument.setViewportView(listInstrumentScrollFix);
		listInstrument.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(listInstrument.getSelectedValue() != null && listInstrument.getSelectedValue().defaultListModel != null){
					modelSelMidiChannel = listInstrument.getSelectedValue().defaultListModel;
					listSelMidiChannel.setModel(modelSelMidiChannel); //Change the Selected Channels, if the Instrument changes
				}
			}
		});
		listInstrument.setModel(modelInstrument);
		listInstrument.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listInstrument.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
		//setup Selected Channels List
		JScrollPane scrlSelChannels = new JScrollPane();
		scrlSelChannels.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrlSelChannels.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrlSelChannels, "cell 2 0,grow");
		
		JPanel listSelMidiChannelScrollFix = new JPanel();
		listSelMidiChannelScrollFix.setLayout(new BorderLayout(0, 0));
		
		
		listSelMidiChannel = new JList<Channels>();
		listSelMidiChannelScrollFix.add(listSelMidiChannel);
		scrlSelChannels.setViewportView(listSelMidiChannelScrollFix);
		listSelMidiChannel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
		//setup LEFT/RIGHT Buttons
		JPanel panel = new JPanel();
		add(panel, "cell 3 0,grow");
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{100, 0};
		gbl_panel.rowHeights = new int[]{65, 40, 40, 46, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnLeft = new JButton("<----");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(modelSelMidiChannel != null){ //Don't do anything if no instrument is selected
					//add the selected channels to the selected channels of the instrument
					List<Channels> sel = listMidiChannel.getSelectedValuesList();
					Utils.addAll(modelSelMidiChannel, sel);
					Utils.removeAll(modelMidiChannel, sel);
				}
			}
		});
		GridBagConstraints gbc_btnLeft = new GridBagConstraints();
		gbc_btnLeft.fill = GridBagConstraints.BOTH;
		gbc_btnLeft.insets = new Insets(0, 0, 5, 0);
		gbc_btnLeft.gridx = 0;
		gbc_btnLeft.gridy = 1;
		panel.add(btnLeft, gbc_btnLeft);
		
		JButton btnRight = new JButton("---->");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(modelSelMidiChannel != null){//Don't do anything if no instrument is selected
					//remove the selected channels from the instrument and add them the the Free Channels List
					List<Channels> sel = listSelMidiChannel.getSelectedValuesList();
					Utils.addAll(modelMidiChannel, sel);
					Utils.removeAll(modelSelMidiChannel, sel);
				}
			}
		});
		GridBagConstraints gbc_btnRight = new GridBagConstraints();
		gbc_btnRight.insets = new Insets(0, 0, 5, 0);
		gbc_btnRight.fill = GridBagConstraints.BOTH;
		gbc_btnRight.gridx = 0;
		gbc_btnRight.gridy = 2;
		panel.add(btnRight, gbc_btnRight);

		
		//SETUP Free Midi Channels List
		JScrollPane scrlChannels = new JScrollPane();
		scrlChannels.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrlChannels.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrlChannels, "cell 4 0,grow");
		
		JPanel listMidiChannelScrollFix = new JPanel();
		listMidiChannelScrollFix.setLayout(new BorderLayout(0, 0));
		
		listMidiChannel = new JList<Channels>();
		listMidiChannelScrollFix.add(listMidiChannel);
		scrlChannels.setViewportView(listMidiChannelScrollFix);
		listMidiChannel.setModel(modelMidiChannel);
		listMidiChannel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
	}

	/**
	 * Lets the User  save selected instruments as a OPM File
	 * 
	 * */
	public void saveToOPM(){
		
		//let the User select which Instruments to Export
		ListSelector<Instrument> sel = new ListSelector<Instrument>(InstrumentMapperPanel.this.modelInstrument);
		int ret = JOptionPane.showConfirmDialog(InstrumentMapperPanel.this, sel, "Select Instruments to Save", JOptionPane.OK_CANCEL_OPTION);
		if (ret == JOptionPane.CANCEL_OPTION) {
			return;
		}

		//the OPM File to Save
		OPMFile f = new OPMFile();
		
		//selected Instruments
		List<Instrument> l = sel.getSelectedValue();

		//add All INstruments to the OPM File
		for (int i = 0; i < l.size(); i++) {
			f.addInstrument(l.get(i), i);
		}
		
		//let the User select the Location to save the File
		ret = InstrumentMapperPanel.this.chooser.showSaveDialog(InstrumentMapperPanel.this);
		if (ret == JFileChooser.CANCEL_OPTION || ret == JFileChooser.ERROR_OPTION){
			return;
		}
		
		//write the File
		try
		{
			OPMFile.saveFile(f, InstrumentMapperPanel.this.chooser.getSelectedFile());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(InstrumentMapperPanel.this, "Could not Save File.", "ERROR", 0);
			e.printStackTrace();
		}
	}
	
	/**
	 * Lets the User load instruments from OPM File into the MapperPanel.
	 * 
	 * */
	public void loadFromOPM(){	
		//Let the User select a File to Load
		int returnVal = InstrumentMapperPanel.this.chooser.showOpenDialog(InstrumentMapperPanel.this);
        if (returnVal == JFileChooser.CANCEL_OPTION) {
          return;
        }

        try
        {
          //Load the File into a OPM File
          OPMFile o = OPMFile.loadFile(InstrumentMapperPanel.this.chooser.getSelectedFile());

          //Let the User Select the Instruments to Import
          ListSelector<Instrument> l = new ListSelector<Instrument>(o.getInstruments());
          int ret = JOptionPane.showConfirmDialog(InstrumentMapperPanel.this, l, "Select Instruments to Load", JOptionPane.OK_CANCEL_OPTION);
          if (ret == JOptionPane.CANCEL_OPTION || ret == JFileChooser.ERROR_OPTION) {
            return;
          }
          
          
          //Add the Selected Instruments
          List<Instrument> ins = l.getSelectedValue();
          for (Instrument i : ins) {
            
        	//If there is no Instrument in the Instrument List, then set the LFO and POLY Instrument
        	if(modelInstrument.size()==0){
      			setLFO(i);
      			setPolyInstrument(i);
      		}
        	
        	//create a new Channels List (for the Selected Channels)
        	i.defaultListModel = new DefaultListModel<Channels>();
            InstrumentMapperPanel.this.addInstrument(i);
            
          }
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
	}
	
	
	
	/**
	 * Adds a new Instrument to the Instrument List
	 * 
	 * */
	private void addNewInstrument(){
		//get Name of the Instrument
		String s = "";
		while(s.trim().isEmpty() && s != null){
			s = JOptionPane.showInputDialog(InstrumentMapperPanel.this, "Enter a Name for the Instrument", "Name", JOptionPane.PLAIN_MESSAGE);
			if(s == null){
				return;
			}
		}
		
		
		//load init Instrument
		OPMFile f = OPMFile.loadFile(OPMFile.class.getResource("/init.opm"));
		Instrument i = f.getInstruments()[0];
		
		//set name and Channels List of the Instrument
		i.defaultListModel = new DefaultListModel<Channels>();
		i.name = s;
		
		//If there is no Instrument in the Instrument List, then set the LFO and POLY Instrument
		if(modelInstrument.size()==0){
			setLFO(i);
			setPolyInstrument(i);
		}
		
		
		addInstrument(i);
	}
	
	
	/**
	 * Lets the User select which Instruments to Remove.
	 * */
	private void removeSelectedInstrument(){
		Instrument i = listInstrument.getSelectedValue();
		if(i == null){
			return;
		}

		//remove Panel
		InstrumentMapperPanel.this.instr.remove(mapInstrToContrPanel.get(i));
		mapInstrToContrPanel.remove(i);
		
		
		//remove all Channels from Mapping
		Object[] ch = modelSelMidiChannel.toArray();
		for(Object o : ch){
			modelMidiChannel.addElement((Channels) o);
		}
		
		modelSelMidiChannel = null;
		listSelMidiChannel.setModel(new DefaultListModel<Channels>());
		
		modelInstrument.removeElement(i);
	}
	
	/**
	 * Resets the InstrumentMapperPanel and adds the Instruments to the Panel.
	 * @param instruments Instruments to add.
	 * 
	 * */
	public void setAllInstruments(Instrument[] instruments){
		
		//delete Everything
		modelMidiChannel.removeAllElements();
		modelInstrument.removeAllElements();
		if(modelSelMidiChannel != null)
			modelSelMidiChannel.removeAllElements();
		modelSelMidiChannel = null;
		
		for(Entry<Instrument, ControlPanel> e : mapInstrToContrPanel.entrySet()){
			instr.remove(e.getValue());
		}
		mapInstrToContrPanel = new HashMap<Instrument, ControlPanel>();
		
		//rebuild Data
		Utils.addAll(modelMidiChannel, Channels.values());
		
		for(Instrument i : instruments){
			addInstrument(i);
			if(i.defaultListModel != null){
				Enumeration<Channels> enume = i.defaultListModel.elements();
				while(enume.hasMoreElements()){
					modelMidiChannel.removeElement(enume.nextElement());
				}
			}
		}
		
		repaint();
	}

	/**
	 * @return all Instruments
	 * */
	public Instrument[] getAllInstruments(){
		Instrument[] ins = new Instrument[modelInstrument.size()];
		
		for(int i = 0; i < modelInstrument.size(); i++){
			ins[i] = modelInstrument.get(i);
		}
		return ins;
	}
	

	/**
	 * sets the LFO of an Instrument to active
	 * @param i the Instrument to set the LFO Active
	 * */
	public void setLFO(Instrument i){
		//deselect all LFOs
		Instrument[] ins = getAllInstruments();
		for(Instrument in : ins){
			in.lfoSelected = false;
		}
		
		//select the Selected LFO
		i.lfoSelected = true;
		i.lfo.map.syncAll();
		
		listInstrument.repaint();
	}
	
	/**
	 * sets a Instrument to be the Poly Instrument
	 * @param i the Instrument to set to Poly
	 * */
	public void setPolyInstrument(Instrument selectedValue) {
			//deselect all Poly Instruments
			Instrument[] ins = getAllInstruments();
			for(Instrument in : ins){
				in.polyInstr = false;
			}
			
			//select the Selected Poly Instrument
			selectedValue.polyInstr = true;
			listInstrument.repaint();
			if(MainJFrame.POLYMODE){
				selectedValue.syncALL();
			}
	}
	
	/**
	 * Shows a panel to select which LFO to set to active (and then sets it)
	 * */
	private void showLFOSelectorPanel(){
		JScrollPane p = new JScrollPane();
		JList<Instrument> l = new JList<Instrument>();
		l.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		p.setViewportView(l);
		l.setModel(modelInstrument);
		
		
		int ret = JOptionPane.showConfirmDialog(InstrumentMapperPanel.this, p, "Select the LFO", JOptionPane.OK_CANCEL_OPTION);
		if(ret == JOptionPane.OK_OPTION){
			if(l.getSelectedValue() != null)
				setLFO(l.getSelectedValue());
		}	
	}
	
	/**
	 * Shows a panel to select which Instrument to set to Poly (and then sets it)
	 * */
	private void showPolySelectorPanel() {
		JScrollPane p = new JScrollPane();
		JList<Instrument> l = new JList<Instrument>();
		l.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		p.setViewportView(l);
		l.setModel(modelInstrument);
		
		
		int ret = JOptionPane.showConfirmDialog(InstrumentMapperPanel.this, p, "Select the LFO", JOptionPane.OK_CANCEL_OPTION);
		if(ret == JOptionPane.OK_OPTION){
			if(l.getSelectedValue() != null)
				setPolyInstrument(l.getSelectedValue());
		}	
	}
	
	
	/**
	 * Adds a Mapper Listener to a Instrument (if a value changes in the Instrument, it will send MidiMessages the the selected channels)
	 * */
	public void addMapperListener(MidiMapping map, Instrument i){
		i.op1.map.addHashMapListener(new MapperListener(map.map_op1,i));
		i.op2.map.addHashMapListener(new MapperListener(map.map_op2,i));
		i.op3.map.addHashMapListener(new MapperListener(map.map_op3,i));
		i.op4.map.addHashMapListener(new MapperListener(map.map_op4,i));
		i.lfo.map.addHashMapListener(new MapperListener(map.map_lfo,i)); //TODO Only one LFO of all Instruments should be Mapped
		i.common.map.addHashMapListener(new MapperListener(map.map_common,i));
	}
	
	/**
	 * Adds a Instrument to the InstrumentMapperPanel.
	 * @param i Instrument to add
	 * */
	public void addInstrument(Instrument i){
		ControlPanel p =  new ControlPanel(i);
		modelInstrument.addElement(i);
		mapInstrToContrPanel.put(i,p);
		InstrumentMapperPanel.this.instr.addTab(i.name, p);
		addMapperListener(mapping, i);
		i.syncALL();
	}
	
	
	/**
	 * A Listener for the Instruments, to Send MidiData to the Selected MidiChannels
	 * */
	private class MapperListener implements HashMapListener<String, Integer>{
		private HashMap<String, Integer> map;
		private Instrument i;
		
		public MapperListener(HashMap<String, Integer> map, Instrument i){
			this.map=map;
			this.i=i;
		}
		
		public void valueChanged(String key, Integer value) {
			//get the mapping
			Integer cc = map.get(key);
			
			//Check if there is a Mapping
			if(cc == null){
				System.out.println("Button not Mapped: "+ key);
				return;
			}
			
			//Check if the Mapping is OOB
			if(cc == null || cc < 0 || cc > 127){
				System.out.println("Button Mapping out of Bounds: "+key);
				return;
			}
			
			
			
			if(mapping.map_lfo == map && !i.lfoSelected){
				//Don't send LFO Changes of Not Selected Instruments
				return;
			}else if(mapping.map_lfo == map){
				//send LFO Changes of Selected Instrument
				bus.sendControllerChange(Channels.CH1.num, cc, value);
				System.out.println(key+"   "+value+"   "+cc+"   "+Channels.CH1);
			}else if(!MainJFrame.POLYMODE){
				//Channel Mode
				for(Object o : i.defaultListModel.toArray()){
					Channels c = (Channels) o;
					bus.sendControllerChange(c.num, cc, value);
					System.out.println(key+"   "+value+"   "+cc+"   "+c);
				}
			}else if(MainJFrame.POLYMODE && i.polyInstr){
				//Only send Changes of Poly-Instrument
				bus.sendControllerChange(Channels.CH1.num, cc, value);
				System.out.println(key+"   "+value+"   "+cc+"   "+Channels.CH1);
			}
		}
	}
}











