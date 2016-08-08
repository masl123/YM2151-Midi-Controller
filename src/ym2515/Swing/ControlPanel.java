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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import ym2515.DataModel.Instrument;

import javax.swing.JScrollPane;

public class ControlPanel extends JPanel {
	/*
	 * --------------------
	 * MIDI PC			  |
	 * --------------------
	 * If "Channel Mode" Active:
	 * 	Load Program into Channel (Channel 0-8)
	 * 	Load Program into ALL CHANNELS (Channel 9)
	 * 
	 * If "Poly Mode" Active:
	 * 	Load Program into ALL CHANNELS (Channels not used)
	 * 
	 * TODO Implement bus.sendMessage(ShortMessage.PROGRAM_CHANGE, 0 CHANNEL, 1 PROG NO, 0);
	 * 
	 * --------------------
	 * MIDI CC:			  |
	 * --------------------
	 * 
	 * OPERATOR 1 (M1): 16 - 25
	 * 16	Level
	 * 17	Mul
	 * 18   Det1
	 * 19   Det2
	 * 20	KSR
	 * 21	At (Rate)
	 * 22   Dec1 (Rate)
	 * 23   Dec1 (Level)
	 * 24   Dec2 (Rate)
	 * 25   Rel (Rate)
	 * 
	 * OPERATOR 2 (M2): 26 - 35
	 * 26	Level
	 * 27	Mul
	 * 28   Det1
	 * 29   Det2
	 * 30	KSR
	 * 31	At (R)
	 * 32   Dec1 (R)
	 * 33   Dec1 (L)
	 * 34   Dec2 (R)
	 * 35   Rel (R)
	 * 
	 * OPERATOR 3 (C1): 36 - 45
	 * 36	Level
	 * 37	Mul
	 * 38   Det1
	 * 39   Det2
	 * 40	KSR
	 * 41	At (R)
	 * 42   Dec1 (R)
	 * 43   Dec1 (L)
	 * 44   Dec2 (R)
	 * 45   Rel (R)
	 * 
	 * OPERATOR 4 (C2): 46 - 55
	 * 46	Level
	 * 47	Mul
	 * 48   Det1
	 * 49   Det2
	 * 50	KSR
	 * 51	At (R)
	 * 52   Dec1 (R)
	 * 53   Dec1 (L)
	 * 54   Dec2 (R)
	 * 55   Rel (R)
	 * 
	 * COMMON: 56 - 67
	 * 56	Op #1 - ON/OFF
	 * 57	Op #2 - ON/OFF
	 * 58	Op #3 - ON/OFF
	 * 59	Op #4 - ON/OFF
	 * 60	AMSense Op #1 - ON/OFF
	 * 61	AMSense Op #2 - ON/OFF
	 * 62	AMSense Op #3 - ON/OFF
	 * 63	AMSense Op #4 - ON/OFF
	 * 64	FB
	 * 65	AMS
	 * 66	PMS
	 * 67	Algorithm
	 * 
	 * LFO 68 - 71
	 * 68	Freq
	 * 69	LFO Wave
	 * 70	AMP Mod Depth
	 * 71	Phase Mod Depth
	 * 72	Noise Freq  
	 * 73	Noise Enable
	 * 
	 * 
	 * OTHER:
	 * 74	POLY/CHANNEL Mode (0 = CHANNEL; 127 = POLY) //TODO maybe switch around
	 * 
	 * 
	 * 
	 * 
	 * _________________________________
	 * FB : Feedback
	 * CONNECT: Algorithm
	 * 
	 * */
	
