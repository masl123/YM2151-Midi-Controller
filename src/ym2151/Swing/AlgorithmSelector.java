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
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Graphics;


/**
 * A  Panel which shows the Current selected Algorithm
 * */
public class AlgorithmSelector extends JPanel {

	private static final long serialVersionUID = 6752262938729908666L;
	
	private int algo = 0;
	private int max;
	
	
	/**
	 * A  Panel which shows the Current selected Algorithm
	 * @param max the number of Algorithms available
	 * */
	public AlgorithmSelector(int max) {
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.max = max;
	}

	/**
	 * Sets the Algorithm to the Value
	 * @param i the Algorithm to Select
	 * 
	 * */
	public void setAlgorithm(int i){
		if(i > max){
			i = max;
		}
		if(i < 0){
			i = 0;
		}
		
		algo = i;
		repaint();
	}
	
	/**
	 * @return the current Algorithm selected
	 * */
	public int getAlgorithm(){
		return algo;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		float f=20.0f; // font size.
		g.setFont(g.getFont().deriveFont(f));
		g.drawString(algo+"",10,(int) (f+10));// provides optimum gap for printing

	}
	
}
