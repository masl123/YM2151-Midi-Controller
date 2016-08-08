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

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.LineBorder;

import ym2515.DataModel.Operator;

import java.awt.Color;


/**
 * The Panel for the Controls of the Operator part of the YM2151 Chip
 * */
public class OperatorPanel extends JPanel {
	
	private static final long serialVersionUID = -8662494362584813994L;

	
	/**
	 * Creates a new Operator Panel
	 * @param op the Operator Data Model
	 * */
	public OperatorPanel(Operator op) {
		
		//set up Layout
		setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 50, 50, 50, 50, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 50, 0, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		//Labels for first row of Controls
		JLabel lblLevel = new JLabel("Level");
		GridBagConstraints gbc_lblLevel = new GridBagConstraints();
		gbc_lblLevel.insets = new Insets(0, 0, 5, 5);
		gbc_lblLevel.gridx = 0;
		gbc_lblLevel.gridy = 1;
		add(lblLevel, gbc_lblLevel);
		
		JLabel lblMul = new JLabel("Mul");
		GridBagConstraints gbc_lblMul = new GridBagConstraints();
		gbc_lblMul.insets = new Insets(0, 0, 5, 5);
		gbc_lblMul.gridx = 1;
		gbc_lblMul.gridy = 1;
		add(lblMul, gbc_lblMul);
		
		JLabel lblDet1 = new JLabel("Det1");
		GridBagConstraints gbc_lblDet1 = new GridBagConstraints();
		gbc_lblDet1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDet1.gridx = 2;
		gbc_lblDet1.gridy = 1;
		add(lblDet1, gbc_lblDet1);
		
		JLabel lblDet2 = new JLabel("Det2");
		GridBagConstraints gbc_lblDet2 = new GridBagConstraints();
		gbc_lblDet2.insets = new Insets(0, 0, 5, 5);
		gbc_lblDet2.gridx = 3;
		gbc_lblDet2.gridy = 1;
		add(lblDet2, gbc_lblDet2);
		
		JLabel lblKSR = new JLabel("KSR");
		GridBagConstraints gbc_lblKSR = new GridBagConstraints();
		gbc_lblKSR.insets = new Insets(0, 0, 5, 0);
		gbc_lblKSR.gridx = 4;
		gbc_lblKSR.gridy = 1;
		add(lblKSR, gbc_lblKSR);
		
		
		//Knobs for first row
		NamedKnob knobLevel = new NamedKnob(op.map, "TL");
		GridBagConstraints gbc_knobLevel = new GridBagConstraints();
		gbc_knobLevel.fill = GridBagConstraints.BOTH;
		gbc_knobLevel.insets = new Insets(0, 0, 5, 5);
		gbc_knobLevel.gridx = 0;
		gbc_knobLevel.gridy = 2;
		add(knobLevel, gbc_knobLevel);
		
		NamedKnob knobMul = new NamedKnob(op.map, "MUL");
		GridBagConstraints gbc_knobMul = new GridBagConstraints();
		gbc_knobMul.fill = GridBagConstraints.BOTH;
		gbc_knobMul.insets = new Insets(0, 0, 5, 5);
		gbc_knobMul.gridx = 1;
		gbc_knobMul.gridy = 2;
		add(knobMul, gbc_knobMul);
		
		NamedKnob knobDet1 = new NamedKnob(op.map, "DT1");
		GridBagConstraints gbc_knobDet1 = new GridBagConstraints();
		gbc_knobDet1.fill = GridBagConstraints.BOTH;
		gbc_knobDet1.insets = new Insets(0, 0, 5, 5);
		gbc_knobDet1.gridx = 2;
		gbc_knobDet1.gridy = 2;
		add(knobDet1, gbc_knobDet1);
		
		NamedKnob knobDet2 = new NamedKnob(op.map, "DT2");
		GridBagConstraints gbc_knobDet2 = new GridBagConstraints();
		gbc_knobDet2.fill = GridBagConstraints.BOTH;
		gbc_knobDet2.insets = new Insets(0, 0, 5, 5);
		gbc_knobDet2.gridx = 3;
		gbc_knobDet2.gridy = 2;
		add(knobDet2, gbc_knobDet2);
		
		NamedKnob knobKSR = new NamedKnob(op.map, "KS");
		GridBagConstraints gbc_knobKSR = new GridBagConstraints();
		gbc_knobKSR.fill = GridBagConstraints.BOTH;
		gbc_knobKSR.insets = new Insets(0, 0, 5, 0);
		gbc_knobKSR.gridx = 4;
		gbc_knobKSR.gridy = 2;
		add(knobKSR, gbc_knobKSR);
		
		
		//Labels for second row of Controls
		JLabel lblAtR = new JLabel("AT (R)");
		GridBagConstraints gbc_lblAtR = new GridBagConstraints();
		gbc_lblAtR.insets = new Insets(0, 0, 5, 5);
		gbc_lblAtR.gridx = 0;
		gbc_lblAtR.gridy = 3;
		add(lblAtR, gbc_lblAtR);
		
		JLabel lblDec1R = new JLabel("Dec1 (R)");
		GridBagConstraints gbc_lblDec1R = new GridBagConstraints();
		gbc_lblDec1R.insets = new Insets(0, 0, 5, 5);
		gbc_lblDec1R.gridx = 1;
		gbc_lblDec1R.gridy = 3;
		add(lblDec1R, gbc_lblDec1R);
		
		JLabel lblDec1L = new JLabel("Dec1 (L)");
		GridBagConstraints gbc_lblDec1L = new GridBagConstraints();
		gbc_lblDec1L.insets = new Insets(0, 0, 5, 5);
		gbc_lblDec1L.gridx = 2;
		gbc_lblDec1L.gridy = 3;
		add(lblDec1L, gbc_lblDec1L);
		
		JLabel lblDec2R = new JLabel("Dec2 (R)");
		GridBagConstraints gbc_lblDec2R = new GridBagConstraints();
		gbc_lblDec2R.insets = new Insets(0, 0, 5, 5);
		gbc_lblDec2R.gridx = 3;
		gbc_lblDec2R.gridy = 3;
		add(lblDec2R, gbc_lblDec2R);
		
		JLabel lblRelR = new JLabel("Rel (R)");
		GridBagConstraints gbc_lblRelR = new GridBagConstraints();
		gbc_lblRelR.insets = new Insets(0, 0, 5, 0);
		gbc_lblRelR.gridx = 4;
		gbc_lblRelR.gridy = 3;
		add(lblRelR, gbc_lblRelR);
		
		
		//Knobs for second row
		NamedKnob knobATR = new NamedKnob(op.map, "AR");
		GridBagConstraints gbc_knobATR = new GridBagConstraints();
		gbc_knobATR.fill = GridBagConstraints.BOTH;
		gbc_knobATR.insets = new Insets(0, 0, 5, 5);
		gbc_knobATR.gridx = 0;
		gbc_knobATR.gridy = 4;
		add(knobATR, gbc_knobATR);
		
		NamedKnob knobDec1R = new NamedKnob(op.map, "D1R");
		GridBagConstraints gbc_knobDec1R = new GridBagConstraints();
		gbc_knobDec1R.fill = GridBagConstraints.BOTH;
		gbc_knobDec1R.insets = new Insets(0, 0, 5, 5);
		gbc_knobDec1R.gridx = 1;
		gbc_knobDec1R.gridy = 4;
		add(knobDec1R, gbc_knobDec1R);
		
		NamedKnob knobDec1L = new NamedKnob(op.map, "D1L");
		GridBagConstraints gbc_knobDec1L = new GridBagConstraints();
		gbc_knobDec1L.fill = GridBagConstraints.BOTH;
		gbc_knobDec1L.insets = new Insets(0, 0, 5, 5);
		gbc_knobDec1L.gridx = 2;
		gbc_knobDec1L.gridy = 4;
		add(knobDec1L, gbc_knobDec1L);
		
		NamedKnob knobDec2R = new NamedKnob(op.map, "D2R");
		GridBagConstraints gbc_knobDec2R = new GridBagConstraints();
		gbc_knobDec2R.fill = GridBagConstraints.BOTH;
		gbc_knobDec2R.insets = new Insets(0, 0, 5, 5);
		gbc_knobDec2R.gridx = 3;
		gbc_knobDec2R.gridy = 4;
		add(knobDec2R, gbc_knobDec2R);
		
		NamedKnob knobRel = new NamedKnob(op.map, "RR");
		GridBagConstraints gbc_knobRel = new GridBagConstraints();
		gbc_knobRel.insets = new Insets(0, 0, 5, 0);
		gbc_knobRel.fill = GridBagConstraints.BOTH;
		gbc_knobRel.gridx = 4;
		gbc_knobRel.gridy = 4;
		add(knobRel, gbc_knobRel);

	}
}
