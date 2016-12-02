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

package ym2151.Preferences.Swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import themidibus.MidiBus;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


/**
 * This Dialog lets You select the Input and Output Midi Devices.
 * */

public class MidiSettingsDialog extends JDialog {

	private static final long serialVersionUID = 7471917727078332966L;
	
	//some Swing stuff
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTable table_1;
	
	//input/output Model
	private SelectableMidiTableModel midiInModel;
	private SelectableMidiTableModel midiOutModel;
	
	
	//Master Tune Selector
	private JSpinner spinner;
	
	
	//Arguments
	private MidiBus midibus;
	private Properties props;
	

	/**
	 * Create the dialog.
	 * @param midibus 
	 */
	public MidiSettingsDialog(Properties props, MidiBus midibuss) {
		
		
		
		this.midibus=midibuss;
		this.props=props;
		
		//set up the Dialog
		setTitle("Midi Settings");
		setBounds(100, 100, 450, 584);
		
		//set the Layout Manager
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		//Find all Midi Devices (maybe the Devices changed, during Runtime )
		MidiBus.findMidiDevices();
		
		
		//Master Tune
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblMasterTune = new JLabel("Master Tune");
				panel.add(lblMasterTune, BorderLayout.WEST);
			}
			{
				spinner = new JSpinner();
				spinner.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						MidiSettingsDialog.this.props.update("MasterTune", spinner.getValue()+"");
						//Message is: F0 M T V F7 where M,T are encoded in ASCII and V is the Value
						byte[] sysexMessage = new byte[]{(byte) 0xf0, 0x4d, 0x54 , (byte) (((Byte)spinner.getValue()) + 63), (byte) 0xf7};
						midibus.sendMessage(sysexMessage);
						System.out.println("SYSEX\t"+"MT\t"+ ((Byte)spinner.getValue()+63));
					}
				});
				spinner.setModel(new SpinnerNumberModel(new Byte((byte) 0), new Byte((byte) - 63), new Byte((byte) 64), new Byte((byte) 1)));
				spinner.setValue(Byte.parseByte(props.getFirstValue("MasterTune", "0")));
				panel.add(spinner, BorderLayout.CENTER);
			}
		}
		
		
		//Midi INPUT Selection
		{
			JLabel lblMidiInput = new JLabel("Midi Input");
			contentPanel.add(lblMidiInput);
		}
		{
			
			table = new JTable();
			midiInModel = new SelectableMidiTableModel();
			
			table.setModel(midiInModel);
			loadMidiDevices(midiInModel, true);
			
			JScrollPane scrollPane = new JScrollPane(table);
			contentPanel.add(scrollPane);
		}
		
		//Midi OUTPUT Selection
		{
			JLabel lblMidiOutput = new JLabel("Midi Output");
			contentPanel.add(lblMidiOutput);
		}
		{
			table_1 = new JTable();
			midiOutModel = new SelectableMidiTableModel();
			loadMidiDevices(midiOutModel, false);
			table_1.setModel(midiOutModel);
			
			JScrollPane scrollPane = new JScrollPane(table_1);
			contentPanel.add(scrollPane);
			
		}
		
		//add a Listener that get the Events if the User Changed a Midi Device
		midiInModel.addTableModelListener(new TableModelListener(){
			public void tableChanged(TableModelEvent e) {
				if(e.getFirstRow()!= TableModelEvent.HEADER_ROW && e.getType() == TableModelEvent.UPDATE){
					if((Boolean) midiInModel.getValueAt(e.getFirstRow(), 0)){
						String input = (String)midiInModel.getValueAt(e.getFirstRow(),1);
						midibus.addInput(input);
						MidiSettingsDialog.this.props.put("IN", input);
					}else{
						String input = (String)midiInModel.getValueAt(e.getFirstRow(),1);
						midibus.removeInput(input);
						MidiSettingsDialog.this.props.remove("IN", input);
					}
				}
			}
		});
		
		//add a Listener that get the Events if the User Changed a Midi Device
		midiOutModel.addTableModelListener(new TableModelListener(){
			public void tableChanged(TableModelEvent e) {
				if(e.getFirstRow()!= TableModelEvent.HEADER_ROW && e.getType() == TableModelEvent.UPDATE){
					if((Boolean) midiOutModel.getValueAt(e.getFirstRow(), 0)){
						String output = (String)midiOutModel.getValueAt(e.getFirstRow(), 1);
						midibus.addOutput(output);
						MidiSettingsDialog.this.props.put("OUT", output);
					}else{
						String output = (String)midiOutModel.getValueAt(e.getFirstRow(), 1);
						midibus.removeOutput(output);
						MidiSettingsDialog.this.props.remove("OUT", output);
					}
				}
			}
		});

		
		//add a OK BUTTON
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	/**
	 * Loads the Current selected Midi Devices from the MidiBus into the Dialog.
	 * @param model the Model to add the Devices to
	 * @param in is it the INPUTs (true) or OUTPUTs (false) to load. 
	 * */
	private void loadMidiDevices(SelectableMidiTableModel model, boolean in){
		if(in){
			for(String s :  MidiBus.availableInputs()){
				boolean ena = false;
				for(String en : midibus.attachedInputs()){
					if(s.equalsIgnoreCase(en)){
						ena = true;
						break;
					}
				}
				model.addRow(new Object[]{ena, s});
			}
		}else{
			for(String s :  MidiBus.availableOutputs()){
				boolean ena = false;
				for(String en : midibus.attachedOutputs()){
					if(s.equalsIgnoreCase(en)){
						ena = true;
						break;
					}
				}
				model.addRow(new Object[]{ena, s});
			}
		}
	}
	
	
	/**
	 * Sets up a Table where the User can select and deselect Midi Devices
	 * 
	 * */
	private class SelectableMidiTableModel extends DefaultTableModel{
		private static final long serialVersionUID = 72089668202860173L;
		
		
		SelectableMidiTableModel(){
			super(new String[] {"Selected", "Name"},0);
		}
		
		
		Class<?>[] columnTypes = new Class[] {
				Boolean.class,Object.class
		};
		@Override
		public Class<?> getColumnClass(int i) {
			return columnTypes[i];
		}
		
		
	}
	
}
