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
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.JToggleButton;

public class BankPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public BankPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{881, 16, 82, 82, 0, 0};
		gridBagLayout.rowHeights = new int[]{34, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		SwitchButtonBankPanel switchButtonBankPanel = new SwitchButtonBankPanel(16, true);
		GridBagConstraints gbc_switchButtonBankPanel = new GridBagConstraints();
		gbc_switchButtonBankPanel.fill = GridBagConstraints.BOTH;
		gbc_switchButtonBankPanel.insets = new Insets(0, 0, 0, 5);
		gbc_switchButtonBankPanel.gridx = 0;
		gbc_switchButtonBankPanel.gridy = 0;
		add(switchButtonBankPanel, gbc_switchButtonBankPanel);
		switchButtonBankPanel.setButton(0);
		
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(128, 128, 128));
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.insets = new Insets(0, 0, 0, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 0;
		add(separator, gbc_separator);
		
		JButton btnLoad = new JButton("L");
		GridBagConstraints gbc_btnLoad = new GridBagConstraints();
		gbc_btnLoad.insets = new Insets(0, 0, 0, 5);
		gbc_btnLoad.fill = GridBagConstraints.BOTH;
		gbc_btnLoad.gridx = 2;
		gbc_btnLoad.gridy = 0;
		add(btnLoad, gbc_btnLoad);
		
		JButton btnSave = new JButton("S");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.gridx = 3;
		gbc_btnSave.gridy = 0;
		add(btnSave, gbc_btnSave);
		
		JToggleButton tglbtnLoadlfo = new JToggleButton("Load LFO");
		GridBagConstraints gbc_tglbtnLoadlfo = new GridBagConstraints();
		gbc_tglbtnLoadlfo.fill = GridBagConstraints.VERTICAL;
		gbc_tglbtnLoadlfo.gridx = 4;
		gbc_tglbtnLoadlfo.gridy = 0;
		add(tglbtnLoadlfo, gbc_tglbtnLoadlfo);
		
		
	}

}
