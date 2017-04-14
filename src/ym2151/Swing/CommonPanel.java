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

package ym2151.Swing;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;

import ym2151.DataModel.Common;
import ym2151.Swing.ListenerHashMap.HashMapListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.Component;


/**
 * The Panel for the Controls of the Common part of the YM2151 Chip
 * */
public class CommonPanel extends JPanel {

	private AlgorithmSelector algorithmSelector;
	
	/**
	 * Creates a new Common Panel
	 * @param common the Common Data Model
	 * 
	 * */
	public CommonPanel(final Common common) {

		//set up the Layout
		setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{5, 214, 82, 5, 0};
		gridBagLayout.rowHeights = new int[]{0, 239, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		//Left Panel
		JPanel pnlleft = new JPanel();
		GridBagConstraints gbc_pnlleft = new GridBagConstraints();
		gbc_pnlleft.fill = GridBagConstraints.BOTH;
		gbc_pnlleft.insets = new Insets(0, 0, 5, 5);
		gbc_pnlleft.gridx = 1;
		gbc_pnlleft.gridy = 1;
		add(pnlleft, gbc_pnlleft);
		GridBagLayout gbl_pnlleft = new GridBagLayout();
		gbl_pnlleft.columnWidths = new int[]{100, 0};
		gbl_pnlleft.rowHeights = new int[]{10, 40, 10, 40, 87, 64, 0};
		gbl_pnlleft.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlleft.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		pnlleft.setLayout(gbl_pnlleft);
		
		//Operator on/off Button Label
		JLabel lblOperatorOnoff = new JLabel("Operator - ON/OFF");
		lblOperatorOnoff.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblOperatorOnoff = new GridBagConstraints();
		gbc_lblOperatorOnoff.fill = GridBagConstraints.BOTH;
		gbc_lblOperatorOnoff.insets = new Insets(0, 0, 5, 0);
		gbc_lblOperatorOnoff.gridx = 0;
		gbc_lblOperatorOnoff.gridy = 0;
		pnlleft.add(lblOperatorOnoff, gbc_lblOperatorOnoff);
		
		//Operator on/off Buttons
		NamedSwitchButtonRowPanel switchButtonRowPanel = new NamedSwitchButtonRowPanel(common.map, new String[]{"OP1_EN","OP2_EN","OP3_EN","OP4_EN"});
		GridBagConstraints gbc_switchButtonRowPanel = new GridBagConstraints();
		gbc_switchButtonRowPanel.fill = GridBagConstraints.BOTH;
		gbc_switchButtonRowPanel.insets = new Insets(0, 0, 5, 0);
		gbc_switchButtonRowPanel.gridx = 0;
		gbc_switchButtonRowPanel.gridy = 1;
		pnlleft.add(switchButtonRowPanel, gbc_switchButtonRowPanel);
		
		
		//AM Sense Button Label
		JLabel lblAmSense = new JLabel("AM Sense - ON/OFF");
		lblAmSense.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblAmSense = new GridBagConstraints();
		gbc_lblAmSense.fill = GridBagConstraints.BOTH;
		gbc_lblAmSense.insets = new Insets(0, 0, 5, 0);
		gbc_lblAmSense.gridx = 0;
		gbc_lblAmSense.gridy = 2;
		pnlleft.add(lblAmSense, gbc_lblAmSense);
		
		//AM Sense on/off Buttons
		NamedSwitchButtonRowPanel switchButtonRowPanel_1 = new NamedSwitchButtonRowPanel(common.map, new String[]{"AMSEN_OP1","AMSEN_OP2","AMSEN_OP3","AMSEN_OP4"});
		GridBagConstraints gbc_switchButtonRowPanel_1 = new GridBagConstraints();
		gbc_switchButtonRowPanel_1.fill = GridBagConstraints.BOTH;
		gbc_switchButtonRowPanel_1.insets = new Insets(0, 0, 5, 0);
		gbc_switchButtonRowPanel_1.gridx = 0;
		gbc_switchButtonRowPanel_1.gridy = 3;
		pnlleft.add(switchButtonRowPanel_1, gbc_switchButtonRowPanel_1);
		
		
		//Panel for the Knobs
		JPanel pnlKnobs = new JPanel();
		GridBagConstraints gbc_pnlKnobs = new GridBagConstraints();
		gbc_pnlKnobs.insets = new Insets(0, 0, 5, 0);
		gbc_pnlKnobs.fill = GridBagConstraints.BOTH;
		gbc_pnlKnobs.gridx = 0;
		gbc_pnlKnobs.gridy = 4;
		pnlleft.add(pnlKnobs, gbc_pnlKnobs);
		GridBagLayout gbl_pnlKnobs = new GridBagLayout();
		gbl_pnlKnobs.columnWidths = new int[]{73, 73, 73, 0};
		gbl_pnlKnobs.rowHeights = new int[]{29, 40, 0};
		gbl_pnlKnobs.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlKnobs.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnlKnobs.setLayout(gbl_pnlKnobs);
		
		//Create the Labels
		JLabel lblFB = new JLabel("FB");
		lblFB.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblFB = new GridBagConstraints();
		gbc_lblFB.fill = GridBagConstraints.BOTH;
		gbc_lblFB.insets = new Insets(0, 0, 5, 5);
		gbc_lblFB.gridx = 0;
		gbc_lblFB.gridy = 0;
		pnlKnobs.add(lblFB, gbc_lblFB);
		
		JLabel lblAMS = new JLabel("AMS");
		lblAMS.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblAMS = new GridBagConstraints();
		gbc_lblAMS.fill = GridBagConstraints.BOTH;
		gbc_lblAMS.insets = new Insets(0, 0, 5, 5);
		gbc_lblAMS.gridx = 1;
		gbc_lblAMS.gridy = 0;
		pnlKnobs.add(lblAMS, gbc_lblAMS);
		
		JLabel lblPMS = new JLabel("PMS");
		lblPMS.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblPMS = new GridBagConstraints();
		gbc_lblPMS.fill = GridBagConstraints.BOTH;
		gbc_lblPMS.insets = new Insets(0, 0, 5, 0);
		gbc_lblPMS.gridx = 2;
		gbc_lblPMS.gridy = 0;
		pnlKnobs.add(lblPMS, gbc_lblPMS);
		
		//Create the Knobs
		NamedKnob knobFB = new NamedKnob(common.map, "FL");
		GridBagConstraints gbc_knobFB = new GridBagConstraints();
		gbc_knobFB.fill = GridBagConstraints.BOTH;
		gbc_knobFB.insets = new Insets(0, 0, 0, 5);
		gbc_knobFB.gridx = 0;
		gbc_knobFB.gridy = 1;
		pnlKnobs.add(knobFB, gbc_knobFB);
		
		
		NamedKnob knobAMS = new NamedKnob(common.map, "AMS");
		GridBagConstraints gbc_knobAMS = new GridBagConstraints();
		gbc_knobAMS.fill = GridBagConstraints.BOTH;
		gbc_knobAMS.insets = new Insets(0, 0, 0, 5);
		gbc_knobAMS.gridx = 1;
		gbc_knobAMS.gridy = 1;
		pnlKnobs.add(knobAMS, gbc_knobAMS);
		
		
		NamedKnob knobPMS = new NamedKnob(common.map, "PMS");
		GridBagConstraints gbc_knobPMS = new GridBagConstraints();
		gbc_knobPMS.fill = GridBagConstraints.BOTH;
		gbc_knobPMS.gridx = 2;
		gbc_knobPMS.gridy = 1;
		pnlKnobs.add(knobPMS, gbc_knobPMS);
		
		
		
		//Setup Volume Knob
		JPanel pnlVolume = new JPanel();
		GridBagConstraints gbc_pnlVolume = new GridBagConstraints();
		gbc_pnlVolume.fill = GridBagConstraints.BOTH;
		gbc_pnlVolume.gridx = 0;
		gbc_pnlVolume.gridy = 5;
		pnlleft.add(pnlVolume, gbc_pnlVolume);
		pnlVolume.setLayout(new BoxLayout(pnlVolume, BoxLayout.Y_AXIS));
		
		JLabel lblVolume = new JLabel("Volume");
		lblVolume.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlVolume.add(lblVolume);
		
		NamedKnob knobVolume = new NamedKnob(common.map, "VOL");
		knobVolume.setVar(63);
		pnlVolume.add(knobVolume);
		
		
		//add the Algorithm Selector and Buttons
		JPanel pnlRight = new JPanel();
		GridBagConstraints gbc_pnlRight = new GridBagConstraints();
		gbc_pnlRight.insets = new Insets(0, 0, 5, 5);
		gbc_pnlRight.fill = GridBagConstraints.BOTH;
		gbc_pnlRight.gridx = 2;
		gbc_pnlRight.gridy = 1;
		add(pnlRight, gbc_pnlRight);
		GridBagLayout gbl_pnlRight = new GridBagLayout();
		gbl_pnlRight.columnWidths = new int[] {75, 0};
		gbl_pnlRight.rowHeights = new int[]{20, 176, 40, 0};
		gbl_pnlRight.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlRight.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		pnlRight.setLayout(gbl_pnlRight);
		
		JLabel lblAlgorithm = new JLabel("Algorithm");
		lblAlgorithm.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblAlgorithm = new GridBagConstraints();
		gbc_lblAlgorithm.fill = GridBagConstraints.BOTH;
		gbc_lblAlgorithm.insets = new Insets(0, 0, 5, 0);
		gbc_lblAlgorithm.gridx = 0;
		gbc_lblAlgorithm.gridy = 0;
		pnlRight.add(lblAlgorithm, gbc_lblAlgorithm);
		
		algorithmSelector = new AlgorithmSelector(7);
		GridBagConstraints gbc_algorithmSelector = new GridBagConstraints();
		gbc_algorithmSelector.fill = GridBagConstraints.BOTH;
		gbc_algorithmSelector.insets = new Insets(0, 0, 5, 0);
		gbc_algorithmSelector.gridx = 0;
		gbc_algorithmSelector.gridy = 1;
		pnlRight.add(algorithmSelector, gbc_algorithmSelector);
		common.map.addHashMapListener(new AlgorithmSelectorListener());
		
		
		JPanel pnlAlgControl = new JPanel();
		GridBagConstraints gbc_pnlAlgControl = new GridBagConstraints();
		gbc_pnlAlgControl.fill = GridBagConstraints.BOTH;
		gbc_pnlAlgControl.gridx = 0;
		gbc_pnlAlgControl.gridy = 2;
		pnlRight.add(pnlAlgControl, gbc_pnlAlgControl);
		pnlAlgControl.setLayout(new GridLayout(0, 2, 0, 0));
		
		
		//Button to Decrease the Selected Algorithm
		JButton buttonLeft = new JButton("");
		buttonLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int algorithm = algorithmSelector.getAlgorithm();
				
				algorithm = (algorithm-1) % 8;
				if(algorithm<0){
					algorithm = 8+algorithm;
				}
				algorithmSelector.setAlgorithm(algorithm);
				algorithmChanged(common.map);
				
			}
		});
		pnlAlgControl.add(buttonLeft);
		
		//Button to increase the Selected Algorithm
		JButton buttonRight = new JButton("");
		buttonRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int algorithm = algorithmSelector.getAlgorithm();
				
				algorithm = (algorithm+1) % 8;
				algorithmSelector.setAlgorithm(algorithm);
				algorithmChanged(common.map);
				
			}
		});
		pnlAlgControl.add(buttonRight);
	}

	/**
	 * Change the Algorithm in the Data Model if the User Clicked on the Button
	 * */
	private void algorithmChanged(ListenerHashMap<String, Integer> map){
		int algorithm = algorithmSelector.getAlgorithm();
		
		if(map.get("CON")== null || !map.get("CON").equals(algorithm)){
			map.put("CON", algorithm << 4);
		}
	}
	
	/**
	 * Change the Algorithm in the algorithmSelector if it got Changed
	 * */
	private class AlgorithmSelectorListener implements HashMapListener<String, Integer>{
		public void valueChanged(String key, Integer value) {
			if(key.equals("CON")){
				algorithmSelector.setAlgorithm(value >> 4);
			}
		}
	}
	
	
	
	
	
}