	private JPanel contentPane;
	
	
	/**
	 * Create a new ControlPanel
	 * @param ins the Instrument to Control
	 * */
	public ControlPanel(Instrument ins) {
		//setup Layout
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(false);
		
		
		//add a ScrollPane (in case the window is to small)
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		contentPane = new JPanel();
		scrollPane.setViewportView(contentPane);
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{350, 350, 0, 0};
		gbl_contentPane.rowHeights = new int[]{35, 138, 35, 138, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		//add Headers for the First Row of Panels
		HeaderPanel headerOp1 = new HeaderPanel();
		headerOp1.setText("OPERATOR 1 (M1)");
		headerOp1.setBackground(new Color(30, 144, 255));
		GridBagConstraints gbc_headerOp1 = new GridBagConstraints();
		gbc_headerOp1.insets = new Insets(0, 0, 5, 5);
		gbc_headerOp1.fill = GridBagConstraints.BOTH;
		gbc_headerOp1.gridx = 0;
		gbc_headerOp1.gridy = 0;
		contentPane.add(headerOp1, gbc_headerOp1);
		
		HeaderPanel headerOp2 = new HeaderPanel();
		headerOp2.setText("OPERATOR 2 (M2)");
		headerOp2.setBackground(new Color(255, 255, 0));
		GridBagConstraints gbc_headerOp2 = new GridBagConstraints();
		gbc_headerOp2.insets = new Insets(0, 0, 5, 5);
		gbc_headerOp2.fill = GridBagConstraints.BOTH;
		gbc_headerOp2.gridx = 1;
		gbc_headerOp2.gridy = 0;
		contentPane.add(headerOp2, gbc_headerOp2);
		
		HeaderPanel headerCommon = new HeaderPanel();
		headerCommon.setBackground(new Color(199, 21, 133));
		headerCommon.setText("COMMON");
		GridBagConstraints gbc_headerCommon = new GridBagConstraints();
		gbc_headerCommon.insets = new Insets(0, 0, 5, 0);
		gbc_headerCommon.fill = GridBagConstraints.BOTH;
		gbc_headerCommon.gridx = 2;
		gbc_headerCommon.gridy = 0;
		contentPane.add(headerCommon, gbc_headerCommon);
		
		
		//add OP1, Op2 and Common Panel
		OperatorPanel op1Panel = new OperatorPanel(ins.op1);
		GridBagConstraints gbc_op1Panel = new GridBagConstraints();
		gbc_op1Panel.fill = GridBagConstraints.BOTH;
		gbc_op1Panel.insets = new Insets(0, 0, 5, 5);
		gbc_op1Panel.gridx = 0;
		gbc_op1Panel.gridy = 1;
		contentPane.add(op1Panel, gbc_op1Panel);
		
		OperatorPanel op2Panel = new OperatorPanel(ins.op2);
		GridBagConstraints gbc_op2Panel = new GridBagConstraints();
		gbc_op2Panel.fill = GridBagConstraints.BOTH;
		gbc_op2Panel.insets = new Insets(0, 0, 5, 5);
		gbc_op2Panel.gridx = 1;
		gbc_op2Panel.gridy = 1;
		contentPane.add(op2Panel, gbc_op2Panel);
		
		CommonPanel commonPanel = new CommonPanel(ins.common);
		GridBagConstraints gbc_commonPanel = new GridBagConstraints();
		gbc_commonPanel.fill = GridBagConstraints.BOTH;
		gbc_commonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_commonPanel.gridx = 2;
		gbc_commonPanel.gridy = 1;
		contentPane.add(commonPanel, gbc_commonPanel);
		
		
		//add Headers for the Second Row of Panels
		HeaderPanel headerOp3 = new HeaderPanel();
		headerOp3.setText("OPERATOR 3 (C1)");
		headerOp3.setBackground(new Color(50, 205, 50));
		GridBagConstraints gbc_headerOp3 = new GridBagConstraints();
		gbc_headerOp3.fill = GridBagConstraints.BOTH;
		gbc_headerOp3.insets = new Insets(0, 0, 5, 5);
		gbc_headerOp3.gridx = 0;
		gbc_headerOp3.gridy = 2;
		contentPane.add(headerOp3, gbc_headerOp3);
		
		HeaderPanel headerOp4 = new HeaderPanel();
		headerOp4.setText("OPERATOR 4 (C2)");
		headerOp4.setBackground(new Color(218, 165, 32));
		GridBagConstraints gbc_headerOp4 = new GridBagConstraints();
		gbc_headerOp4.insets = new Insets(0, 0, 5, 5);
		gbc_headerOp4.fill = GridBagConstraints.BOTH;
		gbc_headerOp4.gridx = 1;
		gbc_headerOp4.gridy = 2;
		contentPane.add(headerOp4, gbc_headerOp4);
		
		HeaderPanel headerLFO = new HeaderPanel();
		headerLFO.setText("LFO");
		headerLFO.setBackground(new Color(0, 191, 255));
		GridBagConstraints gbc_headerLFO = new GridBagConstraints();
		gbc_headerLFO.insets = new Insets(0, 0, 5, 0);
		gbc_headerLFO.fill = GridBagConstraints.BOTH;
		gbc_headerLFO.gridx = 2;
		gbc_headerLFO.gridy = 2;
		contentPane.add(headerLFO, gbc_headerLFO);
		
		
		//add op3, op4 and LFO Panel
		OperatorPanel op3Panel = new OperatorPanel(ins.op3);
		GridBagConstraints gbc_op3Panel = new GridBagConstraints();
		gbc_op3Panel.fill = GridBagConstraints.BOTH;
		gbc_op3Panel.insets = new Insets(0, 0, 0, 5);
		gbc_op3Panel.gridx = 0;
		gbc_op3Panel.gridy = 3;
		contentPane.add(op3Panel, gbc_op3Panel);
		
		OperatorPanel op4Panel = new OperatorPanel(ins.op4);
		GridBagConstraints gbc_op4Panel = new GridBagConstraints();
		gbc_op4Panel.fill = GridBagConstraints.BOTH;
		gbc_op4Panel.insets = new Insets(0, 0, 0, 5);
		gbc_op4Panel.gridx = 1;
		gbc_op4Panel.gridy = 3;
		contentPane.add(op4Panel, gbc_op4Panel);
		
		LFOPanel lfoPanel = new LFOPanel(ins.lfo);
		GridBagConstraints gbc_lfoPanel = new GridBagConstraints();
		gbc_lfoPanel.fill = GridBagConstraints.BOTH;
		gbc_lfoPanel.gridx = 2;
		gbc_lfoPanel.gridy = 3;
		contentPane.add(lfoPanel, gbc_lfoPanel);

	}
}
