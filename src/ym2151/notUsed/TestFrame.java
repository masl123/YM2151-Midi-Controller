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

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestFrame extends JFrame {
	
	public static void main(String[] args) {
		TestFrame frame = new TestFrame(new SwitchButtonBankPanel(16, true));
		frame.setVisible(true);
		
	}
	
	
	public TestFrame(JPanel test){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(test, BorderLayout.CENTER);
		
		
	}
}
