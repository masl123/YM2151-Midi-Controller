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
import javax.swing.JToggleButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;



/**
 * A Panel with Buttons named from 1 to 4
 * */
public class SwitchButtonRowPanel extends JPanel{
	
	
	private LinkedList<SwitchButtonListener> listeners = new LinkedList<SwitchButtonListener>();
	private JToggleButton[] buttons;
	
	
	/**
	 * Create a new SwitchButtonRowPanel
	 * */
	public SwitchButtonRowPanel() {
		setLayout(new GridLayout(0, 4, 0, 0));
		
		JToggleButton button = new JToggleButton("1");
		button.addActionListener(new ButtonListener(0,button));
		add(button);
		
		JToggleButton button_1 = new JToggleButton("2");
		button_1.addActionListener(new ButtonListener(1,button_1));
		add(button_1);
		
		JToggleButton button_2 = new JToggleButton("3");
		button_2.addActionListener(new ButtonListener(2,button_2));
		add(button_2);
		
		JToggleButton button_3 = new JToggleButton("4");
		button_3.addActionListener(new ButtonListener(3,button_3));
		add(button_3);
		
		buttons = new JToggleButton[]{
				button, button_1, button_2, button_3		
		};
		
	}

	/**
	 * Sets a Button
	 * @param no Button to Set
	 * @param b Selected (true) or not Selected (false)
	 * */
	public void setButton(int no, boolean b){
		if(no <  0 || no >= buttons.length){
			return;
		}
		
		buttons[no].setSelected(b);
	}
	
	
	
	
	
	

	/**
	 * Add a Listener to this Object
	 * */
	public void addSwitchButtonListener(SwitchButtonListener l){
		listeners.add(l);
	}
	
	/**
	 * remove a Listener from this Object
	 * */
	public void removeSwitchButtonListener(SwitchButtonListener l){
		listeners.remove(l);
	}
	
	/**
	 * remove all Listeners from this Object
	 * */
	public void removeAllSwitchButtonListener(){
		listeners = new LinkedList<SwitchButtonListener>();
	}
	
	/**
	 * Lets all the Listeners know that something changed
	 * */
	private void dispatchEvent(int no, int state){
		for(SwitchButtonListener l : listeners){
			l.buttonClicked(no, state);
		}	
	}
	
	/**
	 * The type of listener to add to the SwitchButtonRowPanel
	 * */
	public interface SwitchButtonListener{
		public void buttonClicked(int no, int state);
	}
	
	/**
	 * a Listener to add to all the created Buttons
	 * */
	private class ButtonListener implements ActionListener{
		final int no;
		final JToggleButton button;
		public ButtonListener(int no, JToggleButton button){
			this.no=no;
			this.button=button;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			dispatchEvent(no, button.isSelected()? 127 : 0);
		}
	}
	
}
