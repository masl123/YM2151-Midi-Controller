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


package ym2151.notUsed;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JToggleButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

import ym2151.DataModel.OPMFile;

public class IOPanel extends JPanel {
	private JTextField fldPath;
	private JFileChooser chooser;
	private JSpinner spnChannel;
	
	
	/**
	 * Create the panel.
	 */
	public IOPanel() {
		chooser = new JFileChooser();
		
		
		
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{321, 43, 34, 134, 82, 82, 0, 0};
		gridBagLayout.rowHeights = new int[]{34, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		fldPath = new JTextField();
		GridBagConstraints gbc_fldPath = new GridBagConstraints();
		gbc_fldPath.fill = GridBagConstraints.BOTH;
		gbc_fldPath.insets = new Insets(0, 0, 0, 5);
		gbc_fldPath.gridx = 0;
		gbc_fldPath.gridy = 0;
		add(fldPath, gbc_fldPath);
		fldPath.setColumns(10);
		
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = chooser.showOpenDialog(IOPanel.this);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					fldPath.setText(chooser.getSelectedFile().toString());
				}
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.VERTICAL;
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 0;
		add(button, gbc_button);
		
		spnChannel = new JSpinner();
		spnChannel.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spnChannel = new GridBagConstraints();
		gbc_spnChannel.insets = new Insets(0, 0, 0, 5);
		gbc_spnChannel.fill = GridBagConstraints.BOTH;
		gbc_spnChannel.gridx = 2;
		gbc_spnChannel.gridy = 0;
		add(spnChannel, gbc_spnChannel);
		
		JButton btnLoad = new JButton("L");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					OPMFile.loadFile(new File(fldPath.getText()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		});
		
		JLabel lblInstrName = new JLabel("");
		GridBagConstraints gbc_lblInstrName = new GridBagConstraints();
		gbc_lblInstrName.insets = new Insets(0, 0, 0, 5);
		gbc_lblInstrName.gridx = 3;
		gbc_lblInstrName.gridy = 0;
		add(lblInstrName, gbc_lblInstrName);
		GridBagConstraints gbc_btnLoad = new GridBagConstraints();
		gbc_btnLoad.insets = new Insets(0, 0, 0, 5);
		gbc_btnLoad.fill = GridBagConstraints.BOTH;
		gbc_btnLoad.gridx = 4;
		gbc_btnLoad.gridy = 0;
		add(btnLoad, gbc_btnLoad);
		
		JButton btnSave = new JButton("S");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OPMFile.saveFile(null,new File( fldPath.getText()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				
				
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 5;
		gbc_btnSave.gridy = 0;
		add(btnSave, gbc_btnSave);
		
		JToggleButton tglbtnLoadlfo = new JToggleButton("Load LFO");
		GridBagConstraints gbc_tglbtnLoadlfo = new GridBagConstraints();
		gbc_tglbtnLoadlfo.fill = GridBagConstraints.BOTH;
		gbc_tglbtnLoadlfo.gridx = 6;
		gbc_tglbtnLoadlfo.gridy = 0;
		add(tglbtnLoadlfo, gbc_tglbtnLoadlfo);

	}
	
	
	
	
	

}









