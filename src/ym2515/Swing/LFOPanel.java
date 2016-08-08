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

import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import ym2515.DataModel.LFO;
import ym2515.Swing.ListenerHashMap.HashMapListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * The Panel for the Controls of the LFO part of the YM2151 Chip
 * */
public class LFOPanel extends JPanel{
	
	/**
	 * Creates a new LFO Panel
	 * @param lfo the LFO Data Model
	 * 
	 * */
	public LFOPanel(LFO lfo) {
		
		
		//setup Layout
		setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{70, 70, 70, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 70, 0, 70, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		//add Labels for first row of Controls
		JLabel lblPhsModDepth = new JLabel("PHS Mod Depth");
		GridBagConstraints gbc_lblPhsModDepth = new GridBagConstraints();
		gbc_lblPhsModDepth.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhsModDepth.gridx = 0;
		gbc_lblPhsModDepth.gridy = 1;
		add(lblPhsModDepth, gbc_lblPhsModDepth);
		
		JLabel lblFreq = new JLabel("Freq:");
		GridBagConstraints gbc_lblFreq = new GridBagConstraints();
		gbc_lblFreq.insets = new Insets(0, 0, 5, 5);
		gbc_lblFreq.gridx = 1;
		gbc_lblFreq.gridy = 1;
		add(lblFreq, gbc_lblFreq);
		
		JLabel lblWave = new JLabel("Wave");
		GridBagConstraints gbc_lblWave = new GridBagConstraints();
		gbc_lblWave.insets = new Insets(0, 0, 5, 0);
		gbc_lblWave.gridx = 2;
		gbc_lblWave.gridy = 1;
		add(lblWave, gbc_lblWave);
		
		
		//add Knobs of first Row
		NamedKnob namedKnob = new NamedKnob(lfo.map, "PMD");
		GridBagConstraints gbc_namedKnob = new GridBagConstraints();
		gbc_namedKnob.insets = new Insets(0, 0, 5, 5);
		gbc_namedKnob.fill = GridBagConstraints.BOTH;
		gbc_namedKnob.gridx = 0;
		gbc_namedKnob.gridy = 2;
		add(namedKnob, gbc_namedKnob);
		
		NamedKnob knobFreq = new NamedKnob(lfo.map, "LFRQ");
		GridBagConstraints gbc_knobFreq = new GridBagConstraints();
		gbc_knobFreq.fill = GridBagConstraints.BOTH;
		gbc_knobFreq.insets = new Insets(0, 0, 5, 5);
		gbc_knobFreq.gridx = 1;
		gbc_knobFreq.gridy = 2;
		add(knobFreq, gbc_knobFreq);
		
		NamedKnob knobWave = new NamedKnob(lfo.map, "WF");
		GridBagConstraints gbc_knobWave = new GridBagConstraints();
		gbc_knobWave.fill = GridBagConstraints.BOTH;
		gbc_knobWave.insets = new Insets(0, 0, 5, 0);
		gbc_knobWave.gridx = 2;
		gbc_knobWave.gridy = 2;
		add(knobWave, gbc_knobWave);
		
		
		//add Labels for Second row of Controls
		JLabel lblAp = new JLabel("AMP Mod Depth");
		GridBagConstraints gbc_lblAp = new GridBagConstraints();
		gbc_lblAp.insets = new Insets(0, 0, 5, 5);
		gbc_lblAp.gridx = 0;
		gbc_lblAp.gridy = 3;
		add(lblAp, gbc_lblAp);
		
		JLabel lblNoiseFreq = new JLabel("Noise Freq");
		GridBagConstraints gbc_lblNoiseFreq = new GridBagConstraints();
		gbc_lblNoiseFreq.insets = new Insets(0, 0, 5, 0);
		gbc_lblNoiseFreq.gridx = 2;
		gbc_lblNoiseFreq.gridy = 3;
		add(lblNoiseFreq, gbc_lblNoiseFreq);
		
		
		
		//add Controls for Second row
		NamedKnob knobAmD = new NamedKnob(lfo.map, "AMD");
		GridBagConstraints gbc_knobAP = new GridBagConstraints();
		gbc_knobAP.insets = new Insets(0, 0, 5, 5);
		gbc_knobAP.fill = GridBagConstraints.BOTH;
		gbc_knobAP.gridx = 0;
		gbc_knobAP.gridy = 4;
		add(knobAmD, gbc_knobAP);
		
		final JToggleButton tglbtnEnableNoise = new JToggleButton("Enable Noise");
		GridBagConstraints gbc_tglbtnEnableNoise = new GridBagConstraints();
		gbc_tglbtnEnableNoise.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnEnableNoise.gridx = 1;
		gbc_tglbtnEnableNoise.gridy = 4;
		add(tglbtnEnableNoise, gbc_tglbtnEnableNoise);
		tglbtnEnableNoise.addActionListener(new ToggleButtonListener(lfo.map, "NE", tglbtnEnableNoise));
		
		NamedKnob knobNoiseFreq = new NamedKnob(lfo.map, "NFRQ");
		GridBagConstraints gbc_knobNoiseFreq = new GridBagConstraints();
		gbc_knobNoiseFreq.insets = new Insets(0, 0, 5, 0);
		gbc_knobNoiseFreq.fill = GridBagConstraints.BOTH;
		gbc_knobNoiseFreq.gridx = 2;
		gbc_knobNoiseFreq.gridy = 4;
		add(knobNoiseFreq, gbc_knobNoiseFreq);

	}
	
	
	/**
	 * Add a Listener, if the Noise Enable Button Changes
	 * */
	private class ToggleButtonListener implements ActionListener, HashMapListener<String, Integer>{
		ListenerHashMap<String, Integer> map ;
		String KEY;
		JToggleButton btn;
		
		public ToggleButtonListener(ListenerHashMap<String, Integer> map , String KEY, JToggleButton btn){
			this.KEY=KEY;
			this.map=map;
			this.btn=btn;
			map.addHashMapListener(this);
		}
		
		
		
		public void valueChanged(String key, Integer value) {
			if(key.equals(KEY) && map.get(KEY) != value)
				btn.setSelected(value >= 64);
		}

		public void actionPerformed(ActionEvent e) {
			int s = btn.isSelected() ? 127 : 0;
			if(map.get(KEY) != s)
				map.put(KEY, s);
		}
	}

}
