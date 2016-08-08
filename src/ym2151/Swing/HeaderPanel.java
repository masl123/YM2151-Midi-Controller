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

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;


/**
 * A Panel (like a JLabel) where you can set the Text which should be displayed. But you can set the BG Color (unlike the JLabel). 
 * 
 * */
public class HeaderPanel extends JPanel {
	
	private JLabel lblNewLabel;
	
	
	/**
	 * Create the panel.
	 */
	public HeaderPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
		setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel);
		setPreferredSize(lblNewLabel.getPreferredSize());
		setMinimumSize(lblNewLabel.getMinimumSize());
		setSize(10,100);
		
	}

	
	/**
	 * Sets the displayed Text
	 * @param s the Text to display
	 * */
	public void setText(String s){
		lblNewLabel.setText(s);
	}
	
}
